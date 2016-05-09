package com.ks.model.alliance;

import java.util.HashSet;
import java.util.Set;
/**
 * 工会成员管理
 * @author hanjie.l
 *
 */
public class AllianceMembers {
	
	/**
	 * 工会id(主键id)
	 */
	private int id;
	
	/**
	 * 工会成员
	 */
	private Set<Integer> members = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Integer> getMembers() {
		return members;
	}

	public void setMembers(Set<Integer> members) {
		this.members = members;
	}
}
