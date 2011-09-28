/**
 * Copyright 2011 the original author
 */
package framework.api.dto;

import java.io.Serializable;

/**
 * クラインと層からのリクエスト情報.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ClientRequestBean implements Serializable,Cloneable{

	private static final long serialVersionUID = -5810188274130263782L;

	/** リクエストID */
	private String requestId = null;
	
	/**
	 * @return requestId
	 */
	public String getRequestId(){
		return requestId;
	}
	
	/**
	 * @param requestId the requestId to sset
	 */
	public void setRequestId(String requestId){
		this.requestId = requestId;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public ClientRequestBean clone(){
		try {
			return (ClientRequestBean)(super.clone());
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
}
