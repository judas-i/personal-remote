/**
 * Copyright 2011 the original author
 */
package framework.core.exception;

/**
 * 実装不備を示す例外。
 * この例外がスローされた場合は実装ミスによるバグなのでアプリケーションを見直すこと。
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PoorImplementationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_MESSAGE = "Poor Implementation : ";
	
	/**
	 * @param mes メッセージ
	 */
	public PoorImplementationException(String mes){
		super(DEFAULT_MESSAGE+mes);
	}
	
	/**
	 * @param mes メッセージ
	 * @param t 例外
	 */
	public PoorImplementationException(String mes , Throwable t){
		super(DEFAULT_MESSAGE+mes,t);
	}
	
}
