package org.mycard;

import java.util.List;

import org.mycard.common.ActionBarCreator;
import org.mycard.core.UpdateController;
import org.mycard.data.ResourcesConstants;
import org.mycard.data.ServerInfo;
import org.mycard.fragment.BaseFragment.OnActionBarChangeCallback;
import org.mycard.fragment.HomePageFragment;
import org.mycard.fragment.CardWikiFragment;
import org.mycard.fragment.ChatRoomFragment;
import org.mycard.fragment.FinalPhaseFragment;
import org.mycard.fragment.DuelFragment;
import org.mycard.fragment.TabFragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements OnActionBarChangeCallback, Handler.Callback{

	public static class EventHandler extends Handler {
		public EventHandler(Callback back) {
			super(back);
		}
	}

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
			if (position != 0) {
				onActionBarChange(Constants.ACTION_BAR_CHANGE_TYPE_PAGE_CHANGE, position);
				selectItem(position);
			}
		}

	}

	private static final int DRAWER_ID_MY_CARD = 1;
	private static final int DRAWER_ID_DUEL = 2;
	private static final int DRAWER_ID_CARD_WIKI = 3;
	private static final int DRAWER_ID_CHAT_ROOM = 4;
	private static final int DRAWER_ID_FORUM_LINK = 5;
	private static final int DRAWER_ID_FINAL_PHASE = 6;
	
	private static final int MSG_ID_UPDATE_SERVER = 0;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mDrawerItems;
	
	private UpdateController mController;
	
	private ActionBarCreator mActionBarCreator;
	
	private EventHandler mHandler;
	
	private List<ServerInfo> mServerList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		initView();
		setTitle(R.string.mycard);
		mController = new UpdateController((StaticApplication) getApplication());
		mActionBarCreator = new ActionBarCreator(this);
		mHandler = new EventHandler(this);
		mController.asyncUpdateServer(mHandler.obtainMessage(MSG_ID_UPDATE_SERVER));
	}
	
	public UpdateController getController() {
		return mController;
	}

	private void initView() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
				R.drawable.ic_navigation_drawer,
				R.string.app_name,
				R.string.app_name
		);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		mDrawerItems = getResources().getStringArray(R.array.draw_items);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		View headerView = LayoutInflater.from(this).inflate(
				R.layout.drawer_header_view, null);
		mDrawerList.addHeaderView(headerView);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mDrawerItems));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		mDrawerList.setSelection(1);
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
		case DRAWER_ID_DUEL:
			fragment = new DuelFragment();
			break;
		case DRAWER_ID_MY_CARD:
			fragment = new HomePageFragment();
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
		mDrawerList.setItemChecked(position, true);
		setTitle(mDrawerItems[position - 1]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void onActionBarChange(int msgType, int action) {
		// TODO Auto-generated method stub
		switch (msgType) {
		case Constants.ACTION_BAR_CHANGE_TYPE_PAGE_CHANGE:
			if (action == DRAWER_ID_DUEL) {
				mActionBarCreator.setRoomCreate(true);
			} else {
				mActionBarCreator.setRoomCreate(false).setLoading(false).setPlay(false);
			}
			break;
		case Constants.ACTION_BAR_CHANGE_TYPE_DATA_LOADING:
			if (action == 0) {
				mActionBarCreator.setLoading(false).setRoomCreate(true).setPlay(true);
			} else {
				mActionBarCreator.setLoading(true).setRoomCreate(false).setPlay(false);
			}
		default:
			break;
		}
		supportInvalidateOptionsMenu();
	}
	
	public ServerInfo getServer() {
		return mServerList == null ? null : mServerList.get(0);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_ID_UPDATE_SERVER:
			mServerList = mController.getDataStore().getServerList();
			break;

		default:
			break;
		}
		return true;
	}
}
