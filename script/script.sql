-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `authority` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES (1,'ROLE_Administrator'),(2,'ROLE_Magazynier'),(3,'ROLE_Logistyk'),(4,'ROLE_Manager');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorities_users`
--

DROP TABLE IF EXISTS `authorities_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorities_users` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_authorities_users_users_idx` (`user_id`) /*!80000 INVISIBLE */,
  KEY `fk_authorities_users_authorities_idx` (`role_id`),
  CONSTRAINT `fk_authorities_users_authorities` FOREIGN KEY (`role_id`) REFERENCES `authorities` (`id`),
  CONSTRAINT `fk_authorities_users_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities_users`
--

LOCK TABLES `authorities_users` WRITE;
/*!40000 ALTER TABLE `authorities_users` DISABLE KEYS */;
INSERT INTO `authorities_users` VALUES (21,1),(34,1),(21,2),(31,2),(21,3),(32,3),(21,4),(33,4);
/*!40000 ALTER TABLE `authorities_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `component`
--

DROP TABLE IF EXISTS `component`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `component` (
  `id` int NOT NULL AUTO_INCREMENT,
  `part_number` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `parameters` varchar(45) DEFAULT NULL,
  `number` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `component`
--

LOCK TABLES `component` WRITE;
/*!40000 ALTER TABLE `component` DISABLE KEYS */;
INSERT INTO `component` VALUES (21,'GRM1555C1H1R2WA01D','kondensator','0,01 uF',1000),(22,'CL05C331JB5NNNC','kondensator','330 pF',5),(23,'SMD0603-10K-1%','rezystor','10 kOhm',300),(24,'HMC472ALP4ETR','tłumik','',50),(25,'06035C104K4T2A','kondensator','100 nF',450),(27,'T491A106M020AT','kondensator','10 uF',50),(28,'SC1H476M6L07KVR','kondensator','47 uF',300),(29,'1295311','osłona','',10),(31,'ABC123','rezystor','100 kOhm',4000),(34,'DEF456','kondensator','50 uF',1000),(36,'0402N102G500CT','kondensator','100 uF',3000);
/*!40000 ALTER TABLE `component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Elektronicy','elektronicy@gmail.com','123456789'),(3,'Inżyniery','inzyniery@gmail.com','987654324');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` varchar(45) DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `stage` varchar(45) DEFAULT NULL,
  `completed` int DEFAULT NULL,
  `price` float DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `bom_path` varchar(100) DEFAULT NULL,
  `to_buy_path` varchar(100) DEFAULT NULL,
  `receive_date` varchar(45) DEFAULT NULL,
  `deadline` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_project1_idx` (`project_id`),
  KEY `fk_order_customer1_idx` (`customer_id`),
  KEY `employee_id_idx` (`employee_id`),
  CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `employee_id` FOREIGN KEY (`employee_id`) REFERENCES `users` (`id`),
  CONSTRAINT `project_id` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (55,'A/02/2021',200,'Przyjęto zlecenie',50,40000,56,3,21,'D:\\MagazineSystemPliki\\order\\pilot_BOM.xlsx','D:\\MagazineSystemPliki\\order\\A_02_2021Lista.xlsx','03.02.2021','16.04.2021'),(61,'B/02/2021',50,'Przyjęto zlecenie',10,25000,56,1,21,'D:\\MagazineSystemPliki\\order\\pilot_BOM.xlsx','D:\\MagazineSystemPliki\\order\\B_02_2021Lista.xlsx','11.02.2021','31.03.2021'),(62,'C/02/2021',200,'Przyjęto zlecenie',0,15000,62,3,21,'D:\\MagazineSystemPliki\\order\\konsola_BOM.xlsx','D:\\MagazineSystemPliki\\order\\C_02_2021Lista.xlsx','13.02.2021','31.03.2021'),(65,'A/03/2021',200,'Przyjęto zlecenie',0,35000,69,3,21,'D:\\MagazineSystemPliki\\order\\radio_BOM.xlsx','D:\\MagazineSystemPliki\\order\\A_03_2021Lista.xlsx','02.03.2021','12.05.2021'),(66,'02/03/2021',100,'Przyjęto zlecenie',0,42800,62,1,21,'D:\\MagazineSystemPliki\\order\\konsola_BOM.xlsx',NULL,'05.03.2021','10.06.2021');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pcb`
--

DROP TABLE IF EXISTS `pcb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pcb` (
  `id` int NOT NULL AUTO_INCREMENT,
  `producer` varchar(45) DEFAULT NULL,
  `file_path` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pcb`
--

LOCK TABLES `pcb` WRITE;
/*!40000 ALTER TABLE `pcb` DISABLE KEYS */;
INSERT INTO `pcb` VALUES (111,'PCB pilot','D:\\MagazineSystemPliki\\pcb\\paczka.zip'),(113,'PCB konsola','D:\\MagazineSystemPliki\\pcb\\paczka.zip'),(119,'PCB radio','D:\\MagazineSystemPliki\\pcb\\paczka.zip');
/*!40000 ALTER TABLE `pcb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `THTcomments` varchar(200) DEFAULT NULL,
  `SMTcomments` varchar(200) DEFAULT NULL,
  `pcb_id` int DEFAULT NULL,
  `doc_path` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pcb_id_idx` (`pcb_id`),
  CONSTRAINT `pcb_id` FOREIGN KEY (`pcb_id`) REFERENCES `pcb` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (56,'Pilot','Uwaga Pilot','Uwaga THT Pilot','Uwaga SMT Pilot',111,'D:\\MagazineSystemPliki\\project\\pilot_dok.pdf'),(62,'Konsola','Uwaga Konsola','Uwaga THT Konsola','Uwaga SMT Konsola',113,'D:\\MagazineSystemPliki\\project\\konsola_dok.pdf'),(69,'Radio','Uwaga radio','Uwaga THT radio','Uwaga SMT radio',119,'D:\\MagazineSystemPliki\\project\\radio_dok.pdf');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` char(68) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (21,'darek','$2a$10$q2CjqstwdvwCciNMWWTeMu/ZnkiS97Jynt13Uh2NuFovxHnbdqqbG',0,'Dariusz','Piotrkowski','darek@gmail.com'),(31,'userm','$2a$10$Tby4cDtO45k2wUNdNAE6UOpCu.7bVmGUShSeS16Epqr660oe2wHKq',0,'Jan','Kowalski','jkowalski@firma.com'),(32,'userl','$2a$10$X4WsGhPp7K1WNH93qCptLOI3UgbKPGehYcn.T0yT9OvEzrNyq/rAW',0,'Paweł','Nowak','pnowak@firma.com'),(33,'userman','$2a$10$rrkntfJ6MdPYLorjNakAu.u.cH4gSQ984bEZo1dn92jg7dDtWwZNq',0,'Wojciech','Wiśniewski','wwisniewski@firma.com'),(34,'usera','$2a$10$a3jZn.bT.JGEoJ3csNpXYutZY/kO3KtJOQllj/mgrfv6TNL2yuHvy',0,'Adam','Nowakowski','anowakowski@firma.com');
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

-- Dump completed on 2021-01-26 12:48:45
