/*ServletTest.java*/

package jspstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//자바로 만든 웹페이지
@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet {	//http통신을 하기위해 자바에서 제공함 (웹을 실행하기 위함)
	private static final long serialVersionUID = 1L;	//long타입

//    public ServletTest() {	//생성자(클래스이름과 동일) 생략가능
//        super();
//    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");	//한글깨짐 방지
		
		PrintWriter out = response.getWriter();
		out.println("<html>"
				+ "<head><title>서블릿</title>"
				+ "</head>"
				+"<body>"
				+ "<h1>안녕하세요</h1>"
				+"<h2>반갑습니다.</h2>"
				+"</body>"
				+ "</html>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
