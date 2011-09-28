/**
 * Copyright 2011 the original author
 */
package framework.api.query.orm;

import javax.persistence.LockModeType;

import framework.sqlclient.api.orm.OrmCondition;


/**
 *　ORMクエリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface AdvancedOrmQuery<T> {
	
	/**
	 * LockModeTypeを指定する.
	 * @param lockModeType ロックモード
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setPessimisticReadNoWait();
	
	/**
	 * @param <T> 型
	 * @param key　 ヒント句キー
	 * @param value　ヒント句
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setHint(String key, Object value);

	/**
	 * LockModeTypeを指定する.
	 * @param lockModeType ロックモード
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setLockMode(LockModeType lockModeType);

	/**
	 * @param condition 条件
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setCondition(OrmCondition<T> condition);
	
	/**
	 * 主キー検索
	 * @param pks　主キー
	 * @return 検索結果
	 */
	public T find(Object... pks);

	/**
	 * 主キー指定存在チェック
	 * @param pks 主キー
	 * @return true:存在する
	 */
	public boolean exists(Object... pks);

	/**
	 * @return 0件時システムエラー
	 */
	public <Q extends AdvancedOrmQuery<T>> Q enableNoDataError();

	/**
	 * @param arg0 最大件数
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setMaxResults(int arg0);

	/**
	 * @param arg0 先頭位置
	 * @return self
	 */
	public <Q extends AdvancedOrmQuery<T>> Q setFirstResult(int arg0);

}
