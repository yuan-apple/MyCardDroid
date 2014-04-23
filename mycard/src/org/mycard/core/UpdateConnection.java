package org.mycard.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.mycard.StaticApplication;
import org.mycard.data.wrapper.BaseDataWrapper;

public class UpdateConnection implements IBaseConnection{
	

	protected BlockingQueue<BaseDataWrapper> mTaskQueue;
	
	protected IBaseThread mUpdateThread;
	
	
	public UpdateConnection(StaticApplication app, TaskStatusCallback callback) {
		mTaskQueue = new LinkedBlockingQueue<BaseDataWrapper>();
		initThread(app, callback);
	}

	protected void initThread(StaticApplication app, TaskStatusCallback callback) {
		mUpdateThread = new SingleUpdateThreadPool(mTaskQueue, callback, app.getHttpClient());
		mUpdateThread.start();
	}
	
	@Override
	public void addTask(BaseDataWrapper wrapper) {
		try {
			mTaskQueue.put(wrapper);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void purge() {
		mUpdateThread.terminate();
		mTaskQueue.clear();
	}

}
