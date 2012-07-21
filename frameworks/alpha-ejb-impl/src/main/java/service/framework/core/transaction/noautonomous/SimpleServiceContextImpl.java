/**
 * Copyright 2011 the original author
 */
package service.framework.core.transaction.noautonomous;

import service.framework.core.transaction.ServiceContext;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SimpleServiceContextImpl extends ServiceContext{

	private boolean rollbackOnly = false;
	
	/**
	 * @see service.framework.core.transaction.ServiceContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() {
		this.rollbackOnly = true;
	}

	/**
	 * @see service.framework.core.transaction.ServiceContext#isRollbackOnly()
	 */
	@Override
	public boolean isRollbackOnly() {
		return rollbackOnly;
	}

}
