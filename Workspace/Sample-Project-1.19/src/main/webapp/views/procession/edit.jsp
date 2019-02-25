<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose>
	<jstl:when test="${isPrincipal }">
		<jstl:choose>
			<jstl:when test="${procession.id == 0 || procession.isDraft == true}">
		
				<form:form action="procession/edit.do" modelAttribute="procession">
					
					<form:label path="title">
						<spring:message code="procession.title" />
					</form:label>
					<form:textarea path="procession" />
					<form:errors cssClass="error" path="procession" />
					
					<form:label path="description">
						<spring:message code="procession.description" />
					</form:label>
					<form:textarea path="description" />
					<form:errors cssClass="error" path="description" />
					
					<form:label path="organisedMoment">
						<spring:message code="procession.organisedMoment" />
					</form:label>
					<form:textarea path="organisedMoment" />
					<form:errors cssClass="error" path="organisedMoment" />
					<br />
					<br />
		
				</form:form>
			</jstl:when>
			<jstl:otherwise>
				<h3>
					<spring:message code="procession.nopermission" />
				</h3>
			</jstl:otherwise>
		</jstl:choose>
	</jstl:when>
	<jstl:otherwise>
		<p>
			<spring:message code="procession.notAllowed" />
		</p>
	</jstl:otherwise>
</jstl:choose>