<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<security:authorize access="hasRole('BROTHERHOOD')">

	<table class="displayStyle">
		<tr>
			<td><strong> <spring:message
						code="procession.title" /> :
			</strong></td>
			<td><jstl:out value="${procession.title}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="procession.ticker" />
					:
			</strong></td>
			<td><jstl:out value="${procession.ticker}">
				</jstl:out></td>
		</tr>
		
		<tr>
			<td><strong> <spring:message code="procession.description" />
					:
			</strong></td>
			<td><jstl:out value="${procession.description}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message
						code="procession.organisedMoment" /> :
			</strong></td>
			<td><jstl:out value="${procession.organisedMoment}">
				</jstl:out></td>

		</tr>
		
				<tr>
			<td><strong> <spring:message
						code="procession.isDraft" /> :
			</strong></td>
			<td><jstl:out value="${procession.isDraft}">
				</jstl:out></td>

		</tr>
		
	</table>
	<div></div>
	<p>
	<strong> <spring:message code="procession.platform" />	</strong>
	</p>
	
		<input type="button" name="back"
		value="<spring:message code="procession.back" />"
		onclick="window.history.back()" />
		
		<jstl:if test="${isPrincipal}">
			<input type="button" name="edit"
			value="<spring:message code="procession.edit" />"
			onclick="redirect: location.href = 'procession/edit.do?processionId=${procession.id}';" />
		</jstl:if>

</security:authorize>

<security:authorize access="hasRole('MEMBER')">

	<jstl:choose>
		<jstl:when test="${!procession.isDraft}">
			<table class="displayStyle">
				<tr>
					<td><strong> <spring:message
								code="procession.title" /> :
					</strong></td>
					<td><jstl:out value="${procession.title}">
						</jstl:out></td>
				</tr>
		
				<tr>
					<td><strong> <spring:message code="procession.ticker" />
							:
					</strong></td>
					<td><jstl:out value="${procession.ticker}">
						</jstl:out></td>
				</tr>
				
				<tr>
					<td><strong> <spring:message code="procession.description" />
							:
					</strong></td>
					<td><jstl:out value="${procession.description}">
						</jstl:out></td>
				</tr>
		
				<tr>
					<td><strong> <spring:message
								code="procession.organisedMoment" /> :
					</strong></td>
					<td><jstl:out value="${procession.organisedMoment}">
						</jstl:out></td>
		
				</tr>
				
			</table>
		</jstl:when>
		<jstl:otherwise>
			<p>
				<spring:message code="procession.notAllowed" />
			</p>
		</jstl:otherwise>
	</jstl:choose>
	<div></div>
	
		<input type="button" name="back"
		value="<spring:message code="procession.back" />"
		onclick="window.history.back()" />

</security:authorize>