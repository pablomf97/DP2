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

	<form:form action="position/administrator/edit.do"
		modelAttribute="position" id="form1">

		<form:hidden path="id" />

		<p>
			<spring:message code="position.name.es" />
		</p>
		<input type="text" name="nameES" id="nameES"
			value="${position.name.get('Español')}"
			placeholder="<spring:message code='position.edit.placeholder.es' />"
			required>

		<form:errors cssClass="error" path="name" />
		<br />
		<br />

		<p>
			<spring:message code="position.name.en" />
		</p>
		<input type="text" name="nameEN" id="nameEN"
			value="${position.name.get('English')}"
			placeholder="<spring:message code='position.edit.placeholder.en' />"
			required>
		<form:errors cssClass="error" path="name" />
		<br />
		<br />

		<input type="submit" id="submit" name="save"
			value="<spring:message code='position.save' />">
	</form:form>

	<script>
		$('#form1 input[type=text]')
				.on(
						'change invalid',
						function() {
							var textfield = $(this).get(0);

							// 'setCustomValidity not only sets the message, but also marks
							// the field as invalid. In order to see whether the field really is
							// invalid, we have to remove the message first
							textfield.setCustomValidity('');

							if (!textfield.validity.valid) {
								textfield
										.setCustomValidity('<spring:message code='not.blank' />');
							}
						});
	</script>
</security:authorize>