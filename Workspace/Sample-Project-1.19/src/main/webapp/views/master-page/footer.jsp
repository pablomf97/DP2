<%--
 * footer.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<<<<<<< HEAD
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
=======
<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
>>>>>>> Jes√∫s

<jsp:useBean id="date" class="java.util.Date" />
<div style="bottom: 0">
	<hr>

<<<<<<< HEAD
	<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" />
		Acme Madrug· Co., Inc.
	</b>
</div>
=======
<hr />

<div><a href="terms.do"><spring:message
								code="terms" /></a></div>
<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme Madrug· Co., Inc.</b>
>>>>>>> Jes√∫s
