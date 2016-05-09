package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.MallService;
import com.ks.model.pay.Mall;

/**
 * 充值送魂钻服务
 * @author zhoujf
 * @date 2015年6月2日
 */
public class MallServiceImpl extends BaseService implements MallService {

	@Override
    public List<Mall> queryAllMall() {
		return mallDAO.queryAllMall();
    }

}
