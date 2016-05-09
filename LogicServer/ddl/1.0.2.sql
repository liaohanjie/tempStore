
-- 添加普通表
CREATE TABLE `t_climb_tower_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '玩家编号',
  `tower_floor` int(11) DEFAULT '0' COMMENT '试练塔第几次',
  `star_count` tinyint(4) DEFAULT '1' COMMENT '通关星级',
  `fight_count` int(4) DEFAULT '0' COMMENT '当天已经战斗次数',
  `buy_fight_count` tinyint(4) DEFAULT '0' COMMENT '当天已经购买战斗次数',
  `update_time` datetime DEFAULT NULL COMMENT '次数数据刷新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_id_tower_floor` (`user_id`,`tower_floor`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬塔试炼用户爬塔层数记录';


CREATE TABLE `t_climb_tower_rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户编号',
  `tower_floor` int(11) DEFAULT '0' COMMENT '试练塔第几次',
  `star_count` int(11) DEFAULT '0' COMMENT '通关星级',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬塔试炼排行榜';

CREATE TABLE `t_climb_tower_award_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `star_region` tinyint(4) DEFAULT '0' COMMENT '星星大区',
  `star_num` tinyint(4) DEFAULT '0' COMMENT '星级总数',
  `user_id` int(11) DEFAULT '0' COMMENT '用户编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_id_start` (`user_id`,`star_region`,`star_num`) USING BTREE,
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试炼爬塔集星奖励领取记录';


-- 添加配置表
CREATE TABLE `t_climb_tower` (
  `tower_floor` int(11) NOT NULL,
  `level` int(11) DEFAULT '0' COMMENT '等级限制',
  `cost_rock` int(11) DEFAULT '0' COMMENT '消耗试炼石',
  `first_award_id` int(11) DEFAULT '0' COMMENT '首次通关奖励',
  `fix_award_id` int(11) DEFAULT '0' COMMENT '固定通关奖励',
  PRIMARY KEY (`tower_floor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬塔试炼配置';

CREATE TABLE `t_climb_tower_award` (
  `id` int(11) NOT NULL DEFAULT '0',
  `goods_id` int(11) NOT NULL DEFAULT '0' COMMENT '物品编号',
  `goods_type` int(11) NOT NULL DEFAULT '0' COMMENT '物品类型',
  `num` int(11) DEFAULT '0' COMMENT '数量',
  `level` int(11) DEFAULT '0' COMMENT '等级',
  `rate` decimal(3,2) DEFAULT '0.00' COMMENT '概率(1必出, 小于1的概率)',
  PRIMARY KEY (`id`,`goods_id`,`goods_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬塔试炼奖励';

CREATE TABLE `t_climb_tower_star` (
  `star_region` int(11) NOT NULL DEFAULT '0' COMMENT '星星区域',
  `star_num` int(11) NOT NULL DEFAULT '0' COMMENT '星星总数',
  `award_id` int(11) DEFAULT '0' COMMENT '奖励',
  PRIMARY KEY (`star_num`,`star_region`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬塔试炼星级配置';
