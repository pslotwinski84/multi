DROP TABLE IF EXISTS ROOM;
DROP TABLE IF EXISTS MOVIE;
DROP TABLE IF EXISTS SCREENING;


CREATE TABLE ROOM (
  roomid int(10) NOT NULL AUTO_INCREMENT,
  rows int(10) NOT NULL,
  cols int(10) NOT NULL,
  screensize int(10) NOT NULL,
  PRIMARY KEY (roomid)
);

CREATE TABLE MOVIE (
  movieid int(10) NOT NULL AUTO_INCREMENT,
  title varchar(250) NOT NULL,
  description varchar(250) NOT NULL,
  duration int(10) NOT NULL,
  PRIMARY KEY (movieid)
);

CREATE TABLE SCREENING (
  screeningid int(10) NOT NULL AUTO_INCREMENT,
  roomid int(10) NOT NULL,
  movieid int(10) NOT NULL,
  date TIMESTAMP NULL,
  seats varchar(8000) NOT NULL,
  PRIMARY KEY (screeningid)
);

 ALTER TABLE SCREENING
    ADD FOREIGN KEY (roomid) 
    REFERENCES ROOM(roomid);

 ALTER TABLE SCREENING
    ADD FOREIGN KEY (movieid) 
    REFERENCES MOVIE(movieid);

