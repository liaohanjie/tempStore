package com.living.web.view;

import javax.servlet.http.HttpServletResponse;

import com.living.web.core.WebContext;

public class Page404 extends ViewPage {

	@Override
	public void Render(WebContext web, HttpServletResponse response)
			throws Exception {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

}
