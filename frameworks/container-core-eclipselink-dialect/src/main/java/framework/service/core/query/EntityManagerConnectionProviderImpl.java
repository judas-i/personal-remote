/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import java.sql.Connection;

import javax.persistence.EntityManager;

import org.eclipse.persistence.internal.databaseaccess.DatabaseAccessor;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sessions.server.ClientSession;

import framework.jpqlclient.api.EntityManagerProvider;
import framework.sqlclient.api.ConnectionProvider;

/**
 * SQLエンジン用のコネクション取得[エンチE��チE��マネージャ経由].
 * <b>Spring専用</b>
 * 
 * <pre>
 * 前提として、JTA以外�Eトランザクションマネージャで使用すること、E
 * JTAの時�EDataSourceから取得してもコネクション不整合を起こさなぁE��め使用する忁E���EなぁE��E
 * 
 * <p>
 * DataSourceからの直接取得やDataSourceUtils#getConnection(DataSource)では、E
 * 現在実行中のトランザクションが使用してぁE��コネクションとは異なるコネクションが使用されてしまぁE��めデータ不整合となるためE
 * 帳票出力などビジネスロジチE��でSQLエンジンを利用する場合�Eこ�Eクラスを使用してコネクションを取得すること、E
 * 検索しか実行しなぁE�EであればDataSource経由でも問題なぁE��E
 *
 * こ�Eクラスを使用する場合トランザクションを開始してぁE��ぁE��getConnection()時にNPEが発生するため、忁E��トランザクションを開始して実行すること、E
 * TransactionalアノテーションをつけてぁE��もreadOnly=trueとなってぁE��らNG、E
 * </p>
 * 
 * </pre>
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class EntityManagerConnectionProviderImpl implements ConnectionProvider{

	/** エンチE��チE��マネージャ */
	private EntityManager em;
	
	/**
	 * @param provider the provider to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider provider){
		em = provider.getEntityManager();		
	}	

	/**
	 * @see framework.sqlclient.api.ConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() {	
		EntityManagerImpl impl = (EntityManagerImpl)em.getDelegate();		
		ClientSession session = (ClientSession)((AbstractSession)impl.getActiveSession()).getParent();
		DatabaseAccessor accessor = (DatabaseAccessor)session.getAccessor();
		return accessor.getConnection();
	}

}
