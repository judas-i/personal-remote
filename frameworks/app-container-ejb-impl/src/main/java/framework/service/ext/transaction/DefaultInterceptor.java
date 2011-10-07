/**
 * Copyright 2011 the original author
 */
package framework.service.ext.transaction;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import framework.service.ext.advice.ContextAdapterImpl;
import framework.service.ext.advice.InternalDefaultInterceptor;

/**
 * The default interceptor.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultInterceptor {
	
	/**
	 * @param ic the context
	 * @return the result
	 * @throws Throwable the exception
	 */
	@AroundInvoke
	public Object around(InvocationContext ic) throws Throwable {
		return new InternalDefaultInterceptor().around(new ContextAdapterImpl(ic));
	}
}
