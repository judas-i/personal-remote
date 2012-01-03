/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free.impl;

import java.util.List;

import kosmos.framework.sqlclient.api.ConnectionProvider;
import kosmos.framework.sqlclient.api.PersistenceHints;
import kosmos.framework.sqlclient.api.free.FreeParameter;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.api.free.NativeResult;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlengine.facade.BaseSQLParameter;
import kosmos.framework.sqlengine.facade.BatchParameter;
import kosmos.framework.sqlengine.facade.QueryParameter;
import kosmos.framework.sqlengine.facade.QueryResult;
import kosmos.framework.sqlengine.facade.SQLEngineFacade;
import kosmos.framework.sqlengine.facade.SQLParameter;
import kosmos.framework.sqlengine.facade.UpdateParameter;


/**
 * The internal query for SQLEngine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalQueryImpl implements InternalQuery{
	
	/** the ConnectionProvider */
	private ConnectionProvider cs;
	
	/** the facade of SQLEngine */
	private SQLEngineFacade facade;
	
	/**
	 * @param cs the cs to set
	 */
	public void setConnectionProvider(ConnectionProvider cs){
		this.cs = cs;
	}
	
	/**
	 * @param facade the facade to set
	 */
	public void setSqlEngineFacade(SQLEngineFacade facade){
		this.facade = facade;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getTotalResult(kosmos.framework.sqlclient.api.free.FreeQueryParameter)
	 */
	@Override
	public NativeResult getTotalResult(FreeQueryParameter param){
		QueryResult result = facade.executeTotalQuery(createQueryParameter(param), cs.getConnection());
		return new NativeResult(result.isLimited(), result.getResultList(), result.getHitCount());
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getFetchResult(kosmos.framework.sqlclient.api.free.FreeQueryParameter)
	 */
	@Override
	public <T> List<T> getFetchResult(FreeQueryParameter param){
		return facade.executeFetch(createQueryParameter(param), cs.getConnection());		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#count()
	 */
	@Override
	public int count(FreeQueryParameter param){
		return facade.executeCount(createParameter(new QueryParameter(),param), cs.getConnection());
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getResultList()
	 */
	@Override
	public <T> List<T> getResultList(FreeQueryParameter param){
		return facade.executeQuery(createQueryParameter(param), cs.getConnection());		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult(FreeQueryParameter param){
		param.setMaxSize(1);
		List<T> result = getResultList(param);
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate(FreeUpdateParameter param){
		return facade.executeUpdate(createParameter(new UpdateParameter(),param), cs.getConnection());
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#batchUpdate()
	 */
	@Override
	public int[] batchUpdate(FreeUpdateParameter param){
		BatchParameter batch = new BatchParameter();
		batch = createBaseParameter(batch,param);
		batch.setParameters(param.getBatchParam());
		return facade.executeBatch(batch, cs.getConnection());
	}
	
	/**
	 * @return the parameter
	 */
	private QueryParameter createQueryParameter(FreeQueryParameter param){
		QueryParameter parameter = createParameter(new QueryParameter(),param);		
		parameter.setMaxSize(param.getMaxSize());
		parameter.setFirstResult(param.getFirstResult());
		parameter.setResultType(param.getResultType());
		
		if(param.getHints().containsKey(PersistenceHints.SQLENGINE_JDBC_FETCHSIZE)){
			parameter.setFetchSize((Integer)param.getHints().get(PersistenceHints.SQLENGINE_JDBC_FETCHSIZE));
		}
		
		if(param.getFilter() != null){
			parameter.setFilter(new DelegatingResultSetFilter(param.getFilter()));
		}
		return parameter;
	}
	
	/**
	 * @param <S> the type
	 * @param parameter the parameter
	 * @return the parameter
	 */
	private <S extends SQLParameter> S createParameter(S parameter,FreeParameter param){
		S sqlParam = createBaseParameter(parameter,param);
		sqlParam.setAllParameter(param.getParam());
		return sqlParam;
	}
	
	/**
	 * Creates the baseParameter.
	 * @param parameter the parameter
	 * @return the result
	 */
	private <S extends BaseSQLParameter> S createBaseParameter(S parameter,FreeParameter param){
		parameter.setSqlId(param.getQueryId());
		parameter.setSql(param.getSql());		

		if(param.getHints().containsKey(PersistenceHints.SQLENGINE_JDBC_TIMEOUT)){
			parameter.setTimeoutSeconds((Integer)param.getHints().get(PersistenceHints.SQLENGINE_JDBC_TIMEOUT));
		}
		parameter.setAllBranchParameter(param.getBranchParam());
		parameter.setUseRowSql(param.isUseRowSql());
		return parameter;
	}

}
