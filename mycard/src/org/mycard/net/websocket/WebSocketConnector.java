package org.mycard.net.websocket;

import java.net.URI;
import java.nio.ByteBuffer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.mycard.core.MoeThread;
import org.mycard.core.MoeThread.MoeEventHandler;
import org.mycard.data.wrapper.BaseDataWrapper;

import android.util.Log;

public class WebSocketConnector {
	private static final String TAG = "MoeSocketClient";
	
	private MoeEventHandler mHandler;
	
	private URI mURL;
	
	private WebSocketClient mClient; 
	
	private BaseDataWrapper mWrapper;
	
	private StringBuilder mDataCache = new StringBuilder();

	public WebSocketConnector() {
		// TODO Auto-generated constructor stub
	}
		
	public void setHandler(MoeEventHandler handler) {
		// TODO Auto-generated method stub
		mHandler = handler;
	}
	
	public void connect(BaseDataWrapper wrapper) {
		mWrapper = wrapper;
		mURL = URI.create(wrapper.getUrl(0));
		mClient = new WebSocketClient(mURL) {
			@Override
			public void onOpen(ServerHandshake arg0) {
				Log.d(TAG, "opened connection");
			}
			
			@Override
			public void onMessage(String arg0) {
				Log.d(TAG, "received message from server: " + arg0);
				JSONArray jsonArray;
				try {
					jsonArray = new JSONArray(arg0);
					mWrapper.parse(jsonArray);
					mHandler.sendEmptyMessage(MoeThread.MSG_ID_DATA_UPDATE);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onError(Exception arg0) {
				// TODO Auto-generated method stub
				arg0.printStackTrace();
				
			}
			
			@Override
			public void onClose(int arg0, String arg1, boolean arg2) {
				// TODO Auto-generated method stub
				Log.d(TAG, "connection closed by " + (arg2 ? "remote" : "self") + " due to " + arg1);
				mHandler.sendEmptyMessage(MoeThread.MSG_ID_CONNECTION_CLOSED);
			}
			
			@Override
			public void onFragment(Framedata frame) {
				super.onFragment(frame);
				mDataCache.append(new String(frame.getPayloadData().array()));
				if (!frame.isFin()) {
					return;
				} else {
					JSONArray jsonArray;
					try {
						Log.d(TAG, "received fragment:" + new String(mDataCache.toString()));
						jsonArray = new JSONArray(mDataCache.toString());
						mWrapper.parse(jsonArray);
						mHandler.sendEmptyMessage(MoeThread.MSG_ID_DATA_UPDATE);
						mDataCache.delete(0, mDataCache.length());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		mClient.connect();
	}
	
	public void terminate() {
		mClient.close();
	}


}
