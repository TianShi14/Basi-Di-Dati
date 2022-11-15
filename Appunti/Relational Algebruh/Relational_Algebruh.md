# <p align = "center"> Relational Algebra in and out of SQL </p>

###### <p align = "center"> Alcuni simboli non sono precisissimi perchè Markdown non è LaTeX e la liberia è limitata, si capisce comunque però quindi poche lagne </p>

#
# INDEX

- [Selection and Projection](#selection-and-projection)
- [Union, Difference and Intersection](#union-difference-and-intersection-relational-algebra-only)
- [Cartesian Products and Joins](#cartesian-products-and-joins)

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

#

<details>

<summary><h2> Additional Infos (SQL only) </h2> </summary>

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

Possiamo anche usare delle espressioni nella proiezione:
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

SELECT pid, pname, age + 420
FROM Person
WHERE income > 20 AND (age < 40 OR age > 3);
~~~

Generalizzare la soluzione di una selezione
~~~ sql
SELECT *
FROM Person
WHERE pname LIKE 'A_g%'; -- seleziona i nomi che hanno A come iniziale e g come terza lettera
~~~

</details>

#


# Union, Difference and intersection (Relational Algebra Only)

<details>

<summary><h2> Concepts </h2></summary>

Tutti e tre devono essere **union-compatible**, ovvero devono possedere lo stesso numero di colonne, e tutte le coppie di colonne "allineate" devono essere dello stesso tipo

Prenderemo in esempio due tabelle propedeutiche per comprendere i tre concetti: 

### S
|sid|sname|
|:---:|:---:|
|1|Arianna|
|2|Monica|
|3|Mariangelo|

### D
|did|dname|
|:---:|:---:|
|1|Arianna|
|4|Samaritano|
|3|Mariangelo|

### Unione 

$S \cup D$


|sid|sname|
|:---:|:---:|
|1|Arianna|
|2|Monica|
|3|Mariangelo|
|4|Samaritano|

### Intersezione

$S \cap D$

|sid|sname|
|:---:|:---:|
|1|Arianna|
|3|Mariangelo|

### DIfferenza 

$S - D$

|sid|sname|
|:---:|:---:|
|2|Monica|

</details>

#

# Cartesian Products and Joins

Prendiamo in esempio tre tabelle per meglio comprendere di che cazzo si parla!

### isMother

|mother|child|
|:---:|:---:|
Luisa|Maria
Luisa|Luigi
Anna|Olga
Anna|Filippo
Maria|Andrea
Maria|Aldo

### isFather

|father|child|
|:---:|:---:|
Sergio|Franco
Luigi|Olga
Luigi|Filippo
Franco|Andrea
Franco|Aldo

### Persona

|name|age|income|
|:---:|:---:|:---:|
Andrea|27|21
Aldo|25|15
Maria|55|42
Anna|50|35
Filippo|26|30
Luigi|50|40
Franco|60|20
Olga|30|41
Sergio|85|35
Luisa|75|87

<details>

<summary><h2> Cartesian Product and Renaming Operator </h2> </summary> 

Per facilità in questo esempio riduciamo a due tuple ciascuna le tabelle isMother e isFather dunque:

### isMother

|mother|child|
|:---:|:---:|
Luisa|Maria
Luisa|Luigi

### isFather

|father|child|
|:---:|:---:|
Sergio|Franco
Luigi|Olga

Banalmente, date le due tabelle, il **prodotto cartesiano** associa alle ad ogni riga della prima tabella, tutte le righe della seconda, e dunque uscirà che dato $ProdCart = isMother \times isFather$

### ProdCart

|mother|(child)|father|(child)|
|:---:|:---:|:---:|:---:|
|Luisa|Maria|Sergio|Franco
|Luisa|Maria|Luigi|Olga
|Luisa|Luigi|Sergio|Franco
|Luisa|Luigi|Luigi|Olga

In SQL:
~~~ sql

~~~

Per risolvere ambiguità come quella causata dalla doppia presenza di "child" usiamo il renaming operator. Dato $\rho( R (2 \to m\_ child, 4 \to f\_ child), isMother \times isFather)$ avremo:

### R

|mother|m_child|father|f_child|
|:---:|:---:|:---:|:---:|
|Luisa|Maria|Sergio|Franco
|Luisa|Maria|Luigi|Olga
|Luisa|Luigi|Sergio|Franco
|Luisa|Luigi|Luigi|Olga

In SQL:

~~~ sql

~~~

</details>

#

<details>

<summary><h2> Conditioned Join </h2> </summary> 

La **conditioned join** è simile al prodotto cartesiano, ma banalmente aggiunge un criterio per il quale ad una tupla ne debba essere collegata un'altra. Ad esempio, proviamo a fare 

$CondJoin = Persona ⋈_{p1.age < p2.age} Persona$

riscrivibile anche come 

$$\rho(CondJoin(1 \to p1.name, 2 \to p1.age, 3 \to p1.income, 4 \to p2.name, 5 \to p2.age, 6 \to p2.income), \sigma_{p1.age < p2.age}(\rho(p1, Persona) \times \rho(p2, Persona)))$$

nel caso in cui Persona sia composto solo dalle prime 4 tuple

### CondJoin

|p1.name|p1.age|p1.income|p2.name|p2.age|p2.income|
|:---:|:---:|:---:|:---:|:---:|:---:
Andrea|27|21|Anna|50|35
Andrea|27|21|Maria|55|42
Aldo|25|15|Andrea|27|21
Aldo|25|15|Anna|50|35
Aldo|25|15|Maria|55|42
Anna|50|35|Maria|55|42

In SQL:
~~~ sql

~~~

</details>

#

<details>

<summary><h2> Equi Join </h2> </summary> 

**L'equi join**, è null'altro se non una banale conditioned join in cui l'operatore di confronto controlla che i valori di un attributo di una tabella siano uguali ai valori di altri attributi di un'altra tabella.

Tornando a isFather e isMother come esempi, cerchiamo di definire un equi join definita come  

$EquiJoin = isMother ⋈_{isMother.child = isFather.father} isFather$  

oppure anche   
$$\rho(EquiJoin(2 \to m \_child, 4 \to f \_ child), \sigma_{isMother.child = isFather.father}(isMother \times isFather))$$

### EquiJoin

|mother|m_child|father|f_child|
|:---:|:---:|:---:|:---:|
|Luisa|Luigi|Luigi|Olga
|Luisa|Luigi|Luigi|Filippo

in SQL:

~~~ sql

~~~

</details>

#

<details>

<summary><h2> Natural Join </h2></summary>

Sebbene non sia usato molto, il **natural join** consente di svolgere un'operazione di equi join vitale. Dati sempre isMother e isFather definiamo una natural join come

$NatJoin = isMother ⋈ isFather$ 

che generalizziamo, dati X e Y rispettivamente la lista degli attributi di isMother e isFather

$$\rho(NatJoin, \pi_{X, Y-X}(\sigma_{isMother.isMother\cap isFather = isFather.isMother\cap isFather}(isMother \times isFather))) $$

o riassumendo

$$\rho(NatJoin, \pi_{X, Y-X}(isMother ⋈_{equi \_ on \_ isMother \cap isFather} isFather)) $$

### NatJoin

|mother|child|father|
|:---:|:---:|:---:|
|Anna|Olga|Luigi|
|Anna|Filippo|Luigi|
|Maria|Andrea|Franco|
|Maria|Aldo|Franco|

</details>

#

<details>

<summary><h2> Division </h2></summary>

La divisione non è supportata naturalmente nel linguaggio SQL, eppure dà un risultato parecchio fregno che vediamo ora

Per convenienza ci definiamo due nuove tabelle fresche fresche:

### Students

| name | uni_courses | 
|:---:|:---:|
| Paolo | Pisolino Avanzato | 
| Paolo | Punteggiatura 2 | 
| Lucario | 4 Stagioni | 
| Lucario | Pisolino Avanzato | 
| Minestra | Astrofisica Applicata | 
| Minestra | Morbin Time | 
| Minestra | Somma tra Tre Numeri | 

### AdvancedCourses

uni_courses|
:---:
Pisolino Avanzato
4 Stagioni

E definiamo dunque $Div = Students \div AdvancedCourses$

oppure visto come

$$ \rho (Div, \pi_{X - Y}(Students) - \pi_{X-Y}((\pi_{X-Y}(Students) \times AdvancedCourses) - Students)$$

### Div

name|
:---:
Lucario

</details>

#