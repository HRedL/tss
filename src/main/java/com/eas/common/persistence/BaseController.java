/**
 * 
 */
package com.eas.common.persistence;

import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 控制器支持类
 * @author lilinzhen
 * @version 2018年3月4日
 */
public abstract class BaseController {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LogManager.getLogger(getClass());

	/**
	 * 从Session中获取当前登录的用户的ID
	 *
	 * @param session
	 * @return 如果Session中存在用户ID，则返回用户ID，否则返回null
	 */
	protected Integer getUidFromSession(HttpSession session) {
		//从Session中获取当前登录的用户ID
		Object uidObj = session.getAttribute("uid");
		//判断Session中的用户ID是否存在
		if(uidObj == null) {
			return null;
		} else {
			return Integer.valueOf(uidObj.toString());
		}
	}

}
