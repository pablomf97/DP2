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
	<jstl:if test="${permission == true}">

		<form:form action="zone/administrator/edit.do" modelAttribute="zone"
			id="zoneForm">

			<form:hidden path="id" />
			<form:hidden path="version" />

			<form:label path="name">
				<spring:message code="zone.name" />
			</form:label>
			<form:input path="name" />
			<form:errors cssClass="error" path="name" />
			<br />
			<br />

			<form:label path="pictures">
				<spring:message code="zone.pictures" />
			</form:label>
			<form:input path="pictures" />
			<form:errors cssClass="error" path="pictures" />
			<br />
			<br />

			<input type="submit" name="save"
				value="<spring:message code="zone.save"/>" />&nbsp;
		
		<jstl:if test="${zone.id != 0 and isSelected == false}">
				<input type="submit" name="delete"
					value="<spring:message code="zone.delete"/>" />&nbsp;
		
		</jstl:if>

			<input type="button" name="cancel"
				value="<spring:message code="zone.cancel"/>"
				onclick="javascript: relativeRedir('/zone/administrator/list.do');" />
			<br />
		</form:form>
	</jstl:if>
	
	<jstl:if test="${permission == false }">
		<spring:message code="zone.noPermission"/>
	</jstl:if>
</security:authorize>



