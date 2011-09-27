/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer.impl;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Map;

import framework.sqlengine.exception.SQLEngineException;
import framework.sqlengine.executer.RecordHandler;
import framework.sqlengine.executer.TypeConverter;

/**
 * ResultSetの1行をBeanに設定する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class BeanRecordHandlerImpl<T> implements RecordHandler<T> {

	/** 結果型.*/
	private final Class<T> type;
	
	/** メソッドマップ. */
	private final Map<String,Method> methodMap;
	
	/** カラム名. */
	private final String[] labels;
	
	/** 型変換エンジン. */
	private TypeConverter converter;
	
	/**
	 * @param type 結果型
	 * @param labels カラム名
	 * @param methodMap メソッドマップ
	 * @param converter 型変換エンジン
	 */
	public BeanRecordHandlerImpl(Class<T> type, String[] labels, Map<String,Method> methodMap,TypeConverter converter){
		this.type = type;
		this.methodMap = methodMap;
		this.labels = labels;
		this.converter = converter;
	}
	
	/**
	 * @see framework.sqlengine.executer.RecordHandler#getRecord(java.sql.ResultSet)
	 */
	@Override
	public T getRecord(ResultSet resultSet) {
		T row = null;
		try{
			row = type.newInstance();
		}catch(Exception e){
			throw new SQLEngineException(e);
		}
		
		//データ取得
		int size = labels.length;
		for (int i = 0; i < size; i++) {

			String columnLabel = labels[i];
			String name = String.format("set%s",columnLabel);	
			if(methodMap.containsKey(name)){
				Method m = methodMap.get(name);
				
				try {
					Class<?> type = m.getParameterTypes()[0];
					m.invoke(row,converter.getParameter(type,resultSet,columnLabel));					
				} catch (Exception e) {
					throw new SQLEngineException(e);
				}
			}
		}
		
		return row;
	}


}
