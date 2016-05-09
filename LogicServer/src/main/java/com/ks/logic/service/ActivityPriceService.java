package com.ks.logic.service;

import com.ks.access.Transaction;
import com.ks.model.activity.ActivityPrice;

public interface ActivityPriceService {

	public ActivityPrice getActivityPrices();

	@Transaction
	public void updateActivityPrice(ActivityPrice price);
}
