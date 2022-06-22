<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>	<!-- ArrayList 사용하기 위해 import함 -->
<%@ page import="jspstudy.domain.*" %>		<!-- MemberVo 값을 받는걸 import해줌 -->


<!-- function.jsp -->    
<%!
//insert구문 실행
public int insertMember(Connection conn,String memberId,String memberPwd,String memberName,String memberGender,String memberAddr,String memberJumin,String memberphone,String hobby,String memberEmail,String ip){
	int value=0;
	PreparedStatement pstmt = null;
	
	String sql = "insert into b_member(MIDX,MEMBERID,MEMVERPWD,MEMBERNAME,MEMBERGENDER,MEMBERADDR,MEMBERJUMIN,MEMBERPHONE,MEMBERHOBBY,MEMBEREMAIL,MEMBERIP)"
			+"values(midx_b_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
	
	
	try{
		pstmt = conn.prepareStatement(sql);	//문자열의 쿼리가 구문화 됨
		pstmt.setString(1, memberId);	//첫번째 물음표에 memberId 매칭
		pstmt.setString(2, memberPwd);	//넘어온건 매개변수값
		pstmt.setString(3, memberName);
		pstmt.setString(4, memberGender);
		pstmt.setString(5, memberAddr);
		pstmt.setString(6, memberJumin);
		pstmt.setString(7, memberphone);
		pstmt.setString(8, hobby);
		pstmt.setString(9, memberEmail);
		pstmt.setString(10, ip);
		value = pstmt.executeUpdate();
		
		//좀더 보안성 강하게 prepareStatement 사용
		
		
	Statement stmt = conn.createStatement();	//연결객체를 통해서 Statement 구문객체를 생성해서 stmt에 담는다 / 구문을 사용할수있는 클래스 생성 //구문을 만들때 사용
	value = stmt.executeUpdate(sql);	//구문을 실행하고 리턴값으로 실행되었으면 1 아니면 0을 value변수에 담는다
	}catch(Exception e){
		e.printStackTrace();
	}
	
	
	
	return value;
	
}


public ArrayList<MemberVo> memberSelectAll(Connection conn){
	//MemberVo 여러 객체를 담는 ArrayList 클래스를 객체생성한다
	ArrayList<MemberVo> alist = new ArrayList<MemberVo>();	// membervo를 arraylist에 담음
	PreparedStatement pstmt = null;	//선언
	ResultSet rs = null;	//선언
	
	String sql="select * from b_member where delyn='N' order by midx desc";	//쿼리구문을 문자열로 만들어 놓는다
	
	try{
		pstmt = conn.prepareStatement(sql);	//연결객체에 있는 prepareStatement메소드를 실행해서 sql문자열을 담아 구문객체를 만든다
		rs = pstmt.executeQuery();	//실행시켜 rs에 담는다
	
	
		while(rs.next()){	//반복문 사용, 데이터를 받아와서 다음값이 있는지 없는지 확인 있으면 참 //rs.next는 전용클래스 다음데이터가 존재하면 true
			MemberVo mv = new MemberVo();	//반복할때마다 객체생성한다(mv 객체생성)
			//rs에 담아놓은 컬럼값들을 mv에 옮겨담는다
			mv.setMidx(rs.getInt("midx")); 	//객체생성, getInt값을 setMidx에 담는다
			mv.setMembername(rs.getString("memberName"));
			mv.setMemberphone(rs.getString("memberphone"));
			mv.setWriteday(rs.getString("writeday"));	
			alist.add(mv);	//arraylist에 값을 담는 객체를 추가
		}		
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return alist;
}

%> --%>