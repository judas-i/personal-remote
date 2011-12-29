/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.logics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Utility for query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryUtils {

	/** the date format */
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	/**
	 * @param params　the parameter
	 * @param sqlString the SQL for <code>PreparedStatement</code>
	 * @return the SQL binded value to ?
	 */
	public static final String applyValues(List<Object> params , String sqlString){

		Iterator<Object> ite = params.iterator();
		String converted = sqlString;
		
		//?にパラメータを埋め込む
		while(converted.contains("?")){
			if( !ite.hasNext() ){
				throw new IllegalStateException("count of ? is different from parameter count");
			}
			Object v = ite.next();
			converted = StringUtils.replaceOnce(converted, "?", getDisplayValue(v));		
		}	
		return converted;
	}
	
	/**
	 * Gets the displaying value.
	 * @param o the object
	 * @return the value
	 */
	public static String getDisplayValue(Object o){
		if( o instanceof String ){
			return "\'" + o.toString() + "\'";
		} else if (o instanceof Date) {
			return format.format((Date) o);			
		}else{
			return String.valueOf(o);
		}	
	}
	
}
