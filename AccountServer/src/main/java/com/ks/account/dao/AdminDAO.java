package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.account.AdminRole;
import com.ks.model.account.AdminUser;
import com.ks.model.account.Partner;
import com.ks.model.account.Permiss;
import com.ks.model.filter.AdminFilter;
import com.ks.model.filter.AdminRoleFiler;

public class AdminDAO extends GameDAOTemplate {
	private static final RowMapper<AdminUser> ADMIN_USER_ROW = new RowMapper<AdminUser>() {
		@Override
		public AdminUser rowMapper(ResultSet rs) throws SQLException {
			AdminUser admin = new AdminUser();
			admin.setAdminId(rs.getInt("admin_id"));
			admin.setUserName(rs.getString("user_name"));
			admin.setPassword(rs.getString("password"));
			admin.setRoleList(rs.getString("role_id"));
			admin.setCreateTime(rs.getTimestamp("create_time"));
			admin.setUpdateTime(rs.getTimestamp("update_time"));
			admin.setPermissionList(rs.getString("permissions"));
			return admin;
		}
	};
	private static final RowMapper<Permiss> PERMISSION_ROW = new RowMapper<Permiss>() {
		@Override
		public Permiss rowMapper(ResultSet rs) throws SQLException {
			Permiss permiss = new Permiss();
			permiss.setIcon(rs.getString("icon"));
			permiss.setId(rs.getInt("id"));
			permiss.setParentId(rs.getInt("parent_id"));
			permiss.setName(rs.getString("name"));
			return permiss;
		}
	};

	private static final RowMapper<AdminRole> ADMIN_ROLE_ROW = new RowMapper<AdminRole>() {

		@Override
		public AdminRole rowMapper(ResultSet rs) throws SQLException {
			AdminRole role = new AdminRole();
			role.setRoleId(rs.getInt("role_id"));
			role.setRoleName(rs.getString("role_name"));
			role.setPermission(rs.getString("permission"));
			role.setJob(rs.getString("job"));
			role.setDes(rs.getString("des"));
			return role;
		}
	};
	
	private static final RowMapper<Partner> PARTNER_ROW = new RowMapper<Partner>() {

		@Override
		public Partner rowMapper(ResultSet rs) throws SQLException {
			Partner partner = new Partner();
			partner.setParnterId(rs.getInt("partner_id"));
			partner.setPartnerName(rs.getString("partner_name"));
			partner.setPay(rs.getBoolean("is_pay"));
			partner.setSysPlatform(rs.getString("sys_platform"));
			return partner;
		}
	};

	public List<Permiss> queryAllPower() {
		String sql = "select * from t_permission";
		return queryForList(sql, PERMISSION_ROW);
	}

	/**
	 * 查询系统用户
	 * 
	 * @param filter
	 * @return
	 */
	public List<AdminUser> queryAdminUsers(AdminFilter filter) {
		StringBuffer sql = new StringBuffer(
				" SELECT * FROM (SELECT u.*,GROUP_CONCAT(DISTINCT r.permission) permissions  FROM t_admin_user u LEFT JOIN t_admin_role r ON   FIND_IN_SET( r.role_id,u.role_id) GROUP BY u.admin_id)  t_admin_user where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		if (filter.getUserName() != null) {
			sql.append("and user_name like concat('%',?,'%')");
			val.add(filter.getUserName());
		}
		return queryForList(sql.toString(), ADMIN_USER_ROW, val.toArray());
	}

	/**
	 * 修改系统用户
	 * 
	 * @param admin
	 */
	public void updateAdminById(AdminUser admin) {
		String sql = " update  t_admin_user set role_id=?,update_time=now() where admin_id=? ";
		saveOrUpdate(sql, admin.getRoleList(), admin.getAdminId());
	}

	/**
	 * 删除系统用户
	 * 
	 * @param id
	 */
	public void deleteAdminById(int id) {
		String sql = " delete from t_admin_user where admin_id=?";
		saveOrUpdate(sql, id);
	}

	/**
	 * 增加系统用户
	 * 
	 * @param admin
	 */
	public void addAdmin(AdminUser admin) {
		String sql = " insert  into t_admin_user (user_name,password,role_id,create_time,update_time) values(?,?,?,now(),now())";
		saveOrUpdate(sql, admin.getUserName(), admin.getPassword(),
				admin.getRoleList());
	}

	/**
	 * 查询用户角色
	 * 
	 * @param filter
	 * @return
	 */
	public List<AdminRole> queryAdminRoles(AdminRoleFiler filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_admin_role where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		if (filter.getRoleName() != null) {
			sql.append("and role_name like concat('%',?,'%')");
			val.add(filter.getRoleName());
		}
		return queryForList(sql.toString(), ADMIN_ROLE_ROW, val.toArray());
	}

	/**
	 * 修改用户角色
	 * 
	 * @param role
	 */
	public void updateRoleById(AdminRole role) {
		String sql = " update  t_admin_role set permission=?,job=?,des=? where role_id=? ";
		saveOrUpdate(sql, role.getPermission(), role.getJob(), role.getDes(),
				role.getRoleId());
	}

	/**
	 * 增加用户角色
	 * 
	 * @param role
	 */
	public void addRole(AdminRole role) {
		String sql = "insert into t_admin_role(role_name,permission,job,des) values(?,?,?,?)";
		saveOrUpdate(sql, role.getRoleName(), role.getPermission(),
				role.getJob(), role.getDes());
	}

	/**
	 * 删除用户角色
	 * 
	 * @param id
	 */
	public void deleteRoleById(int id) {
		String sql = "delete from t_admin_role where role_id = ?";
		saveOrUpdate(sql, id);
	}
	

	public AdminUser adminLogin(String userName, String password) {
		StringBuffer buff = new StringBuffer(
				"SELECT * FROM (SELECT u.*,GROUP_CONCAT(r.permission) permissions  FROM t_admin_user u LEFT JOIN t_admin_role r ON   FIND_IN_SET( r.role_id,u.role_id) GROUP BY u.admin_id)  t_admin_user where 1=1");
		buff.append(" and user_name=? and password=? ");
		return queryForEntity(buff.toString(), ADMIN_USER_ROW, userName,
				password);
	}
	
	public List<Partner> queryPartners() {
		String sql="select * from t_partner";
		return queryForList(sql, PARTNER_ROW);
	}
}
