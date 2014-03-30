package org.mycard.data.wrapper;

import org.mycard.data.ResourcesConstants;

/**
 * @author mabin
 * 
 */
public interface IBaseWrapper extends ResourcesConstants {

	/**
	 * 
	 * @brief clear all data and force GC. Never use this object after recycle()
	 * @author: mabin
	 * 
	 */
	void recyle();

	/**
	 * 
	 * @brief get certain url to connect
	 * @author: mabin
	 * 
	 */
	String getUrl(int index);
}
