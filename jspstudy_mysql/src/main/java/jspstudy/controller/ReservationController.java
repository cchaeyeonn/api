/* 안씀 BoardController.java(servlet으로 생성)*/

/*package jspstudy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jspstudy.domain.BoardVo;
import jspstudy.domain.Criteria;
import jspstudy.domain.MemberVo;
import jspstudy.domain.PageMaker;
import jspstudy.domain.ReserveDto;
import jspstudy.domain.SearchCriteria;
import jspstudy.service.BoardDao;
import jspstudy.service.MemberDao;

@WebServlet("/ReservationController")
public class ReservationController extends HttpServlet {
	private static final long serialVersionUID = 1L;*/

	/*
	 * public BoardController() { super(); }
	 */

	/*protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 한글깨짐 방지
		response.setContentType("text/html;charset=UTF-8"); // 한글깨짐 방지

		// 가상경로로 온 request가 있으면 처리
		String uri = request.getRequestURI();
		String pj = request.getContextPath();
		String command = uri.substring(pj.length());
		
		
		
		
		if (command.equals("/reservation/reserve.do")) {
			
			
			// 메소드를 호출해서  alist 담는다
			BoardDao bd = new BoardDao();
			ArrayList<ReserveDto> alist = bd.reservelist();
			

			 // 내부적으로 자원을 공유해서 쓸때 값을 담는 메소드(setAttribute)
			// 화면으로 가져간다
			request.setAttribute("alist", alist);
			
			// forward는 가상경로로 들어왔으면 포워드로 실제경로로 들어가게
			RequestDispatcher rd = request.getRequestDispatcher("/reservation/reserve.jsp");
			rd.forward(request, response); 
			// forward방식으로 넘긴다
			
		}
		else if (command.equals("/reservation/reserveAction.do")) {
			System.out.println("예약 처리 화면으로 들어왔음");
		

			//int midx = 2; // 일단 2번회원이 썼다고 지정(test용)로그인 기능 후엔 변경해야함
			HttpSession session = request.getSession();
			int m_midx = (int)session.getAttribute("m_midx");	//형변환

			// BoardDao에서 만든 insert 구문 메소드 사용
			BoardDao bd = new BoardDao(); // 생성자 호출
			int rsidx = Integer.parseInt(request.getParameter("time"));
			System.out.println(request.getParameter("date"));
			System.out.println(rsidx);
			int value = bd.ReserveBoard(rsidx, m_midx);

			// value값이 1이면 실행되었다
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/reservation/reserve.do"); // 실행안되면 가상주소로
			}
		}
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}*/
