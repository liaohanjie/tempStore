package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.account.Notice;
import com.ks.model.account.ServerInfo;

/**
 * 
 * @author living.li
 * @date 2014年4月9日
 */
public class ServerInfoDAO extends GameDAOTemplate {

	private static final RowMapper<ServerInfo> SERVER_INFO_ROW_MAPPER = new RowMapper<ServerInfo>() {
		@Override
		public ServerInfo rowMapper(ResultSet rs) throws SQLException {
			ServerInfo obj = new ServerInfo();
			obj.setId(rs.getInt("id"));
			obj.setPartner(rs.getInt("partner"));
			obj.setServerId(rs.getString("server_id"));
			obj.setServerNo(rs.getInt("server_no"));
			obj.setName(rs.getString("server_name"));
			obj.setVersion(rs.getInt("version"));
			obj.setPort(rs.getInt("port"));
			obj.setIp(rs.getString("ip"));
			obj.setWorldPort(rs.getInt("world_port"));
			obj.setWorldIp(rs.getString("world_ip"));
			obj.setDesc(rs.getString("desc"));
			obj.setPayNotifiUrl(rs.getString("pay_notifi_url"));
			obj.setStatus(rs.getInt("status"));
			obj.setRecommend(rs.getInt("recommend"));
			obj.setMainServerId(rs.getInt("main_server_id"));
			obj.setStartTime(rs.getTimestamp("start_time"));
			obj.setMaintainStartTime(rs.getTimestamp("maintain_start_time"));
			obj.setMaintainEndTime(rs.getTimestamp("maintain_end_time"));
			obj.setMaintainMsg(rs.getString("maintain_msg"));
			obj.setContinueLoginCycle(rs.getInt("continue_login_cycle"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};

	private static final RowMapper<Notice> NOTICE_ROW_MAPPER = new RowMapper<Notice>() {
		@Override
		public Notice rowMapper(ResultSet rs) throws SQLException {
			Notice obj = new Notice();
			obj.setId(rs.getInt("id"));
			obj.setServerNo(rs.getString("server_no"));
			obj.setStartTime(rs.getTimestamp("start_time"));
			obj.setEndTime(rs.getTimestamp("end_time"));
			obj.setContext(rs.getString("context"));
			return obj;
		}
	};

	public List<ServerInfo> queryServerList() {
		String sql = "select * from t_server_info";
		return queryForList(sql, SERVER_INFO_ROW_MAPPER);
	}

	public List<Notice> getNotices() {
		String sql = "select * from t_notice where start_time<=now() and end_time>=now() order by id desc";
		return queryForList(sql, NOTICE_ROW_MAPPER);
	}

	public List<Notice> getNoticeAll() {
		String sql = "select * from t_notice order by id desc";
		return queryForList(sql, NOTICE_ROW_MAPPER);
	}

	public void addNotice(Notice notice) {
		String sql = "insert into t_notice (server_no,start_time,end_time,context) values (?,?,?,?)";
		saveOrUpdate(sql, notice.getServerNo(),notice.getStartTime(), notice.getEndTime(), notice.getContext());

	}

	public void updateNotice(Notice notice) {
		String sql = "update t_notice set server_no=? ,start_time=?,end_time=?,context=? where id=?";
		saveOrUpdate(sql, notice.getServerNo(), notice.getStartTime(), notice.getEndTime(), notice.getContext(), notice.getId());
	}

	// public List<ServerInfo> queryServerById(String serverId) {
	// String sql =
	// "select * from t_server_info where  server_id like  CONCAT('%',?,'%')";
	// return queryForList(sql, SERVER_INFO_ROW_MAPPER, serverId);
	// }

	public ServerInfo queryServerInfoById(Integer id) {
		String sql = "select * from t_server_info where id=?";
		return queryForEntity(sql, SERVER_INFO_ROW_MAPPER, id);
	}

	public List<ServerInfo> queryServerById(String serverId) {
		StringBuffer sql = new StringBuffer("select * from t_server_info where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		if (serverId != null) {
			sql.append(" and  server_id  LIKE  CONCAT('%',?,'%') ");
			val.add(serverId);
		}
		return queryForList(sql.toString(), SERVER_INFO_ROW_MAPPER, val.toArray());
	}

	public void updateServerInfo(ServerInfo serverInfo) {
		String sql = "UPDATE `t_server_info` SET " + "`partner` = ?, `server_id` = ?, `server_no` = ?, `server_name` = ?, `version` = ?, "
		        + "`port`= ?, `ip` = ?,`world_port` = ?, `world_ip` = ?, `desc` = ?, `pay_notifi_url` = ? ,"
		        + " `status` = ? , `recommend` = ?, `main_server_id` = ?, `start_time` = ?, "
		        + "`maintain_start_time` = ?, `maintain_end_time` = ?, `maintain_msg`=?, `continue_login_cycle`=?" + " WHERE `id` = ? ";
		saveOrUpdate(sql, serverInfo.getPartner(), serverInfo.getServerId(), serverInfo.getServerNo(), serverInfo.getName(), serverInfo.getVersion(),
		        serverInfo.getPort(), serverInfo.getIp(), serverInfo.getWorldPort(), serverInfo.getWorldIp(), serverInfo.getDesc(),
		        serverInfo.getPayNotifiUrl(), serverInfo.getStatus(), serverInfo.getRecommend(), serverInfo.getMainServerId(), serverInfo.getStartTime(),
		        serverInfo.getMaintainStartTime(), serverInfo.getMaintainEndTime(), serverInfo.getMaintainMsg(), serverInfo.getContinueLoginCycle(),
		        serverInfo.getId());
	}

	public void addServerInfo(ServerInfo serverInfo) {
		String sql = "INSERT INTO `t_server_info` (" + "`partner`, `server_id`, `server_no`, `server_name`, `version`,"
		        + "`port`,`ip`, `world_port`, `world_ip`, `desc`, `pay_notifi_url`," + "`status`, `recommend`, `main_server_id`, `start_time`,"
		        + "`maintain_start_time`, `maintain_end_time`, `maintain_msg`, `create_time`,`continue_login_cycle`) "
		        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?);";
		saveOrUpdate(sql, serverInfo.getPartner(), serverInfo.getServerId(), serverInfo.getServerNo(), serverInfo.getName(), serverInfo.getVersion(),
		        serverInfo.getPort(), serverInfo.getIp(), serverInfo.getWorldPort(), serverInfo.getWorldIp(), serverInfo.getDesc(),
		        serverInfo.getPayNotifiUrl(), serverInfo.getStatus(), serverInfo.getRecommend(), serverInfo.getMainServerId(), serverInfo.getStartTime(),
		        serverInfo.getMaintainStartTime(), serverInfo.getMaintainEndTime(), serverInfo.getMaintainMsg(), serverInfo.getContinueLoginCycle());
	}

	public void deleteNotice(int id) {
		String sql = " delete from t_notice where id=?";
		saveOrUpdate(sql, id);
	}
}
