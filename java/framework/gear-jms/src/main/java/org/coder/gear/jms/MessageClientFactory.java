/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.jms;

import java.lang.reflect.Proxy;

import javax.jms.ConnectionFactory;


/**
 * The factory to create JMS producer.
 *
 * @author	yoshida-n
 * @version	1.0
 */
public class MessageClientFactory{

	/**
	 * @param service
	 * @param property
	 * @return
	 */
	public <T> T createProducer(Class<T> service,MessagingProperty property , ConnectionFactory factory) {		
		MessageInvocationHandler handler = new MessageInvocationHandler(property,factory);
		return service.cast(Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, handler));
	}

}
