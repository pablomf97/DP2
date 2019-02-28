<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose>
	<jstl:when test="${isPrincipal || procession.id == 0}">
		<jstl:choose>
			<jstl:when test="${procession.id == 0 || procession.isDraft == true}">
				<form:form action="procession/edit.do" modelAttribute="procession" id="form">
					<fieldset>
					<br>
					<form:hidden path="id" />
					<form:label path="title">
						<spring:message code="procession.title" />
					</form:label>
					<form:input path="title" />
					<form:errors cssClass="error" path="title" />
					<br><br>
					
					<form:label path="organisedMoment">
						<spring:message code="procession.organisedMoment" />
					</form:label>
					<form:input path="organisedMoment" />
					<form:errors cssClass="error" path="organisedMoment" />
					<br><br>
					
					<form:label path="description">
						<spring:message code="procession.description" />
					</form:label>
					<br>
					<form:textarea path="description" />
					<form:errors cssClass="error" path="description" />					
					<br />	<br />
					
					<form:label path="platforms">
						<spring:message code="procession.platform" />
					</form:label>
					<br>
				<form:select multiple="true" path="platforms" items="${platforms}" itemLabel="title" />				
				<br><br>
				</fieldset>
				<br />
							
				<jstl:if test="${procession.isDraft == true || procession.id == 0}">
					<input type="submit" id="saveFinal" name="saveFinal" value="<spring:message code="procession.save.final"/>" />&nbsp;
					<input type="submit" id="save" name="save" value="<spring:message code="procession.save"/>" />
				</jstl:if>
				<jstl:if test="${procession.id != 0}">
					<input type="submit" name="delete" value="<spring:message code="procession.delete"/>"
						onclick="return confirm('<spring:message code="procession.confirm.delete"/>')" />&nbsp;
				</jstl:if>
				<input type="button" name="cancel" value="<spring:message code="procession.cancel"/>"
					onclick="redirect: location.href = 'procession/member,brotherhood/list.do';" />
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