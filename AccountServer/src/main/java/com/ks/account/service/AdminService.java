package com.ks.account.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.account.AdminRole;
import com.ks.model.account.AdminUser;
import com.ks.model.account.Partner;
import com.ks.model.account.Permiss;
import com.ks.model.filter.AdminFilter;
import com.ks.model.filter.AdminRoleFiler;

public interface AdminService {

	public AdminUser adminLogin(String userName, String password);

	/**
	 * 得到所有权限
	 * 
	 * @return
	 */
	public List<Permiss> getAllPermission();
	/**
	 * 查询系统用户
	 * 
	 * @param name
	 * @return
	 */
	public List<AdminUser> queryAdminUsers(AdminFilter filter);

	/**
	 * 修改系统用户
	 * 
	 * @param admin
	 */
	@Transaction
	void updateAdminById(AdminUser admin);

	/**
	 * 增加系统用户
	 * 
	 * @param admin
	 */
	@Transaction
	void addAdmin(AdminUser admin);

	/**
	 * 删除系统用户
	 * 
	 * @param id
	 */
	@Transaction
	void deleteAdminById(int id);

	/**
	 * 查询用户角色
	 * 
	 * @param name
	 * @return
	 */
	public List<AdminRole> queryAdminRoles(AdminRoleFiler filter);

	/**
	 * 修改用户角色
	 * 
	 * @param role
	 */
	@Transaction
	void updateRoleById(AdminRole role);
	/**
	 * 增加用户角色
	 * 
	 * @param role
	 */
	@Transaction
	void addRole(AdminRole role);

	/**
	 * 删除用户角色
	 * 
	 * @param id
	 */
	@Transaction
	void deleteRoleById(int id);
	
	List<Partner> queryParnters();
}
