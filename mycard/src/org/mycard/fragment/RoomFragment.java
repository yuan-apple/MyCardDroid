package org.mycard.fragment;

import java.util.List;

import org.mycard.R;
import org.mycard.data.RoomInfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RoomFragment extends TabFragment {
	
	public class RoomTabPageAdapter extends FragmentPagerAdapter {

		public RoomTabPageAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
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
	
	public static final String ROOM_BUNDLE_KEY_TAG_INDEX = "bundle.key.room.tag.index";
	
	private static final int ROOM_TAG_INDEX_SINGLE_MODE = 0;
	private static final int ROOM_TAG_INDEX_MATCH_MODE = 1;
	private static final int ROOM_TAG_INDEX_TAG_MODE = 2;
	
	private static final int MSG_UPDATE_ROOM_LIST = 0;
	
	private SparseArrayCompat<Fragment> mFragments = new SparseArrayCompat<Fragment>();
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mTabs = getResources().getStringArray(R.array.room_items);
		mTabCount = mTabs.length;
		mController.asyncUpdateRoomList(mHandler.obtainMessage(MSG_UPDATE_ROOM_LIST));
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		mController.stopUpdateRoomList();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = super.onCreateView(inflater, container, savedInstanceState);
		return view;
	}
	
	private String[] mTabs;

	@Override
	protected FragmentPagerAdapter initFragmentAdapter() {
		return new RoomTabPageAdapter(getFragmentManager());
	}
	
	@Override
	protected void initTab() {
		super.initTab();
		int i= 0;
		for (String title : mTabs) {
			addTab(i++, title, mTabs.length);
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_UPDATE_ROOM_LIST:
			List<RoomInfo> data = mDataStore.getRooms();
			int size = mFragments.size();
			for (int i = 0; i < size; i++) {
				RoomPageFragment f = ((RoomPageFragment) mFragments.get(i));
				if (f != null) {
					f.setData(data);
				}
			}
			data = null;
			//force to reclaim memory
			System.gc();
			break;
		default:
			break;
		}
		return false;
	}

}
