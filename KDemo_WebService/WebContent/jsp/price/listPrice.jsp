<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
    prefix="fn" %>
    <body>
	<table class="table table-striped">
			<thead>
				<tr>
					<th class="text-center">#</th>
					<th class="text-center">Gönderi Tipi</th>
					<th class="text-center">Başlangıç Fiyatı</th>
					<th class="text-center">Artış Mesafesi</th>
					<th class="text-center">Artış Fiyatı</th>
				</tr>
			</thead>
			<c:forEach var="price" items="${prices}">
				<tr> 
					<td class="visible-lg text-center">
						<c:out value="${price.id}" />
					</td>
					<td class="visible-lg text-center">
						<c:out value="${price.type}" />
					</td>
					<td  class="text-center">
						<c:out value="${price.firstPrice}" />
					</td>
					<td class="text-center">
						${price.increaseDistance }
					</td>	
					<td class="text-center">
						${price.increasePrice }
					</td>
					<td class="text-center">
						<a href="/KDemo/price/delete/${price.id }" class="btn btn-danger">Sil</a>
						<b><a href="/KDemo/price/edit/${price.id }" class="btn btn-success" role="button">Duzenle</a>
					</td>
				</tr>
					
			</c:forEach>
	</table>
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