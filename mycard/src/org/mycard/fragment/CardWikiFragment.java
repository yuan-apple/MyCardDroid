package org.mycard.fragment;

import org.mycard.provider.YGOCards;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CardWikiFragment extends BaseFragment implements LoaderCallbacks<Cursor>{

	private static final int QUERY_SOURCE_LOADER_ID = 0;
	private static final String TAG = "CardWikiFragment";
	private CursorLoader mCursorLoader;
	
	private String[] mProjects = YGOCards.COMMON_DATA_PEOJECTION;
	
	private Uri mContentUri = YGOCards.CONTENT_URI;

	/* (non-Javadoc)
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
		initCursorLoader();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void initCursorLoader() {
		getLoaderManager().initLoader(QUERY_SOURCE_LOADER_ID, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		mCursorLoader = new CursorLoader(getActivity().getApplicationContext(), mContentUri,
				mProjects,
				null, null,
				mProjects[5] + " desc");
		return mCursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		if (arg1 != null) {
			Log.d(TAG, "load finished");
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

}
