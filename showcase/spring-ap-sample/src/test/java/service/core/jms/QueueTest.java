/**
 * Copyright 2011 the original author
 */
package service.core.jms;


import javax.persistence.EntityManager;

import org.coder.alpha.message.messaging.MessageClientFactory;
import org.coder.alpha.message.messaging.MessagingProperty;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;


import service.test.MockService;
import service.test.ServiceUnit;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Ignore
@ContextConfiguration(locations = {"/META-INF/context/oracleAgentApplicationContext.xml","/META-INF/context/jmsApplicationContext.xml"})
public class QueueTest extends ServiceUnit{
	
	@Autowired
	private MessageClientFactory messageClientFactory;

	/**
	 * Queue
	 */
	@Test
	@Rollback(false)
	public void queueSend(){
		
		MessagingProperty property = new MessagingProperty();
		property.setDestinationName("jms/DefaultQueue");
		MockService service = 
			messageClientFactory.createSender(MockService.class, property);
		
		service.exec("arg2");	
		
	}
	

	/**
	 * Topic
	 */
	@Test
	@Rollback(false)
	public void topicSend(){
		MessagingProperty property = new MessagingProperty();
		property.setDestinationName("jms/DefaultTopic");
		MockService service = messageClientFactory.createPublisher(MockService.class, property);
		service.exec("TEST");		
	}


	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return null;
	}
}