/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.query.paging.impl;

import java.util.List;
import framework.api.query.services.PagingRequest;
import framework.api.query.services.PagingResult;
import framework.api.query.services.PagingService;
import framework.sqlclient.api.free.AbstractNativeQuery;
import framework.sqlclient.api.free.QueryAccessor;
import framework.web.core.api.query.WebNativeQueryEngine;
import framework.web.core.api.query.paging.PagingContext;

/**
 * ããEã¸ã³ã°ç¨ã³ã³ãE­ã¹ãE
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class PagingContextImpl implements PagingContext {
	
	/** ããEã¸ã³ã°ãµã¼ãã¹ */
	private final PagingService pagingService;

	/** 1ããEã¸ããããEä»¶æ°  */
	private int pageSize;
	
	/** ãªã¯ã¨ã¹ããã©ã¡ã¼ã¿ */
	private PagingRequest request;
	
	/** ããEã¸ã³ã°æE ± */
	private PagingResult result;
	
	/**
	 * @param pagingService ãµã¼ãã¹
	 */
	PagingContextImpl(PagingService pagingService){
		this.pagingService = pagingService;
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getCurrentPageData()
	 */
	@Override
	public List getCurrentPageData(){
		return result.getCurrentPageData();
	}
	
	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getTotalCount()
	 */
	@Override
	public int getTotalCount() {
		return result.getTotalCount();
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getTotalPage()
	 */
	@Override
	public int getTotalPage() {
		int totalCount = getTotalCount();
		int mod = totalCount % pageSize; 
		return mod == 0 ? totalCount/pageSize : totalCount /pageSize + 1;  
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getCurrentPageNo()
	 */
	@Override
	public int getCurrentPageNo() {
		return result.getCurrentPageNo();
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#prepare(framework.sqlclient.api.free.AbstractNativeQuery, int)
	 */
	@Override
	public List prepare(AbstractNativeQuery query,int pageSize){

		WebNativeQueryEngine engine = (WebNativeQueryEngine)QueryAccessor.getDelegate(query);
		PagingRequest request = new PagingRequest();
		request.setInternal(engine.getRequest());
		request.setPageSize(pageSize);
		
		result = pagingService.prepare(request);	
		this.request = request;
		this.pageSize = pageSize;
		
		return getCurrentPageData();
	}

	/**
	 * @see framework.web.core.api.query.paging.PagingContext#getPageData(int)
	 */
	@Override
	public List getPageData(int pageNo) {

		//ä¾ãã°ããEã¸ãµã¤ãº50ä»¶ã§2ããEã¸ç®ãæå®ããããE1ããã«ãã
		if( pageNo > getTotalPage() ){
			throw new IllegalArgumentException("'pageNo' must be 'totalPageCount' and less ");
		}	
		request.setStartPosition( (pageNo - 1)* pageSize ) ;
		request.setTotalCount(getTotalCount());
		
		result = pagingService.getPageData(request);
		
		return getCurrentPageData();
	}
	
}
