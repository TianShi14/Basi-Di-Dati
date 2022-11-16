~~~ sql
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