/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;

import java.util.ArrayList;
import java.util.List;

import org.coder.alpha.query.free.gateway.PersistenceGateway;





/**
 * BatchModifyQueryImpl.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultBatchModifyQuery implements BatchModifyQuery{
	
	/** parameters */
	private List<ModifyingConditions> parameters = new ArrayList<ModifyingConditions>();
	
	/** the internal query */
	private PersistenceGateway gateway;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	DefaultBatchModifyQuery(PersistenceGateway gateway){
		this.gateway = gateway;
	}

	/**
	 * @see org.coder.alpha.query.free.BatchModifyQuery#addBatch(alpha.query.free.NativeUpdate)
	 */
	@Override
	public void addBatch(ModifyingConditions parameter) {
		parameters.add(parameter);
	}

	/**
	 * @see org.coder.alpha.query.free.BatchModifyQuery#addBatch(org.coder.alpha.query.free.AbstractNativeModifyQuery)
	 */
	@Override
	public void addBatch(AbstractNativeModifyQuery parameter) {
		addBatch(parameter.getParameter());
	}


	/**
	 * @see org.coder.alpha.query.free.BatchModifyQuery#executeBatch()
	 */
	@Override
	public int[] modify() {
		try{
			return gateway.executeBatch(parameters);
		}finally{
			parameters.clear();
		}
	}

}