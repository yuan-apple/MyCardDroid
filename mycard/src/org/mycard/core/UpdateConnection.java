package org.mycard.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.client.HttpClient;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.net.websocket.MoeSocketClient;

public class UpdateConnection {
	
	public interface TaskStatusCallback {
		void onTaskFinish(BaseDataWrapper wrapper);
		void onTaskContinue(BaseDataWrapper wrapper);
	}

	private HttpClient mClient;
	
	private BlockingQueue<BaseDataWrapper> mTaskQueue;
	
	private BaseThread mUpdateThread;
	
	
	public UpdateConnection(HttpClient client, TaskStatusCallback callback, boolean isContinued) {
		// TODO Auto-generated constructor stub
		mClient = client;
		mTaskQueue = new LinkedBlockingQueue<BaseDataWrapper>();
		if (isContinued) {
			mUpdateThread = new MoeThread(callback);
		} else {
			mUpdateThread = new UpdateThread(mTaskQueue, callback, mClient);
		}
		mUpdateThread.start();
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
		mUpdateThread.terminate();
		mTaskQueue.clear();
	}

}
