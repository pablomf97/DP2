<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('MEMBER')">

	<display:table name="processions" id="row"
		requestURI="finder/member/list.do" pagesize="10" class="displaytag">

		<spring:message code="procession.ticker" var="ticker" />
		<display:column property="ticker" title="${ticker}"
			sortable="true" />
					<spring:message code="procession.title" var="title" />
		<display:column property="title" title="${title}"
			sortable="true" />


	</display:table>
</security:authorize>