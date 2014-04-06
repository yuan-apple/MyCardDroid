package org.mycard.core;


import org.mycard.core.IBaseConnection.TaskStatusCallback;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.net.websocket.WebSocketConnector;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class MoeThread extends HandlerThread implements IBaseThread, Handler.Callback{
	
	public static final int MSG_ID_DATA_UPDATE = 0;
	public static final int MSG_ID_CONNECTION_CLOSED = 1;
	
	
	public static class MoeEventHandler extends Handler {
		public MoeEventHandler(Looper lopper, Callback callback) {
			// TODO Auto-generated constructor stub
			super(lopper, callback);
		}

	}

	private static final String TAG = "MoeThread";
	
	private TaskStatusCallback mCallback;
	
	private BaseDataWrapper mWrapper;
	
	private volatile boolean isRunning = true;
	
	private WebSocketConnector mConnector;

	private MoeEventHandler mHandler;
	
	public MoeThread(TaskStatusCallback callback, WebSocketConnector connector) {
		super(TAG);
		mCallback = callback;
		mConnector = connector;
	}
	
	@Override
	protected void onLooperPrepared() {
		// TODO Auto-generated method stub
		super.onLooperPrepared();
		mHandler = new MoeEventHandler(getLooper(), this);
		mConnector.setHandler(mHandler);
	}
	
	public void executeTask(BaseDataWrapper wrapper) {
		mWrapper = wrapper;
		mConnector.connect(wrapper);
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_ID_DATA_UPDATE:
			mCallback.onTaskContinue(mWrapper);
			break;
		case MSG_ID_CONNECTION_CLOSED:
			mWrapper.setResult(false);
			mCallback.onTaskFinish(mWrapper);
			quit();
		default:
			break;
		}
		return false;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	public void terminate() {
		mConnector.terminate();
		isRunning = false;
	}
}
