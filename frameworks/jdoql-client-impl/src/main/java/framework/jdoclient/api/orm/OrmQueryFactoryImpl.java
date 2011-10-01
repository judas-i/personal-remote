/**
 * Copyright 2011 the original author
 */
package framework.jdoclient.api.orm;

import framework.jdoclient.api.PersistenceManagerProvider;
import framework.jdoclient.internal.orm.LocalOrmQueryEngine;
import framework.sqlclient.api.EmptyHandler;
import framework.sqlclient.api.MultiResultHandler;
import framework.sqlclient.api.orm.OrmQuery;
import framework.sqlclient.api.orm.OrmQueryFactory;
import framework.sqlclient.api.orm.OrmUpdate;

/**
 * The factory to create the ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmQueryFactoryImpl implements OrmQueryFactory{

	/** the PersistenceManager */
	private PersistenceManagerProvider pmf;
	
	/** the MultiResultHandler */
	private MultiResultHandler mrh;
	
	/** the EmptyHandler */
	private EmptyHandler eh;
	
	/**
	 * @param pmf the PersistenceManager
	 */
	public void setPersistenceManagerProvider(PersistenceManagerProvider pmf){
		this.pmf = pmf;
	}
	
	/**
	 * @param mrh the MultiResultHandler
	 */
	public void setMultiResultHandler(MultiResultHandler mrh){
		this.mrh = mrh;
	}
	
	/**
	 * @param eh the EmptyHandler
	 */
	public void setEmptyHandler(EmptyHandler eh){
		this.eh = eh;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T, Q extends OrmQuery<T>> Q createQuery(Class<T> entityClass) {
		LocalOrmQueryEngine<T> engine = new LocalOrmQueryEngine<T>(entityClass, pmf.getPersistenceManager(),mrh,eh);
		return (Q)new DefaultOrmQueryImpl<T>(engine);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQueryFactory#createUpdate(java.lang.Class)
	 */
	@Override
	public <T, Q extends OrmUpdate<T>> Q createUpdate(Class<T> entityClass) {
		throw new UnsupportedOperationException();
	}

}
