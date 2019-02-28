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

<security:authorize access="hasRole('BROTHERHOOD')">

	<jstl:choose>
		<jstl:when test="${permission}">
			<display:table style="width: 40%" class="displaytag"
				name="enrolments" requestURI="enrolment/brotherhood/list.do"
				id="enrolment">

				<display:column value="${enrolment.moment}"
					titleKey="enrolment.moment" sortable="true" />

				<display:column value="${enrolment.brotherhood.title}"
					titleKey="enrolment.brotherhood" sortable="true" />

				<jstl:choose>
					<jstl:when test="${language == 'es'}">
						<display:column value="${enrolment.position.name.get('Español')}"
							titleKey="enrolment.position" sortable="true" />
					</jstl:when>
					<jstl:otherwise>
						<display:column value="${enrolment.position.name.get('English')}"
							titleKey="enrolment.position" sortable="true" />
					</jstl:otherwise>
				</jstl:choose>

				<spring:message code="enrolment.not.accepted" var="message1" />
				<spring:message code="enrolment.accepted" var="message2" />
				<spring:message code="enrolment.pending" var="message3" />

				<jstl:choose>
					<jstl:when test="${enrolment.isOut == true}">
						<display:column value="${message1}" titleKey="enrolment.status" />
					</jstl:when>
					<jstl:when test="${enrolment.isOut == false}">
						<display:column value="${message2}" titleKey="enrolment.status" />
					</jstl:when>
					<jstl:otherwise>
						<display:column value="${message3}" titleKey="enrolment.status" />
					</jstl:otherwise>
				</jstl:choose>

				<spring:message code="enrollment.enroll" var="enroll" />
				<spring:message code="enrollment.unenroll" var="unenroll" />

				<display:column titleKey="enrolments.actions">
					<a
						href="enrolment/brotherhood/action.do?action=accept&enrolmentID=${enrolment.id}"><jstl:out
							value="${enroll}" /></a>
					<a
						href="enrolment/brotherhood/action.do?action=reject&enrolmentID=${enrolment.id}"><jstl:out
							value="${unenroll}" /></a>
				</display:column>
			</display:table>
		</jstl:when>
		<jstl:otherwise>
			<p>
				<jstl:out value="${errMsg.getMessage()}" />
			</p>
		</jstl:otherwise>
	</jstl:choose>

</security:authorize>