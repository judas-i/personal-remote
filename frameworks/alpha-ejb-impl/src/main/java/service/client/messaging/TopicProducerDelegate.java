/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import service.framework.core.activation.ServiceLocator;

/**
 * The producer for TOPIC.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class TopicProducerDelegate extends AbstractMessageProducer{

	/**
	 * @see service.client.messaging.AbstractMessageProducer#invoke(service.client.messaging.InvocationParameter, java.lang.String)
	 */
	@Override
	protected Object invoke(InvocationParameter dto, String destinationName)
			throws Throwable {
		
		ServiceLocator.getService(JmsProducer.class).publish(dto, destinationName);
		return null;
	}

}