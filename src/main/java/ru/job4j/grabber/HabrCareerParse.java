package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private final DateTimeParser dateTimeParser;
    private static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PREFIX = "/vacancies?page=";
    public static final String SUFFIX = "&q=Java%20developer&type=all";

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private static String retrieveDescription(String link) {
        String description = "";
        Connection connection = Jsoup.connect(link);
        try {
            Document document = connection.get();
            Elements rows = document.select(".faded-content__container");
            description = rows.first().text();
        } catch (IOException e) {
            System.out.println("Не удалось получить описание вакансии:");
            e.printStackTrace();
        }
        return description;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        Connection connection = Jsoup.connect(link);
        try {
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = titleElement.child(0);
                String vacancyName = titleElement.text();
                LocalDateTime vacancyDate = dateTimeParser.parse(row.select(".vacancy-card__date").first()
                        .child(0).attr("datetime"));
                String currentLink = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                String description = retrieveDescription(currentLink);
                list.add(new Post(vacancyName, currentLink, description, vacancyDate));
            });
        } catch (IOException e) {
            System.out.println("Не удалось получить вакансии:");
            e.printStackTrace();
        }
        return list;
    }
}
