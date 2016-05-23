package com.ks.account.service.impl;

import java.util.List;

import com.ks.account.service.AdminService;
import com.ks.account.service.BaseService;
import com.ks.model.account.AdminRole;
import com.ks.model.account.AdminUser;
import com.ks.model.account.Partner;
import com.ks.model.account.Permiss;
import com.ks.model.filter.AdminFilter;
import com.ks.model.filter.AdminRoleFiler;
import com.ks.util.MD5Util;

public class AdminServiceImpl extends BaseService implements AdminService {
	@Override
	public AdminUser adminLogin(String userName, String password) {
		AdminUser adminUser = adminDAO.adminLogin(userName, password);
		return adminUser;
	}

	@Override
	public List<Permiss> getAllPermission() {
		return adminDAO.queryAllPower();
	}

	@Override
	public List<AdminUser> queryAdminUsers(AdminFilter filter) {
		return adminDAO.queryAdminUsers(filter);
	}

	@Override
	public void updateAdminById(AdminUser admin) {
		adminDAO.updateAdminById(admin);
	}

	@Override
	public void addAdmin(AdminUser admin) {
		admin.setPassword(MD5Util.decode(admin.getPassword()));
		adminDAO.addAdmin(admin);
	}

	@Override
	public void deleteAdminById(int admin) {
		adminDAO.deleteAdminById(admin);
	}

	@Override
	public List<AdminRole> queryAdminRoles(AdminRoleFiler filter) {
		return adminDAO.queryAdminRoles(filter);
	}

	@Override
	public void updateRoleById(AdminRole role) {
		adminDAO.updateRoleById(role);
	}

	@Override
	public void addRole(AdminRole role) {
		adminDAO.addRole(role);
	}

	@Override
	public void deleteRoleById(int id) {
		adminDAO.deleteRoleById(id);
	}

	@Override
	public List<Partner> queryParnters() {
		return adminDAO.queryPartners();
	}
	
}
