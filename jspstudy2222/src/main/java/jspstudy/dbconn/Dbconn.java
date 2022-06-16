//Dbconn.java

package jspstudy.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconn {
	
	//드라이버를 통해서 DB에 연결

  //라이브러리 등록시키고 객체생성시켜서 드라이버에 접속
	private String url="jdbc:mysql://127.0.0.1:3306/mysql?serverTimezone=UTC&characterEncoding=UTF-8";	//외부에서 접근하지 못하게 private
	private String user="root";
	private String password="1234";


	
	public 	Connection getConnection() {
		Connection conn = null;
	
	try {
	Class.forName("com.mysql.cj.jdbc.Driver");	//등록한 드라이버중에 사용가능한 클래스 찾아서 생성
	conn = DriverManager.getConnection(url, user, password);	//연결정보를 통해서 연결객체를 참조변수 conn에 담는다
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	return conn;	//메소드는 void값이 아니면 return이 있어야함
	}

}



//Dbconn.java

/*package jspstudy.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconn {
	
	//드라이버를 통해서 DB에 연결

  //라이브러리 등록시키고 객체생성시켜서 드라이버에 접속
	private String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";	//외부에서 접근하지 못하게
	private String user="system";
	private String password="1234";
	
	
	private String url="jdbc:mysql://115.68.168.125";	//여기는 mysql로 해봤을때
	private String user="cyeon5";						//
	private String password="yeon51424";				//
	
	
	
	public 	Connection getConnection() {
		Connection conn = null;
	
	try {
	Class.forName("oracle.jdbc.driver.OracleDriver");	//등록한 드라이버중에 사용가능한 클래스 찾아서 생성
	conn = DriverManager.getConnection(url, user, password);	//연결정보를 통해서 연결객체를 참조변수 conn에 담는다
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	return conn;	//메소드는 void값이 아니면 return이 있어야함
	}

}*/