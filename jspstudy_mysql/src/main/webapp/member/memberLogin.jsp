<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	request.setCharacterEncoding("UTF-8");
%>

<!-- memberLogin.jsp -->

<!DOCTYPE HTML>
<HTML>
<HEAD>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE>LOGIN</TITLE>
	
	<!-- css 받아오는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	
	<script>
	  //유효성 검사
	  function check(){
		  //alert("테스트입니다.");
		  var fm = document.frm;
		  
		  if(fm.m_memberId.value==""){
			  alert("아이디를 입력하세요.");
			  fm.m_memberId.focus(); 
			  retrun;
		  }
		  
/* 		  else if(fm.idDuplication.value!="idCheck"){
			  alert("아이디 중복체크를 해주세요.")
		  } */
		  
		  else if(fm.m_memberPwd.value==""){
			  alert("비밀번호를 입력하세요.");
			  fm.m_memberPwd.focus(); 
			  retrun;
		  }
		  
		  
/* 		  function fn_dbCheckId(){
			  var fm = document.frm;
			  var id = fm.id.value;
			  if(id.length==0 || id==""){
				  fm.id.focus();
			  }else{
				  window.open("${contextPath}/dbCheckId.do?m_memberid="+id,"","width=500, height=300");
			  }
		  } */
	
		  
		  //action을 통해서 해당 페이지로 넘김
		  //fm.action = "./memberJoinOk.jsp";
		  	fm.action = "<%=request.getContextPath()%>/member/memberLoginAction.do"; //가상경로 사용
			fm.method = "post"; //post 방식으로	  
			fm.submit(); //sumbit으로 넘김 
			
			return;
		}
	  
	</script>		
	
	
	<style>
		a:link{color:black;}
		a:visited{color:black;}
	</style>
	
	

</HEAD>

<BODY>
	<jsp:include page="../header.jsp"></jsp:include>
		
		<br>
		<h3>로그인</h3>
		<div class="content">
			<hr></hr>
			<form name="frm" style="padding-top: 20px;>
				<!-- action="./memberJoinOk.jsp" method="post" -->
				<table style="text-align: left; width: 800px; height: 175px">
					<tr>
						<td>아이디</td>
						<td><input type="text" class="form-control" name="m_memberId" size="30" style="width:225px"" placeholder="아이디";>
						</td>
						<td><input type="button" class="btn btn-outline-secondary" name="button" value="로그인" onclick="check();"></td>
						
						<!--<td><button type="button" onclick="fn_deCheckId()" name="dbCheckId" class="btn btn-outline-secondary">중복확인</button></td>
						 아이디 중복 체크 여부 
						<input type="hidden" name="idDuplication" value="idUncheck"/>-->
					</tr>
					<tr>
						<td>비밀번호</td>
						<td style="width:0px;">
						<input type="password" class="form-control" name="m_memberPwd" size="30" placeholder="비밀번호"></td>
						<td>
						<input type="reset" class="btn btn-outline-secondary" value="다시입력"></td>
					</tr>
				</table>
			</form>
				<center>
				<input type="button" name="button" class="btn btn-outline-secondary" value="회원가입 하기"
					onclick="location.href='<%=request.getContextPath() %>/member/memberJoin.do'">
					
				<input type="button" name="button" class="btn btn-outline-secondary" value="홈으로"
					onclick="location.href='<%=request.getContextPath()%>/index.jsp'">
					
					<br><br>
					
				<%-- <input type="button" name="button" class="btn btn-outline-secondary" value="아이디 찾기"
					onclick="location.href='<%=request.getContextPath()%>/member/findId.do'">
					
				<input type="button" name="button" class="btn btn-outline-secondary" value="비밀번호 찾기"
					onclick="location.href='<%=request.getContextPath()%>/member/findPw.do'"> --%>
					
					<div>
					<a id="find" style="text-decoration: none;" href="<%=request.getContextPath() %>/member/findId.do">아이디 찾기</a>
					&nbsp;&nbsp; | &nbsp;&nbsp;
					<a id="find" style="text-decoration: none;" href="<%=request.getContextPath() %>/member/findPw.do">비밀번호 찾기</a>
					</div>
				
				</center>
		</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</BODY>
</HTML>
