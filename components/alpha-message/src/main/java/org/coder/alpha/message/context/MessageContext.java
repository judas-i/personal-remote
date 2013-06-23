/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.message.context;

import org.coder.alpha.message.object.Message;
import org.coder.alpha.message.object.Messages;


/**
 * The thread-local context.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageContext {
	
	/** the messages. */
	private Messages messages;
	
	private boolean rollbackOnly = false;
	
	private MessageHandler messageHandler = null;
	
	/** the thread local instance*/
	private static ThreadLocal<MessageContext> instance = new ThreadLocal<MessageContext>(){
		protected MessageContext initialValue() {
			return null;
		}
	};

	/**
	 * @param context the context to set
	 */
	protected static void setCurrentInstance(MessageContext context){
		if (context == null) {
			instance.remove();
		} else {
			instance.set(context);
		}
	}
	
	public void setMessageHandler(MessageHandler messageHandler){
		this.messageHandler = messageHandler;
	}

	/**
	 * @return the current context
	 */
	public static MessageContext getCurrentInstance(){
		return instance.get();
	}

	/**
	 * @param message to set
	 */
	public void addMessage(Message message){		
		if(messageHandler.isRollbackOnly(message)){
			this.rollbackOnly = true;
		}
		this.messages.add(message);
	}	

	/**
	 * @return the messages
	 */
	public Messages getMessages(){
		return this.messages;
	}
	
	/**
	 * @return if rollback only
	 */
	public boolean isRollbackOnly(){
		return rollbackOnly;
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
		rollbackOnly = false;
		messages = new Messages();
		messageHandler = null;
		setCurrentInstance(null);
	}

}