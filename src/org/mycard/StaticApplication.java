package org.mycard;

import org.apache.http.client.HttpClient;
import org.mycard.net.http.ThreadSafeHttpClientFactory;

import android.app.Application;

public class StaticApplication extends Application {
	
	private ThreadSafeHttpClientFactory  mHttpFactory;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mHttpFactory = new ThreadSafeHttpClientFactory(this);
	}
	
	public HttpClient getHttpClient() {
		return mHttpFactory.getHttpClient();
	}

}
