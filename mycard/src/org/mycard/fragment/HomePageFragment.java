package org.mycard.fragment;

import org.mycard.R;

import android.os.Bundle;
import android.os.Message;

public class HomePageFragment extends BaseFragment {

	/* (non-Javadoc)
	 * @see android.os.Handler.Callback#handleMessage(android.os.Message)
	 */
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCompiledTitleColor = getResources().getColor(R.color.dark_blue);
	}

}
