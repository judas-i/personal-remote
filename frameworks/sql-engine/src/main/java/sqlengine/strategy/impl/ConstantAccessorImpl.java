/**
 * Copyright 2011 the original author
 */
package sqlengine.strategy.impl;

import sqlengine.domain.ConstantCache;
import sqlengine.exception.QueryException;
import sqlengine.strategy.ConstantAccessor;

/**
 * Gets the constant.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ConstantAccessorImpl implements ConstantAccessor {

	/** the prefix. */
	private String constPrefix = "c_";

	/**
	 * @param constPrefix the constPrefix to set
	 */
	public final void setConstPrefix(String constPrefix) {
		this.constPrefix = constPrefix;
	}

	/**
	 * @see sqlengine.strategy.ConstantAccessor#getConstTarget(java.lang.String)
	 */
	@Override
	public Object[] getConstTarget(String variableName) {

		// 定数値が指定されていた場合、定数値をバインド値に追加する
		if (variableName.startsWith(constPrefix)) {
			String fieldName = variableName.substring(constPrefix.length());
			if (ConstantCache.containsKey(fieldName)) {
				Object[] value = new Object[1];
				value[0] = ConstantCache.get(fieldName);
				return value;
			} else {
				throw new QueryException("[Poor Implementation ] No cache was found . key = " + fieldName);
			}
		}
		return new Object[0];
	}

}