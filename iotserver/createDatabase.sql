BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "weatherStationData" (
	"id"	INTEGER NOT NULL,
	"weatherStation"	INTEGER,
	"temperature"	DECIMAL(5 , 2),
	"baromemetricPressure"	DECIMAL(6 , 2),
	"windSpeed"	DECIMAL(5 , 2),
	"relativeHumidity"	DECIMAL(5 , 2),
	"airQualityIndex"	INTEGER,
	FOREIGN KEY("weatherStation") REFERENCES "weatherStation"("id") ON DELETE CASCADE,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "weatherStation" (
	"id"	INTEGER NOT NULL,
	"field"	INTEGER,
	FOREIGN KEY("field") REFERENCES "field"("id") ON DELETE CASCADE,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "field" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	"name"	TEXT NOT NULL,
	"lat"	NUMERIC,
	"lon"	NUMERIC
);
CREATE TABLE IF NOT EXISTS "users" (
	"id"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	"username"	TEXT NOT NULL UNIQUE,
	"password"	INTEGER NOT NULL,
	"eMail"	TEXT
);
COMMIT;
