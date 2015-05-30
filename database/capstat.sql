/*
Run init.sql prior to running this file, in order to set up the actual
database. If you need to delete the database for some reason, run wipe.sql,
then init.sql, then this file.
 */

CREATE TABLE Users (
  nick          VARCHAR(30)   PRIMARY KEY,
  name          VARCHAR(30),
  pass          VARCHAR(100),
  birthday      DATE,
  admittanceYear  INT,
  admittanceReadingPeriod INT,
  eloRank       DOUBLE
);

CREATE TABLE Matches (
  id            VARCHAR(30)       PRIMARY KEY,
  p1            VARCHAR(30) REFERENCES Users(nick),
  p2            VARCHAR(30) REFERENCES Users(nick),
  spectator     VARCHAR(30) REFERENCES Users(nick),
  p1Score       INT,
  p2Score       INT,
  startTime     LONG,
  endTime       LONG
);

CREATE TABLE ThrowSequences (
  matchId       VARCHAR(30),
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
  MatchId       VARCHAR(30),
  FOREIGN KEY (userNick) REFERENCES Users(nick),
  FOREIGN KEY (MatchId) REFERENCES Matches(id)
);
