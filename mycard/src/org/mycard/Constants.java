package org.mycard;

/**
 * @author mabin
 *
 */
public interface Constants {

	public static final int IO_BUFFER_SIZE = 8192;
	public static final int TRANSACT_TIMEOUT = 2 * 60 * 1000;
	
	public static final int MSG_UPDATE_ROOM_LIST = 0;
	public static final int MSG_ID_UPDATE_SERVER = 1;
	
	
	public static final int ACTION_BAR_CHANGE_TYPE_PAGE_CHANGE = 0x1000;
	public static final int ACTION_BAR_CHANGE_TYPE_DATA_LOADING = 0x1001;
	
	public static final int ACTION_BAR_EVENT_TYPE_NEW = 0x2000;
	public static final int ACTION_BAR_EVENT_TYPE_SETTINGS = 0x2001;
	public static final int ACTION_BAR_EVENT_TYPE_SEARCH = 0x2002;
	public static final int ACTION_BAR_EVENT_TYPE_PLAY = 0x2003;
	
	
	public static final int REQUEST_TYPE_UPDATE_SERVER = 0x3000;
	public static final int REQUEST_TYPE_UPDATE_ROOM = 0x3001;

}
