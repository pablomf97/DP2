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

					<!-- Attributes-->

			<display:column property="title" titleKey="procession.title" sortable="true" />

			<display:column property="ticker" titleKey="procession.ticker" sortable="true" />

			<display:column property="description" titleKey="procession.description"/>

			<display:column property="organisedMoment" titleKey="procession.organisedMoment" sortable="true" />

			<!-- Action links -->

			<display:column>
				<a href="procession/display.do?processionId=${row.id}"> <spring:message
							code="procession.display" />
				</a>		
			</display:column>


	</display:table>
</security:authorize>