-- получить
--  - имена всех person, которые не состоят в компании с id = 5;
--  - название компании для каждого человека.
select p.name as person_name, c.name as company_name
from person as p
left join company as c
on p.company_id = c.id
where c.id is null or c.id <> 5

-- название компании с максимальным количеством человек + количество человек в этой компании
select companies_counts.name, companies_counts.persons_count
from (select c.name, count(p.name) as persons_count
     from company as c
     left join person as p
     on c.id = p.company_id
     group by c.name) as companies_counts
     where companies_counts.persons_count
     in (select max(persons_count)
        from (select c.name, count(p.name) as persons_count
             from company as c
             left join person as p
             on c.id = p.company_id
             group by c.name))
