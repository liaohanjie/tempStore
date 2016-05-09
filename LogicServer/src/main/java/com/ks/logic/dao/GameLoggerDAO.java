package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.exceptions.GameException;
import com.ks.model.filter.GameLoggerFilter;
import com.ks.model.logger.ActivityGiftLogger;
import com.ks.model.logger.AthleticsInfoLog;
import com.ks.model.logger.BakPropLogger;
import com.ks.model.logger.CurrencyLogger;
import com.ks.model.logger.ExpLogger;
import com.ks.model.logger.FriendlyPointLogger;
import com.ks.model.logger.GameLogger;
import com.ks.model.logger.GoldLogger;
import com.ks.model.logger.GoodsLogger;
import com.ks.model.logger.SoulLogger;
import com.ks.model.logger.StaminaLogger;
import com.ks.model.logger.SweepCountLogger;

/**
 * 游戏日志
 * 
 * @author ks
 */
public class GameLoggerDAO extends GameDAOTemplate {
	/**
	 * 添加游戏日志
	 * 
	 * @param logger
	 */
	public void addGameLogger(GameLogger logger) {
		String sql = "insert into "
				+ getTableName(logger)
				+ "(user_id,logger_type,type,ass_id,num,description,create_time) values(?,?,?,?,?,?,now());";
		saveOrUpdate(sql, logger.getUserId(), logger.getLoggerType(),
				logger.getType(), logger.getAssId(), logger.getNum(),
				logger.getDescription());
	}

	private String getTableName(GameLogger logger) {
		switch (logger.getLoggerType()) {
		case GameLogger.LOGGER_TYPE_SOUL:
			return "t_soul_logger";
		case GameLogger.LOGGER_TYPE_STAMINA:
			return "t_stamina_logger";
		case GameLogger.LOGGER_TYPE_GOLD:
			return "t_gold_logger";
		case GameLogger.LOGGER_TYPE_EXP:
			return "t_exp_logger";
		case GameLogger.LOGGER_TYPE_CURRENCY:
			return "t_currency_logger";
		case GameLogger.LOGGER_TYPE_PROP:
		case GameLogger.LOGGER_TYPE_EQUIPMENT:
		case GameLogger.LOGGER_TYPE_STUFF:
		case GameLogger.LOGGER_TYPE_BAK_PROP:
			return "t_goods_logger";
		case GameLogger.LOGGER_TYPE_FRIENDLY_POINT:
			return "t_friendly_point_logger";
		case GameLogger.LOGGER_TYPE_PVP_POINT:
			return "t_athletics_info_log";
		case GameLogger.LOGGER_TYPE_PVP_INTEGRAL:
			return "t_athletics_info_log";
		case GameLogger.LOGGER_TYPE_SWEEP_COUNT:
			return "t_sweepcount_logger";
		case GameLogger.LOGGER_TYPE_ACTIVITY_GIFT:
			return "t_activity_gift_logger";
		default:
			throw new GameException(GameException.CODE_参数错误, "");
		}
	}

	/**
	 * 战魂日志字段映射
	 */
	private static final RowMapper<SoulLogger> SOULL_OGGER_ROW_MAPPER = new RowMapper<SoulLogger>() {
		@Override
		public SoulLogger rowMapper(ResultSet rs) throws SQLException {
			SoulLogger obj = new SoulLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	/**
	 * 体力日志字段映射
	 */
	private static final RowMapper<StaminaLogger> STAMINAl_LOGGER_ROW_MAPPER = new RowMapper<StaminaLogger>() {
		@Override
		public StaminaLogger rowMapper(ResultSet rs) throws SQLException {
			StaminaLogger obj = new StaminaLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	/**
	 * 金币日志字段映射
	 */
	private static final RowMapper<GoldLogger> GOLD_LOGGER_ROW_MAPPER = new RowMapper<GoldLogger>() {
		@Override
		public GoldLogger rowMapper(ResultSet rs) throws SQLException {
			GoldLogger obj = new GoldLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	/**
	 * pvp竞技点日志字段映射
	 */
	private static final RowMapper<AthleticsInfoLog> ATHLETICS_LOGGER_ROW_MAPPER = new RowMapper<AthleticsInfoLog>() {
		@Override
		public AthleticsInfoLog rowMapper(ResultSet rs) throws SQLException {
			AthleticsInfoLog obj = new AthleticsInfoLog();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	/**
	 * 友情点日志字段映射
	 */
	private static final RowMapper<FriendlyPointLogger> FRIENDLYPOINT_LOGGER_ROW_MAPPER = new RowMapper<FriendlyPointLogger>() {

		@Override
		public FriendlyPointLogger rowMapper(ResultSet rs) throws SQLException {
			FriendlyPointLogger obj = new FriendlyPointLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	/**
	 * 扫荡次数日志字段映射
	 */
	private static final RowMapper<SweepCountLogger> SWEEPCOUNT_LOGGER_ROW_MAPPER = new RowMapper<SweepCountLogger>() {
		@Override
		public SweepCountLogger rowMapper(ResultSet rs) throws SQLException {
			SweepCountLogger obj = new SweepCountLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	/**
	 * 货币日志字段映射
	 */
	private static final RowMapper<CurrencyLogger> CURRENCY_LOGGER_ROW_MAPPER = new RowMapper<CurrencyLogger>() {
		@Override
		public CurrencyLogger rowMapper(ResultSet rs) throws SQLException {
			CurrencyLogger obj = new CurrencyLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	/**
	 * 经验日志字段映射
	 */
	private static final RowMapper<ExpLogger> EXP_LOGGER_ROW_MAPPER = new RowMapper<ExpLogger>() {
		@Override
		public ExpLogger rowMapper(ResultSet rs) throws SQLException {
			ExpLogger obj = new ExpLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};

	/**
	 * 物品日志字段映射
	 */
	private static final RowMapper<GoodsLogger> GOODS_LOGGER_ROW_MAPPER = new RowMapper<GoodsLogger>() {
		@Override
		public GoodsLogger rowMapper(ResultSet rs) throws SQLException {
			GoodsLogger obj= new GoodsLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	/**
	 * 副本道具日志字段映射
	 */
	private static final RowMapper<BakPropLogger> BAKPROP_LOGGER_ROW_MAPPER = new RowMapper<BakPropLogger>() {

		@Override
		public BakPropLogger rowMapper(ResultSet rs) throws SQLException {
			BakPropLogger obj = new BakPropLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	private static final RowMapper<ActivityGiftLogger> ACTIVITY_GIFT_LOGGER_ROW_MAPPER = new RowMapper<ActivityGiftLogger>() {

		@Override
		public ActivityGiftLogger rowMapper(ResultSet rs) throws SQLException {
			ActivityGiftLogger obj = new ActivityGiftLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setLoggerType(rs.getInt("logger_type"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
  /**
   * 游戏日志FilterSQL
   * @param filter
   * @param sql
   * @param val
   */
	private void excuteFilterSQL(GameLoggerFilter filter, StringBuffer sql,
			List<Object> val) {
		if (filter.getUserId() != null) {
			sql.append(" and user_id like concat('%',?,'%') ");
			val.add(filter.getUserId()); 
		}
		if (filter.getType() != null) {
			sql.append(" and type = ? " );
			val.add(filter.getType());
		}
		if (filter.getStartTime() != null) {
			sql.append(" and  create_time >=  ?  ");
			val.add(filter.getStartTime());
		}
		if (filter.getEndTime() != null) {
			sql.append(" and  create_time <= ?  ");
			val.add(filter.getEndTime());
		}
	}
	
	/**
	 * 查询战魂日志
	 * @param filter
	 * @return
	 */
	public List<SoulLogger> getSoulLoggers(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_soul_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), SOULL_OGGER_ROW_MAPPER,
				val.toArray());
	}
	
   /**
    * 得到战魂日志总数量
    * @param filter
    * @return
    */
	public Integer getSoulLoggersCount(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_soul_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
    /**
     * 查询体力日志
     * @param filter
     * @return
     */
	public List<StaminaLogger> getStaminLoggers(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_stamina_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), STAMINAl_LOGGER_ROW_MAPPER,
				val.toArray());
	}
	
    /**
     * 得到体力日志总数量
     * @param filter
     * @return
     */
	public Integer getStaminLoggersCount(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_stamina_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询金币日志
	 * @param filter
	 * @return
	 */
	public List<GoldLogger> getGoldLoggers(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_gold_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), GOLD_LOGGER_ROW_MAPPER,
				val.toArray());
	}
     
	/**
	 * 得到金币日志总数量
	 * @param filter
	 * @return
	 */
 	public Integer getGoldLoggersCount(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_gold_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
 	
 	/**
 	 * 查询货币日志
 	 * @param filter
 	 * @return
 	 */
	public List<CurrencyLogger> getCurrencyLoggers(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_currency_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), CURRENCY_LOGGER_ROW_MAPPER,
				val.toArray());
	}
	
   /**
    * 得到货币日志总数量
    * @param filter
    * @return
    */
	public Integer getCurrencyLoggersCount(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_currency_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询经验日志
	 * @param filter
	 * @return
	 */
	public List<ExpLogger> getExpLoggers(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_exp_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), EXP_LOGGER_ROW_MAPPER,
				val.toArray());
	}
	
	/**
	 * 得到经验日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getExpLoggersCount(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_exp_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询pvp竞技点日志
	 * @param filter
	 * @return
	 */
	public List<AthleticsInfoLog> getAthleticsPointLoggers(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_athletics_info_log where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 11 ");
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), ATHLETICS_LOGGER_ROW_MAPPER,
				val.toArray());
	}
	
	/**
	 * 得到pvp竞技点日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getAthleticsPointLoggersCount(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_athletics_info_log where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 11 ");
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询pvp竞技分数日志
	 * @param filter
	 * @return
	 */
	public List<AthleticsInfoLog> getAthleticsScoreLoggers(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_athletics_info_log where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 12 ");
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), ATHLETICS_LOGGER_ROW_MAPPER,
				val.toArray());
	}
	
	/**
	 * 得到pvp竞技分数日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getAthleticsScoreLoggersCount(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_athletics_info_log where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 12 ");
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询扫荡次数日志
	 * @param filter
	 * @return
	 */
	public List<SweepCountLogger> getSweppCountLoggers(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_sweepcount_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), SWEEPCOUNT_LOGGER_ROW_MAPPER,
				val.toArray());
	}
	
	/**
	 * 得到扫荡次数日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getSweppCountLoggersCount(GameLoggerFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_sweepcount_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	  
	/**
	 * 查询友情点日志
	 * @param filter
	 * @return
	 */
	public List<FriendlyPointLogger> getFriendlyPointLoggers(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer("select * from t_friendly_point_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), FRIENDLYPOINT_LOGGER_ROW_MAPPER,val.toArray());
	}
	
	/**
	 * 得到友情点日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getFriendlyPointLoggersCount(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_friendly_point_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询道具日志
	 * @param filter
	 * @return
	 */
	public List<GoodsLogger> getPropLoggers(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select * from t_goods_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 6 ");
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), GOODS_LOGGER_ROW_MAPPER, val.toArray());
	}
	
	/**
	 *得到道具日志总数量 
	 * @param filter
	 * @return
	 */
	public Integer getPropLoggersCount(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select count(1) from t_goods_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 6 ");
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询装备日志
	 * @param filter
	 * @return
	 */
	public List<GoodsLogger> getEquipmentLoggers(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select * from t_goods_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 7 ");
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), GOODS_LOGGER_ROW_MAPPER, val.toArray());
	}
    
	/**
	 * 得到装备日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getEquipmentLoggersCount(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select count(1) from t_goods_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 7 ");
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询材料日志
	 * @param filter
	 * @return
	 */
	public List<GoodsLogger> getStuffLoggers(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select * from t_goods_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 8 ");
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), GOODS_LOGGER_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 得到材料日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getStuffLoggersCount(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select count(1) from t_goods_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 8 ");
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询副本道具日志
	 * @param filter
	 * @return
	 */
	public List<BakPropLogger> getBakPropLoggers(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select * from t_goods_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 10 ");
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), BAKPROP_LOGGER_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 得到副本道具日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getBakPropLoggersCount(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select count(1) from t_goods_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		sql.append(" and logger_type = 10 ");
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询礼包领取数
	 * @param filter
	 * @return
	 */
	public Integer getActivityGiftLoggersCount(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select count(1) from t_activity_gift_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
	
	/**
	 * 查询活动礼包领取日志
	 * @param filter
	 * @return
	 */
	public List<ActivityGiftLogger> getActivityGiftLoggers(GameLoggerFilter filter){
		StringBuffer sql = new StringBuffer(" select * from t_activity_gift_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), ACTIVITY_GIFT_LOGGER_ROW_MAPPER, val.toArray());
	}
}