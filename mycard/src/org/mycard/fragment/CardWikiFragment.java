package org.mycard.fragment;

import org.mycard.R;
import org.mycard.provider.YGOCards;
import org.mycard.utils.ResourceUtils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class CardWikiFragment extends BaseFragment implements
		LoaderCallbacks<Cursor> {

	private static final int QUERY_SOURCE_LOADER_ID = 0;
	private static final String TAG = "CardWikiFragment";
	private CursorLoader mCursorLoader;

	private String[] mProjects = YGOCards.COMMON_DATA_PEOJECTION;
	private String[] mProjects_id = YGOCards.COMMON_DATA_PEOJECTION_ID;

	private Uri mContentUri = YGOCards.CONTENT_URI;

	private SimpleCursorAdapter simpleCursorAdapter = null;
	private ListView listView;
	private Context mContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Handler.Callback#handleMessage(android.os.Message)
	 */
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// initCursorLoader();
		// return super.onCreateView(inflater, container, savedInstanceState);
		mContext = getActivity().getApplicationContext();
		ResourceUtils.init(mContext);

		View view = inflater.inflate(R.layout.card_info_list, null);

		listView = (ListView) view.findViewById(R.id.card_info_list);
		simpleCursorAdapter = new SimpleCursorAdapter(mContext,
				R.layout.card_list_item, null, mProjects_id, new int[] {
						R.id.item_name, R.id.item_level, R.id.item_race,
						R.id.item_attr, R.id.item_atk, R.id.item_def }, 0);
		simpleCursorAdapter.setViewBinder(new CardInfoBinder());
		listView.setAdapter(simpleCursorAdapter);
		initCursorLoader();
		return view;
	}

	private class CardInfoBinder implements ViewBinder {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int arg2) {
			switch (arg2) {
			case 0:
				((TextView) view).setText("名称：" + cursor.getString(8));
				return true;
			case 1:
				((TextView) view).setText("星级：" + cursor.getString(5));
				return true;
			case 2:
				int race = cursor.getInt(6);
				int n_race = getLog(race);
				((TextView) view)
						.setText("种族:"
								+ ResourceUtils
										.getStringArray(R.array.card_race)[n_race]);
				return true;
			case 3:
				int attr = cursor.getInt(7);
				int n_attr = getLog(attr);
				((TextView) view)
						.setText("属性："
								+ ResourceUtils
										.getStringArray(R.array.card_attr)[n_attr]);
				return true;
			case 4:
				((TextView) view).setText("攻击力:" + cursor.getString(3));
				return true;
			case 5:
				((TextView) view).setText("防御力:" + cursor.getString(4));
				return true;

			}
			return false;
		}
	}

	private void initCursorLoader() {
		getLoaderManager().initLoader(QUERY_SOURCE_LOADER_ID, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		mCursorLoader = new CursorLoader(mContext, mContentUri, mProjects,
				null, null, mProjects[5] + " desc");
		return mCursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		if (arg1 != null) {
			Log.d(TAG, "--->load finished");
			// for (arg1.moveToFirst(); !arg1.isAfterLast(); arg1.moveToNext())
			// {
			// String a = arg1.getString(8);
			// if (a.equals("xxxx")) {
			// Log.d("a0", arg1.getString(0));
			// break;
			// }
			// }
		}
		// System.out.println(DatabaseUtils.dumpCursorToString(arg1));
		simpleCursorAdapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

	// 获取数值对应位移数
	private int getLog(int value) {
		int x = 0;
		while (value > 1) {
			value >>= 1;
			x++;
		}
		return x;
	}

}
