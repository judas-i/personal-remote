/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class DomainContext {
	
	private Locale locale;
	
	/** the level of call stack */
	private int callStackLevel = 0;
	
	/** the requestId */
	private String requestId = null;

	/** the messageList */
	private List<Object> messageList = new ArrayList<Object>();
	
	/** flag of error message */
	private boolean failed = false;


	/** the thread local instance*/
	private static ThreadLocal<DomainContext> instance = new ThreadLocal<DomainContext>(){
		protected DomainContext initialValue() {
			return null;
		}
	};

	/**
	 * @param context the context to set
	 */
	protected static void setCurrentInstance(DomainContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}

	/**
	 * @return the current context
	 */
	public static DomainContext getCurrentInstance(){
		return instance.get();
	}
		
	/**
	 * @param requestId
	 */
	public void setRequestId(String requestId){
		this.requestId = requestId;
	}
	
	/**
	 * @return
	 */
	public String getRequestId(){
		return this.requestId;
	}
	
	/**
	 * @return the level of the call stack
	 */
	public int getCallStackLevel(){
		return callStackLevel;
	}
	
	/**
	 * push call stack.
	 */
	public void pushCallStack(){
		callStackLevel++;
	}
	
	/**
	 * pop call stack.
	 */
	public void popCallStack(){
		callStackLevel--;
	}
	
	/**
	 * @param message the message 
	 */
	public void addMessage(Object message){		
		//重複しているメッセージは追加しない
		for(Object o : messageList){
			if(o.equals(message)){
				return;
			}
		}
		messageList.add(message);
		if(getTxVerifier().isRollbackRequired(message)){
			setRollbackOnly();
			setFailed(true);
		}
			
	}	
	
	/**
	 * @return TxVerifier
	 */
	protected abstract TxVerifier getTxVerifier();

	/**
	 * @return the globalMessageList
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getMessageList(){
		return (List<T>)messageList;
	}
	
	/**
	 * Initializes the context.
	 */
	public void initialize(){
		release();
		setCurrentInstance(this);
		
	}
	
	/**
	 * Releases the context.
	 */
	public void release(){			
		locale = null;
		callStackLevel = 0;
		requestId = null;	
		failed = false;
		messageList = new ArrayList<Object>();
		setCurrentInstance(null);
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	/**
	 * @return failed
	 */
	public boolean isFailed() {
		return failed;
	}

	/**
	 * @param failed the failed to set
	 */
	protected void setFailed(boolean failed){
		this.failed = failed;
	}
	
	/**
	 * Set the current transaction to rolling back.
	 */
	public abstract void setRollbackOnly();
	
	/**
	 * @return true:rollback only
	 */
	public abstract boolean isRollbackOnly();

}