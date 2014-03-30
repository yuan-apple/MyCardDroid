package org.mycard;

import org.mycard.data.ResourcesConstants;
import org.mycard.fragment.CardDeckFragment;
import org.mycard.fragment.CardWikiFragment;
import org.mycard.fragment.ChatRoomFragment;
import org.mycard.fragment.FinalPhaseFragment;
import org.mycard.fragment.RoomFragment;
import org.mycard.fragment.TabFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

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
				selectItem(position);
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
	private TextView mMainTitle;
	private ListView mDrawerList;

	private String[] mDrawerItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initActionBar();

		initView();
		setTitle(R.string.mycard);
	}

	private void initView() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerItems = getResources().getStringArray(R.array.draw_items);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		View headerView = LayoutInflater.from(this).inflate(
				R.layout.drawer_header_view, null);
		mDrawerList.addHeaderView(headerView);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mDrawerItems));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	private void initActionBar() {
		// TODO Auto-generated method stub
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
		mDrawerList.setItemChecked(position, true);
		setTitle(mDrawerItems[position - 1]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}
}
