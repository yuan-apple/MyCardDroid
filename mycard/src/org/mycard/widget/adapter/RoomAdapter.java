package org.mycard.widget.adapter;

import java.util.List;

import org.mycard.R;
import org.mycard.data.RoomInfo;

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
		public TextView mMode;
	}

	private List<RoomInfo> mDataList;
	private Context mContext;
	
	public RoomAdapter(List<RoomInfo> lists, Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.room_list_item, null);
			ViewHolder holder = new ViewHolder();
			holder.mTitle = (TextView) convertView.findViewById(R.id.item_list_name);
			holder.mImage = (ImageView) convertView.findViewById(R.id.item_list_icon);
			holder.mMode = (TextView) convertView.findViewById(R.id.item_property_text);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.mImage.setImageResource(R.drawable.logo);
		holder.mTitle.setText(mDataList.get(position).name);
		holder.mTitle.setText(mContext.getResources()
				.getStringArray(R.array.room_items)[mDataList.get(position).mode]);
		return convertView;
	}

}
