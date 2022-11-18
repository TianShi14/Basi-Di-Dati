# <p align = "center"> Relational Algebra in and out of SQL </p>

###### <p align = "center"> Alcuni simboli non sono precisissimi perchè Markdown non è LaTeX e la liberia è limitata, si capisce comunque però quindi poche lagne </p>

#
# INDEX

- [Selection and Projection](#selection-and-projection)
- [Cartesian Products and Joins](#cartesian-products-and-joins)
- [Union, Difference and Intersection](#union-difference-and-intersection)
- [Grouping and Aggregate Operators](#grouping-and-aggregate-operators)

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

Con schema SQL

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

Con schema SQL:
~~~sql
CREATE TABLE isMother(
  mother VARCHAR(20),
  child VARCHAR(20)
);

CREATE TABLE isFather(
  father VARCHAR(20),
  child VARCHAR(20)
);

CREATE TABLE Persona(
  name VARCHAR(20),
  age INTEGER, 
  income INTEGER,
  PRIMARY KEY(name)
);

INSERT INTO isMother VALUES ('Luisa', 'Maria');
INSERT INTO isMother VALUES ('Luisa', 'Luigi');
INSERT INTO isMother VALUES ('Anna', 'Olga');
INSERT INTO isMother VALUES ('Anna', 'Filippo');
INSERT INTO isMother VALUES ('Maria', 'Andrea');
INSERT INTO isMother VALUES ('Maria', 'Aldo');

INSERT INTO isFather VALUES ('Sergio', 'Franco');
INSERT INTO isFather VALUES ('Luigi', 'Olga');
INSERT INTO isFather VALUES ('Luigi', 'Filippo');
INSERT INTO isFather VALUES ('Franco', 'Andrea');
INSERT INTO isFather VALUES ('Franco', 'Aldo');

INSERT INTO Persona VALUES ('Andrea', 27, 21);
INSERT INTO Persona VALUES ('Aldo', 25, 15);
INSERT INTO Persona VALUES ('Maria', 55, 42);
INSERT INTO Persona VALUES ('Anna', 50, 35);
INSERT INTO Persona VALUES ('Filippo', 26, 30);
INSERT INTO Persona VALUES ('Luigi', 50, 40);
INSERT INTO Persona VALUES ('Franco', 60, 20);
INSERT INTO Persona VALUES ('Olga', 30, 41);
INSERT INTO Persona VALUES ('Sergio', 85, 35);
INSERT INTO Persona VALUES ('Luisa', 75, 87);
~~~
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
SELECT *
FROM isMother, isFather;
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
SELECT mother, isMother.child as m_child, father, isFather.child as f_child
FROM isMother, isFather;
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
SELECT *
FROM Persona p1, Persona p2
WHERE p1.age < p2.age;

-- oppure, con la notazione che si userà da ora in avanti

SELECT *
FROM Persona p1 join Persona p2
ON p1.age < p2.age;
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
SELECT mother, isMother.child as m_child, father, isFather.child as f_child
FROM isMother join isFather ON isMother.child = isFather.father; 
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

in SQL:

~~~ sql
-- visto che non c'è modo per fare l'intersezione e la differenza in SQL, faremo uso del sapere quale sia l'intersezione e la differenza tra le due tabelle

SELECT mother, isMother.child /*o isFather.child, lasciare solo child causa ambiguità*/, father
FROM isMother join isFather 
ON isMother.child = isFather.child;

-- o ancor meglio

SELECT *
FROM isMother natural join isFather;
~~~
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

Non mi va di fare l'SQL ci penso n'altra volta amo
</details>

#

<details>

<summary><h2> Outher (other / outer eheheh) Joins in SQL </h2> </summary>

La **left outer join** dell'esempio ci dà ogni coppia padre / figlio disponibile e solo se esiste ci dà la madre

in SQL:
~~~ sql
SELECT *
FROM isFather left outer join isMother
ON isFather.child = isMother.child;
~~~

sputa fuori:


| father | child   | mother | child   |
| :----: | :-----: | :----: | :-----: |
| Franco | Aldo    | Maria  | Aldo    |
| Franco | Andrea  | Maria  | Andrea  |
| Luigi  | Filippo | Anna   | Filippo |
| Sergio | Franco  |        |         |
| Luigi  | Olga    | Anna   | Olga    |

La **right outer join** invece restituisce ogni madre conosciuta anche senza conoscere il padre

in SQL:
~~~ sql
SELECT *
FROM isFather right outer join isMother
ON isFather.child = isMother.child;
~~~

sputa fuori:

| father | child   | mother | child   |
| :----: | :-----: | :----: | :-----: |
| Franco | Aldo    | Maria  | Aldo    |
| Franco | Andrea  | Maria  | Andrea  |
| Luigi  | Filippo | Anna   | Filippo |
|        |         | Luisa  | Luigi   |
|        |         | Luisa  | Maria   |
| Luigi  | Olga    | Anna   | Olga    |

Le **full outer join** invece restituiscono sia ogni padre che ogni madre conosciuti indipendenetemente dalla conoscenza del padre o della madre

| father | child   | mother | child   |
| ------ | ------- | ------ | ------- |
| Franco | Aldo    | Maria  | Aldo    |
| Franco | Andrea  | Maria  | Andrea  |
| Luigi  | Filippo | Anna   | Filippo |
| Sergio | Franco  |        |         |
|        |         | Luisa  | Luigi   |
|        |         | Luisa  | Maria   |
| Luigi  | Olga    | Anna   | Olga    |

</details>

#

# Union, Difference and intersection

Prenderemo in esempio le tabelle precedenti ovvero:
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

Con schema SQL:
~~~sql
CREATE TABLE isMother(
  mother VARCHAR(20),
  child VARCHAR(20)
);

CREATE TABLE isFather(
  father VARCHAR(20),
  child VARCHAR(20)
);

CREATE TABLE Persona(
  name VARCHAR(20),
  age INTEGER, 
  income INTEGER,
  PRIMARY KEY(name)
);

INSERT INTO isMother VALUES ('Luisa', 'Maria');
INSERT INTO isMother VALUES ('Luisa', 'Luigi');
INSERT INTO isMother VALUES ('Anna', 'Olga');
INSERT INTO isMother VALUES ('Anna', 'Filippo');
INSERT INTO isMother VALUES ('Maria', 'Andrea');
INSERT INTO isMother VALUES ('Maria', 'Aldo');

INSERT INTO isFather VALUES ('Sergio', 'Franco');
INSERT INTO isFather VALUES ('Luigi', 'Olga');
INSERT INTO isFather VALUES ('Luigi', 'Filippo');
INSERT INTO isFather VALUES ('Franco', 'Andrea');
INSERT INTO isFather VALUES ('Franco', 'Aldo');

INSERT INTO Persona VALUES ('Andrea', 27, 21);
INSERT INTO Persona VALUES ('Aldo', 25, 15);
INSERT INTO Persona VALUES ('Maria', 55, 42);
INSERT INTO Persona VALUES ('Anna', 50, 35);
INSERT INTO Persona VALUES ('Filippo', 26, 30);
INSERT INTO Persona VALUES ('Luigi', 50, 40);
INSERT INTO Persona VALUES ('Franco', 60, 20);
INSERT INTO Persona VALUES ('Olga', 30, 41);
INSERT INTO Persona VALUES ('Sergio', 85, 35);
INSERT INTO Persona VALUES ('Luisa', 75, 87);
~~~

Tutti e tre devono essere **union-compatible**, ovvero devono possedere lo stesso numero di colonne, e tutte le coppie di colonne "allineate" devono essere dello stesso tipo

<details>

<summary><h2> Union </h2></summary>  

$S \cup D$

Come notiamo, inoltre, gli attributi prendono il nome della prima SELECT

| father | child   |
| :----: | :-----: |
| Luigi  | Filippo |
| Sergio | Franco  |
| Anna   | Olga    |
| Franco | Aldo    |
| Franco | Andrea  |
| Anna   | Filippo |
| Luigi  | Olga    |
| Luisa  | Maria   |
| Luisa  | Luigi   |
| Maria  | Andrea  |
| Maria  | Aldo    |

in SQL:
~~~sql
SELECT *
FROM isFather
union -- all, con all le tuple duplicate vengono tenute
SELECT *
FROM isMother;
~~~

Oppure per togliere ambiguità possiamo fare:
~~~sql
SELECT father as parent, child
FROM isFather
union
SELECT *
FROM isMother;
~~~
E' **IMPORTANTE** la posizione degli attributi nella SELECT, sennò l'ordine delle tuple è f o t t u t o

</details>

#

<details>

<summary><h2> Intersection </h2></summary>

$S \cap D$

| child   |
| :-----: |
| Olga    |
| Filippo |
| Andrea  |
| Aldo    |

in SQL:
~~~ sql
SELECT distinct mo.child
FROM isMother mo join isFather fa 
ON mo.child = fa.child;

-- o anche

SELECT distinct mo.child
FROM isMother mo, isFather fa 
WHERE mo.child = fa.child;
~~~

Oppure col termine specifico presente nativamente in SQL:
~~~ sql
SELECT child
FROM isFather
intersect --all, con all le tuple duplicate vengono tenute, corrisponde con l'eliminazione del distinct dell'esempio precedente
SELECT child
FROM isMother;
~~~
</details>

#

<details>

<summary><h2> Difference </h2></summary> 

$S - D$
| child  |
| :----: |
| Franco |

in SQL:
~~~ sql
SELECT child
FROM isFather
except --all, con all tutte le tuple duplicate vengono tenute
SELECT child
FROM isMother;
~~~

</details> 

#

# Grouping and Aggregate Operators

Riprendiamo in esempio le tabelle:

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

Con schema SQL:
~~~sql
CREATE TABLE isMother(
  mother VARCHAR(20),
  child VARCHAR(20)
);

CREATE TABLE isFather(
  father VARCHAR(20),
  child VARCHAR(20)
);

CREATE TABLE Persona(
  name VARCHAR(20),
  age INTEGER, 
  income INTEGER,
  PRIMARY KEY(name)
);

INSERT INTO isMother VALUES ('Luisa', 'Maria');
INSERT INTO isMother VALUES ('Luisa', 'Luigi');
INSERT INTO isMother VALUES ('Anna', 'Olga');
INSERT INTO isMother VALUES ('Anna', 'Filippo');
INSERT INTO isMother VALUES ('Maria', 'Andrea');
INSERT INTO isMother VALUES ('Maria', 'Aldo');

INSERT INTO isFather VALUES ('Sergio', 'Franco');
INSERT INTO isFather VALUES ('Luigi', 'Olga');
INSERT INTO isFather VALUES ('Luigi', 'Filippo');
INSERT INTO isFather VALUES ('Franco', 'Andrea');
INSERT INTO isFather VALUES ('Franco', 'Aldo');

INSERT INTO Persona VALUES ('Andrea', 27, 21);
INSERT INTO Persona VALUES ('Aldo', 25, 15);
INSERT INTO Persona VALUES ('Maria', 55, 42);
INSERT INTO Persona VALUES ('Anna', 50, 35);
INSERT INTO Persona VALUES ('Filippo', 26, 30);
INSERT INTO Persona VALUES ('Luigi', 50, 40);
INSERT INTO Persona VALUES ('Franco', 60, 20);
INSERT INTO Persona VALUES ('Olga', 30, 41);
INSERT INTO Persona VALUES ('Sergio', 85, 35);
INSERT INTO Persona VALUES ('Luisa', 75, 87);
~~~

<details>
<summary><h2> Ordering and Limits </h2></summary>

Il primo operatore è quello di "**order by**", il quale ordina il nostro risultato sulla base di un attributo scelto ( se CHAR in ordine alfabetico se numerico in ordine numerico ), e ha sia una proprietà ascendente (default) e una discendente (**order by "attributo" desc**). Prendendo la tabella Persona:
~~~sql
SELECT *
FROM Persona
WHERE age > 45
order by income;
~~~
Darà in output:

| name   | age | income |
| :----: | :-: | :----: |
| Franco | 60  | 20     |
| Anna   | 50  | 35     |
| Sergio | 85  | 35     |
| Luigi  | 50  | 40     |
| Maria  | 55  | 42     |
| Luisa  | 75  | 87     |

Si può notare come le tuple siano ordinate in ordine ascendente numerico sulla base della retribuzione (**income**).

Immaginiamo di voler visualizzare, similmente all'esempio precedente, le persone sopra ai 45 anni con l'income più alto, ma in questo caso volessimo limitarci a vedere la top 3 dei capitalisti merdosi, allora faremmo:
~~~sql
SELECT *
FROM Persona
WHERE age > 45
order by income desc
limit 3;
~~~

| name  | age | income |
| :---: | :-: | :----: |
| Luisa | 75  | 87     |
| Maria | 55  | 42     |
| Luigi | 50  | 40     |

</details>

#

<details>

<summary> <h2> Aggregate Operators </h2> </summary>

Count: 
- count (*) -> conta il numero di tuple 
  
  Prendiamo com eesempio isMother e cerchiamo quanti figli ha Anna:
  ~~~ sql
  SELECT * 
  FROM isMother
  WHERE mother = 'Anna';
  ~~~
  Con questa notazione vediamo tutte le tuple in cui appare Anna, tuple che possiamo contare manualmente per sapere in quante istanze Anna appare:
  
  | mother | child   |
  | :----: | :-----: |
  | Anna   | Olga    |
  | Anna   | Filippo |

  Però, in grandi database che non restituiscono tuple così ristrette, qualora ci interessi effettivamente solo il numero di istanze presenti senza sapere a che valori Anna è associata ci basta fare:
  ~~~sql
  SELECT count (*) as numberOf_annaChildren
  FROM isMother
  WHERE mother = 'Anna';
  ~~~
  che dà invece:

  |numberOf_annaChildren|
  | :---: |
  | 2 |
- count (attributo) o count (*distinct* attributo)
  ipotizziamo di aggiungere una tupla in Persona:
  ~~~sql
  INSERT INTO Persona VALUES ('Il Gabibbo');
  ~~~
  creando dunque la tupla:
 
  | name       | age | income |
  | :--------: | :-: | :----: |
  | Il Gabibbo |   null  |    mull    |
  
  Qualora decidessimo di usare "count(*)" per contare il numero di tuple, quest'ultima verrebbe contata come una tupla normalissima (anche se tutti e tre i valori fossero 'null', non possibile nell'esempio perchè abbiamo il NOT NULL constraint ma si capisce dai che cazzo).
  
  Invece, decidessimo di usare "count(income)" ad esempio, riceveremmo un risultato diverso:
  ~~~sql
  SELECT count(income) as numberOf_stipendi
  FROM Persona
  ~~~
  che da:
  | numberof_stipendi |
  | :---------------: |
  | 10                |
  
  Mentre una notazione come:
  ~~~sql
  SELECT count(distinct income) as numberOf_stipendi_diversi
  FROM Persona
  ~~~
  darebbe in output:
  
  | numberof_stipendi_diversi |
  | :---------------: |
  | 9                 |
  
  Poichè Anna e Sergio hanno lo stesso income di 35, e vengono contati solo una volta.

#

Altri operatori:
- **avg, sum, min, max** -> tutti consentono solo di inserire come parametro un attributo specifico:
  - **avg, sum** funzionano con qualsiasi valore numerico, che sia anche una data (DATE) o un orario (TIME)
  - **min, max** funzionano con qualsiasi valore ordinabile (che funziona con l'order by)

  Tutti questi operatori ignorano i valori null e vanno usati nella SELECT.

#

Una cosa alla quale si deve fare particolare attenzione è che abbiano senso i valori che si decide di selezionare / proiettare (SELECT). In linea generale possiamo affermare che nella SELECT non vadano mai associati un attributo e un operatore di aggregazione:

~~~sql
SELECT name, max(age)
FROM Persona; 
~~~ 
In questo esempio, si sta cercando di mostrare una tabella in cui vengono mostrati i nomi di tutte le persone, associate ad una tabella in cui esiste solo un valore che coincide all'età massima presente in Persona, per questo esce errore.

Volessimo invece trovare il nome e l'età della persona più vecchia dovremmo fare un'implementazione simile (questa l'ho fatta io da solo a mano, sono un Dio):
~~~ sql
SELECT name, age
FROM Persona
WHERE age = (
  SELECT max(age)
  FROM Persona
)
~~~
Il che restituirebbe:
| name   | age |
| :----: | :-: |
| Sergio | 85  |

In alcuni casi noi vogliamo che, invece di applicare la query all'intera tabella, essa venga applicata in partizioni di tali. Ad esempio, se a differenza degli esempi precedenti noi non desiderassimo contare il numero di figli presenti in isMother, o il numero di figli di una sola madre, ma invece volessimo trovare il numero di figli per ogni madre presente nella relazione, dovremmo usare la clausola "**group by**":

~~~ sql
SELECT mother, count(child) as numberOf_hellsentCreatures
FROM isMother
GROUP BY mother;
~~~

che darebbe: 

| mother | numberof_hellsentcreatures |
| :----: | :------------------------: |
| Anna   | 2                          |
| Maria  | 2                          |
| Luisa  | 2                          |

Ovviamente, non avrebbe senso ad esempio raggruppare un attributo e poi proiettarne anche un altro di cui sicuramente avrà più valori, per questo di solito nelle proiezioni delle group by solitamente si va solamente ad includere l'attributo raggruppato e eventuali operatori di aggregazione (solitamente il DBMS non lacia errori però associa casualmente uno dei valori presenti nell'attributo non raggrupato e non ha senso!).

La clausola "having" si usa assieme alle group by e corrisponde alla clausola WHERE ma per ogni gruppo, easy.

</details>

# Conclusione

Viste tutte le nozioni possibili, ci resta solo riassumere tutte la possibili clausole che possiamo inserire e in che ordine logico ci conviene usarle.

~~~sql
SELECT "attributi o aggregazioni" -- 7
FROM "tabella" -- 1
WHERE "condizioni" -- 2
GROUP BY "attributo" -- 3
HAVING "condizioni" -- 4
ORDER BY "attributo" -- 5
LIMIT "numero" -- 6
~~~