package org.mycard.widget.adapter;

import java.util.ArrayList;
import java.util.List;

import org.mycard.R;
import org.mycard.data.RoomInfo;
import org.mycard.data.UserInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RoomAdapter extends BaseAdapter {

	public static class ViewHolder {
		public ImageView mImage;
		public TextView mTitle;
		public TextView mProperty;
	}

	private List<RoomInfo> mDataList;
	private Context mContext;

	private int mFilter;

	public RoomAdapter(List<RoomInfo> lists, Context context, int filter) {
		// TODO Auto-generated constructor stub
		super();
		setData(lists);
		mContext = context;
		mFilter = filter;
	}

	public void setData(List<RoomInfo> lists) {
		// TODO Auto-generated method stub
		mDataList = new ArrayList<RoomInfo>();
		for (RoomInfo info : lists) {
			if (info.mode == mFilter) {
				mDataList.add(info.clone());
			}
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.room_list_item, null);
			ViewHolder holder = new ViewHolder();
			holder.mTitle = (TextView) convertView
					.findViewById(R.id.item_list_name);
			holder.mImage = (ImageView) convertView
					.findViewById(R.id.item_list_icon);
			holder.mProperty = (TextView) convertView
					.findViewById(R.id.item_property_text);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.mImage.setImageResource(R.drawable.logo);
		holder.mTitle.setText(mDataList.get(position).name);
		holder.mProperty.setText(generatePropertyString(mDataList.get(position)));
		return convertView;
	}
	
	private String generatePropertyString(RoomInfo roomInfo) {
		StringBuilder builder = new StringBuilder();
		for (UserInfo userInfo : roomInfo.mUsers) {
			builder.append(userInfo.name + " | ");
		}
		builder.append(roomInfo.status
				? mContext.getString(R.string.ongoing)
				: mContext.getString(R.string.pending));
		return builder.toString();
	}
}
