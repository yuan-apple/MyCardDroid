package org.mycard.core;

import org.mycard.core.UpdateConnection.TaskStatusCallback;
import org.mycard.data.wrapper.BaseDataWrapper;

public class MoeThread extends BaseThread {

	public MoeThread(TaskStatusCallback callback) {
		super(callback);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		BaseDataWrapper wrapper = null;
		while (isRunning && !isInterrupted()) {
			
		}
	}

}
