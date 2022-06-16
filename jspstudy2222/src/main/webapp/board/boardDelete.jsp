<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jspstudy.domain.BoardVo" %>
<% BoardVo bv = (BoardVo)request.getAttribute("bv"); %>	<!-- 꺼내는 부분 -->

<!-- boardDelete.jsp -->

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BOARD_DELETE</title>
	
	<!-- css 불러오는 링크 -->
	<link rel="stylesheet" type="text/css" href="../resources/css/style.css">
	
	<script type="text/javascript">
	
		function check(){
			
			var fm = document.frm;
			
			//action을 통해서 해당 페이지로 넘김
			/* 프로젝트이름/board/boardModifyAction.do 라는 가상경로로 넘기기 */
			fm.action = "<%=request.getContextPath()%>/board/boardDeleteAction.do";	//front를 거쳤다가옴
			alert("삭제됩니다.");
			fm.method = "post";	//post 방식으로	  
			fm.submit();  //sumbit으로 넘김
			
		  return;		
		  
		}
	</script>
	
</head>

<body>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<!-- <h3 style="text-align: center;">글 삭제하기</h3> -->
	<br><br><br><br><br><br>
	<div class="content">
		<div class="content2">
			<form name="frm">
				<input type="hidden" name="tidx" value="<%=bv.getTidx() %>">	<!-- 가지고온것 꺼내기 -->
				
				<table class="table table-bordered" border="1" style="width:800px;">
					<tr>
						<td style="text-align:center">삭제하시겠습니까?</td>
					</tr>
					
					<tr>
						<td colspan=2 style="text-align:center">
							<input type="button" name ="button" class="btn btn-outline-secondary" value="삭제" onclick="check();"> 
							<input type="button" name ="button" class="btn btn-outline-secondary" value="취소" onclick="location.href='<%=request.getContextPath()%>/board/boardtip.do'">  
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>