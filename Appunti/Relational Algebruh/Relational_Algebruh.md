# <center> Relational Algebra </center>

###### <center> Alcuni simboli non sono precisissimi perchè Markdown non è LaTeX e la liberia è limitata, si capisce comunque però quindi poche lagne

#
# INDEX

- [Selection and Projection](#selection-and-projection)
- []()
- []()

#

## Selection and Projection

Pochi cazzi, dritti al punto con degli esempi facili facili che si capiscono:

Dato T1:

### T1

| **sid** | **sname** | **age** | **rating** |
| :---: | :---: | :---: | :---: |
|2832|Platone|63|13.5|
|1111|Martina dell'Ombra|13|69.69|
|1453|Beppe Fenoglio|63|7.2|
|3453|Carmelo|35|10.7|

#

<details>

<summary><h2> Click Me </h2> </summary>

L'operazione di selezione *Sig1* = $\sigma_{age > 42}$(*T1*) darà:

### Sig1

| **sid** | **sname** | **age** | **rating** |
| :---: | :---: | :---: | :---: |
|2832|Platone|63|13.5|
|1453|Beppe Fenoglio|63|7.2|

</details>

#

L'operazione di proiezione *Pro1* = $\pi_{age}$(*T1*) invece darà:

### Pro1

| **age** | 
:---:
63
13
35
#### Da notare banalmente che la proiezione elimina i duplicati

#

E infine un'operazione tipo *SP* = $\pi_{sname,age}(\sigma_{rating>9}(T1))$ darà:

### SP

 **sname** | **age** | 
| :---: | :---: | 
|Platone|63|
|Martina dell'Ombra|13|
|Carmelo|35|