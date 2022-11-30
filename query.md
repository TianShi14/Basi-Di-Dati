Query 1:
~~~sql
SELECT DISTINCT d.director, COALESCE(min(gross - budget), -1) AS min, COALESCE(max(gross - budget), -1) AS max, COALESCE(ROUND(avg(gross - budget)), -1) AS avg
FROM directors AS d LEFT OUTER JOIN movies AS m
ON d.director = m.director
WHERE 2022 - d.yearofbirth >= 50
GROUP BY d.director
ORDER BY d.director
~~~
Query 2:
~~~sql
SELECT DISTINCT conta2.title, conta2.year
FROM
(SELECT max(conta.year) AS maxx
 FROM
  (SELECT ma.title, ma.year, count(ma.award) AS ca
   FROM movieawards ma
   WHERE ma.result = 'won'
   GROUP BY ma.title, ma.year
  ) AS conta
 WHERE conta.ca >= 3
) AS conta3
JOIN
(SELECT contaaa.title, contaaa.year
 FROM
  (SELECT ma.title, ma.year, count(ma.award) AS ca
   FROM movieawards ma
   WHERE ma.result = 'won'
   GROUP BY ma.title, ma.year
  ) AS contaaa
 WHERE contaaa.ca >= 3
) AS conta2
ON conta3.maxx = conta2.year
ORDER BY conta2.title, conta2.year
~~~

Query 3:
~~~ sql
SELECT *
FROM
((SELECT DISTINCT m.title, m.year, COALESCE('least expensive') AS feature
  FROM
  (SELECT min(m.budget) AS least
   FROM movies AS m) AS minimo
   JOIN movies as m
   ON m.budget = minimo.least)
UNION
  (SELECT DISTINCT m.title, m.year, COALESCE('most profitable') AS feature
   FROM
   (SELECT max(m.gross - m.budget) AS most
    FROM movies AS m) AS profitto
    JOIN movies as m
    ON (m.gross - m.budget) = profitto.most)) as res
ORDER BY res.title, res.year, res.feature
~~~

Query 4:
~~~ sql
SELECT *
FROM
((SELECT da.award, da.year, dir.director
 FROM 
 (SELECT DISTINCT m.director
  FROM movies AS m
  WHERE m.gross > 1 AND 2022 - m.year < 5) as dir
 JOIN directorawards AS da ON da.director = dir.director)
UNION
(SELECT ma.award, ma.year, dir.director
 FROM 
 movieawards as ma, movies as m,
(SELECT DISTINCT m.director
 FROM movies AS m
 WHERE m.gross > 1 AND 2022 - m.year < 5) as dir
WHERE ma.title = m.title AND ma.year = m.year AND m.director = dir.director AND ma.award LIKE '%, best director')) as pene
ORDER BY award, year, director
~~~

Query 5:
~~~ sql
SELECT DISTINCT *
FROM
((SELECT won.title, won.year, ROUND(CAST(won.num_won AS NUMERIC) / nom.num_nom, 2) AS "tasso di successo"
  FROM
  (SELECT m.title, m.year, count(result) as num_won
   FROM movieawards as ma JOIN movies as m
   ON ma.result= 'won' AND ma.title = m.title AND ma.year = m.year
   GROUP BY m.title, m.year) as won
JOIN 
  (SELECT m.title, m.year, count(result) as num_nom
   FROM movieawards as ma JOIN movies as m
   ON ma.title = m.title AND ma.year = m.year
   GROUP BY m.title, m.year) as nom
   ON won.title = nom.title AND won.year = nom.year)
UNION
 (SELECT null_nom.title, null_nom.year, COALESCE(-1) AS "tasso di successo"
  FROM
  (SELECT m.title, m.year, count(result) as res
   FROM movies as m LEFT OUTER JOIN movieawards as ma
   ON ma.title = m.title AND ma.year = m.year
   GROUP BY m.title, m.year) AS null_nom
  WHERE null_nom.res = 0)) AS success
ORDER BY title, year, "tasso di successo"
~~~

Query 6:
~~~ sql
CREATE TEMP VIEW oscar_won AS(
SELECT os.title, os.year, count(os.result) as conta
FROM
 (SELECT *
 FROM movieawards as ma
 WHERE ma.award LIKE 'Oscar,%' AND ma.result = 'won'
 ) AS os
 GROUP BY os.title, os.year
);

SELECT DISTINCT ow.title, ow.year
FROM
(SELECT max(oscar_won.conta) AS maxx
 FROM oscar_won) AS max_os
 JOIN oscar_won AS ow
 ON max_os.maxx = ow.conta
ORDER BY ow.title, ow.year
~~~

Query 7:
~~~ sql
CREATE TEMP VIEW age AS(
 SELECT d.yearofbirth AS year, d.director
 FROM 
 movieawards AS ma 
 JOIN movies AS m ON ma.title = m.title AND ma.year = m.year
 JOIN directors AS d ON m.director = d.director
 WHERE ma.award ILIKE 'Oscar, best director' AND ma.result = 'won'
 UNION 
 SELECT d.yearofbirth AS year, d.director
 FROM directorawards AS da
 JOIN directors AS d ON da.director = d.director
 WHERE da.award ILIKE 'Oscar' AND da.result = 'won');

SELECT DISTINCT *
FROM
(-- youngest 
 SELECT a.director, COALESCE('youngest') AS feature
 FROM age AS a
 JOIN (SELECT max(year) AS year
       FROM age) AS young
 ON a.year = young.year

 UNION

 -- oldest 
 SELECT a.director, COALESCE('oldest') AS feature
 FROM age AS a
 JOIN (SELECT min(year) AS year
 	   FROM age) AS young
 ON a.year = young.year) AS res
ORDER BY res.director, res.feature
~~~

Query 8: 
~~~ sql
SELECT 
	CASE
		WHEN num_film <> 0 THEN ROUND(CAST(num_osc AS NUMERIC) / num_film, 2) 
		WHEN num_film = 0 THEN -1 
	END percentuale
FROM
(SELECT count(*) as num_osc
 FROM
 (SELECT DISTINCT title, year -- film che hanno vinto due o più Oscar non vengono contati due o più volte
  FROM movieawards AS ma
  WHERE ma.year >= 1980 AND ma.year <= 1989 AND ma.award ILIKE 'Oscar,%' AND ma.result = 'won') AS osc) AS perc_osc,
(SELECT count(*) as num_film
 FROM 
 (SELECT DISTINCT title, year
  FROM movies AS m
  WHERE m.year >= 1980 AND m.year <= 1989) AS mov) AS perc_mov;
~~~

Query 9:
~~~ sql
SELECT DISTINCT m.director
FROM movies AS m JOIN movieawards AS ma ON m.title = ma.title AND m.year = ma.year
WHERE (ma.award ILIKE '%miglior regista' OR ma.award ILIKE '%best director') AND ma.result = 'won' AND (m.gross - m.budget) <= 0
ORDER BY m.director 
~~~
