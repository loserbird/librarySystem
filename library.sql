/*
SQLyog Ultimate v8.32 
MySQL - 5.7.11-log : Database - library
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`library` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `library`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`id`,`name`,`password`) values (1,'bingqin','bingqin'),(2,'admin','admin');

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `bookName` varchar(255) DEFAULT NULL COMMENT '书名',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `categoryId` int(11) DEFAULT NULL COMMENT '类别',
  `imgurl` varchar(255) DEFAULT NULL COMMENT '图片路径',
  `count` int(11) DEFAULT NULL COMMENT '数量',
  `isAvailable` int(11) DEFAULT NULL COMMENT '是否下架',
  PRIMARY KEY (`id`),
  KEY `FK_book` (`categoryId`),
  CONSTRAINT `FK_book` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`id`,`bookName`,`author`,`description`,`categoryId`,`imgurl`,`count`,`isAvailable`) values (1,'四月是你的谎言','小萌','精彩绝伦',1,'/upload/3/0/32b67eb4-3096-40be-b1c7-d6a1e77f6ba2.png',3,1),(2,'寄小读者','小明','适合儿童看',1,'/upload/f/7/7de29cb6-a9bd-41ec-a087-554ae7b1e4d4.jpg',2,1),(3,'小仙剑','金庸','激动人心',4,'/upload/2/4/8040740b-ddc4-4282-a911-077d81d769e0.jpg',4,1),(4,'javawe编程','炳钦','非常精彩',1,'/upload/2/d/2cd2b612-7058-4c2b-87e1-645217c123f4.jpg',2,1),(14,'凯迪拉克','顺丰到付','垃圾书籍',1,'/upload/6/f/d48ed29d-bb8d-46c8-965e-0077802dfc01.jpg',7,1),(15,'爱你不是真的','晓白','搞笑',3,'/upload/c/b/d4a12e20-ded7-4489-bb30-42cff6f5978b.jpg',5,1),(16,'哇咔咔','大发明家','疯了',2,'/upload/5/6/6553302c-0693-432c-939e-e8f21f6e2351.jpg',3,1),(17,'我开的飞快','大幅度','四道口',2,'/upload/1/e/1038fe25-f4d3-4b2f-821f-fa8d5653adce.jpg',2,1),(18,'对方角度考虑','多福多寿','',3,'/upload/a/6/2ac73b3b-aca8-465c-801a-0a054741a5ba.jpg',3,1),(19,'大我让他','无法回头','',4,'/upload/8/c/06416358-b9c3-4c18-9ef9-9a2cbf746315.jpg',5,1),(20,'为哦肥哦i','戴菲菲','开飞机都死额佛',1,'/upload/3/0/24992802-2478-4131-980d-b39d532c31ff.jpg',4,1);

/*Table structure for table `borrow` */

DROP TABLE IF EXISTS `borrow`;

CREATE TABLE `borrow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `bookId` int(11) DEFAULT NULL COMMENT '书籍',
  `userId` varchar(255) DEFAULT NULL COMMENT '用户',
  `applyDate` date DEFAULT NULL COMMENT '申请日期',
  `borrowDate` date DEFAULT NULL COMMENT '借书日期',
  `returnDate` date DEFAULT NULL COMMENT '归还日期',
  `shouldReturnDate` date DEFAULT NULL COMMENT '应还日期',
  `isReturned` int(11) DEFAULT NULL COMMENT '是否归还',
  `state` int(11) DEFAULT NULL COMMENT '是否允许借书',
  PRIMARY KEY (`id`),
  KEY `FK_borrow` (`bookId`),
  KEY `FK_borrow1` (`userId`),
  CONSTRAINT `FK_borrow` FOREIGN KEY (`bookId`) REFERENCES `book` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_borrow1` FOREIGN KEY (`userId`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `borrow` */

insert  into `borrow`(`id`,`bookId`,`userId`,`applyDate`,`borrowDate`,`returnDate`,`shouldReturnDate`,`isReturned`,`state`) values (7,3,'3115000000','2016-06-05','2016-06-06','2016-06-09','2016-09-04',1,1),(8,3,'3115000000','2016-06-05','2016-06-05','2016-06-09','2016-09-03',1,1),(9,4,'3115000000','2016-06-05','2016-06-05','2016-06-09','2016-07-05',1,1),(10,1,'3115000000','2016-06-05','2016-06-06','2016-06-09','2016-09-04',1,1),(11,2,'3115000000','2016-06-05','2016-06-06','2016-06-09','2016-08-05',1,1),(15,4,'3115000000','2016-06-07','2016-06-07','2016-06-09','2016-08-06',1,1),(16,3,'3115005304','2016-06-07','2016-06-07','2016-06-10','2016-07-07',1,1),(17,2,'3115005304','2016-06-07','2016-06-07','2016-06-10','2016-08-06',1,1),(18,1,'3115005304','2016-06-07','2016-06-07','2016-06-10','2016-08-06',1,1),(19,3,'3115005304','2016-06-07','2016-06-07','2016-06-10','2016-07-07',1,1),(20,4,'3115005304','2016-06-07','2016-06-07','2016-06-10','2016-08-06',1,1),(21,14,'3115005304','2016-06-07','2016-06-07','2016-06-10','2016-08-06',1,1),(30,14,'3115000000','2016-06-07','2016-06-09','2016-06-09','2016-08-08',1,1),(31,16,'3115005306','2016-06-09','2016-06-09','2016-06-09','2016-09-07',1,1),(32,15,'3115005306','2016-06-09','2016-06-09','2016-06-09','2016-08-08',1,1),(33,1,'3115005306','2016-06-09','2016-06-09','2016-06-09','2016-08-08',1,1),(34,3,'3115005306','2016-06-09','2016-06-09','2016-06-09','2016-07-09',1,1),(39,1,'3115005304','2016-06-09','2016-06-09','2016-06-10','2016-08-08',1,1),(40,4,'3115005304','2016-06-09','2016-06-09','2016-06-10','2016-08-08',1,1),(41,1,'3115000000','2016-06-10','2016-06-10','2016-06-10','2016-08-09',1,1),(42,2,'3115005304','2016-06-10','2016-06-10','2016-06-10','2016-08-09',1,1),(43,1,'3115005304','2016-06-10','2016-06-10','2016-06-10','2016-08-09',1,1),(44,3,'3115005304','2016-06-10','2016-06-10',NULL,'2016-07-10',0,1),(45,1,'3115005304','2016-06-11',NULL,NULL,NULL,0,0),(46,15,'3115005304','2016-06-11',NULL,NULL,NULL,0,0);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `categoryName` varchar(255) DEFAULT NULL COMMENT '类名',
  `days` varchar(255) DEFAULT NULL COMMENT '可借天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`id`,`categoryName`,`days`) values (1,'天文地理','60'),(2,'小说','90'),(3,'散文','60'),(4,'计算机类','30'),(5,'经济类','90');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` varchar(20) NOT NULL,
  `studentName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `grade` varchar(20) DEFAULT NULL,
  `major` varchar(20) DEFAULT NULL,
  `department` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`id`,`studentName`,`password`,`state`,`email`,`grade`,`major`,`department`) values ('3115005304','bingqin','bingqin',1,'',NULL,NULL,NULL),('3115006305','caibingqin','12345e',0,'eiweu@qq.com','大二','日语','外国语学院');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` varchar(255) NOT NULL COMMENT '账号',
  `userName` varchar(255) DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `role` varchar(255) DEFAULT NULL COMMENT '角色',
  `department` varchar(255) DEFAULT NULL COMMENT '学院',
  `major` varchar(255) DEFAULT NULL COMMENT '专业',
  `grade` varchar(255) DEFAULT NULL COMMENT '年级',
  `state` int(11) DEFAULT NULL COMMENT '是否注册通过',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`uid`,`userName`,`password`,`email`,`role`,`department`,`major`,`grade`,`state`) values ('3115000000','admin','d12345','15626140150@163.com','admin','计算机学院','软件工程','大四',1),('3115005304','小白菜','a123456','923230014@qq.com','student','计算机学院','软件工程','大一',1),('3115005305','小王','b23456','3231464171@qq.com','student','计算机学院','软件工程','大二',1),('3115005306','炳钦','a123456','3231464171@qq.com','student','外国语学院','日语','大三',1),('3115005308','小妹','c12345','923230014@qq.com','student','外国语学院','英语','大三',1),('3115005311','疾风剑豪','f12345','233454324@qq.com','student','外国语学院','英语','大二',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
