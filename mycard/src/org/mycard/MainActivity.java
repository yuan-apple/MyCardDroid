package org.mycard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mycard.common.ActionBarController;
import org.mycard.common.ActionBarCreator;
import org.mycard.core.Controller;
import org.mycard.core.UpdateController;
import org.mycard.data.Model;
import org.mycard.data.ResourcesConstants;
import org.mycard.data.ServerInfo;
import org.mycard.fragment.BaseFragment.OnActionBarChangeCallback;
import org.mycard.fragment.BaseFragment;
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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		OnActionBarChangeCallback, Handler.Callback {

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
			if (position != -1) {
				onActionBarChange(Constants.ACTION_BAR_CHANGE_TYPE_PAGE_CHANGE,
						position);
				selectItem(position + 1);
			}
		}

	}

	private static final String IMAGE_TAG = "image";
	private static final String TEXT_TAG = "text";

	private static final int DRAWER_ID_MY_CARD = 1;
	private static final int DRAWER_ID_DUEL = 2;
	private static final int DRAWER_ID_CARD_WIKI = 3;
	private static final int DRAWER_ID_CHAT_ROOM = 4;
	private static final int DRAWER_ID_FORUM_LINK = 5;
	private static final int DRAWER_ID_FINAL_PHASE = 6;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mDrawerItems;

	private Integer[] imageArray = { R.drawable.ic_drawer_home,
			R.drawable.ic_drawer_duel, R.drawable.ic_drawer_card_wiki,
			R.drawable.ic_drawer_chat, R.drawable.ic_drawer_forum };
	private int[] viewTo = { R.id.drawer_item_image, R.id.drawer_item_text };
	private String[] dataFrom = { IMAGE_TAG, TEXT_TAG };

	private List<Map<String, Object>> mDrawerListData = new ArrayList<Map<String, Object>>();

	private Controller mController;

	private ActionBarCreator mActionBarCreator;
	
	private ActionBarController mActionBarController;

	private EventHandler mHandler;

	private List<ServerInfo> mServerList;
	
	private LinearLayout drawLayout;
	
	private TextView mUserStatusDes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActionBar();
		initView();
		setTitle(R.string.mycard);
		mController = Controller.peekInstance();
		mActionBarCreator = new ActionBarCreator(this);
		mActionBarController = new ActionBarController();
		mHandler = new EventHandler(this);
		mController.asyncUpdateServer(mHandler
				.obtainMessage(Constants.MSG_ID_UPDATE_SERVER));
	}

	private void initView() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, R.string.app_name,
				R.string.app_name);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		mUserStatusDes = (TextView) findViewById(R.id.user_status_des_text);
		mUserStatusDes.setText(R.string.login_sign_up);

		mDrawerItems = getResources().getStringArray(R.array.draw_items);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		int size = mDrawerItems.length;
		for (int i = 0; i < size; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put(IMAGE_TAG, imageArray[i]);
			item.put(TEXT_TAG, mDrawerItems[i]);
			mDrawerListData.add(item);
		}

		mDrawerList.setAdapter(new SimpleAdapter(this, mDrawerListData,
				R.layout.drawer_list_item, dataFrom, viewTo));
		drawLayout = (LinearLayout) findViewById(R.id.left_layout);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		selectItem(1);
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
		return mActionBarController.handleAction(item.getItemId());
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
		args.putString(BaseFragment.ARG_ITEM_TITLE, mDrawerItems[position - 1]);
		fragment.setArguments(args);
		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.replace(R.id.content_frame, fragment).commit();
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
			if (action == DRAWER_ID_DUEL) {
				mActionBarCreator.setRoomCreate(true);
			} else {
				mActionBarCreator.setRoomCreate(false).setLoading(false)
						.setPlay(false);
			}
			break;
		case Constants.ACTION_BAR_CHANGE_TYPE_DATA_LOADING:
			if (action == 0) {
				mActionBarCreator.setLoading(false).setRoomCreate(true)
						.setPlay(true);
			} else {
				mActionBarCreator.setLoading(true).setRoomCreate(false)
						.setPlay(false);
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
		case Constants.MSG_ID_UPDATE_SERVER:
			mServerList = Model.peekInstance().getServerList();
			break;

		default:
			break;
		}
		return true;
	}
	
	public void registerForActionNew(Handler h) {
		mActionBarController.registerForActionNew(h);
	}
	
	public void unregisterForActionNew(Handler h) {
		mActionBarController.unregisterForActionNew(h);
	}
	
	public void registerForActionPlay(Handler h) {
		mActionBarController.registerForActionPlay(h);
	}
	
	public void unregisterForActionPlay(Handler h) {
		mActionBarController.unregisterForActionPlay(h);
	}
}
