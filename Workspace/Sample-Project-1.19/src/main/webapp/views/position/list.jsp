
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

<security:authorize access="hasRole('ADMINISTRATOR')">

	<jstl:choose>
		<jstl:when test="${err}">
			<p>
				<jstl:out value="${errMsg.getMessage()}" />
			</p>
		</jstl:when>
		<jstl:otherwise>
			<display:table style="width: 40%" class="displaytag" name="positions"
				requestURI="position/administrator/list.do" id="position">

				<jstl:choose>
					<jstl:when test="${pageContext.response.locale.language == 'es'}">
						<jstl:set var="espanyol" value="${position.name.get('Espa�ol')}" />
						<display:column titleKey="position.name" sortable="true">
							<jstl:out value="${espanyol}" />
						</display:column>
					</jstl:when>
					<jstl:otherwise>
						<jstl:set var="english" value="${position.name.get('English')}" />
						<display:column titleKey="position.name" sortable="true">
							<jstl:out value="${english}" />
						</display:column>
					</jstl:otherwise>
				</jstl:choose>
				<display:column style="width: 15%">
					<a href="position/administrator/edit.do?positionID=${position.id}"><spring:message
							code="position.edit" /></a>
				</display:column>
			</display:table>
			<a href="position/administrator/create.do"><spring:message
					code="position.create" /></a>
		</jstl:otherwise>
	</jstl:choose>

</security:authorize>