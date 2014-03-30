package org.mycard.fragment;

import org.mycard.MainActivity;
import org.mycard.core.UpdateController;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	
	protected MainActivity mActivity;
	protected UpdateController mController;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		mController = mActivity.getController();
	}

}
