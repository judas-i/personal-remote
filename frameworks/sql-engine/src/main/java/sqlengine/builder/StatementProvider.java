/**
 * Copyright 2011 the original author
 */
package sqlengine.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Provides the <code>Statement</code>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StatementProvider {
	
	/**
	 * Creates the statement.
	 * For {@link PreparedStatement#executeBatch()}.
	 * 
	 * @param sqlId the queryId
	 * @param con the connection
	 * @param sql the SQL
	 * @return the statement
	 */
	PreparedStatement createStatement(String sqlId,Connection con ,String sql);

}
