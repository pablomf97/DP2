<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<!-- Listing grid -->
<jstl:choose>
	<jstl:when test="${permission }">

	<security:authorize access="hasRole('BROTHERHOOD')">
			<display:table pagesize="5" class="displaytag" name="platforms"
		requestURI="platform/list.do" id="row">
		<!-- Attributes-->
		
		<display:column property="title" titleKey="platform.title" sortable="true" />
		
		<display:column property="description" titleKey="platform.description"/>
	
		<!-- Action links -->
	
		<display:column>
			<a href="platform/display.do?platformId=${row.id}"> <spring:message
						code="platform.display" />
			</a>		
		</display:column>
		
		<display:column>
			<a href="platform/edit.do?platformId=${row.id}"> <spring:message
					code="platform.edit" />
			</a>
		</display:column>
	
	</display:table>
	<p>
		<a href="platform/create.do"><spring:message
				code="platform.create" /></a>
	</p>
	</security:authorize>
	</jstl:when>
	<jstl:otherwise>
		<p>
			<spring:message code="platform.notAllowed" />
		</p>
	</jstl:otherwise>
</jstl:choose>