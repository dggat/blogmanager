PRAGMA auto_vacuum = 1;
PRAGMA encoding = "UTF-8";
PRAGMA foreign_keys = 1;
PRAGMA journal_mode = WAL;
PRAGMA synchronous = NORMAL;

CREATE TABLE Nutzer(
	Email VARCHAR UNIQUE NOT NULL COLLATE NOCASE CHECK (Email LIKE '_%@_%._%' AND Email NOT GLOB '*[^0-9a-zA-Z]*@*.*'
	                                                                          AND Email NOT GLOB '*@*[^0-9a-zA-Z]*.*'
	                                                                          AND Email NOT GLOB '*@*.*[^a-zA-Z]*'),

	Passwort VARCHAR NOT NULL CHECK (length(Passwort) >=5  AND length(Passwort) <= 9
							       AND Passwort GLOB '*[0-9]*'
	                               AND Passwort GLOB '*[A-Z]*'
                                   AND Passwort GLOB '*[a-z]*'
                                   AND Passwort GLOB '*[A-Za-z]*'
	                               AND Passwort NOT GLOB '*[A-Z][A-Z]*'
	                               AND Passwort NOT GLOB '*[ -/]*'
							       AND Passwort NOT GLOB '*[:-?]*'
							       AND Passwort NOT GLOB '*[[-_]*'
							       AND Passwort NOT GLOB '*[{-~]*'),
	
	Geschlecht VARCHAR NOT NULL CHECK (Geschlecht in ('m', 'w', 'd')),
	Benutzername VARCHAR NOT NULL CHECK (length(Benutzername) > 0 AND Benutzername NOT GLOB '*[^ -~]*'),
	Geburtsdatum DATE NOT NULL CHECK (Geburtsdatum IS strftime('%Y-%m-%d', Geburtsdatum)),
	PRIMARY KEY (Email)
);

CREATE TABLE Redakteur (
Email VARCHAR UNIQUE NOT NULL COLLATE NOCASE,
	Vorname VARCHAR NOT NULL CHECK (length(Vorname) > 0 AND Vorname NOT GLOB '*[^ -~]*'),
	Nachname VARCHAR NOT NULL CHECK (length(Nachname) > 0 AND Nachname NOT GLOB '*[^ -~]*'),
	Biographie TEXT CHECK (length(Biographie) >= 0 AND Biographie GLOB '*[ -~]*'
	                                               OR Biographie NOT GLOB '*[^ -~]*'),
	PRIMARY KEY (Email),
	FOREIGN KEY (Email) REFERENCES Nutzer(Email) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Chefredakteur (
	Email VARCHAR UNIQUE NOT NULL COLLATE NOCASE,
	Telefonnummer VARCHAR UNIQUE NOT NULL COLLATE NOCASE CHECK (Telefonnummer NOT GLOB '*[^ -~]*'),  
	PRIMARY KEY (Email),
	FOREIGN KEY (Email) REFERENCES Redakteur(Email) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Blogeintrag ( 
	Id INTEGER UNIQUE NOT NULL,
	Titel VARCHAR CHECK (length(Titel) > 0 AND Titel GLOB '*[ -~]*'),
	Text TEXT CHECK (length(Text) > 0 AND Text GLOB '*[ -~]*'),
	Erstellungsdatum DATETIME NOT NULL CHECK (Erstellungsdatum IS strftime('%Y-%m-%d %H:%M:%S', Erstellungsdatum)),
	Aenderungsdatum DATETIME CHECK (Aenderungsdatum IS strftime('%Y-%m-%d %H:%M:%S', Aenderungsdatum) AND strftime('%s',Aenderungsdatum) - strftime('%s',Erstellungsdatum) >= 0),
	RedakteurEMail VARCHAR NOT NULL COLLATE NOCASE,
	PRIMARY KEY (Id),
	FOREIGN KEY (RedakteurEMail) REFERENCES Redakteur(EMail) ON UPDATE CASCADE ON DELETE CASCADE	
);

CREATE TABLE Kommentar (
	Id INTEGER UNIQUE NOT NULL,
	Erstellungsdatum DATETIME NOT NULL CHECK (Erstellungsdatum IS strftime('%Y-%m-%d %H:%M:%S', Erstellungsdatum)),
	Text TEXT CHECK (length(Text) > 0 AND Text GLOB '*[ -~]*'), 
	Email VARCHAR NOT NULL COLLATE NOCASE,	
	BlogeintragId INTEGER NOT NULL,	
	PRIMARY KEY (Id),
	FOREIGN KEY (EMail) REFERENCES Nutzer(EMail) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (BlogeintragId) REFERENCES Blogeintrag(Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Album (
	Id INTEGER UNIQUE NOT NULL COLLATE NOCASE,
	Private BOOLEAN NOT NULL CHECK (Private == True OR Private == False),
	PRIMARY KEY (Id),
	FOREIGN KEY (Id) REFERENCES Blogeintrag(Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Produktrezension (
	Id INTEGER UNIQUE NOT NULL COLLATE NOCASE,
	Produktbezeichnung VARCHAR NOT NULL CHECK (length(Produktbezeichnung) > 0 AND Produktbezeichnung NOT GLOB '*[^ -~]*'),
	Fazit TEXT NOT NULL CHECK (length(Fazit) > 0 AND length(Fazit) < 1001
												AND Fazit GLOB '*[ -~]*'),
	PRIMARY KEY (Id),
	FOREIGN KEY (Id) REFERENCES Blogeintrag(Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Bild (
	Id INTEGER UNIQUE NOT NULL COLLATE NOCASE,
	Bezeichnung VARCHAR NOT NULL CHECK(length(Bezeichnung) > 0 AND Bezeichnung NOT GLOB '*[^ -~]*'),
	Aufnahmeort VARCHAR NOT NULL CHECK(length(Aufnahmeort) > 0 AND Aufnahmeort NOT GLOB '*[^ -~]*'),
	Pfad VARCHAR UNIQUE NOT NULL COLLATE NOCASE CHECK(Pfad LIKE '_%.png%' AND Pfad NOT GLOB '*[^ -~]*.png*.*'),
	PRIMARY KEY (Id)
);

CREATE TABLE Schlagwort (
	Wort VARCHAR UNIQUE NOT NULL COLLATE NOCASE CHECK(length(Wort) > 0 AND Wort NOT GLOB '*[^A-z]*'),
	PRIMARY KEY (Wort)
);

CREATE TABLE Hat_gesehen (
	EMail VARCHAR NOT NULL COLLATE NOCASE,
	BlogeintragId INTEGER NOT NULL COLLATE NOCASE,
	PRIMARY KEY (EMail, BlogeintragId),
	UNIQUE (Email, BlogeintragId),
	FOREIGN KEY (EMail) REFERENCES Nutzer(EMail) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (BlogeintragId) REFERENCES Blogeintrag(Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Bewertet (
	EMail VARCHAR NOT NULL COLLATE NOCASE,
	BlogeintragId INTEGER NOT NULL COLLATE NOCASE,
	Bewertung INTEGER NOT NULL CHECK (Bewertung > 0 AND Bewertung <= 10),
	PRIMARY KEY (EMail, BlogeintragId),
	UNIQUE (Email, BlogeintragId),
	FOREIGN KEY (EMail) REFERENCES Nutzer(EMail) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (BlogeintragId) REFERENCES Blogeintrag(Id) ON UPDATE CASCADE ON DELETE CASCADE	
);

CREATE TABLE Folgt (
	EMail1 VARCHAR NOT NULL COLLATE NOCASE CHECK(Email1 != Email2),
	EMail2 VARCHAR NOT NULL COLLATE NOCASE CHECK(Email1 != Email2),
	PRIMARY KEY (EMail1, EMail2),
	UNIQUE (Email1, EMail2),										
	FOREIGN KEY (EMail1) REFERENCES Redakteur(EMail) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (EMail2) REFERENCES Redakteur(EMail) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Enthaelt (
	BlogeintragId INTEGER NOT NULL COLLATE NOCASE,
	BildId INTEGER NOT NULL COLLATE NOCASE,
	PRIMARY KEY (BlogeintragId, BildId),
	UNIQUE (BlogeintragId, BildId), 									
	FOREIGN KEY (BlogeintragId) REFERENCES Album(Id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (BildId) REFERENCES Bild(Id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Gehoert_zu (
	Wort VARCHAR NOT NULL COLLATE NOCASE,
	BlogeintragId INTEGER NOT NULL COLLATE NOCASE,
	PRIMARY KEY (Wort, BlogeintragId),
	UNIQUE (Wort, BlogeintragId),										
	FOREIGN KEY (Wort) REFERENCES Schlagwort(Wort) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (BlogeintragId) REFERENCES Blogeintrag(Id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TRIGGER edit_Blogeintrag 
   AFTER UPDATE ON Blogeintrag
BEGIN
	UPDATE Blogeintrag
		SET Aenderungsdatum = datetime('now', 'localtime') WHERE Id=old.Id;
END;

CREATE TRIGGER edit_Bild 
   BEFORE UPDATE ON Bild
BEGIN
	SELECT
        CASE
            WHEN ((SELECT COUNT(BlogeintragId) > 1 FROM Enthaelt WHERE BildId=new.Id)) THEN
				RAISE(ABORT, 'Das Bild kann nicht geaendert werden, da es von mehreren Blogeintraegen benutzt wird!')
			END;
END;
   
CREATE TRIGGER delete_Bild 
   BEFORE DELETE ON Bild
BEGIN
	SELECT
        CASE
            WHEN (SELECT COUNT(BlogeintragId) > 1 FROM Enthaelt WHERE BildId=old.Id) THEN
				RAISE(ABORT, 'Dieses Bild darf nicht geloescht werdern, da es von mehreren Blogeintraegen benutzt wird!')
			END;
END;

CREATE TRIGGER set_Kommentar 
   BEFORE INSERT ON Kommentar
BEGIN
	SELECT
        CASE
            WHEN (SELECT COUNT(EMail = new.EMail) = 0 FROM Hat_gesehen WHERE EMail=new.EMail AND BlogeintragId=new.BlogeintragId) THEN
           -- WHEN (select EMail = new.EMail from Nutzer where EMail in (select EMail from Hat_gesehen where BlogeintragId = new.BlogeintragId)) THEN
				RAISE(ABORT, 'Blogeintraege, die Sie nich gesehen haben, duerfen Sie nicht kommentiert werden!')
			END;
END;