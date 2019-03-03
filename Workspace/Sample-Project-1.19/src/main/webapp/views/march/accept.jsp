<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose>
	<jstl:when test="${isPrincipal && march.status == 'PENDING'}">
		<form:form action="march/acceptb.do" modelAttribute="march" id="form">
			<fieldset>
			<br>
			<form:hidden path="id" />
			<form:label path="row">
				<spring:message code="march.row" />
			</form:label>
			<form:textarea path="row" />
			<form:errors cssClass="error" path="row" />
			<br><br>
			<form:label path="column">
				<spring:message code="march.column" />
			</form:label>
			<form:textarea path="column" />
			<form:errors cssClass="error" path="column" />
			<br><br>
		</fieldset>
		<br />
		<input type="submit" name="accept" value="<spring:message code="march.accept"/>" />&nbsp;
		<input type="button" name="cancel" value="<spring:message code="march.cancel"/>"
			onclick="redirect: location.href = 'march/member,brotherhood/list.do';" />
		<br />
		</form:form>
	</jstl:when>
	<jstl:otherwise>
		<p>
			<spring:message code="march.notAllowed" />
		</p>
	</jstl:otherwise>
</jstl:choose>