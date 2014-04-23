package org.mycard.core;


import org.mycard.Constants;
import org.mycard.StaticApplication;
import org.mycard.core.IBaseConnection.TaskStatusCallback;
import org.mycard.data.Model;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.data.wrapper.IBaseWrapper;
import org.mycard.data.wrapper.LoginDataWrapper;
import org.mycard.data.wrapper.RoomDataWrapper;
import org.mycard.data.wrapper.ServerDataWrapper;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.util.SparseArrayCompat;

public class UpdateController implements TaskStatusCallback {
	
	public static boolean isServerUpdated; 
	
	private static final int UPDATE_TYPE_SERVER_LIST = 0x0;
	
	private static final int UPDATE_TYPE_ROOM_LIST = 0x1;
	
	private static final int UPDATE_TYPE_LOGIN = 0x2;
	
	private static final int UPDATE_MAX_TYPE = UPDATE_TYPE_LOGIN + 1;
	
	private Context mContext;
	
	private Model mModel;
	
	private SparseArrayCompat<Message> mUpdateMessages;
	
	private IBaseConnection mMoeConnection;
	
	private IBaseConnection mInstantConnection;
	
	public UpdateController(StaticApplication app) {
		mContext = app;
		mModel = Model.peekInstance();
		mUpdateMessages = new SparseArrayCompat<Message>(UPDATE_MAX_TYPE);
		mInstantConnection = new InstantConnection(app, this);
		mMoeConnection = new WebSocketConnection(this);
	}
	
	/* package */ void asyncUpdateServer(Message msg) {
		mUpdateMessages.put(UPDATE_TYPE_SERVER_LIST, msg);
		ServerDataWrapper wrapper = new ServerDataWrapper(Constants.REQUEST_TYPE_UPDATE_SERVER);
		mInstantConnection.addTask(wrapper);
	}
	
	/* package */ void asyncUpdateRoomList(Message msg) {
		mUpdateMessages.put(UPDATE_TYPE_ROOM_LIST, msg);
		RoomDataWrapper wrapper = new RoomDataWrapper(Constants.REQUEST_TYPE_UPDATE_ROOM);
		mMoeConnection.addTask(wrapper);
	}
	
	/* package */ void asyncLogin(Message msg) {
		mUpdateMessages.put(UPDATE_TYPE_LOGIN, msg);
		BaseDataWrapper wrapper = new LoginDataWrapper(Constants.REQUEST_TYPE_LOGIN);
		wrapper.setParam((Bundle) msg.obj);
		mInstantConnection.addTask(wrapper);
	}
	
	/* package */ void stopUpdateRoomList() {
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
		} else if (wrapper instanceof LoginDataWrapper) {
			key = UPDATE_TYPE_LOGIN;
		}
		Message msg = mUpdateMessages.get(key);
		if (msg != null) {
			if (wrapper.getResult() == IBaseWrapper.TASK_STATUS_SUCCESS) {
				mModel.updateData(wrapper);
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
				mModel.updateData(wrapper);
			}
			Message reply = Message.obtain(msg);
			reply.arg2 = wrapper.getResult();
			reply.sendToTarget();
		}
	}

}
