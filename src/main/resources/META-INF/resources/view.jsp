<%@ include file="/init.jsp" %>

<portlet:renderURL var="editEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/view" />
</portlet:renderURL>

<p>Bienvenido! <%= request.getAttribute("loginScreen") %></p>