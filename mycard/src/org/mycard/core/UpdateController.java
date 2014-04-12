package org.mycard.core;


import org.mycard.StaticApplication;
import org.mycard.core.IBaseConnection.TaskStatusCallback;
import org.mycard.data.DataStore;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.data.wrapper.IBaseWrapper;
import org.mycard.data.wrapper.RoomDataWrapper;
import org.mycard.data.wrapper.ServerDataWrapper;

import android.content.Context;
import android.os.Message;
import android.support.v4.util.SparseArrayCompat;

public class UpdateController implements TaskStatusCallback {
	
	private static final int UPDATE_TYPE_SERVER_LIST = 0;
	
	private static final int UPDATE_TYPE_ROOM_LIST = 1;
	
	private static final int UPDATE_MAX_TYPE = UPDATE_TYPE_ROOM_LIST + 1;
	
	private Context mContext;
	private DataStore mStore;
	
	private SparseArrayCompat<Message> mUpdateMessages;
	
	private IBaseConnection mMoeConnection;
	
	private IBaseConnection mHttpConnection;
	
	public UpdateController(StaticApplication app) {
		mContext = app;
		mStore = new DataStore();
		mUpdateMessages = new SparseArrayCompat<Message>(UPDATE_MAX_TYPE);
		mHttpConnection = new UpdateConnection(app, this);
		mMoeConnection = new MoeConnection(this);
	}
	
	public DataStore getDataStore() {
		return mStore;
	}
	
	
	public void asyncUpdateServer(Message msg) {
		mUpdateMessages.put(UPDATE_TYPE_SERVER_LIST, msg);
		ServerDataWrapper wrapper = new ServerDataWrapper("http");
		mHttpConnection.addTask(wrapper);
	}
	
	public void asyncUpdateRoomList(Message msg) {
		mUpdateMessages.put(UPDATE_TYPE_ROOM_LIST, msg);
		RoomDataWrapper wrapper = new RoomDataWrapper("ws");
		mMoeConnection.addTask(wrapper);
	}
	
	public void stopUpdateRoomList() {
		// TODO Auto-generated method stub
		mMoeConnection.purge();
	}



	@Override
	public void onTaskFinish(BaseDataWrapper wrapper) {
		int key = -1;
		if (wrapper instanceof ServerDataWrapper) {
			key = UPDATE_TYPE_SERVER_LIST;
		} else if (wrapper instanceof RoomDataWrapper) {
			key = UPDATE_TYPE_ROOM_LIST;
		}
		Message msg = mUpdateMessages.get(key);
		if (msg != null) {
			if (wrapper.getResult() == IBaseWrapper.TASK_STATUS_SUCCESS) {
				mStore.updateData(wrapper);
			}
			msg.arg2 = wrapper.getResult();
			msg.sendToTarget();
			mUpdateMessages.remove(key);
		}

	}

	@Override
	public void onTaskContinue(BaseDataWrapper wrapper) {
		int key = -1;
		if (wrapper instanceof ServerDataWrapper) {
			key = UPDATE_TYPE_SERVER_LIST;
		} else if (wrapper instanceof RoomDataWrapper) {
			key = UPDATE_TYPE_ROOM_LIST;
		}
		Message msg = mUpdateMessages.get(key);
		if (msg != null) {
			if (wrapper.getResult() == IBaseWrapper.TASK_STATUS_SUCCESS) {
				mStore.updateData(wrapper);
			}
			Message reply = Message.obtain(msg);
			reply.arg2 = wrapper.getResult();
			reply.sendToTarget();
		}
	}

}
