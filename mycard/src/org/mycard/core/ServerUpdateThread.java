package org.mycard.core;

import org.apache.http.client.HttpClient;
import org.mycard.core.IBaseConnection.TaskStatusCallback;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.net.http.BaseHttpConnector;
import org.mycard.net.http.UpdateHttpConnector;

public class ServerUpdateThread extends BaseThread {
	
	private BaseHttpConnector mConnector;
	
	private BaseDataWrapper mWrapper;

	public ServerUpdateThread(TaskStatusCallback callback, HttpClient client) {
		super(callback);
		mConnector = new UpdateHttpConnector(client);
	}
	
	/*package */ void setWrapper(BaseDataWrapper wrapper) {
		mWrapper = wrapper;
		//TODO: to be fixed
		if (!isAlive()) {
			this.start();
		}
	}
	
	@Override
	public void run() {
			try {
				if (mWrapper != null) {
					mConnector.get(mWrapper);
					mCallback.onTaskFinish(mWrapper);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (!UpdateController.isServerUpdated) {
			synchronized (sServerLock) {
				sServerLock.notifyAll();
				UpdateController.isServerUpdated = true;
			}
		}
	}

}
