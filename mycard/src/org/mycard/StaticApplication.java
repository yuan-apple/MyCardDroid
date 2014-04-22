package org.mycard;

import org.apache.http.client.HttpClient;
import org.mycard.core.Controller;
import org.mycard.data.Model;
import org.mycard.net.http.ThreadSafeHttpClientFactory;

import android.app.Application;

public class StaticApplication extends Application {
	
	private ThreadSafeHttpClientFactory  mHttpFactory;
	
	private static StaticApplication INSTANCE;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		INSTANCE = this;
		mHttpFactory = new ThreadSafeHttpClientFactory(this);
		Controller.peekInstance();
		Model.peekInstance();
	}
	
	public HttpClient getHttpClient() {
		return mHttpFactory.getHttpClient();
	}
	
	public static StaticApplication peekInstance() {
		return INSTANCE;
	}

}
