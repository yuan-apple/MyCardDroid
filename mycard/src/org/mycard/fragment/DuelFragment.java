package org.mycard.fragment;

import java.util.List;

import org.mycard.Constants;
import org.mycard.R;
import org.mycard.data.ResourcesConstants;
import org.mycard.data.RoomInfo;
import org.mycard.data.wrapper.IBaseWrapper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.RelativeLayout;

public class DuelFragment extends TabFragment {

	public class RoomTabPageAdapter extends FragmentPagerAdapter {

		public RoomTabPageAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Log.d(TAG, "getItem: " + arg0);
			Fragment ft;
			switch (arg0) {
			case ROOM_TAG_INDEX_SINGLE_MODE:
				ft = RoomPageFragment.newInstance(ROOM_TAG_INDEX_SINGLE_MODE);
				break;
			case ROOM_TAG_INDEX_MATCH_MODE:
				ft = RoomPageFragment.newInstance(ROOM_TAG_INDEX_MATCH_MODE);
				break;
			case ROOM_TAG_INDEX_TAG_MODE:
				ft = RoomPageFragment.newInstance(ROOM_TAG_INDEX_TAG_MODE);
				break;
			default:
				ft = RoomPageFragment.newInstance(ROOM_TAG_INDEX_SINGLE_MODE);
				break;
			}
			mFragments.put(arg0, ft);
			return ft;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			Log.d(TAG, "destroyItem: " + position);
			super.destroyItem(container, position, object);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTabs.length;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

	}

	private static final String TAG = "RoomFragment";

	public static final String ROOM_BUNDLE_KEY_TAG_INDEX = "bundle.key.room.tag.index";

	private static final int ROOM_TAG_INDEX_SINGLE_MODE = 0;
	private static final int ROOM_TAG_INDEX_MATCH_MODE = 1;
	private static final int ROOM_TAG_INDEX_TAG_MODE = 2;

	private SparseArrayCompat<Fragment> mFragments = new SparseArrayCompat<Fragment>();

	private boolean isDataloaded = false;

	private RelativeLayout mExtraView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCompiledTitleColor = getResources().getColor(R.color.dark_purple);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onAttach: E");
		super.onAttach(activity);
		mTabs = getResources().getStringArray(R.array.duel_mode);
		mTabCount = mTabs.length;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDetach: E");
		super.onDetach();
		for (int i = 0; i < mFragments.size(); i++) {
			RoomPageFragment f = ((RoomPageFragment) mFragments.get(i));
			if (f != null) {
				f.onDetach();
			}
		}
		mFragments.clear();
	}

	public void onResume() {
		Log.d(TAG, "onResume: E");
		super.onResume();
		mController.asyncUpdateRoomList(mHandler
				.obtainMessage(Constants.MSG_UPDATE_ROOM_LIST));
		mActionBarCallback.onActionBarChange(
				Constants.ACTION_BAR_CHANGE_TYPE_DATA_LOADING, 1);
		for (int i = 0; i < mFragments.size(); i++) {
			RoomPageFragment f = ((RoomPageFragment) mFragments.get(i));
			if (f != null) {
				f.onResume();
			}
		}
		mActivity.registerForActionNew(mHandler);
		mActivity.registerForActionPlay(mHandler);
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause: E");
		super.onPause();
		mController.stopUpdateRoomList();
		isDataloaded = false;
		for (int i = 0; i < mFragments.size(); i++) {
			RoomPageFragment f = ((RoomPageFragment) mFragments.get(i));
			if (f != null) {
				f.onPause();
			}
		}
		mActivity.unregisterForActionNew(mHandler);
		mActivity.unregisterForActionPlay(mHandler);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreateView: E");
		View view = super.onCreateView(inflater, container, savedInstanceState);
		mExtraView = (RelativeLayout) view.findViewById(R.id.extra_view);
		if (!isDataloaded) {
			ViewGroup vg = (ViewGroup) inflater.inflate(
					R.layout.loading_progress, null);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			mExtraView.addView(vg, lp);
			mExtraView.setVisibility(View.VISIBLE);
		}
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if (isDataloaded) {
			mExtraView.removeAllViews();
		}
	}

	private String[] mTabs;

	@Override
	protected FragmentPagerAdapter initFragmentAdapter() {
		return new RoomTabPageAdapter(getChildFragmentManager());
	}

	@Override
	protected void initTab() {
		super.initTab();
		int i = 0;
		for (String title : mTabs) {
			addTab(i++, title, mTabs.length);
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case Constants.MSG_UPDATE_ROOM_LIST:
			if (msg.arg2 == IBaseWrapper.TASK_STATUS_SUCCESS) {
				if (!isDataloaded) {
					isDataloaded = true;
					mActionBarCallback.onActionBarChange(
							Constants.ACTION_BAR_CHANGE_TYPE_DATA_LOADING, 0);
					if (mExtraView != null) {
						mExtraView.setVisibility(View.GONE);
					}
				}
				List<RoomInfo> data = mDataStore.getRooms();
				int size = mFragments.size();
				for (int i = 0; i < size; i++) {
					RoomPageFragment f = ((RoomPageFragment) mFragments.get(i));
					if (f != null) {
						f.setData(data);
					}
				}
				data = null;
				// force to reclaim memory
				System.gc();
			} else if (msg.arg2 == IBaseWrapper.TASK_STATUS_FAILED) {
				mController.asyncUpdateRoomList(mHandler
						.obtainMessage(Constants.MSG_UPDATE_ROOM_LIST));
				isDataloaded = false;
				if (mExtraView != null) {
					mExtraView.setVisibility(View.VISIBLE);
				}
			}
			break;
		case Constants.ACTION_BAR_EVENT_TYPE_NEW:
			Log.i(TAG, "receive action bar new click event");
			Bundle bundle = new Bundle();
			bundle.putBoolean(ResourcesConstants.ROOM_OPTIONS, true);
			showDialog(bundle);
			break;
		case Constants.ACTION_BAR_EVENT_TYPE_PLAY:
			Log.i(TAG, "receive action bar play click event");
		default:
			break;
		}
		return true;
	}

}
