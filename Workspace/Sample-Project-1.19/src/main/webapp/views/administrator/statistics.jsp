<%--
 * action-2.jsp
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




	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.member.statistics" /></th>
		</tr>
		<tr>
			<td><spring:message code="administrator.avgMember" /></td>
			<td style="text-align: right">${averageMemberPerBrotherhood}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.minMember" /></td>
			<td style="text-align: right">${minMemberPerBrotherhood}</td>
		</tr>

		<tr>
			<td><spring:message code="administrator.maxMember" /></td>
			<td style="text-align: right">${maxMemberPerBrotherhood}</td>
		</tr>

		<tr>
			<td><spring:message code="administrator.stdevMember" /></td>
			<td style="text-align: right">${stdevMemberPerBrotherhood}</td>
		</tr>

		<tr>
			<td><spring:message code="administrator.acceptedMembers" /></td>
			<jstl:forEach var="member" items="${acceptedMembers}">
				<td style="text-align: right"><jstl:out
						value="${member.userAccount.username}" /></td>
				<br />
			</jstl:forEach>

		</tr>
	</table>

	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.brotherhood.statistics" /></th>
		</tr>
		<tr>
			<td><spring:message code="administrator.largestBrotherhood" /></td>
			<td style="text-align: right">${largestBrotherhood.title}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.smallestBrotherhood" /></td>
			<td style="text-align: right">${smallestBrotherhood.title}</td>
		</tr>


	</table>

	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.request.statistics" /></th>
		</tr>
		<tr>
			<td><spring:message code="administrator.requests.approved" /></td>
			<td style="text-align: right">${ratioApprovedRequests}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.requests.rejected" /></td>
			<td style="text-align: right">${ratioRejectedRequests}</td>
		</tr>

		<tr>
			<td><spring:message code="administrator.requests.pending" /></td>
			<td style="text-align: right">${ratioPendingRequests}</td>
		</tr>

		<tr>
			<td><spring:message
					code="administrator.requests.approved.procession" /></td>
			<jstl:forEach var="i" begin="0" end="${processions}">
				<td style="text-align: right">${ratioApprovedProcession[i]}</td>
			</jstl:forEach>

		</tr>
	</table>

	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.processions.statistics" /></th>
		</tr>

		<tr>
			<td><spring:message code="administrator.early.processions" /></td>
			<jstl:forEach var="p" items="${earlyProcessions}">
				<td style="text-align: right"><jstl:out value="${p.title}" /></td>
				<br />
			</jstl:forEach>

		</tr>
	</table>

	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.positions.statistics" /></th>
		</tr>

		<tr>
			<td><spring:message code="administrator.positions.histogram" /></td>
			<jstl:forEach var="x" begin="0" end="${positions}">
				<td style="text-align: right"><jstl:out value="${histogram[x]}" /></td>
			</jstl:forEach>


		</tr>
	</table>
</security:authorize>
