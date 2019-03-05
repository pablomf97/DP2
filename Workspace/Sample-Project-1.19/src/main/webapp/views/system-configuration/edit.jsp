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

	<form:form action="sysconfig/administrator/edit.do"
		modelAttribute="sysConfig" methodParam="post">

		<form:hidden path="id" />
		<form:hidden path="version" />

		<form:label path="systemName">
			<spring:message code="system.name" />:
		</form:label>
		<form:input path="systemName" value="${sysConfig.systemName}" />
		<form:errors cssClass="error" path="systemName" />
		<br>
		<br>

		<form:label path="messagePriority">
			<spring:message code="system.message.priority" />:
		</form:label>
		<form:input path="messagePriority"
			value="${sysConfig.messagePriority}" />
		<form:errors cssClass="error" path="messagePriority" />
		<br>
		<br>

		<form:label path="banner">
			<spring:message code="system.bannerURL" />:
		</form:label>
		<form:input path="banner" value="${sysConfig.banner}" size="50%" />
		<form:errors cssClass="error" path="banner" />
		<br>
		<br>

		<form:label path="timeResultsCached">
			<spring:message code="system.resultscached" />:
		</form:label>
		<form:input path="timeResultsCached"
			value="${sysConfig.timeResultsCached}" size="5%" />
		<form:errors cssClass="error" path="timeResultsCached"
			code="${timeErr}" />
		<br>
		<br>

		<form:label path="maxResults">
			<spring:message code="system.resultspersearch" />:
		</form:label>
		<form:input required="true" path="maxResults"
			value="${sysConfig.maxResults}" size="5%" />
		<form:errors cssClass="error" path="maxResults" code="${maxErr}" />
		<br>
		<br>

		<form:label path="countryCode">
			<spring:message code="system.countrycode" />:
		</form:label>
		<form:input path="countryCode" value="${sysConfig.countryCode}" />
		<form:errors cssClass="error" path="countryCode" />
		<br>
		<br>

		<form:label path="spamWords">
			<spring:message code="system.spamwords" />:
		</form:label>
		<form:input path="spamWords"
			placeholder="<spring:message code='system.lists.placeholder' />"
			value="${sysConfig.spamWords}" size="100%" />
		<form:errors cssClass="error" path="spamWords" />
		<br>
		<br>

		<form:label path="negativeWords">
			<spring:message code="system.negativewords" />:
		</form:label>
		<form:input path="negativeWords"
			placeholder="<spring:message code='system.lists.placeholder' />"
			value="${sysConfig.negativeWords}" size="100%" />
		<form:errors cssClass="error" path="negativeWords" />
		<br>
		<br>

		<form:label path="possitiveWords">
			<spring:message code="system.positivewords" />:
		</form:label>
		<form:input path="possitiveWords"
			placeholder="<spring:message code='system.lists.placeholder' />"
			value="${sysConfig.possitiveWords}" size="100%" />
		<form:errors cssClass="error" path="possitiveWords" />
		<br>
		<br>

		<p>
			<spring:message code="wel.name.es" />
		</p>
		<input type="text" name="nameES" id="nameES" size="100%"
			value="${sysConfig.welcomeMessage.get('Español')}"
			placeholder="<spring:message code='sysconfig.edit.welcome.message.es' />"
			required>

		<form:errors cssClass="error" path="welcomeMessage" />
		<br />
		<br />

		<p>
			<spring:message code="wel.name.en" />
		</p>
		<input type="text" name="nameEN" id="nameEN" size="100%"
			value="${sysConfig.welcomeMessage.get('English')}"
			placeholder="<spring:message code='sysconfig.edit.welcome.message.en' />"
			required>
		<form:errors cssClass="error" path="welcomeMessage" />
		<br />
		<br />

		<input type="submit" name="save" id="save"
			value='<spring:message code="system.save"/>' />
		<input type="button" name="cancel"
			value="<spring:message code="system.cancel" />"
			onclick="javascript: relativeRedir('sysconfig/administrator/display.do');" />
	</form:form>

</security:authorize>