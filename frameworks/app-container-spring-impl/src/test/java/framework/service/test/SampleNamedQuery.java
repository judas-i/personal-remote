/**
 * Use is subject to license terms.
 */
package framework.service.test;

import framework.service.ext.query.EclipseLinkNamedQuery;
import framework.sqlclient.api.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/04/24 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_QUERY.sql")
public class SampleNamedQuery extends EclipseLinkNamedQuery{

	public enum Bind {
		test,
		attr,
	}
	public enum Branch {
		test,
		attr,
		attr2,
		arc
	}
	
	public Enum<?>[] getParameterNames() {
		return Bind.values();
	}
	
	public Enum<?>[] getBranchParameterNames() {
		return Branch.values();
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQuery setTest(String value){
		setBranchParameter(Branch.test.name(), value);
		setParameter(Bind.test.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQuery setAttr(String value){
		setParameter(Bind.attr.name(), value);
		setBranchParameter(Branch.attr.name(), value);
		return this;
	}


	/**
	 * @param value
	 */
	public SampleNamedQuery setAttr2(Integer value){
		setBranchParameter(Branch.attr2.name(), value);
		return this;
	}
	
	/**
	 * @param value
	 */
	public SampleNamedQuery setArc(String value){
		setBranchParameter(Branch.arc.name(), value);
		return this;
	}

}