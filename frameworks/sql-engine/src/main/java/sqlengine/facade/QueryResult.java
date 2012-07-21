/**
 * Copyright 2011 the original author
 */
package sqlengine.facade;

import java.util.List;

/**
 * The query result.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class QueryResult {

	/** if true hit count is more than max size */
	private final boolean limited;
	
	/** the data */
	private final List resultList;
	
	/** the hit count */
	private final int hitCount;

	/**
	 * @param limited the limited
	 * @param result the result
	 * @param hitCount the hit count
	 */
	public QueryResult(boolean limited ,List result , int hitCount){
		this.limited = limited;
		this.resultList = result;
		this.hitCount = hitCount;
	}
	
	/**
	 * @return f true hit count is more than max size
	 */
	public boolean isLimited(){
		return this.limited;
	}
	
	/**
	 * @return the result
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(){
		return this.resultList;
	}
	
	/**
	 * @return the hit count
	 */
	public int getHitCount(){
		return this.hitCount;
	}
}