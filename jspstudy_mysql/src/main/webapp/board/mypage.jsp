<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="jspstudy.domain.*"%>
<%@ page import="java.util.ArrayList"%>

 <%
	if ((int)session.getAttribute("m_midx") == 16) {
	
		session.setAttribute("saveUrl", request.getRequestURI()); //save가 전체값을 담는다
		out.println(
		"<script>alert('관리자는 마이페이지가 없습니다');location.href='" + request.getContextPath() + "/index.jsp'</script>");
	} ;
%>

<!-- mypage.jsp -->

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>MYPAGE</title>
	
	<!-- css 받는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">

</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>
		<center>
		<div class="content">
			<br>
			<h3>마이페이지</h3>
			<hr>
			<h4>예약 현황</h4><br>
			<span> [예약하신 시간 / 선생님 성함 / 선생님 성별]</span><br><br>
			<%//=request.getAttribute("alist") %>
			<% if(request.getAttribute("alist") != null){
					ArrayList<ReserveDto> alist = (ArrayList<ReserveDto>)request.getAttribute("alist");
					for (ReserveDto rv : alist){%>
						<%=rv.getRS_date()%> / <%=rv.getRS_stime()%> / <%=rv.getTENAME() %> / <%=rv.getTEGENDER() %>
							<!-- <input type="button" name="button" class="btn btn-outline-secondary" value="삭제하기" onclick="location.href='#'"> --><br><br>
					<% } 
				}else{
					out.println("예약이 없습니다.");
					}%><br><br>
			
			<input type="button" name="button" class="btn btn-outline-secondary" value="홈으로"
				onclick="location.href='<%=request.getContextPath()%>/index.jsp'"><br><br>
		</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>