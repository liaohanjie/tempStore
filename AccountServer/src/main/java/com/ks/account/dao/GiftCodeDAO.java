package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.account.Gift;
import com.ks.model.account.GiftCode;
import com.ks.model.account.GiftCodeAward;
import com.ks.model.account.GiftCodeLogger;

public class GiftCodeDAO extends GameDAOTemplate {

	private static final RowMapper<GiftCode> GIFT_CODE_ROW_MAPPER = new RowMapper<GiftCode>() {
		@Override
		public GiftCode rowMapper(ResultSet rs) throws SQLException {
			GiftCode obj = new GiftCode();
			obj.setCode(rs.getString("code"));
			obj.setState(rs.getInt("state"));
			obj.setAwardId(rs.getString("award_id"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUseTime(rs.getTimestamp("use_time"));
			obj.setServerId(rs.getString("server_id"));
			obj.setEndTime(rs.getDate("end_time"));
			obj.setUseType(rs.getInt("use_type"));
			obj.setValidType(rs.getInt("valid_type"));
			return obj;
		}
	};

	private static final RowMapper<Gift> GIFT__ROW_MAPPER = new RowMapper<Gift>() {
		@Override
		public Gift rowMapper(ResultSet rs) throws SQLException {
			Gift obj = new Gift();
			obj.setId(rs.getInt("id"));
			obj.setName(rs.getString("gift_name"));
			obj.setDesc(rs.getString("gift_desc"));
			return obj;
		}
	};

	private static final RowMapper<GiftCodeAward> GIFT_CODE_AWARD_ROW_MAPPER = new RowMapper<GiftCodeAward>() {
		@Override
		public GiftCodeAward rowMapper(ResultSet rs) throws SQLException {
			GiftCodeAward obj = new GiftCodeAward();
			obj.setAwardId(rs.getString("award_id"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setGoodsType(rs.getInt("goods_type"));
			obj.setNum(rs.getInt("num"));
			// obj.setLevel(rs.getInt("level"));
			return obj;
		}
	};

	private static final RowMapper<GiftCodeLogger> GIFT_CODE__LOGGER_ROW_MAPPER = new RowMapper<GiftCodeLogger>() {
		@Override
		public GiftCodeLogger rowMapper(ResultSet rs) throws SQLException {
			GiftCodeLogger obj = new GiftCodeLogger();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setCode(rs.getString("code"));
			obj.setAwardId(rs.getString("award_id"));
			obj.setAwardId(rs.getString("server_id"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};

	public void addBatchCode(List<GiftCode> codes) {
		List<Object[]> args = new ArrayList<>();
		for (GiftCode code : codes) {
			args.add(new Object[] { code.getGiftId(), code.getCode(), code.getState(), code.getServerId(), code.getAwardId(), code.getEndTime(),
			        code.getUseType(), code.getValidType() });
		}
		String sql = "insert into t_gift_code(gift_id,code,state,server_id,award_id,end_time,use_type,valid_type,create_time) values(?,?,?,?,?,?,?,?,now())";
		executeBatch(sql, args);
	}

	public void addBatchAward(List<GiftCodeAward> awards) {
		List<Object[]> args = new ArrayList<>();
		for (GiftCodeAward award : awards) {
			args.add(new Object[] { award.getGiftId(), award.getAwardId(), award.getAssId(), award.getGoodsType(), award.getNum() });
		}
		String sql = "insert into t_gift_code_award(gift_id,award_id,ass_id,goods_type,num) values(?,?,?,?,?)";
		executeBatch(sql, args);
	}

	public GiftCode queryCode(String code) {
		String sql = "select * from t_gift_code where code=?";
		return queryForEntity(sql, GIFT_CODE_ROW_MAPPER, code);
	}

	public List<GiftCodeAward> quertCodeAward(String awardId) {
		String sql = "select * from t_gift_code_award where award_id=?";
		return queryForList(sql, GIFT_CODE_AWARD_ROW_MAPPER, awardId);
	}

	/**
	 * 使用礼包激活码
	 * 
	 * @param code
	 */
	public void useCode(String code) {
		String sql = "update t_gift_code set state=?,use_time=now() where code=?";
		saveOrUpdate(sql, GiftCode.STATE_UESD, code);
	}

	/**
	 * 查询礼包激活码
	 * 
	 * @return
	 */
	public List<GiftCode> queryGiftCodes(int giftId) {
		String sql = " select * from t_gift_code where gift_id = ? ";
		return queryForList(sql, GIFT_CODE_ROW_MAPPER, giftId);
	}
	/**
	 * 查询礼包激活码日志
	 * 
	 * @param userId
	 * @return
	 */
	public List<GiftCodeLogger> queryGiftCodeLogger(int userId, String awardId) {
		String sql = "select * from  t_gift_code_logger where user_id = ? and award_id=?";
		return queryForList(sql, GIFT_CODE__LOGGER_ROW_MAPPER, userId, awardId);
	}

	public List<GiftCodeLogger> queryGiftCodeLogger(int userId, String awardId, String serverId) {
		String sql = "select * from  t_gift_code_logger where user_id = ? and award_id=? and server_id = ?";
		return queryForList(sql, GIFT_CODE__LOGGER_ROW_MAPPER, userId, awardId, serverId);
	}

	/**
	 * 新增礼包激活码使用日志
	 * 
	 * @param codLogger
	 */
	public void addGiftCodeLogger(GiftCodeLogger codLogger) {
		String sql = "insert into t_gift_code_logger(user_id,server_id,code,award_id,create_time) values(?,?,?,?,now())";
		saveOrUpdate(sql, codLogger.getUserId(), codLogger.getServerId(), codLogger.getCode(), codLogger.getAwardId());
	}

	/**
	 * 新增礼包
	 * 
	 * @param gift
	 */
	public void addGift(Gift gift) {
		String sql = "insert into t_gift(gift_name,gift_desc) values(?,?)";
		saveOrUpdate(sql, gift.getName(), gift.getDesc());
	}

	/**
	 * 查询礼包
	 * 
	 * @param giftName
	 * @return
	 */
	public Gift queryGift(String giftName) {
		String sql = "select * from t_gift where gift_name=?";
		return queryForEntity(sql, GIFT__ROW_MAPPER, giftName);
	}

	public List<Gift> queryGifts() {
		String sql = "select * from t_gift ";
		return queryForList(sql, GIFT__ROW_MAPPER);
	}
}
