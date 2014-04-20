package org.mycard.widget;

import android.content.Context;
import android.widget.Button;

public interface RoomConfigUIBase {
	Context getContext();
	
	RoomConfigController getController();
	
	void setPositiveButton(CharSequence text);
	
	void setCancelButton(CharSequence text);
	
	Button getPosiveButton();
	
	Button getCancelButton();
	
	void setTitle(CharSequence text);
	
	void setTitle(int resId);

}
