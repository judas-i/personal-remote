/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.loader;

import kosmos.framework.api.service.ComponentLocator;

import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

/**
 * Finds the component from the Spring's context.
 *  
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ComponentLocatorImpl extends ComponentLocator{
	
	/** コンテキスト */
	protected WebApplicationContext context = null;	

	/**
	 * @see kosmos.framework.web.core.loader.ComponentLocator#initialize(org.springframework.web.context.WebApplicationContext)
	 */
	void construct(WebApplicationContext context) {
		this.context = context;
		delegate = this;
	}
	
	/**
	 * @param 終了処理
	 */
	static void terminate(){
		delegate = null;
	}

	/**
	 * @see kosmos.framework.web.core.loader.ComponentLocator#lookupServiceByInterface(java.lang.Class)
	 */
	@Override
	public <T> T lookupServiceByInterface(Class<T> clazz) {
		return clazz.cast(lookupService(StringUtils.uncapitalize(clazz.getSimpleName())+ "Impl"));
	}


	/**
	 * @see kosmos.framework.web.core.loader.ComponentLocator#lookupService(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T lookupService(String name) {
		return (T)context.getBean(name);
	}

	@Override
	public <T> T lookupRemoteService(Class<T> clazz) {
		throw new UnsupportedOperationException();
	}


}