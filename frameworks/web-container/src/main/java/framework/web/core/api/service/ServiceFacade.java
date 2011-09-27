/**
 * Copyright 2011 the original author
 */
package framework.web.core.api.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * サービスファサードを示すアノテーション.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceFacade {

	/** エイリアス */
	String alias() default "";
	
}
