<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<jsp:include page="../layout/header.jsp"></jsp:include>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>

<div class="container-md">
	<br>
	<h2>Member List Page</h2>
	<br>
		<table class="table table-hover">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">Email</th>
				<th scope="col">Nick_Name</th>
				<th scope="col">Reg_Date</th>
				<th scope="col">Last_Login</th>
				<th scope="col">Auths</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${mvoList }" var="mvoList" varStatus="i">
				<tr>
					<th>${i.count}</th>
					<td><a href="/member/modify?email=${mvoList.email }">${mvoList.email }</a></td>
					<td>${mvoList.nickName }</td>
					<td>${mvoList.regAt }</td>
					<td>${mvoList.lastLogin }</td>
<%-- 					<td>
						<p>
						<c:forEach items="${mvoList.authList }" var="authList">
							${authList.auth }
						</c:forEach>
						</p>
					</td> --%>
					<c:set value="${mvoList.authList }" var="authList" />
					<td>
						<c:choose>
							<c:when test="${authList.stream().anyMatch(AuthVO -> AuthVO.auth.contains('ROLE_ADMIN')).get() }">
								<p>ADMIN / USER</p>
							</c:when>
							<c:otherwise>
								<p>USER</p>
							</c:otherwise>
						</c:choose>		
					</td>		 
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
	
	
<jsp:include page="../layout/footer.jsp"></jsp:include>