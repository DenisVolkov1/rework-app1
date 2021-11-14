package main.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class StartFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		((HttpServletRequest)request).getRequestURI().startsWith("/css");
		((HttpServletRequest)request).getRequestURI().startsWith("/images");
		((HttpServletRequest)request).getRequestURI().startsWith("/favicon"); 
		 chain.doFilter(request, response);
	}

}
