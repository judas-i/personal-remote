/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;



/**
 * The base of the queries.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface BatchUpdate {
	
	/**
	 * Adds the JPA hint.
	 * 
	 * @param <T> the type
	 * @param arg0 the key of the hint
	 * @param arg1 the hint value
	 * @return self
	 */
	public <T extends BatchUpdate> T addBatch();
	
	/**
	 * Updates the data.
	 * 
	 * @return the updated count
	 */
	public int[] update();
	
}
