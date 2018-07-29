CREATE DATABASE  IF NOT EXISTS `url_shortner` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `url_shortner`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: url_shortner
-- ------------------------------------------------------
-- Server version	5.7.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `shortened_url`
--

DROP TABLE IF EXISTS `shortened_url`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shortened_url` (
  `ID` smallint(6) NOT NULL,
  `ORIGNAL_URL` varchar(100) NOT NULL,
  `SHORT_URL` varchar(100) NOT NULL,
  `CREATION_DATE` datetime DEFAULT NULL,
  `EXPIRY_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shortened_url`
--

LOCK TABLES `shortened_url` WRITE;
/*!40000 ALTER TABLE `shortened_url` DISABLE KEYS */;
INSERT INTO `shortened_url` VALUES (1,'http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/HomeWebsocket/WebsocketHome.html','http://localhost:8080/UrlShortening/cc/url/1532538376020','2018-08-25 22:06:16','2018-08-06'),(2,'https://docs.oracle.com/javaee/6/tutorial/doc/bnafu.html','http://localhost:8080/UrlShortening/cc/url/1532538754009','2018-07-25 22:12:34','2018-08-27'),(3,'https://docs.oracle.com/cd/E19776-01/820-4867/ghrpv/index.html','http://localhost:8080/UrlShortening/cc/url/1532782601584','2018-07-25 22:16:35','2018-08-27'),(4,'https://www.facebook.com/groups/Javagroup123/','http://localhost:8080/UrlShortening/cc/url/1532630671857','2018-07-26 23:44:32','2018-09-06');
/*!40000 ALTER TABLE `shortened_url` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `url_click`
--

DROP TABLE IF EXISTS `url_click`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `url_click` (
  `ID` smallint(6) NOT NULL AUTO_INCREMENT,
  `SHORTENED_URL_ID` smallint(6) NOT NULL,
  `TOTAL_CLICKS` mediumint(9) DEFAULT '0',
  `CLICK_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `SHORTENED_URL_ID` (`SHORTENED_URL_ID`),
  CONSTRAINT `url_click_ibfk_1` FOREIGN KEY (`SHORTENED_URL_ID`) REFERENCES `shortened_url` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `url_click`
--

LOCK TABLES `url_click` WRITE;
/*!40000 ALTER TABLE `url_click` DISABLE KEYS */;
INSERT INTO `url_click` VALUES (1,1,4,'2018-07-26'),(2,1,17,'2018-07-29'),(3,3,1,'2018-07-27'),(4,4,1,'2018-07-27'),(5,2,9,'2018-07-27'),(6,1,5,'2018-07-27'),(7,1,13,'2018-07-28'),(8,1,9,'2018-07-30'),(9,1,19,'2018-07-25'),(10,1,58,'2018-07-24'),(11,1,1,'2018-07-23'),(12,1,28,'2018-07-22'),(13,1,12,'2018-07-21'),(14,1,15,'2018-07-20'),(15,1,20,'2018-07-31'),(16,1,1,'2018-07-19'),(17,1,20,'2018-07-18'),(18,1,1,'2018-08-01'),(19,4,1,'2018-07-28'),(20,2,1,'2018-07-28');
/*!40000 ALTER TABLE `url_click` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'url_shortner'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-29 16:21:22
