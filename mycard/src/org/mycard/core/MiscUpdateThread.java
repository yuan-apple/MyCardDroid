package org.mycard.core;

import org.mycard.core.IBaseConnection.TaskStatusCallback;
import org.mycard.net.websocket.WebSocketConnector;

public class MiscUpdateThread extends MoeThread {

	public MiscUpdateThread(TaskStatusCallback callback,
			WebSocketConnector connector) {
		super(callback, connector);
	}
	
	@Override
	public void run() {
		if (!UpdateController.isServerUpdated) {
			synchronized (sServerLock) {
				try {
					sServerLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		super.run();
	}

}
