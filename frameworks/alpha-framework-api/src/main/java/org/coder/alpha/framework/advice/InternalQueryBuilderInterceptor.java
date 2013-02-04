/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.coder.alpha.framework.logging.DefaultQueryLogger;
import org.coder.alpha.framework.logging.QueryLogger;



/**
 * An advice for the SQL Builder.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalQueryBuilderInterceptor implements InternalInterceptor{

	/** the instance of logging */
	private QueryLogger logger = new DefaultQueryLogger();
	
	/** the list contains query id */
	private List<String> ignoreList = new ArrayList<String>();
	
	/**
	 * @param logger the logger to set
	 */
	public void setQueryLogger(QueryLogger logger){
		this.logger = logger;
	}
	
	/**
	 * @return enabled
	 */
	public boolean isEnabled(){
		return logger.isDebugEnabled();
	}
	
	/**
	 * @param ignoreList the ignoreList to set
	 */
	public void setIgnoreList(String ignoreList){
		String[] ignore = ignoreList.split(",");
		this.ignoreList = Arrays.asList(ignore);
	}

	/**
	 * @see org.coder.alpha.framework.advice.InternalInterceptor#around(org.coder.alpha.framework.advice.InvocationAdapter)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object around(InvocationAdapter contextInvoker) throws Throwable {				
		if(logger.isTraceEnabled() && !ignoreList.contains(contextInvoker.getArgs()[2])){						
			Map<String,Object> parameter = (Map<String,Object>)(contextInvoker.getArgs()[1]);
			String previous = String.class.cast(contextInvoker.getArgs()[0]);
			StringBuilder builder = new StringBuilder();
			for(Map.Entry<String, Object> v : parameter.entrySet()){						
				if(v.getValue() instanceof String){
					builder.append(v.getKey()).append("=\'").append(v.getValue()).append("\' ");	
				}else{
					builder.append(v.getKey()).append("=").append(v.getValue()).append(" ");
				}
			}
			logger.trace(String.format("sql before prepared statement \n%s\n[%s]",previous,builder.toString()));				
		}
		//変換後ログ
		Object result = contextInvoker.proceed();
		if(logger.isTraceEnabled() && !ignoreList.contains(contextInvoker.getArgs()[2])){		
			String replaced = String.class.cast(result);	
			logger.trace(String.format("sql after evaluate \n%s\n",replaced));				
		}
		return result;
	}

}