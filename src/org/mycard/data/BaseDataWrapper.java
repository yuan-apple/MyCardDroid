package org.mycard.data;

import java.util.ArrayList;

import android.os.Bundle;

/**
 * @author mabin
 * 
 */
public abstract class BaseDataWrapper implements IBaseWrapper {

	protected int mItemID;

	protected int mChannelId;
	protected ArrayList<String> mUrls;
	protected Bundle mData;
	protected boolean mResult;

	/**
	 * 
	 */
	public BaseDataWrapper(int channelID, int itemID) {
		mChannelId = channelID;
		mItemID = itemID;
		mUrls = new ArrayList<String>();
	}

	public void setData(Bundle data) {
		mData = data;
	}
	
	protected void formatTestUrl() {
	}
	protected abstract void formatManufactureUrl();
	
	protected abstract void formatIntegrationTestUrl();
	
	protected abstract void formatInternalTestUrl();
	
	public Bundle getData() {
		return mData;
	}

	public int getItemIndex() {
		return mItemID;
	}
	
	@Override
	public void recyle() {
		// TODO Auto-generated method stub
	}

	public boolean getResult() {
		return mResult;
	}

	public void setResult(boolean result) {
		mResult = result;
	}
	
	/* (non-Javadoc)
	 * @see com.uc.addon.indoorsmanwelfare.model.data.wrapper.IBaseWrapper#getUrl(int)
	 */
	@Override
	public String getUrl(int index) {
		// TODO Auto-generated method stub
		if (index >= mUrls.size()) {
			return null;
		} else {
			return mUrls.get(index);
		}
	}
}