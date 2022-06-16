<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="jspstudy.domain.*"%>
<%@ page import="java.util.ArrayList"%>


<%
	if (session.getAttribute("m_midx") == null) {	//세션값이 없을 때
		session.setAttribute("saveUrl", "http://localhost:8090/jspstudy/board/reserve.do"); //save가 전체값을 담는다
		//안넘어오는 오류가 났었는데 이유는 saveurl이 그 전 주소값을 저장하고 있는데 그 값을 reserve.jsp로 계속 담고있어서 오류가 난거였음
		out.println(
		"<script>alert('로그인이 필요합니다.');location.href='" + request.getContextPath() + "/member/memberLogin.do'</script>");		
	}
	else{	//세션값이 존재
		if ((int)session.getAttribute("m_midx") == 16) {	//세션값이 존재하는데 m_midx가 16번 일때(master)
			
			session.setAttribute("saveUrl", request.getRequestURI()); //save가 전체값을 담는다
			out.println(
			"<script>alert('관리자는 예약할 수 없습니다.');location.href='" + request.getContextPath() + "/index.jsp'</script>");
		}
	}
%>


<%
	request.setCharacterEncoding("UTF-8");
%>


<!-- reserve.jsp -->

<%
	ArrayList<ReserveDto> alist = (ArrayList<ReserveDto>)request.getAttribute("alist");	//컨트롤러에서 처리를 하고 (그 값을 담아서 여기서 꺼내서 사용)처리된 값을 담아서 여기로 넘겨서 사용하는 방법
	
	//System.out.println("alist는 "+alist.get(0).getRIDX());	//첫번째에 있는 ridx
	System.out.println("alist는 "+alist.get(1).getRIDX());
	//System.out.println("alist는 "+alist.get(2).getRIDX());
	
%>

<!DOCTYPE HTML>
<HTML>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE>RESERVE</TITLE>
	
	<!-- css 받는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	
	
	<script>
	 	function reserve(){
			 //alert("테스트입니다.");
	 		var fm = document.frm;

	 		
			 //action을 통해서 해당 페이지로 넘김
			   /* 프로젝트이름/board/reserve.do 라는 가상경로로 넘기기 */
			  	fm.action = "<%=request.getContextPath()%>/board/reserveAction.do"; //front를 거쳤다가옴
				fm.method = "post"; //post 방식으로	  
				fm.submit(); //sumbit으로 넘김
				//alert("예약 되었습니다");
				return; 
	
			}
			 
	</script>

</head>

<body><center>
	<jsp:include page="../header.jsp"></jsp:include>
		<div class="content">
			<center>
			<br><br>
			<h3>예약</h3>
			<hr></hr>
		
			<div class="reserve">
				<form name="frm">
					<div class="date">
						<span> 원하는 시간을 선택후 예약하세요.</span><br><br>
						<span> 예약 완료 후엔 취소가 어려우니 신중한 예약 부탁드립니다.</span><br><br>
						<span> 예약 가능한 날짜(년월일) / 시간 / 선생님 성함 / 선생님 성별</span><br><br>
								
						<select class="form-select form-select-sm" value="" name="rsidx" style="width: 350px;">
						<% for (ReserveDto rv : alist){%>
							<option value="<%=rv.getRSIDX()%>">
								<%=rv.getRS_date()%> / <%=rv.getRS_stime()%> / <%=rv.getTENAME() %> / <%=rv.getTEGENDER() %>
							</option>
								
							<%}%>
						</select>	
						<br>
						<input type="button" name="button" class="btn btn-outline-secondary" value="예약하기" onclick="reserve()"></div>
				</form>
			</div>
		</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</HTML>
