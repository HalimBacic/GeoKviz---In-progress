BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "country" (
	"countryName"	TEXT NOT NULL,
	"capitalCity"	TEXT,
	"neighbours"	TEXT,
	"landmarks"	TEXT,
	"info"	TEXT,
	"continent"	TEXT,
	"level"	INTEGER
);
INSERT INTO "country" VALUES ('Germany','Berlin','Austria France Swicerland Poland','Brandeburg','Central Europe','Europe',1);
INSERT INTO "country" VALUES ('England','London','Wels Scotland','Big Ben','Brexit','Europe',1);
COMMIT;
