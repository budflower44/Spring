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
	<h2>Member Join Page</h2>
	<br>
	<form action="/member/register" method="post">
		<div class="mb-3">
			<label for="id" class="form-label">ID</label> <input type="text"
				name="id" class="form-control" id="id" placeholder="id">
		</div>
		<div class="mb-3">
			<label for="pw" class="form-label">PW</label> <input type="password"
				name="pw" class="form-control" id="pw" placeholder="password">
		</div>
		<div class="mb-3">
			<label for="name" class="form-label">Name</label> <input type="text"
				name="name" class="form-control" id="name" placeholder="name">
		</div>
		<div class="mb-3">
			<label for="email" class="form-label">Email</label> <input
				type="email" name="email" class="form-control" id="email"
				placeholder="email">
		</div>
		<div class="mb-3">
			<label for="home" class="form-label">Home</label> <input type="text"
				name="home" class="form-control" id="home" placeholder="home">
		</div>
		<div class="mb-3">
			<label for="age" class="form-label">Age</label> <input type="number"
				name="age" class="form-control" id="age" placeholder="age">
		</div>
		<button type="submit" class="btn btn-primary">Register</button>
	</form>

<jsp:include page="../layout/footer.jsp"></jsp:include>