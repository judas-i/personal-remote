package framework.web.core.api.query.paging;

import java.util.List;
import framework.sqlclient.api.free.AbstractNativeQuery;

/**
 * ページングコンテキスト.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public interface PagingContext {

	/**
	 * @return 現在のページのデータ
	 */
	public List getCurrentPageData();

	/**
	 * @return 総件数
	 */
	public int getTotalCount();

	/**
	 * @return 総ページ数
	 */
	public int getTotalPage();

	/**
	 * @return 現在取得しているデータのページ番号(1～)
	 */
	public int getCurrentPageNo();

	/**
	 * ページング準備.
	 * @param query クエリ
	 * @param pageSize ページサイズ
	 */
	public List prepare(AbstractNativeQuery query, int pageSize);

	/**
	 * ページデータ管理機構に問い合わせてデータをロードする.
	 * @param pageNo ページ番号
	 * @return データ
	 */
	public List getPageData(int pageNo);

}
