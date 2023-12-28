<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous">

<jsp:include page="../layout/header.jsp"></jsp:include>

<div class="container-md">
	<br>
	<h2>Board register Page</h2>
	<br>
	<form action="/board/register" method="post" enctype="multipart/form-data">
		<div class="mb-3">
			<label for="title" class="form-label">Title</label> <input
				type="text" name="title" class="form-control" id="title"
				placeholder="title">
		</div>
		<div class="mb-3">
			<label for="writer" class="form-label">writer</label> 
			<input type="text" name="writer" class="form-control" id="writer" value="${ses.id }" readonly="readonly">
		</div>
		<div class="mb-3">
			<label for="content" class="form-label">content</label>
			<textarea class="form-control" name="content" id="content" rows="3"></textarea>
		</div>

		<!-- file 입력 라인 추가 -->
		<div class="mb-3">
			<label for="file" class="form-label">files...</label> 
			<br>
			<input type="file" name="files" class="form-control" id="file" multiple="multiple" style="display : none">
			<button type="button" class="btn btn-primary" id="trigger">fileUpload</button>	
		</div>
		<!-- 파일 목록 표시라인 -->
		<div class="mb-3" id="fileZone">
		</div>
		
		<button type="submit" class="btn btn-primary" id="regBtn" >Register</button>
</div>

</form>





<script src="/resources/js/boardRegister.js"></script>
<jsp:include page="../layout/footer.jsp"></jsp:include>