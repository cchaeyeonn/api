<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    

<%
	request.setCharacterEncoding("UTF-8");

%>
	
	
<!-- memberJoin.html -->
<!-- memberJoin.jsp 로 이름변경-->    

<!DOCTYPE HTML>
<HTML>
<HEAD>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE>JOIN </TITLE> 
  <link rel="stylesheet" type="text/css" href="../resources/css/style.css">
  
	<script>
	  //유효성 검사
	  function check(){
		  //alert("테스트입니다.");
		  var fm = document.frm;
		  
		  if(fm.m_memberId.value==""){
			  alert("아이디를 입력하세요");
			  fm.m_memberId.focus(); 
			  retrun;
		  }
		  else if(fm.flag.value=="1"){
			  alert("사용할 수 없는 아이디 입니다.");
			  fm.flag.focus(); 
			  retrun;
		  }
		  
		  else if(fm.m_memberPwd.value==""){
			  alert("비밀번호를 입력하세요");
			  fm.m_memberPwd.focus(); 
			  retrun;
		  }
		  else if(fm.m_memberPwd2.value==""){
			  alert("비밀번호 확인을 입력하세요");
			  fm.m_memberPwd2.focus(); 
			  retrun;
		  }
		  else if(fm.m_memberPwd.value != fm.m_memberPwd2.value){
			  alert("비밀번호가 일치하지 않습니다");
			  fm.m_memberPwd2.value="";
			  fm.m_memberPwd2.focus(); 
			  retrun;
		  }
		  else if(fm.m_memberName.value==""){
			  alert("이름을 입력하세요");
			  fm.m_memberName.focus(); 
			  retrun;
		  }
		  /* else if(fm.memberEmail.value==""){
			  alert("이메일을 입력하세요");
			  fm.memberEmail.focus(); 
			  retrun;
		  } */
		  
		  else if(fm.m_memberphone.value==""){
			  alert("연락처를 입력하세요");
			  fm.m_memberphone.focus(); 
			  retrun;
		  }
		  
		  
		  alert("회원가입되었습니다.");
		  //action을 통해서 해당 페이지로 넘김
		  //fm.action = "./memberJoinOk.jsp";
		  fm.action = "<%=request.getContextPath()%>/member/memberJoinAction.do";	//가상경로 사용
		  fm.method = "post";	//post 방식으로	  
		  fm.submit();  //sumbit으로 넘김
		  
		  return;				  
		  
	  }
  
	</script>
	
	
	
	<!-- jquery 사용하기 위해 CDN 사용 -->
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script>
	function idCheck(){
		
		//$('.form-control').focusout(function(){
			
			let m_memberId = $('.form-control').val();	//form-control에 입력되는 값
			//alert("m_memberId: "+m_memberId);
			
			$.ajax({
				url : "<%=request.getContextPath()%>/member/memberIdcheck.do",
				type : "post",
				data :{"userId": m_memberId},	//userId값으로 m_memberId값을 넘김(키값)
				dataType : 'json',	//json 형식으로 보내줌
				success : function(data){
					if(data.result == 0){
						$("#checkId").html('사용할 수 없는 아이디입니다.');
						$("#checkId").attr('color','red');
						$("#flag").val("1");
						
					}else{
						$("#checkId").html('사용할 수 있는 아이디입니다.');
						$("#checkId").attr('color','green');
						$("#flag").val("0");
					}
				},
				error : function(){
					alert("서버요청실패");
				}
			});
		//})
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

<BODY><center>
	 <div class="page">
	 	<jsp:include page="../header.jsp"></jsp:include>
	 
	 	<br><br>
		<center><h3>회원가입</h3>
		 
		<div class="content">
		
			<hr></hr>
			<form name="frm" style="padding-top: 20px;">  <!-- action="./memberJoinOk.jsp" method="post" -->
				
				<table style="text-align:left;width:800px;height:300px">
					<tr>
						<td>아이디</td>
						<td>
						<input type="hidden" name="flag" value="0" id="flag">
						<input type="text" class="form-control" name="m_memberId" size="30" onblur="idCheck();" placeholder="아이디">
						<font id="checkId" size="2"></font>
						</td>
					</tr>
					
					<tr>
						<td>비밀번호</td>
						<td><input type="password" class="form-control" name="m_memberPwd" size="30" placeholder="비밀번호"></td>
					</tr>
					
					<tr>
						<td>비밀번호확인</td>
						<td><input type="password" class="form-control" name="m_memberPwd2" size="30" placeholder="비밀번호 확인"></td>
					</tr>
					
					<tr>
						<td>이름</td>
						<td><input type="text" class="form-control" name="m_memberName" size="30" placeholder="이름"></td>
					</tr>
					
					<!-- <tr>
						<td>이메일</td>
						<td><input type="email" name="memberEmail" size="30"></td>
					</tr> -->
					
					<tr>
						<td>성별</td>
						<td>
							<input type="radio" name ="m_memberGender" value="M" class="btn-check" name="options-outlined" id="success-outlined" autocomplete="off" checked>
							<label class="btn btn-outline-secondary" for="success-outlined">남자</label>
							<input type="radio" name ="m_memberGender" value="F" class="btn-check" name="options-outlined" id="danger-outlined" autocomplete="off">
							<label class="btn btn-outline-secondary" for="danger-outlined">여자</label>
						</td>
					</tr>
						<!-- <input type="radio" name ="m_memberGender" value="M" checked>남자
						<input type="radio" name ="m_memberGender" value="F">여자 -->
					
					<tr>
						<td>연락처 (숫자만 입력)</td>
						<td>
							<input type="text" id="inputPhone" class="form-control" name="m_memberphone" size="30" maxlength='11' placeholder="연락처 (숫자만 입력하세요)">
						</td>
					</tr>
					
					<tr>
						<td></td>
						<td>
							<input type="button" name ="button" class="btn btn-outline-secondary" value="확인" onclick="check();"> 
							<input type="reset" class="btn btn-outline-secondary" value="다시작성"> 
							<input type="button" name ="button" class="btn btn-outline-secondary" value="홈으로" onclick="location.href='<%=request.getContextPath() %>/index.jsp'">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</BODY>
</HTML>
