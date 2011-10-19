/**
 * Copyright 2011 the original author
 */
package framework.service.ext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import framework.sqlengine.builder.ConstCache;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ServiceUnit extends Assert{
	
	protected static EJBContainer container;
	
	/**
	 * @throws Exception
	 */
	@BeforeClass
    public static void setUpClass() throws Exception {
		Map<String,Object> prop = new HashMap<String,Object>();
		prop.put(EJBContainer.APP_NAME, "test");
		prop.put("org.glassfish.ejb.embedded.glassfish.instance.root", "C:/Env/Personal/glassfishv3/glassfish/domains/domain1");
		container = EJBContainer.createEJBContainer(prop);
		
		StubServiceLocator locator = new StubServiceLocator();
		locator.initialize(new DefaultComponentBuilder());
		
		try{
			Class<?> clazz = Class.forName(CachableConst.class.getName());					
			Field[] fs = clazz.getFields();
			for(Field f: fs){
				if(!Modifier.isStatic(f.getModifiers()) || !Modifier.isFinal(f.getModifiers()) || Modifier.isInterface(f.getModifiers())){
					continue;
				}
				Object value = f.get(null);
				if( ConstCache.containsKey(f.getName())){
					throw new IllegalArgumentException(" duplicate filed name = " + f.getName());
				}else{
					ConstCache.put(f.getName(), value);
				}
			}
		}catch(Exception e){
			throw new IllegalStateException("failed to load cache",e);
		}	
	}
	
	/**
	 * @throws Exception
	 */
	@AfterClass
    public static void close() throws Exception {
		container.close();
	}
}
