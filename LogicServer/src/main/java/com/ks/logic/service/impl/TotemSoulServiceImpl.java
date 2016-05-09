package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.TotemSoulService;
import com.ks.model.totem.TotemSoul;

/**
 * 图腾规则产出
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class TotemSoulServiceImpl extends BaseService implements TotemSoulService {
//
//	@Override
//    public void add(TotemSoul entity) {
//	    totemSoulDAO.add(entity);
//    }
//
//	@Override
//    public void update(TotemSoul entity) {
//		totemSoulDAO.update(entity);
//    }
//
//	@Override
//    public void delete(int id) {
//		totemSoulDAO.delete(id);
//    }

	@Override
    public List<TotemSoul> queryAll() {
	    return totemSoulDAO.queryAll();
    }
}
