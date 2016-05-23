/*
Navicat MySQL Data Transfer

Source Server         : 时界内网
Source Server Version : 50624
Source Host           : 192.168.100.80:3306
Source Database       : db_soul_info

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-05-23 11:39:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `game_id` int(11) DEFAULT '0' COMMENT '游戏编号',
  `partner_id` int(11) DEFAULT '0' COMMENT '合作伙伴ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户账号ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '登陆用户名',
  `login_count` int(11) DEFAULT '0' COMMENT '登陆次数',
  `last_login_server_id` int(11) DEFAULT NULL COMMENT '最后登陆区服编号',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `status` tinyint(255) DEFAULT '1' COMMENT '玩家状态(1: 正常， 0: 禁止登陆)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8 COMMENT='游戏账号部分信息';

-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名',
  `permission` varchar(1024) DEFAULT NULL COMMENT '权限',
  `job` varchar(50) DEFAULT NULL COMMENT '职位',
  `des` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_user`;
CREATE TABLE `t_admin_user` (
  `admin_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `role_id` varchar(1024) DEFAULT NULL COMMENT '角色id',
  `create_time` timestamp NOT NULL DEFAULT '1979-01-01 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '1979-01-01 00:00:00',
  `permission` varchar(1024) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `admin_id` (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1218 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_bulletin_logger
-- ----------------------------
DROP TABLE IF EXISTS `t_bulletin_logger`;
CREATE TABLE `t_bulletin_logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(25) DEFAULT NULL COMMENT '操作者',
  `server_id` varchar(25) DEFAULT NULL COMMENT '发送的服务器',
  `content` varchar(1000) DEFAULT NULL COMMENT '发送的游戏公告内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_chat_logger
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_logger`;
CREATE TABLE `t_chat_logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` varchar(50) DEFAULT '0',
  `type` int(11) DEFAULT NULL COMMENT '日志类型（1.系统; 2.世界; 3.私信; 4.公会）',
  `send_id` int(11) DEFAULT NULL COMMENT '发送者',
  `received_id` int(11) DEFAULT NULL COMMENT '接收者',
  `content` varchar(2000) DEFAULT NULL COMMENT '发送内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_game
-- ----------------------------
DROP TABLE IF EXISTS `t_game`;
CREATE TABLE `t_game` (
  `id` int(11) NOT NULL,
  `game_key` varchar(255) DEFAULT '' COMMENT '名称关键字缩写',
  `game_name` varchar(255) DEFAULT '' COMMENT '游戏名称',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态: 0:关闭 1:开启',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_gift
-- ----------------------------
DROP TABLE IF EXISTS `t_gift`;
CREATE TABLE `t_gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gift_name` varchar(255) DEFAULT '',
  `gift_desc` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_gift_code
-- ----------------------------
DROP TABLE IF EXISTS `t_gift_code`;
CREATE TABLE `t_gift_code` (
  `gift_id` int(11) DEFAULT NULL,
  `code` varchar(8) NOT NULL COMMENT '激活码',
  `state` int(4) NOT NULL DEFAULT '0' COMMENT '使用状态',
  `award_id` varchar(32) NOT NULL COMMENT '奖励ID',
  `server_id` varchar(25) NOT NULL COMMENT '服务器ID',
  `create_time` timestamp NOT NULL DEFAULT '1979-01-01 00:00:00' COMMENT '创建时间',
  `use_time` timestamp NULL DEFAULT NULL COMMENT '使用时间',
  `end_time` datetime DEFAULT NULL COMMENT '失效时间',
  `use_type` tinyint(4) DEFAULT NULL COMMENT '使用类型（0:同类型礼包单个无限使用; 1:同类型礼包单个用户单次使用; 2.同类型礼包所有用户单次使用）',
  `valid_type` tinyint(4) DEFAULT NULL COMMENT '有效类型（0:永久有效; 1:固定时间有效）',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_gift_code_award
-- ----------------------------
DROP TABLE IF EXISTS `t_gift_code_award`;
CREATE TABLE `t_gift_code_award` (
  `gift_id` int(11) DEFAULT NULL,
  `award_id` varchar(32) NOT NULL COMMENT '奖励编号',
  `goods_type` int(4) NOT NULL COMMENT '物品类型',
  `ass_id` int(11) NOT NULL COMMENT '物品ID',
  `num` int(11) NOT NULL COMMENT '数量',
  KEY `AWARD_FK_ID` (`gift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_gift_code_logger
-- ----------------------------
DROP TABLE IF EXISTS `t_gift_code_logger`;
CREATE TABLE `t_gift_code_logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户Id',
  `server_id` int(11) DEFAULT NULL COMMENT '区服',
  `code` varchar(8) DEFAULT NULL COMMENT '激活码',
  `award_id` varchar(32) DEFAULT NULL COMMENT '奖励Id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_gift_template
-- ----------------------------
DROP TABLE IF EXISTS `t_gift_template`;
CREATE TABLE `t_gift_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gift_template_name` varchar(255) DEFAULT '' COMMENT '礼包名称',
  `gift_template_goods` varchar(5000) DEFAULT '' COMMENT '礼包模板物品(json格式)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8 COMMENT='礼包模板';

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL,
  `goods_name` varchar(255) DEFAULT '' COMMENT '名称',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `num` int(11) DEFAULT '0' COMMENT '数量(-1: 没有限制)',
  `status` tinyint(4) DEFAULT '0' COMMENT '0: 正常， -1: 删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品';

-- ----------------------------
-- Table structure for t_goods_template
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_template`;
CREATE TABLE `t_goods_template` (
  `goodsId` int(11) NOT NULL DEFAULT '0',
  `name` varchar(50) DEFAULT NULL COMMENT '物品名称',
  `type` int(11) NOT NULL COMMENT '物品类型',
  `level` int(11) DEFAULT NULL COMMENT '物品等级',
  PRIMARY KEY (`goodsId`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `server_no` varchar(24) NOT NULL DEFAULT '0' COMMENT '区服',
  `start_time` timestamp NOT NULL DEFAULT '1979-09-01 00:00:00' COMMENT '开始时间',
  `end_time` timestamp NOT NULL DEFAULT '1979-09-01 00:00:00' COMMENT '结束时间',
  `context` varchar(20480) NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_order_return
-- ----------------------------
DROP TABLE IF EXISTS `t_order_return`;
CREATE TABLE `t_order_return` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT '' COMMENT '用户名',
  `order_no` varchar(255) DEFAULT '' COMMENT '订单号',
  `amount` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  `return_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_partner
-- ----------------------------
DROP TABLE IF EXISTS `t_partner`;
CREATE TABLE `t_partner` (
  `partner_id` int(11) NOT NULL COMMENT '渠道',
  `partner_name` varchar(25) DEFAULT NULL COMMENT '渠道名称',
  `sys_platform` varchar(255) DEFAULT '' COMMENT '系统平台',
  `is_pay` tinyint(1) DEFAULT '1' COMMENT '是否开启充值',
  PRIMARY KEY (`partner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_pay_config
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_config`;
CREATE TABLE `t_pay_config` (
  `id` int(11) NOT NULL,
  `game_id` int(11) DEFAULT '0' COMMENT '游戏ID',
  `partner_id` int(11) DEFAULT '0' COMMENT '渠道合作商编号',
  `pay_channel_id` varchar(255) DEFAULT '' COMMENT '合作商支付渠道编号',
  `pay_channel_name` varchar(255) DEFAULT '' COMMENT '合作商名称',
  `pay_channel_type_name` varchar(255) DEFAULT '' COMMENT '合作商支付渠道类型名',
  `order_prefix` char(2) DEFAULT '' COMMENT '订单前缀(2位)',
  `scale` int(11) DEFAULT '10' COMMENT '比例(人民币：魂钻)， 1: 10',
  `status` tinyint(255) DEFAULT '0' COMMENT '是否启用(0: 不启用 ， 1: 启用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付渠道配置表';

-- ----------------------------
-- Table structure for t_pay_order
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_order`;
CREATE TABLE `t_pay_order` (
  `pay_id` int(44) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `user_partner` int(10) DEFAULT NULL COMMENT '用户渠道',
  `pay_config_id` int(11) DEFAULT '0' COMMENT '支付渠道配置ID，参考 t_pay_config 表',
  `game_coin` int(10) unsigned DEFAULT NULL COMMENT '游戏币数量',
  `gift_coin` int(10) unsigned DEFAULT NULL COMMENT '赠送的游戏币',
  `amount` int(10) unsigned DEFAULT NULL COMMENT '订单金额',
  `server_id` varchar(25) DEFAULT NULL COMMENT '充值的服务器',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `retry_times` int(10) DEFAULT NULL COMMENT '重试次数',
  `status` int(10) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `pay_sucess_time` timestamp NOT NULL DEFAULT '1979-07-01 00:00:00' COMMENT '支付成功是时间（状态1）',
  `delivery_sucess_time` timestamp NOT NULL DEFAULT '1979-07-01 00:00:00' COMMENT '成功发货时间',
  `update_time` timestamp NULL DEFAULT '1979-07-01 00:00:00' COMMENT '修改时间',
  `last_retry_time` timestamp NULL DEFAULT '1979-07-01 00:00:00' COMMENT '最后重试是时间',
  `last_ret_code` varchar(200) DEFAULT NULL COMMENT '最后返回的错误码',
  `goods_id` tinyint(4) DEFAULT '0' COMMENT '0魂钻， 1 黄金月卡 ， 2 钻石月卡 (参考 t_goods)',
  `bill_no` varchar(200) DEFAULT '' COMMENT '渠道商订单号(没有填空)',
  `ext1` text COMMENT '扩展信息1',
  `ext2` varchar(255) DEFAULT '' COMMENT '扩展信息2',
  `ext3` varchar(255) DEFAULT '' COMMENT '扩展信息3',
  PRIMARY KEY (`pay_id`),
  UNIQUE KEY `inx_order_no` (`order_no`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1184 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) NOT NULL COMMENT '父节点IDeas',
  `icon` varchar(10) DEFAULT NULL COMMENT '图标',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `create_time` timestamp NOT NULL DEFAULT '1979-01-01 00:00:00' COMMENT '时间',
  `update_time` timestamp NOT NULL DEFAULT '1979-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_restitution_order_logger
-- ----------------------------
DROP TABLE IF EXISTS `t_restitution_order_logger`;
CREATE TABLE `t_restitution_order_logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(25) DEFAULT NULL COMMENT '操作者',
  `order_no` varchar(64) DEFAULT NULL COMMENT '原始订单号',
  `amount` int(11) DEFAULT NULL COMMENT '充值金额',
  `game_coin` int(11) DEFAULT NULL COMMENT '补偿的游戏币',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_server_info
-- ----------------------------
DROP TABLE IF EXISTS `t_server_info`;
CREATE TABLE `t_server_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `partner` int(10) NOT NULL COMMENT '渠道',
  `server_id` varchar(20) NOT NULL COMMENT '服务器ID',
  `server_no` int(11) DEFAULT '0' COMMENT '区服编号(数字1-N 依次叠加)',
  `server_name` varchar(50) DEFAULT NULL COMMENT '服务器名称',
  `version` int(4) NOT NULL COMMENT '版本',
  `port` int(10) NOT NULL COMMENT '登录服端口',
  `ip` varchar(30) NOT NULL COMMENT '登录服地址',
  `world_port` int(10) DEFAULT NULL,
  `world_ip` varchar(30) DEFAULT NULL,
  `desc` varchar(100) DEFAULT '' COMMENT '描述',
  `pay_notifi_url` varchar(500) DEFAULT NULL,
  `status` int(10) DEFAULT '0' COMMENT '状态(-1: 关闭， 0: 即将开区, 1: 维护，2: 正常, 3:推荐 4: 火爆)',
  `recommend` tinyint(4) DEFAULT '0' COMMENT '1: 推荐 ， 0: 不推荐',
  `main_server_id` int(11) DEFAULT '0' COMMENT '服务器主区ID(0: 表示主区， 其他参数表示所属哪个区服)',
  `start_time` datetime DEFAULT NULL COMMENT '开服时间',
  `maintain_start_time` datetime DEFAULT NULL COMMENT '维护开始时间',
  `maintain_end_time` datetime DEFAULT NULL COMMENT '维护结束时间',
  `maintain_msg` varchar(255) DEFAULT '' COMMENT '维护显示信息',
  `continue_login_cycle` int(11) DEFAULT '7' COMMENT '连续登陆周期，作为连续登陆活动使用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `server_id_unique` (`server_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='服务器信息';

-- ----------------------------
-- Table structure for t_violation_logger
-- ----------------------------
DROP TABLE IF EXISTS `t_violation_logger`;
CREATE TABLE `t_violation_logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `server_id` varchar(50) DEFAULT NULL COMMENT '服务器',
  `type` int(11) DEFAULT NULL COMMENT '违规类型（ 1.禁言;  2.封号）',
  `description` varchar(500) DEFAULT NULL COMMENT '违规备注',
  `forbidden_time` datetime DEFAULT NULL COMMENT '禁用时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
