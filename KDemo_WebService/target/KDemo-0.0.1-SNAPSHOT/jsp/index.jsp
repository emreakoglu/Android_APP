<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
  setTimeout(function () {
	  var hangoutButton = document.getElementById("yenile");
	  hangoutButton.click();
	  }, 5000);
</script>
</head>
<body>
	<table class="table table-striped">
			<thead>
				<tr>
					<th class="text-center">Nereden</th>
					<th class="text-center">Nereye</th>
					<th class="text-center">Tarih</th>
					<th class="text-center">Mesafe</th>
					<th class="text-center">Gönderi Tipi</th>
					<th class="text-center">Durum</th>
				</tr>
			</thead>
			<c:forEach var="jspBeanReqeust" items="${jspBeanReqeustList}">
				<tr> 
					<td class="visible-lg text-center">
						<c:out value="${jspBeanReqeust.from}" />
					</td>
					<td  class="text-center">
						<c:out value="${jspBeanReqeust.to}" />
					</td>
					<td class="text-center">
						${jspBeanReqeust.date }
					</td>	
					<td class="text-center">
						${jspBeanReqeust.distance }
					</td>
					<td class="text-center">
						${jspBeanReqeust.gondericiTipi }
					</td>
					<td class="text-center">
						${jspBeanReqeust.status }
					</td>
				</tr>
			</c:forEach>
	</table>
	<h3>
		<a id="yenile" href="updateQuery">Yenile</a>
	</h3>

</body>
</html>