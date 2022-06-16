<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	request.setCharacterEncoding("UTF-8");
	String m_memberpwd = (String)request.getAttribute("m_memberpwd");
	//out.println(m_memberpwd);
%>

<!-- findPw.jsp -->

<!DOCTYPE HTML>
<HTML>
<HEAD>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE>PW_FIND</TITLE>
	
	<!-- css 받아오는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
	
	 function find(){
		  //alert("테스트입니다.");
		  var fm = document.frm;
		  
		  if(fm.m_memberid.value==""){
			  alert("아이디를 입력하세요.");
			  fm.m_memberId.focus(); 
			  retrun;
		  }
		  
		  else if(fm.m_memberphone.value==""){
			  alert("전화번호를 입력하세요.");
			  fm.M_MEMBERPHONE.focus(); 
			  retrun;
		  }
		  
	
		  
		  //action을 통해서 해당 페이지로 넘김
		  //fm.action = "./memberJoinOk.jsp";
		  	fm.action = "<%=request.getContextPath()%>/member/memberpwfind.do"; //가상경로 사용
			fm.method = "post"; //post 방식으로	  
			fm.submit(); //sumbit으로 넘김 
			
			return;
		}
	 
	 
	 
		 var replaceNotInt = /[^0-9]/gi;
		    
		    $(document).ready(function(){
		        
		        $("#inputPhone").on("focusout", function() {
		            var x = $(this).val();
		            if (x.length > 0) {
		                if (x.match(replaceNotInt)) {
		                   x = x.replace(replaceNotInt, "");
		                }
		                $(this).val(x);
		            }
		        }).on("keyup", function() {
		            $(this).val($(this).val().replace(replaceNotInt, ""));
		        });
		 
		    });
	    
	    
	
	</script>		
	
	
	
	

</HEAD>

<BODY>
	<jsp:include page="../header.jsp"></jsp:include>
		
		<br>
		<h3>비밀번호 찾기</h3>
		<div class="content">
			<hr></hr>
			<form name="frm" style="padding-top: 20px";>
				
				
				<table style="text-align: center; width: 800px; height: 175px">
					<tr>
						<td><input type="text" class="form-control" name="m_memberid" id="m_memberid" size="30" style="width:500px;" placeholder="아이디">
						</td>
						<td><input type="button" class="btn btn-outline-secondary" name="button" value="찾기" onclick="find();"></td>
					</tr>
					<tr>
						<td style="width:0px;">
						<input type="text" id="inputPhone" class="form-control" name="m_memberphone" size="30" style="width:500px;" placeholder="전화번호">
						</td>
						<td><input type="reset" class="btn btn-outline-secondary" value="다시입력"></td>
					</tr>
					<tr>
						<td>
						
						<input type="button" name="button" class="btn btn-outline-secondary" value="로그인"
							onclick="location.href='<%=request.getContextPath() %>/member/memberLogin.do'">
						<input type="button" name="button" class="btn btn-outline-secondary" value="아이디 찾기"
							onclick="location.href='<%=request.getContextPath()%>/member/findId.do'">
						</td>
					</tr>
						<tr>
						<td><% 
						if(m_memberpwd != null){
							if ("notfound".equals(m_memberpwd)){ %>
								<div id="notfound">
									회원가입된 정보가 없습니다.
								</div>
								
							<%}else{ %>
								비밀번호는 <%=m_memberpwd %> 입니다
								<%} }%>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
<%-- 		<div>
		<% if (m_memberid == null){ %>
		<%}else{ %>
			id는 <%=m_memberid %>
		</div>
	<%} %> --%>
	
	<jsp:include page="../footer.jsp"></jsp:include>
</BODY>
</HTML>
