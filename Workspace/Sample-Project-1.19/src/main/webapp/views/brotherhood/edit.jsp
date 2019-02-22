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
<form:form action="brotherhood/edit.do" modelAttribute="brotherhoodForm"
	methodParam="post"
	onsubmit="javascript: return checkPhone('${confirmTelephone}');">

	<form:hidden path="id" />

	<security:authorize access="isAnonymous()">

		<form:label path="username">
			<spring:message code="actor.userAccount.username" />:
			</form:label>
		<form:input path="username" />
		<form:errors cssClass="error" path="username" />
		<br />

		<form:label path="password">
			<spring:message code="actor.userAccount.password" />:
			</form:label>
		<form:password path="password" />
		<form:errors cssClass="error" path="password" />
		<br />

		<form:label path="password2">
			<spring:message code="actor.userAccount.password2" />:
			</form:label>
		<form:password path="password2" />
		<form:errors cssClass="error" path="password2" />
		<br />
	</security:authorize>

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
	<form:input path="title" value="${brotherhood.title}" id="title" />
	<form:errors cssClass="error" path="title" />
	<br>

	<%-- 
TODO falta implementar el sistema de incluir múltiples imágenes
	<form:label path="pictures">
		<spring:message code="brotherhood.pictures" />:
		</form:label>
	<form:input path="pictures" value="${brotherhood.pictures}" />
	<form:errors cssClass="error" path="pictures" />
	<br> --%>
	
	<form:label path="title">
		<spring:message code="actor.check.law" />:
		</form:label>
	<form:checkbox path="checkBox" default="false" id="checkBox" />
	<spring:message cssClass="error" code="actor.check.law.error" />
	<br>

	<input type="submit" name="save" id="save"
		value='<spring:message code="actor.save"/>' onclick="checkPhone()" />
	<input type="button" name="cancel"
		value="<spring:message code="actor.cancel" />"
		onclick="javascript: relativeRedir('actor/display.do');" />
	<br />
</form:form>
