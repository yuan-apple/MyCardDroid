package org.mycard.net.websocket;

import java.net.URI;
import java.util.zip.GZIPInputStream;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import org.mycard.data.wrapper.BaseDataWrapper;

import android.util.Log;

public class MoeSocketClient extends WebSocketClient {
	private static final String TAG = "MoeSocketClient";

	public MoeSocketClient(URI serverURI, BaseDataWrapper wrapper) {
		// TODO Auto-generated constructor stub
		super(serverURI);
	}
		
	public MoeSocketClient(URI serverUri, Draft draft) {
		super(serverUri, draft);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Connection closed by " + (arg2 ? "remote peer" : "us") + "due to " + arg1);
	}

	@Override
	public void onError(Exception arg0) {
		// TODO Auto-generated method stub
		arg0.printStackTrace();
	}

	@Override
	public void onMessage(String arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "received: " + arg0);
	}

	@Override
	public void onOpen(ServerHandshake arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "opened connection");
	}
	
	@Override
	public void onFragment(Framedata frame) {
		// TODO Auto-generated method stub
		Log.d(TAG, "received fragment:" + new String(frame.getPayloadData().array()));
	}

}
