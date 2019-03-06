<%--
 * action-2.jsp
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




	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.member.statistics" /></th>
		</tr>
		<tr>
			<td><spring:message code="administrator.avgMember" /></td>
			<td style="text-align: right">${averageMemberPerBrotherhood}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.minMember" /></td>
			<td style="text-align: right">${minMemberPerBrotherhood}</td>
		</tr>

		<tr>
			<td><spring:message code="administrator.maxMember" /></td>
			<td style="text-align: right">${maxMemberPerBrotherhood}</td>
		</tr>

		<tr>
			<td><spring:message code="administrator.stdevMember" /></td>
			<td style="text-align: right">${stdevMemberPerBrotherhood}</td>
		</tr>
<jstl:if test="${not empty acceptedMembers}">
		<tr>
			<td><spring:message code="administrator.acceptedMembers" /></td>
			<jstl:forEach var="member" items="${acceptedMembers}">
				<td style="text-align: right"><jstl:out
						value="${member.userAccount.username}" /></td>
				<br />
			</jstl:forEach>

		</tr>
		</jstl:if>
	</table>

	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.brotherhood.statistics" /></th>
		</tr>
		<tr>
			<td><spring:message code="administrator.largestBrotherhood" /></td>
			<td style="text-align: right">${largestBrotherhood.title}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.smallestBrotherhood" /></td>
			<td style="text-align: right">${smallestBrotherhood.title}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.maxBrotherhoodPerArea" /></td>
			<td style="text-align: right">${maxBrotherhoodPerArea}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.minBrotherhoodPerArea" /></td>
			<td style="text-align: right">${minBrotherhoodPerArea}</td>
		</tr>

		<tr>
			<td><spring:message
					code="administrator.ratioBrotherhoodsPerArea" /></td>
			<td style="text-align: right">${ratioBrotherhoodsPerArea}</td>
		</tr>

		<tr>
			<td><spring:message
					code="administrator.countBrotherhoodsPerArea" /></td>
			<td style="text-align: right">${countBrotherhoodsPerArea}</td>
		</tr>

		<tr>
			<td><spring:message
					code="administrator.stdevBrotherhoodPerArea" /></td>
			<td style="text-align: right">${stdevBrotherhoodPerArea}</td>
		</tr>
	</table>

	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.request.statistics" /></th>
		</tr>
		<tr>
			<td><spring:message code="administrator.requests.approved" /></td>
			<td style="text-align: right">${ratioApprovedRequests}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.requests.rejected" /></td>
			<td style="text-align: right">${ratioRejectedRequests}</td>
		</tr>

		<tr>
			<td><spring:message code="administrator.requests.pending" /></td>
			<td style="text-align: right">${ratioPendingRequests}</td>
		</tr>
<jstl:if test="${not empty processions}">
		<tr>
			<td><spring:message
					code="administrator.requests.approved.procession" /></td>
			<jstl:forEach var="i" begin="0" end="${processions}">
				<td style="text-align: right">${ratioApprovedProcession[i]}</td>
			</jstl:forEach>

		</tr>
		
		
		<tr>
			<td><spring:message
					code="administrator.requests.rejected.procession" /></td>
			<jstl:forEach var="i" begin="0" end="${processions}">
				<td style="text-align: right">${ratioRejectedInAProcession[i]}</td>
			</jstl:forEach>

		</tr>
		
				<tr>
			<td><spring:message
					code="administrator.requests.pending.procession" /></td>
			<jstl:forEach var="i" begin="0" end="${processions}">
				<td style="text-align: right">${ratioPendingInAProcession[i]}</td>
			</jstl:forEach>

		</tr>
		</jstl:if>
	</table>

<jstl:if test="${not empty earlyProcessions}">
	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2"><spring:message
					code="administrator.processions.statistics" /></th>
		</tr>

		<tr>
			<td><spring:message code="administrator.early.processions" /></td>
			<jstl:forEach var="p" items="${earlyProcessions}">
				<td style="text-align: right"><jstl:out value="${p.title}" /></td>
				<br />
			</jstl:forEach>

		</tr>
		
	</table>
</jstl:if>
	<table class="displayStyle" style="width: 50%">

		<tr>
			<th colspan="2"><spring:message
					code="administrator.finder.statistics" /></th>
		</tr>
		<tr>
			<td><spring:message code="administrator.resultsFinder" /></td>
			<td style="text-align: right">${statsFinder[0]}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.resultsFinder.min" /></td>
			<td style="text-align: right">${statsFinder[1]}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.resultsFinder.avg" /></td>
			<td style="text-align: right">${statsFinder[2]}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.resultsFinder.stdev" /></td>
			<td style="text-align: right">${statsFinder[3]}</td>
		</tr>
		<tr>
			<td><spring:message code="administrator.emptyFinder" /></td>
			<td style="text-align: right">${ratioFinders}</td>
		</tr>
	</table>

	<table class="displayStyle" style="width: 50%">
		<tr>
			<th colspan="2" style="text-align: center"><spring:message
					code="administrator.positions.statistics" /></th>
		</tr>

		<tr>


			<td style="text-align: right"><script
					src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>

				<canvas id="myChart" width="100" height="100"></canvas> <jstl:if
					test="${language == 'en'}">

					<script>
var ctx = document.getElementById("myChart");
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: [<jstl:out value="${nameEnPositions}"/>],
        datasets: [{
            label: 'Positions',
            data: <jstl:out value="${countPositions}"/>,
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)',
                'rgba(55, 59, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)',
                'rgba(55, 59, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
    	 scales: {
             yAxes: [{
                 ticks: {
                     beginAtZero:true
                 }
             }]
         },
   
   maintainAspectRatio:true,
   responsive:true,

 legend:{
   display:true,
   position:'right',
   labels:{
     fontColor:'#000'
   }
 },
 layout:{
   padding:{
     left:0,
     right:0,
     bottom:300,
     top:0
   }
 },
 tooltips:{
   enabled:true
 }
    }
});
</script>

				</jstl:if> <jstl:if test="${language == 'es'}">

					<script>
var ctx = document.getElementById("myChart");
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ["Presidente", "Vicepresidente", "Secretario", "Tesorero", "Historiador", "Promotor","Vocal"],
        datasets: [{
            label: 'Cargos',
            data: [<jstl:out value="${countPositions[0]}" />,<jstl:out value="${countPositions[1]}" />,<jstl:out value="${countPositions[2]}" />,<jstl:out value="${countPositions[3]}" />,
                   <jstl:out value="${countPositions[4]}" />,<jstl:out value="${countPositions[5]}" />,<jstl:out value="${countPositions[6]}" />],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)',
                'rgba(55, 59, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)',
                'rgba(55, 59, 64, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
    	 scales: {
             yAxes: [{
                 ticks: {
                     beginAtZero:true
                 }
             }]
         },
   
   maintainAspectRatio:true,
   responsive:true,

 legend:{
   display:true,
   position:'right',
   labels:{
     fontColor:'#000'
   }
 },
 layout:{
   padding:{
     left:0,
     right:0,
     bottom:300,
     top:0
   }
 },
 tooltips:{
   enabled:true
 }
    }
});
</script>


				</jstl:if></td>




		</tr>
	</table>



</security:authorize>
