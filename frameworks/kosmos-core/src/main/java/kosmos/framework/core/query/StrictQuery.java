/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query;

import java.util.List;



/**
 * StrictQuery.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StrictQuery<T> extends LimitedOrmQuery<T>{

	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> eq(Metadata<T, V> column, V value);
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> eqFix(Metadata<T, V> column, String value);

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> gt(Metadata<T, V> column, V value);
	
	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> gtFix(Metadata<T, V> column, String value);

	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> lt(Metadata<T, V> column, V value);
	
	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> ltFix(Metadata<T, V> column, String value);

	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> gtEq(Metadata<T, V> column, V value);
	
	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> gtEqFix(Metadata<T, V> column, String value);

	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> ltEq(Metadata<T, V> column, V value);
	
	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> ltEqFix(Metadata<T, V> column, String value);

	/**
	 * Adds 'between'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param from the from-value to be added
	 * @param to the to-value to be added 
	 * @return self
	 */
	public abstract <V> StrictQuery<T> between(Metadata<T, V> column,
			V from, V to);

	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> containsList(Metadata<T, V> column, List<V> value);
	
	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public abstract <V> StrictQuery<T> contains(Metadata<T, V> column, V... value);
	
	/**
	 * Adds 'ASC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public abstract StrictQuery<T> asc(Metadata<T, ?> column);

	/**
	 * Adds 'DESC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public abstract StrictQuery<T> desc(Metadata<T, ?> column);

	/**
	 * Searches the result.
	 * 
	 * @return the result
	 */
	public abstract List<T> getResultList();

	/**
	 * Searches the first result.
	 * 
	 * @return the result
	 */
	public abstract T getSingleResult();

	/**
	 * Determines whether the result searched by primary keys is found.
	 * 
	 * @return true:exsists
	 */
	public abstract boolean exists();
}
