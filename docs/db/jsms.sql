/*
Navicat MySQL Data Transfer

Source Server         : localMysql
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : jsms

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-04-16 20:37:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_uperm
-- ----------------------------
DROP TABLE IF EXISTS `user_uperm`;
CREATE TABLE `user_uperm` (
  `role_code` varchar(36) NOT NULL,
  `up_id` varchar(36) NOT NULL,
  KEY `FKnvxwdkn31lljwqiv0xa0iyvg6` (`up_id`),
  KEY `FKe71qh9naoldol9f5ydj7011w4` (`role_code`),
  CONSTRAINT `FKe71qh9naoldol9f5ydj7011w4` FOREIGN KEY (`role_code`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKnvxwdkn31lljwqiv0xa0iyvg6` FOREIGN KEY (`up_id`) REFERENCES `rperm` (`rp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_uperm
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` varchar(36) NOT NULL,
  `role_code` varchar(24) NOT NULL,
  KEY `FKl11ie1m3f4y95wx0qh9xixf1w` (`role_code`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKl11ie1m3f4y95wx0qh9xixf1w` FOREIGN KEY (`role_code`) REFERENCES `role` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('5', 'ROLE_OPT');
INSERT INTO `user_role` VALUES ('3', 'ROLE_OPT');
INSERT INTO `user_role` VALUES ('4', 'ROLE_OPT');
INSERT INTO `user_role` VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `user_role` VALUES ('2', 'ROLE_OPT');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(36) NOT NULL,
  `username` varchar(24) DEFAULT NULL,
  `user_caption` varchar(56) DEFAULT NULL,
  `password` varchar(56) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `age` varchar(3) DEFAULT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `telephone` varchar(16) DEFAULT NULL,
  `email` varchar(56) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `agency_id` varchar(36) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK3x269n17oftkff7pg3uxj0niy` (`agency_id`),
  CONSTRAINT `FK3x269n17oftkff7pg3uxj0niy` FOREIGN KEY (`agency_id`) REFERENCES `agency` (`agency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '管理员', '123456', '1', '28', '1991-01-01', '13500000000', 'admin@x.com', '湖北武汉', '1', '2019-04-11 11:04:59', '系统管理员');
INSERT INTO `user` VALUES ('2', 'tom', 'TOM', '123456', '1', '29', '1990-01-01', '13500000001', 'tom@x.com', '111223344', null, null, '');
INSERT INTO `user` VALUES ('3', 'rose', 'ROSE', '123456', '0', '25', null, '13500000002', 'rose@x.com', null, '3', '2019-04-03 17:21:31', null);
INSERT INTO `user` VALUES ('4', 'junly', 'JUNLY', '123456', '0', '24', null, '13500000003', 'junly@x.com', null, '3', '2019-04-04 17:21:34', null);
INSERT INTO `user` VALUES ('5', 'jeem', 'JEEM', '123456', '1', '29', null, '13500000004', 'jeem@x.com', '', '3', '2019-04-03 17:21:31', null);

-- ----------------------------
-- Table structure for uperm
-- ----------------------------
DROP TABLE IF EXISTS `uperm`;
CREATE TABLE `uperm` (
  `up_id` varchar(36) NOT NULL,
  `permission` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`up_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uperm
-- ----------------------------

-- ----------------------------
-- Table structure for rperm
-- ----------------------------
DROP TABLE IF EXISTS `rperm`;
CREATE TABLE `rperm` (
  `rp_id` varchar(36) NOT NULL,
  `permission` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`rp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rperm
-- ----------------------------
INSERT INTO `rperm` VALUES ('1', 'user:create');
INSERT INTO `rperm` VALUES ('2', 'user:delete');
INSERT INTO `rperm` VALUES ('3', 'user:query');

-- ----------------------------
-- Table structure for role_rperm
-- ----------------------------
DROP TABLE IF EXISTS `role_rperm`;
CREATE TABLE `role_rperm` (
  `role_code` varchar(24) NOT NULL,
  `rp_id` varchar(36) NOT NULL,
  KEY `FKdb4j12wjq3imnxvgqj5mgae5m` (`rp_id`),
  KEY `FKiwx4lrm5gtuybx0wtxelawkoh` (`role_code`),
  CONSTRAINT `FKdb4j12wjq3imnxvgqj5mgae5m` FOREIGN KEY (`rp_id`) REFERENCES `rperm` (`rp_id`),
  CONSTRAINT `FKiwx4lrm5gtuybx0wtxelawkoh` FOREIGN KEY (`role_code`) REFERENCES `role` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_rperm
-- ----------------------------
INSERT INTO `role_rperm` VALUES ('ROLE_OPT', '1');
INSERT INTO `role_rperm` VALUES ('ROLE_OPT', '2');
INSERT INTO `role_rperm` VALUES ('ROLE_OPT', '3');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `role_code` varchar(24) NOT NULL,
  `menu_code` varchar(24) NOT NULL,
  KEY `FKimvusitnai3c6d7c7n9f35s11` (`menu_code`),
  KEY `FKdf84umi0aaqgqgs2hpjd73qip` (`role_code`),
  CONSTRAINT `FKdf84umi0aaqgqgs2hpjd73qip` FOREIGN KEY (`role_code`) REFERENCES `role` (`role_code`),
  CONSTRAINT `FKimvusitnai3c6d7c7n9f35s11` FOREIGN KEY (`menu_code`) REFERENCES `menu` (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0100');
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0101');
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0102');
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0103');
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0104');
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0105');
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0200');
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0201');
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0202');
INSERT INTO `role_menu` VALUES ('ROLE_ADMIN', '0203');
INSERT INTO `role_menu` VALUES ('ROLE_OPT', '0200');
INSERT INTO `role_menu` VALUES ('ROLE_OPT', '0201');
INSERT INTO `role_menu` VALUES ('ROLE_OPT', '0202');
INSERT INTO `role_menu` VALUES ('ROLE_OPT', '0203');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_code` varchar(24) NOT NULL,
  `role_name` varchar(56) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('ROLE_ADMIN', '系统管理员', null);
INSERT INTO `role` VALUES ('ROLE_MANAGER', '管理员', null);
INSERT INTO `role` VALUES ('ROLE_OPT', '操作员', null);
INSERT INTO `role` VALUES ('ROLE_TEST', '测试员 ', null);

-- ----------------------------
-- Table structure for perm
-- ----------------------------
DROP TABLE IF EXISTS `perm`;
CREATE TABLE `perm` (
  `perm_id` varchar(36) NOT NULL,
  `action` varchar(56) DEFAULT NULL,
  `domain` varchar(24) DEFAULT NULL,
  `target` varchar(36) DEFAULT NULL,
  `type` varchar(2) DEFAULT NULL,
  `type_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of perm
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_code` varchar(24) NOT NULL,
  `menu_name` varchar(56) DEFAULT NULL,
  `parent_menu_code` varchar(24) DEFAULT NULL,
  `style` varchar(56) DEFAULT NULL,
  `path` varchar(128) DEFAULT NULL,
  `leaf` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('0100', '系统管理', null, 'layui-icon layui-icon-set-fill', null, '0');
INSERT INTO `menu` VALUES ('0101', '用户管理', '0100', null, 'user.html', '1');
INSERT INTO `menu` VALUES ('0102', '机构管理', '0100', null, 'agency.html', '1');
INSERT INTO `menu` VALUES ('0103', '角色管理', '0100', null, 'role.html', '1');
INSERT INTO `menu` VALUES ('0104', '菜单管理', '0100', null, 'menu.html', '1');
INSERT INTO `menu` VALUES ('0105', '数据字典', '0100', null, 'itemcode.html', '1');
INSERT INTO `menu` VALUES ('0106', '权限管理', '0100', null, 'rperm.html', '1');
INSERT INTO `menu` VALUES ('0200', '内容管理', null, 'layui-icon layui-icon-component', null, null);
INSERT INTO `menu` VALUES ('0201', '分类管理', '0200', null, null, null);
INSERT INTO `menu` VALUES ('0202', '文档管理', '0200', null, null, null);
INSERT INTO `menu` VALUES ('0203', '评论管理', '0200', null, null, null);

-- ----------------------------
-- Table structure for codeitem
-- ----------------------------
DROP TABLE IF EXISTS `codeitem`;
CREATE TABLE `codeitem` (
  `item_id` varchar(36) NOT NULL,
  `group_id` varchar(36) DEFAULT NULL,
  `item_code` varchar(12) DEFAULT NULL,
  `item_name` varchar(56) DEFAULT NULL,
  `item_type` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `FK6xxvxbkr9igkc1gjk710792uc` (`group_id`),
  CONSTRAINT `FK6xxvxbkr9igkc1gjk710792uc` FOREIGN KEY (`group_id`) REFERENCES `codegroup` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of codeitem
-- ----------------------------
INSERT INTO `codeitem` VALUES ('1', '1', '01', '男', null);
INSERT INTO `codeitem` VALUES ('2', '1', '02', '女', null);
INSERT INTO `codeitem` VALUES ('297e79e16a04e3f7016a0504e66f0001', '297e79e16a04e3f7016a04e76c5d0000', '1', '是', '');
INSERT INTO `codeitem` VALUES ('297e79e16a04e3f7016a05061a910002', '297e79e16a04e3f7016a04e76c5d0000', '0', '否', '');

-- ----------------------------
-- Table structure for codegroup
-- ----------------------------
DROP TABLE IF EXISTS `codegroup`;
CREATE TABLE `codegroup` (
  `group_id` varchar(36) NOT NULL,
  `group_name` varchar(56) DEFAULT NULL,
  `group_caption` varchar(128) DEFAULT NULL,
  `group_type` varchar(26) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of codegroup
-- ----------------------------
INSERT INTO `codegroup` VALUES ('1', 'sex', '性别', null);
INSERT INTO `codegroup` VALUES ('297e79e16a04e3f7016a04e76c5d0000', 'bool', '布尔', '01');

-- ----------------------------
-- Table structure for agency
-- ----------------------------
DROP TABLE IF EXISTS `agency`;
CREATE TABLE `agency` (
  `agency_id` varchar(36) NOT NULL,
  `agency_code` varchar(16) DEFAULT NULL,
  `agency_name` varchar(56) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `duty` varchar(24) DEFAULT NULL,
  `parent_agency_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`agency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agency
-- ----------------------------
INSERT INTO `agency` VALUES ('1', '001', '管理部', null, null, null);
INSERT INTO `agency` VALUES ('2', '002', '人事部', null, null, null);
INSERT INTO `agency` VALUES ('3', '003', '市场部', null, null, null);
