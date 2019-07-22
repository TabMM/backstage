/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : laity

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-01-04 14:01:20
*/

SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for `LAITY_SYS_USER`  用户名
-- ----------------------------
DROP TABLE IF EXISTS `LAITY_SYS_USER`;
CREATE TABLE `LAITY_SYS_USER`
(
    `user_id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `username`       varchar(50)  DEFAULT NULL COMMENT '用户名',
    `name`           varchar(100) DEFAULT NULL,
    `password`       varchar(50)  DEFAULT NULL COMMENT '密码',
    `dept_id`        bigint(20)   DEFAULT NULL,
    `email`          varchar(100) DEFAULT NULL COMMENT '邮箱',
    `mobile`         varchar(100) DEFAULT NULL COMMENT '手机号',
    `status`         tinyint(255) DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
    `user_id_create` bigint(255)  DEFAULT NULL COMMENT '创建用户id',
    `gmt_create`     datetime     DEFAULT NULL COMMENT '创建时间',
    `gmt_modified`   datetime     DEFAULT NULL COMMENT '修改时间',
    `sex`            bigint(32)   DEFAULT NULL COMMENT '性别',
    `birth`          datetime     DEFAULT NULL COMMENT '出身日期',
    `pic_id`         bigint(32)   DEFAULT NULL,
    `live_address`   varchar(500) DEFAULT NULL COMMENT '现居住地',
    `hobby`          varchar(255) DEFAULT NULL COMMENT '爱好',
    `province`       varchar(255) DEFAULT NULL COMMENT '省份',
    `city`           varchar(255) DEFAULT NULL COMMENT '所在城市',
    `district`       varchar(255) DEFAULT NULL COMMENT '所在地区',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 138
  DEFAULT CHARSET = utf8;


-- ----------------------------
-- Table structure for `LAITY_SYS_LOG` 系统日志
-- ----------------------------
DROP TABLE IF EXISTS `LAITY_SYS_LOG`;
CREATE TABLE `LAITY_SYS_LOG`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`    bigint(20)    DEFAULT NULL COMMENT '用户id',
    `username`   varchar(50)   DEFAULT NULL COMMENT '用户名',
    `operation`  varchar(50)   DEFAULT NULL COMMENT '用户操作',
    `time`       int(11)       DEFAULT NULL COMMENT '响应时间',
    `method`     varchar(200)  DEFAULT NULL COMMENT '请求方法',
    `params`     varchar(5000) DEFAULT NULL COMMENT '请求参数',
    `ip`         varchar(64)   DEFAULT NULL COMMENT 'IP地址',
    `gmt_create` datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 372
  DEFAULT CHARSET = utf8 COMMENT ='系统日志';

-- ----------------------------
-- Table structure for `sys_dept` 部门管理
-- ----------------------------
DROP TABLE IF EXISTS `LAITY_SYS_DEPT`;
CREATE TABLE `LAITY_SYS_DEPT`
(
    `dept_id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `parent_id` bigint(20)  DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
    `name`      varchar(50) DEFAULT NULL COMMENT '部门名称',
    `order_num` int(11)     DEFAULT NULL COMMENT '排序',
    `del_flag`  tinyint(4)  DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
    PRIMARY KEY (`dept_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8 COMMENT ='部门管理';


-- ----------------------------
-- Table structure for `LAITY_SYS_FILE` 文件上传
-- ----------------------------
DROP TABLE IF EXISTS `LAITY_SYS_FILE`;
CREATE TABLE `LAITY_SYS_FILE` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `type` int(11) DEFAULT NULL COMMENT '文件类型',
    `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COMMENT='文件上传';


-- ----------------------------
-- Table structure for `LAITY_SYS_MENU` 菜单管理
-- ----------------------------
DROP TABLE IF EXISTS `LAITY_SYS_MENU`;
CREATE TABLE `LAITY_SYS_MENU` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COMMENT='菜单管理';


-- ----------------------------
-- Table structure for `LAITY_SYS_LOG` 登录日志
-- ----------------------------
DROP TABLE IF EXISTS `LAITY_SSO_LOG`;
CREATE TABLE `LAITY_SSO_LOG`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`    bigint(20)    DEFAULT NULL COMMENT '用户id',
    `username`   varchar(50)   DEFAULT NULL COMMENT '用户名',
    `operation`  varchar(50)   DEFAULT NULL COMMENT '用户操作',
    `time`       int(11)       DEFAULT NULL COMMENT '响应时间',
    `method`     varchar(200)  DEFAULT NULL COMMENT '请求方法',
    `params`     varchar(5000) DEFAULT NULL COMMENT '请求参数',
    `ip`         varchar(64)   DEFAULT NULL COMMENT 'IP地址',
    `gmt_create` datetime      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 372
  DEFAULT CHARSET = utf8 COMMENT ='登录日志';
