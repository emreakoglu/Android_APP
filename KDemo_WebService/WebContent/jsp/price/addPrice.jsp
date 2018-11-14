<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
    prefix="fn" %>
    
<body>
	<form:form name="price" method="POST" modelAttribute="price" action="/KDemo/price/save">
	<form:hidden path="id"/>
	
	<div class="panel panel-default" style="padding: 15px;">
	<div class="panel-heading">Fiyat Ekleme-Düzenleme</div>
	<div class="panel-body">
			<div class="input-group">
				<span class="input-group-addon">Gönderi Tipi</span>
				<select id="type" name="type" class="form-control">
				   <option value="Evrak">Evrak</option>
				   <option value="Küçük">Küçük</option>
				   <option value="Büyük">Büyük</option>
				</select>
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">Başlangıç Fiyatı</span>
				<form:input path="firstPrice" class="form-control"/>
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">Artış Mesafesi</span>
				<form:input path="increaseDistance" class="form-control"/>
			</div>
			<br>
			<div class="input-group">
				<span class="input-group-addon">Artış Fiyatı</span>
				<form:input path="increasePrice" class="form-control"/>
			</div>
			<br>	
	</div>
	<div class="panel-footer">
		<button class="btn btn-primary" type="submit">Kaydet</button>
	</div>
	</div>
	
	</form:form>
</body>