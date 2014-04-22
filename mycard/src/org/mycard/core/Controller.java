package org.mycard.core;

import org.mycard.StaticApplication;

import android.os.Message;

public class Controller {
	
	private static Controller INSTANCE;
	
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

}
