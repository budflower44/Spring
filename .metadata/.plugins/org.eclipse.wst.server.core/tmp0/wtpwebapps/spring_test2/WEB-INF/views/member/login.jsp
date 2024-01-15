<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../layout/header.jsp"></jsp:include>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>

<div class="container-md">
	<br>
	<h2>Member Login Page</h2>
	<br>

	<form action="/member/login" method="post">
		<div class="mb-3">
			<label for="email" class="form-label">ID</label> <input type="text"
				name="email" class="form-control" id="email" placeholder="email">
		</div>
		<div class="mb-3">
			<label for="pwd" class="form-label">PW</label> <input type="password"
				name="pwd" class="form-control" id="pwd" placeholder="password">
		</div>
		<button type="submit" class="btn btn-primary">Login</button>
	</form>
</div>

<c:if test="${not empty param.errMsg }">
	<div class="mb-3 text-danger">
		<c:choose>
			<c:when test="${param.errMsg eq 'Bad credentials' }">
				<c:set value="이메일 & 비밀번호가 일치하지 않습니다." var="errText"></c:set>
			</c:when>
			<c:otherwise>
				<c:set value="관리자에게 문의해주세요." var="errText"></c:set>
			</c:otherwise>
		</c:choose>
		${errText }
	</div>
</c:if>

<jsp:include page="../layout/footer.jsp"></jsp:include>
