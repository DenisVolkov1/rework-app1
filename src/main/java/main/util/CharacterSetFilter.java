package main.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CharacterSetFilter implements Filter {
	private String encoding = "utf-8";
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Привет фильтр!");
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ((HttpServletRequest) request).getRequestURI().startsWith("/css");
        
        chain.doFilter(request, response);
		
        
        
	}
	public void init(FilterConfig filterConfig) 
            throws ServletException {
			String encodingParam = 
			filterConfig.getInitParameter("encoding");
			if (encodingParam != null) {
			encoding = encodingParam;
	}
}

}
