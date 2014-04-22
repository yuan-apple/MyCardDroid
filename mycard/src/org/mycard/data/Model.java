package org.mycard.data;

import java.util.List;

import org.mycard.StaticApplication;
import org.mycard.data.wrapper.BaseDataWrapper;

public class Model {
	
	private static Model INSTANCE;
	
	private DataStore mDataStore;
	
	private Model(StaticApplication app) {
		mDataStore = new DataStore();
	}

	public static Model peekInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Model(StaticApplication.peekInstance());
		}
		return INSTANCE;
		
	}

	public void updateData(BaseDataWrapper wrapper) {
		mDataStore.updateData(wrapper);
	}

	public List<ServerInfo> getServerList() {
		return mDataStore.getServerList();
	}

	public List<RoomInfo> getRooms() {
		return mDataStore.getRooms();
	}

}
