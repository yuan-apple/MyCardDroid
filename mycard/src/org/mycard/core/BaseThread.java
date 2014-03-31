package org.mycard.core;

import org.mycard.core.UpdateConnection.TaskStatusCallback;

public abstract class BaseThread extends Thread {
	
	public BaseThread(TaskStatusCallback callback) {
		// TODO Auto-generated constructor stub
		mCallback = callback; 
	}
	
	protected volatile boolean isRunning = true;
	
	protected TaskStatusCallback mCallback;
	
	public void terminate() {
		if (isRunning) {
			interrupt();
			isRunning = false;
		}
	}

}
