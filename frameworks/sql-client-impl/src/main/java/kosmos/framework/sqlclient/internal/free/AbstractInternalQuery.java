package kosmos.framework.sqlclient.internal.free;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The internal query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractInternalQuery implements InternalQuery{
	
	/** the pattern */
	protected static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-z][a-zA-Z0-9_]*)");
	
	/** the parameter for <code>PreparedStatement</code> */
	protected Map<String,Object> param = null;
	
	/** the parameter for analyze the template */
	protected Map<String,Object> branchParam = null;
	
	/** the max size */
	protected int maxSize = 0;
	
	/** the start position */
	protected int firstResult = 0;
	
	/** the queryId */
	protected final String queryId;
	
	/** the SQL */
	protected final String sql;
	
	/** the executing SQL */
	protected String firingSql = null;

	/** if true dont analyze the template*/
	protected final boolean useRowSql;
	
	/** the JPA hint */
	protected Map<String,Object> hints = new HashMap<String,Object>();
	
	
	/**
	 * @param useRowSql　the useRowSql to set
	 * @param sql the SQL to set
	 * @param queryId the queryId to set
	 */
	public AbstractInternalQuery(boolean useRowSql,String sql, String queryId){		
		this.queryId = queryId;
		this.useRowSql = useRowSql;
		this.param = new HashMap<String,Object>();
		this.branchParam = new HashMap<String,Object>();		
		this.sql = sql;		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setBranchParameter(String arg0 , Object arg1){
		this.branchParam.put(arg0,arg1);
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getFirstResult()
	 */
	@Override
	public int getFirstResult() {
		return this.firstResult;
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getMaxResults()
	 */
	@Override
	public int getMaxResults() {
		return maxSize;
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#setFirstResult(int)
	 */
	@Override
	public void setFirstResult(int arg0) {
		this.firstResult = arg0;
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#setMaxResults(int)
	 */
	@Override
	public void setMaxResults(int arg0) {
		maxSize = arg0;
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setParameter(String arg0, Object arg1) {
		param.put(arg0, arg1);
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#getHints()
	 */
	@Override
	public Map<String,Object> getHints() {
		return hints;
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.free.InternalQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setHint(String key , Object value) {
		this.hints.put(key, value);
	}


}
