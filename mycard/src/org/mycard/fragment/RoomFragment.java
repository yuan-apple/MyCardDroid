package org.mycard.fragment;

import org.mycard.R;

import android.app.Activity;
import android.mtp.MtpStorageInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
			Fragment ft = new RoomPageFragment();;
			switch (arg0) {
			case ROOM_TAG_INDEX_SINGLE_MODE:
				break;
			case ROOM_TAG_INDEX_MATCH_MODE:
				break;
			case ROOM_TAG_INDEX_TAG_MODE:
				break;
			default:
				break;
			}
			return ft;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTabs.length;
		}

	}
	
	private static final int ROOM_TAG_INDEX_SINGLE_MODE = 0;
	private static final int ROOM_TAG_INDEX_MATCH_MODE = 1;
	private static final int ROOM_TAG_INDEX_TAG_MODE = 2;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mTabs = getResources().getStringArray(R.array.room_items);
		mTabCount = mTabs.length;
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

}
