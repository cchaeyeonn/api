<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jspstudy.domain.BoardVo" %>

<% 
	BoardVo bv = (BoardVo)request.getAttribute("bv");
%>

<!-- boardContent.jsp -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BOARD_CONTENT</title>

<!-- css 링크 받는 부분 -->
<link rel="stylesheet" type="text/css" href="../resources/css/style.css">

</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<div class="content" style="text-align:center;">
		<br>
		<h3></h3>
		<br>
		
		<table class="table table-bordered" border="1" style="width:900px;">
		
			<form name="frm">
				<tr>
					<%
					if(bv.getT_head() != null) {%>
						<td class="l">머리말</td>
						<td class="R">[<%=bv.getT_head()%>] </td>
					<%}%>
					<%-- <td>머리말 구분</td>
					<td> [<%=bv.getT_head()%>]  </td> --%>
				</tr>
				
				<tr>
					<td class="l" style="width:250px">제목 &nbsp;&nbsp;</td>
					<td><%=bv.getT_subject() %></td>
				</tr>
				<%-- (날짜: <%=bv.getT_writer().substring(0,10) %>) --%>
				
				<tr>
					<td class="l">내용</td>
					<td class="R"><%=bv.getT_content()%>
					<%
					if(bv.getT_filename() != null){
						%>	
						<img src="http://localhost:8090<%=request.getContextPath() %>/images/<%=bv.getT_filename()%>">
						<a href="<%=request.getContextPath() %>/board/fileDownload.do?t_filename=<%=bv.getT_filename()%>"><%=bv.getT_filename() %></a>
					<%}%>
					
					</td>
				</tr>
				
				<tr>
					<td class="l">작성자</td>
					<td class="R"><%=bv.getT_writer()%></td>
				</tr>
				
				<tr>
					<td colspan=2 style="text-align:right">
					<%-- <%System.out.println((Integer)session.getAttribute("m_midx")+","+bv.getM_midx()+","+session); %> --%>
					
					
					<!-- 로그인된 아이디와 작성자가 같을때만 수정,삭제 가능-->
					<%
						if(session.getAttribute("m_midx") != null && ((Integer)session.getAttribute("m_midx"))==bv.getM_midx()){	//저장이 오브젝트형으로 돼서 integer형으로 변환하는거
					%>
						<input type="button" name ="modify" class="btn btn-outline-secondary" value="수정" onclick="location.href='<%=request.getContextPath()%>/board/boardModify.do?tidx=<%=bv.getTidx()%>'"> 
						<input type="button" name ="delete" class="btn btn-outline-secondary" value="삭제" onclick="location.href='<%=request.getContextPath()%>/board/boardDelete.do?tidx=<%=bv.getTidx()%>'">
					
					<%}%>
					
						<input type="button" name ="reply" class="btn btn-outline-secondary" value="답변" onclick="location.href='<%=request.getContextPath()%>/board/boardReply.do?tidx=<%=bv.getTidx()%>&origintidx=<%=bv.getOrigintidx()%>&t_depth=<%=bv.getT_depth()%>&t_level_=<%=bv.getT_level_()%>'"> 
						<input type="button" name ="list" class="btn btn-outline-secondary" value="목록" onclick="location.href='<%=request.getContextPath()%>/board/boardtip.do'"> 
					</td>
				</tr>
			</form>
		</table>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>