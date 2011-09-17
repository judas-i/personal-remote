/**
 * Use is subject to license terms.
 */
package framework.sqlclient.internal.impl;

import java.util.List;

import framework.sqlclient.api.EmptyHandler;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.NativeQuery;
import framework.sqlclient.api.free.NativeResult;
import framework.sqlclient.api.free.ResultSetFilter;
import framework.sqlclient.internal.AbstractLocalNativeQueryEngine;

/**
 *　内部クエリ実行エンジン
 *
 * @author	yoshida-n
 * @version	created.
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class QueryEngineImpl extends AbstractLocalNativeQueryEngine<InternalQueryImpl> implements NativeQuery{

	/** 0件時処理 */
	private final EmptyHandler emptyHandler;
	
	/**
	 * @param delegate クエリ
	 */
	public QueryEngineImpl(InternalQueryImpl delegate , EmptyHandler emptyHandler){
		super(delegate);
		this.emptyHandler = emptyHandler;
	}

	/**
	 * @see framework.sqlclient.api.free.free.api.sql.FreeQuery#getResultList()
	 */
	@Override
	public <T> List<T> getResultList() {
		List<T> list = delegate.getResultList();
		if(nodataError && list.isEmpty()){
			emptyHandler.handleEmptyResult(delegate);
		}
		return list;
	}

	/**
	 * @see framework.sqlclient.api.free.free.api.sql.FreeQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		delegate.setBranchParameter(arg0, arg1);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.free.api.sql.NativeQuery#setFilter(framework.api.sql.RecordFilter)
	 */
	@Override
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter) {
		delegate.setFilter(filter);
		return (T)this;
	}

	/**
	 * @see framework.sqlclient.api.free.free.api.sql.NativeQuery#getTotalResult()
	 */
	@Override
	public <T> NativeResult<T> getTotalResult() {
		return (NativeResult<T>)delegate.getNativeResult();		
	}

	/**
	 * @see framework.sqlclient.api.free.free.api.sql.NativeQuery#count()
	 */
	@Override
	public int count() {
		return delegate.count();
	}

	/**
	 * @see framework.sqlclient.api.free.NativeQuery#getFetchResult()
	 */
	@Override
	public <T> List<T> getFetchResult() {
		return delegate.getFetchResult();
	}

}