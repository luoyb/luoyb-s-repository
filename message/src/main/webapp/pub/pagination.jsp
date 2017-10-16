<%@ page contentType="text/html;charset=UTF-8"%>

<ul class="pagination">

	<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }" />
	
	<c:if test="${page.pageNo gt 1}">
  		<li class=""><a href="javascript:doQuery(${page.pageNo-1});">&laquo;</a></li>
  	</c:if>
  	
  	<c:forEach begin="1" end="${page.pageCount}" step="1" var="index">
  		<li class="<c:if test="${page.pageNo eq index}">active</c:if>"><a href="javascript:doQuery(${index});">${index}</a></li>
  	</c:forEach>
  	
  	<c:if test="${page.pageNo lt page.pageCount}">
  		<li class=""><a href="javascript:doQuery(${page.pageNo+1});">&raquo;</a></li>
  	</c:if>
  	
</ul>

<script>
	function doQuery(index) {
		$("#pageNo").val(index);
		$('#queryForm').submit();
	}
</script>