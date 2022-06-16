<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="jspstudy.domain.*"%>
<%@ page import="java.util.ArrayList"%>

<%@ include file="/dbconn.jsp" %>
<%@ include file="/function.jsp" %>


<!-- boardList.jsp -->
<%
	
	ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");	//컨트롤러에서 처리를 하고 (그 값을 담아서 여기서 꺼내서 사용)처리된 값을 담아서 여기로 넘겨서 사용하는 방법
	PageMaker pm = (PageMaker)request.getAttribute("pm");

	//request.setCharacterEncoding("UTF-8");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
<link rel="stylesheet" type="text/css" href="../css/style.css">

</head>

<body>
	<div class="page">
		<header>
			<h2>
				<a href="../index.jsp">홈</a>
			</h2>
			<nav>
				<ul>
					<li><a href="<%=request.getContextPath() %>/board/reserve.do">예약하기</a></li>
					<li><a
						href="<%=request.getContextPath() %>/board/boardList.do">게시판</a></li>
					<li><a
						href="<%=request.getContextPath() %>/member/memberLogin.do">회원로그인</a></li>
					<li><a
						href="<%=request.getContextPath() %>/member/memberJoin.do">회원가입</a></li>
				</ul>
			</nav>
			<div class="loginsession">
					<%
					if (session.getAttribute("m_midx") != null) {
						out.println("회원아이디:" + session.getAttribute("m_memberId") + "<br>");
						out.println("회원이름:" + session.getAttribute("m_memberName") + "<br>");
		
						out.println("<a href='" + request.getContextPath() + "/member/memberLogout.do'>로그아웃</a><br>");
					}
					%>
				</div>
		</header>
		<hr>
		<div class="nav">
			<nav>
				<ul>
					<li><a href="#">팁</a></li>
					<li><a href="#">질문</a></li>
					<li><a href="#">리뷰</a></li>
				</ul>
			</nav>
			<hr>
		</div>
		<div class="content" style="padding-left: 430px;">


			<form name="frm"
				action="<%=request.getContextPath() %>/board/boardList.do"
				method="post">
				<table border=0 style="width: 800px; text-align: right">
					<tr>
						<td style="width: 620px;"><select name="searchType">
								<option value="subject">제목</option>
								<option value="writer">작성자</option>
						</select></td>
						<td><input type="text" name="keyword" size="10"></td>
						<td><input type="submit" name="submit" value="검색"></td>
					</tr>
				</table>
			</form>

			<%
		if (session.getAttribute("midx") != null){
			out.println("회원아이디:"+session.getAttribute("memberId")+"<br>");
			out.println("회원이름:"+session.getAttribute("memberName")+"<br>");
			
			out.println("<a href='"+request.getContextPath()+"/member/memberLogout.do'>로그아웃</a><br>");
			}
			%>
			<h1>게시판 목록</h1>
			<table border="1" style="width: 800px">
				<tr style="color: green;">
					<td style="width: 100px">bidx 번호</td>
					<td style="width: 400px">제목</td>
					<td style="width: 200px">작성자</td>
					<td style="width: 100px">작성일</td>
				</tr>

				<!-- 반복문으로 출력 -->
				<% for (BoardVo bv : alist){ %>
				<tr>
					<td><%=bv.getBidx()%></td>
					<td>
						<% 
			for(int i=1; i<=bv.getLevel_();i++){
				out.println("&nbsp;&nbsp;");
				if(i==bv.getLevel_()){
					out.println("ㄴ");
				}
			
			}
			%><!-- 프로젝트 이름/마디/컨텐츠  --> <a
						href="<%=request.getContextPath()%>/board/boardContent.do?bidx=<%=bv.getBidx()%>"><%=bv.getSubject()%></a>
					</td>
					<td><%=bv.getWriter()%></td>
					<td><%=bv.getWriteday()%></td>
				</tr>
				<% } %>
			</table>
			<table style="width: 800px; text-align: center;">
				<tr>
					<td style="width: 200px; text-align: right;">
						<%
			String keyword = pm.getScri().getKeyword();
			
			String searchType = pm.getScri().getSearchType();	//파라미터 변수로 담아서 가지고 다니려고
			
			
			if(pm.isPrev() == true){
				out.println("<a href='"+request.getContextPath()+"/board/boardList.do?page="+(pm.getStartPage()-1)+"&keyword="+keyword+"&searchType="+searchType+">'>◀</a>");
			}
			%>
					</td>
					<td>
						<%
			for (int i = pm.getStartPage(); i<=pm.getEndPage(); i++){
				out.println("<a href='"+request.getContextPath()+"/board/boardList.do?page="+i+"&keyword="+keyword+"&searchType="+searchType+"'>"+i+"</a>");
			}
			%>
					</td>
					<td style="width: 200px; text-align: left;">
						<%
			System.out.println(pm.getEndPage());
			if(pm.isNext()&&pm.getEndPage() >0){
				out.println("<a href='"+request.getContextPath()+"/board/boardList.do?page="+(pm.getEndPage()+1)+"&keyword="+keyword+"&searchType="+searchType+"'>▶</a>");
			}
			%>
					</td>
				</tr>
			</table>
			<input type="button" name="button" value="홈으로"
				onclick="location.href='<%=request.getContextPath() %>/index.jsp'">
			<input type="button" name="button" value="글쓰기"
				onclick="location.href='<%=request.getContextPath() %>/board/boardWrite.do'">
		</div>
</body>
</html> --%>