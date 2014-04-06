package org.mycard.common;

import org.mycard.R;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;

public class ActionBarCreator {
	
	private Context mContext;
	
	public ActionBarCreator(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	
	private boolean mLoading = false;
	
	private boolean mSearch = false;
	
	private boolean mRoomCreate = false;
	
	private boolean mSettings = true;
	
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
	
	public void createMenu(Menu menu) {
		int index= 0;
		menu.removeGroup(Menu.NONE);
		if (mSettings) {
			MenuItem item = menu.add(Menu.NONE, index++, Menu.NONE, R.string.action_settings);
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_NEVER);
		}
		if (mLoading) {
			MenuItem item = menu.add(Menu.NONE, index++, Menu.NONE, "");
			MenuItemCompat.setActionView(item, R.layout.actionbar_loading_progress);
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			MenuItemCompat.expandActionView(item);
		}
		if (mSearch) {
			MenuItem item = menu.add(Menu.NONE,index++, Menu.NONE, mContext.getResources().getString(R.string.action_search)).setIcon(R.drawable.ic_action_search);
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}
		if (mRoomCreate) {
			MenuItem item = menu.add(Menu.NONE,index++, Menu.NONE, mContext.getResources().getString(R.string.action_new_room)).setIcon(R.drawable.ic_action_new);
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
		}
		
	}

}
