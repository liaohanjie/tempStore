package com.ks.action.account;

import java.util.List;

import com.ks.model.account.AdminRole;
import com.ks.model.account.AdminUser;
import com.ks.model.account.Partner;
import com.ks.model.account.Permiss;
import com.ks.model.filter.AdminFilter;
import com.ks.model.filter.AdminRoleFiler;

public interface AdminAction {
	AdminUser adminLogin(String userName, String password);

	List<Permiss> getPowers();


	/**
	 * 查询系统用户
	 * 
	 * @param name
	 * @return
	 */
	List<AdminUser> queryAdminByFilter(AdminFilter filter);

	/**
	 * 修改系统用户
	 * 
	 * @param admin
	 */
	void updateAdminById(AdminUser admin);

	/**
	 * 增加系统用户
	 * 
	 * @param admin
	 */
	void addAdmin(AdminUser admin);

	/**
	 * 删除系统用户
	 * 
	 * @param id
	 */
	void deleteAdminById(int id);

	/**
	 * 查询用户角色
	 * 
	 * @param name
	 * @return
	 */
	List<AdminRole> queryRoleByFilter(AdminRoleFiler filter);

	/**
	 * 修改用户角色
	 * 
	 * @param role
	 */
	void updateRoleById(AdminRole role);

	/**
	 * 增加用户角色
	 * 
	 * @param role
	 */
	void addRole(AdminRole role);

	/**
	 * 删除用户角色
	 * 
	 * @param id
	 */
	void deleteRoleById(int id);
	
	/**
	 * 查询所有渠道
	 * @return
	 */
	List<Partner> queryPartners();
}
