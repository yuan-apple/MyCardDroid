package org.mycard.net.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.HttpClient;
import org.mycard.Constants;
import org.mycard.data.ResourcesConstants;
import org.mycard.data.wrapper.BaseDataWrapper;
import org.mycard.data.wrapper.IBaseWrapper;

import android.util.Log;

/**
 * @author mabin
 * 
 */
public class DataHttpConnector extends BaseHttpConnector implements
		ResourcesConstants {

	private static final String TAG = "UpdateHttpConnector";

	/**
	 * @param client
	 */
	public DataHttpConnector(HttpClient client) {
		super(client);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uc.addon.indoorsmanwelfare.net.http.BaseHttpConnector#get(com.uc.
	 * addon.indoorsmanwelfare.model.data.wrapper.BaseDataWrapper)
	 */
	@Override
	public void get(BaseDataWrapper wrapper) {
		// TODO Auto-generated method stub
		int i = 0;
		String url = wrapper.getUrl(0);
		while (null != url) {
			InputStream is = HttpUtils.doGet(mClient, url);
			if (null != is) {
				try {
					handleResponse(is, wrapper);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
			}
			url = wrapper.getUrl(++i);
		}
	}

	/**
	 * 
	 * @author: mabin
	 * @return
	 **/
	@Override
	protected void handleResponse(InputStream data, BaseDataWrapper wrapper)
			throws InterruptedException {
		int status = IBaseWrapper.TASK_STATUS_SUCCESS;
		StringBuilder out = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(data));
		int len = -1;
		char[] buffer = new char[Constants.IO_BUFFER_SIZE];
		try {
			while ((!Thread.currentThread().isInterrupted() && (len = reader
					.read(buffer, 0, Constants.IO_BUFFER_SIZE)) != -1))
				out.append(buffer, 0, len);
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			Log.d(TAG, out.toString());
			wrapper.parse(out);
		} catch (IOException e) {
			e.printStackTrace();
			status = IBaseWrapper.TASK_STATUS_FAILED;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			status = IBaseWrapper.TASK_STATUS_FAILED;
		} finally {
			wrapper.setResult(status);
			buffer = null;
			out.delete(0, out.length());
			out = null;
			System.gc();
		}
	}
}
