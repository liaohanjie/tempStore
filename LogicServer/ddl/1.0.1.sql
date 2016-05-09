
-- 更新配置表
-- t_vip_rpivilege, t_vip_week_award
-- t_totem_rule, t_totem_soul
-- t_stmamina
-- t_coin_hand, t_coin_hand_rule
-- t_call_rule, t_call_rule_soul

-- 添加表

-- 添加需要字段

ALTER TABLE `t_user_stat` ADD COLUMN `coin_hand_num`  int NULL DEFAULT 0 COMMENT '点金手当天已使用次数，0点置0' AFTER `activity_recharge_curreny`;
ALTER TABLE `t_user_stat` ADD COLUMN `call_soul_friend_point_num`  int NULL DEFAULT 0 COMMENT '友情点召唤次数' AFTER `coin_hand_num`, ADD COLUMN `call_soul_currency_num`  int NULL DEFAULT 0 COMMENT '魂钻召唤次数' AFTER `call_soul_friend_point_num`;
ALTER TABLE `t_user_stat` ADD COLUMN `day_total_currency`  int NULL DEFAULT 0 COMMENT '每日充值累计' AFTER `call_soul_currency_num`, ADD COLUMN `activity_continuous_recharge_count`  int NULL DEFAULT 0 COMMENT '活动内连续充值次数(连续充值活动,按天算连续)' AFTER `day_total_currency`, ADD COLUMN `last_recharge_time`  datetime NULL COMMENT '最后充值时间' AFTER `activity_continuous_recharge_count`, ADD COLUMN `activity_total_cost_currency`  int NULL DEFAULT 0 COMMENT '活动内累计消费额' AFTER `last_recharge_time`, ADD COLUMN `online_gift_get_count`  int NULL DEFAULT 0 COMMENT '在线礼包领取次数(按天)' AFTER `activity_total_cost_currency`;
ALTER TABLE `t_user_stat` ADD COLUMN `activity_continuous_recharge_get_mark`  int NULL DEFAULT 0 COMMENT '活动连续领取记录(二进制标记)' AFTER `online_gift_get_count`;
ALTER TABLE `t_exploration_award` ADD COLUMN `weight`  int NULL DEFAULT 0 COMMENT '权重' AFTER `rate`;