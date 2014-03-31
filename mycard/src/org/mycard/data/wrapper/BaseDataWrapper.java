package org.mycard.data.wrapper;

import java.util.ArrayList;

import org.json.JSONArray;

/**
 * @author mabin
 * 
 */
public abstract class BaseDataWrapper implements IBaseWrapper {

	protected ArrayList<String> mUrls;
	protected boolean mResult;
	
	/**
	 * 
	 */
	public BaseDataWrapper() {
		mUrls = new ArrayList<String>();
	}

	
	public abstract void parse(JSONArray data);

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