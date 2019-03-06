<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<jstl:if test="${not empty messageCode}">
		<h4>
			<spring:message code="${messageCode}" />
		</h4>
	</jstl:if>

	<display:table name="messageBoxes" id="messagebox" requestURI="${requestURI}"
		pagesize="10">
		<display:column>
			<jstl:out value="${messagebox.name}"></jstl:out>
		</display:column>

		<display:column>
			<button
				onClick="window.location.href='messagebox/content.do?Id=${messagebox.id}'">
				<spring:message code="messagebox.seeM" />
			</button>
		</display:column>

		<display:column titleKey="messagebox.edit">
			<jstl:if test="${messagebox.isPredefined == false}">
				<button
					onClick="window.location.href='messagebox/edit.do?Id=${messagebox.id}'">
					<spring:message code="messagebox.edit" />
				</button>
			</jstl:if>
		</display:column>
		<display:column titleKey="messagebox.delete">
			<jstl:if test="${messagebox.isPredefined == false}">
				<button
					onClick="window.location.href='messagebox/delete.do?Id=${messagebox.id}'">
					<spring:message code="messagebox.delete" />
				</button>
			</jstl:if>
		</display:column>

	</display:table>
	<br />
	<display:table name="messages" id="row" pagesize="5"
		requestURI="${requestURI}">
<display:column>
			<jstl:out value="${row.subject}"></jstl:out>
		</display:column>
				<display:column>
			<jstl:out value="${row.sender.userAccount.username}"></jstl:out>
		</display:column>
				<display:column>
			<jstl:out value="${row.sentMoment}"></jstl:out>
		</display:column>
				<display:column>
			<jstl:out value="${row.priority}"></jstl:out>
		</display:column>
				<display:column>
			<jstl:out value="${row.tags}"></jstl:out>
		</display:column>
		<display:column>
			<button
				onClick="window.location.href='message/display.do?messageId=${row.id}'">
				<spring:message code="message.display" />
			</button>
		</display:column>
		<display:column>
			<button
				onClick="window.location.href='message/copy.do?messageId=${row.id}'">
				<spring:message code="message.edit" />
			</button>
		</display:column>
		<display:column>
			<button
				onClick="window.location.href='message/delete.do?messageId=${row.id}&boxId=${box.id}'">
				<spring:message code="message.delete" />
			</button>
		</display:column>
	</display:table> 

</div>
