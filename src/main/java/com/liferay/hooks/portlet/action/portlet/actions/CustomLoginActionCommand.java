package com.liferay.hooks.portlet.action.portlet.actions;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		 immediate = true,
		 property = {
		 "javax.portlet.name=com_liferay_login_web_portlet_LoginPortlet",
		 "mvc.command.name=/login/login",
		 "service.ranking:Integer=100"
		 },
		 service = MVCActionCommand.class
		 )

public class CustomLoginActionCommand extends BaseMVCActionCommand{

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		 System.out.println("EJECUTANDO EL LOGIN --------------------------------------------------------------");
		 ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		 Company company = themeDisplay.getCompany();
		 
		 String compan = company.getAuthType();

		 String screenName = themeDisplay.getUser().getScreenName();
		 String emailAddress = themeDisplay.getUser().getDisplayEmailAddress();
		 String password = themeDisplay.getUser().getPassword();

		 System.out.println("El COMPANY AUTH ES " +compan);

		 System.out.println("El emailaddresss del login " +emailAddress+ " ----------------------------------------------------");
		 

		// now read your parameters, e.g. like this:
		// long someParameter = ParamUtil.getLong(request, "someParameter");
		 

		 
		 mvcActionCommand.processAction(actionRequest, actionResponse);
	}
	
	 /*
	 * You still execute original logic
	 */
	 @Reference(target = "(&(mvc.command.name=/login/login)(javax.portlet.name=com_liferay_login_web_portlet_LoginPortlet))")
	 protected MVCActionCommand mvcActionCommand;

}

