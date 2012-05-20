/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.services;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockException;

import kosmos.framework.service.core.activation.ServiceLocatorImpl;
import kosmos.framework.service.core.activation.ServiceLocator;
import kosmos.framework.service.core.entity.TestEntity;
import kosmos.framework.sqlclient.orm.OrmQuery;
import kosmos.framework.sqlclient.orm.OrmQueryFactory;

import org.eclipse.persistence.config.QueryHints;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class RequiresNewReadOnlyServiceImpl implements RequiresNewReadOnlyService{

	public String test() {
		OrmQueryFactory ormQueryFactory = ServiceLocator.createDefaultOrmQueryFactory();
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0);
		query.find("1");
		return "OK";
	}

	@Override
	public String crushException() {
		OrmQueryFactory ormQueryFactory = ServiceLocatorImpl.createDefaultOrmQueryFactory();
		OrmQuery<TestEntity> query = ormQueryFactory.createQuery(TestEntity.class);
		try{
			//握り潰し、ただしExceptionHandlerでにぎり潰してぁE��ければJPASessionのロールバックフラグはtrueになめE
			query.setLockMode(LockModeType.PESSIMISTIC_READ).setHint(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 0);
			query.find("1");
		}catch(PessimisticLockException pe){
			return "NG";
		}
		return "OK";
	}

}