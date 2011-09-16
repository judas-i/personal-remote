/**
 * Use is subject to license terms.
 */
package framework.sqlengine.executer.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import framework.sqlengine.executer.Selecter;

/**
 * SQL発行処理、リトライ等が必要な場合にはここで対応する.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SelecterImpl implements Selecter{

	/**
	 * @see framework.sqlengine.executer.Selecter#select(java.sql.PreparedStatement)
	 */
	@Override
	public ResultSet select(PreparedStatement stmt) throws SQLException {
		return stmt.executeQuery();
	}

}