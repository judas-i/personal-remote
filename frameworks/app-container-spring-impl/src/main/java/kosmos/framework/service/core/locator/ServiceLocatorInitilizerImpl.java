/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.locator;

import kosmos.framework.api.service.ServiceLocatorInitializer;

import org.springframework.context.ApplicationContext;


/**
 * The context initializer.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServiceLocatorInitilizerImpl implements ServiceLocatorInitializer{

	private SpringServiceLocator locater = null;

	/**
	 * @see kosmos.framework.api.service.ServiceLocatorInitializer#construct(java.lang.Object)
	 */
	@Override
	public void construct(Object resource) {
		if( resource instanceof String){
			locater = new ServiceLocatorImpl();
			((ServiceLocatorImpl)locater).construct((String)resource);
		}else if(resource instanceof ApplicationContext){
			locater = new ExternalServiceLocatorImpl();
			((ExternalServiceLocatorImpl)locater).construct((ApplicationContext)resource);
		}
	}

	/**
	 * @see kosmos.framework.api.service.ServiceLocatorInitializer#destroy()
	 */
	@Override
	public void destroy() {
		if( locater != null){
			locater.destroy();
		}
	}
}