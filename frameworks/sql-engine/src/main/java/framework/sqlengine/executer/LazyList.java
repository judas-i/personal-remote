/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import framework.sqlengine.exception.SQLEngineException;

/**
 * ResultSetをフェッチして取得するためのリスト.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class LazyList<E> implements List<E>{

	private static final long serialVersionUID = 1L;

	/** 検索結果 */
	private final ResultSet rs;

	/** 実行済みステートメント */
	private final Statement statement;

	/** 検索解析処理 */
	private final RecordHandler<E> handler;

	/** 結果 */
	private final ResultSetIterator itr = new ResultSetIterator();
	
	/** サイズ */
	private int size = 0;
	
	/** 検索可能最大件数 */
	private int maxSize = 0;
	
	/** 一回でも実行されたか否か */
	private boolean firstExecuted = false;
	
	/** 取得した1行 */
	private E created = null;
	

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize(){
		close();
	}
	
	/**
	 * @param statement ステートメント
	 * @param rs 結果
	 * @param maxSize 最大件数
	 * @param handler ハンドラ
	 */
	public LazyList(Statement statement ,ResultSet rs, int maxSize , RecordHandler<E> handler){
		this.rs = rs;
		this.statement = statement;
		this.maxSize = maxSize;
		this.handler = handler;
	}
	
	/**
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {
		if(isClosed()){
			return size;
		}
		throw new UnsupportedOperationException("cursor must be closed to invoke this method");	
	}

	/**
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		if(isClosed()){
			return size == 0;
		}else{
			if(firstExecuted){
				return size == 0; 
			}else{
				throw new UnsupportedOperationException("cursor must be closed to invoke this method");	
			}
		}
	}

	/**
	 * @see java.util.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return itr;
	}

	/**
	 * @see java.util.List#toArray()
	 */
	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#remove(int)
	 */
	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#subList(int, int)
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.List#get(int)
	 */
	@Override
	public E get(int index) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @return ResultSetクローズ有無
	 */
	private boolean isClosed(){
		try{
			return rs.isClosed();
		}catch(Exception sqle){
			close();
			throw new SQLEngineException(sqle);
		}
	}

	/**
	 * クローズ
	 */
	private void close(){

		try{
			if(rs != null && !rs.isClosed()){
				rs.close();
			}
		} catch (SQLException e) {
			throw new SQLEngineException(e);
		}finally{
			try{
				if( statement != null && !statement.isClosed()){
					statement.close();
				}
			}catch(SQLException ee){
				throw new SQLEngineException(ee);
			}
		}
	}
	
	/**
	 * イテレータ.
	 *
	 * @author yoshida-n
	 * @version 2011/08/31 created.
	 */
	private class ResultSetIterator implements Iterator<E>{

		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			if(size > maxSize && maxSize > 0){
				close();
				return false;
			}
			try{
				boolean hasNext = rs.next();
				if(hasNext){
					firstExecuted = true;
					size++;
					created = handler.getRecord(rs);		
				}else{
					close();
				}
				return hasNext;
			}catch(Exception sqle){
				close();
				throw new SQLEngineException(sqle);
			}catch(Error e){
				close();
				throw e;
			}
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
		public E next() {
			return created;
		}

		/**
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();			
		}
		
	}
}
