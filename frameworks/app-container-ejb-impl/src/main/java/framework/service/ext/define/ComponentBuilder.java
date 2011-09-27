/**
 * Copyright 2011 the original author
 */
package framework.service.ext.define;

import java.lang.reflect.InvocationHandler;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.service.RequestListener;
import framework.core.message.MessageBean;
import framework.logics.builder.MessageAccessor;
import framework.service.core.async.AsyncServiceFactory;
import framework.service.core.messaging.MessageClientFactory;
import framework.service.core.persistence.EntityManagerAccessor;
import framework.sqlclient.api.free.QueryFactory;

/**
 * DIã³ã³ãEã«ä»£ããã³ã³ããEãã³ããçæEãã.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ComponentBuilder {
	
	/**
	 * @return ã¡ãE»ã¼ã¸ã³ã°ãã¡ã¯ããª
	 */
	public MessageClientFactory createMessagingClientFactory();
	
	
	/**
	 * @return ãªã¯ã¨ã¹ããªã¹ããE
	 */
	public RequestListener createRequestListener();
	
	/**
	 * @return JMS topicéä¿¡ã¨ã³ã¸ã³
	 */
	public InvocationHandler createPublisher();
	
	
	/**
	 * @return JMS queueéä¿¡ã¨ã³ã¸ã³
	 */
	public InvocationHandler createSender();
	
	/**
	 * @return ã¡ãE»ã¼ã¸ã¢ã¯ã»ãµ
	 */
	public MessageAccessor<MessageBean> createMessageAccessor();
	
	/**
	 * @return ã¯ã¨ãªãã¡ã¯ããª
	 */
	public QueryFactory createQueryFactory();
	
	/**
	 * @return WEBå±¤ããã®ã¯ã¨ãªç¨ã®ãã¡ã¯ããª
	 */
	public QueryFactory createWebQueryFactory();
	
	/**
	 * @return éåæãµã¼ãã¹ãã¡ã¯ããª
	 */
	public AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return ORMã¯ã¨ãªãã¡ã¯ããª
	 */
	public AdvancedOrmQueryFactory createOrmQueryFactory();
	
	/**
	 * @return ã¨ã³ãE£ãE£ããã¼ã¸ã£ã©ãEã¼
	 */
	public EntityManagerAccessor createEntityManagerAccessor();

}
