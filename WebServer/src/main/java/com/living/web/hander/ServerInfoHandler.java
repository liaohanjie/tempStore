package com.living.web.hander;

import java.util.ArrayList;
import java.util.List;

import com.ks.model.account.Notice;
import com.ks.model.account.ServerInfo;
import com.living.web.JsonUtil;
import com.living.web.core.WebContext;
import com.living.web.remote.HandlerAdapter;
import com.living.web.view.JsonPage;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

public class ServerInfoHandler extends HandlerAdapter {
	public ViewPage notice(WebContext ctx) {
		List<Notice> notices=serverInfoAction().getNotice();
		String notice="";
		if(!notices.isEmpty()){
			notice=notices.get(0).getContext();
			notice = notice.replaceAll("\r\n", "<br>").toLowerCase();
			notice = notice.replaceAll("<font size=\"(\\d*)\">", "<font size=\"26\">");
			//logger.error(""+notice);
		}	
		ctx.put("context", notice);
		return ctx.go(new JsonPage());
	}
	public ViewPage getServerStat(WebContext ctx) {
		ctx.getResponse().setCharacterEncoding("utf-8");;
		List<ServerInfo> servers=serverInfoAction().getServerList();
		
		if (servers != null && !servers.isEmpty()) {
			List<ServerInfo> list = new ArrayList<>();
			for (ServerInfo server : servers) {
				if (server.getStatus() != -1) {
					list.add(server);
				}
			}
			servers = list;
		}
		ctx.put("servers", servers);
		ctx.put("version", "1.2.1");
		ctx.put("resources_version", "1.0.0");
		return new StringPage(JsonUtil.writeAsString(ctx.getContexParams()));
	}
}
