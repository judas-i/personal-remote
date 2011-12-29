/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.exception;

import kosmos.framework.core.message.Messages;


/**
 * The unexpected exception thrown if no result is found.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UnexpectedDataFoundException extends SystemException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param message the message
	 * @param cause the cause
	 */
	public UnexpectedDataFoundException() {
		super(Messages.MSG_SYS_UNEXPECTED_DATA_FOUND);
	}
	
}