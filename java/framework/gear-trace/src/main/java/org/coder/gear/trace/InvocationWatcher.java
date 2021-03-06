/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.trace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * InvocationWatcher.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class InvocationWatcher {
	
	/** the logger */
	private static final Log LOG = LogFactory.getLog(InvocationWatcher.class);
	
	/** the startTime */
	private long startTime = 0L;
	
	/** call stack */
	private final int callStack;

	/** the target className */
	private final String className;
	
	/** the target method name */
	private final String methodName;
	
	/**
	 * @return true is enabled
	 */
	public static boolean isEnabled() {
		return LOG.isInfoEnabled();
	}
	
	/**
	 * @param className the className to set
	 * @param methodName the methodName to set
	 */
	public InvocationWatcher(Class<?> declaringType, String methodName, int callStack){
		this.className = declaringType.getName();
		this.methodName = methodName;
		this.callStack = callStack;
	}
	
	/**
	 * Starts watch.
	 */
	public void start() {
		if(!isEnabled()){
			return;
		}
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Stops watch.
	 */
	public void stop() {
		if(!isEnabled()){
			return;
		}
		long end = System.currentTimeMillis() - startTime;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < callStack; i++) {
			builder.append("\t");
		}
		LOG.info(String.format("msec %d:\t%s%s.%s", end, builder.toString(),className,methodName));
	}
	
}
