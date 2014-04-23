package org.mycard.common;

import org.mycard.R;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;

public class ActionBarCreator {

	private Context mContext;

	public ActionBarCreator(Context context) {
		mContext = context;
	}

	private boolean mLoading = false;

	private boolean mSearch = false;

	private boolean mRoomCreate = false;

	private boolean mSettings = true;

	private boolean mPlay = false;
	
	private boolean mUserStatus = true;
	private int mUserStatusRes = R.string.login_sign_up;
	
	public ActionBarCreator setUserStatus(boolean userStatus, int resID) {
		mUserStatus = userStatus;
		mUserStatusRes = resID;
		return this;
	}

	public ActionBarCreator setSettings(boolean settings) {
		mSearch = true;
		return this;
	}

	public ActionBarCreator setLoading(boolean loading) {
		mLoading = loading;
		return this;
	}

	public ActionBarCreator setSearch(boolean search) {
		mSearch = search;
		return this;
	}

	public ActionBarCreator setRoomCreate(boolean roomCreate) {
		mRoomCreate = roomCreate;
		return this;
	}

	public ActionBarCreator setPlay(boolean play) {
		mPlay = play;
		return this;
	}

	public void createMenu(Menu menu) {
		int index = 0;
		menu.removeGroup(Menu.NONE);
		if (mUserStatus) {
			MenuItem useritem = menu.add(Menu.NONE, R.id.action_userstatus, index++, mUserStatusRes);
			MenuItemCompat.setShowAsAction(useritem, MenuItemCompat.SHOW_AS_ACTION_NEVER);
		}
		if (mSettings) {
			MenuItem item = menu.add(Menu.NONE, R.id.action_settings, index++,
					R.string.action_settings);
			MenuItemCompat.setShowAsAction(item,
					MenuItemCompat.SHOW_AS_ACTION_NEVER);
		}
		if (mLoading) {
			MenuItem item = menu.add(Menu.NONE, R.id.action_loading, index++,
					"");
			MenuItemCompat.setActionView(item,
					R.layout.actionbar_loading_progress);
			MenuItemCompat
					.setShowAsAction(
							item,
							MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
									| MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			MenuItemCompat.expandActionView(item);
		}
		if (mPlay) {
			MenuItem item = menu.add(Menu.NONE, R.id.action_play, index++,
					mContext.getResources().getString(R.string.action_play))
					.setIcon(R.drawable.ic_action_play);
			MenuItemCompat.setShowAsAction(item,
					MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		}
		if (mSearch) {
			MenuItem item = menu.add(Menu.NONE, R.id.action_search, index++,
					mContext.getResources().getString(R.string.action_search))
					.setIcon(R.drawable.ic_action_search);
			MenuItemCompat
					.setShowAsAction(
							item,
							MenuItemCompat.SHOW_AS_ACTION_ALWAYS
									| MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}
		if (mRoomCreate) {
			MenuItem item = menu
					.add(Menu.NONE,
							R.id.action_new,
							index++,
							mContext.getResources().getString(
									R.string.action_new_room)).setIcon(
							R.drawable.ic_action_new);
			MenuItemCompat.setShowAsAction(item,
					MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		}

	}

}
