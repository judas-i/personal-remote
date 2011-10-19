/**
 * Copyright 2011 the original author
 */
package framework.service.core.services;

import framework.api.query.services.PagingRequest;
import framework.api.query.services.PagingResult;
import framework.api.query.services.PagingService;
import framework.service.core.query.Pager;
import framework.sqlclient.api.free.AbstractNativeQuery;
import framework.sqlclient.api.free.QueryFactory;

/**
 * A paging service.
 * 
 * <pre>
 * Always execute SQL to get latest data.
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractLatestPagingService implements PagingService{

	/**
	 * @return the factory to create the query
	 */
	protected abstract QueryFactory getQueryFactory();

	/**
	 * @see framework.api.query.services.PagingService#prepare(framework.api.query.services.PagingRequest)
	 */
	@Override
	public PagingResult prepare(PagingRequest request) {
		
		//件数取得。件数制限なしを想定しているため大量件数の場合ResultSet#next()でインクリメントするとパフォーマンスが非常に悪い。そのためcountで件数のみ取得する
		AbstractNativeQuery query = createQuery(request);	
	
		//ページング開始
		return Pager.create().prepare(query,request.getStartPosition(),request.getPageSize());
	}
	
	/**
	 * @see framework.api.query.services.PagingService#getPageData(framework.api.query.services.PagingRequest)
	 */
	@Override
	public PagingResult getPageData(PagingRequest request) {
		
		AbstractNativeQuery query = createQuery(request);

		//ページング開始
		return Pager.create().paging(query,request.getStartPosition(), request.getPageSize(),request.getTotalCount());
	
	}
	
	/**
	 * @param request　the request
	 * @return the query
	 */
	private AbstractNativeQuery createQuery(PagingRequest request){
		
		if( request.getInternal().getFirstResult() != 0){
			throw new IllegalArgumentException("'firstResult' must be 0 for paging");
		}
		if( request.getInternal().getMaxSize() != 0 ){
			throw new IllegalArgumentException("'maxSize' must be 0");
		}	
		if( request.getPageSize() <= 0 ){
			throw new IllegalArgumentException("'pageSize' must be greater than 0");
		}	
		Class<AbstractNativeQuery> queryClass = request.getInternal().getQueryClass(); 
		AbstractNativeQuery query = getQueryFactory().createQuery(queryClass);
		
		return ParameterConverter.setParameters(request.getInternal(), query);
	}
	
}
