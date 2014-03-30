package org.mycard.fragment;

import java.util.List;

import org.mycard.R;
import org.mycard.data.RoomInfo;
import org.mycard.widget.adapter.RoomAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RoomPageFragment extends BaseFragment {
	
	private ListView mContentView;
	private RoomAdapter mAdapter;
	private List<RoomInfo> mData;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = (ListView) inflater.inflate(R.layout.common_list, null);
		return mContentView;
	}

}
