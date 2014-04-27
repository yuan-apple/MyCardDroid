package org.mycard.core;

import org.mycard.Constants;
import org.mycard.StaticApplication;
import org.mycard.actionbar.ActionBarController;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;

public class Controller {
	
	private static Controller INSTANCE;
	
	private UserStatusTracker mLoginStatusTracker;
	
	private UpdateController mUpdateController;
	
	private ActionBarController mActionBarController;
	
	private Controller(StaticApplication app) {
		mUpdateController = new UpdateController(app);
		mActionBarController = new ActionBarController();
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
	
	public boolean handleActionBarEvent(MenuItem item) {
		return mActionBarController.handleAction(item);
	}

	public void registerForActionNew(Handler h) {
		mActionBarController.registerForActionNew(h);
	}

	public void unregisterForActionNew(Handler h) {
		mActionBarController.unregisterForActionNew(h);
	}

	public void registerForActionPlay(Handler h) {
		mActionBarController.registerForActionPlay(h);
	}

	public void unregisterForActionPlay(Handler h) {
		mActionBarController.unregisterForActionPlay(h);
	}
	
	public void registerForActionSearch(Handler h) {
		mActionBarController.registerForActionSearch(h);
	}

	public void unregisterForActionSearch(Handler h) {
		mActionBarController.unregisterForActionSearch(h);
	}
	
	public void registerForActionFilter(Handler h) {
		mActionBarController.registerForActionFilter(h);
	}
	
	public void unregisterForActionFilter(Handler h) {
		mActionBarController.unregisterForActionFilter(h);
	}


}
