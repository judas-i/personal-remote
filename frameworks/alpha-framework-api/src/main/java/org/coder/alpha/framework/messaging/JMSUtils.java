/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging;

import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.lang.StringUtils;

/**
 * JMS Utility.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class JMSUtils {

	/**
	 * Set property and header
	 * @param property the property
	 * @param message the message
	 */
	public static void setPropertyAndHeader(MessagingProperty property, Message message)
	throws JMSException{
		if(StringUtils.isNotEmpty(property.getJmsCorrelationID())){
			message.setJMSCorrelationID(property.getJmsCorrelationID());
		}
		if(StringUtils.isNotEmpty(property.getJmsType())){
			message.setJMSType(property.getJmsType());
		}
		for(Entry<String,Object> e : property.getJmsProperty().entrySet()){
			Object v = e.getValue();
			if(v instanceof String){
				message.setStringProperty(e.getKey(), v.toString());
			}else if(v instanceof Double){
				message.setDoubleProperty(e.getKey(), (Double)v);
			}else if(v instanceof Long ){
				message.setLongProperty(e.getKey(), (Long)v);
			}else if(v instanceof Integer){
				message.setIntProperty(e.getKey(), (Integer)v);
			}else if(v instanceof Boolean){
				message.setBooleanProperty(e.getKey(), (Boolean)v);
			}else if(v instanceof Byte){
				message.setByteProperty(e.getKey(), (Byte)v);
			}else if(v instanceof Float){
				message.setFloatProperty(e.getKey(), (Float)v);
			}else if(v instanceof Short){
				message.setShortProperty(e.getKey(), (Short)v);
			}else{
				message.setObjectProperty(e.getKey(),v);
			}
		}
	}
	
}
