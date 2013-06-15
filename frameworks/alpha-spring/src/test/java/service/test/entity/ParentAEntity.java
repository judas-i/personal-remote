/**
 * Copyright 2011 the original author
 */
package service.test.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Entity
@Table(name="PARENT_A")
public class ParentAEntity{
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private String test;

	@Column
	private String attr;
	
	@Column
	private int attr2;
	
	@Version
	@Column
	private int version;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="parent_test",referencedColumnName="test",insertable=false,updatable=false)
	private List<ChildAEntity> childs;
	
	/**
	 * @return
	 */
    public List<ChildAEntity> getChilds(){
		return childs;
	}
	
	/**
	 * @param childs
	 */
	public void setChilds(List<ChildAEntity> childs){
		this.childs = childs;
	}
	
	/**
	 * @param test the test to set
	 */
	public ParentAEntity setTest(String test) {
		this.test = test;
		return this;
	}

	/**
	 * @return the test
	 */
	@Id
	@Column
	public String getTest() {
		return test;
	}

	/**
	 * @param attr the attr to set
	 */
	public ParentAEntity setAttr(String attr) {
		this.attr = attr;
		return this;
	}

	/**
	 * @return the atstr
	 */
	@Column
	public String getAttr() {
		return attr;
	}

	/**
	 * @param attr2 the attr2 to set
	 */
	public ParentAEntity setAttr2(int attr2) {
		this.attr2 = attr2;
		return this;
	}

	/**
	 * @return the attr2
	 */
	@Column
	public int getAttr2() {
		return attr2;
	}

	/**
	 * @param version the version to set
	 */
	public ParentAEntity setVersion(int version) {
		this.version = version;
		return this;
	}

	/**
	 * @return the version
	 */
	@Version
	@Column
	public int getVersion() {
		return version;
	}
}
