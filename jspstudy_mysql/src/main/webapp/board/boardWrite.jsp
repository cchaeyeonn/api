<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if (session.getAttribute("m_midx") == null) {
	
		session.setAttribute("saveUrl", request.getRequestURI()); //save가 전체값을 담는다
		out.println(
		"<script>alert('로그인해주세요');location.href='" + request.getContextPath() + "/member/memberLogin.do'</script>");
	} ;
%>

<!-- boardWrite.jsp -->

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BOARD_WRITE</title>
	
	<!-- css받는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	
	<script type="text/javascript">
	  //유효성 검사
	  function check(){
		  //alert("테스트입니다.");
		   var fm = document.frm;	//변수 fm
		  
		  if(fm.t_head.value==""){
			  alert("머리말을 입력하세요");
			  fm.t_head.focus(); 
			  retrun;
		  }
		  else if(fm.t_subject.value==""){
			  alert("제목을 입력하세요");
			  fm.t_subject.focus(); 
			  retrun;
		  }
		  else if(fm.t_content.value==""){
			  alert("내용을 입력하세요");
			  fm.t_content.focus(); 
			  retrun;
		  }
		   
		  //alert("전송합니다");
		 
		//action을 통해서 해당 페이지로 넘김
		   /* 프로젝트이름/board/boardWriteaction.do 라는 가상경로로 넘기기 */
		  	fm.action = "<%=request.getContextPath()%>/board/boardWriteAction.do";
			fm.enctype = "multipart/form-data"; //멀티형태로 파일형식 넘기기
			fm.method = "post"; //post 방식으로	  
			fm.submit(); //sumbit으로 넘김
	
			return;
	
		}
	</script>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	<br><br>
		<h3 style="text-align:center;">글쓰기</h3>
				<hr><br>
		<div class="wrap">
			<div class="content">
				<table style="width: 800px;">
					<form name="frm">
						<tr>
							<td>머리말</td>
							<td>
								<select class="form-select form-select-sm" id="hcon" name="t_head">
									<option value="">머리말을 선택하세요.</option>
									<option value="팁">팁</option>
									<option value="Q&A">Q&A</option>
									<option value="리뷰">리뷰</option>
								</select> <br> <br>
							</td>
						</tr>
							<!-- <select id="hcon" name="t_head" size="1"> -->
						
						<tr>
							<td style="width: 100px">제목</td>
							<td><input type="text" class="form-control" id="exampleFormControlInput1" name="t_subject" placeholder="제목을 입력해주세요."></td>
						</tr>
							<!-- <input type="text" name="t_subject" placeholder="제목을 입력해주세요." size="50"> -->
	
	
						<tr>
							<td style="width: 100px">내용</td>
							<td><textarea class="form-control" id="exampleFormControlTextarea1" name="t_content" placeholder="내용을 입력해주세요." rows="3"></textarea></td>
						</tr>
							<!-- <textarea name="t_content" placeholder="내용을 입력해주세요." cols="60" rows="10"></textarea> -->
	
	
					
						<tr>
							<td style="width: 100px">작성자</td>
							<td><input class="form-control" type="text" name="t_writer" value="<%=session.getAttribute("m_memberName")%>" readonly></td>
						</tr>
							<%-- <input type="text" name="t_writer" size="50" value="<%=session.getAttribute("m_memberName")%>" readonly="readonly"> --%>
				
					
						<tr>
							<td>파일 업로드</td>
							<td> <input class="form-control form-control-sm" id="formFileSm" type="file" name="t_filename"></td>
						</tr>
							<!-- <input type="file" name="t_filename"> -->
					
				
						<tr>
							<td colspan=2 style="text-align: center">
								<input type="button" class="btn btn-outline-secondary" name="button" value="작성" onclick="check();">
								<input type="reset" class="btn btn-outline-secondary" value="다시 쓰기">
								<input type="button" name ="list" class="btn btn-outline-secondary" value="목록" onclick="location.href='<%=request.getContextPath()%>/board/boardtip.do'"> 
							</td>
						</tr>
				
					</form>
				</table>
			</div>
		</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>