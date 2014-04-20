package org.mycard.core;

import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.net.websocket.WebSocketConnector;

public class MoeConnection implements IBaseConnection {
	
	private IBaseThread mUpdateThread;
	private WebSocketConnector mConnector;
	
	public MoeConnection(TaskStatusCallback callback) {
		mConnector = new WebSocketConnector();
		mUpdateThread = new MiscUpdateThread(callback, mConnector);
		mUpdateThread.start();
	}

	@Override
	public void addTask(BaseDataWrapper wrapper) {
		// TODO Auto-generated method stub
		((MoeThread)mUpdateThread).executeTask(wrapper);
	}

	@Override
	public void purge() {
		// TODO Auto-generated method stub
		mUpdateThread.terminate();
	}

}
