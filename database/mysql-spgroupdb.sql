CREATE SCHEMA IF NOT EXISTS spgroup_friendmgmt default character set utf8;
USE spgroup_friendmgmt;
DROP TABLE if exists USER;
DROP TABLE if exists RELATIONSHIP;

CREATE TABLE USER (
  ID varchar(36) not null,
  EMAIL VARCHAR(100) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE RELATIONSHIP (
  ID varchar(36) not null,
  FRIEND_ID varchar(36) not null,
  FRIEND_TYPE VARCHAR(36) NOT NULL DEFAULT 'FRIEND',
  BLOCK INT DEFAULT 0,
  PRIMARY KEY (ID, FRIEND_ID)
);