<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${possible == false && mensaje.id==0 && (!broadcast)}">
	<jstl:if test="${(mensaje.id == 0) && (!broadcast)}">
		<form:form action="message/actor/edit.do" modelAttribute="mensaje">

			<form:label path="recipient">
				<spring:message code="message.recipient.userAccount" />:
	</form:label>
			<form:select path="recipient">
				<form:option label="-----" value="0" />
				<form:options items="${recipients}" itemLabel="userAccount.username"
					itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="recipient" /><br /><br />

			<acme:textbox code="message.subject" path="subject"/><br><br>
			
			<acme:textbox code="message.body" path="body"/><br><br>

			<form:label path="priority">
				<spring:message code="message.priority" />
			</form:label>
			<form:radiobutton path="priority" value="HIGH" />
			<spring:message code="message.priority.high" />
			<form:radiobutton path="priority" value="NEUTRAL" checked="checked" />
			<spring:message code="message.priority.neutral" />
			<form:radiobutton path="priority" value="LOW" />
			<spring:message code="message.priority.low" />
			<br />
			<br />

			<jstl:if test="${mensaje.id == 0}">
				<acme:submit code="message.send" name="save"/>&nbsp;
  			</jstl:if>

			<jstl:if test="${(mensaje.id == 0) && (broadcast)}">
				<acme:submit code="message.broadcast" name="save"/>&nbsp;
			</jstl:if>

			<acme:cancel code="message.cancel" url="messagebox/list.do"/><br/><br/>
		</form:form>
	</jstl:if>
</jstl:if>