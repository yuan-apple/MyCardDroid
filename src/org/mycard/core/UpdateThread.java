package org.mycard.core;

import java.util.concurrent.BlockingQueue;

import org.apache.http.client.HttpClient;
import org.mycard.core.UpdateConnection.TaskFinishCallback;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.net.http.UpdateHttpConnector;

public class UpdateThread extends BaseThread {

	public UpdateThread(BlockingQueue<BaseDataWrapper> queue, TaskFinishCallback callback, HttpClient client) {
		super(queue, callback);
		// TODO Auto-generated constructor stub
		mConnector = new UpdateHttpConnector(client);
	}
	

}
