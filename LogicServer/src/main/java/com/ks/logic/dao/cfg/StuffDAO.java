package com.ks.logic.dao.cfg;

import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.impl.RowMapperImpl;
import com.ks.model.stuff.Stuff;

/**
 * 材料
 * @author ks
 */
public class StuffDAO extends GameCfgDAOTemplate{
	
	public List<Stuff> queryAllStuff(){
		return queryForList("select * from t_stuff", new RowMapperImpl<>(Stuff.class));
	}

}
