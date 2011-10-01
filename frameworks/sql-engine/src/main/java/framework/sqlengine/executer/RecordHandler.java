/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;

/**
 * Gets the one record from ResultSet.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface RecordHandler<T> {
	
	/**
	 * @param rs the rs
	 * @return the result 
	 */
	public T getRecord(ResultSet rs);
}
