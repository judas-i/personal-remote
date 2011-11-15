/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kosmos.framework.web.core.context.WebContext;

/**
 * The base of the <code>Filter</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractFilter implements Filter{
	
	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)arg0;
		WebContext context = createContext(request);	
		context.initialize(request);
		postConstructContext(context,request);
		try{
			doFilterInternal(request,(HttpServletResponse)arg1,arg2);
		}finally{
			preDestroyContext(context);		
			context.release();
		}
	}

	/**
	 * @param request the request
	 * @return the context
	 */
	protected abstract WebContext createContext(HttpServletRequest request);
	
	/**
	 * @param context the context
	 * @param request the request
	 */
	protected void postConstructContext(WebContext context , HttpServletRequest request){	
		
	}
	
	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	protected abstract void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, FilterChain arg2) throws IOException, ServletException;
	
	
	/**
	 * @param context the context
	 */
	protected void preDestroyContext(WebContext context){		
	}
	
	
}