package framework.service.ext.async;

import java.lang.reflect.Method;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

import framework.service.core.async.AsyncService;
import framework.service.core.locator.ServiceLocator;

/**
 * 非同期処理実行エンジン.
 *
 * @author yoshida-n
 * @version	2011/04/07 created.
 */
@Asynchronous
@Stateless
public class AsyncServiceImpl implements AsyncService{

	/**
	 * @see framework.service.ext.core.async.AsyncExecutor#execute(java.lang.Object, java.lang.reflect.Method, java.lang.Object)
	 */
	public Future<Object> execute(Object proxy, Method method , Object args) throws Exception{	
		
		Object service = ServiceLocator.lookupByInterface(method.getDeclaringClass());
		
		Object value  = null;
		if(method.getParameterTypes() == null || method.getParameterTypes().length == 0){
			value = method.invoke(service);
		}else{
			value = method.invoke(service, args);
		}
		return new AsyncResult<Object>(value);
	}
}