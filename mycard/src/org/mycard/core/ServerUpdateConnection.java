package org.mycard.core;

import org.mycard.StaticApplication;
import org.mycard.data.wrapper.BaseDataWrapper;

public class ServerUpdateConnection implements IBaseConnection {
	protected IBaseThread mUpdateThread;

	public ServerUpdateConnection(StaticApplication app,
			TaskStatusCallback callback) {
		mUpdateThread = new ServerUpdateThread(callback, app.getHttpClient());
	}
	

	@Override
	public void addTask(BaseDataWrapper wrapper) {
		((ServerUpdateThread)mUpdateThread).setWrapper(wrapper);
	}

	@Override
	public void purge() {
		mUpdateThread.terminate();
	}

}
