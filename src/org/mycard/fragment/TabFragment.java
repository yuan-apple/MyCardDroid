package org.mycard.fragment;

import org.mycard.R;
import org.mycard.widget.SyncHorizontalScrollView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * @author mabin
 * 
 */
public abstract class TabFragment extends Fragment {

	public static final String ARG_ITEM_INDEX = "pagefragment.number";
	protected ImageView iv_nav_indicator;
	protected ImageView iv_nav_left;
	protected ImageView iv_nav_right;
	protected ViewPager mViewPager;
	private int indicatorWidth;
	private SyncHorizontalScrollView mHsv;
	private RelativeLayout rl_nav;
	private RadioGroup rg_nav_content;
	private FragmentPagerAdapter mAdapter;
	private LayoutInflater mInflater;
	private int currentIndicatorLeft;
	
	protected int mTabCount;

	private TextView mTabTitle;

	protected Activity mAcitivity;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mAcitivity = activity;
		mInflater = LayoutInflater.from(mAcitivity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View contentView = inflater.inflate(R.layout.fragment_base, null);

		mTabTitle = (TextView) contentView.findViewById(R.id.main_title_text);
		rl_nav = (RelativeLayout) contentView.findViewById(R.id.rl_nav);

		mHsv = (SyncHorizontalScrollView) contentView.findViewById(R.id.mHsv);

		rg_nav_content = (RadioGroup) contentView
				.findViewById(R.id.rg_nav_content);

		iv_nav_indicator = (ImageView) contentView
				.findViewById(R.id.iv_nav_indicator);
		iv_nav_left = (ImageView) contentView.findViewById(R.id.iv_nav_left);
		iv_nav_right = (ImageView) contentView.findViewById(R.id.iv_nav_right);

		mViewPager = (ViewPager) contentView.findViewById(R.id.mViewPager);
		initView();
		setUpListener();
		return contentView;
	}

	private void initView() {

		DisplayMetrics dm = new DisplayMetrics();
		mAcitivity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		indicatorWidth = dm.widthPixels / (mTabCount > 4 ? 4 : mTabCount);

		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
		cursor_Params.width = indicatorWidth;// 初始化滑动下标的宽
		iv_nav_indicator.setLayoutParams(cursor_Params);

		mHsv.setSomeParam(rl_nav, iv_nav_left, iv_nav_right, mAcitivity);

		initTab();

		mAdapter = initFragmentAdapter();
		mViewPager.setAdapter(mAdapter);
	}

	private void setUpListener() {

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				if (rg_nav_content != null
						&& rg_nav_content.getChildCount() > position) {
					((RadioButton) rg_nav_content.getChildAt(position))
							.performClick();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		rg_nav_content
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						if (rg_nav_content.getChildAt(checkedId) != null) {

							TranslateAnimation animation = new TranslateAnimation(
									currentIndicatorLeft,
									((RadioButton) rg_nav_content
											.getChildAt(checkedId)).getLeft(),
									0f, 0f);
							animation.setInterpolator(new LinearInterpolator());
							animation.setDuration(100);
							animation.setFillAfter(true);

							// 执行位移动画
							iv_nav_indicator.startAnimation(animation);

							mViewPager.setCurrentItem(checkedId); // ViewPager
																	// 跟随一起 切换

							// 记录当前 下标的距最左侧的 距离
							currentIndicatorLeft = ((RadioButton) rg_nav_content
									.getChildAt(checkedId)).getLeft();

							mHsv.smoothScrollTo(
									(checkedId > 1 ? ((RadioButton) rg_nav_content
											.getChildAt(checkedId)).getLeft()
											: 0)
											- ((RadioButton) rg_nav_content
													.getChildAt(2)).getLeft(),
									0);
						}
					}
				});
	}

	protected abstract FragmentPagerAdapter initFragmentAdapter();

	protected void initTab() {
		rg_nav_content.removeAllViews();
	}

	protected void addTab(int index, String text, int totalLength) {
		RadioButton rb = (RadioButton) mInflater.inflate(
				R.layout.nav_radiogroup_item, null);
		rb.setId(index);
		rb.setText(text);
		rb.setLayoutParams(new LayoutParams(indicatorWidth,
				LayoutParams.MATCH_PARENT));
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(indicatorWidth,
				LayoutParams.MATCH_PARENT);
		lp.weight = (float) (1.0 / totalLength);
		rg_nav_content.addView(rb);
	}

	protected void setTitle(String text) {
		mTabTitle.setText(text);
	}

}
