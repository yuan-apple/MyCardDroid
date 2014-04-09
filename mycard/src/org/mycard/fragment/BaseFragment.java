package org.mycard.fragment;

import org.mycard.MainActivity;
import org.mycard.core.UpdateController;
import org.mycard.data.DataStore;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements Handler.Callback {

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

	protected MainActivity mActivity;
	protected UpdateController mController;
	protected DataHandler mHandler;
	protected DataStore mDataStore;
	protected OnActionBarChangeCallback mActionBarCallback;

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
