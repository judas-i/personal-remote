/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.activation;

import java.lang.reflect.InvocationHandler;

import kosmos.framework.core.activation.ComponentLocator;
import kosmos.framework.core.activation.ServiceActivator;
import kosmos.framework.core.exception.ConcurrentBusinessException;
import kosmos.framework.core.query.AdvancedOrmQueryFactory;
import kosmos.framework.service.core.async.AsyncServiceFactory;
import kosmos.framework.service.core.messaging.MessageClientFactory;
import kosmos.framework.service.core.transaction.ServiceContext;
import kosmos.framework.sqlclient.api.free.QueryFactory;


/**
 * A service locator.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ServiceLocator extends ComponentLocator{

	/**
	 * @return the <code>MessageClientFactory</code>
	 */
	public abstract MessageClientFactory createMessageClientFactory();
	
	/**
	 * @return the <code>ServiceActivator</code>
	 */
	public abstract ServiceActivator createServiceActivator();
	
	/**
	 * @return the JMS publisher
	 */
	public abstract InvocationHandler createPublisher();
	
	/**
	 * @return the JMS sender
	 */
	public abstract InvocationHandler createSender();
	
	/**
	 * @return the <code>QueryFactory</code>
	 */
	public abstract QueryFactory createQueryFactory();
	
	/**
	 * @return the <code>QueryFactory</code> only called from WEB 
	 */
	public abstract QueryFactory createClientQueryFactory();
	
	/**
	 * @return the <code>AsyncServiceFactory</code>
	 */
	public abstract AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return the <code>AdvancedOrmQueryFactory</code>
	 */
	public abstract AdvancedOrmQueryFactory createOrmQueryFactory();
	
	/**
	 * @return the ServiceContext
	 */
	public abstract ServiceContext createServiceContext();
	
	/**
	 * @return the ConcurrentBusinessException
	 */
	public abstract ConcurrentBusinessException createConcurrentBusinessException(Throwable cause);
	
	/**
	 * @return the MessageClientFactory
	 */
	public static ConcurrentBusinessException createDefaultConcurrentBusinessException(Throwable cause){
		return getDelegate().createConcurrentBusinessException(cause);
	}
	
	/**
	 * @return the MessageClientFactory
	 */
	public static MessageClientFactory createDefaultMessageClientFactory(){
		return getDelegate().createMessageClientFactory();
	}
	
	/**
	 * @return the ServiceActivator
	 */
	public static ServiceActivator createDefaultServiceActivator(){
		return getDelegate().createServiceActivator();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultPublisher(){
		return getDelegate().createPublisher();
	}
	
	/**
	 * @return the InvocationHandler
	 */
	public static InvocationHandler createDefaultSender(){
		return getDelegate().createSender();
	}
	
	/**
	 * @return the QueryFactory
	 */
	public static QueryFactory createDefaultQueryFactory(){
		return getDelegate().createQueryFactory();
	}
	
	/**
	 * @return the QueryFactory
	 */
	public static QueryFactory createDefaultClientQueryFactory(){
		return getDelegate().createClientQueryFactory();
	}
	
	/**
	 * @return the AdvancedOrmQueryFactory
	 */
	public static AdvancedOrmQueryFactory createDefaultOrmQueryFactory(){
		return getDelegate().createOrmQueryFactory();
	}
	
	/**
	 * @return the AsyncServiceFactory
	 */
	public static AsyncServiceFactory createDefaultAsyncServiceFactory(){
		return getDelegate().createAsyncServiceFactory();
	}
	
	/**
	 * @return the ServiceContext
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ServiceContext> T createDefaultServiceContext(){
		return (T)getDelegate().createServiceContext();
	}		
	
	/**
	 * @return
	 */
	private static ServiceLocator getDelegate(){
		return (ServiceLocator)delegate;
	}
	
}