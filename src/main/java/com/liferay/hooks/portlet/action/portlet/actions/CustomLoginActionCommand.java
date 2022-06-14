package com.liferay.hooks.portlet.action.portlet.actions;

import com.liferay.hooks.portlet.action.constants.CustomLoginActionPortletKeys;
import com.liferay.hooks.portlet.action.portlet.util.LoginUtil;
import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManager;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
 immediate = true,
 property = {
 "javax.portlet.name=com_liferay_login_web_portlet_LoginPortlet",
 "javax.portlet.name="+ CustomLoginActionPortletKeys.CUSTOMLOGINACTION,
 "javax.portlet.name="+ LoginPortletKeys.FAST_LOGIN,
 "javax.portlet.name="+ LoginPortletKeys.LOGIN,
 "mvc.command.name=/login/login",
 "service.ranking:Integer=100"
 },
 service = MVCActionCommand.class
 )
public class CustomLoginActionCommand implements MVCActionCommand{
	
	private static final String regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
	Pattern pattern = Pattern.compile(regex);

	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String ScreenName = "";
		User user = null;
		String loginAction = ParamUtil.getString(actionRequest, "login");
		String password = ParamUtil.getString(actionRequest, "password");
		boolean rememberMe = ParamUtil.getBoolean(actionRequest, "rememberMe");
		String authType = themeDisplay.getCompany().getAuthType();
		try {
			HttpServletRequest httpServletRequest = _portal
					.getOriginalServletRequest(_portal.getHttpServletRequest(actionRequest));

			HttpServletResponse httpServletResponse = _portal.getHttpServletResponse(actionResponse);

			Matcher matcher = pattern.matcher(loginAction);
			if (matcher.matches()) {
				user = UserLocalServiceUtil.getUserByEmailAddress(themeDisplay.getCompanyId(), loginAction);
				ScreenName = user.getScreenName();
				_authenticatedSessionManager.login(httpServletRequest, httpServletResponse, ScreenName, password,
						rememberMe, authType);
				actionResponse.sendRedirect(themeDisplay.getPathMain());

			} else {
				mvcActionCommand.processAction(actionRequest, actionResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


@Reference(
target = "(component.name=com.liferay.login.web.internal.portlet.action.LoginMVCActionCommand)")
protected MVCActionCommand mvcActionCommand;

@Reference
private AuthenticatedSessionManager _authenticatedSessionManager;

@Reference
private Portal _portal;

}