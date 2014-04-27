package org.mycard.fragment;

import org.mycard.R;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class CardDetailFragment extends BaseFragment implements OnTouchListener {
	
	public static CardDetailFragment newInstance(Bundle param) {
		CardDetailFragment fragment = new CardDetailFragment();
		
		fragment.setArguments(param);
		return fragment;
	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.card_detail, null);
		view.setOnTouchListener(this);
		return view;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return true;
	}
}
