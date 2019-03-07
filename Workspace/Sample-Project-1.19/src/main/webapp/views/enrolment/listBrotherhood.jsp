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

				<display:column titleKey="enrolment.moment" sortable="true">
					<jstl:out value="${enrolment.moment}" />
				</display:column>

				<display:column titleKey="enrolment.brotherhood" sortable="true">
					<jstl:out value="${enrolment.brotherhood.title}" />
				</display:column>

				<jstl:choose>
					<jstl:when test="${pageContext.response.locale.language == 'es'}">
						<display:column titleKey="enrolment.position" sortable="true">
							<jstl:out value="${enrolment.position.name.get('Espa�ol')}" />
						</display:column>
					</jstl:when>
					<jstl:otherwise>
						<display:column titleKey="enrolment.position" sortable="true">
							<jstl:out value="${enrolment.position.name.get('English')}" />
						</display:column>
					</jstl:otherwise>
				</jstl:choose>

				<spring:message code="enrolment.not.accepted" var="message1" />
				<spring:message code="enrolment.accepted" var="message2" />
				<spring:message code="enrolment.pending" var="message3" />

				<display:column titleKey="enrolment.status" sortable="true">
					<jstl:choose>
						<jstl:when test="${enrolment.isOut == true}">
							<jstl:out value="${message1}" />
						</jstl:when>
						<jstl:when test="${enrolment.isOut == false}">
							<jstl:out value="${message2}" />
						</jstl:when>
						<jstl:otherwise>
							<jstl:out value="${message3}" />
						</jstl:otherwise>
					</jstl:choose>
				</display:column>

				<spring:message code="enrollment.enroll" var="enroll" />
				<spring:message code="enrollment.unenroll" var="unenroll" />

				<display:column titleKey="enrolments.actions">

					<jstl:choose>
						<jstl:when test="${enrolment.isOut != false}">
							<jstl:if test="${!empty enrolment.brotherhood.zone}">
								<a
									href="enrolment/brotherhood/action.do?action=accept&enrolmentID=${enrolment.id}"><jstl:out
										value="${enroll}" /></a>
							</jstl:if>
						</jstl:when>
						<jstl:otherwise>
							<a
								href="enrolment/brotherhood/action.do?action=reject&enrolmentID=${enrolment.id}"><jstl:out
									value="${unenroll}" /></a>
						</jstl:otherwise>
					</jstl:choose>
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