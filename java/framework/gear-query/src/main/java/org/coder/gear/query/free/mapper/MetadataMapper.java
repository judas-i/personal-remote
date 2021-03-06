/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gets the one record from ResultSet.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface MetadataMapper {
	
	/**
	 * @param rs the rs
	 * @return the result 
	 */
	<T> T getRecord(ResultSet rs) throws SQLException;
}
