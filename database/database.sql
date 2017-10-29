-- MySQL dump 10.16  Distrib 10.1.28-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: customers
-- ------------------------------------------------------
-- Server version	10.1.28-MariaDB

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `S1No` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fullName` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Email` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `userName` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(3000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `isActivated` int(10) unsigned DEFAULT NULL,
  `admins` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`S1No`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Thomas F','thomas.fossaert@epitech.eu','Panzani','2aefc34200a294a3cc7db81b43a81873',1,'admins'),(2,'Robin Grattepanche','robin.grattepanche@epitech.eu','Grodin','2aefc34200a294a3cc7db81b43a81873',1,'admins'),(3,'Jean patate','patate@frite.com','pommefrite','945e9f0b4e381b13aa70b94b89a28709',1,NULL),(5,'Gilbert','gilbert.delafontaine@gmail.com','Gilette','0cf31ea5c7b6ed1e68ff2efce0bf2cde',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-29 16:33:06
