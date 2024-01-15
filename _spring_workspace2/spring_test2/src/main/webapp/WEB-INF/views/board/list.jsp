<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../layout/header.jsp"></jsp:include>

<div class="container-md">
	<br>
	<h2>Board List Page</h2>
	<br>
	
	<!-- Search Line -->
	<div class="container-fluid">
		<form action="/board/list" method="get" class="d-flex" role="search">
			<c:set value="${ph.pgvo.type}" var="typed"></c:set>
			<select name="type" class="form-select form-select-sm" aria-label="Small select example">
				<option ${ph.pgvo.type eq null ? 'selected' : '' }>Choose...</option>
				<option value="t" ${typed eq 't' ? 'selected' : '' }>Title</option>
				<option value="w" ${typed eq 'w' ? 'selected' : '' }>Writer</option>
				<option value="c" ${typed eq 'c' ? 'selected' : '' }>Content</option>
				<option value="tc" ${typed eq 'tc' ? 'selected' : '' }>Title&Content</option>
				<option value="tw" ${typed eq 'tw' ? 'selected' : '' }>Title&Writer</option>
				<option value="wc" ${typed eq 'wc' ? 'selected' : '' }>Writer&Content</option>
				<option value="twc" ${typed eq 'twc' ? 'selected' : '' }>Title&Writer&Content</option>
			</select>
			<input type="hidden" name="pageNo" value="1">
			<input type="hidden" name="qty" value="${ph.pgvo.qty }">
			<input name="keyword" placeholder="Search..." class="form-control me-2" type="search" placeholder="Search" aria-label="Search" value="${ph.pgvo.keyword }">
			<button class="btn btn-primary position-relative" type="submit">Search
			<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">${ph.totalCount }
			<span class="visually-hidden"></span>
			</button>
		</form>
	</div>
	
	<!-- 검색라인 -->
<%-- 		<div class="container-fluid">
			<form action="/board/list" method="get" class="d-flex" role="search">
				<select name="type" class="form-select form-select-sm" aria-label="Small select example">
					<option ${ph.pgvo.type==null ? 'selected' : ''}>Choose...</option>
					<option value="t" ${ph.pgvo.type eq 't' ? 'selected' : ''} >Title</option>
					<option value="w" ${ph.pgvo.type eq 'w' ? 'selected' : ''}>Writer</option>
					<option value="c" ${ph.pgvo.type eq 'c' ? 'selected' : ''}>Content</option>
					<option value="tc" ${ph.pgvo.type eq 'tc' ? 'selected' : ''}>Title&Content</option>
					<option value="tw" ${ph.pgvo.type eq 'tw' ? 'selected' : ''}>Title&Writer</option>
					<option value="wc" ${ph.pgvo.type eq 'wc' ? 'selected' : ''}>Writer&Content</option>
					<option value="twc" ${ph.pgvo.type eq 'twc' ? 'selected' : ''}>All</option>
				</select> <input name="keyword" placeholder="Search..."
					class="form-control me-2" type="search" placeholder="Search"
					aria-label="Search" value="${ph.pgvo.keyword }"> 
					<input type="hidden" name="pageNo" value="1"> 
					<input type="hidden" name="qty" value="10">
				<button class="btn btn-primary position-relative" type="submit">Search
				<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">${ph.totalCount }<span class="visually-hidden"></span>
				</span>
				</button>
			</form>
		</div> --%>


	<table class="table table-hover">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">title</th>
				<th scope="col">reg_date</th>
				<th scope="col">read_count</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="bvo">
				<tr>
					<%-- <c:forEach begin="1" end="${ph.pgvo.totalCount }" var="i">
					<th scope="row">${list.size()+1 - i.count }</th>
					</c:forEach> --%>
					<th>${bvo.bno }</th>
					<td><a href="/board/detail?bno=${bvo.bno }">${bvo.title }</a></td>
					<td>${bvo.regAt }</td>
					<td>${bvo.readCount }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 페이지네이션 라인 -->
	
	<nav aria-label="Page navigation example">
		<ul class="pagination">
			
			<!-- prev -->
			<c:if test="${ph.prev }">
				<li class="page-item">
				<a href="/board/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" class="page-link" aria-label="Previous">
				<span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>		
			
			<!-- 페이지번호 -->
			<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
				<li class="page-item"><a href="/board/list?pageNo=${i}&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" a class="page-link">${i}</a></li>
			</c:forEach>
			
			<!-- next -->
				<li class="page-item ${(ph.next eq false) ? 'disabled' : '' }">
					<a href="/board/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}" class="page-link" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a></li>
			
			<!-- 전체보기 -->
			<li class="page-item">
				<a href="/board/list" class="page-link" aria-label="Previous">
				<span aria-hidden="true">전체보기</span>
			</a></li>
		</ul>
	</nav>
	
<%-- 	<nav aria-label="Page navigation example">
		<ul class="pagination">
			<!-- 이전 -->
			<c:if test="${ph.prev }">
				<li class="page-item"><a class="page-link"
					href="/board/list?pageNo=${ph.startPage-1}&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>
			</c:if>
				<li class="page-item"><a class="page-link"
					href="/board/list"
					aria-label="Previous"> <span aria-hidden="true">전체보기</span></a></li>
			
			<!-- 페이지 번호 -->
			<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
				<li class="page-item"><a class="page-link" href="/board/list?pageNo=${i}&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">${i}</a></li>
			</c:forEach>

			<!-- 다음 -->
			<c:if test="${ph.next }">
				<li class="page-item"><a class="page-link"
					href="/board/list?pageNo=${ph.endPage+1}&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
			</c:if>
		</ul>
	</nav> --%>

</div>

<!-- <script>
	const isDel = `<c:out value="${isDel}" />`;
	if (isDel == 1) {
		alert("게시글이 삭제되었습니다.");
	}
</script> -->

<jsp:include page="../layout/footer.jsp"></jsp:include>
