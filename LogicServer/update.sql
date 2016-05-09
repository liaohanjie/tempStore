#2015.10.23
DROP TABLE IF EXISTS t_soul;
DROP TABLE IF EXISTS t_monster;
DROP TABLE IF EXISTS t_chapter;
DROP TABLE IF EXISTS t_chapter_round;
DROP TABLE IF EXISTS t_drop;
DROP TABLE IF EXISTS t_box;
DROP TABLE IF EXISTS t_user_rule;
DROP TABLE IF EXISTS t_soul_evolution;
DROP TABLE IF EXISTS t_prop;
DROP TABLE IF EXISTS t_prop_effect;
DROP TABLE IF EXISTS t_equipment;
DROP TABLE IF EXISTS t_equipment_effect;
DROP TABLE IF EXISTS t_equipment_repair;
DROP TABLE IF EXISTS t_stuff;
DROP TABLE IF EXISTS t_buding_drop;
DROP TABLE IF EXISTS t_buding_rule;
DROP TABLE IF EXISTS t_buding;
DROP TABLE IF EXISTS t_active_skill;
DROP TABLE IF EXISTS t_active_skill_effect;
DROP TABLE IF EXISTS t_cap_skill;
DROP TABLE IF EXISTS t_cap_skill_effect;
DROP TABLE IF EXISTS t_social_skill_rule;
DROP TABLE IF EXISTS t_friend_gifi_rule;
DROP TABLE IF EXISTS t_goods_synthesis;
DROP TABLE IF EXISTS t_goods_synthesis_rule;
DROP TABLE IF EXISTS t_achieve;
DROP TABLE IF EXISTS t_achieve_award;
DROP TABLE IF EXISTS t_bak_prop;
DROP TABLE IF EXISTS t_athleticsinfo_award;
DROP TABLE IF EXISTS t_vip_privilege;
DROP TABLE IF EXISTS t_chapter_join;
DROP TABLE IF EXISTS t_exploration_award;
DROP TABLE IF EXISTS t_growth_fund_rule;
DROP TABLE IF EXISTS t_vip_week_award;
DROP TABLE IF EXISTS t_exploration_exp;
DROP TABLE IF EXISTS t_mission_award;
DROP TABLE IF EXISTS t_mission_condition;
DROP TABLE IF EXISTS t_mission;
DROP TABLE IF EXISTS t_equipment_skill;
DROP TABLE IF EXISTS t_totem_rule;
DROP TABLE IF EXISTS t_totem_soul;
DROP TABLE IF EXISTS t_call_rule;
DROP TABLE IF EXISTS t_call_rule_soul;
DROP TABLE IF EXISTS t_coin_hand;
DROP TABLE IF EXISTS t_coin_hand_rule;
DROP TABLE IF EXISTS t_stamina;

#2015.10.26
DROP TABLE IF EXISTS t_total_login_gift;
DROP TABLE IF EXISTS t_ontime_login_gift;
DROP TABLE IF EXISTS t_activity_cfg;
DROP TABLE IF EXISTS t_drop_rate_multiple;
DROP TABLE IF EXISTS t_product;
DROP TABLE IF EXISTS t_product_item;
DROP TABLE IF EXISTS t_call_soul_rule;
DROP TABLE IF EXISTS t_mall;
DROP TABLE IF EXISTS t_social_skill;
DROP TABLE IF EXISTS t_social_skill_effect;
DROP TABLE IF EXISTS t_activity_call_soul_rule;
DROP TABLE IF EXISTS t_activity_gift;
DROP TABLE IF EXISTS t_activity_soul_rule;
DROP TABLE IF EXISTS t_call_soul_notice;
DROP TABLE IF EXISTS t_arena_rule;

DROP TABLE IF EXISTS t_activity_define;
DROP TABLE IF EXISTS t_buy_coin_gift;
DROP TABLE IF EXISTS t_drop_rate_multiple;
DROP TABLE IF EXISTS t_flash_gift_bag;
DROP TABLE IF EXISTS t_zone_config;


#2015.10.29 db_soul_call_cfg
DROP TABLE IF EXISTS t_call_soul_rule;
DROP TABLE IF EXISTS t_activity_call_soul_rule;


#2015.11.25 ===========BOSS数据表=======db_soul_call=================

ALTER TABLE t_user ADD COLUMN point INT(11) DEFAULT 0 AFTER  first_currency;

CREATE TABLE `t_user_boss_record` (
  `user_id` int(11) NOT NULL,
  `cur_boss_id` int(11) NOT NULL,
  `cur_boss_level` int(11) NOT NULL COMMENT '当前攻打的boss等级',
  `version` varchar(20) NOT NULL COMMENT '当前参加活动的版本',
  `total_hurt` bigint(20) NOT NULL COMMENT '本次活动造成总的伤害',
  `next_fight_time` bigint(20) NOT NULL COMMENT '下次战斗冷却时间',
  `inspired_value` int(11) NOT NULL COMMENT '鼓舞值',
  `receieve_join` int(11) NOT NULL COMMENT '是否领取参与奖励',
  `receieve_rank` int(11) NOT NULL COMMENT '是否领取排名奖励',
  PRIMARY KEY (`user_id`,`cur_boss_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_world_boss_record` (
  `boss_id` int(11) NOT NULL,
  `version` varchar(20) NOT NULL COMMENT '当前boss版本',
  `level` int(11) NOT NULL COMMENT '当前boss等级',
  `cur_blood` bigint(20) NOT NULL COMMENT 'boss当前血量',
  `max_blood` bigint(20) NOT NULL COMMENT '最大血量',
  `open` int(11) NOT NULL COMMENT '活动开启状态',
  `end_time` bigint(20) NOT NULL COMMENT '结束时间',
  `next_begin_time` bigint(20) NOT NULL COMMENT '下次开启时间',
  PRIMARY KEY (`boss_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



#2015.11.25 ============BOSS数据表======db_soul_call_cfg=================

CREATE TABLE `t_boss_setting` (
  `boss_id` int(11) NOT NULL,
  `level` int(11) NOT NULL COMMENT '等级',
  `map` varchar(50) NOT NULL COMMENT '地图背景',
  `monsters` varchar(50) NOT NULL COMMENT '怪物列表',
  `blood` bigint(20) NOT NULL COMMENT 'boss的总血量',
  `kill_reward` text NOT NULL COMMENT '击杀奖励',
  `hurt` bigint(20) NOT NULL COMMENT '伤害奖励核算值',
  `hurt_reward` text NOT NULL COMMENT '伤害奖励'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_bossopen_setting` (
  `boss_id` int(11) NOT NULL,
  `weeks` text NOT NULL COMMENT '忽略，目前为无效字段',
  `begin_end_times` text NOT NULL COMMENT '开始结束时间段',
  `cost_gold` int(11) NOT NULL COMMENT '金币鼓舞消耗金币',
  `gold_add` int(11) NOT NULL COMMENT '金币鼓舞增加值',
  `cost_diamond` int(11) NOT NULL COMMENT '钻石鼓舞消耗钻石',
  `diamond_add` int(11) NOT NULL COMMENT '钻石鼓舞增加值',
  `up_limit` int(11) NOT NULL COMMENT '鼓舞上限',
  `join_level` int(11) NOT NULL COMMENT '参与等级',
  `join_rewards` text NOT NULL,
  PRIMARY KEY (`boss_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_bossrank_reward_setting` (
  `boss_id` varchar(255) DEFAULT NULL,
  `rank` varchar(255) DEFAULT NULL COMMENT '排名',
  `rewards` varchar(255) DEFAULT NULL COMMENT '奖励'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#2016.01.11  修复精炼卷轴BUG
ALTER TABLE t_user_goods_0 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;
ALTER TABLE t_user_goods_1 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;
ALTER TABLE t_user_goods_2 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;
ALTER TABLE t_user_goods_3 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;
ALTER TABLE t_user_goods_4 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;
ALTER TABLE t_user_goods_5 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;
ALTER TABLE t_user_goods_6 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;
ALTER TABLE t_user_goods_7 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;
ALTER TABLE t_user_goods_8 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;
ALTER TABLE t_user_goods_9 ADD COLUMN eq_skill_level int(10) NOT NULL DEFAULT 1 AFTER eq_skill_id;



#===================交换竞技场功能(db_soul_call_cfg表)==================================
CREATE TABLE `t_robot` (
  `id` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `playerName` varchar(255) DEFAULT NULL,
  `templateId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_team_template` (
  `templateId` int(11) NOT NULL,
  `level1` int(11) NOT NULL,
  `level2` int(11) NOT NULL,
  `level3` int(11) NOT NULL,
  `level4` int(11) NOT NULL,
  `level5` int(11) NOT NULL,
  `soul1` int(11) NOT NULL,
  `soul2` int(11) NOT NULL,
  `soul3` int(11) NOT NULL,
  `soul4` int(11) NOT NULL,
  `soul5` int(11) NOT NULL,
  PRIMARY KEY (`templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_swaparena_reward_setting` (
  `rank` int(11) NOT NULL,
  `rewards` text NOT NULL,
  PRIMARY KEY (`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_swaparena_buy_setting` (
  `times` int(11) NOT NULL,
  `cost` int(11) NOT NULL,
  PRIMARY KEY (`times`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


#===================交换竞技场功能(db_soul_call表)==================================

CREATE TABLE `t_swap_arena` (
  `id` int(11) NOT NULL,
  `buyTimes` int(11) NOT NULL,
  `lastUpdateTime` bigint(20) NOT NULL,
  `nextFightTime` bigint(20) NOT NULL,
  `rank` int(11) NOT NULL,
  `robot` bit(1) NOT NULL,
  `times` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `t_swaparena_fight_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attackId` int(11) NOT NULL,
  `attackName` varchar(255) NOT NULL,
  `attackSoulId` int(11) NOT NULL,
  `attackNewRank` int(11) NOT NULL,
  `attackOldRank` int(11) NOT NULL,
  `defendId` int(11) NOT NULL,
  `defendName` varchar(255) NOT NULL,
  `defendNewRank` int(11) NOT NULL,
  `defendOldRank` int(11) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `defendSoulId` int(11) NOT NULL,
  `win` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE t_user ADD COLUMN honor INT(11) DEFAULT 0 AFTER  point;

#好友送礼优化

ALTER TABLE t_friend_gift_0 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;
ALTER TABLE t_friend_gift_1 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;
ALTER TABLE t_friend_gift_2 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;
ALTER TABLE t_friend_gift_3 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;
ALTER TABLE t_friend_gift_4 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;
ALTER TABLE t_friend_gift_5 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;
ALTER TABLE t_friend_gift_6 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;
ALTER TABLE t_friend_gift_7 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;
ALTER TABLE t_friend_gift_8 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;
ALTER TABLE t_friend_gift_9 ADD COLUMN receieve bit(1) DEFAULT FALSE AFTER  create_time;


#===============================战斗校验===============================

CREATE TABLE `t_user_fight_check` (
  `userId` int(11) NOT NULL,
  `battleType` int(11) DEFAULT NULL,
  `pass` bit(1) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_user_doubtcheck_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clientData` text NOT NULL,
  `commiter` int(11) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `serverData` text NOT NULL,
  `reason` varchar(255) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#配置表部分
CREATE TABLE `t_check_config` (
  `id` int(11) NOT NULL COMMENT '主键id',
  `open` bit(1) NOT NULL COMMENT '是否开启校验',
  `attRange` float NOT NULL COMMENT '攻击力浮动',
  `defRange` float NOT NULL COMMENT '防御浮动',
  `hpRange` float NOT NULL COMMENT '生命浮动',
  `replyRange` float NOT NULL COMMENT '恢复力浮动',
  `skillDamageRange` float NOT NULL COMMENT '带技能伤害浮动',
  `noSkillDamageRange` float NOT NULL COMMENT '普通攻击伤害浮动',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#=============================禁言===============================
ALTER TABLE t_user ADD COLUMN ban_account_time datetime DEFAULT '1990-01-01 00:00:00' AFTER  honor;
ALTER TABLE t_user ADD COLUMN ban_chat_time datetime DEFAULT '1990-01-01 00:00:00' AFTER  ban_account_time;

#============================工会=================================
CREATE TABLE `t_alliance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descs` varchar(255) DEFAULT NULL,
  `devote` bigint(20) NOT NULL,
  `allianceLevel` int(11) NOT NULL,
  `allianceName` varchar(255) DEFAULT NULL,
  `nextRefreshTime` bigint(20) NOT NULL,
  `notice` varchar(255) DEFAULT NULL,
  `ownerUserId` int(11) NOT NULL,
  `todayDevote` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_alliance_members`;
CREATE TABLE `t_alliance_members` (
  `id` int(11) NOT NULL,
  `members` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_user_alliance`;
CREATE TABLE `t_user_alliance` (
  `userId` int(11) NOT NULL,
  `allianceId` int(11) NOT NULL,
  `currencyBuild` int(11) NOT NULL,
  `devote` bigint(20) NOT NULL,
  `generalBuild` int(11) NOT NULL,
  `goldBuild` int(11) NOT NULL,
  `nextRefreshTime` bigint(20) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



#============================工会配置=================================
DROP TABLE IF EXISTS `t_alliance_setting`;
CREATE TABLE `t_alliance_setting` (
  `level` int(11) NOT NULL,
  `capacity` int(11) NOT NULL,
  `costDevote` int(11) NOT NULL,
  `costGold` int(11) NOT NULL,
  `maxLevel` int(11) NOT NULL,
  PRIMARY KEY (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_alliance_shopitem`;
CREATE TABLE `t_alliance_shopitem` (
  `id` int(11) NOT NULL,
  `goodsId` int(11) NOT NULL,
  `goodsType` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `allianceLevel` int(11) NOT NULL,
  `costDevote` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE t_soul ADD COLUMN addDevote int(11) DEFAULT 0 AFTER  marquee;
ALTER TABLE t_stuff ADD COLUMN addDevote int(11) DEFAULT 0 AFTER  sell_gold;



