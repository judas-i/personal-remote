/**
 * 
 */
package org.coder.mightyguard.register.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.resource.cci.Connection;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.coder.gear.query.QueryFactoryFinder;
import org.coder.gear.query.criteria.query.ListReadQuery;
import org.coder.mightyguard.register.domain.application.AppVersion;
import org.eclipse.persistence.internal.nosql.adapters.mongo.MongoConnection;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @author yoshida-n
 *
 */
@Path("/version")
public class ReferenceService {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("mightyguard");
	
	private static final QueryFactoryFinder finder = new QueryFactoryFinder();
	
	/**
	 * Gets the version list
	 * @param version
	 * @return
	 */
	@Path("list")
	@GET
	@Produces("application/json")
	public Set<String> previousList(@QueryParam("current") String version,@QueryParam("limit") @DefaultValue("10") int limit){
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();		
		MongoConnection mc = MongoConnection.class.cast(em.unwrap(Connection.class));
		DB db = mc.getDB();
		DBCollection cl = db.getCollection(AppVersion.class.getSimpleName().toUpperCase());
		
		//検索条件
		Object matchOption = dbo("DATE",dbo("$lt",getDate(version,cl)));
		DBObject match = dbo("$match", matchOption);
		
		//プロジェクト
		DBObject projectOption = dbo("VERSION",1);
		projectOption.put("DATE", 1);
		DBObject project = dbo("$project", projectOption);
		
		//グループ
		Object groupOption = dbo("_id","$VERSION");
		DBObject group = dbo("$group",groupOption);		
		
		CommandResult result = cl.aggregate(match,project,group).getCommandResult();
		BasicDBList list = BasicDBList.class.cast(result.get("result"));
		
		TreeSet<String> set = new TreeSet<String>();
		for(Object e : list){
			BasicDBObject o = BasicDBObject.class.cast(e);
			String ver= (String)o.get("_id");
			set.add(ver);
		}
		return set.descendingSet();
	}
	
	/**
	 * 日付取得
	 * @param version
	 * @param cl
	 * @return
	 */
	private String getDate(String version,DBCollection cl){
			Object dateMatchObject = dbo("VERSION",version);
			DBObject dateMatch = dbo("$match", dateMatchObject);		
			DBObject dateProjectObject = dbo("DATE", 1);
			dateProjectObject.put("VERSION", 1);
			DBObject dateProject = dbo("$project", dateProjectObject);
			Object dateGroupObject = dbo("_id",dbo("DATE","$DATE"));		
			DBObject dateGroup = dbo("$group",dateGroupObject);
			BasicDBList dateResult = (BasicDBList)cl.aggregate(dateMatch, dateProject,dateGroup).getCommandResult().get("result");
			DBObject object = (DBObject)dateResult.get(0);
			DBObject resultId = (DBObject)object.get("_id");
			String lastDate = (String)resultId.get("DATE");
			return lastDate;
	}

	/**
	 * @param version
	 * @param previousVersion
	 * @return
	 */
	@Path("diff")
	@GET
	@Produces("application/json")
	public List<AppVersion> diff(@QueryParam("current") String version,@QueryParam("previous") String previousVersion){
		EntityManager em = factory.createEntityManager();
		return doDiff(version,previousVersion,em);		
	}
	
	/**
	 * @param version
	 * @param previousVersion
	 * @return
	 */
	@Path("show")
	@GET
	@Produces("application/json")
	public List<AppVersion> show(@QueryParam("current") String version){
		EntityManager em = factory.createEntityManager();		
		ListReadQuery<AppVersion> currentQuery = finder.createCriteriaQueryFactory().createListReadQuery(AppVersion.class, em);
		List<AppVersion> cList = currentQuery.lt("version", version).call();
		
		TreeMap<String, String> map = new TreeMap<String,String>();
		for(AppVersion e : cList){
			map.put(e.date,e.version);
		}		
		String prevVer = map.descendingMap().values().iterator().next();
		return doDiff(version,prevVer,em);		
	}
	
	
	/**
	 * @param version
	 * @param previous
	 * @param em
	 * @return
	 */
	private List<AppVersion> doDiff(String version , String previous , EntityManager em){
				
		ListReadQuery<AppVersion> currentQuery = finder.createCriteriaQueryFactory().createListReadQuery(AppVersion.class, em);
		List<AppVersion> cList = currentQuery.eq("version", version).call();
		
		ListReadQuery<AppVersion> previousQuery = finder.createCriteriaQueryFactory().createListReadQuery(AppVersion.class, em);
		List<AppVersion> pList = previousQuery.eq("version", previous).call();
		
		//前回バージョンのリビジョン設定
    	Map<String,String> moduleMaxRevs = new HashMap<String,String>();
    	for(AppVersion e : pList){
    		moduleMaxRevs.put(e.moduleId, e.info.entry.commit.revision);
    	}
    	
    	//過去リビジョンのカット
    	for(AppVersion e : cList){
    		e.log.removeUnderRevision(Integer.parseInt(moduleMaxRevs.get(e.moduleId)));    	
    	}
    	return cList;		
	}
	
	/**
	 * @param key
	 * @param value
	 * @return
	 */
	private BasicDBObject dbo(String key, Object value){
		return new BasicDBObject(key, value);
	}
}
