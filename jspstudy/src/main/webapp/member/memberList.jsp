<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jspstudy.domain.*"%>
<%@ page import="java.util.*"%>

<%@ include file="/dbconn.jsp" %>
<%@ include file="/function.jsp" %>


<!-- memberList.jsp -->
<%
	
	ArrayList<MemberVo> alist = (ArrayList<MemberVo>)request.getAttribute("alist");	//컨트롤러에서 처리를 하고 (그 값을 담아서 여기서 꺼내서 사용)처리된 값을 담아서 여기로 넘겨서 사용하는 방법

	

	//request.setCharacterEncoding("UTF-8");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<!-- css 받아오는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>

	<%--<header>
		<h2>
			<a href="../index.jsp">홈</a>
		</h2>
		 <nav>
			<ul>
				<li><a href="<%=request.getContextPath() %>/board/reserve.do">예약하기</a></li>
				<li><a href="<%=request.getContextPath() %>/board/boardList.do">게시판</a></li>
				<li><a
					href="<%=request.getContextPath() %>/member/memberLogin.do">회원로그인</a></li>
				<li><a
					href="<%=request.getContextPath() %>/member/memberJoin.do">회원가입</a></li>
			</ul>
		</nav> --%>
		<%-- <div class="loginsession">
			<%
			if (session.getAttribute("midx") != null){
				out.println("회원아이디:"+session.getAttribute("memberId")+"<br>");
				out.println("회원이름:"+session.getAttribute("memberName")+"<br>");
					
				out.println("<a href='"+request.getContextPath()+"/member/memberLogout.do'>로그아웃</a><br>");
				}
			%>
		</div> --%>
	</header>
	<div class="content" style="padding-left: 430px;">
	
<%-- 		<!--세션에 담겨져있는 값을 찍어봄 -->
		<%
if (session.getAttribute("midx") != null){
	out.println("회원아이디:"+session.getAttribute("memberId")+"<br>");
	out.println("회원이름:"+session.getAttribute("memberName")+"<br>");
	
	out.println("<a href='"+request.getContextPath()+"/member/memberLogout.do'>로그아웃</a><br>");
	}
	%> --%>

		<h4>회원 목록</h4>
		<table border="1" style="width: 800px">
			<tr>
				<td style="color: green;">midx 번호</td>
				<td style="color: green;">번호</td>
				<td style="color: green;">이름</td>
				<td style="color: green;">전화번호</td>
				<td style="color: green;">작성일</td>
			</tr>

			<%-- <a href='<%=request.getContextPath()%>/index.jsp'>홈으로</a><br> --%>

			<% for (MemberVo mv : alist){ %>
			<tr>
				<td><%=mv.getM_midx()%></td>
				<td><% out.println(mv.getM_midx()); %></td>
				<td><%=mv.getM_membername()%></td>
				<td><%=mv.getM_memberphone()%></td>
				<td><%=mv.getM_joindate()%></td>
			</tr>
			<% } %>


		</table>
		<input type="button" name="button" class="btn btn-outline-secondary" value="홈으로"
			onclick="location.href='<%=request.getContextPath() %>/index.jsp'">
	</div>
<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>