//Dbconn.java

package jspstudy.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconn {
	
	//����̹��� ���ؼ� DB�� ����

  //���̺귯�� ��Ͻ�Ű�� ��ü�������Ѽ� ����̹��� ����
	private String url="jdbc:mysql://127.0.0.1:3306/mysql?serverTimezone=UTC&characterEncoding=UTF-8";	//�ܺο��� �������� ���ϰ� private
	private String user="root";
	private String password="1234";


	
	public 	Connection getConnection() {
		Connection conn = null;
	
	try {
	Class.forName("com.mysql.cj.jdbc.Driver");	//����� ����̹��߿� ��밡���� Ŭ���� ã�Ƽ� ����
	conn = DriverManager.getConnection(url, user, password);	//���������� ���ؼ� ���ᰴü�� �������� conn�� ��´�
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	return conn;	//�޼ҵ�� void���� �ƴϸ� return�� �־����
	}

}



//Dbconn.java

/*package jspstudy.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbconn {
	
	//����̹��� ���ؼ� DB�� ����

  //���̺귯�� ��Ͻ�Ű�� ��ü�������Ѽ� ����̹��� ����
	private String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";	//�ܺο��� �������� ���ϰ�
	private String user="system";
	private String password="1234";
	
	
	private String url="jdbc:mysql://115.68.168.125";	//����� mysql�� �غ�����
	private String user="cyeon5";						//
	private String password="yeon51424";				//
	
	
	
	public 	Connection getConnection() {
		Connection conn = null;
	
	try {
	Class.forName("oracle.jdbc.driver.OracleDriver");	//����� ����̹��߿� ��밡���� Ŭ���� ã�Ƽ� ����
	conn = DriverManager.getConnection(url, user, password);	//���������� ���ؼ� ���ᰴü�� �������� conn�� ��´�
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	return conn;	//�޼ҵ�� void���� �ƴϸ� return�� �־����
	}

}*/