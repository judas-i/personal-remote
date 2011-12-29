/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.message;

/**
 * Framework Messages.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface Messages {
	
	/** the message level */
	public static enum Level {
		I,W,E,F
	}

	/** データ未存在 */
	public static final String MSG_SYS_UNEXPECTED_NO_DATA_FOUND 	= "MSG_SYS_001";
	
	/** 不正データ存在 */
	public static final String MSG_SYS_UNEXPECTED_DATA_FOUND 		= "MSG_SYS_002";
	
	/** 不正複数件数データ存在 */
	public static final String MSG_SYS_UNEXPECTED_MULTI_DATA_FOUND 	= "MSG_SYS_003";
	
}