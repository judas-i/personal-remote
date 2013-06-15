/**
 * Copyright 2011 the original author
 */
package service.testcase;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PessimisticLockException;

import org.apache.log4j.MDC;
import org.coder.alpha.query.criteria.ComparingOperand;
import org.coder.alpha.query.criteria.query.ListReadQuery;
import org.coder.alpha.query.criteria.query.SingleReadQuery;
import org.coder.alpha.query.criteria.query.UpdateQuery;
import org.coder.alpha.query.exception.UniqueConstraintException;
import org.eclipse.persistence.config.QueryHints;

import service.Registry;
import service.entity.DateEntity;
import service.entity.EmbeddPK;
import service.entity.EmbeddedEntity;
import service.entity.IDateEntity;
import service.entity.ITestEntity;
import service.entity.TestEntity;
import service.services.RequiresNewService;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Stateless
public class LocalEntityQueryTestBean extends BaseCase {
	
	
	public void isNullisNotNull(){
		SingleReadQuery<TestEntity> query = createSingleReader(TestEntity.class);
		query.isNotNull(ITestEntity.ATTR).isNull(ITestEntity.ATTR).call();
		
		UpdateQuery<TestEntity> modifier = createUpdater(TestEntity.class);
		modifier.isNotNull(ITestEntity.ATTR).isNull(ITestEntity.ATTR);
		modifier.set(ITestEntity.ATTR2, 100).call();
	}
	
	public void embed() {
		
		SingleReadQuery<EmbeddedEntity> query = createSingleReader(EmbeddedEntity.class);
		query.addCriteria("id.keyAbc", ComparingOperand.Equal, "11");
		query.call();
		
		
		EmbeddPK pk = new EmbeddPK();
		EmbeddedEntity e = new EmbeddedEntity();
		pk.setKeyAbc("ABC");
		pk.setKeyDef("DEF");
		e.setAttr("aaa");
		e.setAttr2(100);
		e.setPrimaryKeys(pk);
		persist(e);
		flush();
	}
	
	
	public void duplicateError(){
		//1件目
		TestEntity e = new TestEntity();
		e.setAttr("attr");
		e.setTest("1");
		em.persist(e);
		
		//2件目
		TestEntity e2 = new TestEntity();
		e2.setAttr("attr");
		e2.setTest("1");
		em.persist(e2);
		
		try{
			em.flush();
			fail();
		}catch(UniqueConstraintException dbe){
			dbe.printStackTrace();
		}
	}
	
	public void allCondition(){
		setUpData("TEST.xls");
	
		ListReadQuery<TestEntity> query = createListReader(TestEntity.class);	
		query.setHint(QueryHints.HINT,"/*+ HINT */");
		
		List<TestEntity> result = getOneRecord(query);
	
		assertEquals(1,result.size());
		
		TestEntity first = result.get(0);
		em.detach(first);
		first.setAttr("100");
		
		ListReadQuery<TestEntity> forres = createListReader(TestEntity.class);
		List<TestEntity> updated = getOneRecord(forres);	
		assertEquals("2",updated.get(0).getAttr());
		
		context.setRollbackOnly();
	}

	public void disableDetach(){
		setUpData("TEST.xls");
		//更新前取征E

		ListReadQuery<TestEntity> query =createListReader(TestEntity.class);
		List<TestEntity> result = getOneRecord(query);	
		assertEquals(1,result.size());
		
		//更新
		TestEntity first = result.get(0);
		first.setAttr("100");
	
		//更新結果
		SingleReadQuery<TestEntity> forres =createSingleReader(TestEntity.class);
		TestEntity entity = forres.eq(ITestEntity.TEST, "2").call();
		assertEquals("100",entity.getAttr());
		
		context.setRollbackOnly();
	}


	/**
	 * 更新後検索
	 */
	public void updateAny(){
		setUpData("TEST.xls");

		//更新
		UpdateQuery<TestEntity> update = createUpdater(TestEntity.class);
		update.eq(ITestEntity.TEST, "2");
		update.set(ITestEntity.ATTR, "AAA").call();
		
		//検索
		SingleReadQuery<TestEntity> query = createSingleReader(TestEntity.class);		
		
		//更新結果(NamedUpdate更新前に検索してぁE��ば永続化コンチE��スト�E更新前キャチE��ュが使用されるためrefleshする忁E��あり。今回はNamedUpdate実行してぁE��ぁE�Eでreflesh不要E��E
		TestEntity entity = query.eq(ITestEntity.TEST, "2").call();
		assertEquals("AAA",entity.getAttr());
		
		context.setRollbackOnly();
	}
	

	/**
	 * 1件取得　降頁E��ーチE
	 */

	public void callWithDesc(){
		setUpData("TEST.xls");

		SingleReadQuery<TestEntity> query = createSingleReader(TestEntity.class);
		query.desc(ITestEntity.TEST);
		TestEntity result = query.call();
		assertEquals("2",result.getAttr());
		
		context.setRollbackOnly();
	}
	
	/**
	 * 1件取得　昁E��E��ーチE
	 */

	public void callWithAsc(){
		setUpData("TEST.xls");
		SingleReadQuery<TestEntity> query = createSingleReader(TestEntity.class);
		query.asc(ITestEntity.TEST);
		TestEntity result = query.call();
		assertEquals("3",result.getAttr());
		
		context.setRollbackOnly();
	}
	

	/**
	 * 2件目取征E
	 */

	public void callSetFirstWithDesc(){
		setUpData("TEST.xls");
		

		SingleReadQuery<TestEntity> query = createSingleReader(TestEntity.class);
		query.desc(ITestEntity.TEST);
		query.setFirstResult(1);
		TestEntity result = query.call();
		assertEquals("3",result.getAttr());
		
		context.setRollbackOnly();
	}
	
	/**
	 * 2件目から取征E
	 */

	public void getResultSetFirst(){
		setUpData("TEST.xls");
		

		ListReadQuery<TestEntity> query = createListReader(TestEntity.class);
		query.setFirstResult(1);
		List<TestEntity> result = query.call();
		assertEquals(1,result.size());
		assertEquals("2",result.get(0).getAttr());
		
		context.setRollbackOnly();
	}
	
	/**
	 * 2件目から3件目取征E
	 */

	public void getResultSetFirstMax2(){
		setUpData("TEST.xls");
		

		TestEntity f = new TestEntity();
		f.setTest("900").setAttr("900").setAttr2(900);
		em.persist(f);
		
		TestEntity s = new TestEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(100);	//versionNoの持E���E無視される
		em.persist(s);
		
		TestEntity t = new TestEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		em.persist(t);
		
		ListReadQuery<TestEntity> query = createListReader(TestEntity.class);
		query.desc(ITestEntity.TEST);
		query.contains(ITestEntity.TEST, Arrays.asList("0","1,","2","900","901","902"));
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<TestEntity> result = query.call();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());	//忁E��楽観ロチE��番号は1からinsert
		assertEquals("900",result.get(1).getAttr());
		
		//更新
		result.get(0).setAttr("AAA");
		em.flush();
		
		//楽観ロチE��番号インクリメント確誁E
		result = query.call();		
		assertEquals(2,result.get(0).getVersion());
		
		context.setRollbackOnly();
	}
	
	/**
//	 * 0件シスチE��エラー
//	 */
//
//	public void nodataError(){
//		try{	
//			
//			ListReadQuery<TestEntity> query = createListReader(TestEntity.class).enableNoDataError();
//			query.eq(ITestEntity.TEST, "AGA").call();
//			context.setRollbackOnly();
//			fail();
//		}catch(UnexpectedNoDataFoundException une){
//			une.printStackTrace();
//			context.setRollbackOnly();
//		}catch(Throwable t){
//			t.printStackTrace();
//			fail();
//		}
//	}
	
	/**
	 * PK検索
	 */

	public void find(){	
		setUpData("TEST.xls");	
		TestEntity result = em.find(TestEntity.class,"1");
		em.detach(result);
		result.setAttr("1100");
		
		
		result = em.find(TestEntity.class,"1");
		assertNotEquals("1100",result.getAttr());
		context.setRollbackOnly();
	}
	
	/**
	 * PK検索、更新
	 */

	public void findDisableDetach(){
		setUpData("TEST.xls");	
		TestEntity result = em.find(TestEntity.class,"1");
		result.setAttr("1100");
		
		result = em.find(TestEntity.class,"1");
		assertEquals("1100",result.getAttr());
		context.setRollbackOnly();
	}

//	/**
//	 * 0件シスチE��エラー
//	 */
//
//	public void findNodataError(){
//		try{	
//
//			ListReadQuery<TestEntity> query = createListReader(TestEntity.class);
//			query.enableNoDataError();
//			query.find("AA");
//			fail();
//		}catch(UnexpectedNoDataFoundException une){
//			une.printStackTrace();
//		}
//		context.setRollbackOnly();
//	}
	/**
	
//	/**
//	 * 0件シスチE��エラー
//	 */
//
//	public void findAnyNodataError(){
//		try{	
//
//			ListReadQuery<TestEntity> query = createListReader(TestEntity.class);
//			query.enableNoDataError();
//			query.eq(ITestEntity.TEST, "aaa");
//			query.findAny();
//			fail();
//		}catch(UnexpectedNoDataFoundException une){
//			une.printStackTrace();
//		}catch(Throwable t){
//			fail();
//		}
//		context.setRollbackOnly();
//	}
	
	/**
	 *  存在チェチE�� not 
	 */

	public void exists(){
		
		setUpData("TEST.xls");
		ListReadQuery<TestEntity> query = createListReader(TestEntity.class);		
		assertEquals(true,query.call() != null);
		context.setRollbackOnly();
	}
	
	/**
	 * PK存在チェチE��
	 */

	public void isEmptyPK(){
		setUpData("TEST.xls");
		assertEquals(false,em.find(TestEntity.class,"AGA") != null);
		context.setRollbackOnly();
	}
	
	/**
	 * PK存在チェチE��
	 */

	public void existsPK(){
		setUpData("TEST.xls");	
			
		assertEquals(true,em.find(TestEntity.class,"1") != null);
		context.setRollbackOnly();
	}
	
	/**
	 * 一意制紁E��ラー
	 */
	
	public void uniqueConstraintError(){
		setUpData("TEST.xls");	
		TestEntity entity = new TestEntity();
		entity.setTest("1");
		entity.setAttr("aaa");
		entity.setAttr2(30);
		
		em.persist(entity);
		
		try{
			em.flush();
			fail();
		}catch(UniqueConstraintException de){
			de.printStackTrace();
		}
	
	}
	
	/**
	 * 一意制紁E��ラー無要E
	 */
	
	public void ignoreUniqueConstraintError(){
		setUpData("TEST.xls");
		//一意制紁E��効匁E
		MDC.put("ID","IGNORE_TEST");
	
		TestEntity entity = new TestEntity();
		entity.setTest("1");
		entity.setAttr("aaa");
		entity.setAttr2(30);
		
		em.persist(entity);	
		em.flush();
		
		MDC.put("ID","aaa");
		
		TestEntity entity2 = new TestEntity();
		entity2.setTest("530");
		entity2.setAttr("aaa");
		entity2.setAttr2(30);
	
		em.persist(entity2);	
		context.setRollbackOnly();

	}
	
	/**
	 * ロチE��連番チェチE��エラー
	 */
	
	public void versionNoError(){	
		setUpData("TEST.xls");
		final TestEntity result = em.find(TestEntity.class,"1");
		result.setVersion(2);
		try{
			em.flush();
			fail();
		}catch(OptimisticLockException e){
			return;
		}
	}
	/**
	 * ロチE��連番チェチE��エラー無要E
	 */
	
	public void ignoreVersionNoError(){
		setUpData("TEST.xls");
		//ロチE��連番エラー無効匁E行単位�E更新をさせる場合、こぁE��るか自律トランザクションにする忁E��がある�E�E
		MDC.put("ID","IGNORE_TEST");
					

		final TestEntity result = em.find(TestEntity.class,"1");
		result.setVersion(2);
		
		
		//ロチE��連番エラー無要EDumyExceptionHandlerで握りつぶし！E
		em.flush(
//				new FlushHandler(){
//
//			@Override
//			public void handle(RuntimeException pe) {
//				pe.printStackTrace();
//				assertEquals(OptimisticLockException.class,pe.getClass());		
//				//リフレチE��ュしてロチE��連番をDBと合わせておかなぁE��次のFlushでも失敗してしまぁE��E
//				per.reflesh(result);
//			}
//			
//		}
				);
		MDC.put("ID","IGNORE_TE");
		
		//バ�Eジョン番号を指定しなぁE��め更新成功
		TestEntity res2 =  em.find(TestEntity.class,"2");
		assertEquals(0,res2.getVersion());
		res2.setAttr("aa");
		em.flush();

		assertEquals("aa", em.find(TestEntity.class,"2").getAttr());
		context.setRollbackOnly();
	}
	
	/**
	 * 悲観ロチE��エラー無効匁E
	 * @throws SQLException 
	 */

	public void invalidFindWithLockNoWaitError(){
		
		RequiresNewService service = Registry.getComponentFinder().getBean(RequiresNewService.class);
		service.persist();
		
		MDC.put("ID","IGNORE_TEST");
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		em.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
	
		assertEquals("OK",service.test());	
		
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */
	public void findWithLockNoWaitError(){	
		
		RequiresNewService service = Registry.getComponentFinder().getBean(RequiresNewService.class);
		service.persist();
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		em.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);

		try{
			//トランザクション墁E��でもスローされた例外�Eそ�EままキャチE��可能
			service.test();
			fail();
		}catch(EJBException ejbe){
			Exception e = ejbe.getCausedByException();
			if(e instanceof PessimisticLockException ){
				PessimisticLockException pe = (PessimisticLockException)e;
				SQLException sqle = (SQLException)pe.getCause().getCause();
				assertEquals("54",String.valueOf(sqle.getErrorCode()));
			}else{
				ejbe.printStackTrace();
				fail();
			}
		}
		
	}
	
	/**
	 * 自律トランザクション先で例外にぎり潰した時、E
	 * 自律トランザクションで例外になっても呼び出し�EでキャチE��してぁE��ば問題なぁE
	 * 
	 * @throws SQLException 
	 */

	public void crushExceptionInAutonomousTransaction(){	
		
		RequiresNewService service = Registry.getComponentFinder().getBean(RequiresNewService.class);
		service.persist();
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT,0);
		em.find(TestEntity.class,"1",LockModeType.PESSIMISTIC_READ,hints);
		//Springと異なり、自律トランザクション先のEntityManagerがrollbackOnlyでもExceptionはスローされない。
		assertEquals("NG",service.crushException());
			
		
	}
	
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */

	public void queryPessimisticLockError(){	
		
		RequiresNewService service = Registry.getComponentFinder().getBean(RequiresNewService.class);
		service.persist();
		
				
		ListReadQuery<TestEntity> query = createListReader(TestEntity.class);
		query.setLockModeType(LockModeType.PESSIMISTIC_READ);
		query.eq(ITestEntity.TEST,"1");
		
		query.call();	//callめEaxResult持E���E場吁EQL構文エラー　ↁEEclipseLinkのバグ
		try{
			service.test();			
			fail();
		}catch(EJBException ejbe){
			Exception e = ejbe.getCausedByException();
			if(e instanceof PessimisticLockException ){
				PessimisticLockException pe = (PessimisticLockException)e;
				SQLException sqle = (SQLException)pe.getCause().getCause();
				assertEquals("54",String.valueOf(sqle.getErrorCode()));
			}else{
				ejbe.printStackTrace();
				fail();
			}
		}
	
	}
	
	/**
	 * 悲観ロチE��エラー
	 * @throws SQLException 
	 */

	public void invalidQueryPessimisticLockError(){	
		MDC.put("ID","IGNORE_TEST");
		
		RequiresNewService service = Registry.getComponentFinder().getBean(RequiresNewService.class);
		service.persist();
		
				
		ListReadQuery<TestEntity> query = createListReader(TestEntity.class);
		query.setLockModeType(LockModeType.PESSIMISTIC_READ);
		query.eq(ITestEntity.TEST,"1");
		query.setHint(QueryHints.HINT, "/* TEST */");
		
		query.call();			
		assertEquals("OK",service.test());
			
	}
	
	
	/**
	 * メチE��ージ持E��E
	 */
	
	/**
	 * メチE��ージ持E��E
	 */

	public void dateCheck(){
	
		DateEntity entity = new DateEntity();
		entity.setTest("aa").setAttr("aaa").setAttr2(100).setDateCol(new Date());
		em.persist(entity);
		
		SingleReadQuery<DateEntity> query = createSingleReader(DateEntity.class);
		assertEquals(false,query.eq(IDateEntity.DATE_COL, new Date()).eq(IDateEntity.TEST,"aaaa").call()!=null);
		context.setRollbackOnly();
	}

	
	/**
	 * @return
	 */
	private List<TestEntity> getOneRecord(ListReadQuery<TestEntity> query ){	
		query.between(ITestEntity.TEST, "0", "30").eq(ITestEntity.TEST,"2").gtEq(ITestEntity.TEST, "0").ltEq(ITestEntity.TEST, "30").lt(ITestEntity.TEST, "30").gt(ITestEntity.TEST, "0");		
		query.between(ITestEntity.ATTR, "0", "20").eq(ITestEntity.ATTR,"2").gtEq(ITestEntity.ATTR, "0").ltEq(ITestEntity.ATTR, "20").lt(ITestEntity.ATTR, "20").gt(ITestEntity.ATTR, "0");
		query.between(ITestEntity.ATTR2, 0, 100).eq(ITestEntity.ATTR2,2).gtEq(ITestEntity.ATTR2, 0).ltEq(ITestEntity.ATTR2, 100).lt(ITestEntity.ATTR2, 100).gt(ITestEntity.ATTR2, 0);		
		query.contains(ITestEntity.TEST, Arrays.asList("2","2","2"));
		return query.call();
	}
	
	
	public void remote() {
		
	}
}
