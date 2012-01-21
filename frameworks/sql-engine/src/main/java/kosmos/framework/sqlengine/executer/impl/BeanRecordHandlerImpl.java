/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer.impl;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import kosmos.framework.sqlengine.exception.SQLEngineException;
import kosmos.framework.sqlengine.executer.RecordHandler;
import kosmos.framework.sqlengine.executer.TypeConverter;


/**
 * Gets the one record from ResultSet for JavaBean.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class BeanRecordHandlerImpl implements RecordHandler {

	/** the type*/
	private final Class<?> type;
	
	/** the cache of the setter methods */
	private final Map<String,Method> methodMap;
	
	/** the names of the column. */
	private final String[] labels;
	
	/** the names of the column. */
	private final String[] javaLabels;
	
	/** the engine. */
	private TypeConverter converter;
	
	/**
	 * @param resultType the resultType 
	 * @param labels the labels
	 * @param methodMap the setter methods
	 * @param converter the converter
	 */
	public BeanRecordHandlerImpl(Class<?> type, String[] labels,String[] javaLabels , Map<String,Method> methodMap,TypeConverter converter){
		this.type = type;
		this.methodMap = methodMap;
		this.labels = labels;
		this.javaLabels = javaLabels;
		this.converter = converter;
	}
	
	/**
	 * @see kosmos.framework.sqlengine.executer.RecordHandler#getRecord(java.sql.ResultSet)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getRecord(ResultSet resultSet) throws SQLException {
		T row = null;
		try{
			row = (T)type.newInstance();
		}catch(Exception e){
			throw new SQLEngineException(e);
		}
		
		//データ取得
		int size = labels.length;
		for (int i = 0; i < size; i++) {

			String columnLabel = labels[i];
			String columnJavaLabel = javaLabels[i];
			String name = String.format("set%s",columnJavaLabel);	
			if(methodMap.containsKey(name)){
				Method m = methodMap.get(name);
				
				try {
					Class<?> type = m.getParameterTypes()[0];
					m.invoke(row,converter.getParameter(type,resultSet,columnLabel));					
				} catch(SQLException sqle){
					throw sqle;
				} catch (Exception e) {
					throw new SQLEngineException(e);
				}
			}
		}
		
		return row;
	}


}
