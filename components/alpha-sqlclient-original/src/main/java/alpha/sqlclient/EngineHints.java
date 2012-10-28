/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient;

import alpha.sqlclient.PersistenceHints;

/**
 * EngineHints.
 *
 * @author yoshida-n
 * @version	created.
 */
public class EngineHints extends PersistenceHints {
	
	private static final long serialVersionUID = 2114220196556253634L;

	/** the jdbc timeout for 'SQLEngine'*/
	public static final String SQLENGINE_JDBC_TIMEOUT = "alpha.jdbc.jdbc.timeout";

	/** the jdbc fetch size for 'SQLEngine' */
	public static final String SQLENGINE_JDBC_FETCHSIZE = "alpha.jdbc.jdbc.fetchsize";
	
	public static final String BSQLENGINE_BATCH_INERT = "alpha.jdbc.batch.insert";

	/**
	 * Sets the jdbc timeout.
	 * @param timeout 
	 * @return self
	 */
	public PersistenceHints setJdbcTimeout(int timeout) {
		put(SQLENGINE_JDBC_TIMEOUT,timeout);
		return this;
	}
	
	/**
	 * @return the time out
	 */
	public int getJdbcTimeout() {
		return (Integer)get(SQLENGINE_JDBC_TIMEOUT);
	}
	
	/**
	 * Sets the fetch size.
	 * @param fetchSize
	 * @return self
	 */
	public PersistenceHints setJdbcFetchsize(int fetchSize) {
		put(SQLENGINE_JDBC_FETCHSIZE,fetchSize);
		return this;
	}
	
	/**
	 * @return the fetching size.
	 */
	public int getJdbcFetchsize() {
		return (Integer)get(SQLENGINE_JDBC_FETCHSIZE);
	}

}