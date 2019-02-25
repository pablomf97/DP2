<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose>
	<jstl:when test="${isPrincipal }">
		<form:form modelAttribute="platform" action="platform/edit.do"
			id="form">
			<fieldset>
				<br>
				<form:hidden path="id" />
				<form:label path="title">
					<spring:message code="platform.title" />:
				</form:label>
				<form:input path="title"  />
				<form:errors cssClass="error" path="title" />
				<br/><br/>		
				
				<form:label path="pictures">
					<spring:message code="platform.pictures" />:
				</form:label>
				<form:input path="pictures"  />
				<form:errors cssClass="error" path="pictures" />
				<br/><br />
				
				<form:label path="description">
					<spring:message code="platform.description" />:
				</form:label>
				<form:textarea path="description"  />
				<form:errors cssClass="error" path="description" />
				<br />
				<br />
			
			</fieldset>
			<br>
			<input type="submit" name="save" id="save" value="<spring:message code="platform.save" />" />&nbsp; 
				<jstl:if test="${platform.id != 0}">
					<input type="submit" name="delete" value="<spring:message code="platform.delete" />"
						onclick="return confirm('<spring:message code="platform.confirm.delete" />')" />&nbsp;
				</jstl:if>
				<jstl:if test="${platform.id!=0}">
				<input type="button" name="cancel" value="<spring:message code="platform.cancel" />"
					onclick="redirect: location.href = 'platform/list.do';" />
				<br />
				</jstl:if>
				
		</form:form>
	</jstl:when>
	<jstl:otherwise>
		<p>
			<spring:message code="platform.notAllowed" />
		</p>
	</jstl:otherwise>
</jstl:choose>