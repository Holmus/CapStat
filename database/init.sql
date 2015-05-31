CREATE USER 'capstat_user'@'localhost' IDENTIFIED BY '1234';
CREATE DATABASE capstat;

GRANT ALL ON capstat.* TO 'capstat_user'@'localhost';
GRANT ALL PRIVILEGES ON mysql.proc TO 'capstat_user'@'localhost';
