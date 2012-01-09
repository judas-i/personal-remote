/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.advice;


import org.aspectj.lang.ProceedingJoinPoint;


/**
 * The wrapper of the joinPoint.
 *
 * @author yoshida-n
 * @version	created.
 */
public class InvocationAdapterImpl implements InvocationAdapter{
	
	private ProceedingJoinPoint joinPoint;
	
	/**
	 * @param joinPoint the joinPoint
	 */
	public InvocationAdapterImpl(ProceedingJoinPoint joinPoint){
		this.joinPoint = joinPoint;
	}

	/**
	 * @see kosmos.framework.core.activation.InvocationAdapter#getMethodName()
	 */
	@Override
	public String getMethodName() {
		return joinPoint.getSignature().getName();
	}

	/**
	 * @see kosmos.framework.core.activation.InvocationAdapter#getArgs()
	 */
	@Override
	public Object[] getArgs() {
		return joinPoint.getArgs();
	}

	/**
	 * @see kosmos.framework.core.activation.InvocationAdapter#getThis()
	 */
	@Override
	public Object getThis() {
		return joinPoint.getThis();
	}

	/**
	 * @see kosmos.framework.core.activation.InvocationAdapter#proceed()
	 */
	@Override
	public Object proceed() throws Throwable{
		return joinPoint.proceed();
	}

	/**
	 * @see kosmos.framework.core.activation.InvocationAdapter#getDeclaringTypeName()
	 */
	@Override
	public String getDeclaringTypeName() {
		return joinPoint.getSignature().getDeclaringTypeName();
	}

}
