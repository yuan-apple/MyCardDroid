package org.mycard.core;

import java.util.concurrent.BlockingQueue;

import org.apache.http.client.HttpClient;
import org.mycard.core.UpdateConnection.TaskStatusCallback;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.net.http.UpdateHttpConnector;

public class UpdateThread extends BaseDownloadThread {

	public UpdateThread(BlockingQueue<BaseDataWrapper> queue, TaskStatusCallback callback, HttpClient client) {
		super(queue, callback);
		// TODO Auto-generated constructor stub
		mConnector = new UpdateHttpConnector(client);
	}
}
