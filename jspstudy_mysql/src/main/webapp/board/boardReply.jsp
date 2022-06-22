<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jspstudy.domain.BoardVo" %>
<% BoardVo bv = (BoardVo)request.getAttribute("bv"); %>
    
<%
	if(session.getAttribute("m_midx")==null){
	out.println("<script>alert('로그인해주세요');location.href='"+request.getContextPath()+"/member/memberLogin.do'</script>");
	};
	%>
	
<!-- boardReply.jsp -->


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BOARD_REPLY</title>
	
	<!-- css 받는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	
	  <script type="text/javascript">
	  //유효성 검사
	  	function check(){
		  //alert("테스트입니다.");
		  var fm = document.frm;	//변수 fm
		  
		  if(fm.t_subject.value==""){
			  alert("제목을 입력하세요");
			  fm.t_subject.focus(); 
			  retrun;
		  }
		  else if(fm.t_content.value==""){
			  alert("내용을 입력하세요");
			  fm.t_content.focus(); 
			  retrun;
		  }
		  else if(fm.t_writer.value==""){
			  alert("작성자을 입력하세요");
			  fm.t_writer.focus(); 
			  retrun;
		  }
		  
		  //alert("전송합니다");
		  
		  //action을 통해서 해당 페이지로 넘김
		   /* 프로젝트이름/board/boardWriteaction.do 라는 가상경로로 넘기기 */
		  fm.action = "<%=request.getContextPath()%>/board/boardReplyAction.do";	//front를 거쳤다가옴
		  fm.method = "post";	//post 방식으로	  
		  fm.submit();  //sumbit으로 넘김
		  
		  return;		
		  
	  	}
	  </script>  
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	 
	<div class="content">
		<br><br>
		<h3 style="text-align:center;">댓글</h3><br><br>
		
		<table class="table table-bordered" border="1" style="width:800px;">
			<form name="frm">
				<input type="hidden" name="tidx" value="<%=bv.getTidx()%>">	<!-- 쿼리에서 사용하니까 가져가려고 -->
				<input type="hidden" name="origintidx" value="<%=bv.getOrigintidx()%>">
				<input type="hidden" name="t_depth" value="<%=bv.getT_depth()%>">
				<input type="hidden" name="t_level_" value="<%=bv.getT_level_()%>">
				
				<tr>
					<td style="width:100px">제목</td>
					<td><input type="text" class="form-control" name="t_subject" size="50"></td>
				</tr>
				
				<tr>
					<td style="width:100px">내용</td>
					<td>
						<textarea name="t_content" class="form-control" placeholder="내용을 입력해주세요." cols="60" rows="10"></textarea>
					</td>
				</tr>
				
				<tr>
					<td style="width:100px">작성자</td>
					<td><input type="text" class="form-control" name="t_writer" size="50" value="<%=session.getAttribute("m_memberName")%>" readonly="readonly"></td>
				</tr>
				
				<tr>
					<td colspan=2 style="text-align:center">
						<input type="button" name ="button" class="btn btn-outline-secondary" value="확인" onclick="check();">
						<input type="button" name ="list" class="btn btn-outline-secondary" value="목록" onclick="location.href='<%=request.getContextPath()%>/board/boardtip.do'">  
						<!-- <input type="reset" class="btn btn-outline-secondary" value="리셋">  -->
					</td>
				</tr>
			</form>
		</table>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>