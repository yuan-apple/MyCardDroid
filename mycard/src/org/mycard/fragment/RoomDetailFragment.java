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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author mabin
 * 
 */
public class RoomDetailFragment extends DialogFragment implements
		OnTouchListener {

	public static final String ROOM_INFO_NAME = "room.info.name";
	public static final String ROOM_INFO_RULE = "room.info.rule";
	public static final String ROOM_INFO_MODE = "room.info.mode";
	public static final String ROOM_INFO_LIFEPOINTS = "room.info.lp";
	public static final String ROOM_INFO_INITIALHAND = "room.info.inithand";
	public static final String ROOM_INFO_DRAWCARDS = "room.info.drawcards";

	private ViewGroup mContentView;
	@SuppressWarnings("unused")
	private ViewGroup mTitleGroup;
	private OnClickListener mOnClickListener;

	private Activity mActivity;
	private String mTitle;

	private int mRule;
	private int mMode;
	private int mLifePoints;
	private int mInitalHands;
	private int mDrawCards;

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

	public void setOnclickListener(OnClickListener l) {
		mOnClickListener = l;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTitle = getArguments().getString(ROOM_INFO_NAME);
		mRule = getArguments().getInt(ROOM_INFO_RULE);
		mMode = getArguments().getInt(ROOM_INFO_MODE);
		mLifePoints = getArguments().getInt(ROOM_INFO_LIFEPOINTS);
		mInitalHands = getArguments().getInt(ROOM_INFO_INITIALHAND);
		mDrawCards = getArguments().getInt(ROOM_INFO_DRAWCARDS);

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
				getResources().getStringArray(R.array.card_limit)[mRule]));
		TextView duelModeView = (TextView) contentView
				.findViewById(R.id.duel_mode_text);
		duelModeView.setText(mActivity.getResources()
				.getString(
						R.string.duel_mode,
						mActivity.getResources().getStringArray(
								R.array.duel_mode)[mMode]));
		TextView initLpText = (TextView) contentView
				.findViewById(R.id.init_lp_text);
		initLpText.setText(mActivity.getResources().getString(R.string.init_lp,
				mLifePoints));
		TextView initHandsText = (TextView) contentView
				.findViewById(R.id.init_hands_text);
		initHandsText.setText(mActivity.getResources().getString(R.string.init_hands, mInitalHands));
		
		TextView drawCountText = (TextView) contentView.findViewById(R.id.draw_count_text);
		drawCountText.setText(mActivity.getResources().getString(R.string.draw_count, mDrawCards));
		
		AlertDialog dialog = new AlertDialog.Builder(mActivity)
				.setTitle(mTitle)
				.setView(contentView)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.setNegativeButton("cancel",
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
