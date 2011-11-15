/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import kosmos.framework.sqlclient.api.free.AbstractUpdate;

/**
 * NamedUpdate.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNamedUpdate extends AbstractUpdate<NamedUpdate> implements NamedUpdate{
	

	/**
	 * @see kosmos.framework.jpqlclient.api.free.NamedUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends NamedUpdate> T setHint(String arg0, Object arg1) {
		delegate.setHint(arg0,arg1);
		return (T)this;
	}
	
}