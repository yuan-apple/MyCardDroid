package org.mycard.fragment;

import java.util.List;

import org.mycard.R;
import org.mycard.data.RoomInfo;
import org.mycard.widget.adapter.RoomAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RoomPageFragment extends BaseFragment implements OnItemClickListener {
	
	private static final int MSG_UPDATE_ROOM_LIST = 0;
	
	private ListView mContentView;
	private RoomAdapter mAdapter;
	private List<RoomInfo> mData;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mController.asyncUpdateRoomList(mHandler.obtainMessage(MSG_UPDATE_ROOM_LIST));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContentView = (ListView) inflater.inflate(R.layout.common_list, null);
		mContentView.setOnItemClickListener(this);
		return mContentView;
	}

	/* (non-Javadoc)
	 * @see android.os.Handler.Callback#handleMessage(android.os.Message)
	 */
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MSG_UPDATE_ROOM_LIST:
			prepareData();
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * 
	 * @return
	**/
	private void prepareData() {
		mData = mDataStore.getRoomList();
		mAdapter = new RoomAdapter(mData, mActivity);
		mContentView.setAdapter(mAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

}
