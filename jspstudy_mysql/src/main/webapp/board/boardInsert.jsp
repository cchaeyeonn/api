<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%

	request.setCharacterEncoding("UTF-8");

	String subject = request.getParameter("subject");
	String content = request.getParameter("content");
	String writer = request.getParameter("writer"); 
	String memberphone = request.getParameter("memberphone");
	
	out.println(subject + "<br>");
	out.println(content + "<br>");
	out.println(writer + "<br>");	
	
	
%>	

<!--
String memberEmail = request.getParameter("memberEmail");
	String memberJumin = request.getParameter("memberJumin");
	String memberGender = request.getParameter("memberGender");
	String memberAddr = request.getParameter("memberAddr");
	String[] memberHobby = request.getParameterValues("memberHobby");	//배열로 받음
	//String memberHobby = request.getParameter("memberHobby"); */
	
	/* //배열안에 있는거 출력하기
	for(int i=0; i<memberHobby.length; i++){
		out.println(memberHobby[i]+"<br>");
	}

	/* 
	out.println(memberEmail + "<br>");
	out.println(memberphone + "<br>");
	out.println(memberJumin + "<br>");
	out.println(memberGender + "<br>");
	out.println(memberAddr + "<br>"); */
	//out.println(memberHobby + "<br>");
 -->



<!-- boardInsert.jsp -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BOARD_INSERT</title>
</head>
</html>