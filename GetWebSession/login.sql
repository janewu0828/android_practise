/*
MySQL Data Transfer
Source Host: localhost
Source Database: login
Target Host: localhost
Target Database: login
Date: 2011-6-14 11:10:46
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for info
-- ----------------------------
CREATE TABLE `info` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `userid` int(12) DEFAULT NULL,
  `userinfo` varchar(100) DEFAULT NULL,
  `level` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `useid` (`userid`),
  CONSTRAINT `useid` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `info` VALUES ('1', '1', 'charlie  is a developer.', '1');
INSERT INTO `info` VALUES ('2', '2', 'william is a boss.', '20');
INSERT INTO `user` VALUES ('1', 'charlie', 'password', '1');
INSERT INTO `user` VALUES ('2', 'william', 'mypassword', '2');
