<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose>
	<jstl:when test="${march.id ==0}">
		<form:form modelAttribute="march" action="march/edit.do"
			id="form">
			<fieldset>
				<br>
				<form:hidden path="id" />
				<form:label path="procession">
					<spring:message code="march.procession" />:
				</form:label>
				<form:select name="procession" path="procession" style="width:300px;">
					<jstl:forEach var="p" items="${toApply}">
						<form:option value="${p}" label="${p.title}" />
					</jstl:forEach>
				</form:select>
				<br/><br/>					
			</fieldset>
			<br>
			<input type="submit" name="save" id="save" value="<spring:message code="march.save" />" />&nbsp; 
				<jstl:if test="${march.id != 0}">
					<input type="submit" name="delete" value="<spring:message code="march.delete" />"
						onclick="return confirm('<spring:message code="march.confirm.delete" />')" />&nbsp;
				</jstl:if>
				<jstl:if test="${march.id!=0}">
				<input type="button" name="cancel" value="<spring:message code="march.cancel" />"
					onclick="redirect: location.href = 'march/list.do';" />
				<br />
				</jstl:if>
				
		</form:form>
	</jstl:when>
	<jstl:otherwise>
		<p>
			<spring:message code="march.notAllowed" />
		</p>
	</jstl:otherwise>
</jstl:choose>