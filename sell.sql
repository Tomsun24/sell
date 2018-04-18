/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : sell

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2018-04-18 17:35:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) COLLATE utf8_latvian_ci NOT NULL,
  `order_id` varchar(32) COLLATE utf8_latvian_ci NOT NULL,
  `product_id` varchar(32) COLLATE utf8_latvian_ci NOT NULL,
  `product_name` varchar(64) COLLATE utf8_latvian_ci NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '当前价格,单位分',
  `product_quantity` int(11) NOT NULL COMMENT '数量',
  `product_icon` varchar(512) COLLATE utf8_latvian_ci DEFAULT NULL COMMENT '小图',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_latvian_ci;

-- ----------------------------
-- Records of order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `order_master`
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
  `order_id` varchar(32) COLLATE utf8_latvian_ci NOT NULL,
  `buyer_name` varchar(32) COLLATE utf8_latvian_ci NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) COLLATE utf8_latvian_ci NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) COLLATE utf8_latvian_ci NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) COLLATE utf8_latvian_ci NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态, 默认为新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态, 默认未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_buyer_openid` (`buyer_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_latvian_ci;

-- ----------------------------
-- Records of order_master
-- ----------------------------

-- ----------------------------
-- Table structure for `product_category`
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) COLLATE utf8_latvian_ci NOT NULL COMMENT '类目名字',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_latvian_ci;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES ('1', '食物', '2', '2018-04-17 11:18:45', '2018-04-17 11:18:52');
INSERT INTO `product_category` VALUES ('5', 'food', '3', '2018-04-17 16:15:50', '2018-04-17 16:15:50');
INSERT INTO `product_category` VALUES ('8', 'food10', '5', '2018-04-17 16:18:11', '2018-04-17 17:56:40');
INSERT INTO `product_category` VALUES ('9', 'food3', '2', '2018-04-17 16:22:46', '2018-04-17 16:22:46');

-- ----------------------------
-- Table structure for `product_info`
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) COLLATE utf8_latvian_ci NOT NULL,
  `product_name` varchar(64) COLLATE utf8_latvian_ci NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) COLLATE utf8_latvian_ci DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) COLLATE utf8_latvian_ci DEFAULT NULL COMMENT '小图',
  `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_latvian_ci;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('123432435', '火烧', '54.50', '34', '香甜可口', 'http://2314.jpg', '0', '1', '2018-04-18 16:05:04', '2018-04-18 16:05:04');
INSERT INTO `product_info` VALUES ('31323423', '烤肉', '34.40', '100', '非常的好吃', '', '0', '3', '2018-04-18 15:22:28', '2018-04-18 15:22:28');
INSERT INTO `product_info` VALUES ('31423423', '烤鱼', '64.40', '100', '非常的好吃33', 'http://xx.jpg', '0', '2', '2018-04-18 15:24:27', '2018-04-18 15:24:27');
INSERT INTO `product_info` VALUES ('31423443', '烤全羊', '134.40', '103', '非常的好吃5', 'http://xyy.jpg', '0', '1', '2018-04-18 15:25:19', '2018-04-18 15:25:19');

-- ----------------------------
-- Table structure for `seller_info`
-- ----------------------------
DROP TABLE IF EXISTS `seller_info`;
CREATE TABLE `seller_info` (
  `id` varchar(32) COLLATE utf8_latvian_ci NOT NULL,
  `username` varchar(32) COLLATE utf8_latvian_ci NOT NULL,
  `password` varchar(32) COLLATE utf8_latvian_ci NOT NULL,
  `openid` varchar(64) COLLATE utf8_latvian_ci NOT NULL COMMENT '微信openid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_latvian_ci COMMENT='卖家信息表';

-- ----------------------------
-- Records of seller_info
-- ----------------------------
