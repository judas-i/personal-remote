/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.txscriptejb.interceptor;

import java.lang.reflect.Method;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.coder.alpha.trace.Tracer;

/**
 * TraceInterceptor.
 *
 * @author yoshida-n
 * @version	1.0
 */
@Traceable
@Interceptor
public class TraceInterceptor {
	
	/**
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object around(InvocationContext context) throws Throwable {
		Method method = context.getMethod();
		Tracer.start(method.getDeclaringClass(),method.getName());		
		try{
			return context.proceed();
		}finally {
			Tracer.stop();
		}
	}

}