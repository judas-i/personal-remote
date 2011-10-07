/**
 * Copyright 2011 the original author
 */
package framework.service.ext.advice;

import org.aspectj.lang.ProceedingJoinPoint;

import framework.service.core.advice.InternalInterceptor;

/**
 * the intercepter to delegate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DelegatingInterceptor{
	
	/** the advice */
	private InternalInterceptor advice;
	
	/**
	 * @param advice the advice
	 */
	public void setInternal(InternalInterceptor advice){		
		this.advice = advice;
	}

	/**
	 * @param invocation　the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	public Object around(ProceedingJoinPoint invocation) throws Throwable {
		return advice.around(new ContextAdapterImpl(invocation));
	}

}
