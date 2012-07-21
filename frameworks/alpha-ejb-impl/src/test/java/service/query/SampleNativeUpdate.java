/**
 * Copyright 2011 the original author
 */
package service.query;

import client.sql.free.AbstractNativeModifyQuery;
import client.sql.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_NATIVE_UPDATE.sql")
public class SampleNativeUpdate extends AbstractNativeModifyQuery{

	public enum Bind {
		test,
		attr,
		attr2set
	}
	public enum Branch {
		test,
		attr,
		attr2,
		arc
	}
	
	/**
	 * @param value
	 */
	public SampleNativeUpdate setTest(String value){
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeUpdate setAttr(String value){
		setParameter(Bind.attr.name(), value);		
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNativeUpdate setAttr2(Integer value){
		setParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNativeUpdate setArc(Object value){
		setParameter(Branch.arc.name(), value);
		return this;
	}

	/**
	 * @param value
	 */
	public SampleNativeUpdate setAttr2set(Integer value){
		setParameter(Bind.attr2set.name(), value);
		return this;
	}
}