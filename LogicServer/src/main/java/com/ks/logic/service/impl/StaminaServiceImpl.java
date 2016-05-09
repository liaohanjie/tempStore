package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.StaminaService;
import com.ks.model.stamina.Stamina;

/**
 * 购买体力配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月18日
 */
public class StaminaServiceImpl extends BaseService implements StaminaService {
//	
//	@Override
//    public void add(Stamina entity) {
//		staminaDAO.add(entity);
//    }
//
//	@Override
//    public void update(Stamina entity) {
//		staminaDAO.update(entity);
//    }
//
//	@Override
//    public void delete(int id) {
//		staminaDAO.delete(id);
//    }

	@Override
    public List<Stamina> queryAll() {
	    return staminaDAO.queryAll();
    }
}
