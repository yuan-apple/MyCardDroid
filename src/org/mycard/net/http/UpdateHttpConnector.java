package org.mycard.net.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mycard.Constants;
import org.mycard.data.BaseDataWrapper;
import org.mycard.data.ResourcesConstants;

import android.util.Log;

/**
 * @author mabin
 * 
 */
public class UpdateHttpConnector extends BaseHttpConnector implements
		ResourcesConstants {

	private static final String TAG = "UpdateHttpConnector";

	/**
	 * @param client
	 */
	public UpdateHttpConnector(HttpClient client) {
		super(client);
	}
	
	/* (non-Javadoc)
	 * @see com.uc.addon.indoorsmanwelfare.net.http.BaseHttpConnector#get(com.uc.addon.indoorsmanwelfare.model.data.wrapper.BaseDataWrapper)
	 */
	@Override
	public void get(BaseDataWrapper wrapper) {
		// TODO Auto-generated method stub
		int i = 0;
		String url = wrapper.getUrl(0);
		while(null != url) {
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
	protected void handleResponse(InputStream data, BaseDataWrapper wrapper) throws InterruptedException{
		boolean status = false;
		StringBuilder out = new StringBuilder();
		JSONObject jsonObj;
		BufferedReader reader = new BufferedReader(new InputStreamReader(data));
		int len = -1;
		char[] buffer = new char[Constants.IO_BUFFER_SIZE];
		try {
			while ((!Thread.currentThread().isInterrupted() && (len = reader.read(buffer, 0, Constants.IO_BUFFER_SIZE)) != -1))
				out.append(buffer, 0, len);
			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException();
			}
			jsonObj = new JSONObject(out.toString());
//			String result = jsonObj.getString(JSON_KEY_QUERY_STATUS);
//			if (!RESULT_OK.equals(result)) {
//				wrapper.setResult(status);
//				return;
//			}
//			if (jsonObj.has(JSON_KEY_QUERY_VERSION) &&
//					jsonObj.has(JSON_KEY_QUERY_LATEST)) {
//				((UpdateDataWrapper) wrapper).setVersions(jsonObj.getString(JSON_KEY_QUERY_VERSION));
//				((UpdateDataWrapper) wrapper).setLatests(jsonObj.getString(JSON_KEY_QUERY_LATEST));
//			}
//			if (jsonObj.has(JSON_KEY_QUERY_DATA)) {
//				Object upAppItemObj = jsonObj.get(JSON_KEY_QUERY_DATA);
//				if (!JSONObject.NULL.equals(upAppItemObj)) {
//					if (upAppItemObj instanceof JSONArray) {
//						((UpdateDataWrapper) wrapper)
//								.parse((JSONArray) upAppItemObj);
//						status = true;
//					}
//				}
//				upAppItemObj = null;
//			}
			jsonObj = null;
		} catch (JSONException e) {
			e.printStackTrace();
			status = false;
		} catch (IOException e) {
			e.printStackTrace();
			status = false;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			status = false;
		} finally {
			wrapper.setResult(status);
			buffer = null;
			out.delete(0, out.length());
			out = null;
			System.gc();
		}
	}
}
