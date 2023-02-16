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

use `echo`;
-- write sql script below...

--
-- Table structure for table `greeting`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `greeting` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID，主键',
  `create_at` timestamp(6) NULL DEFAULT NULL COMMENT '创建时间',
  `update_at` timestamp(6) NULL DEFAULT NULL COMMENT '更新时间',
  `entity` json DEFAULT NULL COMMENT 'greeting proto 结构内数据',
  `greeted` varchar(768) GENERATED ALWAYS AS (`entity`->>'$.greeted') STORED COMMENT 'proto 中的字段，直接映射出来做 index 使用',
  PRIMARY KEY (`id`),
  KEY `idx_greeted` (`greeted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='greeting表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `greeting`
--

LOCK TABLES `greeting` WRITE;
/*!40000 ALTER TABLE `greeting` DISABLE KEYS */;
/*!40000 ALTER TABLE `greeting` ENABLE KEYS */;
UNLOCK TABLES;

-- mysql script end.
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
