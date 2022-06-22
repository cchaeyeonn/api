//MemberController.java

package jspstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jspstudy.domain.MemberVo;
import jspstudy.service.BoardDao;
import jspstudy.service.MemberDao;

@WebServlet("/MemberController") // .do�� ������ ������ membercontroller���� ó��
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8"); // �ѱ۱��� ����

		String uri = request.getRequestURI();

		String pj = request.getContextPath();
		String command = uri.substring(pj.length());

		
//ȸ������ ó���κ� ����
		if (command.equals("/member/memberJoinAction.do")) {

			// getParameter�� �̿��ؼ� ������ ��� ȣ����
			String m_memberId = request.getParameter("m_memberId");
			String m_memberPwd = request.getParameter("m_memberPwd");
			String m_memberName = request.getParameter("m_memberName");
			String m_memberphone = request.getParameter("m_memberphone");
			String m_memberJumin = request.getParameter("m_memberJumin");
			String m_memberGender = request.getParameter("m_memberGender");

			// ip����
			String ip = InetAddress.getLocalHost().getHostAddress(); // java ���� //ip�� ����

			MemberDao md = new MemberDao();
			/*int value = md.insertMember(memberId, memberPwd, memberName, memberGender, memberAddr, memberJumin,
					memberphone, hobby, memberEmail, ip); // insertMember���Ϲ޾Ƽ� ȣ��*/
			
			int value = md.insertMember(m_memberId, m_memberPwd, m_memberName, m_memberGender,
					m_memberphone, ip); // insertMember���Ϲ޾Ƽ� ȣ��

			PrintWriter out = response.getWriter();
			
			

			// �Է��� �Ǿ�����(1)
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				// out.println("<script>alert('ȸ������ ����');
				// location.href='"+request.getContextPath()+"'</script>");
			} else {
				response.sendRedirect(request.getContextPath() + "/member/memberJoin.do"); // ������
				// out.println("<script>alert('ȸ������ ����');
				System.out.println("ȸ������ ����");
				// location.href='./memberJoin.html'</script>");
			}
		}

		
		
//ȸ������ ȭ�� �κ� ����
		else if (command.equals("/member/memberJoin.do")) {
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberJoin.jsp");
			rd.forward(request, response);
		}
		
		
		
//��� ����Ʈ �κ� ����
		else if (command.equals("/member/memberList.do")) {

			MemberDao md = new MemberDao();  //memberdao�� ��ü�������Ѽ� ��� 
			ArrayList<MemberVo> alist = md.memberSelectAll();

			request.setAttribute("alist", alist); // ���������� �ڿ��� �����ؼ� ���� ���� ��� �޼ҵ�(setAttribute)

			RequestDispatcher rd = request.getRequestDispatcher("/member/memberList.jsp");
			rd.forward(request, response);
		}
		
		

//ȸ�� �α��� �κ� ȭ�� ����
		else if (command.equals("/member/memberLogin.do")) {

			// �̵��ϴ� �κ� ��Ÿ����
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberLogin.jsp"); // �̵���Ű�� �κ�
			rd.forward(request, response); // forward������� �ѱ��
		}

		
//ȸ�� �α��� ó�� �κ� ����
		else if (command.equals("/member/memberLoginAction.do")) {
			
			// 1.�Ѿ�°�
			String m_memberId = request.getParameter("m_memberId");
			String m_memberPwd = request.getParameter("m_memberPwd");
			

			// 2.ó���ϴ� �κ�
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLogin(m_memberId, m_memberPwd);
			 //System.out.print("mv"+mv);

			HttpSession session = request.getSession(); // ���� ������
			PrintWriter out=response.getWriter();

			// 3.�̵��ϴ� �κ�
			
			if (mv != null) { // ���� ������
				session.setAttribute("m_midx", mv.getM_midx());
				session.setAttribute("m_memberId", mv.getM_memberid());
				session.setAttribute("m_memberName", mv.getM_membername());
				
				BoardDao bd = new BoardDao();

				
				if (session.getAttribute("saveUrl") != null) {	//���� ������ ���� ������
					session.setAttribute("alist", bd.reservelist());
					response.sendRedirect((String) session.getAttribute("saveUrl")); // ���ǰ��� saveurl�̶�� ���� ��� �̵�
					
				}
				else {
					
					out.println("<script>alert('�α��� �Ǿ����ϴ�.');location.href='"+request.getContextPath()+"/index.jsp'</script>");
					//response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
			} else {
				//request.setAttribute("loginResult", "fail");
				//RequestDispatcher rd = request.getRequestDispatcher("/member/memberLogin.jsp");
				//rd.forward(request, response);
				out.println("<script>alert('�α��ο� �����߽��ϴ�.');location.href='"+request.getContextPath()+"/member/memberLogin.do'</script>");
				//response.sendRedirect(request.getContextPath() + "/member/memberLogin.do");
			}

		}

		
		
//ȸ�� �α׾ƿ� �κ� ����
		else if (command.equals("/member/memberLogout.do")) {
			HttpSession session = request.getSession();
			session.invalidate(); // �ʱ�ȭ��
			response.sendRedirect(request.getContextPath() + "/");
		}
		
		
		
		
//���̵� üũ �κ� ����
		else if (command.equals("/member/memberIdcheck.do")) {
			//System.out.println("�Ѿ��");
			String userId = request.getParameter("userId");	//�Ѿ�°��� �޾ƿ�
			
			MemberDao md = new MemberDao();	//��ü����
			int value = md.checkId(userId);	//�����ϸ� value ������
			//System.out.println("value: "+value);
			
			
			PrintWriter out = response.getWriter();
			out.println("{\"result\":\""+value+"\"}");	//\���� ������ "�� ����ϱ� ����
			
		}
		
		
		
//���̵� ã�� ȭ��
		else if (command.equals("/member/findId.do")) {
			RequestDispatcher rd = request.getRequestDispatcher("/member/findId.jsp");
			rd.forward(request, response);
			
		}
		
		
		
//���̵� ã�� ó�� �κ� ����
		else if (command.equals("/member/memberidfind.do")) {
			String m_membername = request.getParameter("m_membername");
			String m_memberphone = request.getParameter("m_memberphone");
			
			MemberDao md = new MemberDao();
			String m_memberid = md.findId(m_membername,m_memberphone);
			//MemberVo mv = md.findId(m_membername,m_memberphone);	//���
			if(m_memberid != null){
				request.setAttribute("m_memberid", m_memberid);
			}
			else {
				request.setAttribute("m_memberid", "notfound");
			}
			
			//System.out.println("md.findId(m_membername,m_memberphone) :"+md.findId(m_membername,m_memberphone));
			
			//�ѱ�
			RequestDispatcher rd = request.getRequestDispatcher("/member/findId.jsp");
			rd.forward(request, response);
			
		}
		
		
//��й�ȣ ã�� ȭ��
				else if (command.equals("/member/findPw.do")) {
					RequestDispatcher rd = request.getRequestDispatcher("/member/findPw.jsp");
					rd.forward(request, response);
					
				}
		

		
//��й�ȣ ã�� ó�� �κ� ����
				else if (command.equals("/member/memberpwfind.do")) {
					String m_memberid = request.getParameter("m_memberid");
					String m_memberphone = request.getParameter("m_memberphone");
					
					MemberDao md = new MemberDao();
					String m_memberpwd = md.findPw(m_memberid,m_memberphone);
					//MemberVo mv = md.findPw(m_memberid,m_memberphone);	//���
					
					if(m_memberpwd != null){
						request.setAttribute("m_memberpwd", m_memberpwd);
					}
					else {
						request.setAttribute("m_memberpwd", "notfound");
					}
					//request.setAttribute("m_memberpwd", mv.getM_memberpwd());
					
					//�ѱ�
					RequestDispatcher rd = request.getRequestDispatcher("/member/findPw.jsp");
					rd.forward(request, response);
					
				}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
