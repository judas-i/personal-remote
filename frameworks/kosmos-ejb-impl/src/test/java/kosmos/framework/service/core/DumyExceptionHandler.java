/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import kosmos.framework.jpqlclient.customizer.JPQLExceptionHandlerImpl;
import kosmos.framework.service.core.transaction.ServiceContext;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.OptimisticLockException;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DumyExceptionHandler extends JPQLExceptionHandlerImpl{

	//TODO ここで判定するにはDat
	
	protected Object handleOptimisticLockException(OptimisticLockException e){
		ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();

		if( context.isSuppressOptimisticLockError() ){
			System.out.println("ロック連番");
		}else{
			throw e;
		}
		return null;
	}

	@Override
	public Object handleException(RuntimeException exception) {
		//楽観ロック例外かDatabaseExceptionしかない
		if(exception instanceof OptimisticLockException){
			return handleOptimisticLockException((OptimisticLockException)exception);
		}else if(exception instanceof DatabaseException){
			try{
				return super.handleException(exception);
			}catch(RuntimeException uce){
				ServiceTestContextImpl context = (ServiceTestContextImpl)ServiceContext.getCurrentInstance();

				if( context.isSuppressOptimisticLockError() ){
					System.out.println("ロック連番");
				}else{
					throw uce;
				}
				return null;
			}
		}
		throw exception;
	}

}
