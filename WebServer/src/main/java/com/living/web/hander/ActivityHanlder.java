package com.living.web.hander;

import com.ks.exceptions.GameException;
import com.living.web.core.WebContext;
import com.living.web.remote.HandlerAdapter;
import com.living.web.view.JsonPage;
import com.living.web.view.ViewPage;

public class ActivityHanlder extends HandlerAdapter {
	public ViewPage useCode(WebContext ctx) {
		int errorcode = 0;
		try {
			String code = ctx.getAsString(true, "code");
			// String time = ctx.getAsString(true, "time");
			String serverId = ctx.getAsString(true, "serverId");
			int userId = ctx.getAsInt(true, "userId");
			// String sign = ctx.getAsString(true, "sign");
			// String localSign = MD5Util.decode(code + "|" + time + "|" +
			// serverId + "|" + userId);
			giftCodeAction().useCode(code, serverId, userId);
		} catch (Exception e) {

			if (e instanceof GameException) {
				errorcode = ((GameException) e).getCode();
			} else {
				errorcode = GameException.CODE_服务器错误;
			}
			e.printStackTrace();
		}
		ctx.put("code", errorcode);
		return ctx.go(new JsonPage());
	}

}
