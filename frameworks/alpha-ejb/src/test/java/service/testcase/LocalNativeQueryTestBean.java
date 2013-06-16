/**
 * Copyright 2011 the original author
 */
package service.testcase;

import java.sql.Connection;
import java.util.List;

import javax.persistence.Query;

import org.coder.alpha.query.criteria.query.ListReadQuery;
import org.coder.alpha.query.criteria.query.SingleReadQuery;
import org.coder.alpha.query.free.QueryCallback;
import org.coder.alpha.query.free.result.TotalList;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.ScrollableCursor;

import service.CachableConst;
import service.entity.ITestEntity;
import service.entity.TargetEntity;
import service.query.SampleNativeQuery;
import service.query.SampleNativeQueryConst;
import service.query.SampleNativeResult;
import service.query.SampleNativeUpdate;



/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LocalNativeQueryTestBean extends BaseCase{
	
	
	public void paging() {
		
		for(int i = 0; i < 150; i++){
			Query query = em.createNativeQuery("select 1 from dual");
			query.setHint(QueryHints.SCROLLABLE_CURSOR, HintValues.TRUE);
			ScrollableCursor cursor = (ScrollableCursor)query.getSingleResult();
			cursor.close();
			em.unwrap(Connection.class);
		}
		
		em.createNativeQuery("delete from testa").executeUpdate();
		for(int i = 0 ; i < 100; i ++){
			TargetEntity e = new TargetEntity();
			String v = i+"";
			if( v.length() == 1) v = "0" +v;
			e.setTest(v);
			em.persist(e);
		}
		SampleNativeQuery query = createSelect(SampleNativeQuery.class);		
		query.getParameter().setSql("select * from testa order by test");
		query.setMaxResults(30);
		TotalList<SampleNativeResult> data = query.getTotalResult();
		assertEquals(100,data.getHitCount());
		List<SampleNativeResult> resultList = data;
		assertEquals(30,resultList.size());
		assertEquals("00",resultList.get(0).getTest());
		assertEquals("29",resultList.get(29).getTest());
		
		query = createSelect(SampleNativeQuery.class);		
		query.getParameter().setSql("select * from testa order by test");
		query.setMaxResults(30).setFirstResult(30);
		data = query.getTotalResult();
		assertEquals(100,data.getHitCount());
		resultList = data;
		assertEquals(30,resultList.size());
		assertEquals("30",resultList.get(0).getTest());
		assertEquals("59",resultList.get(29).getTest());
		
		query = createSelect(SampleNativeQuery.class);	
		query.getParameter().setSql("select * from testa order by test");
		query.setMaxResults(30).setFirstResult(60);
		data = query.getTotalResult();
		assertEquals(100,data.getHitCount());
		resultList = data;
		assertEquals(30,resultList.size());
		assertEquals("60",resultList.get(0).getTest());
		assertEquals("89",resultList.get(29).getTest());
		
		query = createSelect(SampleNativeQuery.class);	
		query.getParameter().setSql("select * from testa order by test");
		query.setMaxResults(30).setFirstResult(90);
		data = query.getTotalResult();
		assertEquals(100,data.getHitCount());
		resultList = data;
		assertEquals(10,resultList.size());
		assertEquals("90",resultList.get(0).getTest());
		assertEquals("99",resultList.get(9).getTest());
		
	}
	
	public void fetch() {
		em.createNativeQuery("delete from testa").executeUpdate();
		for(int i = 0 ; i < 100; i ++){
			TargetEntity e = new TargetEntity();
			String v = i+"";
			if( v.length() == 1) v = "0" +v;
			e.setTest(v);
			em.persist(e);
		}
		SampleNativeQuery query = createSelect(SampleNativeQuery.class);		
		query.getParameter().setSql("select * from testa order by test");
		query.setMaxResults(30);
		List<SampleNativeResult> result = query.getFetchResult();
		int count = 0;
		SampleNativeResult last = null;
		for(SampleNativeResult r : result){
			count++;
			last = r;
		}
		assertEquals("29",last.getTest());
		assertEquals(30,count);

		
		query = createSelect(SampleNativeQuery.class);		
		query.getParameter().setSql("select * from testa order by test");
		query.setFirstResult(30).setMaxResults(30);
		result = query.getFetchResult();
		count = 0;
		last = null;
		for(SampleNativeResult r : result){
			count++;
			last = r;
		}
		assertEquals("59",last.getTest());
		assertEquals(30,count);
		
		query = createSelect(SampleNativeQuery.class);		
		query.getParameter().setSql("select * from testa order by test");
		query.setFirstResult(60).setMaxResults(30);
		result = query.getFetchResult();
		count = 0;
		last = null;
		for(SampleNativeResult r : result){
			count++;
			last = r;
		}
		assertEquals("89",last.getTest());
		assertEquals(30,count);
		
		query = createSelect(SampleNativeQuery.class);		
		query.getParameter().setSql("select * from testa order by test");
		query.setFirstResult(90).setMaxResults(30);
		result = query.getFetchResult();
		count = 0;
		last = null;
		for(SampleNativeResult r : result){
			count++;
			last = r;
		}
		assertEquals("99",last.getTest());
		assertEquals(10,count);
	}
	
	/**
	 * 通常検索
	 */
	public void select(){
		setUpData("TEST.xls");
		SampleNativeQuery query = createSelect(SampleNativeQuery.class);		
		query.setTest("1");
		List<SampleNativeResult> result = query.getResultList();
		assertEquals("3",result.get(0).getAttr());
		context.setRollbackOnly();	
	}
	/**
	 * 通常検索if刁E
	 */
	public void selectIfAttr(){
		setUpData("TEST.xls");
		SampleNativeQuery query = createSelect(SampleNativeQuery.class);
		query.setAttr("1000");
		query.setTest("1");
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(0,result.size());
		context.setRollbackOnly();	
	}
	

	/**
	 * if斁E��索
	 * 数値比輁E��not null、文字�E比輁E
	 */
	
	public void selectIfAttr2(){
		setUpData("TEST.xls");
		SampleNativeQuery query = createSelect(SampleNativeQuery.class);
		query.setAttr2(500).setTest("1").setArc("500");
		
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(0,result.size());
		context.setRollbackOnly();	
	}
	
//	/**
//	 * 結果0件シスチE��エラー
//	 */
//	
//	public void nodataError(){
//		setUpData("TEST.xls");
//		SampleNativeQuery query = createSelect(SampleNativeQuery.class).enableNoDataError();
//		query.setAttr2(500).setTest("1").setArc("500");
//		
//		try{
//			query.getResultList();
//			fail();
//		}catch(UnexpectedNoDataFoundException e){
//			e.printStackTrace();
//		}
//		context.setRollbackOnly();	
//	}
	
//	/**
//	 * exists
//	 */
//	
//	public void exists(){
//		setUpData("TEST.xls");
//		SampleNativeQuery query = createSelect(SampleNativeQuery.class);
//		query.setAttr2(500).setTest("1");
//		assertTrue(query.exists());
//		context.setRollbackOnly();	
//	}

	/**
	 * getSingleResult
	 */	
	public void getSingleResult(){
		setUpData("TEST.xls");
		SampleNativeQuery query = createSelect(SampleNativeQuery.class);
		query.setAttr2(500).setTest("1");
		SampleNativeResult e = query.getSingleResult();
		assertEquals("1",e.getTest());
		context.setRollbackOnly();	
	}
	
	/**
	 * setMaxSize
	 */
	
	public void setMaxSize(){
		TargetEntity entity = new TargetEntity();
		entity.setTest("1000").setAttr("aa").setAttr2(111);
		setUpData("TEST.xls");
		SampleNativeQuery query = createSelect(SampleNativeQuery.class).setMaxResults(2);
		List<SampleNativeResult> e = query.getResultList();
		assertEquals(2,e.size());
		e.get(0);
		e.get(1);
		context.setRollbackOnly();	
	}
	
	/**
	 * setFirstResult、E件目�E�E件目取征E
	 */
	
	public void setFirstResult(){
		setUpData("TEST.xls");
		
		TargetEntity f = new TargetEntity();
		f.setTest("900").setAttr("900").setAttr2(900);
		persist(f);
		
		TargetEntity s = new TargetEntity();
		s.setTest("901").setAttr("901").setAttr2(900).setVersion(100);	//versionNoの持E���E無視される
		persist(s);
		
		TargetEntity t = new TargetEntity();
		t.setTest("902").setAttr("902").setAttr2(900);
		persist(t);
		flush();
		
		SampleNativeQuery query = createSelect(SampleNativeQuery.class);		
		query.setFirstResult(1);
		query.setMaxResults(2);
		List<SampleNativeResult> result = query.getResultList();
		assertEquals(2,result.size());
		assertEquals("901",result.get(0).getAttr());
		assertEquals(1,result.get(0).getVersion());	//忁E��楽観ロチE��番号は1からinsert
		assertEquals("900",result.get(1).getAttr());
		context.setRollbackOnly();	
	}
	
	/**
	 * $test = c_TARGET_CONST_1
	 */
	
	public void constTest(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = createSelect(SampleNativeQueryConst.class);
		c.setTest("1");
		List<SampleNativeResult> e = c.getResultList();
		assertEquals(1,e.size());
		context.setRollbackOnly();	
	}

	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	
	public void constAttr(){
	
		setUpData("TEST.xls");
		SampleNativeQueryConst c = createSelect(SampleNativeQueryConst.class);
		c.setTest("2");
		c.setAttr(CachableConst.TARGET_TEST_1_OK);
		List<SampleNativeResult> e = c.getResultList();
		assertEquals(1,e.size());
		context.setRollbackOnly();	
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	
	public void constVersionNo(){
	
		setUpData("TEST.xls");
		SingleReadQuery<TargetEntity> eq = createSingleReader(TargetEntity.class);
		eq.eq(ITestEntity.TEST, "1").call().setAttr2(CachableConst.TARGET_INT);
		flush();
		
		SampleNativeQueryConst c = createSelect(SampleNativeQueryConst.class);
		c.setArc(CachableConst.TARGET_INT);		
		List<SampleNativeResult> e = c.getResultList();
		assertEquals(1,e.size());
		context.setRollbackOnly();	
	}

	/**
	 * total result
	 */
	
	public void getHitCount(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = createSelect(SampleNativeQuery.class);
		query.setMaxResults(1);
		TotalList<SampleNativeResult> result = query.getTotalResult();
		assertEquals(2,result.getHitCount());
		assertEquals(true,result.isLimited());
		assertEquals(1,result.size());
		context.setRollbackOnly();	
	}
	
	/**
	 * ResultSetフェチE��取征E
	 */
	
	public void lazySelect(){
		
		setUpData("TEST.xls");
		
		SampleNativeQuery query = createSelect(SampleNativeQuery.class);
		query.setMaxResults(100);
		long count = query.getFetchResult(new CallbackImpl());		
		assertEquals(2L,count);
		context.setRollbackOnly();	
	}
	

	private class CallbackImpl implements QueryCallback<SampleNativeResult> {

		@Override
		public void handleRow(SampleNativeResult oneRecord, long rowIndex) {
			if(rowIndex == 0){
				assertEquals("2",oneRecord.getTest());
			}else {
				assertEquals("1",oneRecord.getTest());
			}
		}

		@Override
		public void terminate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void initialize() {
			// TODO Auto-generated method stub
			
		}

		
	}
	

	/**
	 * test
	 */
	 
	public void updateConstTest(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = createUpsert(SampleNativeUpdate.class);
		update.setTest("1");
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);

		SingleReadQuery<TargetEntity> e = createSingleReader(TargetEntity.class);
		TargetEntity res = e.eq(ITestEntity.TEST, "1").call();
		assertEquals(900,res.getAttr2());
		context.setRollbackOnly();	
		
	}
	

	/**
	 * attr
	 */
	 
	public void updateConstAttr(){
		setUpData("TEST.xls");
		SampleNativeUpdate update = createUpsert(SampleNativeUpdate.class);
		update.setTest("2");
		update.setAttr(CachableConst.TARGET_TEST_1_OK);
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		ListReadQuery<TargetEntity> e = createListReader(TargetEntity.class);
		TargetEntity res = e.eq(ITestEntity.ATTR, CachableConst.TARGET_TEST_1).call().get(0);
		assertEquals(900,res.getAttr2());
		context.setRollbackOnly();	
		
	}
	
	/**
	 * $attr = c_TARGET_CONST_1_OK
	 */
	
	public void updateConstVersionNo(){
	
		setUpData("TEST.xls");
		SingleReadQuery<TargetEntity> eq = createSingleReader(TargetEntity.class);
		eq.eq(ITestEntity.TEST, "1").call().setAttr2(CachableConst.TARGET_INT);				
		
		SampleNativeUpdate update = createUpsert(SampleNativeUpdate.class);
		update.setArc(CachableConst.TARGET_INT);		
		update.setAttr2set(900);
		int count = update.update();
		assertEquals(1,count);
		
		ListReadQuery<TargetEntity> e = createListReader(TargetEntity.class);
		
		//NativeUpdateを実行しても永続化コンチE��スト�E実行されなぁE��従って最初に検索した永続化コンチE��スト�EのエンチE��チE��が�E利用される、E
		//これを防ぎ、NamedUpdateの実行結果を反映したDB値を取得するためにrefleshする、E
		e.setHint(QueryHints.REFRESH, HintValues.TRUE);
		
		TargetEntity res = e.eq(ITestEntity.ATTR, CachableConst.TARGET_TEST_1).call().get(0);

		assertEquals(900,res.getAttr2());
		context.setRollbackOnly();	
	}

}
