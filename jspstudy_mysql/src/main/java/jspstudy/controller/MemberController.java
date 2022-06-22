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

@WebServlet("/MemberController") // .do로 들어오는 모든것은 membercontroller에서 처리
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8"); // 한글깨짐 방지

		String uri = request.getRequestURI();

		String pj = request.getContextPath();
		String command = uri.substring(pj.length());

		
//회원가입 처리부분 시작
		if (command.equals("/member/memberJoinAction.do")) {

			// getParameter를 이용해서 변수에 담아 호출함
			String m_memberId = request.getParameter("m_memberId");
			String m_memberPwd = request.getParameter("m_memberPwd");
			String m_memberName = request.getParameter("m_memberName");
			String m_memberphone = request.getParameter("m_memberphone");
			String m_memberJumin = request.getParameter("m_memberJumin");
			String m_memberGender = request.getParameter("m_memberGender");

			// ip추출
			String ip = InetAddress.getLocalHost().getHostAddress(); // java 폴더 //ip가 나옴

			MemberDao md = new MemberDao();
			/*int value = md.insertMember(memberId, memberPwd, memberName, memberGender, memberAddr, memberJumin,
					memberphone, hobby, memberEmail, ip); // insertMember리턴받아서 호출*/
			
			int value = md.insertMember(m_memberId, m_memberPwd, m_memberName, m_memberGender,
					m_memberphone, ip); // insertMember리턴받아서 호출

			PrintWriter out = response.getWriter();
			
			

			// 입력이 되었으면(1)
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				// out.println("<script>alert('회원가입 성공');
				// location.href='"+request.getContextPath()+"'</script>");
			} else {
				response.sendRedirect(request.getContextPath() + "/member/memberJoin.do"); // 가상경로
				// out.println("<script>alert('회원가입 실패');
				System.out.println("회원가입 실패");
				// location.href='./memberJoin.html'</script>");
			}
		}

		
		
//회원가입 화면 부분 시작
		else if (command.equals("/member/memberJoin.do")) {
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberJoin.jsp");
			rd.forward(request, response);
		}
		
		
		
//멤버 리스트 부분 시작
		else if (command.equals("/member/memberList.do")) {

			MemberDao md = new MemberDao();  //memberdao를 객체생성시켜서 사용 
			ArrayList<MemberVo> alist = md.memberSelectAll();

			request.setAttribute("alist", alist); // 내부적으로 자원을 공유해서 쓸때 값을 담는 메소드(setAttribute)

			RequestDispatcher rd = request.getRequestDispatcher("/member/memberList.jsp");
			rd.forward(request, response);
		}
		
		

//회원 로그인 부분 화면 시작
		else if (command.equals("/member/memberLogin.do")) {

			// 이동하는 부분 나타나게
			RequestDispatcher rd = request.getRequestDispatcher("/member/memberLogin.jsp"); // 이동시키는 부분
			rd.forward(request, response); // forward방식으로 넘긴다
		}

		
//회원 로그인 처리 부분 시작
		else if (command.equals("/member/memberLoginAction.do")) {
			
			// 1.넘어온값
			String m_memberId = request.getParameter("m_memberId");
			String m_memberPwd = request.getParameter("m_memberPwd");
			

			// 2.처리하는 부분
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLogin(m_memberId, m_memberPwd);
			 //System.out.print("mv"+mv);

			HttpSession session = request.getSession(); // 세션 꺼낸다
			PrintWriter out=response.getWriter();

			// 3.이동하는 부분
			
			if (mv != null) { // 값이 있으면
				session.setAttribute("m_midx", mv.getM_midx());
				session.setAttribute("m_memberId", mv.getM_memberid());
				session.setAttribute("m_memberName", mv.getM_membername());
				
				BoardDao bd = new BoardDao();

				
				if (session.getAttribute("saveUrl") != null) {	//이전 페이지 값이 있으면
					session.setAttribute("alist", bd.reservelist());
					response.sendRedirect((String) session.getAttribute("saveUrl")); // 세션값에 saveurl이라는 값을 담아 이동
					
				}
				else {
					
					out.println("<script>alert('로그인 되었습니다.');location.href='"+request.getContextPath()+"/index.jsp'</script>");
					//response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
			} else {
				//request.setAttribute("loginResult", "fail");
				//RequestDispatcher rd = request.getRequestDispatcher("/member/memberLogin.jsp");
				//rd.forward(request, response);
				out.println("<script>alert('로그인에 실패했습니다.');location.href='"+request.getContextPath()+"/member/memberLogin.do'</script>");
				//response.sendRedirect(request.getContextPath() + "/member/memberLogin.do");
			}

		}

		
		
//회원 로그아웃 부분 시작
		else if (command.equals("/member/memberLogout.do")) {
			HttpSession session = request.getSession();
			session.invalidate(); // 초기화됨
			response.sendRedirect(request.getContextPath() + "/");
		}
		
		
		
		
//아이디 체크 부분 시작
		else if (command.equals("/member/memberIdcheck.do")) {
			//System.out.println("넘어옴");
			String userId = request.getParameter("userId");	//넘어온값을 받아옴
			
			MemberDao md = new MemberDao();	//객체생성
			int value = md.checkId(userId);	//실행하면 value 값나옴
			//System.out.println("value: "+value);
			
			
			PrintWriter out = response.getWriter();
			out.println("{\"result\":\""+value+"\"}");	//\쓰는 이유는 "를 사용하기 위함
			
		}
		
		
		
//아이디 찾기 화면
		else if (command.equals("/member/findId.do")) {
			RequestDispatcher rd = request.getRequestDispatcher("/member/findId.jsp");
			rd.forward(request, response);
			
		}
		
		
		
//아이디 찾기 처리 부분 시작
		else if (command.equals("/member/memberidfind.do")) {
			String m_membername = request.getParameter("m_membername");
			String m_memberphone = request.getParameter("m_memberphone");
			
			MemberDao md = new MemberDao();
			String m_memberid = md.findId(m_membername,m_memberphone);
			//MemberVo mv = md.findId(m_membername,m_memberphone);	//담김
			if(m_memberid != null){
				request.setAttribute("m_memberid", m_memberid);
			}
			else {
				request.setAttribute("m_memberid", "notfound");
			}
			
			//System.out.println("md.findId(m_membername,m_memberphone) :"+md.findId(m_membername,m_memberphone));
			
			//넘김
			RequestDispatcher rd = request.getRequestDispatcher("/member/findId.jsp");
			rd.forward(request, response);
			
		}
		
		
//비밀번호 찾기 화면
				else if (command.equals("/member/findPw.do")) {
					RequestDispatcher rd = request.getRequestDispatcher("/member/findPw.jsp");
					rd.forward(request, response);
					
				}
		

		
//비밀번호 찾기 처리 부분 시작
				else if (command.equals("/member/memberpwfind.do")) {
					String m_memberid = request.getParameter("m_memberid");
					String m_memberphone = request.getParameter("m_memberphone");
					
					MemberDao md = new MemberDao();
					String m_memberpwd = md.findPw(m_memberid,m_memberphone);
					//MemberVo mv = md.findPw(m_memberid,m_memberphone);	//담김
					
					if(m_memberpwd != null){
						request.setAttribute("m_memberpwd", m_memberpwd);
					}
					else {
						request.setAttribute("m_memberpwd", "notfound");
					}
					//request.setAttribute("m_memberpwd", mv.getM_memberpwd());
					
					//넘김
					RequestDispatcher rd = request.getRequestDispatcher("/member/findPw.jsp");
					rd.forward(request, response);
					
				}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
