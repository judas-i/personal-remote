/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.customizer;

import java.sql.SQLException;

import kosmos.framework.sqlclient.exception.DeadLockException;
import kosmos.framework.sqlclient.exception.UniqueConstraintException;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.ExceptionHandler;

/**
 * Handles the JPQL exception.
 *
 * @author yoshida-n
 * @version	created.
 */
public class JPQLExceptionHandlerImpl implements ExceptionHandler{
	
	/** 一意制約エラー */
	private static final int uniqueErrorCode = 1;

	/** デッドロック */
	private static final int deadLockErrorcode = 60;
	

	/**
	 * @see org.eclipse.persistence.exceptions.ExceptionHandler#handleException(java.lang.RuntimeException)
	 */
	@Override
	public Object handleException(RuntimeException exception) {
		if(exception instanceof DatabaseException){
			DatabaseException de = (DatabaseException)exception;
			Throwable t = de.getInternalException();
			
			//基本的にDatabaseExceptionをスローした場合、この後でJPAが適切なPersistenceExceptionに変換する
			//DeadLockとか一意制約とか自動で変換されないやつだけここで変換する。
			
			if(t instanceof SQLException){
				SQLException sqle = (SQLException)t;
				int code = sqle.getErrorCode();
				//一意制約エラー
				if(code == uniqueErrorCode){
					throw new UniqueConstraintException(sqle);				
				//デッドロック
				}else if(code == deadLockErrorcode){
					throw new DeadLockException(sqle);
				}				
			}
			throw de;			
		}else{
			throw exception;
		}
	}

}
