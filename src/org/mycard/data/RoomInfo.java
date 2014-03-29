package org.mycard.data;

import java.util.ArrayList;

public class RoomInfo extends BaseInfo {
	
	public String name;
	public boolean status;
	public int serverId;
	
	public ArrayList<UserInfo> mUsers;
	public int mode;
	public int rule;
	public boolean privacy;
	public int startLp;
	
	@Override
	protected RoomInfo clone() {
		// TODO Auto-generated method stub
		return (RoomInfo)super.clone();
	}

}
