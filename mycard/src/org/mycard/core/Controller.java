package org.mycard.core;

import org.mycard.Constants;
import org.mycard.StaticApplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Controller {
	
	private static Controller INSTANCE;
	
	private UserStatusTracker mLoginStatusTracker;
	
	private UpdateController mUpdateController;
	
	private Controller(StaticApplication app) {
		mUpdateController = new UpdateController(app);
	}

	public static Controller peekInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Controller(StaticApplication.peekInstance());
		}
		return INSTANCE;
		
	}

	public void asyncUpdateServer(Message msg) {
		mUpdateController.asyncUpdateServer(msg);
	}

	public void asyncUpdateRoomList(Message msg) {
		mUpdateController.asyncUpdateRoomList(msg);
		
	}

	public void stopUpdateRoomList() {
		mUpdateController.stopUpdateRoomList();
	}
	
	public void asyncLogin(Bundle data) {
		mUpdateController.asyncLogin(Message.obtain(mLoginStatusTracker, Constants.MSG_ID_LOGIN, data));
		String name = data.getString(Constants.BUNDLE_KEY_USER_NAME);
		mLoginStatusTracker.changeLoginStatus(UserStatusTracker.LOGIN_STATUS_LOGGING, true);
		mLoginStatusTracker.setLoginName(name);
	}
	
	public String getLoginName() {
		return mLoginStatusTracker.getLoginName();
	}
	
	public int getLoginStatus() {
		return mLoginStatusTracker.getLoginStatus();
	}
	
	public void registerForLoginStatusChange(Handler h) {
		mLoginStatusTracker.registerForLoginStatusChange(h);
	}
	
	public void unregisterForLoginStatusChange(Handler h) {
		mLoginStatusTracker.unregisterForLoginStatusChange(h);
	}

}
