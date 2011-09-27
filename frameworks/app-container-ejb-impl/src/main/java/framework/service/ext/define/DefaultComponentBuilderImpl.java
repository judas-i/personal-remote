/**
 * Copyright 2011 the original author
 */
package framework.service.ext.define;

import java.lang.reflect.InvocationHandler;

import framework.api.query.orm.AdvancedOrmQueryFactory;
import framework.api.service.RequestListener;
import framework.core.message.MessageBean;
import framework.jpqlclient.api.EntityManagerProvider;
import framework.jpqlclient.api.free.JPAQueryFactoryImpl;
import framework.jpqlclient.api.free.NativeQueryFactoryImpl;
import framework.jpqlclient.api.orm.OrmQueryFactoryImpl;
import framework.jpqlclient.internal.orm.impl.GenericJPADaoImpl;
import framework.logics.builder.MessageAccessor;
import framework.logics.builder.impl.MessageBuilderImpl;
import framework.service.core.async.AsyncServiceFactory;
import framework.service.core.error.MessageAccessorImpl;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.messaging.MessageClientFactory;
import framework.service.core.persistence.EntityManagerAccessor;
import framework.service.core.persistence.EntityManagerAccessorImpl;
import framework.service.core.query.AdvancedOrmQueryFactoryImpl;
import framework.service.core.query.CustomEmptyHandlerImpl;
import framework.service.core.query.DataSourceConnectionProviderImpl;
import framework.service.ext.async.AsyncServiceFactoryImpl;
import framework.service.ext.listener.RequestListenerImpl;
import framework.service.ext.messaging.MessageClientFactoryImpl;
import framework.service.ext.messaging.QueueProducerDelegate;
import framework.service.ext.messaging.TopicProducerDelegate;
import framework.sqlclient.api.free.QueryFactory;
import framework.sqlengine.facade.impl.SQLEngineFacadeImpl;

/**
 * フレームワークのコンポ�Eネントを生�Eする.
 * 
 * <pre>
 * SessionBeanはロールバックフラグがたってぁE��と新規作�EできなぁE��め、設計によっては処琁E��行不可能となる、EEntityManager単体�E取得�E可能�E�E
 * フレームワークの吁E��コンポ�EネントをSessionBeanとしてしまぁE��送E��がきかなくなるためPOJOとして生�Eする、E
 * (ただしくはSessionBeanのメソチE��コール時にインターセプターでTransactionRolledBackExceptionがスローされる！E
 * ただし、EntityManagerの提供老E�Eみ、AsyncServiceはSessionBeanとせざるを得なぁE�EでSessionBeanとする、E
 *
 * インターセプターを仕込みたけれ�EPOJOでなく、独自で動的プロキシを使ぁE��CDIを使用すること、E
 * </pre>
 * 
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultComponentBuilderImpl implements ComponentBuilder {

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createMessageAccessor()
	 */
	@Override
	public MessageAccessor<MessageBean> createMessageAccessor() {
		MessageAccessorImpl accessor = new MessageAccessorImpl();
		accessor.setMessageBuilder(new MessageBuilderImpl());
		return accessor;
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		JPAQueryFactoryImpl factory = new JPAQueryFactoryImpl();
		factory.setEmptyHandler( new CustomEmptyHandlerImpl());
		factory.setEntityManagerProvider(createEntityManagerProvider());		
		return factory;	
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createEntityManagerAccessor()
	 */
	@Override
	public EntityManagerAccessor createEntityManagerAccessor() {
		EntityManagerAccessorImpl accessor = new EntityManagerAccessorImpl();
		accessor.setEntityManagerProvider(createEntityManagerProvider());
		return accessor;
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createOrmQueryFactory()
	 */
	@Override
	public AdvancedOrmQueryFactory createOrmQueryFactory() {
		AdvancedOrmQueryFactoryImpl impl = new AdvancedOrmQueryFactoryImpl();
		OrmQueryFactoryImpl internal = new OrmQueryFactoryImpl();
		
		GenericJPADaoImpl dao = new GenericJPADaoImpl();
		dao.setEntityManagerProvider(createEntityManagerProvider());
	
		internal.setGenericDao(dao);
	
		impl.setInternalFactory(internal);
		return impl;
	}
	
	/**
	 * @see framework.service.ext.define.ComponentBuilder#createAsyncServiceFactory()
	 */
	@Override
	public AsyncServiceFactory createAsyncServiceFactory() {
		return new AsyncServiceFactoryImpl();
	}
	
	/**
	 * @return エンチE��チE��マネージャの供給老E
	 */
	protected EntityManagerProvider createEntityManagerProvider() {
		return ServiceLocator.lookupByInterface(EntityManagerProvider.class);
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createWebQueryFactory()
	 */
	@Override
	public QueryFactory createWebQueryFactory() {
		NativeQueryFactoryImpl factory = new NativeQueryFactoryImpl();
		factory.setEmptyHandler( new CustomEmptyHandlerImpl());
		factory.setConnectionProvider(new DataSourceConnectionProviderImpl());
		factory.setSqlEngineFacade(new SQLEngineFacadeImpl());
		return factory;	
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createPublisher()
	 */
	@Override
	public InvocationHandler createPublisher() {
		return new TopicProducerDelegate();
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createSender()
	 */
	@Override
	public InvocationHandler createSender() {
		return new QueueProducerDelegate();
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createMessagingClientFactory()
	 */
	@Override
	public MessageClientFactory createMessagingClientFactory() {
		return new MessageClientFactoryImpl();
	}

	/**
	 * @see framework.service.ext.define.ComponentBuilder#createRequestListener()
	 */
	@Override
	public RequestListener createRequestListener() {
		return new RequestListenerImpl();
	}

}
