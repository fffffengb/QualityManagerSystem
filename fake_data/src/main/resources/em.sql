/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : em

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 03/02/2020 20:50:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型：2，3，4',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '总经理，经理，大组长',
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_permission`;
CREATE TABLE `auth_role_permission`  (
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permission_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `auth_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `auth_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `username` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role`  (
  `employee_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`employee_id`, `role_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `auth_user_role_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `auth_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `auth_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_dpt
-- ----------------------------
DROP TABLE IF EXISTS `bs_dpt`;
CREATE TABLE `bs_dpt`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `employee_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sms_dpt_employee_id_9fee3939`(`employee_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_group
-- ----------------------------
DROP TABLE IF EXISTS `bs_group`;
CREATE TABLE `bs_group`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `employee_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sms_group_employee_id_4585153e`(`employee_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_group_stat_member
-- ----------------------------
DROP TABLE IF EXISTS `bs_group_stat_member`;
CREATE TABLE `bs_group_stat_member`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stat_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `morning_shift_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `middle_shift_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `night_shift_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sms_team_stat_member_team_id_81fd299c`(`group_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_member
-- ----------------------------
DROP TABLE IF EXISTS `bs_member`;
CREATE TABLE `bs_member`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `birthplace` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_workshop
-- ----------------------------
DROP TABLE IF EXISTS `bs_workshop`;
CREATE TABLE `bs_workshop`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `employee_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sms_workshop_employee_id_7520ab3b`(`employee_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bs_workshop_group
-- ----------------------------
DROP TABLE IF EXISTS `bs_workshop_group`;
CREATE TABLE `bs_workshop_group`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `workshop_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cfg_work_period
-- ----------------------------
DROP TABLE IF EXISTS `cfg_work_period`;
CREATE TABLE `cfg_work_period`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `start_time` time(6) DEFAULT NULL,
  `end_time` time(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_dpt_avg
-- ----------------------------
DROP TABLE IF EXISTS `d_dpt_avg`;
CREATE TABLE `d_dpt_avg`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dpt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) DEFAULT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_dpt_daily
-- ----------------------------
DROP TABLE IF EXISTS `d_dpt_daily`;
CREATE TABLE `d_dpt_daily`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dpt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` date DEFAULT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_dpt_online
-- ----------------------------
DROP TABLE IF EXISTS `d_dpt_online`;
CREATE TABLE `d_dpt_online`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dpt_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) DEFAULT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_group_avg
-- ----------------------------
DROP TABLE IF EXISTS `d_group_avg`;
CREATE TABLE `d_group_avg`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) DEFAULT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_group_daily
-- ----------------------------
DROP TABLE IF EXISTS `d_group_daily`;
CREATE TABLE `d_group_daily`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` date DEFAULT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_group_online
-- ----------------------------
DROP TABLE IF EXISTS `d_group_online`;
CREATE TABLE `d_group_online`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) DEFAULT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_stat_avg
-- ----------------------------
DROP TABLE IF EXISTS `d_stat_avg`;
CREATE TABLE `d_stat_avg`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  `time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_stat_daily
-- ----------------------------
DROP TABLE IF EXISTS `d_stat_daily`;
CREATE TABLE `d_stat_daily`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_stat_online
-- ----------------------------
DROP TABLE IF EXISTS `d_stat_online`;
CREATE TABLE `d_stat_online`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  `time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_workshop_avg
-- ----------------------------
DROP TABLE IF EXISTS `d_workshop_avg`;
CREATE TABLE `d_workshop_avg`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `workshop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) DEFAULT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_workshop_daily
-- ----------------------------
DROP TABLE IF EXISTS `d_workshop_daily`;
CREATE TABLE `d_workshop_daily`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `workshop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` date DEFAULT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for d_workshop_online
-- ----------------------------
DROP TABLE IF EXISTS `d_workshop_online`;
CREATE TABLE `d_workshop_online`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `workshop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) DEFAULT NULL,
  `quality` decimal(32, 3) DEFAULT NULL,
  `work_hour` decimal(32, 3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
