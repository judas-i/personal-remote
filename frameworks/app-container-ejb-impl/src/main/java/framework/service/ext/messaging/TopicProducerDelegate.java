/**
 * Use is subject to license terms.
 */
package framework.service.ext.messaging;

import framework.api.dto.RequestDto;
import framework.service.core.locator.ServiceLocator;
import framework.service.core.messaging.AbstractMessageProducer;

/**
 * Topic用.
 *
 * @author yoshida-n
 * @version	created.
 */
public class TopicProducerDelegate extends AbstractMessageProducer{

	/**
	 * @see framework.service.core.messaging.AbstractMessageProducer#invoke(framework.api.dto.RequestDto, java.lang.String)
	 */
	@Override
	protected Object invoke(RequestDto dto, String destinationName)
			throws Throwable {
		
		ServiceLocator.lookupByInterface(JmsProducer.class).publish(dto, destinationName);
		return null;
	}

}
