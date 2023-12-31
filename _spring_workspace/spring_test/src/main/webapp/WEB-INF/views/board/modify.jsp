<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous">

<jsp:include page="../layout/header.jsp"></jsp:include>

	<div class="container-md">
	<br>
	<h2>Board Modify Page</h2>
	<br>
	<c:set value="${boardDTO.bvo }" var="bvo" />
	<form action="/board/modify" method="post" enctype="multipart/form-data">
		<div class="mb-3">
			<label for="bno" class="form-label">#</label> <input
				type="text" name="bno" class="form-control" id="bno" value="${bvo.bno }" readonly="readonly">
		</div>
		<div class="mb-3">
			<label for="title" class="form-label">Title</label> <input
				type="text" name="title" class="form-control" id="title"
				placeholder="title" value="${bvo.title }">
		</div>
		<div class="mb-3">
			<label for="writer" class="form-label">writer</label> <input
				type="text" name="writer" class="form-control" id="writer"
				value="${bvo.writer }" readonly="readonly">
		</div>
		<div class="mb-3">
			<label for="reg_date" class="form-label">regdate</label> <input
				type="text" name="reg_date" class="form-control" id="reg_date"
				value="${bvo.reg_date }" readonly="readonly">
		</div>
		<div class="mb-3">
			<label for="content" class="form-label">content</label>
			<textarea class="form-control" name="content" id="content" rows="3">${bvo.content }</textarea>
		</div>
		
			<!-- 파일 표시 라인 -->
	<c:set value="${boardDTO.flist }" var="flist" />
	<div class="mb-3">
		<ul class="list-group list-group-flush">
		<!-- 파일 개수만큼 li를 추가하여 파일을 표시, 타입이 1인 경우만 표시 -->
		<!-- 
			li -> div => img 그림표시
				  div => 파일이름, 작성일, span size
		 -->
		 <!-- 파일 리스트 중 하나만 가져와서 fvo로 저장 -->
		 <c:forEach items="${flist }" var="fvo">
		 <div>
			<li class="list-group-item">
				<c:choose>
					<c:when test="${fvo.file_type > 0 }">
						<div>
						<!-- /upload/ -> servlet-context에 매핑 설정되어 있어서 사용함 -->
						<!-- /upload/save_dir/uuid_file_name -->
						<!-- 썸네일 버전 : src="/upload/${fn:replace(fvo.save_dir, '\\', '/') }/${fvo.uuid}_th_${fvo.file_name}" -->
							<img alt="" src="/upload/${fn:replace(fvo.save_dir, '\\', '/') }/${fvo.uuid}_${fvo.file_name}">
						</div>
					</c:when>
					<c:otherwise>
						<div>
							<!-- 아이콘 같은 모양 하나 가져와서 넣기 -->
						</div>
					</c:otherwise>
				</c:choose>
				<div>
				<!-- div => 파일이름, 작성일, span size -->
					<div>${fvo.file_name }</div>
					<div>${fvo.reg_date }</div>
					<span class="badge text-bg-warning">${fvo.file_size }byte</span>
					<button type="button" data-uuid="${fvo.uuid }" class="file-x">X</button>
				</div>
			</li>
			</div>
		</c:forEach>
		</ul>
		<br>
		<!-- 수정 파일 등록 라인 -->
		<div class="mb-3">
			<label for="file" class="form-label">New files...</label> 
			<br>
			<input type="file" name="files" class="form-control" id="file" multiple="multiple" style="display : none">
			<button type="button" class="btn btn-primary" id="trigger">fileUpload</button>	
		</div>
		<!-- 파일 목록 표시라인 -->
		<div class="mb-3" id="fileZone">
		
		</div>
		
		<br><br>
		<div class="position-relative">
		<div class="position-absolute bottom-0 end-0">
			<button type="submit" class="btn btn-primary" id="regBtn">Modify</button>
			<a href="/board/remove?bno=${bvo.bno }"><button type="button" class="btn btn-danger">delete</button></a>
			<a href="/board/list"><button type="button" class="btn btn-secondary">list..</button></a>
		</div>
		</div>
	</div>
</form>
	
<script>
	const bnoVal = `<c:out value="${bvo.bno}"/>`;
</script>

<script src="/resources/js/boardRegister.js"></script>
<script src="/resources/js/boardModify.js"></script>
<jsp:include page="../layout/footer.jsp"></jsp:include>