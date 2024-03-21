package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HabrCareerDateTimeParserTest {

    @Test
    void whenParseDateTimeStringToLocalDateTimeThenBe() {
        HabrCareerDateTimeParser parser = new HabrCareerDateTimeParser();
        List<LocalDateTime> parserList = List.of(
                parser.parse("2024-03-18T18:22:09+03:00"),
                parser.parse("2024-02-21T18:21:56+03:00"),
                parser.parse("2024-03-13T14:05:04+03:00"));
        List<LocalDateTime> resultList = List.of(
                LocalDateTime.of(2024, 3, 18, 18, 22, 9),
                LocalDateTime.of(2024, 2, 21, 18, 21, 56),
                LocalDateTime.of(2024, 3, 13, 14, 5, 4));
        assertThat(parserList).containsSequence(resultList);
    }
}