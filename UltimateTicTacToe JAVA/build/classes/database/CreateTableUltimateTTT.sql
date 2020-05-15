create table HISTORY
(
        ID int GENERATED ALWAYS AS IDENTITY not null primary key,
	PLAYER1 VARCHAR(25) not null,
	PLAYER2 VARCHAR(25) not null,
	WINS INTEGER,
	LOSES INTEGER,
	DRAW INTEGER
);

create table PLAYER
(
	NAME_PLAYER VARCHAR(25) not null primary key
        WINS INTEGER,
	LOSES INTEGER,
	DRAW INTEGER
);