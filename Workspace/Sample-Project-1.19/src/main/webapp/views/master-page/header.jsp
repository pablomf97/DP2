<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img style="height: 255px; padding-bottom: 12px"
		src="${banner}" alt="Acme Madrugá Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/edit.do?id=0"><spring:message
								code="master.page.administrator.register" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message
								code="master.page.administrator.dashboard" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="actor/display.do"><spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/edit.do"><spring:message
								code="master.page.profile.edit" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="j_spring_security_logout"><spring:message
						code="master.page.logout" /> </a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message
						code="master.page.signup" /></a>
				<ul>
					<li><a href="security/login.do"><spring:message
								code="master.page.login" /></a></li>
					<li><a href="brotherhood/edit.do?id=0"><spring:message
								code="master.page.register.brotherhood" /></a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>

	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

