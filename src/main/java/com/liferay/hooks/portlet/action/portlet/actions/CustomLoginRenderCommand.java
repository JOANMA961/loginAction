package com.liferay.hooks.portlet.action.portlet.actions;

import com.liferay.hooks.portlet.action.constants.CustomLoginActionPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		 immediate = true,
		 property = {
		 "javax.portlet.name=" + CustomLoginActionPortletKeys.CUSTOMLOGINACTION,
		 "mvc.command.name=/",
		 "service.ranking:Integer=100"
		 },
		 service = MVCRenderCommand.class
		 )
public class CustomLoginRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest request, RenderResponse renderResponse) throws PortletException {
		ThemeDisplay theme = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		String email = theme.getUser().getEmailAddress();
		request.setAttribute("loginScreen", email);

		return "/view.jsp";
		
	}
//	@Reference(target = "(component.name=com.liferay.login.web.internal.portlet.action.LoginMVCRenderCommand)")
//	protected MVCRenderCommand mvcRenderCommand;
}

