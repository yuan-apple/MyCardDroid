/*
 *****************************************************************************
 * Copyright (C) 2005-2013 UCWEB Corporation. All Rights Reserved
 * File        : RoomInfoFragment.java
 * Description : 
 * Creation    : 2014年4月9日
 * Author      : mabin@ucweb.com
 * History     : 
 *               Creation, 2014年4月9日, mabin, Create the file
 ******************************************************************************
 **/
package org.mycard.fragment;

import org.mycard.R;
import org.mycard.data.ResourcesConstants;

import cn.garymb.ygodata.YGOGameOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author mabin
 * 
 */
public class RoomDetailFragment extends DialogFragment implements
		OnTouchListener, ResourcesConstants{

	private ViewGroup mContentView;
	@SuppressWarnings("unused")
	private ViewGroup mTitleGroup;

	private Activity mActivity;

	private YGOGameOptions mGameOptions;

	/**
	 * Create a new instance of WelfareDialogFragment, providing "num" as an
	 * argument.
	 */
	public static RoomDetailFragment newInstance(Bundle bundle) {
		RoomDetailFragment f = new RoomDetailFragment();

		// Supply num input as an argument.
		f.setArguments(bundle);

		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.DialogFragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGameOptions = getArguments().getParcelable(GAME_OPTIONS);

		setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		view.setOnTouchListener(this);
		super.onViewCreated(view, savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View contentView = LayoutInflater.from(mActivity).inflate(
				R.layout.room_detail_content, null);
		TextView cardLimitView = (TextView) contentView
				.findViewById(R.id.card_limit_text);
		cardLimitView.setText(mActivity.getResources().getString(
				R.string.card_limit,
				getResources().getStringArray(R.array.card_limit)[mGameOptions.mRule]));
		TextView duelModeView = (TextView) contentView
				.findViewById(R.id.duel_mode_text);
		duelModeView.setText(mActivity.getResources()
				.getString(
						R.string.duel_mode,
						mActivity.getResources().getStringArray(
								R.array.duel_mode)[mGameOptions.mMode]));
		TextView initLpText = (TextView) contentView
				.findViewById(R.id.init_lp_text);
		initLpText.setText(mActivity.getResources().getString(R.string.init_lp,
				mGameOptions.mStartLP));
		TextView initHandsText = (TextView) contentView
				.findViewById(R.id.init_hands_text);
		initHandsText.setText(mActivity.getResources().getString(R.string.init_hands, mGameOptions.mStartHand));
		
		TextView drawCountText = (TextView) contentView.findViewById(R.id.draw_count_text);
		drawCountText.setText(mActivity.getResources().getString(R.string.draw_count, mGameOptions.mDrawCount));
		
		AlertDialog dialog = new AlertDialog.Builder(mActivity)
				.setTitle(mGameOptions.mRoomName)
				.setView(contentView)
				.setPositiveButton(R.string.button_join, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						ComponentName component = new ComponentName("cn.garymb.ygomobile", "cn.garymb.ygomobile.YGOMobileActivity");
						intent.setComponent(component);
						intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						intent.putExtra(YGOGameOptions.YGO_GAME_OPTIONS_BUNDLE_KEY, mGameOptions);
						startActivity(intent);
					}
				})
				.setNegativeButton(R.string.button_cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).create();
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return mContentView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return true;
	}
}
