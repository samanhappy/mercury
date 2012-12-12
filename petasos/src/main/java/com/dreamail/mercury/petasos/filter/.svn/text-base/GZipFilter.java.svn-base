package com.dreamail.mercury.petasos.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.dreamail.mercury.petasos.gzip.GZipResponse;

public class GZipFilter implements Filter {

	public void destroy() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		GZipResponse zipResponse = new GZipResponse(res);
		res.setHeader("Content-Encoding", "gzip");
		chain.doFilter(request, zipResponse);
		zipResponse.flush();
	}

}
