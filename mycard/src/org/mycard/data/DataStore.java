package org.mycard.data;

import java.util.ArrayList;
import java.util.List;

import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.data.wrapper.RoomDataWrapper;
import org.mycard.data.wrapper.ServerDataWrapper;

public class DataStore {
	private List<ServerInfo> mServers;
	private List<RoomInfo> mRooms;

	public DataStore() {
		// TODO Auto-generated constructor stub
		mServers = new ArrayList<ServerInfo>();
		mRooms = new ArrayList<RoomInfo>();
	}

	public synchronized void updateData(BaseDataWrapper wrapper) {
		if (wrapper instanceof ServerDataWrapper) {
			mServers.clear();
			int size = ((ServerDataWrapper) wrapper).size();
			for (int i = 0; i < size; i++) {
				mServers.add(i,
						(ServerInfo) ((ServerDataWrapper) wrapper).getItem(i));
			}
		} else if (wrapper instanceof RoomDataWrapper) {
			mRooms.clear();
			int size = ((RoomDataWrapper) wrapper).size();
			for (int i = 0; i < size; i++) {
				mRooms.add(i, (RoomInfo) ((RoomDataWrapper) wrapper).getItem(i));
			}
		}
	}

	public synchronized List<ServerInfo> getServerList() {
		List<ServerInfo> servers = new ArrayList<ServerInfo>();
		for (ServerInfo info : mServers) {
			servers.add(info.clone());
		}
		return servers;
	}
	
	public synchronized List<RoomInfo> getRoomList() {
		List<RoomInfo> rooms = new ArrayList<RoomInfo>();
		for (RoomInfo info : mRooms) {
			rooms.add(info.clone());
		}
		return rooms;
	}

}
