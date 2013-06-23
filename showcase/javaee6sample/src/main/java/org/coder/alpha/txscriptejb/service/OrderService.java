/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.coder.alpha.txscriptejb.domain.OrderBizLogic;
import org.coder.alpha.txscriptejb.interceptor.Traceable;

/**
 * OrderService.
 *
 * @author yoshida-n
 * @version	created.
 */
@Traceable
@Stateless	//SessionBeanでなくてもCMTに対応してほしい
public class OrderService {

	/** デフォルトだとOrderServiceと同じライフサイクルになる */
	@Inject
	private OrderBizLogic bizLogic;
	
	/**
	 * Persist.
	 * @param orderNo
	 */
	public void persist(String orderNo){
		bizLogic.persist(orderNo);
	}
}