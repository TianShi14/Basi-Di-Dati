# <p align = "center"> Relational Algebra in and out of SQL </p>

###### <p align = "center"> Alcuni simboli non sono precisissimi perchè Markdown non è LaTeX e la liberia è limitata, si capisce comunque però quindi poche lagne </p>

#
# INDEX

- [Selection and Projection](#selection-and-projection)
- []()
- []()

#

# Selection and Projection

Pochi cazzi, dritti al punto con degli esempi facili facili che si capiscono:

Dato T1:

### T1

| **sid** | **sname** | **age** | **rating** |
| :---: | :---: | :---: | :---: |
|2832|Platone|63|13.5|
|1111|Martina dell'Ombra|13|69.69|
|1453|Beppe Fenoglio|63|7.2|
|3453|Carmelo|35|10.7|

Ovvero in SQL

~~~sql
-- notazione per creare la tabella

CREATE TABLE T1
(
    sid CHAR(4),
    sname VARCHAR(50),
    age INTEGER,
    rating FLOAT,
    PRIMARY KEY(sid)
);

-- notazione per inserire i valori

INSERT INTO T1 (sid, sname, age, rating) VALUES ('2832', 'Platone', 63, 13.5);

INSERT INTO T1 (sid, sname, age, rating) VALUES ('1111', 'Martina dell Ombra', 13, 69.69);
                /* piccola nota, volendo inserire Martina dell'Ombra basterebbe fare 
                $$Martina dell'Ombra$$ */
INSERT INTO T1 (sid, sname, age, rating) VALUES ('1453', 'Beppe Fenoglio', 63, 7.2);

INSERT INTO T1 (sid, sname, age, rating) VALUES ('3453', 'Carmelo', 35, 10.7);

-- notazione per mostrare tutti i valori

SELECT * FROM T1; /* IMPORTANTE: se eseguite solo una query potete anche evitare il ;
                  ma senza implementazione e con più query poi non ve le distingue /*
~~~

#

<details>

<summary><h2> Selection </h2> </summary>

L'operazione di selezione *Sig1* = $\sigma_{age > 42}$(*T1*)

In SQL
~~~sql
SELECT * -- Seleziona tutte le righe e le colonne...
FROM T1  -- ...dalla tabella T1...
WHERE age > 42  -- ...tale che l'età sia maggiore di 42.
~~~

Dà come risultato:

### Sig1

| **sid** | **sname** | **age** | **rating** |
| :---: | :---: | :---: | :---: |
|2832|Platone|63|13.5|
|1453|Beppe Fenoglio|63|7.2|

</details>

#

<details>

<summary><h2> Projection </h2> </summary>

L'operazione di proiezione *Pro1* = $\pi_{age}$(*T1*)

In SQL
~~~sql
SELECT age --seleziona solo la colonna "age"
FROM T1
~~~

Produce:

### Pro1

| **age** | 
:---:
63
13
63
35
#### Piccola nota: dalle lezioni esce fuori che l'operatore di proiezione *dovrebbe* eliminare i duplicati, ma sta cosa nse vede pe niente, questo perchè ancora non abbiamo tenuto conto di un terminino importantino che vediamo qui di seguito (ricordo di fare opportune verifiche sul [sito](https://www.db-fiddle.com) consigliato dal Professore per esercitarsi):

~~~sql
SELECT distinct age -- distinct elimina le copie
FROM T1
~~~

Che invece darà in "output":

| **age** | 
:---:
63
13
35

</details>

#

<details>

<summary><h2> Projection of a Selection </h2> </summary>

E infine un'operazione tipo *SP* = $\pi_{sname,age}(\sigma_{rating>9}(T1))$

In SQL
~~~sql
SELECT sname, age
FROM T1
WHERE rating > 9;
~~~

Dà:

### SP

 **sname** | **age** | 
|:---: | :---: | 
|Martina dell'Ombra|13|
|Platone|63|
|Carmelo|35|

#### Non dò certezze sul fatto che sia una ricorrenza voluta da PostgreSQL, anche perchè non c'ho troppa sbatta di andare a verificare ciò che sto per dire, però pare che dall'alto verso il basso il risultato della query abbia organizzato i dati che abbiamo "proiettato" dal rating più alto fino al più basso (può essere che lo faccia sempre come no).

</details>

Per questi esempi useremo queste tabelle e il [sito](https://www.db-fiddle.com) consigliato:

~~~sql
CREATE TABLE Person
(
    pid CHAR(3),
    pname VARCHAR(50),
    income INTEGER,
    age INTEGER,
    PRIMARY KEY(pid)
);

INSERT INTO Person (pid, pname, income, age) VALUES ('aaa', 'Paolo', 30, 43);
INSERT INTO Person (pid, pname, income, age) VALUES ('aab', 'Francesca', 60, 5);
INSERT INTO Person (pid, pname, income, age) VALUES ('aba', 'Carla', 23, 60);
INSERT INTO Person (pid, pname, income, age) VALUES ('abb', 'Gesù Cristo', 0, 33);

------------------------------------------------------------


~~~
Name conventions:
~~~ sql
SELECT Person.pname, Person.income
FROM Person
WHERE Person.age < 35;

-- oppure se non ci sono ambiguità

SELECT pname, income
FROM Person
WHERE age < 35;

-- oppure se ci sono ambiguità ma vogliamo una soluzione più facile

SELECT p.pname, p.income
FROM Person /* as */ p
WHERE p.age < 35;
~~~

Per modificare il nome degli attributi in un risultato usiamo:
~~~ sql
SELECT p.pname as Person_Name, p.income, p.income as Person_Salary -- ripetere con label diversi
FROM Person p
WHERE p.age <35;
~~~

Possiamo anche usare delle espressioni nella selezione:
~~~ sql
SELECT income/2 as Salary
FROM Person
WHERE age > 10;
~~~

Oppure complicare gli argomenti della selezione:

~~~ sql
SELECT pid, pname, age
FROM Person
WHERE income > 20 AND income <40;

-- o anche 

SELECT pid, pname + ' Palla Destra'
FROM Person
WHERE 
~~~