/* �Ⱦ� BoardController.java(servlet���� ����)*/

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
		request.setCharacterEncoding("UTF-8"); // �ѱ۱��� ����
		response.setContentType("text/html;charset=UTF-8"); // �ѱ۱��� ����

		// �����η� �� request�� ������ ó��
		String uri = request.getRequestURI();
		String pj = request.getContextPath();
		String command = uri.substring(pj.length());
		
		
		
		
		if (command.equals("/reservation/reserve.do")) {
			
			
			// �޼ҵ带 ȣ���ؼ�  alist ��´�
			BoardDao bd = new BoardDao();
			ArrayList<ReserveDto> alist = bd.reservelist();
			

			 // ���������� �ڿ��� �����ؼ� ���� ���� ��� �޼ҵ�(setAttribute)
			// ȭ������ ��������
			request.setAttribute("alist", alist);
			
			// forward�� �����η� �������� ������� ������η� ����
			RequestDispatcher rd = request.getRequestDispatcher("/reservation/reserve.jsp");
			rd.forward(request, response); 
			// forward������� �ѱ��
			
		}
		else if (command.equals("/reservation/reserveAction.do")) {
			System.out.println("���� ó�� ȭ������ ������");
		

			//int midx = 2; // �ϴ� 2��ȸ���� ��ٰ� ����(test��)�α��� ��� �Ŀ� �����ؾ���
			HttpSession session = request.getSession();
			int m_midx = (int)session.getAttribute("m_midx");	//����ȯ

			// BoardDao���� ���� insert ���� �޼ҵ� ���
			BoardDao bd = new BoardDao(); // ������ ȣ��
			int rsidx = Integer.parseInt(request.getParameter("time"));
			System.out.println(request.getParameter("date"));
			System.out.println(rsidx);
			int value = bd.ReserveBoard(rsidx, m_midx);

			// value���� 1�̸� ����Ǿ���
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/reservation/reserve.do"); // ����ȵǸ� �����ּҷ�
			}
		}
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}*/
