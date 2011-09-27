/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

import java.util.List;


/**
 * ネイティブクエリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NativeQuery extends FreeQuery{
	
	/**
	 * @return ヒットカウント
	 */
	public <T> NativeResult<T> getTotalResult();
	
	/**
	 * @return フェッチして取得
	 */
	public <T> List<T> getFetchResult();
	
	/**
	 * @param <T> 型 
	 * @param filter フィルター
	 * @return self
	 */
	@SuppressWarnings("rawtypes")
	public <T extends NativeQuery> T setFilter(ResultSetFilter filter);
	
}
