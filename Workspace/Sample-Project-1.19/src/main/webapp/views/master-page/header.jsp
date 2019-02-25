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
						code="master.page.administrator.position" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="position/administrator/list.do"><spring:message
								code="master.page.administrator.list.position" /></a></li>
					<li><a href="position/administrator/create.do"><spring:message
								code="master.page.administrator.create.position" /></a></li>
				</ul></li>
			<li><a class="fNiv"><spring:message
						code="master.page.brotherhood.system" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sysconfig/administrator/display.do"><spring:message
								code="master.page.brotherhood.sysconfig.manage" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('MEMBER')">
			<li><a class="fNiv"><spring:message
						code="master.page.member" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="enrolment/member/list.do"><spring:message
								code="master.page.member.enrollments" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message
						code="master.page.brotherhood.enrolments" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="enrolment/brotherhood/list.do"><spring:message
								code="master.page.brotherhood.enrolments.manage" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/action-1.do"><spring:message
								code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message
								code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message
								code="master.page.profile.action.3" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

