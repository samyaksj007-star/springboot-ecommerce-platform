-- MySQL dump 10.13  Distrib 8.4.5, for macos15 (x86_64)
--
-- Host: localhost    Database: SAKURA_SCENTED
-- ------------------------------------------------------
-- Server version	8.4.5

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `col_address_dtl`
--

DROP TABLE IF EXISTS `col_address_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `col_address_dtl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_line` varchar(255) NOT NULL,
  `city` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `is_primary` bit(1) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `postal_code` varchar(20) NOT NULL,
  `state` varchar(100) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK327vqh68mn7p15gfui5ctroy3` (`user_id`,`address_line`,`postal_code`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `col_address_dtl`
--

LOCK TABLES `col_address_dtl` WRITE;
/*!40000 ALTER TABLE `col_address_dtl` DISABLE KEYS */;
INSERT INTO `col_address_dtl` VALUES (36,'A44 ADA Bank Colony','Aligarh','India','2025-09-14 23:31:28.312067','Samyak',_binary '','Jain','08755781117','202001','Uttar Pradesh','2025-09-14 23:31:28.312080',25),(38,'A44 ADA Bank','Aligarh','India','2025-09-14 23:31:57.559313','adsnhi',_binary '\0','Jain','08755781117','202001','Uttar Pradesh','2025-09-14 23:31:57.559321',25);
/*!40000 ALTER TABLE `col_address_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `col_cart_dtl`
--

DROP TABLE IF EXISTS `col_cart_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `col_cart_dtl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `discount_applied` int DEFAULT NULL,
  `price_at_added` decimal(10,2) NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `col_cart_dtl`
--

LOCK TABLES `col_cart_dtl` WRITE;
/*!40000 ALTER TABLE `col_cart_dtl` DISABLE KEYS */;
INSERT INTO `col_cart_dtl` VALUES (1,'2025-09-06 22:32:51.810976',0,999.00,53,1,NULL,'INACTIVE',999.00,'2025-09-06 22:37:21.365040',25),(2,'2025-09-06 22:34:35.990636',0,123.00,2,8,NULL,'INACTIVE',984.00,'2025-09-06 22:37:42.949913',25),(3,'2025-09-06 22:37:56.577977',0,123.00,2,1,NULL,'INACTIVE',123.00,'2025-09-07 02:39:54.693065',25),(4,'2025-09-07 02:39:57.714141',0,999.00,53,1,NULL,'INACTIVE',999.00,'2025-09-07 02:52:08.661371',25),(5,'2025-09-07 02:40:16.615239',0,123.00,2,8,NULL,'INACTIVE',984.00,'2025-09-07 02:40:26.822571',25),(6,'2025-09-07 02:52:16.242523',0,999.00,53,1,NULL,'INACTIVE',999.00,'2025-09-07 02:53:57.056973',25),(7,'2025-09-07 02:52:50.120337',0,123.00,2,1,NULL,'ACTIVE',123.00,'2025-09-11 13:59:12.112819',25),(8,'2025-09-07 02:55:41.633471',0,888.00,54,1,NULL,'INACTIVE',888.00,'2025-09-11 13:59:14.846817',25),(9,'2025-09-11 13:58:57.861936',0,999.00,53,1,NULL,'ACTIVE',999.00,'2025-09-11 13:58:57.861958',25);
/*!40000 ALTER TABLE `col_cart_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `col_order_dtl`
--

DROP TABLE IF EXISTS `col_order_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `col_order_dtl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `payment_id` varchar(255) DEFAULT NULL,
  `razorpay_order_id` varchar(255) DEFAULT NULL,
  `receipt` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `col_order_dtl`
--

LOCK TABLES `col_order_dtl` WRITE;
/*!40000 ALTER TABLE `col_order_dtl` DISABLE KEYS */;
/*!40000 ALTER TABLE `col_order_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `col_product_dtl`
--

DROP TABLE IF EXISTS `col_product_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `col_product_dtl` (
  `id` bigint NOT NULL,
  `product_image_1` varchar(255) DEFAULT NULL,
  `product_image_2` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `dimensions` varchar(255) DEFAULT NULL,
  `fragrance` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `col_product_dtl`
--

LOCK TABLES `col_product_dtl` WRITE;
/*!40000 ALTER TABLE `col_product_dtl` DISABLE KEYS */;
INSERT INTO `col_product_dtl` VALUES (1,'image/products/product1.webp','image/products/product2.webp','Cinnamon',799,NULL,NULL,NULL),(2,'image/products/product1.webp','image/products/product2.webp','Cinnamon 1',123,NULL,NULL,NULL),(3,'image/products/product1.webp','image/products/product2.webp','Cinnamon 2',234,NULL,NULL,NULL),(4,'image/products/product1.webp','image/products/product2.webp','Cinnamon 3',876,NULL,NULL,NULL),(5,'image/products/product1.webp','image/products/product2.webp','Cinnamon 4',89,NULL,NULL,NULL),(6,'image/products/product1.webp','image/products/product2.webp','Cinnamon 5',23,NULL,NULL,NULL),(7,'image/products/product1.webp','image/products/product2.webp','Cinnamon 6',45,NULL,NULL,NULL),(53,'image/products/product1.webp','image/products/product2.webp','Cinnamon Delight Candle',999,'Hello this is product decription','10*10*30',NULL),(54,'image/products/product1.webp','image/products/product2.webp','Cinnamon Candle',888,NULL,NULL,NULL);
/*!40000 ALTER TABLE `col_product_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `col_product_dtl_seq`
--

DROP TABLE IF EXISTS `col_product_dtl_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `col_product_dtl_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `col_product_dtl_seq`
--

LOCK TABLES `col_product_dtl_seq` WRITE;
/*!40000 ALTER TABLE `col_product_dtl_seq` DISABLE KEYS */;
INSERT INTO `col_product_dtl_seq` VALUES (151);
/*!40000 ALTER TABLE `col_product_dtl_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COL_WISHLIST_DTL`
--

DROP TABLE IF EXISTS `COL_WISHLIST_DTL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COL_WISHLIST_DTL` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_product` (`user_id`,`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COL_WISHLIST_DTL`
--

LOCK TABLES `COL_WISHLIST_DTL` WRITE;
/*!40000 ALTER TABLE `COL_WISHLIST_DTL` DISABLE KEYS */;
INSERT INTO `COL_WISHLIST_DTL` VALUES (22,'2025-08-25 00:36:19.000000',53,11),(23,'2025-08-25 00:36:20.000000',7,11),(24,'2025-08-25 00:37:11.000000',2,11),(25,'2025-08-25 00:37:12.000000',3,11),(31,'2025-09-11 13:58:23.000000',2,25),(32,'2025-09-11 13:58:38.000000',3,25),(33,'2025-09-11 13:58:39.000000',1,25),(34,'2025-09-11 13:58:42.000000',4,25);
/*!40000 ALTER TABLE `COL_WISHLIST_DTL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_fragrances`
--

DROP TABLE IF EXISTS `product_fragrances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_fragrances` (
  `product_id` bigint NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `fragrance_name` varchar(255) NOT NULL,
  PRIMARY KEY (`product_id`,`fragrance_name`),
  CONSTRAINT `FK4h0fhtiwwean9nxmpaqbfvhmr` FOREIGN KEY (`product_id`) REFERENCES `col_product_dtl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_fragrances`
--

LOCK TABLES `product_fragrances` WRITE;
/*!40000 ALTER TABLE `product_fragrances` DISABLE KEYS */;
INSERT INTO `product_fragrances` VALUES (53,'/image/products/product3.webp','Flora and fona'),(53,'/image/products/product2.webp','Green forest'),(53,'/image/products/product1.webp','Rosemary');
/*!40000 ALTER TABLE `product_fragrances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `product_id` bigint NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  KEY `FKauatodkhdferbhdoc2opset31` (`product_id`),
  CONSTRAINT `FKauatodkhdferbhdoc2opset31` FOREIGN KEY (`product_id`) REFERENCES `col_product_dtl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (53,'/image/products/product1.webp'),(53,'/image/products/product2.webp'),(53,'/image/products/product3.webp');
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `provider` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2001-08-28','sakuraadmin@gmail.com','Samyak','Jain','8755781117','Samyak Jain','$2a$10$FeM3MfcZ7MygaJB8wbH8z.IvKchNmYekvjiIZMinIXqWx4iD4WDKC','Admin','ADMIN'),(7,'2025-07-28','pankajjainpj1010@gmail.com','Pankaj','Jain','9412545946',NULL,'$2a$10$zyrKYB3orpl5Tl6PWiJDku81mFacMkr2BvKuX/6Mfo0bfUQ7eZhGO','local','USER'),(11,'2001-01-10','anshityagi369@gmail.com','Anshika','Tyagi','7300710525','Anshika Tyagi','$2a$10$bmsRk5gRJ8/Ry9Kl0IV/X.LYcI.kbcp6TFLQrl1.Bpa9Lmn9EhFGq','local','USER'),(25,NULL,'samyaksj007@gmail.com',NULL,NULL,NULL,'samyak jain','DUMMY_PASSWORD','google','USER'),(26,NULL,'samyak28082000@gmail.com',NULL,NULL,NULL,'Samyak Jain','DUMMY_PASSWORD','google','USER');
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

-- Dump completed on 2025-09-30  0:47:11
