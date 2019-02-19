<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script>
	function checkPhone(msg) {
		var phone = document.getElementById("phoneNumber");
		var phonePattern = new RegExp(/^(((([+][1-9]{1}[0-9]{0,2}[\s]){0,1}([(][1-9]{1}[0-9]{0,2}[)][\s]){0,1})){0,1}([0-9]{4}){1}([0-9]{0,}))$/);

		if (phonePattern.test(phone)) {
			return true;
		} else {
			return confirm(msg);
		}
	}
</script>

<p>
	<spring:message code="actor.edit" />
</p>

<spring:message code="phone.confirmation" var="confirmTelephone" />
<form:form action="brotherhood/edit.do" modelAttribute="brotherhood"
	methodParam="post"
	onsubmit="javascript: return checkPhone('${confirmTelephone}');">

	<form:hidden path="id" />

	<jstl:if test="${brotherhood.id == 0}">

		<form:hidden path="userAccount.authorities[0].authority" />

		<form:label path="userAccount.username">
			<spring:message code="actor.userAccount.username" />:
			</form:label>
		<form:input path="userAccount.username" />
		<form:errors cssClass="error" path="userAccount.username" />
		<br />

		<form:label path="userAccount.password">
			<spring:message code="actor.userAccount.password" />:
			</form:label>
		<form:password path="userAccount.password" />
		<form:errors cssClass="error" path="userAccount.password" />
		<br />

	</jstl:if>

	<form:label path="surname">
		<spring:message code="actor.surname" />:
		</form:label>
	<form:input path="surname" value="${brotherhood.surname}" />
	<form:errors cssClass="error" path="surname" />
	<br>

	<form:label path="name">
		<spring:message code="actor.name" />:
		</form:label>
	<form:input path="name" value="${brotherhood.name}" />
	<form:errors cssClass="error" path="name" />
	<br>

	<form:label path="middleName">
		<spring:message code="actor.middlename" />:
		</form:label>
	<form:input path="middleName" value="${brotherhood.middleName}" />
	<form:errors cssClass="error" path="middleName" />
	<br>

	<form:label path="email">
		<spring:message code="actor.email" />:
		</form:label>
	<form:input path="email" value="${brotherhood.email}" id="email" />
	<form:errors cssClass="error" path="email" />
	<br>

	<form:label path="photo">
		<spring:message code="actor.photo" />:
		</form:label>
	<form:input path="photo" value="${brotherhood.photo}" />
	<form:errors cssClass="error" path="photo" />
	<br>

	<form:label path="phoneNumber">
		<spring:message code="actor.phone" />:
		</form:label>
	<form:input path="phoneNumber" value="${brotherhood.phoneNumber}"
		id="phoneNumber" />
	<form:errors cssClass="error" path="phoneNumber" />
	<br>

	<form:label path="address">
		<spring:message code="actor.address" />:
		</form:label>
	<form:input path="address" value="${brotherhood.address}" />
	<form:errors cssClass="error" path="address" />
	<br>
	
		<form:label path="title">
		<spring:message code="brotherhood.title" />:
		</form:label>
	<form:input path="title" value="${brotherhood.title}"
		id="title" />
	<form:errors cssClass="error" path="title" />
	<br>

	<!-- <form:label path="pictures">
		<spring:message code="brotherhood.pictures" />:
		</form:label>
	<form:input path="pictures" value="${brotherhood.pictures}" />
	<form:errors cssClass="error" path="pictures" />
	<br> -->

	<input type="submit" name="save" id="save"
		value='<spring:message code="actor.save"/>' onclick="checkPhone()" />
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: relativeRedir('actor/display.do');" />
	<br />
</form:form>
