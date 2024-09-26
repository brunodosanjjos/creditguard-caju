CREATE DATABASE IF NOT EXISTS db_creditguard;
USE db_creditguard;
CREATE USER IF NOT EXISTS 'myuser'@'%' IDENTIFIED BY 'secret';
GRANT ALL PRIVILEGES ON db_creditguard.* TO 'myuser'@'%';
FLUSH PRIVILEGES