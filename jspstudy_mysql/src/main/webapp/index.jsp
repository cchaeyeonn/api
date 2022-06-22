<%@ page import ="jspstudy.dbconn.Dbconn" %>

<!-- index.jsp -->

<!doctype html>
<html>
<head>
  <title>HOME</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
	<link rel="stylesheet" type="text/css" href="./resources/css/style.css">
	
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

</head>
<body>
<%
Dbconn dbconn = new Dbconn(); 
System.out.println("dbconn:"+dbconn);
%>

	<jsp:include page="header.jsp"></jsp:include>
	
	<div class="index_content">
		<div class="main_container">
			<div class="conA">
				<div class="slide img1"></div>
				<div class="slide img2"></div>
				<div class="slide img3"></div>
			</div>
		</div>
	</div>
	
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>


<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- index.jsp -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page</title>

<link rel="stylesheet" type="text/css" href="./resources/css/style.css">
<link rel="stylesheet" type="text/css" href="./resources/css/style_index.css">


 
 </head>
<body style="margin:0px;">
<jsp:include page="header.jsp"></jsp:include>
<%@ include file="header.jsp" %>

	<div class="index_content">

		<div class="main_container">
			<div class="conA">
				<div class="slide img1"></div>
				<div class="slide img2"></div>
				<div class="slide img3"></div>


			</div>

		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html> --%>