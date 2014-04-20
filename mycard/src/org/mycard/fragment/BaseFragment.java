package org.mycard.fragment;

import org.mycard.MainActivity;
import org.mycard.R;
import org.mycard.core.UpdateController;
import org.mycard.data.DataStore;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment implements Handler.Callback{

	public interface OnActionBarChangeCallback {
		void onActionBarChange(int msgType, int action);
	}

	/**
	 * @author mabin
	 * 
	 */
	public static class DataHandler extends Handler {
		/**
		 * 
		 */
		public DataHandler(Looper looper, Callback callback) {
			// TODO Auto-generated constructor stub
			super(looper, callback);
		}
	}
	
	public static final String ARG_ITEM_TITLE = "basefragment.title";

	protected MainActivity mActivity;
	protected UpdateController mController;
	protected DataHandler mHandler;
	protected DataStore mDataStore;
	protected OnActionBarChangeCallback mActionBarCallback;
	
	protected String mTitle;
	
	protected TextView mActionBarTitle;
	
	protected int mCompiledTitleColor;
	
	
	@Override
	public void onResume() {
		super.onResume();
		if (mTitle != null) {
			mActionBarTitle.setText(mTitle);
			mActionBarTitle.setTextColor(mCompiledTitleColor);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTitle = getArguments().getString(ARG_ITEM_TITLE);
		int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		mActionBarTitle = (TextView) mActivity.findViewById(actionBarTitleId);
		mCompiledTitleColor = getResources().getColor(R.color.black);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		mController = mActivity.getController();
		mDataStore = mController.getDataStore();
		mHandler = new DataHandler(mActivity.getMainLooper(), this);
		if (activity instanceof OnActionBarChangeCallback) {
			mActionBarCallback = (OnActionBarChangeCallback) activity;
		}
	}

	protected void showDialog(Bundle params) {
		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		RoomDetailFragment newFragment = RoomDetailFragment
				.newInstance(params);
		newFragment.show(ft, "dialog");
	}
}
