CREATE TABLE HACKYUSER (
  ID UUID NOT NULL ,
  EMAIL VARCHAR(256) NOT NULL ,
  PASSWORD VARCHAR(256) NOT NULL ,
  ROLE CHAR(32) NULL ,
  LASTMOD TIMESTAMP NOT NULL ,
  XMLUSER VARCHAR(64000) NOT NULL ,
  XMLUSERREF VARCHAR(2000) NOT NULL ,
  PRIMARY KEY (ID) );

CREATE  TABLE PROJECT (
  ID UUID NOT NULL ,
  PROJECTNAME VARCHAR(128) NOT NULL ,
  OWNER_ID UUID NOT NULL ,
  STARTTIME TIMESTAMP NOT NULL ,
  ENDTIME TIMESTAMP NOT NULL ,
  LASTMOD TIMESTAMP NOT NULL ,
  XMLPROJECT VARCHAR(64000) NOT NULL ,
  XMLPROJECTREF VARCHAR(2000) NOT NULL ,
  PRIMARY KEY (ID) ,
  CONSTRAINT OWNER_ID
    FOREIGN KEY (OWNER_ID)
    REFERENCES HACKYUSER (ID )
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE  TABLE PROJECTURI (
  ID UUID NOT NULL ,
  PROJECT_ID UUID NOT NULL ,
  URI VARCHAR(512) NOT NULL ,
  PRIMARY KEY (ID),
  CONSTRAINT PROJECT_ID
    FOREIGN KEY (PROJECT_ID)
    REFERENCES PROJECT (ID)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
    
CREATE TABLE SENSORDATATYPE (
  ID UUID NOT NULL ,
  NAME VARCHAR(256) NOT NULL ,
  LASTMOD TIMESTAMP NOT NULL ,
  XMLSENSORDATATYPE VARCHAR(64000) NOT NULL ,
  XMLSENSORDATATYPEREF VARCHAR(2000) NOT NULL ,
  PRIMARY KEY (ID) );

CREATE TABLE SENSORDATA (
  ID UUID NOT NULL ,
  OWNER_ID UUID NOT NULL ,
  TSTAMP TIMESTAMP NOT NULL ,
  SDT_ID UUID NOT NULL ,
  RUNTIME TIMESTAMP NOT NULL ,
  TOOL VARCHAR(64) NOT NULL ,
  RESOURCE VARCHAR(512) NOT NULL ,
  LASTMOD TIMESTAMP NOT NULL ,
  XMLSENSORDATA VARCHAR(64000) NOT NULL ,
  XMLSENSORDATAREF VARCHAR(2000) NOT NULL ,
  PRIMARY KEY (ID) ,
  CONSTRAINT OWNER_ID
    FOREIGN KEY (OWNER_ID)
    REFERENCES HACKYUSER (ID )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT SDT_ID
    FOREIGN KEY (SDT_ID)
    REFERENCES SENSORDATATYPE (ID)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE TABLE SENSORDATA_PROPERTIES (
  ID UUID NOT NULL,
  SENSORDATA_ID UUID NOT NULL ,
  KEY VARCHAR(256) NOT NULL ,
  VALUE VARCHAR(256) NOT NULL ,
  PRIMARY KEY (ID) ,
  CONSTRAINT SENSORDATA_ID
    FOREIGN KEY (SENSORDATA_ID)
    REFERENCES SENSORDATA (ID)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

CREATE INDEX TstampIndex ON SensorData(Tstamp asc);
CREATE INDEX RuntimeIndex ON SensorData(Runtime desc);
CREATE INDEX ToolIndex ON SensorData(Tool asc);
CREATE INDEX UserIndex ON HackyUser(email asc);
CREATE INDEX SdtNameIndex ON SensorDataType(name asc);
CREATE INDEX OwnerIdIndex ON SensorData(owner_id asc);



