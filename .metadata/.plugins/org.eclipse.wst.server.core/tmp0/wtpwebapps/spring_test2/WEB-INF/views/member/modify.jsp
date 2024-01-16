<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<jsp:include page="../layout/header.jsp"></jsp:include>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>

<div class="container-md">
	<br>
	<h2>Member Modify Page</h2>
	<br>
	<form action="/member/modify" method="post">
		<div class="mb-3">
			<label for="email" class="form-label">Email</label> 
			<input type="text" name="email" class="form-control" id="email" placeholder="email" readonly="readonly" value="${mvo.email }">
		</div>
		<div class="mb-3">
			<label for="pwd" class="form-label">PW</label> 
			<input type="password" name="pwd" class="form-control" id="pwd" placeholder="변경할 password...">
		</div>
		<div class="mb-3">
			<label for="nickName" class="form-label">Nick_name</label> 
			<input type="text" name="nickName" class="form-control" id="nickName" value="${mvo.nickName }">
		</div>
		<div class="mb-3">
		<c:forEach items="${mvo.authList }" var="auths">
			<p>Auth${i} : ${auths.auth }</p> 				
		</c:forEach>
		</div>
		<!-- 해당 멤버의 권한을 출력 (2개일수도 있음.) -->
		
		<button type="submit" class="btn btn-primary">Modify</button>
		<a href="/member/delete"><button type="button" class="btn btn-danger">Delete</button></a>
	</form>

<jsp:include page="../layout/footer.jsp"></jsp:include>