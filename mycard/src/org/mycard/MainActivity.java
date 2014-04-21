package org.mycard;

import org.mycard.common.ActionBarCreator;
import org.mycard.core.UpdateController;
import org.mycard.data.ResourcesConstants;
import org.mycard.fragment.BaseFragment.OnActionBarChangeCallback;
import org.mycard.fragment.CardDeckFragment;
import org.mycard.fragment.CardWikiFragment;
import org.mycard.fragment.ChatRoomFragment;
import org.mycard.fragment.FinalPhaseFragment;
import org.mycard.fragment.RoomFragment;
import org.mycard.fragment.TabFragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements
		OnActionBarChangeCallback {

	/**
	 * @author mabin
	 * 
	 */
	public class DrawerItemClickListener implements OnItemClickListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.widget.AdapterView.OnItemClickListener#onItemClick(android
		 * .widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if (position != -1) {
				onActionBarChange(Constants.ACTION_BAR_CHANGE_TYPE_PAGE_CHANGE,
						position);
				selectItem(position + 1);
			}
		}

	}

	private static final int DRAWER_ID_CARD_DECK = 1;
	private static final int DRAWER_ID_CARD_WIKI = 2;
	private static final int DRAWER_ID_ROOM_LIST = 3;
	private static final int DRAWER_ID_CHAT_ROOM = 4;
	private static final int DRAWER_ID_FORUM_LINK = 5;
	private static final int DRAWER_ID_FINAL_PHASE = 6;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mDrawerItems;

	private UpdateController mController;

	private ActionBarCreator mActionBarCreator;
	private LinearLayout drawLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		initView();
		setTitle(R.string.mycard);
		mController = new UpdateController((StaticApplication) getApplication());
		mActionBarCreator = new ActionBarCreator(this);
	}

	public UpdateController getController() {
		return mController;
	}

	private void initView() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, R.string.app_name,
				R.string.app_name);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		drawLayout = (LinearLayout) findViewById(R.id.left_layout);

		mDrawerItems = getResources().getStringArray(R.array.draw_items);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// View headerView = LayoutInflater.from(this).inflate(
		// R.layout.drawer_header_view, null);
		// mDrawerList.addHeaderView(headerView);
		// drawLayout.removeAllViews();
		// drawLayout.addView(headerView);
		// drawLayout.addView(mDrawerList);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mDrawerItems));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	private void initActionBar() {
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		mActionBarCreator.createMenu(menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		mActionBarCreator.createMenu(menu);
		// getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		// Create a new fragment and specify the planet to show based on
		// position
		Fragment fragment = null;
		switch (position) {
		case DRAWER_ID_ROOM_LIST:
			fragment = new RoomFragment();
			break;
		case DRAWER_ID_CARD_DECK:
			fragment = new CardDeckFragment();
			break;
		case DRAWER_ID_CARD_WIKI:
			fragment = new CardWikiFragment();
			break;
		case DRAWER_ID_CHAT_ROOM:
			fragment = new ChatRoomFragment();
			break;
		case DRAWER_ID_FORUM_LINK:
			Uri uri = Uri.parse(ResourcesConstants.FORUM_URL);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
			return;
		case DRAWER_ID_FINAL_PHASE:
			fragment = new FinalPhaseFragment();
			break;
		default:
			break;
		}
		Bundle args = new Bundle();
		args.putInt(TabFragment.ARG_ITEM_INDEX, position);
		fragment.setArguments(args);
		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position - 1, true);
		setTitle(mDrawerItems[position - 1]);
		// mDrawerLayout.closeDrawer(mDrawerList);
		mDrawerLayout.closeDrawer(drawLayout);
	}

	@Override
	public void onActionBarChange(int msgType, int action) {
		// TODO Auto-generated method stub
		switch (msgType) {
		case Constants.ACTION_BAR_CHANGE_TYPE_PAGE_CHANGE:
			if (action == DRAWER_ID_ROOM_LIST) {
				mActionBarCreator.setRoomCreate(true).setSearch(true);
			} else {
				mActionBarCreator.setRoomCreate(false).setSearch(false);
			}
			break;
		case Constants.ACTION_BAR_CHANGE_TYPE_DATA_LOADING:
			if (action == 0) {
				mActionBarCreator.setLoading(false).setRoomCreate(true)
						.setSearch(true);
			} else {
				mActionBarCreator.setLoading(true).setRoomCreate(false)
						.setSearch(false);
			}
		default:
			break;
		}
		supportInvalidateOptionsMenu();
	}
}
