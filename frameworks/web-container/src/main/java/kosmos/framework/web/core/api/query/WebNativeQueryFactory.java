/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.api.query;

import kosmos.framework.api.query.services.NativeQueryService;
import kosmos.framework.core.exception.PoorImplementationException;
import kosmos.framework.logics.utils.ClassUtils;
import kosmos.framework.sqlclient.api.free.AbstractFreeQuery;
import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.api.free.AbstractUpdate;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.QueryAccessor;
import kosmos.framework.sqlclient.api.free.QueryFactory;
import kosmos.framework.web.core.api.service.ServiceCallable;
import kosmos.framework.web.core.api.service.ServiceFacade;

/**
 * The factor to create query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ServiceCallable
public class WebNativeQueryFactory implements QueryFactory{
	
	@ServiceFacade
	private NativeQueryService service;
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createQuery(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K extends FreeQuery, T extends AbstractFreeQuery<K>> T createQuery(Class<T> queryClass) {
		K delegate = null;
		
		if(AbstractNativeQuery.class.isAssignableFrom(queryClass)){			
			delegate = (K)new WebNativeQueryEngine(queryClass,service);
		}else{
			throw new PoorImplementationException("unsupporetd query type : type = " + queryClass);
		}
		T eq = ClassUtils.newInstance(queryClass);
		QueryAccessor.setDelegate(eq, delegate);		
		return eq;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.QueryFactory#createUpdate(java.lang.Class)
	 */
	@Override
	public <K extends FreeUpdate, T extends AbstractUpdate<K>> T createUpdate(Class<T> entityClass) {
		throw new UnsupportedOperationException();
	}

}