package org.mycard.data;

/**
 * @author mabin
 *
 */
public interface ResourcesConstants {
	
	public static final String FORUM_URL = "https://forum.my-card.in/";
	
	
	public static final String SERVER_LIST_URL = "mycard-in/servers.json";
	public static final String ROOM_LIST_URL = "mycard-in/rooms.json";
	
	public static final String JSON_KEY_ID = "id";
	public static final String JSON_KEY_NAME = "name";
	
	/**
	 * For server info.
	 */
	public static final String JSON_KEY_SERVER_IP_ADDR = "ip";
	public static final String JSON_KEY_SERVER_PORT = "port";
	public static final String JSON_KEY_SERVER_AUTH = "auth";
	public static final String JSON_KEY_SERVER_INDEX_URL = "index";
	public static final String JSON_KEY_SERVER_MAX_ROOMS = "max_rooms";
	public static final String JSON_KEY_SERVER_TYPE = "server_type";
	
	/**
	 * For room info
	 */
	public static final String JSON_KEY_ROOM_STATUS = "status";
	public static final String JSON_KEY_ROOM_SERVER_ID = "server_id";
	public static final String JSON_KEY_ROOM_MODE = "mode";
	public static final String JSON_KEY_ROOM_USERS = "users";
	
	//Optional
	public static final String JSON_KEY_ROOM_PRIVACY = "private";
	public static final String JSON_KEY_ROOM_RULE = "rule";
	public static final String JSON_KEY_ROOM_START_LP = "start_lp";
	public static final String JSON_KEY_ROOM_START_HAND = "start_hand";
	public static final String JSON_KEY_ROOM_DRAW_COUNT = "draw_count";
	
	/**
	 * For user info
	 */
	public static final String JSON_KEY_USER_CERTIFIED = "certified";
	public static final String JSON_KEY_USER_PLAYER_ID = "player";
	
	
	public static final int GAME_MODE_SINGLE = 0;
	public static final int GAME_MODE_MATCH = 1;
	public static final int GAME_MODE_TAG = 2;
	
	
	public static final String GAME_STATUS_START = "start";
	public static final String GAME_STATUS_WAIT = "wait";
	
	public static final int GAME_RULE_OCG_ONLY = 0;
	public static final int GAME_RULE_TCG_ONLY = 1;
	public static final int GAME_RULE_OCG_TCG = 2;

}
