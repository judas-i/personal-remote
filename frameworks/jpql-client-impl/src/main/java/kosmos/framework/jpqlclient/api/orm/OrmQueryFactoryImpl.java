/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.orm;

import kosmos.framework.jpqlclient.api.orm.JPAOrmQuery;
import kosmos.framework.jpqlclient.api.orm.JPAOrmUpdate;
import kosmos.framework.jpqlclient.internal.orm.GenericDao;
import kosmos.framework.jpqlclient.internal.orm.impl.LocalOrmQueryEngine;
import kosmos.framework.jpqlclient.internal.orm.impl.LocalOrmUpdateEngine;
import kosmos.framework.sqlclient.api.orm.OrmQuery;
import kosmos.framework.sqlclient.api.orm.OrmQueryFactory;
import kosmos.framework.sqlclient.api.orm.OrmUpdate;

/**
 * The factory to create the query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmQueryFactoryImpl implements OrmQueryFactory{
	
	/** the DAO */
	private GenericDao genericDao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setGenericDao(GenericDao dao){
		this.genericDao = dao;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T,Q extends OrmQuery<T>> Q createQuery(Class<T> entityClass) {
		JPAOrmQuery<T> engine = new LocalOrmQueryEngine<T>(entityClass).setDao(genericDao);
		return (Q)create(engine);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQueryFactory#createUpdate(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T,Q extends OrmUpdate<T>> Q createUpdate(Class<T> entityClass){
		JPAOrmUpdate<T> engine = new LocalOrmUpdateEngine<T>(entityClass).setAccessor(genericDao);
		return (Q)create(engine);
	}
	
	/**
	 * @param <T> the type
	 * @param <Q> the type
	 * @param engine the internal engine
	 * @return the updater
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected JPAOrmUpdate create(JPAOrmUpdate engine ){
		return new DefaultOrmUpdateImpl(engine);
	}
	
	/**
	 * @param <T> the type
	 * @param <Q> the type
	 * @param engine the internal engine
	 * @return the query
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected JPAOrmQuery create(JPAOrmQuery engine ){
		return new DefaultOrmQueryImpl(engine);
	}
	
}