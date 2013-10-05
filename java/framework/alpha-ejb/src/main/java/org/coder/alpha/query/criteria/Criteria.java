/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.criteria;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.coder.alpha.query.free.query.Conditions;



/**
 * The condition.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class Criteria {
	
	/** the column name */
	private final String colName; 
	
	/** the binding value's count */
	private int bindCount;
	
	/** the operand */
	private final Operand operand;
	
	/** the binding value */
	private final Object value;

	/**
	 * @param colName the colName
	 * @param bindCount the bindCount
	 * @param operand the operand
	 * @param value the from value
	 * @param toValue the to value 
	 */
	public Criteria(String colName , int bindCount ,Operand operand , Object value){
		this.colName = colName;
		this.bindCount = bindCount;
		this.operand = operand;
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return the operand
	 */
	public Operand getOperand() {
		return operand;
	}

	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}
	
	/**
	 * @return the colName
	 */
	public int getBindCount() {
		return bindCount;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	/**
	 * Returns the expression
	 * @param tableAlias the alias
	 * @return expression
	 */
	public String getExpression(String tableAlias){
		String name = tableAlias == null ? colName : tableAlias + "." + colName;
		return operand.getExpression(name, colName+"_" + bindCount, value);
	}
	
	/**
	 * Sets the parameter 
	 * @param tableAlias the alias
	 * @return expression
	 */
	public void accept(Conditions conditions){
		operand.setParameter(conditions, colName + "_" + bindCount, value);
	}
}
