package org.mycard.core;

import java.util.concurrent.BlockingQueue;

import org.apache.http.client.HttpClient;
import org.mycard.core.UpdateConnection.TaskFinishCallback;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.net.http.BaseHttpConnector;

public class BaseThread extends Thread {
	private volatile boolean isRunning = true;

	private BlockingQueue<BaseDataWrapper> mQueue;

	protected BaseHttpConnector mConnector;
	
	protected TaskFinishCallback mCallback;

	public BaseThread(BlockingQueue<BaseDataWrapper> queue, TaskFinishCallback callback) {
		// TODO Auto-generated constructor stub
		mQueue = queue;
		mCallback = callback;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		BaseDataWrapper wrapper = null;
		while (isRunning && !isInterrupted()) {
			try {
				wrapper = mQueue.take();
				if (wrapper != null) {
					mConnector.get(wrapper);
					mCallback.onTaskFinish(wrapper);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void terminate() {
		if (isRunning) {
			interrupt();
			isRunning = false;
		}
	}

}
