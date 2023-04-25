-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: poh_db
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Current Database: `poh_db`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `poh_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `poh_db`;

--
-- Table structure for table `poh_bought_equipment`
--

DROP TABLE IF EXISTS `poh_bought_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_bought_equipment` (
  `beid` bigint NOT NULL COMMENT '设备id',
  `equip_name` varchar(20) NOT NULL DEFAULT '' COMMENT '设备名',
  `equip_code` char(15) NOT NULL COMMENT '设备码(购买设备获取)',
  `equip_owner` bigint DEFAULT NULL COMMENT '设备归属者(若无则为空)',
  `danger` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否设备处于危险状态(0否，1是)',
  `showed` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否主页展示(0否，1是)',
  `main` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为主设备(0否，1是)',
  `locked` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否锁定设备(0否，1是)',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `group_id` bigint NOT NULL COMMENT '所在组id',
  PRIMARY KEY (`beid`),
  UNIQUE KEY `equip_code` (`equip_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_bought_equipment`
--

LOCK TABLES `poh_bought_equipment` WRITE;
/*!40000 ALTER TABLE `poh_bought_equipment` DISABLE KEYS */;
INSERT INTO `poh_bought_equipment` VALUES (1545367578242727938,'常用设备','460049167410300',1,_binary '\0',_binary '',_binary '',_binary '\0',1,'2022-07-08 11:21:57',1,'2022-07-13 14:45:24',1),(1545370197216477185,'默认设备','324242424678997',1,_binary '\0',_binary '',_binary '\0',_binary '\0',1,'2022-07-08 11:32:21',1,'2022-07-10 10:02:54',1),(1545370366179819521,'测试设备','687893521231134',1,_binary '\0',_binary '',_binary '\0',_binary '\0',1,'2022-07-08 11:33:02',NULL,'2022-07-08 19:33:01',1545370135832838146),(1545370449579360257,'展示设备','890423570746345',1,_binary '\0',_binary '',_binary '\0',_binary '\0',1,'2022-07-08 11:33:21',NULL,'2022-07-08 19:33:21',1),(1546079547874725891,'默认设备','543536896795767',1546079547828588545,_binary '\0',_binary '',_binary '',_binary '\0',1546079547828588545,'2022-07-10 10:31:04',NULL,'2022-07-10 18:31:03',1546079547874725890),(1546518334824103939,'默认设备','964747455222445',1546518334794743809,_binary '\0',_binary '',_binary '',_binary '\0',1546518334794743809,'2022-07-11 15:34:39',NULL,'2022-07-11 23:34:38',1546518334824103938);
/*!40000 ALTER TABLE `poh_bought_equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poh_equip_group`
--

DROP TABLE IF EXISTS `poh_equip_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_equip_group` (
  `gid` bigint DEFAULT NULL COMMENT '设备组id',
  `group_name` varchar(20) NOT NULL DEFAULT '' COMMENT '分组名',
  `description` varchar(100) DEFAULT NULL COMMENT '分组描述',
  `group_owner` bigint NOT NULL COMMENT '分组归属者',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备分组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_equip_group`
--

LOCK TABLES `poh_equip_group` WRITE;
/*!40000 ALTER TABLE `poh_equip_group` DISABLE KEYS */;
INSERT INTO `poh_equip_group` VALUES (1,'默认分组','系统的默认分组',1,1,'2022-06-13 19:29:56',1,'2022-07-12 16:02:41'),(1545370135832838146,'测试分组','用于测试的分组',1,1,'2022-07-08 11:32:07',1,'2022-07-12 15:48:11'),(1546079547874725890,'默认分组','系统提供的默认分组',1546079547828588545,1546079547828588545,'2022-07-10 10:31:04',NULL,'2022-07-10 18:31:03'),(1546518334824103938,'默认分组','系统提供的默认分组',1546518334794743809,1546518334794743809,'2022-07-11 15:34:39',NULL,'2022-07-11 23:34:38');
/*!40000 ALTER TABLE `poh_equip_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poh_equipment`
--

DROP TABLE IF EXISTS `poh_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_equipment` (
  `eid` bigint NOT NULL COMMENT '设备id',
  `equip_code` char(15) NOT NULL COMMENT '设备码(购买设备获取)',
  `battery_capacity` int NOT NULL DEFAULT '0' COMMENT '电池容量',
  `version` varchar(20) NOT NULL COMMENT '设备版本',
  `type` varchar(20) NOT NULL COMMENT '设备类型',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`eid`),
  UNIQUE KEY `equip_code` (`equip_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_equipment`
--

LOCK TABLES `poh_equipment` WRITE;
/*!40000 ALTER TABLE `poh_equipment` DISABLE KEYS */;
INSERT INTO `poh_equipment` VALUES (15,'460049167410300',4000,'V1.0','POH_CM_POSITION',1,'2022-06-13 20:16:19',NULL,'2022-06-13 20:16:19'),(16,'324242424678997',4000,'V1.0','POH_CM_POSITION',1,'2022-06-13 20:16:19',NULL,'2022-06-13 20:16:19'),(17,'687893521231134',4000,'V1.0','POH_CM_POSITION',1,'2022-06-13 20:16:19',NULL,'2022-06-13 20:16:19'),(18,'890423570746345',4000,'V1.0','POH_CM_POSITION',1,'2022-06-13 20:16:19',NULL,'2022-06-13 20:16:19'),(19,'543536896795767',4000,'V1.0','POH_CM_POSITION',1,'2022-06-13 20:16:19',NULL,'2022-06-13 20:16:19'),(20,'964747455222445',4000,'V1.0','POH_CM_POSITION',1,'2022-06-13 20:16:19',NULL,'2022-06-13 20:16:19'),(21,'756345223421895',4000,'V1.0','POH_CM_POSITION',1,'2022-06-13 20:16:19',NULL,'2022-06-13 20:16:19'),(22,'580646243257900',4000,'V1.0','POH_CM_POSITION',1,'2022-06-13 20:16:19',NULL,'2022-06-13 20:16:19'),(23,'314536132479056',4000,'V1.0','POH_CM_POSITION',1,'2022-06-13 20:16:19',NULL,'2022-06-13 20:16:19');
/*!40000 ALTER TABLE `poh_equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poh_equipment_connect_group`
--

DROP TABLE IF EXISTS `poh_equipment_connect_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_equipment_connect_group` (
  `egid` bigint NOT NULL COMMENT '设备及分组关联id',
  `eid` bigint DEFAULT NULL COMMENT '设备id',
  `gid` bigint DEFAULT NULL COMMENT '分组id',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`egid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备及分组关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_equipment_connect_group`
--

LOCK TABLES `poh_equipment_connect_group` WRITE;
/*!40000 ALTER TABLE `poh_equipment_connect_group` DISABLE KEYS */;
INSERT INTO `poh_equipment_connect_group` VALUES (1,1,1,1,'2022-05-22 22:21:44',1,'2022-05-22 22:21:44'),(2,2,1,1,'2022-05-22 22:26:59',NULL,'2022-05-22 22:26:59'),(3,2,2,1,'2022-05-22 22:29:13',NULL,'2022-05-22 22:29:13');
/*!40000 ALTER TABLE `poh_equipment_connect_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `poh_equipment_view`
--

DROP TABLE IF EXISTS `poh_equipment_view`;
/*!50001 DROP VIEW IF EXISTS `poh_equipment_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `poh_equipment_view` AS SELECT 
 1 AS `eid`,
 1 AS `equip_name`,
 1 AS `equip_code`,
 1 AS `equip_owner`,
 1 AS `danger`,
 1 AS `group_id`,
 1 AS `showed`,
 1 AS `main`,
 1 AS `locked`,
 1 AS `battery_capacity`,
 1 AS `version`,
 1 AS `type`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `poh_location`
--

DROP TABLE IF EXISTS `poh_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_location` (
  `lid` bigint NOT NULL COMMENT '主键ID',
  `equipment_id` bigint NOT NULL COMMENT '所属设备id',
  `locate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '定位时间',
  `longitude` double NOT NULL COMMENT '经度',
  `longitude_hemisphere` enum('E','W') NOT NULL COMMENT '经度半球(E东，W西)',
  `latitude` double NOT NULL COMMENT '纬度',
  `latitude_hemisphere` enum('N','S') NOT NULL COMMENT '纬度半球(N北，S南)',
  `satellite_num` int NOT NULL COMMENT '可视卫星总数',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_location`
--

LOCK TABLES `poh_location` WRITE;
/*!40000 ALTER TABLE `poh_location` DISABLE KEYS */;
INSERT INTO `poh_location` VALUES (1,1,'2022-05-23 14:45:48',116.404,'E',39.915,'N',8,1,'2022-05-23 14:45:48',1,'2022-05-23 14:45:48');
/*!40000 ALTER TABLE `poh_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poh_menu`
--

DROP TABLE IF EXISTS `poh_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_menu` (
  `mid` bigint NOT NULL COMMENT '菜单id',
  `parent_mid` bigint DEFAULT NULL COMMENT '父菜单id',
  `menu_name` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单名',
  `permission` varchar(100) NOT NULL DEFAULT '*' COMMENT '权限',
  `locked` int NOT NULL DEFAULT '0' COMMENT '是否被锁定(1是,0否)',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_menu`
--

LOCK TABLES `poh_menu` WRITE;
/*!40000 ALTER TABLE `poh_menu` DISABLE KEYS */;
INSERT INTO `poh_menu` VALUES (1,NULL,'设备操作','equipment:operate',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(2,1,'增加设备','equipment:operate:insert',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(3,1,'删除设备','equipment:operate:delete',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(4,1,'修改设备','equipment:operate:update',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(5,1,'查看设备','equipment:operate:select',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(6,NULL,'设备分组操作','equipmentGroup:operate',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(7,6,'增加分组','equipmentGroup:operate:insert',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(8,6,'删除分组','equipmentGroup:operate:delete',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(9,6,'修改分组','equipmentGroup:operate:update',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(10,6,'查看分组','equipmentGroup:operate:select',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(11,NULL,'用户操作','user:operate',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(12,11,'增加系统用户','user:operate:insert',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(13,11,'删除系统用户','user:operate:delete',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(14,11,'修改系统用户','user:operate:update',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(15,11,'查看系统用户','user:operate:select',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(16,11,'增加系统用户家人','user:family:operate:insert',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(17,11,'删除系统用户家人','user:family:operate:delete',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(18,11,'修改系统用户家人','user:family:operate:update',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(19,11,'查看系统用户家人','user:family:operate:select',0,1,'2022-06-13 19:32:05',1,'2022-06-13 19:32:05'),(20,11,'系统用户查看个人信息','user:operate:select:owner',0,1,'2022-06-28 17:26:52',NULL,'2022-06-28 17:26:52'),(21,11,'系统用户修改个人信息','user:operate:update:owner',0,1,'2022-06-28 17:26:52',NULL,'2022-06-28 17:27:57');
/*!40000 ALTER TABLE `poh_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poh_role`
--

DROP TABLE IF EXISTS `poh_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_role` (
  `rid` bigint NOT NULL COMMENT '角色id',
  `role_mark` varchar(20) NOT NULL COMMENT '角色标记',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `locked` int NOT NULL DEFAULT '0' COMMENT '是否被锁定(1是,0否)',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `role_mark` (`role_mark`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_role`
--

LOCK TABLES `poh_role` WRITE;
/*!40000 ALTER TABLE `poh_role` DISABLE KEYS */;
INSERT INTO `poh_role` VALUES (1,'admin','系统管理员','系统最高权限所有者',0,1,'2022-06-13 19:32:18',1,'2022-06-13 19:32:18'),(2,'system_user','系统用户','购买识途设备使用系统的用户',0,1,'2022-06-13 19:32:18',1,'2022-06-13 19:32:18'),(3,'system_user_family','系统用户家人','系统用户添加的家人',0,1,'2022-06-13 19:32:18',1,'2022-06-13 19:32:18'),(4,'system_family_super','系统用户高权限家人','系统用户添加的家人，并且赋予了后台权限',0,1,'2022-06-13 19:32:18',1,'2022-06-28 17:23:30');
/*!40000 ALTER TABLE `poh_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poh_role_connect_menu`
--

DROP TABLE IF EXISTS `poh_role_connect_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_role_connect_menu` (
  `rmid` bigint NOT NULL COMMENT '角色菜单关联id',
  `rid` bigint DEFAULT NULL COMMENT '角色id',
  `mid` bigint DEFAULT NULL COMMENT '菜单id',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`rmid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_role_connect_menu`
--

LOCK TABLES `poh_role_connect_menu` WRITE;
/*!40000 ALTER TABLE `poh_role_connect_menu` DISABLE KEYS */;
INSERT INTO `poh_role_connect_menu` VALUES (1,1,1,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(2,1,2,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(3,1,3,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(4,1,4,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(5,1,5,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(6,1,6,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(7,1,7,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(8,1,8,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(9,1,9,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(10,1,10,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(11,1,11,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(12,1,12,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(13,1,13,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(14,1,14,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(15,1,15,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(16,1,16,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(17,1,17,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(18,1,18,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(19,1,19,1,'2022-05-22 21:05:01',1,'2022-05-22 21:05:01'),(20,2,1,1,'2022-05-22 21:32:58',NULL,'2022-05-22 21:32:58'),(21,2,2,1,'2022-05-22 21:32:58',NULL,'2022-05-22 21:32:58'),(22,2,3,1,'2022-05-22 21:32:58',NULL,'2022-05-22 21:32:58'),(23,2,4,1,'2022-05-22 21:32:58',NULL,'2022-05-22 21:32:58'),(24,2,5,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(25,2,6,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(26,2,7,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(27,2,8,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(28,2,9,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(29,2,10,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(30,2,16,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(31,2,17,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(32,2,18,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(33,2,19,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(34,2,20,1,'2022-06-28 17:29:04',NULL,'2022-06-28 17:29:04'),(35,2,21,1,'2022-06-28 17:29:04',NULL,'2022-06-28 17:29:04'),(36,1,20,1,'2022-06-28 17:29:04',NULL,'2022-06-28 17:29:04'),(37,1,21,1,'2022-06-28 17:29:04',NULL,'2022-06-28 17:29:04'),(38,4,1,1,'2022-05-22 21:32:58',NULL,'2022-05-22 21:32:58'),(39,4,2,1,'2022-05-22 21:32:58',NULL,'2022-05-22 21:32:58'),(40,4,3,1,'2022-05-22 21:32:58',NULL,'2022-05-22 21:32:58'),(41,4,4,1,'2022-05-22 21:32:58',NULL,'2022-05-22 21:32:58'),(42,4,5,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(43,4,6,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(44,4,7,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(45,4,8,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(46,4,9,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(47,4,10,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(48,4,16,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(49,4,17,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(50,4,18,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27'),(51,4,19,1,'2022-06-21 20:48:27',NULL,'2022-06-21 20:48:27');
/*!40000 ALTER TABLE `poh_role_connect_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poh_user`
--

DROP TABLE IF EXISTS `poh_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_user` (
  `uid` bigint NOT NULL COMMENT '用户id',
  `parent_uid` bigint DEFAULT NULL COMMENT '所属主用户id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nickname` varchar(20) NOT NULL DEFAULT '' COMMENT '昵称',
  `identity` varchar(20) DEFAULT NULL COMMENT '用户身份',
  `gender` int NOT NULL DEFAULT '0' COMMENT '性别(0男，1女)',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `locked` int NOT NULL DEFAULT '0' COMMENT '是否被锁定(1是,0否)',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_user`
--

LOCK TABLES `poh_user` WRITE;
/*!40000 ALTER TABLE `poh_user` DISABLE KEYS */;
INSERT INTO `poh_user` VALUES (1,NULL,'admin','14e1b600b1fd579f47433b88e8d85291','管理员','系统管理员',0,'909099093@qq.com',0,1,'2022-06-13 19:32:31',1,'2022-06-13 19:32:31'),(1538143111816970241,NULL,'jack','1c3b882ee2f099e27af72ef88e08fdb3','jack马','系统用户',1,'nihao123@qq.com',0,1,'2022-06-18 12:54:30',NULL,'2022-06-18 20:54:29'),(1539055806888095745,1,'account','14e1b600b1fd579f47433b88e8d85291','account','叔叔',1,'1333122674941@qq.com',0,1,'2022-06-21 01:21:13',NULL,'2022-06-21 09:21:13'),(1541791283936350209,1,'helloabc','1c3b882ee2f099e27af72ef88e08fdb3','你好ABC','随便',1,NULL,0,1,'2022-06-28 14:31:02',NULL,'2022-06-28 22:31:01'),(1542497365369389058,NULL,'lily','14e1b600b1fd579f47433b88e8d85291','lily','系统用户',0,NULL,0,1,'2022-06-30 13:16:45',NULL,'2022-06-30 21:16:44'),(1542500066777989121,NULL,'jake','1c3b882ee2f099e27af72ef88e08fdb3','jake','系统用户',0,NULL,0,1,'2022-06-30 13:27:29',NULL,'2022-06-30 21:27:28'),(1542508350066991105,NULL,'lucy','1c3b882ee2f099e27af72ef88e08fdb3','lucy','系统用户',0,NULL,0,1,'2022-06-30 14:00:24',NULL,'2022-06-30 22:00:23'),(1542508864607461378,NULL,'liubai','1c3b882ee2f099e27af72ef88e08fdb3','liubai','系统用户',0,NULL,0,1,'2022-06-30 14:02:26',NULL,'2022-06-30 22:02:26'),(1543149770570268673,1,'nihao','1c3b882ee2f099e27af72ef88e08fdb3','你好','哈哈',0,NULL,0,1,'2022-07-02 08:29:10',NULL,'2022-07-02 16:29:10'),(1546079547828588545,NULL,'hello','1c3b882ee2f099e27af72ef88e08fdb3','hello','系统用户',0,NULL,0,1,'2022-07-10 10:31:04',NULL,'2022-07-10 18:31:03'),(1546518334794743809,NULL,'helloworld','1c3b882ee2f099e27af72ef88e08fdb3','helloworld','系统用户',0,NULL,0,1,'2022-07-11 15:34:39',NULL,'2022-07-11 23:34:38');
/*!40000 ALTER TABLE `poh_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poh_user_connect_role`
--

DROP TABLE IF EXISTS `poh_user_connect_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poh_user_connect_role` (
  `urid` bigint NOT NULL COMMENT '用户角色关联id',
  `uid` bigint DEFAULT NULL COMMENT '用户id',
  `rid` bigint DEFAULT NULL COMMENT '角色id',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_by` bigint DEFAULT NULL COMMENT '最后修改者',
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`urid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poh_user_connect_role`
--

LOCK TABLES `poh_user_connect_role` WRITE;
/*!40000 ALTER TABLE `poh_user_connect_role` DISABLE KEYS */;
INSERT INTO `poh_user_connect_role` VALUES (1,1,2,1,'2022-05-22 21:00:45',1,'2022-05-22 21:00:45'),(2,2,2,1,'2022-05-22 21:28:28',NULL,'2022-05-22 21:28:28'),(1538143111816970242,1538143111816970241,2,1,'2022-06-18 12:54:30',NULL,'2022-06-18 20:54:29'),(1538448713147600897,1538448713126629377,2,1,'2022-06-19 09:08:51',NULL,'2022-06-19 17:08:50'),(1542497365369389059,1542497365369389058,2,0,'2022-06-30 13:16:45',NULL,'2022-06-30 21:16:44'),(1542500066811543553,1542500066777989121,2,0,'2022-06-30 13:27:29',NULL,'2022-06-30 21:27:28'),(1542508350092156929,1542508350066991105,2,0,'2022-06-30 14:00:24',NULL,'2022-06-30 22:00:23'),(1542508864636821505,1542508864607461378,2,0,'2022-06-30 14:02:26',NULL,'2022-06-30 22:02:26'),(1542764413785812994,1541791283936350209,4,1,'2022-07-01 06:57:54',NULL,'2022-07-01 14:57:54'),(1542779493114040322,1539055806888095745,4,1,'2022-07-01 07:57:49',NULL,'2022-07-01 15:57:49'),(1546079547828588546,1546079547828588545,2,0,'2022-07-10 10:31:04',NULL,'2022-07-10 18:31:03'),(1546508933958549505,1546508933916606465,2,0,'2022-07-11 14:57:17',NULL,'2022-07-11 22:57:17'),(1546510522878373891,1546510522878373890,2,0,'2022-07-11 15:03:36',NULL,'2022-07-11 23:03:36'),(1546511093370716162,1546511093337161730,2,0,'2022-07-11 15:05:52',NULL,'2022-07-11 23:05:52'),(1546518334824103937,1546518334794743809,2,0,'2022-07-11 15:34:39',NULL,'2022-07-11 23:34:38');
/*!40000 ALTER TABLE `poh_user_connect_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `poh_db`
--

USE `poh_db`;

--
-- Final view structure for view `poh_equipment_view`
--

/*!50001 DROP VIEW IF EXISTS `poh_equipment_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `poh_equipment_view` AS select `e1`.`beid` AS `eid`,`e1`.`equip_name` AS `equip_name`,`e1`.`equip_code` AS `equip_code`,`e1`.`equip_owner` AS `equip_owner`,`e1`.`danger` AS `danger`,`e1`.`group_id` AS `group_id`,`e1`.`showed` AS `showed`,`e1`.`main` AS `main`,`e1`.`locked` AS `locked`,`e2`.`battery_capacity` AS `battery_capacity`,`e2`.`version` AS `version`,`e2`.`type` AS `type` from (`poh_bought_equipment` `e1` left join `poh_equipment` `e2` on((`e1`.`equip_code` = `e2`.`equip_code`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-29 14:47:06
