package com.ks.game.model;

import java.io.Serializable;
/**
 * 玩家模型
 * @author ks
 */
public class PlayerModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**合作方*/
	private int partner;
	/**用户名*/
	private String username;
	
	public static PlayerModel create(int partner,String username){
		PlayerModel model = new PlayerModel();
		model.setPartner(partner);
		model.setUsername(username);
		return model;
	}

	public int getPartner() {
		return partner;
	}

	public void setPartner(int partner) {
		this.partner = partner;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		if(username==null){
			return super.hashCode();
		}else{
			return partner+username.hashCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PlayerModel){
			PlayerModel m = (PlayerModel)obj;
			if(username==null){
				return partner==m.getPartner()&&username==m.getUsername();
			}else{
				return partner==m.getPartner()&&username.equals(m.getUsername());
			}
		}
		return false;
	}
	
}
