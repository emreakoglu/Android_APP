<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
    prefix="fn" %>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!-- <html> -->
<!-- <head> -->
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<!-- <title>DashBoard Ekranı</title> -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> -->
<!--   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script> -->
<!--   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<script type="text/javascript">
  setTimeout(function () {
	  var hangoutButton = document.getElementById("yenile");
	  hangoutButton.click();
	  }, 50000);
  
  $('#confirm-delete').on('show.bs.modal', function(e) {
	    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	});
</script>
<body>
	<table class="table table-striped">
			<thead>
				<tr>
					<th class="text-center">#</th>
					<th class="text-center">Nereden</th>
					<th class="text-center">Nereye</th>
					<th class="text-center">Tarih</th>
					<th class="text-center">Mesafe</th>
					<th class="text-center">Gönderi Tipi</th>
					<th class="text-center"><spring:message code="label.lastName" /></th>
					<th class="text-center"></th>
				</tr>
			</thead>
			<c:forEach var="beanRequest" items="${beanRequests}">
				<tr> 
					<td class="visible-lg text-center">
						<c:out value="${beanRequest.id}" />
					</td>
					<td class="visible-lg text-center">
						<c:out value="${beanRequest.nereden}" />
					</td>
					<td  class="text-center">
						<c:out value="${beanRequest.nereye}" />
					</td>
					<td class="text-center">
						${beanRequest.tarih }
					</td>	
					<td class="text-center">
						${beanRequest.distance }
					</td>
					<td class="text-center">
						${beanRequest.gondericiTipi }
					</td>
					<td class="text-center">
						${beanRequest.status }
					</td>
					<td class="text-center">
						<a href="/KDemo/delete/${beanRequest.id }" class="btn btn-danger">Sil</a>
						<b><a href="/KDemo/edit/${beanRequest.id }" class="btn btn-success" role="button">Duzenle</a>
					</td>
				</tr>
					
			</c:forEach>
	</table>
	<div class="panel-footer">
		<a href="/KDemo/list" id="yenile" class="btn btn-primary" role="button">Yenile</a>
		<b><a href="/KDemo/add" id="yenile" class="btn btn-primary" role="button">Manuel Ekle</a>
	</div>
	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					    <div class="modal-dialog">
					        <div class="modal-content">
					            <div class="modal-header">
					                ...
					            </div>
					            <div class="modal-body">
					                ...
					            </div>
					            <div class="modal-footer">
					                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					                 <a class="btn btn-danger btn-ok">Delete</a>
					            </div>
					        </div>
					    </div>
					</div>
</body>
<!-- </html> -->