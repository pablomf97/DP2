<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="messagebox/edit.do" modelAttribute="messageBox">

	<form:hidden path="id" />

	<h3>
		<spring:message code="messagebox.header" />
	</h3>
	<jstl:if test="${not empty messageCode}">
		<h4>
			<spring:message code="${messageCode}" />
		</h4>
	</jstl:if>
	<spring:message code="messagebox.placeholder.name"
		var="placeholderdescription" />
	<form:label path="name">
		<spring:message code="messagebox.name" />*: </form:label>
	<form:input path="name" placeholder="${placeholderdescription}" />
	<form:errors path="name" cssClass="error" />
	<jstl:if test="${not empty uniqueBox}">
		<a class="error"><spring:message code="${uniqueBox}" /></a>
	</jstl:if>
	<br />


	<input type="submit" name="save"
		value="<spring:message code="messagebox.save"/>" />


</form:form>
