-- MySQL dump 10.19  Distrib 10.3.39-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: SACER_KETTLE
-- ------------------------------------------------------
-- Server version	10.3.39-MariaDB-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--------------------------------------------------------
--  DDL for Table MON_EXEC_TRASF
--------------------------------------------------------

  CREATE TABLE "SACER_KETTLE"."MON_EXEC_TRASF"
   (	"ID_EXEC_TRASF" NUMBER DEFAULT "SACER_KETTLE"."SMON_EXEC_TRASF"."NEXTVAL",
	"ID_PIG_OBJECT" NUMBER,
	"NM_TRASF" VARCHAR2(100 BYTE),
	"TI_STATO_TRASF" VARCHAR2(30 BYTE),
	"DT_INVOCAZIONE_WS" DATE,
	"DT_INIZIO_TRASF" DATE,
	"DT_FINE_TRASF" DATE,
	"DS_STATO_TRASF" VARCHAR2(1024 BYTE),
	"CD_ERR_TRASF" VARCHAR2(30 BYTE),
	"DS_ERR_TRASF" VARCHAR2(1024 BYTE),
	"NM_KS_INSTANCE" VARCHAR2(100 BYTE) DEFAULT 'istanza singola'
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "TBS_JBOSS"   NO INMEMORY ;

   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."ID_EXEC_TRASF" IS 'PK della tabella. La sequence utilizzata e'' la SMON_EXEC_TRASF.';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."ID_PIG_OBJECT" IS 'Identificativo del pig_object (vedi schema SACER_PING)';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."NM_TRASF" IS 'Nome della trasformazione associata all''id oggetto (corrisponde al nome della cartella sul repository)';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."TI_STATO_TRASF" IS 'Stato della trasformazione';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."DT_INVOCAZIONE_WS" IS 'Data/ora in cui il ws e'' stato invocato';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."DT_INIZIO_TRASF" IS 'Data ora in cui la trasformazione e'' effettivamente iniziata';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."DT_FINE_TRASF" IS 'Data ora in cui la trasformazione è terminata';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."DS_STATO_TRASF" IS 'Descrizione dello stato della trasformazione';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."CD_ERR_TRASF" IS 'Codice di errore della trasformazione';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."DS_ERR_TRASF" IS 'Descrizione dell''errore sulla trasformazione';
   COMMENT ON COLUMN "SACER_KETTLE"."MON_EXEC_TRASF"."NM_KS_INSTANCE" IS 'Identificativo istanza di kettle che possiede questa riga di log. "istanza_singola" si riferisce al kettle server non parallelizzato';
   COMMENT ON TABLE "SACER_KETTLE"."MON_EXEC_TRASF"  IS 'Tabella di monitoraggio trasformazioni kettle';

--
-- Table structure for table `R_CLUSTER`
--

DROP TABLE IF EXISTS `R_CLUSTER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_CLUSTER` (
  `ID_CLUSTER` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `BASE_PORT` varchar(255) DEFAULT NULL,
  `SOCKETS_BUFFER_SIZE` varchar(255) DEFAULT NULL,
  `SOCKETS_FLUSH_INTERVAL` varchar(255) DEFAULT NULL,
  `SOCKETS_COMPRESSED` tinyint(1) DEFAULT NULL,
  `DYNAMIC_CLUSTER` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_CLUSTER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_CLUSTER`
--

LOCK TABLES `R_CLUSTER` WRITE;
/*!40000 ALTER TABLE `R_CLUSTER` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_CLUSTER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_CLUSTER_SLAVE`
--

DROP TABLE IF EXISTS `R_CLUSTER_SLAVE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_CLUSTER_SLAVE` (
  `ID_CLUSTER_SLAVE` bigint(20) NOT NULL,
  `ID_CLUSTER` int(11) DEFAULT NULL,
  `ID_SLAVE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_CLUSTER_SLAVE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_CLUSTER_SLAVE`
--

LOCK TABLES `R_CLUSTER_SLAVE` WRITE;
/*!40000 ALTER TABLE `R_CLUSTER_SLAVE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_CLUSTER_SLAVE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_CONDITION`
--

DROP TABLE IF EXISTS `R_CONDITION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_CONDITION` (
  `ID_CONDITION` bigint(20) NOT NULL,
  `ID_CONDITION_PARENT` int(11) DEFAULT NULL,
  `NEGATED` tinyint(1) DEFAULT NULL,
  `OPERATOR` varchar(255) DEFAULT NULL,
  `LEFT_NAME` varchar(255) DEFAULT NULL,
  `CONDITION_FUNCTION` varchar(255) DEFAULT NULL,
  `RIGHT_NAME` varchar(255) DEFAULT NULL,
  `ID_VALUE_RIGHT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_CONDITION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_CONDITION`
--

LOCK TABLES `R_CONDITION` WRITE;
/*!40000 ALTER TABLE `R_CONDITION` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_CONDITION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_DATABASE`
--

DROP TABLE IF EXISTS `R_DATABASE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_DATABASE` (
  `ID_DATABASE` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ID_DATABASE_TYPE` int(11) DEFAULT NULL,
  `ID_DATABASE_CONTYPE` int(11) DEFAULT NULL,
  `HOST_NAME` varchar(255) DEFAULT NULL,
  `DATABASE_NAME` mediumtext DEFAULT NULL,
  `PORT` int(11) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `SERVERNAME` varchar(255) DEFAULT NULL,
  `DATA_TBS` varchar(255) DEFAULT NULL,
  `INDEX_TBS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DATABASE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_DATABASE`
--

LOCK TABLES `R_DATABASE` WRITE;
/*!40000 ALTER TABLE `R_DATABASE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_DATABASE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_DATABASE_ATTRIBUTE`
--

DROP TABLE IF EXISTS `R_DATABASE_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_DATABASE_ATTRIBUTE` (
  `ID_DATABASE_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_DATABASE` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_STR` mediumtext DEFAULT NULL,
  PRIMARY KEY (`ID_DATABASE_ATTRIBUTE`),
  UNIQUE KEY `IDX_RDAT` (`ID_DATABASE`,`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_DATABASE_ATTRIBUTE`
--

LOCK TABLES `R_DATABASE_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `R_DATABASE_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_DATABASE_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_DATABASE_CONTYPE`
--

DROP TABLE IF EXISTS `R_DATABASE_CONTYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_DATABASE_CONTYPE` (
  `ID_DATABASE_CONTYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DATABASE_CONTYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_DATABASE_CONTYPE`
--

LOCK TABLES `R_DATABASE_CONTYPE` WRITE;
/*!40000 ALTER TABLE `R_DATABASE_CONTYPE` DISABLE KEYS */;
INSERT INTO `R_DATABASE_CONTYPE` VALUES (1,'Native','Native (JDBC)'),(2,'ODBC','ODBC'),(3,'OCI','OCI'),(4,'Plugin','Plugin specific access method'),(5,'JNDI','JNDI'),(6,',','Custom');
/*!40000 ALTER TABLE `R_DATABASE_CONTYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_DATABASE_TYPE`
--

DROP TABLE IF EXISTS `R_DATABASE_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_DATABASE_TYPE` (
  `ID_DATABASE_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DATABASE_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_DATABASE_TYPE`
--

LOCK TABLES `R_DATABASE_TYPE` WRITE;
/*!40000 ALTER TABLE `R_DATABASE_TYPE` DISABLE KEYS */;
INSERT INTO `R_DATABASE_TYPE` VALUES (1,'DERBY','Apache Derby'),(2,'AS/400','AS/400'),(3,'AZURESQLDB','Azure SQL DB'),(4,'INTERBASE','Borland Interbase'),(5,'INFINIDB','Calpont InfiniDB'),(6,'IMPALASIMBA','Cloudera Impala'),(7,'DBASE','dBase III, IV or 5'),(8,'EXASOL4','Exasol 4'),(9,'EXTENDB','ExtenDB'),(10,'FIREBIRD','Firebird SQL'),(11,'GENERIC','Generic database'),(12,'GOOGLEBIGQUERY','Google BigQuery'),(13,'GREENPLUM','Greenplum'),(14,'SQLBASE','Gupta SQL Base'),(15,'H2','H2'),(16,'HIVE','Hadoop Hive'),(17,'HIVE2','Hadoop Hive 2/3'),(18,'HIVEWAREHOUSE','Hive Warehouse Connector'),(19,'HYPERSONIC','Hypersonic'),(20,'DB2','IBM DB2'),(21,'IMPALA','Impala'),(22,'INFOBRIGHT','Infobright'),(23,'INFORMIX','Informix'),(24,'INGRES','Ingres'),(25,'VECTORWISE','Ingres VectorWise'),(26,'CACHE','Intersystems Cache'),(27,'KINGBASEES','KingbaseES'),(28,'LucidDB','LucidDB'),(29,'MARIADB','MariaDB'),(30,'SAPDB','MaxDB (SAP DB)'),(31,'MONETDB','MonetDB'),(32,'MSACCESS','MS Access'),(33,'MSSQL','MS SQL Server'),(34,'MSSQLNATIVE','MS SQL Server (Native)'),(35,'MYSQL','MySQL'),(36,'MONDRIAN','Native Mondrian'),(37,'NEOVIEW','Neoview'),(38,'NETEZZA','Netezza'),(39,'ORACLE','Oracle'),(40,'ORACLERDB','Oracle RDB'),(41,'PALO','Palo MOLAP Server'),(42,'KettleThin','Pentaho Data Services'),(43,'POSTGRESQL','PostgreSQL'),(44,'REDSHIFT','Redshift'),(45,'REMEDY-AR-SYSTEM','Remedy Action Request System'),(46,'SAPR3','SAP ERP System'),(47,'SNOWFLAKEHV','Snowflake'),(48,'SPARKSIMBA','SparkSQL'),(49,'SQLITE','SQLite'),(50,'SYBASE','Sybase'),(51,'SYBASEIQ','SybaseIQ'),(52,'TERADATA','Teradata'),(53,'UNIVERSE','UniVerse database'),(54,'VERTICA','Vertica'),(55,'VERTICA5','Vertica 5+');
/*!40000 ALTER TABLE `R_DATABASE_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_DEPENDENCY`
--

DROP TABLE IF EXISTS `R_DEPENDENCY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_DEPENDENCY` (
  `ID_DEPENDENCY` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_DATABASE` int(11) DEFAULT NULL,
  `TABLE_NAME` varchar(255) DEFAULT NULL,
  `FIELD_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DEPENDENCY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_DEPENDENCY`
--

LOCK TABLES `R_DEPENDENCY` WRITE;
/*!40000 ALTER TABLE `R_DEPENDENCY` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_DEPENDENCY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_DIRECTORY`
--

DROP TABLE IF EXISTS `R_DIRECTORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_DIRECTORY` (
  `ID_DIRECTORY` bigint(20) NOT NULL,
  `ID_DIRECTORY_PARENT` int(11) DEFAULT NULL,
  `DIRECTORY_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_DIRECTORY`),
  UNIQUE KEY `IDX_RDIR` (`ID_DIRECTORY_PARENT`,`DIRECTORY_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_DIRECTORY`
--

LOCK TABLES `R_DIRECTORY` WRITE;
/*!40000 ALTER TABLE `R_DIRECTORY` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_DIRECTORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_ELEMENT`
--

DROP TABLE IF EXISTS `R_ELEMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_ELEMENT` (
  `ID_ELEMENT` bigint(20) NOT NULL,
  `ID_ELEMENT_TYPE` int(11) DEFAULT NULL,
  `NAME` text DEFAULT NULL,
  PRIMARY KEY (`ID_ELEMENT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_ELEMENT`
--

LOCK TABLES `R_ELEMENT` WRITE;
/*!40000 ALTER TABLE `R_ELEMENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_ELEMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_ELEMENT_ATTRIBUTE`
--

DROP TABLE IF EXISTS `R_ELEMENT_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_ELEMENT_ATTRIBUTE` (
  `ID_ELEMENT_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_ELEMENT` int(11) DEFAULT NULL,
  `ID_ELEMENT_ATTRIBUTE_PARENT` int(11) DEFAULT NULL,
  `ATTR_KEY` varchar(255) DEFAULT NULL,
  `ATTR_VALUE` mediumtext DEFAULT NULL,
  PRIMARY KEY (`ID_ELEMENT_ATTRIBUTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_ELEMENT_ATTRIBUTE`
--

LOCK TABLES `R_ELEMENT_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `R_ELEMENT_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_ELEMENT_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_ELEMENT_TYPE`
--

DROP TABLE IF EXISTS `R_ELEMENT_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_ELEMENT_TYPE` (
  `ID_ELEMENT_TYPE` bigint(20) NOT NULL,
  `ID_NAMESPACE` int(11) DEFAULT NULL,
  `NAME` text DEFAULT NULL,
  `DESCRIPTION` mediumtext DEFAULT NULL,
  PRIMARY KEY (`ID_ELEMENT_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_ELEMENT_TYPE`
--

LOCK TABLES `R_ELEMENT_TYPE` WRITE;
/*!40000 ALTER TABLE `R_ELEMENT_TYPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_ELEMENT_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOB`
--

DROP TABLE IF EXISTS `R_JOB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOB` (
  `ID_JOB` bigint(20) NOT NULL,
  `ID_DIRECTORY` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext DEFAULT NULL,
  `EXTENDED_DESCRIPTION` mediumtext DEFAULT NULL,
  `JOB_VERSION` varchar(255) DEFAULT NULL,
  `JOB_STATUS` int(11) DEFAULT NULL,
  `ID_DATABASE_LOG` int(11) DEFAULT NULL,
  `TABLE_NAME_LOG` varchar(255) DEFAULT NULL,
  `CREATED_USER` varchar(255) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  `USE_BATCH_ID` tinyint(1) DEFAULT NULL,
  `PASS_BATCH_ID` tinyint(1) DEFAULT NULL,
  `USE_LOGFIELD` tinyint(1) DEFAULT NULL,
  `SHARED_FILE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_JOB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOB`
--

LOCK TABLES `R_JOB` WRITE;
/*!40000 ALTER TABLE `R_JOB` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_JOB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOBENTRY`
--

DROP TABLE IF EXISTS `R_JOBENTRY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOBENTRY` (
  `ID_JOBENTRY` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY_TYPE` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext DEFAULT NULL,
  PRIMARY KEY (`ID_JOBENTRY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOBENTRY`
--

LOCK TABLES `R_JOBENTRY` WRITE;
/*!40000 ALTER TABLE `R_JOBENTRY` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_JOBENTRY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOBENTRY_ATTRIBUTE`
--

DROP TABLE IF EXISTS `R_JOBENTRY_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOBENTRY_ATTRIBUTE` (
  `ID_JOBENTRY_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` double DEFAULT NULL,
  `VALUE_STR` mediumtext DEFAULT NULL,
  PRIMARY KEY (`ID_JOBENTRY_ATTRIBUTE`),
  UNIQUE KEY `IDX_RJEA` (`ID_JOBENTRY_ATTRIBUTE`,`CODE`,`NR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOBENTRY_ATTRIBUTE`
--

LOCK TABLES `R_JOBENTRY_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `R_JOBENTRY_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_JOBENTRY_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOBENTRY_COPY`
--

DROP TABLE IF EXISTS `R_JOBENTRY_COPY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOBENTRY_COPY` (
  `ID_JOBENTRY_COPY` bigint(20) NOT NULL,
  `ID_JOBENTRY` int(11) DEFAULT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY_TYPE` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `GUI_LOCATION_X` int(11) DEFAULT NULL,
  `GUI_LOCATION_Y` int(11) DEFAULT NULL,
  `GUI_DRAW` tinyint(1) DEFAULT NULL,
  `PARALLEL` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_JOBENTRY_COPY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOBENTRY_COPY`
--

LOCK TABLES `R_JOBENTRY_COPY` WRITE;
/*!40000 ALTER TABLE `R_JOBENTRY_COPY` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_JOBENTRY_COPY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOBENTRY_DATABASE`
--

DROP TABLE IF EXISTS `R_JOBENTRY_DATABASE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOBENTRY_DATABASE` (
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY` int(11) DEFAULT NULL,
  `ID_DATABASE` int(11) DEFAULT NULL,
  KEY `IDX_RJD1` (`ID_JOB`),
  KEY `IDX_RJD2` (`ID_DATABASE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOBENTRY_DATABASE`
--

LOCK TABLES `R_JOBENTRY_DATABASE` WRITE;
/*!40000 ALTER TABLE `R_JOBENTRY_DATABASE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_JOBENTRY_DATABASE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOBENTRY_TYPE`
--

DROP TABLE IF EXISTS `R_JOBENTRY_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOBENTRY_TYPE` (
  `ID_JOBENTRY_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_JOBENTRY_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOBENTRY_TYPE`
--

LOCK TABLES `R_JOBENTRY_TYPE` WRITE;
/*!40000 ALTER TABLE `R_JOBENTRY_TYPE` DISABLE KEYS */;
INSERT INTO `R_JOBENTRY_TYPE` VALUES (1,'ABORT','Abort job'),(2,'ADD_RESULT_FILENAMES','Add filenames to result'),(3,'EMRJobExecutorPlugin','Amazon EMR job executor'),(4,'HiveJobExecutorPlugin','Amazon Hive job executor'),(5,'DataRefineryBuildModel','Build model'),(6,'MYSQL_BULK_FILE','Bulk load from MySQL into file'),(7,'MSSQL_BULK_LOAD','Bulk load into MSSQL'),(8,'MYSQL_BULK_LOAD','Bulk load into MySQL'),(9,'CHECK_DB_CONNECTIONS','Check DB connections'),(10,'CHECK_FILES_LOCKED','Check files locked'),(11,'FOLDER_IS_EMPTY','Check if a folder is empty'),(12,'CONNECTED_TO_REPOSITORY','Check if connected to repository'),(13,'XML_WELL_FORMED','Check if XML file is well formed'),(14,'WEBSERVICE_AVAILABLE','Check webservice availability'),(15,'FILES_EXIST','Checks if files exist'),(16,'COLUMNS_EXIST','Columns exist in a table'),(17,'FOLDERS_COMPARE','Compare folders'),(18,'DOS_UNIX_CONVERTER','Convert file between Windows and Unix'),(19,'COPY_FILES','Copy files'),(20,'CREATE_FOLDER','Create a folder'),(21,'CREATE_FILE','Create file'),(22,'PGP_DECRYPT_FILES','Decrypt files with PGP'),(23,'DELETE_FILE','Delete file'),(24,'DELETE_RESULT_FILENAMES','Delete filenames from result'),(25,'DELETE_FILES','Delete files'),(26,'DELETE_FOLDERS','Delete folders'),(27,'MSGBOX_INFO','Display msgbox info'),(28,'DTD_VALIDATOR','DTD validator'),(29,'PGP_ENCRYPT_FILES','Encrypt files with PGP'),(30,'EVAL_FILES_METRICS','Evaluate files metrics'),(31,'EVAL_TABLE_CONTENT','Evaluate rows number in a table'),(32,'DummyJob','Example job (deprecated)'),(33,'ExitWithWarning','Exit with warning'),(34,'EXPORT_REPOSITORY','Export repository to XML file'),(35,'FILE_COMPARE','File compare'),(36,'FILE_EXISTS','File exists'),(37,'FTP_DELETE','FTP delete'),(38,'FTP','Get a file with FTP'),(39,'FTPS_GET','Get a file with FTPS'),(40,'SFTP','Get a file with SFTP'),(41,'GET_POP','Get mails (POP3/IMAP)'),(42,'HadoopCopyFilesPlugin','Hadoop copy files'),(43,'HadoopJobExecutorPlugin','Hadoop job executor '),(44,'HL7MLLPAcknowledge','HL7 MLLP acknowledge'),(45,'HL7MLLPInput','HL7 MLLP input'),(46,'HTTP','HTTP'),(47,'EVAL','JavaScript'),(48,'JOB','Job'),(49,'MAIL','Mail'),(50,'MAIL_VALIDATOR','Mail validator'),(51,'MOVE_FILES','Move files'),(52,'MS_ACCESS_BULK_LOAD','MS Access bulk load (deprecated)'),(53,'OozieJobExecutor','Oozie job executor'),(54,'PALO_CUBE_CREATE','Palo cube create (deprecated)'),(55,'PALO_CUBE_DELETE','Palo cube delete (deprecated)'),(56,'JobParerUnZip','Parer Unzip'),(57,'HadoopTransJobExecutorPlugin','Pentaho MapReduce'),(58,'HadoopPigScriptExecutorPlugin','Pig script executor'),(59,'PING','Ping a host'),(60,'COPY_MOVE_RESULT_FILENAMES','Process result filenames'),(61,'DATASOURCE_PUBLISH','Publish model'),(62,'FTP_PUT','Put a file with FTP'),(63,'SFTPPUT','Put a file with SFTP'),(64,'SYSLOG','Send information using syslog'),(65,'SEND_NAGIOS_PASSIVE_CHECK','Send Nagios passive check'),(66,'SNMP_TRAP','Send SNMP trap'),(67,'SET_VARIABLES','Set variables'),(68,'SHELL','Shell'),(69,'SIMPLE_EVAL','Simple evaluation'),(70,'SparkSubmit','Spark submit'),(71,'SPECIAL','Special entries'),(72,'SQL','SQL'),(73,'SqoopExport','Sqoop export'),(74,'SqoopImport','Sqoop import'),(75,'SUCCESS','Success'),(76,'TABLE_EXISTS','Table exists'),(77,'TALEND_JOB_EXEC','Talend job execution (deprecated)'),(78,'TELNET','Telnet a host'),(79,'TRANS','Transformation'),(80,'TransformationSummary','Transformation Summary'),(81,'TRUNCATE_TABLES','Truncate tables'),(82,'UNZIP','Unzip file'),(83,'FTPS_PUT','Upload files to FTPS'),(84,'PGP_VERIFY_FILES','Verify file signature with PGP'),(85,'DELAY','Wait for'),(86,'WAIT_FOR_FILE','Wait for file'),(87,'WAIT_FOR_SQL','Wait for SQL'),(88,'WRITE_TO_FILE','Write to file'),(89,'WRITE_TO_LOG','Write to log'),(90,'WriteToReport','Write to report'),(91,'XSD_VALIDATOR','XSD validator'),(92,'XSLT','XSL transformation'),(93,'ZIP_FILE','Zip file');
/*!40000 ALTER TABLE `R_JOBENTRY_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOB_ATTRIBUTE`
--

DROP TABLE IF EXISTS `R_JOB_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOB_ATTRIBUTE` (
  `ID_JOB_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` bigint(20) DEFAULT NULL,
  `VALUE_STR` mediumtext DEFAULT NULL,
  PRIMARY KEY (`ID_JOB_ATTRIBUTE`),
  UNIQUE KEY `IDX_JATT` (`ID_JOB`,`CODE`,`NR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOB_ATTRIBUTE`
--

LOCK TABLES `R_JOB_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `R_JOB_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_JOB_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOB_HOP`
--

DROP TABLE IF EXISTS `R_JOB_HOP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOB_HOP` (
  `ID_JOB_HOP` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_JOBENTRY_COPY_FROM` int(11) DEFAULT NULL,
  `ID_JOBENTRY_COPY_TO` int(11) DEFAULT NULL,
  `ENABLED` tinyint(1) DEFAULT NULL,
  `EVALUATION` tinyint(1) DEFAULT NULL,
  `UNCONDITIONAL` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_JOB_HOP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOB_HOP`
--

LOCK TABLES `R_JOB_HOP` WRITE;
/*!40000 ALTER TABLE `R_JOB_HOP` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_JOB_HOP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOB_LOCK`
--

DROP TABLE IF EXISTS `R_JOB_LOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOB_LOCK` (
  `ID_JOB_LOCK` bigint(20) NOT NULL,
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_USER` int(11) DEFAULT NULL,
  `LOCK_MESSAGE` mediumtext DEFAULT NULL,
  `LOCK_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_JOB_LOCK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOB_LOCK`
--

LOCK TABLES `R_JOB_LOCK` WRITE;
/*!40000 ALTER TABLE `R_JOB_LOCK` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_JOB_LOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_JOB_NOTE`
--

DROP TABLE IF EXISTS `R_JOB_NOTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_JOB_NOTE` (
  `ID_JOB` int(11) DEFAULT NULL,
  `ID_NOTE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_JOB_NOTE`
--

LOCK TABLES `R_JOB_NOTE` WRITE;
/*!40000 ALTER TABLE `R_JOB_NOTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_JOB_NOTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_LOG`
--

DROP TABLE IF EXISTS `R_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_LOG` (
  `ID_LOG` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `ID_LOGLEVEL` int(11) DEFAULT NULL,
  `LOGTYPE` varchar(255) DEFAULT NULL,
  `FILENAME` varchar(255) DEFAULT NULL,
  `FILEEXTENTION` varchar(255) DEFAULT NULL,
  `ADD_DATE` tinyint(1) DEFAULT NULL,
  `ADD_TIME` tinyint(1) DEFAULT NULL,
  `ID_DATABASE_LOG` int(11) DEFAULT NULL,
  `TABLE_NAME_LOG` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_LOG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_LOG`
--

LOCK TABLES `R_LOG` WRITE;
/*!40000 ALTER TABLE `R_LOG` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_LOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_LOGLEVEL`
--

DROP TABLE IF EXISTS `R_LOGLEVEL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_LOGLEVEL` (
  `ID_LOGLEVEL` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_LOGLEVEL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_LOGLEVEL`
--

LOCK TABLES `R_LOGLEVEL` WRITE;
/*!40000 ALTER TABLE `R_LOGLEVEL` DISABLE KEYS */;
INSERT INTO `R_LOGLEVEL` VALUES (1,'Error','Error'),(2,'Minimal','Minimal'),(3,'Basic','Basic'),(4,'Detailed','Detailed'),(5,'Debug','Debug'),(6,'Rowlevel','Row Level (very detailed)');
/*!40000 ALTER TABLE `R_LOGLEVEL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_NAMESPACE`
--

DROP TABLE IF EXISTS `R_NAMESPACE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_NAMESPACE` (
  `ID_NAMESPACE` bigint(20) NOT NULL,
  `NAME` text DEFAULT NULL,
  PRIMARY KEY (`ID_NAMESPACE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_NAMESPACE`
--

LOCK TABLES `R_NAMESPACE` WRITE;
/*!40000 ALTER TABLE `R_NAMESPACE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_NAMESPACE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_NOTE`
--

DROP TABLE IF EXISTS `R_NOTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_NOTE` (
  `ID_NOTE` bigint(20) NOT NULL,
  `VALUE_STR` mediumtext DEFAULT NULL,
  `GUI_LOCATION_X` int(11) DEFAULT NULL,
  `GUI_LOCATION_Y` int(11) DEFAULT NULL,
  `GUI_LOCATION_WIDTH` int(11) DEFAULT NULL,
  `GUI_LOCATION_HEIGHT` int(11) DEFAULT NULL,
  `FONT_NAME` mediumtext DEFAULT NULL,
  `FONT_SIZE` int(11) DEFAULT NULL,
  `FONT_BOLD` tinyint(1) DEFAULT NULL,
  `FONT_ITALIC` tinyint(1) DEFAULT NULL,
  `FONT_COLOR_RED` int(11) DEFAULT NULL,
  `FONT_COLOR_GREEN` int(11) DEFAULT NULL,
  `FONT_COLOR_BLUE` int(11) DEFAULT NULL,
  `FONT_BACK_GROUND_COLOR_RED` int(11) DEFAULT NULL,
  `FONT_BACK_GROUND_COLOR_GREEN` int(11) DEFAULT NULL,
  `FONT_BACK_GROUND_COLOR_BLUE` int(11) DEFAULT NULL,
  `FONT_BORDER_COLOR_RED` int(11) DEFAULT NULL,
  `FONT_BORDER_COLOR_GREEN` int(11) DEFAULT NULL,
  `FONT_BORDER_COLOR_BLUE` int(11) DEFAULT NULL,
  `DRAW_SHADOW` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_NOTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_NOTE`
--

LOCK TABLES `R_NOTE` WRITE;
/*!40000 ALTER TABLE `R_NOTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_NOTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_PARTITION`
--

DROP TABLE IF EXISTS `R_PARTITION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_PARTITION` (
  `ID_PARTITION` bigint(20) NOT NULL,
  `ID_PARTITION_SCHEMA` int(11) DEFAULT NULL,
  `PARTITION_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_PARTITION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_PARTITION`
--

LOCK TABLES `R_PARTITION` WRITE;
/*!40000 ALTER TABLE `R_PARTITION` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_PARTITION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_PARTITION_SCHEMA`
--

DROP TABLE IF EXISTS `R_PARTITION_SCHEMA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_PARTITION_SCHEMA` (
  `ID_PARTITION_SCHEMA` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DYNAMIC_DEFINITION` tinyint(1) DEFAULT NULL,
  `PARTITIONS_PER_SLAVE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_PARTITION_SCHEMA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_PARTITION_SCHEMA`
--

LOCK TABLES `R_PARTITION_SCHEMA` WRITE;
/*!40000 ALTER TABLE `R_PARTITION_SCHEMA` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_PARTITION_SCHEMA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_REPOSITORY_LOG`
--

DROP TABLE IF EXISTS `R_REPOSITORY_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_REPOSITORY_LOG` (
  `ID_REPOSITORY_LOG` bigint(20) NOT NULL,
  `REP_VERSION` varchar(255) DEFAULT NULL,
  `LOG_DATE` datetime DEFAULT NULL,
  `LOG_USER` varchar(255) DEFAULT NULL,
  `OPERATION_DESC` mediumtext DEFAULT NULL,
  PRIMARY KEY (`ID_REPOSITORY_LOG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_REPOSITORY_LOG`
--

LOCK TABLES `R_REPOSITORY_LOG` WRITE;
/*!40000 ALTER TABLE `R_REPOSITORY_LOG` DISABLE KEYS */;
INSERT INTO `R_REPOSITORY_LOG` VALUES (1,'5.0','2024-05-06 17:32:41','admin','Creation of the Kettle repository');
/*!40000 ALTER TABLE `R_REPOSITORY_LOG` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_SLAVE`
--

DROP TABLE IF EXISTS `R_SLAVE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_SLAVE` (
  `ID_SLAVE` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `HOST_NAME` varchar(255) DEFAULT NULL,
  `PORT` varchar(255) DEFAULT NULL,
  `WEB_APP_NAME` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `PROXY_HOST_NAME` varchar(255) DEFAULT NULL,
  `PROXY_PORT` varchar(255) DEFAULT NULL,
  `NON_PROXY_HOSTS` varchar(255) DEFAULT NULL,
  `MASTER` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_SLAVE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_SLAVE`
--

LOCK TABLES `R_SLAVE` WRITE;
/*!40000 ALTER TABLE `R_SLAVE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_SLAVE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_STEP`
--

DROP TABLE IF EXISTS `R_STEP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_STEP` (
  `ID_STEP` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext DEFAULT NULL,
  `ID_STEP_TYPE` int(11) DEFAULT NULL,
  `DISTRIBUTE` tinyint(1) DEFAULT NULL,
  `COPIES` int(11) DEFAULT NULL,
  `GUI_LOCATION_X` int(11) DEFAULT NULL,
  `GUI_LOCATION_Y` int(11) DEFAULT NULL,
  `GUI_DRAW` tinyint(1) DEFAULT NULL,
  `COPIES_STRING` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_STEP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_STEP`
--

LOCK TABLES `R_STEP` WRITE;
/*!40000 ALTER TABLE `R_STEP` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_STEP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_STEP_ATTRIBUTE`
--

DROP TABLE IF EXISTS `R_STEP_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_STEP_ATTRIBUTE` (
  `ID_STEP_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_STEP` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` bigint(20) DEFAULT NULL,
  `VALUE_STR` mediumtext DEFAULT NULL,
  PRIMARY KEY (`ID_STEP_ATTRIBUTE`),
  UNIQUE KEY `IDX_RSAT` (`ID_STEP`,`CODE`,`NR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_STEP_ATTRIBUTE`
--

LOCK TABLES `R_STEP_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `R_STEP_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_STEP_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_STEP_DATABASE`
--

DROP TABLE IF EXISTS `R_STEP_DATABASE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_STEP_DATABASE` (
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_STEP` int(11) DEFAULT NULL,
  `ID_DATABASE` int(11) DEFAULT NULL,
  KEY `IDX_RSD1` (`ID_TRANSFORMATION`),
  KEY `IDX_RSD2` (`ID_DATABASE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_STEP_DATABASE`
--

LOCK TABLES `R_STEP_DATABASE` WRITE;
/*!40000 ALTER TABLE `R_STEP_DATABASE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_STEP_DATABASE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_STEP_TYPE`
--

DROP TABLE IF EXISTS `R_STEP_TYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_STEP_TYPE` (
  `ID_STEP_TYPE` bigint(20) NOT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `HELPTEXT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_STEP_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_STEP_TYPE`
--

LOCK TABLES `R_STEP_TYPE` WRITE;
/*!40000 ALTER TABLE `R_STEP_TYPE` DISABLE KEYS */;
INSERT INTO `R_STEP_TYPE` VALUES (1,'ExitWithWarningStep','!ExitWithWarningStep.Name!','!ExitWithWarningStep.TooltipDesc!'),(2,'SanitizeXML','!SanitizeXML.Name!','!SanitizeXML.TooltipDesc!'),(3,'Abort','Abort','Abort a transformation'),(4,'CheckSum','Add a checksum','Add a checksum column for each input row'),(5,'Constant','Add constants','Add one or more constants to the input rows'),(6,'Sequence','Add sequence','Get the next value from an sequence'),(7,'FieldsChangeSequence','Add value fields changing sequence','Add sequence depending of fields value change.\nEach time value of at least one field change, PDI will reset sequence.'),(8,'AddXML','Add XML','Encode several fields into an XML fragment'),(9,'AnalyticQuery','Analytic query','Execute analytic queries over a sorted dataset (LEAD/LAG/FIRST/LAST)'),(10,'FieldMetadataAnnotation','Annotate stream','Add more details to describe data for published models used by the Streamlined Data Refinery.'),(11,'Append','Append streams','Append 2 streams in an ordered way'),(12,'AppendToFileStep','Append to file','Append to file.'),(13,'AutoDoc','Automatic documentation output','This step automatically generates documentation based on input in the form of a list of transformations and jobs'),(14,'AvroInputNew','Avro input','Reads data from Avro file'),(15,'AvroInput','Avro input (deprecated)','Reads data from an Avro file'),(16,'AvroOutput','Avro output','Writes data to an Avro file according to a mapping'),(17,'BlockUntilStepsFinish','Block this step until steps finish','Block this step until selected steps finish.'),(18,'BlockingStep','Blocking step','The Blocking step blocks all output until the very last row is received from the previous step.'),(19,'Calculator','Calculator','Create new fields by performing simple calculations'),(20,'DBProc','Call DB procedure','Get back information by calling a database procedure.'),(21,'CallEndpointStep','Call endpoint','Call an endpoint of the Pentaho Server.'),(22,'CassandraInput','Cassandra input','Reads data from a Cassandra table'),(23,'CassandraOutput','Cassandra output','Writes to a Cassandra table'),(24,'ChangeFileEncoding','Change file encoding','Change file encoding and create a new file'),(25,'FileLocked','Check if file is locked','Check if a file is locked by another process'),(26,'WebServiceAvailable','Check if webservice is available','Check if a webservice is available'),(27,'CloneRow','Clone row','Clone a row as many times as needed'),(28,'ClosureGenerator','Closure generator','This step allows you to generates a closure table using parent-child relationships.'),(29,'ColumnExists','Column exists','Check if a column exists'),(30,'CombinationLookup','Combination lookup/update','Update a junk dimension in a data warehouse.\nAlternatively, look up information in this dimension.\nThe primary key of a junk dimension are all the fields.'),(31,'ConcatFields','Concat fields','Concat fields together into a new field (similar to the Text File Output step)'),(32,'RowsToResult','Copy rows to result','Use this step to write rows to the executing job.\nThe information will then be passed to the next entry in this job.'),(33,'CouchDbInput','CouchDB input','Reads from a Couch DB view'),(34,'CreditCardValidator','Credit card validator','The Credit card validator step will help you tell:\n(1) if a credit card number is valid (uses LUHN10 (MOD-10) algorithm)\n(2) which credit card vendor handles that number\n(VISA, MasterCard, Diners Club, EnRoute, American Express (AMEX),...)'),(35,'CsvInput','CSV file input','Simple CSV file input'),(36,'DataGrid','Data grid','Enter rows of static data in a grid, usually for testing, reference or demo purpose'),(37,'Validator','Data validator','Validates passing data based on a set of rules'),(38,'DBJoin','Database join','Execute a database query using stream values as parameters'),(39,'DBLookup','Database lookup','Look up values in a database using field values'),(40,'CubeInput','De-serialize from file','Read rows of data from a data cube.'),(41,'Delay','Delay row','Output each input row after a delay'),(42,'Delete','Delete','Delete data in a database table based upon keys'),(43,'DetectEmptyStream','Detect empty stream','This step will output one empty row if input stream is empty\n(ie when input stream does not contain any row)'),(44,'DimensionLookup','Dimension lookup/update','Update a slowly changing dimension in a data warehouse.\nAlternatively, look up information in this dimension.'),(45,'Dummy','Dummy (do nothing)','This step type doesn\'t do anything.\nIt\'s useful however when testing things or in certain situations where you want to split streams.'),(46,'DynamicSQLRow','Dynamic SQL row','Execute dynamic SQL statement build in a previous field'),(47,'TypeExitEdi2XmlStep','EDI to XML','Converts Edi text to generic XML'),(48,'ElasticSearchBulk','Elasticsearch bulk insert (deprecated)','Performs bulk inserts into ElasticSearch'),(49,'MailInput','Email messages input','Read POP3/IMAP server and retrieve messages'),(50,'ShapeFileReader','ESRI shapefile reader','Reads shape file data from an ESRI shape file and linked DBF file'),(51,'MetaInject','ETL metadata injection','ETL metadata injection'),(52,'DummyStep','Example step (deprecated)','This is a plugin example step'),(53,'ExecProcess','Execute a process','Execute a process and return the result'),(54,'ExecSQLRow','Execute row SQL script','Execute SQL script extracted from a field\ncreated in a previous step.'),(55,'ExecSQL','Execute SQL script','Execute an SQL script, optionally parameterized using input rows'),(56,'FileExists','File exists','Check if a file exists'),(57,'FilterRows','Filter rows','Filter rows using simple equations'),(58,'FixedInput','Fixed file input','Fixed file input'),(59,'Formula','Formula','Calculate a formula using Pentaho\'s libformula'),(60,'FuzzyMatch','Fuzzy match','Finding approximate matches to a string using matching algorithms.\nRead a field from a main stream and output approximative value from lookup stream.'),(61,'RandomCCNumberGenerator','Generate random credit card numbers','Generate random valide (luhn check) credit card numbers'),(62,'RandomValue','Generate random value','Generate random value'),(63,'RowGenerator','Generate rows','Generate a number of empty or equal rows.'),(64,'getXMLData','Get data from XML','Get data from XML file by using XPath.\n This step also allows you to parse XML defined in a previous field.'),(65,'GetFileNames','Get file names','Get file names from the operating system and send them to the next step.'),(66,'FilesFromResult','Get files from result','This step allows you to read filenames used or generated in a previous entry in a job.'),(67,'GetFilesRowsCount','Get files rows count','Returns rows count for text files.'),(68,'GetSlaveSequence','Get ID from slave server','Retrieves unique IDs in blocks from a slave server.  The referenced sequence needs to be configured on the slave server in the XML configuration file.'),(69,'RecordsFromStream','Get records from stream','This step allows you to read records from a streaming step.'),(70,'GetRepositoryNames','Get repository names','Lists detailed information about transformations and/or jobs in a repository'),(71,'RowsFromResult','Get rows from result','This allows you to read rows from a previous entry in a job.'),(72,'GetSessionVariableStep','Get session variables','Get session variables from the current user session.'),(73,'GetSubFolders','Get subfolder names','Read a parent folder and return all subfolders'),(74,'SystemInfo','Get system info','Get information from the system like system date, arguments, etc.'),(75,'GetTableNames','Get table names','Get table names from database connection and send them to the next step'),(76,'GetVariable','Get variables','Determine the values of certain (environment or Kettle) variables and put them in field values.'),(77,'TypeExitGoogleAnalyticsInputStep','Google Analytics','Fetches data from google analytics account'),(78,'GPBulkLoader','Greenplum bulk loader (deprecated)','Greenplum bulk loader'),(79,'GPLoad','Greenplum load','Greenplum load'),(80,'GroupBy','Group by','Builds aggregates in a group by fashion.\nThis works only on a sorted input.\nIf the input is not sorted, only double consecutive rows are handled correctly.'),(81,'ParallelGzipCsvInput','GZIP CSV input','Parallel GZIP CSV file input reader'),(82,'HadoopFileInputPlugin','Hadoop file input','Process files from an HDFS location'),(83,'HadoopFileOutputPlugin','Hadoop file output','Create files in an HDFS location '),(84,'Hash','Hash','!Hash.TooltipDesc!'),(85,'HBaseInput','HBase input','Reads data from a HBase table according to a mapping '),(86,'HBaseOutput','HBase output','Writes data to an HBase table according to a mapping'),(87,'HBaseRowDecoder','HBase row decoder','Decodes an incoming key and HBase result object according to a mapping '),(88,'HL7Input','HL7 input','Reads and parses HL7 messages and outputs a series of values from the messages'),(89,'HTTP','HTTP client','Call a web service over HTTP by supplying a base URL by allowing parameters to be set dynamically'),(90,'HTTPPOST','HTTP post','Call a web service request over HTTP by supplying a base URL by allowing parameters to be set dynamically'),(91,'DetectLastRow','Identify last row in a stream','Last row will be marked'),(92,'IfNull','If field value is null','Sets a field value to a constant if it is null.'),(93,'InfobrightOutput','Infobright loader','Load data to an Infobright database table'),(94,'VectorWiseBulkLoader','Ingres VectorWise bulk loader','This step interfaces with the Ingres VectorWise Bulk Loader \"COPY TABLE\" command.'),(95,'Injector','Injector','Injector step to allow to inject rows into the transformation through the java API'),(96,'InsertUpdate','Insert / update','Update or insert rows in a database based upon keys.'),(97,'JavaFilter','Java filter','Filter rows using java code'),(98,'Jms2Consumer','JMS consumer','Consumes JMS streams'),(99,'Jms2Producer','JMS producer','Produces JMS streams'),(100,'JobExecutor','Job executor','This step executes a Pentaho Data Integration job, sets parameters and passes rows.'),(101,'JoinRows','Join rows (cartesian product)','The output of this step is the cartesian product of the input streams.\nThe number of rows is the multiplication of the number of rows in the input streams.'),(102,'JsonInput','JSON input','Extract relevant portions out of JSON structures (file or incoming field) and output rows'),(103,'JsonOutput','JSON output','Create JSON block and output it in a field or a file.'),(104,'KafkaConsumerInput','Kafka consumer','Consume messages from a Kafka topic'),(105,'KafkaProducerOutput','Kafka producer','Produce messages to a Kafka topic'),(106,'LDAPInput','LDAP input','Read data from LDAP host'),(107,'LDAPOutput','LDAP output','Perform Insert, upsert, update, add or delete operations on records based on their DN (Distinguished  Name).'),(108,'LDIFInput','LDIF input','Read data from LDIF files'),(109,'LoadFileInput','Load file content in memory','Load file content in memory'),(110,'LucidDBStreamingLoader','LucidDB streaming loader (deprecated)','Load data into LucidDB by using Remote Rows UDX.'),(111,'Mail','Mail','Send eMail.'),(112,'MailValidator','Mail validator','Check if an email address is valid.'),(113,'Mapping','Mapping (sub-transformation)','Run a mapping (sub-transformation), use MappingInput and MappingOutput to specify the fields interface'),(114,'MappingInput','Mapping input specification','Specify the input interface of a mapping'),(115,'MappingOutput','Mapping output specification','Specify the output interface of a mapping'),(116,'HadoopEnterPlugin','MapReduce input','Enter a Hadoop Mapper or Reducer transformation'),(117,'HadoopExitPlugin','MapReduce output','Exit a Hadoop Mapper or Reducer transformation '),(118,'MemoryGroupBy','Memory group by','Builds aggregates in a group by fashion.\nThis step doesn\'t require sorted input.'),(119,'MergeJoin','Merge join','Joins two streams on a given key and outputs a joined set. The input streams must be sorted on the join key'),(120,'MergeRows','Merge rows (diff)','Merge two streams of rows, sorted on a certain key.  The two streams are compared and the equals, changed, deleted and new rows are flagged.'),(121,'StepMetastructure','Metadata structure of stream','This is a step to read the metadata of the incoming stream.'),(122,'AccessInput','Microsoft Access input','Read data from a Microsoft Access file'),(123,'AccessOutput','Microsoft Access output','Stores records into an MS-Access database table.'),(124,'ExcelInput','Microsoft Excel input','Read data from Excel and OpenOffice Workbooks (XLS, XLSX, ODS).'),(125,'ExcelOutput','Microsoft Excel output','Stores records into an Excel (XLS) document with formatting information.'),(126,'TypeExitExcelWriterStep','Microsoft Excel writer','Writes or appends data to an Excel file'),(127,'MimeDiscovererStep','Mime discoverer','!MimeDiscovererStep.TooltipDesc!'),(128,'ScriptValueMod','Modified JavaScript value','This is a modified plugin for the Scripting Values with improved interface and performance.\nWritten & donated to open source by Martin Lange, Proconis : http://www.proconis.de'),(129,'MondrianInput','Mondrian input','Execute and retrieve data using an MDX query against a Pentaho Analyses OLAP server (Mondrian)'),(130,'MonetDBAgileMart','MonetDB Agile Mart','Load data into MonetDB for Agile BI use cases'),(131,'MonetDBBulkLoader','MonetDB bulk loader','Load data into MonetDB by using their bulk load command in streaming mode.'),(132,'MongoDbInput','MongoDB input','Reads from a Mongo DB collection'),(133,'MongoDbOutput','MongoDB output','Writes to a Mongo DB collection'),(134,'MQTTConsumer','MQTT consumer','Subscribes and streams an MQTT Topic'),(135,'MQTTProducer','MQTT producer','Produce messages to a MQTT Topic'),(136,'MultiwayMergeJoin','Multiway merge join','Multiway merge join'),(137,'MySQLBulkLoader','MySQL bulk loader','MySQL bulk loader step, loading data over a named pipe (not available on MS Windows)'),(138,'NullIf','Null if','Sets a field value to null if it is equal to a constant value'),(139,'NumberRange','Number range','Create ranges based on numeric field'),(140,'OlapInput','OLAP input','Execute and retrieve data using an MDX query against any XML/A OLAP datasource using olap4j'),(141,'OraBulkLoader','Oracle bulk loader','Use Oracle bulk loader to load data'),(142,'OrcInput','ORC input','Reads data from ORC file'),(143,'OrcOutput','ORC output','Writes data to an Orc file according to a mapping'),(144,'StepsMetrics','Output steps metrics','Return metrics for one or several steps'),(145,'PaloCellInput','Palo cell input (deprecated)','Reads data from a defined Palo Cube '),(146,'PaloCellOutput','Palo cell output (deprecated)','Writes data to a defined Palo Cube'),(147,'PaloDimInput','Palo dim input (deprecated)','Reads data from a defined Palo Dimension'),(148,'PaloDimOutput','Palo dim output (deprecated)','Writes data to defined Palo Dimension'),(149,'ParquetInput','Parquet input','Reads data from a Parquet file.'),(150,'ParquetOutput','Parquet output','Writes data to a Parquet file according to a mapping.'),(151,'PentahoReportingOutput','Pentaho reporting output','Executes an existing report (PRPT)'),(152,'PGPDecryptStream','PGP decrypt stream','Decrypt data stream with PGP'),(153,'PGPEncryptStream','PGP encrypt stream','Encrypt data stream with PGP'),(154,'PGBulkLoader','PostgreSQL bulk loader','PostgreSQL Bulk Loader'),(155,'PrioritizeStreams','Prioritize streams','Prioritize streams in an order way.'),(156,'ProcessFiles','Process files','Process one file per row (copy or move or delete).\nThis step only accept filename in input.'),(157,'PropertyOutput','Properties output','Write data to properties file'),(158,'PropertyInput','Property input','Read data (key, value) from properties files.'),(159,'RegexEval','Regex evaluation','Regular expression Evaluation\nThis step uses a regular expression to evaluate a field. It can also extract new fields out of an existing field with capturing groups.'),(160,'ReplaceString','Replace in string','Replace all occurences a word in a string with another word.'),(161,'ReservoirSampling','Reservoir sampling','[Transform] Samples a fixed number of rows from the incoming stream'),(162,'Rest','REST client','Consume RESTfull services.\nREpresentational State Transfer (REST) is a key design idiom that embraces a stateless client-server\narchitecture in which the web services are viewed as resources and can be identified by their URLs'),(163,'Denormaliser','Row denormaliser','Denormalises rows by looking up key-value pairs and by assigning them to new fields in the output rows.\nThis method aggregates and needs the input rows to be sorted on the grouping fields'),(164,'Flattener','Row flattener','Flattens consecutive rows based on the order in which they appear in the input stream'),(165,'Normaliser','Row normaliser','De-normalised information can be normalised using this step type.'),(166,'RssInput','RSS input','Read RSS feeds'),(167,'RssOutput','RSS output','Read RSS stream.'),(168,'RuleAccumulator','Rules accumulator','Rules accumulator step'),(169,'RuleExecutor','Rules executor','Rules executor step'),(170,'SSH','Run SSH commands','Run SSH commands and returns result.'),(171,'S3CSVINPUT','S3 CSV input','Is capable of reading CSV data stored on Amazon S3 in parallel'),(172,'S3FileOutputPlugin','S3 file output','Create files in an S3 location'),(173,'SalesforceDelete','Salesforce delete','Delete records in Salesforce module.'),(174,'SalesforceInput','Salesforce input','Extract data from Salesforce'),(175,'SalesforceInsert','Salesforce insert','Insert records in Salesforce module.'),(176,'SalesforceUpdate','Salesforce update','Update records in Salesforce module.'),(177,'SalesforceUpsert','Salesforce upsert','Insert or update records in Salesforce module.'),(178,'SampleRows','Sample rows','Filter rows based on the line number.'),(179,'SAPINPUT','SAP input (deprecated)','Read data from SAP ERP, optionally with parameters'),(180,'SASInput','SAS input','This step reads files in sas7bdat (SAS) native format'),(181,'Script','Script (deprecated)','Calculate values by scripting in Ruby, Python, Groovy, JavaScript, ... (JSR-223)'),(182,'SecretKeyGenerator','Secret key generator','Generate secret key for algorithms such as DES, AES, TripleDES.'),(183,'SelectValues','Select values','Select or remove fields in a row.\nOptionally, set the field meta-data: type, length and precision.'),(184,'SyslogMessage','Send message to syslog','Send message to syslog server'),(185,'CubeOutput','Serialize to file','Write rows of data to a data cube'),(186,'SetValueField','Set field value','Set value of a field with another value field'),(187,'SetValueConstant','Set field value to a constant','Set value of a field to a constant'),(188,'FilesToResult','Set files in result','This step allows you to set filenames in the result of this transformation.\nSubsequent job entries can then use this information.'),(189,'SetSessionVariableStep','Set session variables','Set session variables in the current user session.'),(190,'SetVariable','Set variables','Set environment variables based on a single input row.'),(191,'SFTPPut','SFTP put','Upload a file or a stream file to remote host via SFTP'),(192,'CreateSharedDimensions','Shared dimension','Create shared dimensions for use with Streamlined Data Refinery.'),(193,'SimpleMapping','Simple mapping (sub-transformation)','Run a mapping (sub-transformation), use MappingInput and MappingOutput to specify the fields interface.  This is the simplified version only allowing one input and one output data set.'),(194,'SingleThreader','Single threader','Executes a transformation snippet in a single thread.  You need a standard mapping or a transformation with an Injector step where data from the parent transformation will arive in blocks.'),(195,'SocketReader','Socket reader','Socket reader.  A socket client that connects to a server (Socket Writer step).'),(196,'SocketWriter','Socket writer','Socket writer.  A socket server that can send rows of data to a socket reader.'),(197,'SortRows','Sort rows','Sort rows based upon field values (ascending or descending)'),(198,'SortedMerge','Sorted merge','Sorted merge'),(199,'SplitFieldToRows3','Split field to rows','Splits a single string field by delimiter and creates a new row for each split term'),(200,'FieldSplitter','Split fields','When you want to split a single field into more then one, use this step type.'),(201,'SQLFileOutput','SQL file output','Output SQL INSERT statements to file'),(202,'SSTableOutput','SSTable output','Writes to a filesystem directory as a Cassandra SSTable'),(203,'StreamLookup','Stream lookup','Look up values coming from another stream in the transformation.'),(204,'StringOperations','String operations','Apply certain operations like trimming, padding and others to string value.'),(205,'StringCut','Strings cut','Strings cut (substring).'),(206,'SwitchCase','Switch / case','Switch a row to a certain target step based on the case value in a field.'),(207,'SymmetricCryptoTrans','Symmetric cryptography','Encrypt or decrypt a string using symmetric encryption.\nAvailable algorithms are DES, AES, TripleDES.'),(208,'SynchronizeAfterMerge','Synchronize after merge','This step perform insert/update/delete in one go based on the value of a field.'),(209,'TableAgileMart','Table Agile Mart','Load data into a table for Agile BI use cases'),(210,'TableCompare','Table compare','Compares 2 tables and gives back a list of differences'),(211,'TableExists','Table exists','Check if a table exists on a specified connection'),(212,'TableInput','Table input','Read information from a database table.'),(213,'TableOutput','Table output','Write information to a database table'),(214,'TeraFast','Teradata Fastload bulk loader','The Teradata Fastload bulk loader'),(215,'TeraDataBulkLoader','Teradata TPT bulk loader','Teradata TPT bulkloader, using tbuild command'),(216,'TextFileInput','Text file input','Read data from a text file in several formats.\nThis data can then be passed on to the next step(s)...'),(217,'OldTextFileInput','Text file input (deprecated)','Read data from a text file in several formats.\nThis data can then be passed on to the next step(s)...'),(218,'TextFileOutput','Text file output','Write rows to a text file.'),(219,'TextFileOutputLegacy','Text file output (deprecated)','Write rows to a text file.'),(220,'TransExecutor','Transformation executor','This step executes a Pentaho Data Integration transformation, sets parameters and passes rows.'),(221,'Unique','Unique rows','Remove double rows and leave only unique occurrences.\nThis works only on a sorted input.\nIf the input is not sorted, only double consecutive rows are handled correctly.'),(222,'UniqueRowsByHashSet','Unique rows (HashSet)','Remove double rows and leave only unique occurrences by using a HashSet.'),(223,'UnivariateStats','Univariate statistics','This step computes some simple stats based on a single input field'),(224,'Update','Update','Update data in a database table based upon keys'),(225,'UserDefinedJavaClass','User defined Java class','This step allows you to program a step using Java code'),(226,'Janino','User defined Java expression','Calculate the result of a Java Expression using Janino'),(227,'ValueMapper','Value mapper','Maps values of a certain field from one value to another'),(228,'VerticaBulkLoader','Vertica bulk loader','Bulk load data into a Vertica database table'),(229,'WebServiceLookup','Web services lookup','Look up information using web services (WSDL)'),(230,'WriteToLog','Write to log','Write data to log'),(231,'WriteToReportStep','Write to report','!WriteToReportStep.TooltipDesc!'),(232,'XBaseInput','XBase input','Reads records from an XBase type of database file (DBF)'),(233,'XMLInputStream','XML input stream (StAX)','This step is capable of processing very large and complex XML files very fast.'),(234,'XMLJoin','XML join','Joins a stream of XML-Tags into a target XML string'),(235,'XMLOutput','XML output','Write data to an XML file'),(236,'XSDValidator','XSD validator','Validate XML source (files or streams) against XML Schema Definition.'),(237,'XSLT','XSL transformation','Make an XSL transformation'),(238,'YamlInput','YAML input ','Read YAML source (file or stream) parse them and convert them to rows and writes these to one or more output.'),(239,'ZipFile','Zip file','Zip a file.\nFilename will be extracted from incoming stream.');
/*!40000 ALTER TABLE `R_STEP_TYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_TRANSFORMATION`
--

DROP TABLE IF EXISTS `R_TRANSFORMATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_TRANSFORMATION` (
  `ID_TRANSFORMATION` bigint(20) NOT NULL,
  `ID_DIRECTORY` int(11) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` mediumtext DEFAULT NULL,
  `EXTENDED_DESCRIPTION` mediumtext DEFAULT NULL,
  `TRANS_VERSION` varchar(255) DEFAULT NULL,
  `TRANS_STATUS` int(11) DEFAULT NULL,
  `ID_STEP_READ` int(11) DEFAULT NULL,
  `ID_STEP_WRITE` int(11) DEFAULT NULL,
  `ID_STEP_INPUT` int(11) DEFAULT NULL,
  `ID_STEP_OUTPUT` int(11) DEFAULT NULL,
  `ID_STEP_UPDATE` int(11) DEFAULT NULL,
  `ID_DATABASE_LOG` int(11) DEFAULT NULL,
  `TABLE_NAME_LOG` varchar(255) DEFAULT NULL,
  `USE_BATCHID` tinyint(1) DEFAULT NULL,
  `USE_LOGFIELD` tinyint(1) DEFAULT NULL,
  `ID_DATABASE_MAXDATE` int(11) DEFAULT NULL,
  `TABLE_NAME_MAXDATE` varchar(255) DEFAULT NULL,
  `FIELD_NAME_MAXDATE` varchar(255) DEFAULT NULL,
  `OFFSET_MAXDATE` double DEFAULT NULL,
  `DIFF_MAXDATE` double DEFAULT NULL,
  `CREATED_USER` varchar(255) DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `MODIFIED_USER` varchar(255) DEFAULT NULL,
  `MODIFIED_DATE` datetime DEFAULT NULL,
  `SIZE_ROWSET` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANSFORMATION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_TRANSFORMATION`
--

LOCK TABLES `R_TRANSFORMATION` WRITE;
/*!40000 ALTER TABLE `R_TRANSFORMATION` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_TRANSFORMATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_TRANS_ATTRIBUTE`
--

DROP TABLE IF EXISTS `R_TRANS_ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_TRANS_ATTRIBUTE` (
  `ID_TRANS_ATTRIBUTE` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `NR` int(11) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `VALUE_NUM` bigint(20) DEFAULT NULL,
  `VALUE_STR` mediumtext DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_ATTRIBUTE`),
  UNIQUE KEY `IDX_TATT` (`ID_TRANSFORMATION`,`CODE`,`NR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_TRANS_ATTRIBUTE`
--

LOCK TABLES `R_TRANS_ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `R_TRANS_ATTRIBUTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_TRANS_ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_TRANS_CLUSTER`
--

DROP TABLE IF EXISTS `R_TRANS_CLUSTER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_TRANS_CLUSTER` (
  `ID_TRANS_CLUSTER` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_CLUSTER` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_CLUSTER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_TRANS_CLUSTER`
--

LOCK TABLES `R_TRANS_CLUSTER` WRITE;
/*!40000 ALTER TABLE `R_TRANS_CLUSTER` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_TRANS_CLUSTER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_TRANS_HOP`
--

DROP TABLE IF EXISTS `R_TRANS_HOP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_TRANS_HOP` (
  `ID_TRANS_HOP` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_STEP_FROM` int(11) DEFAULT NULL,
  `ID_STEP_TO` int(11) DEFAULT NULL,
  `ENABLED` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_HOP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_TRANS_HOP`
--

LOCK TABLES `R_TRANS_HOP` WRITE;
/*!40000 ALTER TABLE `R_TRANS_HOP` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_TRANS_HOP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_TRANS_LOCK`
--

DROP TABLE IF EXISTS `R_TRANS_LOCK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_TRANS_LOCK` (
  `ID_TRANS_LOCK` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_USER` int(11) DEFAULT NULL,
  `LOCK_MESSAGE` mediumtext DEFAULT NULL,
  `LOCK_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_LOCK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_TRANS_LOCK`
--

LOCK TABLES `R_TRANS_LOCK` WRITE;
/*!40000 ALTER TABLE `R_TRANS_LOCK` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_TRANS_LOCK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_TRANS_NOTE`
--

DROP TABLE IF EXISTS `R_TRANS_NOTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_TRANS_NOTE` (
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_NOTE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_TRANS_NOTE`
--

LOCK TABLES `R_TRANS_NOTE` WRITE;
/*!40000 ALTER TABLE `R_TRANS_NOTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_TRANS_NOTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_TRANS_PARTITION_SCHEMA`
--

DROP TABLE IF EXISTS `R_TRANS_PARTITION_SCHEMA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_TRANS_PARTITION_SCHEMA` (
  `ID_TRANS_PARTITION_SCHEMA` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_PARTITION_SCHEMA` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_PARTITION_SCHEMA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_TRANS_PARTITION_SCHEMA`
--

LOCK TABLES `R_TRANS_PARTITION_SCHEMA` WRITE;
/*!40000 ALTER TABLE `R_TRANS_PARTITION_SCHEMA` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_TRANS_PARTITION_SCHEMA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_TRANS_SLAVE`
--

DROP TABLE IF EXISTS `R_TRANS_SLAVE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_TRANS_SLAVE` (
  `ID_TRANS_SLAVE` bigint(20) NOT NULL,
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_SLAVE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_TRANS_SLAVE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_TRANS_SLAVE`
--

LOCK TABLES `R_TRANS_SLAVE` WRITE;
/*!40000 ALTER TABLE `R_TRANS_SLAVE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_TRANS_SLAVE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_TRANS_STEP_CONDITION`
--

DROP TABLE IF EXISTS `R_TRANS_STEP_CONDITION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_TRANS_STEP_CONDITION` (
  `ID_TRANSFORMATION` int(11) DEFAULT NULL,
  `ID_STEP` int(11) DEFAULT NULL,
  `ID_CONDITION` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_TRANS_STEP_CONDITION`
--

LOCK TABLES `R_TRANS_STEP_CONDITION` WRITE;
/*!40000 ALTER TABLE `R_TRANS_STEP_CONDITION` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_TRANS_STEP_CONDITION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_USER`
--

DROP TABLE IF EXISTS `R_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_USER` (
  `ID_USER` bigint(20) NOT NULL,
  `LOGIN` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `ENABLED` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_USER`
--

LOCK TABLES `R_USER` WRITE;
/*!40000 ALTER TABLE `R_USER` DISABLE KEYS */;
INSERT INTO `R_USER` VALUES (1,'admin','2be98afc86aa7f2e4cb79ce71da9fa6d4','Administrator','User manager',1),(2,'guest','2be98afc86aa7f2e4cb79ce77cb97bcce','Guest account','Read-only guest account',1);
/*!40000 ALTER TABLE `R_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_VALUE`
--

DROP TABLE IF EXISTS `R_VALUE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_VALUE` (
  `ID_VALUE` bigint(20) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `VALUE_TYPE` varchar(255) DEFAULT NULL,
  `VALUE_STR` varchar(255) DEFAULT NULL,
  `IS_NULL` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_VALUE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_VALUE`
--

LOCK TABLES `R_VALUE` WRITE;
/*!40000 ALTER TABLE `R_VALUE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_VALUE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_VERSION`
--

DROP TABLE IF EXISTS `R_VERSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_VERSION` (
  `ID_VERSION` bigint(20) NOT NULL,
  `MAJOR_VERSION` int(11) DEFAULT NULL,
  `MINOR_VERSION` int(11) DEFAULT NULL,
  `UPGRADE_DATE` datetime DEFAULT NULL,
  `IS_UPGRADE` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_VERSION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_VERSION`
--

LOCK TABLES `R_VERSION` WRITE;
/*!40000 ALTER TABLE `R_VERSION` DISABLE KEYS */;
INSERT INTO `R_VERSION` VALUES (1,5,0,'2024-05-06 17:32:41',0);
/*!40000 ALTER TABLE `R_VERSION` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-06 17:34:16
