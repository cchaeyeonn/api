/*FrontController.java(Servlet으로 생성)*/

package jspstudy.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());	//글자깨짐에 영향을 줄수있어서 주석
		
		//웹 설정파일에 등록(web.xml)
		String uri = request.getRequestURI();
		String pj = request.getContextPath();
		String command = uri.substring(pj.length());	//프로젝트이름을 뺀 나머지 가상경로
		//ex)	/member/memberList.do
		
		
		//가상경로 설정을 위해서 작성함
		String[] subpath = command.split("/");	// /로 잘라서 구분자
		String location = subpath[1];	//member 문자열이 추출 (1위치) 0,1,2..
		

		if(location.equals("member")) {
			MemberController mc = new MemberController();
			mc.doGet(request, response);
			
		}
		else if(location.equals("board")) {
			BoardController bc = new BoardController();
			bc.doGet(request, response);
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
