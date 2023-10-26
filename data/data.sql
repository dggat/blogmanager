insert into Nutzer VALUES('mustermann1@mail.com', 'pasAa1', 'm', 'mustermam1', '2000-11-11');
insert into Nutzer VALUES('mustermann2@mail.com', 'pasAa2', 'm', 'mustermam2', '2000-10-11');
insert into Nutzer VALUES('mustermann3@mail.com', 'pasAa3', 'm', 'mustermam2', '2000-09-11');
insert into Nutzer VALUES('mustermann4@mail.com', 'pasAa4', 'm', 'mustermam2', '2000-08-11');
insert into Nutzer VALUES('mustermann5@mail.com', 'pasAa2', 'm', 'mustermam2', '2000-07-11');
insert into Nutzer VALUES('mustermann6@mail.com', 'pasAa6', 'm', 'mustermam2', '2000-06-11');
insert into Nutzer VALUES('mustermann7@mail.com', 'pasAa7', 'm', 'mustermam2', '2000-05-11');
insert into Nutzer VALUES('mustermann8@mail.com', 'pasAa8', 'm', 'mustermam2', '2000-04-11');
insert into Nutzer VALUES('mustermann9@mail.com', 'pasAa9', 'm', 'mustermam2', '2000-03-11');
insert into Nutzer VALUES('musterFrau1@mail.com', 'pasAa1', 'w', 'musteFrau1', '2000-03-11');
insert into Nutzer VALUES('musterFrau2@mail.com', 'pasAa2', 'w', 'musteFrau2', '2000-03-11');
insert into Nutzer VALUES('musterFrau3@mail.com', 'pasAa3', 'w', 'musteFrau3', '2000-03-11');
insert into Nutzer VALUES('musterFrau4@mail.com', 'pasAa4', 'w', 'musteFrau4', '2000-03-11');

--Redakteure
insert into Nutzer VALUES('RedakteurMustermann1@mail.com', 'RedpasAa1', 'm', 'RedakMustermam1', '2000-11-12');
insert into Nutzer VALUES('RedakteurMustermann2@mail.com', 'RedpasAa2', 'm', 'RedakMustermam2', '2000-11-13');
insert into Nutzer VALUES('RedakteurMustermann3@mail.com', 'RedpasAa3', 'm', 'RedakMustermam2', '2000-11-13');
insert into Nutzer VALUES('RedakteurinMusterFrau1@mail.com', 'RedpasAa1', 'w', 'RedakinMusteFrau1', '2000-11-13');
insert into Nutzer VALUES('RedakteurinMusterFrau2@mail.com', 'RedpasAa2', 'w', 'RedakinMusteFrau2', '2000-11-13');
insert into Nutzer VALUES('RedakteurinMusterFrau3@mail.com', 'RedpasAa3', 'w', 'RedakinMusteFrau3', '2000-11-13');

--Chefs
insert into Nutzer VALUES('ChefredakteurMustermann1@mail.com', 'ChefRpas1', 'm', 'Chefredakmustermam1', '2000-11-13');
insert into Nutzer VALUES('ChefredakteurMustermann2@mail.com', 'ChefRpas2', 'm', 'Chefredakmustermam2', '2000-11-15');
insert into Nutzer VALUES('ChefredakteurMustermann3@mail.com', 'ChefRpas3', 'm', 'Chefredakmustermam2', '2000-11-15');


-- Redakteure:
insert into Redakteur VALUES('RedakteurMustermann1@mail.com', 'Max', 'Musterman', '');
insert into Redakteur VALUES('RedakteurMustermann2@mail.com', 'Dax', ' Musterman',' ');
insert into Redakteur VALUES('RedakteurMustermann3@mail.com', 'Frax', 'Musterman',' ');
insert into Redakteur VALUES('RedakteurinMusterFrau1@mail.com', 'Barbara', 'Liskov',' ');
insert into Redakteur VALUES('RedakteurinMusterFrau2@mail.com', 'Ada', 'Lovelace',' ');
insert into Redakteur VALUES('RedakteurinMusterFrau3@mail.com', 'Marie', 'Sklodowska Curie',' ');
insert into Redakteur VALUES('ChefredakteurMustermann1@mail.com', 'ChefR', 'Musterman', 'Chefbiographie 1');
insert into Redakteur VALUES('ChefredakteurMustermann2@mail.com', 'CHefR', 'der zweite Musterman', 'Kurze Biographie');
insert into Redakteur VALUES('ChefredakteurMustermann3@mail.com', 'Chef3', 'egal', '');

--Chefredakteure:
insert into Chefredakteur VALUES('ChefredakteurMustermann1@mail.com', '0049 211 121211122');
insert into Chefredakteur VALUES('ChefredakteurMustermann2@mail.com', '211 121211123');
insert into Chefredakteur VALUES('ChefredakteurMustermann3@mail.com', '23456789035');

--Blogeintraege:
INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail) VALUES('Wie schreibe ich den perfekten Blogbeitrag?','Text tum Blogeintrag 1', '2020-11-11 10:10:11', '2020-11-11 10:10:11', 'RedakteurMustermann1@mail.com');
INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, RedakteurEMail) VALUES('Wie schreibe ich den perfekten Blogbeitrag?', 'Um einen wirklich guten Blogbeitrag schreiben zu können...', '2020-11-11 10:10:10', 'RedakteurMustermann1@mail.com');
INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail) VALUES('Schritt 1 – Setze dir Ziele für deinen Blogartikel', 'Noch vor dem Schreiben des Blogbeitrags solltest du dir deine Zielgruppe genauer ansehen.', '2020-11-11 10:10:11', '2020-11-11 10:10:11', 'RedakteurMustermann2@mail.com');
INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail) VALUES('Schritt 2 – Lerne deine Zielgruppe kennen', 'Bevor du mit dem Schreiben loslegst, ist es wichtig, dir mit deinem Beitrag ein konkretes Ziel zu setzen. Was willst du mit dem Blogbeitrag schreiben erreichen?', '2020-11-11 10:10:12', '2020-11-11 10:10:12', 'RedakteurMustermann3@mail.com');
INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail) VALUES('Schritt 3 – Finde das richtige Thema', 'Wenn du selbst einen Blog betreibst, kennst du vermutlich die Situation...', '2020-11-11 10:10:11', '2022-11-11 10:10:10', 'RedakteurMustermann2@mail.com');
INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail) VALUES('Schritt 4 – Entdecke deinen eigenen Stil', 'Verpasse deinen Blogbeiträgen eine persönliche Note. Denn dein Schreibstil ist das, was deine Artikel', '2020-11-11 10:10:11', '2022-11-11 10:10:10', 'RedakteurMustermann2@mail.com');
INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail) VALUES('Schritt 5 – Die Balance zwischen Lesererwartung und SEO', 'Deinen Blogbeitrag schreibst du in erster Linie für deine Leser, nicht für Suchmaschinen.', '2020-11-11 10:10:11', '2022-11-11 10:10:10', 'RedakteurMustermann2@mail.com');
INSERT INTO Blogeintrag(Titel, Text, Erstellungsdatum, Aenderungsdatum, RedakteurEMail) VALUES('Das Keyword: entscheidend für die Suchmaschine', 'Suchmaschinen zeigen in der Suchausgabe die Beiträge mit themenrelevantem Inhalt.', '2020-11-11 10:10:11', '2022-11-11 10:10:10', 'RedakteurMustermann2@mail.com');

--Hat_gesehen:
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann1@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann1@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann1@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann1@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann1@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann1@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann1@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann1@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann2@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann2@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann2@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann2@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann2@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann2@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann2@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann2@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann3@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann3@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann3@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann3@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann3@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann3@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann3@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann3@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann4@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann4@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann4@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann4@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann4@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann4@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann4@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann4@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann5@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann5@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann5@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann5@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann5@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann5@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann5@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann5@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann6@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann6@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann6@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann6@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann6@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann6@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann6@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann6@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann7@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann7@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann7@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann7@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann7@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann7@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann7@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann7@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann8@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann8@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann8@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann8@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann8@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann8@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann8@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann8@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann9@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann9@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann9@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann9@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann9@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann9@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann9@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('mustermann9@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau1@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau1@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau1@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau1@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau1@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau1@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau1@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau1@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau2@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau2@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau2@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau2@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau2@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau2@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau2@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau2@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau3@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau3@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau3@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau3@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau3@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau3@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau3@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau3@mail.com',8);

INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau4@mail.com',1);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau4@mail.com',2);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau4@mail.com',3);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau4@mail.com',4);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau4@mail.com',5);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau4@mail.com',6);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau4@mail.com',7);
INSERT INTo Hat_gesehen(Email, BlogeintragId) VALUES('musterFrau4@mail.com',8);

--Kommentare:
INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES('2000-11-13 11:11:11', 'mustermann2 kommentiert Blogeintrag 1', 'mustermann2@mail.com', 1);
INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES(datetime('now', 'localtime'), 'mustermann2 kommentiert Blogeintrag 2', 'mustermann2@mail.com', 2);
INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES(datetime('now', 'localtime'), 'mustermann4 kommentiert Blogeintrag 3', 'mustermann4@mail.com', 3);
INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES(datetime('now', 'localtime'), 'mustermann6 kommentiert Blogeintrag 4', 'mustermann6@mail.com', 4);
INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES(datetime('now', 'localtime'), 'mustermann9 kommentiert Blogeintrag 1', 'mustermann9@mail.com', 1);
INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES(datetime('now', 'localtime'), 'mustermann9 kommentiert Blogeintrag 5', 'mustermann5@mail.com', 5);
INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES(datetime('now', 'localtime'), 'musterFrau1 kommentiert Blogeintrag 6', 'musterFrau1@mail.com', 6);
INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES(datetime('now', 'localtime'), 'musterFrau2 kommentiert Blogeintrag 7', 'musterFrau2@mail.com', 7);
INSERT INTO Kommentar(Erstellungsdatum, Text, EMail, BlogeintragId) VALUES(datetime('now', 'localtime'), 'musterFrau3 kommentiert Blogeintrag 8', 'musterFrau3@mail.com', 8);


--Albume:

INSERT INTO Album VALUES(1, FALSE);
INSERT INTO Album VALUES(2, FALSE);
INSERT INTO Album VALUES(3, FALSE);
INSERT INTO Album VALUES(4, FALSE);
INSERT INTO Album VALUES(5, TRUE);

--Produktbezeichnungen:

INSERT INTO Produktrezension(Id, Produktbezeichnung, Fazit) VALUES(6, 'Absatzsteigerungen bei bewerteten Produkten und Webshops', 'In fact, I found the book when I read a Stephen King novel, where I was mentioned as one of the books that in Stephen King most.');
INSERT INTO Produktrezension(Id, Produktbezeichnung, Fazit) VALUES(7, ' Bewertungsanfragen direkt im Webshop', 'Wenn ich auf die Tablets schaue, die existieren, dann gibt es, insbesondere im Bereich der Windows Tablets eine Vielzahl von Geräten, bei denen der');
INSERT INTO Produktrezension VALUES(8, 'Zulaessige Inhalte von Bewertungsanfragen', 'In diesem Test werde ich auf die Möglichkeiten eingehen, ein Tablet als Notizblock-Ersatz zu nutzen. ');

--Bilder:
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Lanschaft1', 'unvekannt', 'muster/pfad/bi ld1.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Sonne2', 'unbekannt', 'muster/pfad/bi!!ld2.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer1', 'unvbekannt', 'muster/pf)(/)(ad/bild3.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer2', 'unbekannt', 'muster/pf)(/)(ad/bild14.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer3', 'unbekannt', 'muster/pf)(/)(ad/bild4.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer4', 'unbekannt', 'muster/pf)(/)(ad/bild5.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer5', 'unbekannt', 'muster/pf)(/)(ad/bild6.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer6', 'unbekannt', 'muster/pf)(/)(ad/bild7.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer7', 'unbekannt', 'muster/pf)(/)(ad/bild8.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer8', 'unbekannt', 'muster/pf)(/)(ad/bild9.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer9', 'unbekannt', 'muster/pf)(/)(ad/bild10.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer10', 'unbekannt', 'muster/pf)(/)(ad/bild11.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer11', 'unbekannt', 'muster/pf)(/)(ad/bild12.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer12', 'unbekannt', 'muster/pf)(/)(ad/bild13.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer13', 'unbekannt', 'muster/pf)(/)(ad/bild133.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Meer14', 'unbekannt', 'muster/pf)(/)(ad/bild131.png');
INSERT INTO Bild(Bezeichnung, Aufnahmeort, Pfad) VALUES('Poster', 'unbekannt', 'muste§"$§%**ÄÄ_Är/pfad/bild4.png');

--Schlagwoerter:
INSERT INTO Schlagwort VALUES('Reise');
INSERT INTO Schlagwort VALUES('Film');
INSERT INTO Schlagwort VALUES('Reisen');
INSERT INTO Schlagwort VALUES('Filme');
INSERT INTO Schlagwort VALUES('Movie');
INSERT INTO Schlagwort VALUES('Wandern');
INSERT INTO Schlagwort VALUES('Zirkus');

--Bewertet
INSERT INTO Bewertet VALUES('mustermann1@mail.com', 1, 10);
INSERT INTO Bewertet VALUES('mustermann6@mail.com', 2, 9);
INSERT INTO Bewertet VALUES('mustermann6@mail.com', 3, 10);
INSERT INTO Bewertet VALUES('mustermann7@mail.com', 1, 10);
INSERT INTO Bewertet VALUES('mustermann7@mail.com', 2, 9 );
INSERT INTO Bewertet VALUES('mustermann7@mail.com', 3, 10);
INSERT INTO Bewertet VALUES('musterFrau1@mail.com', 3, 3);
INSERT INTO Bewertet VALUES('musterFrau2@mail.com', 3, 5);
INSERT INTO Bewertet VALUES('musterFrau3@mail.com', 3, 7);

--Folgt
INSERT INTO Folgt VALUES('RedakteurMustermann1@mail.com', 'RedakteurMustermann3@mail.com');
INSERT INTO Folgt VALUES('RedakteurMustermann2@mail.com', 'RedakteurMustermann1@mail.com');
INSERT INTO Folgt VALUES('RedakteurMustermann3@mail.com', 'RedakteurMustermann1@mail.com');

--Enthaelt
INSERT INTO Enthaelt VALUES(1, 1);
INSERT INTO Enthaelt VALUES(3, 2);
INSERT INTO Enthaelt VALUES(3, 3);
INSERT INTO Enthaelt VALUES(5, 4);
INSERT INTO Enthaelt VALUES(5, 5);
INSERT INTO Enthaelt VALUES(3, 6);
INSERT INTO Enthaelt VALUES(5, 7);
INSERT INTO Enthaelt VALUES(1, 8);
INSERT INTO Enthaelt VALUES(1, 9);
INSERT INTO Enthaelt VALUES(4, 9);
INSERT INTO Enthaelt VALUES(1, 10);
INSERT INTO Enthaelt VALUES(2, 11);
INSERT INTO Enthaelt VALUES(3, 11);
INSERT INTO Enthaelt VALUES(3, 12);
INSERT INTO Enthaelt VALUES(5, 13);
INSERT INTO Enthaelt VALUES(1, 14);
INSERT INTO Enthaelt VALUES(1, 15);
INSERT INTO Enthaelt VALUES(1, 12);
INSERT INTO Enthaelt VALUES(1, 13);

--Gehoert_zu
INSERT INTO Gehoert_zu VALUES('Film', 1);
INSERT INTO Gehoert_zu VALUES('Film', 2);
INSERT INTO Gehoert_zu VALUES('Film', 3);
INSERT INTO Gehoert_zu VALUES('Filme', 2);
INSERT INTO Gehoert_zu VALUES('Filme', 3);
INSERT INTO Gehoert_zu VALUES('Movie', 1);
INSERT INTO Gehoert_zu VALUES('Movie', 2);
INSERT INTO Gehoert_zu VALUES('Movie', 3);

--TRIGGER Test edit_blogeintrag
--UPDATE Blogeintrag SET Titel='New', Text='Letzte Aenderung: Sihe Änderungsdatum' WHERE id=2;


-- TRIGGER Test edit_Bild neuer Pfad von einem Bild, welches vom mehr als zwei Alben benutzt wird
--UPDATE Bild SET Pfad='kjakh.png' WHERE id = 2;


-- Trigger Test delete_Bild: Bild, welches vom mehr als zwei Alben benutzt wird
--DELETE FROM Bild WHERE id = 3;


--TRIGGER test Speicherung von 16 Bildern soll unmöglich sein
--INSERT INTO Enthaelt VALUES(1, 16);