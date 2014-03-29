package org.mycard.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.client.HttpClient;
import org.mycard.data.wrapper.BaseDataWrapper;

public class UpdateConnection {
	
	public interface TaskFinishCallback {
		void onTaskFinish(BaseDataWrapper wrapper);
	}

	private HttpClient mClient;
	
	private BlockingQueue<BaseDataWrapper> mTaskQueue;
	
	private UpdateThread mThread;
	
	
	public UpdateConnection(HttpClient client, TaskFinishCallback callback) {
		// TODO Auto-generated constructor stub
		mClient = client;
		mTaskQueue = new LinkedBlockingQueue<BaseDataWrapper>();
		mThread = new UpdateThread(mTaskQueue, callback, mClient);
		mThread.start();
	}
	
	public void addTask(BaseDataWrapper wrapper) {
		try {
			mTaskQueue.put(wrapper);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void purge() {
		mThread.terminate();
		mTaskQueue.clear();
	}

}
