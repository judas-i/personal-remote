/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.internal;

import framework.sqlclient.api.Query;
import framework.sqlclient.api.free.FreeQuery;

/**
 * The local query engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractLocalQueryEngine<D extends AbstractInternalQuery> implements FreeQuery{

	/** the delegate */
	protected final D delegate;

	/** if true raise the exception */
	protected boolean nodataError = false;

	/**
	 * @param delegate the query
	 */
	public AbstractLocalQueryEngine(D delegate){
		this.delegate = delegate;		
	}

	/**
	 * @see framework.sqlclient.api.Query#enableNoDataError()
	 */
	@Override
	public <T extends Query> T enableNoDataError(){
		nodataError = true;
		return (T)this;
	}
	
	/**
	 * @see framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		return (T)delegate.getSingleResult();
	}

	/**
	 * @see framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <T extends Query> T setFirstResult(int arg0) {
		delegate.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <T extends Query> T setMaxResults(int arg0) {
		delegate.setMaxResults(arg0);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.FreeQuery#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setParameter(String arg0 , Object arg1){
		delegate.setParameter(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * @see framework.sqlclient.api.free.FreeQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists() {
		setMaxResults(1);
		return ! getResultList().isEmpty();
	}

	
}
