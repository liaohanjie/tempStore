package com.ks.account.action;

import java.util.List;

import com.ks.account.service.AdminService;
import com.ks.account.service.ServiceFactory;
import com.ks.action.account.AdminAction;
import com.ks.model.account.AdminRole;
import com.ks.model.account.AdminUser;
import com.ks.model.account.Partner;
import com.ks.model.account.Permiss;
import com.ks.model.filter.AdminFilter;
import com.ks.model.filter.AdminRoleFiler;

public class AdminActionImpl implements AdminAction {
	private static final AdminService adminService = ServiceFactory
			.getService(AdminService.class);

	@Override
	public AdminUser adminLogin(String userName, String password) {
		return adminService.adminLogin(userName, password);
	}

	@Override
	public List<Permiss> getPowers() {
		return adminService.getAllPermission();
	}

	@Override
	public void updateAdminById(AdminUser admin) {
		adminService.updateAdminById(admin);
	}

	@Override
	public void addAdmin(AdminUser admin) {
		adminService.addAdmin(admin);
	}

	@Override
	public void deleteAdminById(int id) {
		adminService.deleteAdminById(id);
	}

	@Override
	public void updateRoleById(AdminRole id) {
		adminService.updateRoleById(id);
	}

	@Override
	public void addRole(AdminRole role) {
		adminService.addRole(role);
	}

	@Override
	public void deleteRoleById(int id) {
		adminService.deleteRoleById(id);
	}

	@Override
	public List<AdminUser> queryAdminByFilter(AdminFilter filter) {
		return adminService.queryAdminUsers(filter);
	}

	@Override
	public List<AdminRole> queryRoleByFilter(AdminRoleFiler filter) {
		return adminService.queryAdminRoles(filter);
	}

	@Override
	public List<Partner> queryPartners() {
		return adminService.queryParnters() ;
	}
}
