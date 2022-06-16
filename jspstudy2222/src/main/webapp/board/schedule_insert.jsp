<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="jspstudy.domain.*"%>
<%@ page import="java.util.ArrayList"%>

<%
	if ((int)session.getAttribute("m_midx") != 16) {
	
		session.setAttribute("saveUrl", request.getRequestURI()); //save가 전체값을 담는다
		out.println(
		"<script>alert('관리자 페이지입니다');location.href='" + request.getContextPath() + "/index.jsp'</script>");
	}
%>

<%
	ArrayList<ReserveDto> alist = (ArrayList<ReserveDto>)request.getAttribute("alist");	//컨트롤러에서 처리를 하고 (그 값을 담아서 여기서 꺼내서 사용)처리된 값을 담아서 여기로 넘겨서 사용하는 방법
	
	//System.out.println("alist는 "+alist.get(0).getRIDX());	//첫번째에 있는 ridx
	System.out.println("alist는 "+alist.get(1).getRIDX());
	//System.out.println("alist는 "+alist.get(2).getRIDX());
	
%>



<!-- schedule_insert.jsp -->

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>SCHEDULE_INSERT</title>
	
	<!-- css받는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	
 	<script type="text/javascript">
	  //유효성 검사
	  function check(){
		  //alert("테스트입니다.");
		   var fm = document.frm;	//변수 fm
		  
		  if(fm.rs_stime.value==""){
			  alert("시간을 입력하세요");
			  fm.rs_stime.focus(); 
			  retrun;
		  }
		  else if(fm.rs_ftime.value==""){
			  alert("시간을 입력하세요");
			  fm.rs_ftime.focus(); 
			  retrun;
		  }
		  else if(fm.rs_date.value==""){
			  alert("날짜를 입력하세요");
			  fm.rs_date.focus(); 
			  retrun;
		  }
		  else if(fm.teidx.value==""){
			  alert("선생님 번호를 입력하세요");
			  fm.teidx.focus(); 
			  retrun;
		  }
		   
		  //alert("전송합니다");
		 
		//action을 통해서 해당 페이지로 넘김
		   /* 프로젝트이름/board/boardWriteaction.do 라는 가상경로로 넘기기 */
		  	fm.action = "<%=request.getContextPath()%>/board/schedule_insertAction.do";
			fm.method = "post"; //post 방식으로	  
			fm.submit(); //sumbit으로 넘김
	
			return;
	
		}
	</script>
</head>
<body>
	<jsp:include page="../header.jsp"></jsp:include>
	<br><br>
		<h3 style="text-align:center;">스케줄 추가</h3>
				<hr><br>
		<div class="wrap">
			<div class="content">
				<table style="width: 800px;">
					<form name="frm">
					
						<tr>
							<td>시작 시간 ~ 끝나는 시간 (ex: 14:00~15:00)</td>
							<td><input type="text" class="form-control" name="rs_stime" placeholder="시간을 입력해주세요."></td>
						</tr>
						<tr>
							<td>끝나는 시간 (ex : 15:00)</td>
							<td><input type="text" class="form-control" name="rs_ftime" placeholder="끝나는 시간을 입력해주세요."></td>
						</tr>
						<tr>
							<td>날짜 (ex: 20220610)</td>
							<td><input type="text" class="form-control" name="rs_date" placeholder="날짜을 입력해주세요."></td>
						</tr>
						<tr>
							<td>선생님 번호 (1~6 중 1택/최 이 김 박 오 양)</td>
							<td><input type="text" class="form-control" name="teidx" placeholder="선생님 번호를 입력해주세요."></td>
						</tr>
						
						<tr>
							<td colspan=2 style="text-align: center">
								<input type="button" class="btn btn-outline-secondary" name="button" value="추가하기" onclick="check();">
								<input type="reset" class="btn btn-outline-secondary" value="다시 쓰기">
								<input type="button" name="button" class="btn btn-outline-secondary" value="홈으로" onclick="location.href='<%=request.getContextPath() %>/index.jsp'"> 
							</td>
						</tr>
				
					</form>
				</table>
				<div class="re" style="text-align:center;">
				<br><br>
					<h4>현재 예약가능한 스케줄</h4>
						<% for (ReserveDto rv : alist){%>
							
								<%=rv.getRS_date()%> / <%=rv.getRS_stime()%> / <%=rv.getTENAME() %> / <%=rv.getTEGENDER() %><br>
							
								
							<%}%>
						
				
				</div>
			</div>
		</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>