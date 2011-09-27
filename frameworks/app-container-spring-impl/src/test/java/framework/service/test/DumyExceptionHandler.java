/**
 * Copyright 2011 the original author
 */
package framework.service.test;

import javax.persistence.PessimisticLockException;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.OptimisticLockException;

import framework.service.core.exception.AbstractJPAExceptionHandler;
import framework.service.core.transaction.ServiceContext;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DumyExceptionHandler extends AbstractJPAExceptionHandler{

	/**
	 * @see framework.service.core.exception.AbstractJPAExceptionHandler#handleOptimisticLockException(org.eclipse.persistence.exceptions.OptimisticLockException)
	 */
	@Override
	protected Object handleOptimisticLockException(OptimisticLockException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();

		if( context.isSuppressOptimisticLockError() ){
			System.out.println("ロチE��連番チェチE��エラー無要E);
		}else{
			throw e;
		}
		return null;
	}
	
	/**
	 * @see framework.service.core.exception.AbstractJPAExceptionHandler#handleOptimisticLockException(org.eclipse.persistence.exceptions.OptimisticLockException)
	 */
	@Override
	protected Object handleDatabaseException(DatabaseException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();

		if( context.isSuppressOptimisticLockError() ){
			System.out.println("チE�Eタベ�Eスエラー無要E);
		}else{
			throw e;
		}
		return null;
	}
	
	/**
	 * @see framework.service.core.exception.AbstractJPAExceptionHandler#handleOptimisticLockException(org.eclipse.persistence.exceptions.OptimisticLockException)
	 */
	@Override
	protected Object handlePessimisticLockException(PessimisticLockException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();

		if( context.isSuppressOptimisticLockError() ){
			System.out.println("悲観ロチE��エラー無要E);
		}else{
			throw e;
		}
		return null;
	}

}
