~~~sql
INSERT INTO Directors VALUES ('Nolan', 1970);
INSERT INTO Directors VALUES ('Spielberg', 1946);
INSERT INTO Directors VALUES ('Tarantino', 1963);
INSERT INTO Directors VALUES ('Goldberg', 1982);
INSERT INTO Directors VALUES ('Zeitlin', 1982);

---- Nolan 

INSERT INTO Movies VALUES ('Tenet', 2020, 'Nolan', 205,365);
INSERT INTO Movies VALUES ('Inception', 2010,'Nolan', 160, 836);
INSERT INTO Movies VALUES ('Interstellar', 2014, 'Nolan', 165, 773);
INSERT INTO Movies VALUES ('The Prestige', 2006, 'Nolan', 40, 109);
INSERT INTO Movies VALUES ('Dunkirk', 2017, 'Nolan', 100, 527);

-- Spielberg

INSERT INTO Movies VALUES ('ET', 1982, 'Spielberg', 10, 792);
INSERT INTO Movies VALUES ('Saving Private Ryan', 1998, 'Spielberg', 70, 482);
INSERT INTO Movies VALUES ('Schlinder List', 1993, 'Spielberg', 22, 322);
INSERT INTO Movies VALUES ('Raiders of the Lost Ark', 1981, 'Spielberg', 18, 389);
INSERT INTO Movies VALUES ('Men in Black', 1997, 'Spielberg', 90, 589);

-- Tarantino

INSERT INTO Movies VALUES ('Pulp Fiction', 1994 , 'Tarantino', 8, 213);
INSERT INTO Movies VALUES ('Le Iene', 1992 , 'Tarantino', 1, 3 );
INSERT INTO Movies VALUES ('The Hateful Eight', 2015, 'Tarantino', 44, 156);
INSERT INTO Movies VALUES ('Dal Tramonto All Alba', 1996 , 'Tarantino', 19, 25);
INSERT INTO Movies VALUES ('C era Una Volta… a Hollywood', 2019, 'Tarantino', 90, 377);

-- Goldberg

INSERT INTO Movies VALUES ('Sausage Party', 2016, 'Goldberg', 19, 140);
INSERT INTO Movies VALUES ('Goon', 2011, 'Goldberg', 1, 7);
INSERT INTO Movies VALUES ('This is The End', 2013, 'Goldberg', 32, 126);
INSERT INTO Movies VALUES ('Suxbad', 2007, 'Goldberg', 20, 170);
INSERT INTO Movies VALUES ('Game Over, Man!', 2018, 'Goldberg', 27, 28);

-- Zeitlin

-- No.

INSERT INTO movieawards VALUES('Tenet', 2020, 'Golden Angel Award, Most Popular ', 'won');
INSERT INTO movieawards VALUES('Tenet', 2020, 'Hochi Film Award, Best Foreign Language Film ', 'won');
INSERT INTO movieawards VALUES('Tenet', 2020, 'Saturn Award, Best Director', 'nominated');



INSERT INTO movieawards VALUES('Inception', 2010, 'Gold Derby Film Award, Original Screenplay', 'won');
INSERT INTO movieawards VALUES('Inception', 2010, 'Golden Schmoes, Best Director of the Year', 'won');
INSERT INTO movieawards VALUES('Inception', 2010, 'Hollywood Film Awards, Hollywood Movie of the Year', 'won');

INSERT INTO movieawards VALUES('Interstellar', 2014, 'Saturn Award, best writing', 'won');
INSERT INTO movieawards VALUES('Interstellar', 2014, 'Saturn Award, best director', 'nominated');
INSERT INTO movieawards VALUES('Interstellar', 2014, 'Amanda, best foreign', 'nominated');

INSERT INTO movieawards VALUES('The Prestige', 2006, 'Empire Award, Best Director', 'won');
INSERT INTO movieawards VALUES('The Prestige', 2006, 'ACCA, Best Adapted Screenplay', 'nominated');
INSERT INTO movieawards VALUES('The Prestige', 2006, 'IOMA, Best Adapted Screenplay', 'won');

INSERT INTO movieawards VALUES('Dunkirk', 2017, 'Amanda, best foreign', 'nominated');
INSERT INTO movieawards VALUES('Dunkirk', 2017, 'Eda, best director', 'nominated');
INSERT INTO movieawards VALUES('Dunkirk', 2017, 'afcc, best director', 'won');

INSERT INTO movieawards VALUES('ET', 1982, 'Amanda, best foreign', 'nominated');
INSERT INTO movieawards VALUES('ET', 1982, 'Eda, best director', 'nominated');
INSERT INTO movieawards VALUES('ET', 1982, 'afcc, best director', 'won');

INSERT INTO movieawards VALUES('Schlinder list', 1993, 'Amanda, best foreign', 'nominated');
INSERT INTO movieawards VALUES('Schlinder list', 1993, 'Eda, best director', 'nominated');
INSERT INTO movieawards VALUES('Schlinder list', 1993, 'Aa, best foreign', 'won');


INSERT INTO movieawards VALUES('Saving Private Ryan', 1998, 'Aa, best foreign', 'won');
INSERT INTO movieawards VALUES('Saving Private Ryan', 1998, 'Aa, best culo', 'won');
INSERT INTO movieawards VALUES('Saving Private Ryan', 1998, 'Soldato salvato, best giacomo', 'won');

INSERT INTO movieawards VALUES('Men in Black', 1997, 'Soldato salvato, best giacomo', 'won');
INSERT INTO movieawards VALUES('Raiders of the Lost Ark', 1981, 'amanda, best giacomo', 'won');
INSERT INTO movieawards VALUES('Le Iene', 1992, 'Soldato salvato, best giacomo', 'nominated');
INSERT INTO movieawards VALUES('Pulp Fiction', 1994, 'salvato, best luca', 'nominated');
INSERT INTO movieawards VALUES('Dal Tramonto All Alba', 1996, 'amanda, best tramonto', 'won');
INSERT INTO movieawards VALUES('C era Una Volta… a Hollywood',2019 , 'collina dorata, best giacomo', 'won');
INSERT INTO movieawards VALUES('The Hateful Eight', 2015, 'ciao, best odiatore', 'nominated');
INSERT INTO movieawards VALUES('Sausage Party', 2016, 'salsiccia, best party', 'won');
INSERT INTO movieawards VALUES('This is The End', 2013, 'morte, best finale', 'won');
INSERT INTO movieawards VALUES('Suxbad',2007, 'Sold, best bad', 'nominated');
INSERT INTO movieawards VALUES('Goon',2011, 'amanda, best goon', 'won');
INSERT INTO movieawards VALUES('Game Over, Man!', 2018, 'morte, best finale', 'nominated');



INSERT INTO directorawards VALUES('Spielberg', 1982, 'Amanda', 'nominated');
INSERT INTO directorawards VALUES('Spielberg', 1982, 'Eda', 'nominated');
INSERT INTO directorawards VALUES('Spielberg', 1982, 'afcc', 'won');

INSERT INTO directorawards VALUES('Spielberg', 1993, 'Amanda', 'nominated');
INSERT INTO directorawards VALUES('Spielberg', 1993, 'Eda', 'nominated');
INSERT INTO directorawards VALUES('Spielberg', 1993, 'Aa', 'won');


INSERT INTO directorawards VALUES('Spielberg', 1998, 'Aa', 'won');
INSERT INTO directorawards VALUES('Spielberg', 1998, 'Aa', 'won');
INSERT INTO directorawards VALUES('Spielberg', 1998, 'Soldato salvato', 'won');

INSERT INTO directorawards VALUES('Spielberg', 1997, 'Soldato salvato', 'won');
INSERT INTO directorawards VALUES('Spielberg', 1981, 'amanda', 'won');



----------------------------------------------------------------

INSERT INTO directorawards VALUES('Nolan', 2020, 'Golden Angel Award ', 'won');
INSERT INTO directorawards VALUES('Nolan', 2020, 'Hochi Film Award', 'won');
INSERT INTO directorawards VALUES('Nolan', 2020, 'Saturn Award', 'nominated');



INSERT INTO directorawards VALUES('Nolan', 2010, 'Gold Derby Film Award', 'won');
INSERT INTO directorawards VALUES('Nolan', 2010, 'Golden Schmoes', 'won');
INSERT INTO directorawards VALUES('Nolan', 2010, 'Hollywood Film Awards', 'won');

INSERT INTO directorawards VALUES('Nolan', 2014, 'Saturn Award', 'won');
INSERT INTO directorawards VALUES('Nolan', 2014, 'Saturn Award', 'nominated');
INSERT INTO directorawards VALUES('Nolan', 2014, 'Amanda', 'nominated');

INSERT INTO directorawards VALUES('Nolan', 2006, 'Empire Award', 'won');
INSERT INTO directorawards VALUES('Nolan', 2006, 'ACCA', 'nominated');
INSERT INTO directorawards VALUES('Nolan', 2006, 'IOMA', 'won');

INSERT INTO directorawards VALUES('Nolan', 2017, 'Amanda', 'nominated');
INSERT INTO directorawards VALUES('Nolan', 2017, 'Eda', 'nominated');
INSERT INTO directorawards VALUES('Nolan', 2017, 'afcc', 'won');

------------------------------------------------------------------------

INSERT INTO directorawards VALUES('Tarantino', 1992, 'Soldato salvato', 'nominated');
INSERT INTO directorawards VALUES('Tarantino', 1994, 'salvato', 'nominated');
INSERT INTO directorawards VALUES('Tarantino', 1996, 'amanda', 'won');
INSERT INTO directorawards VALUES('Tarantino', 2019 , 'collina dorata', 'won');
INSERT INTO directorawards VALUES('Tarantino', 2015, 'ciao', 'nominated');

----------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO directorawards VALUES('Goldberg', 2016, 'salsiccia', 'won');
INSERT INTO directorawards VALUES('Goldberg', 2013, 'morte', 'won');
INSERT INTO directorawards VALUES('Goldberg',2007, 'Sold', 'nominated');
INSERT INTO directorawards VALUES('Goldberg',2011, 'amanda', 'won');
INSERT INTO directorawards VALUES('Goldberg', 2018, 'morte', 'nominated');

~~~