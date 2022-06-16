<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="jspstudy.domain.*"%>
<%@ page import="java.util.ArrayList"%>


<!-- board_tip.jsp -->
<%
	
	ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");	//컨트롤러에서 처리를 하고 (그 값을 담아서 여기서 꺼내서 사용)처리된 값을 담아서 여기로 넘겨서 사용하는 방법
	PageMaker pm = (PageMaker)request.getAttribute("pm");

	//request.setCharacterEncoding("UTF-8");

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title> BOARD </title>
	
	<!-- css와 부트스트랩 받아오는 부분 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	<link rel="stylesheet" type="text/css" href="bootstrap-3.3.2-dist/ccss/bootstrap.min.css">

</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>
		<br>
		<div class="content" style="text-align:center;">
		<h3>board</h3>
		<br>
		
		<!-- 게시판 위에 검색기능 시작 -->
			<form name="frm" action="<%=request.getContextPath() %>/board/boardtip.do" method="post">
				
				<table border=0 style="width: 800px; text-align: right">
					<tr>
					<td>
						<div style="text-align:left;">
						<div class="form-check form-check-inline">
						  <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="Tip" checked disabled>
						  <label class="form-check-label" for="inlineCheckbox1">Tip</label>
						</div>
						<div class="form-check form-check-inline">
						  <input class="form-check-input" type="checkbox" id="inlineCheckbox2" value="Q&A" checked disabled>
						  <label class="form-check-label" for="inlineCheckbox2">Q&A</label>
						</div>
						<div class="form-check form-check-inline">
						  <input class="form-check-input" type="checkbox" id="inlineCheckbox3" value="Review" checked disabled>
						  <label class="form-check-label" for="inlineCheckbox3">Review</label>
						</div>
						</div>
					</td>
					</tr>
					
					<tr>
						<td style="width: 620px;">
						<select name="searchType" class="form-select form-select-sm" style="width:100px;">
								<option value="t_subject">제목</option>
								<option value="t_writer">작성자</option>
						</select>
						</td>
						<td><input type="text" class="form-control" id="exampleFormControlInput1" name="keyword" size="10"></td>
						<td><input type="submit" class="btn btn-outline-secondary" name="submit" value="검색"></td>
					</tr>
				</table>
			</form>
			<!-- 게시판 위에 검색기능 끝 -->
			
			
			
			<div class="ta" >
				<table class="table table-hover" style="width: 800px; margin:auto;">
					<tr style="color: green;">
					
						<!-- <td style="width: 100px">tidx 번호</td> -->
						<!-- <td style="width: 400px">머리말</td> -->
						<td style="width: 400px" >제목</td>
						<td style="width: 200px">작성자</td>
						<td style="width: 100px">작성일</td>
					</tr>
					
						<!-- 반복문으로 출력 -->
						<%
						for (BoardVo bv : alist) {
						%>
					<tbody>
						<tr>
							<%-- <td><%=bv.getTidx()%></td> --%>
							<td><%if(bv.getT_head() != null) {%>[<%=bv.getT_head()%>] <%}
							for (int i = 1; i <= bv.getT_level_(); i++) {
								out.println("&nbsp;&nbsp;");
								if (i == bv.getT_level_()) {
									out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ㄴ");
								}
							}
							%>
							<!-- 프로젝트 이름/마디/컨텐츠  --> 
							<a href="<%=request.getContextPath()%>/board/boardContent.do?tidx=<%=bv.getTidx()%>"><%=bv.getT_subject()%></a>
							</td>
							<td><%=bv.getT_writer()%></td>
							<td><%=bv.getT_writeday()%></td>
						</tr>
					</tbody>
						<% } %>
				</table>
			
				<table style="width: 800px; text-align: center;">
					<tr>
						<td style="width: 200px; text-align: right;">
							<%
							String keyword = pm.getScri().getKeyword();
							String searchType = pm.getScri().getSearchType();	//파라미터 변수로 담아서 가지고 다니려고
														
							if(pm.isPrev() == true){
								out.println("<a href='"+request.getContextPath()+"/board/boardtip.do?page="+(pm.getStartPage()-1)+"&keyword="+keyword+"&searchType="+searchType+">'>◀</a>");
							}
							%>
						</td>
						<td>
							<%
							for (int i = pm.getStartPage(); i<=pm.getEndPage(); i++){
								out.println("<a href='"+request.getContextPath()+"/board/boardtip.do?page="+i+"&keyword="+keyword+"&searchType="+searchType+"'>"+i+"</a>");
							}
							%>
						</td>
						<td style="width: 200px; text-align: left;">
							<%
							System.out.println(pm.getEndPage());
							if(pm.isNext()&&pm.getEndPage() >0){
								out.println("<a href='"+request.getContextPath()+"/board/boardtip.do?page="+(pm.getEndPage()+1)+"&keyword="+keyword+"&searchType="+searchType+"'>▶</a>");
							}
							%>
						</td>
					</tr>
				</table>
				<br>
				<input type="button" name="button" class="btn btn-outline-secondary" value="홈으로"
					onclick="location.href='<%=request.getContextPath() %>/index.jsp'">
				<input type="button" name="button" class="btn btn-outline-secondary" value="글쓰기"
					onclick="location.href='<%=request.getContextPath() %>/board/boardWrite.do'">
				<br><br>
			</div>
		</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>