/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import javax.persistence.EntityManager;
import javax.persistence.QueryHint;

import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.jpqlclient.internal.free.impl.InternalNamedQueryImpl;
import kosmos.framework.jpqlclient.internal.free.impl.LocalNamedQueryEngine;
import kosmos.framework.jpqlclient.internal.free.impl.LocalNamedUpdateEngine;
import kosmos.framework.sqlclient.api.Update;
import kosmos.framework.sqlclient.api.free.AbstractFreeQuery;
import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.api.free.AbstractNativeUpdate;
import kosmos.framework.sqlclient.api.free.AbstractUpdate;
import kosmos.framework.sqlclient.api.free.AnonymousQuery;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.QueryAccessor;
import kosmos.framework.sqlclient.api.free.QueryFactory;
import kosmos.framework.sqlengine.builder.ConstAccessor;
import kosmos.framework.sqlengine.builder.SQLBuilder;
import kosmos.framework.sqlengine.builder.impl.ConstAccessorImpl;
import kosmos.framework.sqlengine.builder.impl.SQLBuilderProxyImpl;


/**
 * The factory to create the named query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class NamedQueryFactoryImpl implements QueryFactory{
	
	/** the <code>EntityManager</code> */
	protected EntityManager em;
	
	/** the <code>ConstAccessor</code> */
	protected ConstAccessor accessor = new ConstAccessorImpl();
	
	/** the <code>SQLBuilder</code> */
	protected SQLBuilder builder = new SQLBuilderProxyImpl();


	/**
	 * @param builder the builder to set
	 */
	public void setSqlBuilder(SQLBuilder builder){
		this.builder = builder;
	}
	
	/**
	 * @param accessor the accessor to set
	 */
	public void setConstAccessor(ConstAccessor accessor){
		this.accessor = accessor;
	}
	
	/**
	 * @param provider the provider to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider provider){
		em = provider.getEntityManager();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createUpdate(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeUpdate,T extends AbstractUpdate<K>> T createUpdate(Class<T> updateClass) {
		K delegate = null;
		
		if( AbstractNamedUpdate.class.isAssignableFrom(updateClass)){			
			delegate = (K)createNamedUpdateEngine(updateClass);
		
		}else if(AbstractNativeUpdate.class.isAssignableFrom(updateClass)){
			delegate = (K)createNativeUpdateEngine(updateClass);
		
		//Other
		}else{
			throw new IllegalArgumentException("unsupporetd query type : type = " + updateClass);
		}
		T update = newInstance(updateClass);
		QueryAccessor.setDelegate(update,delegate);
		return update;
	}


	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeQuery,T extends AbstractFreeQuery<K>> T createQuery(Class<T> queryClass){
		K delegate = null;
		
		if( AbstractNamedQuery.class.isAssignableFrom(queryClass)){
			delegate = (K)createNamedQueryEngine(queryClass);
		
		}else if(AbstractNativeQuery.class.isAssignableFrom(queryClass)){			
			delegate = (K)createNativeQueryEngine(queryClass);
			
		//Other
		}else{
			throw new IllegalArgumentException("unsupporetd query type : type = " + queryClass);
		}
		T eq = newInstance(queryClass);
		QueryAccessor.setDelegate(eq, delegate);		
		return eq;
	}
	
	/**
	 * @param queryClass　the class of the query 
	 * @return the query
	 */
	protected FreeQuery createNativeQueryEngine(Class<?> queryClass){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param updateClass　the class of the updater
	 * @return the updater
	 */
	protected FreeUpdate createNativeUpdateEngine(Class<?> updateClass){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param queryClass　the class of the query
	 * @return the query
	 */
	protected FreeQuery createNamedQueryEngine(Class<?> queryClass){
		return new LocalNamedQueryEngine(getNamedQuery(queryClass));
	}
	
	/**
	 * @param updateClass　the class of the updater
	 * @return the updater
	 */
	protected Update createNamedUpdateEngine(Class<?> updateClass){
		return new LocalNamedUpdateEngine(getNamedQuery(updateClass));
	}
	

	/**
	 * Creates the internal named query.
	 * 
	 * @param clazz the class 
	 * @return the query
	 */
	protected InternalNamedQueryImpl getNamedQuery(Class<?> clazz){
		
		javax.persistence.NamedQuery nq = clazz.getAnnotation(javax.persistence.NamedQuery.class);
		InternalNamedQueryImpl query = null;
		QueryHint[] hints = null;
		//標準
		if(nq != null){
			query = new InternalNamedQueryImpl(nq.name(),nq.query(), em,clazz.getSimpleName() ,false,builder,accessor);				
			hints = nq.hints();
		
		//拡張-if文用	
		}else{
			AnonymousQuery aq = clazz.getAnnotation(AnonymousQuery.class);
			query = new InternalNamedQueryImpl(null,aq.query(), em, clazz.getSimpleName(),false,builder,accessor);
			hints = aq.hints();
		}
		if( hints == null){
			hints = new QueryHint[0];
		}
		for(QueryHint h: hints){
			query.setHint(h.name(), h.value());
		}
		return query;
	}
	
	/**
	 * @param <T>　the type
	 * @param clazz the class 
	 * @return the new instance
	 */
	protected <T> T newInstance(Class<T> clazz){
		try{
			return clazz.newInstance();
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
	}
	
}