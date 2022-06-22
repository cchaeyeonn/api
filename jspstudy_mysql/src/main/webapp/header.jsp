<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!-- header.jsp -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- style.css 참조영역 -->
<link rel="stylesheet" type="text/css" href="./resources/css/style.css">


<!-- 부트스트랩 참조 영역 -->
 	<!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- Optional JavaScript; choose one of the two! -->
    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->
    <!--
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
    -->
    
</head>

<body>

	<!-- 상단 헤더 시작-->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="<%=request.getContextPath()%>/index.jsp">HOME</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item">
						<a class="nav-link active" aria-current="page" href="<%=request.getContextPath() %>/board/reserve.do">Reservation</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="<%=request.getContextPath() %>/board/boardtip.do">Board</a>
					</li>
					<li class="nav-item" style="display:flex;">
						<%
						if (session.getAttribute("m_midx") != null) {%> 
							<a class="nav-link" href="<%=request.getContextPath() %>/board/mypage.do"> 
							<% out.println("Hello, "+"["+session.getAttribute("m_memberId")+"]");%></a>
							
							<a class="nav-link" href="<%=request.getContextPath() %>/member/memberLogout.do"> 
							<% out.println("Logout");%></a>
							<a class="nav-link" href="<%=request.getContextPath() %>/board/schedule_insert.do">schedule</a>
							 
						<%	/* &nbsp; out.println("<a href='" + request.getContextPath() + "/member/memberLogout.do'>Logout</a><br>"); */
						} 
						
						
						
						else if(session.getAttribute("m_midx") == null) {
							//값이 존재하는 경우에만 로그인버튼을 띄움.
							//처리를 안해주고 로그인 버튼을 누를시 LoginForm.jsp로 넘어가면 Top.jsp에 있는 로그인 버튼이 그대로 나옴.
							%> <a class="nav-link" href="<%=request.getContextPath() %>/member/memberLogin.do">Login</a>
						<% } %>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="<%=request.getContextPath() %>/member/memberList.do">MemerList</a>
					</li>
					
					
						
						
						<%-- <a class="nav-link" href="<%=request.getContextPath() %>/board/schedule_insert.do">schedule</a> --%>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- 상단 헤더 끝-->
	
	
		<%-- <header>
		<h2>
			<a href="<%=request.getContextPath()%>/index.jsp">HOME</a>
		</h2>
		<nav>
		<ul>
			<li><a href="<%=request.getContextPath() %>/board/reserve.do">예약하기</a></li>
			<li><a href="<%=request.getContextPath() %>/board/boardtip.do">게시판</a></li>
			<li>
				<%
				if (session.getAttribute("m_midx") != null) {%> 
					<a href="<%=request.getContextPath() %>/board/mypage.do"> 
					<% out.println("["+session.getAttribute("m_memberId")+"]" + "님");%></a>&nbsp;<%
						out.println("<a href='" + request.getContextPath() + "/member/memberLogout.do'>로그아웃</a><br>");
						} 
				else if(session.getAttribute("m_midx") == null) {
					//값이 존재하는 경우에만 로그인버튼을 띄움.
					//처리를 안해주고 로그인 버튼을 누를시 LoginForm.jsp로 넘어가면 Top.jsp에 있는 로그인 버튼이 그대로 나옴.
					%> <a href="<%=request.getContextPath() %>/member/memberLogin.do">회원로그인</a>
				<% } %>
			</li>
	
		</ul>
		</nav>
		<div class="loginsession"></div>
		</header> --%>
		
</body>
</html>
