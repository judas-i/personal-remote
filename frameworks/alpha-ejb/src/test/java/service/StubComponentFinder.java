/**
 * Copyright 2011 the original author
 */
package service;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.coder.alpha.ejb.registry.UnifiedComponentFinder;





/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class StubComponentFinder extends UnifiedComponentFinder{

	/**
	 * @see org.coder.alpha.ejb.registry.UnifiedComponentFinder#getBean(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String serviceName,Properties prop){
		
		String test = String.format("java:global/test/test-classes/%s",serviceName);		
		String format = String.format("java:global/test/classes/%s",serviceName);		
		try{
			return (T)lookupFormat(test);
		}catch(Exception e){
			return (T)lookupFormat(format);
		}

	}
	
	/**
	 * @param format
	 * @param prop
	 * @return
	 */
	private Object lookupFormat(String format){
		Properties properties = new Properties();
	    properties.put("java.naming.factory.initial", "com.sun.enterprise.naming.impl.SerialInitContextFactory");
	    properties.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
	    properties.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
	    properties.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
	    properties.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
		
		try{		
			return new InitialContext(properties).lookup(format);
			
		}catch(NamingException ne){
			
			try{
				return new InitialContext().lookup(format);
			}catch(NamingException ee){
				throw new IllegalArgumentException("Failed to load alpha.domain ", ne);
			}
		}
	}

}
