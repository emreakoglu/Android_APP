<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
    prefix="fn" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!-- <html> -->
<!-- <head> -->
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<!-- <title>Sirapiş Ekleme-Düzenleme</title> -->
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> -->
<!-- <link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.css" rel="stylesheet" type="text/css" /> -->
<!--   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script> -->
<!--   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<!--   <script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script> -->
<!-- </head> -->
<body>
	<form:form name="beanRequest" method="POST" modelAttribute="beanRequest" action="/KDemo/save">
	<form:hidden path="id"/>
	
	<div class="panel panel-default" style="padding: 15px;">
	<div class="panel-heading">Sirapiş Ekleme-Düzenleme</div>
	<div class="panel-body">
			<div class="input-group">
				<span class="input-group-addon">Nereden</span>
				<form:input path="nereden" class="form-control"/>
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">Nereye</span>
				<form:input path="nereye" class="form-control"/>
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">Mesafe</span>
				<form:input path="distance" class="form-control"/>
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">Gönderi Tipi</span>
				<select id="gondericiTipi" name="gondericiTipi" class="form-control">
				   <option value="Evrak">Evrak</option>
				   <option value="Küçük">Küçük</option>
				   <option value="Büyük">Büyük</option>
				</select>
			</div>
			<br>
			<div class="form-group date">
				<div class="input-group input-append date" id="datePicker">
					<span class="input-group-addon">Tarhi Seç</span>
						<form:input path="tarih" id="startDate"  class="form-control dp" />
		                <span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span></span>
				</div>
				<script>
						$(document).ready(function() {
						    $('#datePicker').datepicker({
							    	format: 'dd/mm/yyyy',
						    		todayHighlight:'TRUE',
						            startDate: '-0d',
						            autoclose: true,
						            language: 'tr',
						            calendarWeeks: true,
							        });
						});
					</script>
			</div>
			<div class="input-group">
				<span class="input-group-addon">Durum</span>
<%-- 				<form:input path="status" class="form-control"/> --%>
			<div class="col-sm-1">
				<form:checkbox path="status" class="form-control" />
			</div>
			</div>	
		
	</div>
	<div class="panel-footer">
		<button class="btn btn-primary" type="submit">Gonder</button>
	</div>
	</div>
	
	</form:form>
</body>
<!-- </html> -->