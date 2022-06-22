<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@ page import="jspstudy.domain.BoardVo" %>
<% BoardVo bv = (BoardVo)request.getAttribute("bv"); %>

<%
if(session.getAttribute("m_midx")==null){
	out.println("<script>alert('로그인해주세요');location.href='"+request.getContextPath()+"/member/memberLogin.do'</script>");};
	%>
	
<!-- boardModify.jsp -->


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BOARD_MODIFT</title>
	
	<!-- css 받는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	
	 <script type="text/javascript">
	  //유효성 검사
	  function check(){
		  //alert("테스트입니다.");
		  var fm = document.frm;	//변수 fm
		  
		  if(fm.t_subject.value==""){
			  alert("제목을 입력하세요");
			  fm.subject.focus(); 
			  retrun;
		  }
		  else if(fm.t_content.value==""){
			  alert("내용을 입력하세요");
			  fm.content.focus(); 
			  retrun;
		  }
		  else if(fm.t_writer.value==""){
			  alert("작성자을 입력하세요");
			  fm.writer.focus(); 
			  retrun;
		  }
		  //alert("전송합니다");
		  
		  //action을 통해서 해당 페이지로 넘김
		   /* 프로젝트이름/board/boardModifyAction.do 라는 가상경로로 넘기기 */
		  fm.action = "<%=request.getContextPath()%>/board/boardModifyAction.do";	//front를 거쳤다가옴
		  fm.method = "post";	//post 방식으로	  
		  fm.submit();  //sumbit으로 넘김
		  
		  return;		
	  }
	 </script>
</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<div class="content" style="text-align:center;">
		<h3>글 수정하기</h3>
		<hr><br>
		
		<table class="table table-bordered" border="1" style="width:800px; text-align:left;">
			<form name="frm">
				<input type="hidden" name="tidx" value="<%=bv.getTidx() %>">	<!-- hidden으로 넘김 -->
				
				<%
				if(bv.getT_head() != null){%>
					<tr>
						<td>머리말</td><td>[<%=bv.getT_head()%>] </td>
					</tr>
				<%}%>
				
				<tr>
					<td style="width:100px">제목</td>
					<td> <input type ="text" class="form-control" name="t_subject" placeholder="제목을 입력해주세요." value="<%=bv.getT_subject() %>"></td>
				</tr>
				
				<tr>
					<td style="width:100px">내용</td>
					<td>
					<textarea name="t_content" class="form-control" placeholder="내용을 입력해주세요." cols="60" rows="10">
					<%=bv.getT_content().trim() %></textarea>
					</td>
				</tr>
				
				<tr>
					<td style="width:100px">작성자</td>
					<td><input type="text" name="t_writer" class="form-control" size="50" value="<%=bv.getT_writer()%>" readonly="readonly"></td>
				</tr>
				
				<tr>
					<td colspan=2 style="text-align:center">
						<input type="button" class="btn btn-outline-secondary" name ="button" value="확인" onclick="check();"> 
						<input type="button" name ="list" class="btn btn-outline-secondary" value="목록" onclick="location.href='<%=request.getContextPath()%>/board/boardtip.do'"> 
					</td>
				</tr>
			</form>
		</table>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>