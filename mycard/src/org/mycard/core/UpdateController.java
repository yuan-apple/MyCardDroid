package org.mycard.core;


import org.mycard.StaticApplication;
import org.mycard.core.UpdateConnection.TaskFinishCallback;
import org.mycard.data.DataStore;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.data.wrapper.RoomDataWrapper;
import org.mycard.data.wrapper.ServerDataWrapper;

import android.content.Context;
import android.os.Message;
import android.support.v4.util.SparseArrayCompat;

public class UpdateController implements TaskFinishCallback {
	
	private static final int UPDATE_TYPE_SERVER_LIST = 0;
	
	private static final int UPDATE_TYPE_ROOM_LIST = 1;
	
	private static final int UPDATE_MAX_TYPE = UPDATE_TYPE_ROOM_LIST + 1;
	
	private Context mContext;
	private DataStore mStore;
	
	private SparseArrayCompat<Message> mUpdateMessages;
	
	private UpdateConnection mConnection;
	
	public UpdateController(StaticApplication app) {
		mContext = app;
		mStore = new DataStore();
		mUpdateMessages = new SparseArrayCompat<Message>(UPDATE_MAX_TYPE);
		mConnection = new UpdateConnection(app.getHttpClient(), this);
	}
	
	public DataStore getDataStore() {
		return mStore;
	}
	
	
	public void asyncUpdateServer(Message msg) {
		mUpdateMessages.put(UPDATE_TYPE_SERVER_LIST, msg);
		ServerDataWrapper wrapper = new ServerDataWrapper();
		mConnection.addTask(wrapper);
	}
	
	public void asyncUpdateRoomList(Message msg) {
		mUpdateMessages.put(UPDATE_TYPE_ROOM_LIST, msg);
		RoomDataWrapper wrapper = new RoomDataWrapper();
		mConnection.addTask(wrapper);
	}


	@Override
	public void onTaskFinish(BaseDataWrapper wrapper) {
		// TODO Auto-generated method stub
		mStore.updateData(wrapper);
		int key = -1;
		if (wrapper instanceof ServerDataWrapper) {
			key = UPDATE_TYPE_SERVER_LIST;
		} else if (wrapper instanceof RoomDataWrapper) {
			key = UPDATE_TYPE_ROOM_LIST;
		}
		Message msg = mUpdateMessages.get(key);
		if (msg != null) {
			msg.sendToTarget();
			mUpdateMessages.remove(key);
		}
	}

}
