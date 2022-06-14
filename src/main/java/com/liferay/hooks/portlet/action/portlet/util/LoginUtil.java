package com.liferay.hooks.portlet.action.portlet.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

public class LoginUtil extends PortalException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1316783713885397030L;

	public static void login(PortletRequest request, String loginAction) {
		
		String loginD = ParamUtil.get(request, "loginD", loginAction);
		
		request.setAttribute("loginD", loginD);
		
		
	}

}
