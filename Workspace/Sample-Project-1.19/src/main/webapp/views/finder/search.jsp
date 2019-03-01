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



<security:authorize access="hasRole('MEMBER')">

	<form:form action="finder/member/search.do" modelAttribute="finder">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="searchMoment" value="01/01/2001 00:00" />

		<form:label path="keyWord">
			<spring:message code="finder.keyWord" />:
		</form:label>
		<form:input path="keyWord" />
		<form:errors cssClass="error" path="keyWord" />
		<br />
		<br />

		<form:label path="minimumMoment">
			<spring:message code="finder.minimumMoment" />:
		</form:label>
		<form:input path="minimumMoment" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="minimumMoment" />
		
		&#160;

		<form:label path="maximumMoment">
			<spring:message code="finder.maximumMoment" />:
		</form:label>
		<form:input path="maximumMoment" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="maximumMoment" />
		<br />
		<br />

		<form:label path="area">
			<spring:message code="finder.area" />:
		</form:label>
		<form:input path="area" />
		<form:errors cssClass="error" path="area" />
		<br />
		<br />

		<input type="submit" name="save" id="save"
			value="<spring:message code="finder.showResults" />" />
		
	&#160;
		<jstl:if test="${finder.id!=0}">
			<input type="submit" name="delete" id="delete"
				value='<spring:message code="finder.delete"/>' />
		</jstl:if>

	</form:form>
</security:authorize>