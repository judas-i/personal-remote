/**
 * Use is subject to license terms.
 */
package framework.service.ext.services;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import framework.api.query.services.PagingService;
import framework.service.core.services.AbstractLatestPagingService;
import framework.sqlclient.api.free.QueryFactory;

/**
 * 最新取得用ページングサービス.
 *
 * @author yoshida-n
 * @version	2011/05/13 created.
 */
@Service("LatestPagingServiceImpl")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class LatestPagingServiceImpl extends AbstractLatestPagingService implements PagingService{

	/** クエリファクトリ */
	@Resource
	private QueryFactory clientQueryFactory;

	/**
	 * @see framework.service.core.services.AbstractLatestPagingService#getQueryFactory()
	 */
	@Override
	protected QueryFactory getQueryFactory() {
		return clientQueryFactory;
	}
	
}