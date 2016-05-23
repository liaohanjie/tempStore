package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.filter.ViolationLoggerFilter;
import com.ks.model.violation.ViolationLogger;

/**
 * 违规日志
 * 
 * @author lipp 2016年1月23日
 */
public class ViolationLoggerDAO extends GameDAOTemplate {
	private static final RowMapper<ViolationLogger> VIOLATION_LOGGER_MAPPER = new RowMapper<ViolationLogger>() {
		@Override
		public ViolationLogger rowMapper(ResultSet rs) throws SQLException {
			ViolationLogger obj = new ViolationLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setServerId(rs.getString("server_id"));
			obj.setType(rs.getInt("type"));
			obj.setForbideenTime(rs.getTimestamp("forbidden_time"));
			obj.setDescription(rs.getString("description"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};

	/**
	 * 保存违规记录
	 * 
	 * @param logger
	 */
	public void saveViolationLogger(ViolationLogger logger) {
		String sql = "insert into t_violation_logger(user_id,server_id,type,forbidden_time,description,create_time) values(?,?,?,?,?,now())";
		saveOrUpdate(sql, logger.getUserId(), logger.getServerId(), logger.getType(), logger.getForbideenTime(), logger.getDescription());
	}

	/**
	 * 查看违规日志
	 * 
	 * @return
	 */
	public List<ViolationLogger> getViolationLoggers(ViolationLoggerFilter filter) {
		StringBuffer sql = new StringBuffer("select * from t_violation_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), VIOLATION_LOGGER_MAPPER, val.toArray());
	}

	/**
	 * 得到违规日志总数量
	 * 
	 * @param filter
	 * @return
	 */
	public Integer getViolationLoggerCount(ViolationLoggerFilter filter) {
		StringBuffer sql = new StringBuffer("select count(1) from t_violation_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}

	/**
	 * 违规记录查询FilterSQL
	 * 
	 * @param filter
	 * @param sql
	 * @param val
	 */
	private void excuteFilterSQL(ViolationLoggerFilter filter, StringBuffer sql, List<Object> val) {
		if (filter.getUserId() != null) {
			sql.append(" and user_id = ?");
			val.add(filter.getUserId());
		}
		if (filter.getServerId() != null) {
			sql.append(" and server_id = ?");
			val.add(filter.getServerId());
		}
		if (filter.getType() != null) {
			sql.append(" and type = ? ");
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

}
