/**
 * 
 */
package org.coder.gear.sample.sastruts.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * @author yoshida-n
 *
 */
@Entity
public class Stock {
	
	//データアクセスロジックを持たせてActiveRecordにするのもあり
	//ただし、別途Factoryは作らないといけないので、シンプルな場合にはDomainModel + DataMapper(JPA)が楽
	
	@Id
	public Long itemNo;
	
	public Long reserved;
	
	public Long reservable;

	@Version
	public Long version;
	
	/**
	 * @param count
	 * @return
	 */
	public boolean canReserve(Long count) {
		return reservable > count;
	}
	
	/**
	 * @param count
	 */
	public void reserve(Long count){
		reserved += count;
		reservable -= count;
	}
	
	/**
	 * @param count
	 */
	public void cancel(Long count){
		reserved -= count;
		reservable += count;
	}
	
	/**
	 * @param count
	 */
	public void deliver(Long count){
		reserved -= count;
	}
}