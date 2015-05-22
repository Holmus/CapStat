/* --- WIPE DATABASE (Oracle syntax) --- */
/*
begin
for c in (select table_name from user_tables) loop
execute immediate ('drop table '||c.table_name||' cascade constraints');
end loop;
end;
/
begin
for c in (select * from user_objects) loop
execute immediate ('drop '||c.object_type||' '||c.object_name);
end loop;
end;
/
*/
/* END WIPE DATABASE */



/* --- RUN THESE ON YOUR LOCAL DATABASE FIRST TO SET IT UP --- */

/*
DROP DATABASE capstat;
DROP USER 'capstat_user'@'localhost';

CREATE USER 'capstat_user'@'localhost' IDENTIFIED BY '1234';
CREATE DATABASE capstat;

GRANT ALL ON capstat.* TO 'capstat_user'@'localhost';
GRANT ALL PRIVILEGES ON mysql.proc TO 'capstat_user'@'localhost';
 */



/* --- CREATING TABLES --- */
CREATE TABLE Users (
  nick          VARCHAR(30)   PRIMARY KEY,
  name          VARCHAR(30),
  pass          VARCHAR(300),
  birthday      DATE,
  admittanceYear  INT,
  admittanceReadingPeriod INT,
  eloRank       DOUBLE
);

CREATE TABLE Matches (
  id            CHAR(4)       PRIMARY KEY,
  p1            VARCHAR(30),
  p2            VARCHAR(30),
  spectator     VARCHAR(30),
  p1Score       INT,
  p2Score       INT,
  startTime     LONG,
  endTime       LONG,
  FOREIGN KEY (p1) REFERENCES Users(nick),
  FOREIGN KEY (p2) REFERENCES Users(nick),
  FOREIGN KEY (spectator) REFERENCES Users(nick)
);

CREATE TABLE ThrowSequences (
  matchId       CHAR(4),
  sequenceIndex INT,
  glasses       CHAR(20),
  startingPlayer  VARCHAR(30),
  throwBeforeWasHit BOOLEAN,
  sequence      VARCHAR(1000),

  FOREIGN KEY (matchId) REFERENCES Matches(id),
  PRIMARY KEY (matchId, sequenceIndex)
);

CREATE TABLE Attends(
  userNick      VARCHAR(30),
  MatchnId       CHAR(4),
  FOREIGN KEY (userNick) REFERENCES Users(nick),
  FOREIGN KEY (MatchnId) REFERENCES Matches(id)
);