<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="layout/header.jsp"></jsp:include>
<h1>
	Hello world!  
</h1>

<c:if test="${ses.id ne null}">
<div>
<span>${ses.id } 님 안녕하세요 ~ !!</span> <span class="badge text-bg-secondary">${ses.last_login }</span>
</div>
</c:if>

<script>
	const msg_login = `<c:out value="${msg_login}"/>`;
	if(msg_login === "-1"){
		alert("로그인 실패~!!")
	}
</script>
<script>
	const msg_login_sucess = `<c:out value="${msg_login_sucess}"/>`;
	if(msg_login_sucess === "1"){
		alert("로그인 성공~!!")
	}
</script>
<script>
	const msg_logout = `<c:out value="${msg_logout}"/>`;
	if(msg_logout === "1"){
		alert("로그아웃 되었습니다~!!")
	}
</script>
<script>
	const msg_modify = `<c:out value="${msg_modify}"/>`;
	if(msg_modify === "1"){
		alert("회원정보가 수정되었습니다. 다시 로그인해주세요.")
	}
</script>
<script>
	const msg_delete = `<c:out value="${msg_delete}"/>`;
	if(msg_delete === "1"){
		alert("회원 탈퇴가 정상적으로 되었습니다. 안녕히 가십시오.")
	}
</script>


<!-- 선생님풀이 -->
<!-- <script>
	const msg_modify = `<c:out value="${msg_modify}"/>`;
	if(msg_modify === "1"){
		alert("회원정보가 수정되었습니다. 다시 로그인해주세요.")
	}
</script> -->


<jsp:include page="layout/footer.jsp"></jsp:include>

