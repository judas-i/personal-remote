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
 * DIコンチE��に代わりコンポ�Eネントを生�Eする.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ComponentBuilder {
	
	/**
	 * @return メチE��ージングファクトリ
	 */
	public MessageClientFactory createMessagingClientFactory();
	
	
	/**
	 * @return リクエストリスナ�E
	 */
	public RequestListener createRequestListener();
	
	/**
	 * @return JMS topic送信エンジン
	 */
	public InvocationHandler createPublisher();
	
	
	/**
	 * @return JMS queue送信エンジン
	 */
	public InvocationHandler createSender();
	
	/**
	 * @return メチE��ージアクセサ
	 */
	public MessageAccessor<MessageBean> createMessageAccessor();
	
	/**
	 * @return クエリファクトリ
	 */
	public QueryFactory createQueryFactory();
	
	/**
	 * @return WEB層からのクエリ用のファクトリ
	 */
	public QueryFactory createWebQueryFactory();
	
	/**
	 * @return 非同期サービスファクトリ
	 */
	public AsyncServiceFactory createAsyncServiceFactory();
	
	/**
	 * @return ORMクエリファクトリ
	 */
	public AdvancedOrmQueryFactory createOrmQueryFactory();
	
	/**
	 * @return エンチE��チE��マネージャラチE��ー
	 */
	public EntityManagerAccessor createEntityManagerAccessor();

}
