/*BoardController.java(servlet으로 생성)*/

package jspstudy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 한글깨짐 방지
		response.setContentType("text/html;charset=UTF-8"); // 한글깨짐 방지

		// 가상경로로 온 request가 있으면 처리
		String uri = request.getRequestURI();
		String pj = request.getContextPath();
		String command = uri.substring(pj.length());
		
		int sizeLimit = 1024*1024*15;
		//String uploadPath="D:\\openApi(B)\\dev\\jspstudy\\src\\main\\webapp\\";
		String uploadPath="D:\\dev\\workspace\\jspstudy\\src\\main\\webapp\\";	//이미지 올라가는 경로 지정(image전까지)
		String saveFolder="images";
		String saveFullPath = uploadPath+saveFolder;

		
		
//글쓰기 화면 시작
		if (command.equals("/board/boardWrite.do")) {
			System.out.println("글쓰기 화면에 들어왔음");

			// forward는 가상경로로 들어왔으면 포워드로 실제경로로 들어가게
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardWrite.jsp");
			rd.forward(request, response); // forward방식으로 넘긴다
			
		}
		
		

//글쓰기 처리화면 시작
		else if (command.equals("/board/boardWriteAction.do")) {
			System.out.println("글쓰기 처리 화면으로 들어왔음");
			
			MultipartRequest multipartRequest = null;
			multipartRequest = new MultipartRequest(request,saveFullPath,sizeLimit,"UTF-8",new DefaultFileRenamePolicy());

			String t_head = multipartRequest.getParameter("t_head");
			String t_subject = multipartRequest.getParameter("t_subject");
			String t_content = multipartRequest.getParameter("t_content");
			String t_writer = multipartRequest.getParameter("t_writer");
			
			Enumeration files = multipartRequest.getFileNames();	//열거자에 저장된 파일을 담는 객체생성
			String file = (String)files.nextElement();	//담긴 객체의 파일 이름을 얻는다(저장되는 파일이름)
			String t_fileName = multipartRequest.getFilesystemName(file);	//넘어오는 객체중에 해당되는 파일이름으로 되어있는 파일이름을 추출한다
			String originFileName = multipartRequest.getOriginalFileName(file);	//원본의 파일이름

			String ip = InetAddress.getLocalHost().getHostAddress(); // ip값 추출하기

			//int midx = 2; // 일단 처음엔 2번회원이 썼다고 지정(test용)로그인 기능 후엔 변경해야함
			HttpSession session = request.getSession();
			int m_midx = (int)session.getAttribute("m_midx");	//형변환

			// BoardDao에서 만든 insert 구문 메소드 사용
			BoardDao bd = new BoardDao(); // 생성자 호출
			int value = bd.insertBoard(t_head, t_subject, t_content, t_writer, ip, m_midx, t_fileName);

			// value값이 1이면 실행되었다
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardWrite.do"); // 실행안되면 가상주소로
			}
		}

		
		
//게시판 팁(리스트) 시작
		else if (command.equals("/board/boardtip.do")) {
			System.out.println("게시판 팁 화면 들어왔음");
			
			String page = request.getParameter("page");	//파라미터값으로 page를 불러옴
			if(page == null) page ="1";
			int pagex = Integer.parseInt(page);
			
			String keyword = request.getParameter("keyword");	//파라미터값으로 keyword를 불러옴
			if(keyword == null) keyword="";	//키워드 없으면 빈값으로 처리		
			
			
			String searchType = request.getParameter("searchType");
			if(searchType == null) searchType="t_subject";
			
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setSearchType(searchType);
			scri.setKeyword(keyword);
			

			// 처리하는 부분
			BoardDao bd = new BoardDao(); /* BoardDao를 객체생성시켜서 사용 */
			int cnt = bd.boardTotal(scri);
			
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			pm.setTotalCount(cnt);
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(scri);	//cri에 페이지들어가져 있음

			request.setAttribute("alist", alist); // 내부적으로 자원을 공유해서 쓸때 값을 담는 메소드(setAttribute)
			request.setAttribute("pm", pm);
				
			// 이동하는 부분
			RequestDispatcher rd = request.getRequestDispatcher("/board/board_tip.jsp"); // 이동시키는 부분
			rd.forward(request, response); // forward방식으로 넘긴다
		}
		
		

// 게시판 컨텐츠(내용보기) 시작
		else if (command.equals("/board/boardContent.do")) {
			System.out.println("게시판 컨텐츠에 들어옴");

			// 1.넘어온 값을 받는다
			String tidx = request.getParameter("tidx"); // 파라미터로 넘기는 값은 무조건 String형
			int tidx_ = Integer.parseInt(tidx); // string을 int형으로 바꾸기

			// 2.처리한다
			BoardDao bd = new BoardDao(); // boaddao객체생성
			BoardVo bv = bd.boardSelectOne(tidx_); // bidx로 넣으면 int값인데string으로 되어있어서 int형으로 바꾼 bidx_을 집어넣어줌
			request.setAttribute("bv", bv); // 내부에 같은 위치에서 자원 공유한다
											// 값을 화면까지 가져가서 화면에서 풀어서 꺼낸다

			// 3.이동하는 부분
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardContent.jsp"); // 이동시키는 부분
			rd.forward(request, response); // forward방식으로 넘긴다
		}
		
		
// 게시판 수정부분 시작
		else if (command.equals("/board/boardModify.do")) {
			System.out.println("게시판 수정에 들어옴");

			String tidx = request.getParameter("tidx"); // 파라미터로 넘기는 값은 무조건 String형
			int tidx_ = Integer.parseInt(tidx); // string을 int형으로 바꾸기

			// 2.처리한다
			BoardDao bd = new BoardDao(); // boaddao객체생성
			BoardVo bv = bd.boardSelectOne(tidx_); // bidx로 넣으면 int값인데string으로 되어있어서 int형으로 바꾼 bidx_을 집어넣어줌
			request.setAttribute("bv", bv); // 내부에 같은 위치에서 자원 공유한다
			// System.out.println(bv.getBidx()); //값을 화면까지 가져가서 화면에서 풀어서 꺼낸다

			// 이동하는 부분
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardModify.jsp"); // 이동시키는 부분
			rd.forward(request, response); // forward방식으로 넘긴다
		}

		
// 글쓰기 수정처리 부분 시작
		else if (command.equals("/board/boardModifyAction.do")) {
			System.out.println("글쓰기 수정처리 화면으로 들어왔음");
			
			String t_head = request.getParameter("t_head");
			String t_subject = request.getParameter("t_subject");
			String t_content = request.getParameter("t_content");
			String t_writer = request.getParameter("t_writer");
			int tidx = Integer.parseInt(request.getParameter("tidx"));
			
			//int m_midx = Integer.parseInt(request.getParameter("m_midx"));
			//int tidx_ = Integer.parseInt(tidx);


			String ip = InetAddress.getLocalHost().getHostAddress(); // ip값 추출하기

			//int midx = 2; // 일단 2번회원이 썼다고 지정(test용)로그인 기능 후엔 변경해야함
			HttpSession session = request.getSession();
			//int midx = (int)session.getAttribute("m_midx");	//형변환

			int m_midx = (int)session.getAttribute("m_midx");
			// BoardDao에서 만든 insert 구문 메소드 사용
			BoardDao bd = new BoardDao(); // 생성자 호출
			int value = bd.updateBoard(t_head, t_subject, t_content, t_writer, m_midx, tidx);

			// value값이 1이면 실행되었다
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardContent.do?tidx=" + tidx);
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardModify.do?tidx=" + tidx); // 실행안되면 가상주소로
			}
		}
		
		

//게시판 글 삭제 부분 시작
		else if (command.equals("/board/boardDelete.do")) {
			System.out.println("게시판 삭제에 들어옴");

			String tidx = request.getParameter("tidx"); // 파라미터로 넘기는 값은 무조건 String형
			int tidx_ = Integer.parseInt(tidx); // string을 int형으로 바꾸기

			// 2.처리한다
			BoardDao bd = new BoardDao(); // boaddao객체생성
			BoardVo bv = bd.boardSelectOne(tidx_); // bidx로 넣으면 int값인데string으로 되어있어서 int형으로 바꾼 bidx_을 집어넣어줌
			request.setAttribute("bv", bv); // 내부에 같은 위치에서 자원 공유한다
			//System.out.println(bv.getBidx()); //값을 화면까지 가져가서 화면에서 풀어서 꺼낸다

			// 이동하는 부분
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardDelete.jsp"); // 이동시키는 부분
			rd.forward(request, response); // forward방식으로 넘긴다
			
		}
		
		
//게시판 글 삭제 처리 시작
		else if (command.equals("/board/boardDeleteAction.do")) {
			System.out.println("글쓰기 삭제처리 화면으로 들어왔음");
			
			String tidx = request.getParameter("tidx");
			int tidx_ = Integer.parseInt(tidx);

			String ip = InetAddress.getLocalHost().getHostAddress(); // ip값 추출하기

			//int midx = 2; // 일단 2번회원이 썼다고 지정(test용)로그인 기능 후엔 변경해야함
			HttpSession session = request.getSession();
			int midx = (int)session.getAttribute("m_midx");	//형변환

			// BoardDao에서 만든 insert 구문 메소드 사용
			BoardDao bd = new BoardDao(); // 생성자 호출
			int value = bd.deleteBoard(tidx_);

			// value값이 1이면 실행되었다
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do"); // 실행안되면 가상주소로
			}
		}
		
		
		
//게시판 답변화면 시작
		else if (command.equals("/board/boardReply.do")) {
			
			System.out.println("게시판 답변 화면으로 들어왔음");
			
			String tidx = request.getParameter("tidx");
			String origintidx = request.getParameter("origintidx");
			String t_depth = request.getParameter("t_depth");
			String t_level_ = request.getParameter("t_level_");
			
			BoardVo bv = new BoardVo();	//넘어온값을 bv에 담는다
			bv.setM_midx(Integer.parseInt(tidx));	//bidx값을 int형으로 받아준다
			bv.setOrigintidx(Integer.parseInt(origintidx));
			bv.setT_depth(Integer.parseInt(t_depth));
			bv.setT_level_(Integer.parseInt(t_level_));
			
			//bv에 담은것을 화면으로 가지고 감
			request.setAttribute("bv", bv);
			
			// 이동하는 부분 나타나게
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardReply.jsp"); // 이동시키는 부분
			rd.forward(request, response); // forward방식으로 넘긴다
			
		}
		
		
//게시판 답변화면 처리 시작
		else if (command.equals("/board/boardReplyAction.do")) {
			
			// 메소드에 이 아래 값을 넘겨주려함 
			String tidx = request.getParameter("tidx");
			String origintidx = request.getParameter("origintidx");
			String t_depth = request.getParameter("t_depth");
			String t_level_ = request.getParameter("t_level_");
			String t_subject = request.getParameter("t_subject");
			String t_content = request.getParameter("t_content");
			String t_writer = request.getParameter("t_writer");
			String ip = InetAddress.getLocalHost().getHostAddress(); // ip값 추출하기
			
			//int midx = 2; // 일단 2번회원이 썼다고 지정(test용)로그인 기능 후엔 변경해야함
			HttpSession session = request.getSession();
			int m_midx = (int)session.getAttribute("m_midx");	//형변환
			
			BoardVo bv = new BoardVo();	//객체로 만들어서 넘김
			bv.setM_midx(Integer.parseInt(tidx));	//bidx값을 int형으로 받아준다
			bv.setOrigintidx(Integer.parseInt(origintidx));
			bv.setT_depth(Integer.parseInt(t_depth));
			bv.setT_level_(Integer.parseInt(t_level_));
			bv.setT_subject(t_subject);
			bv.setT_content(t_content);
			bv.setT_writer(t_writer);
			bv.setT_ip(ip);
			bv.setM_midx(m_midx);
			
			BoardDao bd = new BoardDao(); //객체생성
			int value = bd.replyBoard(bv);	//객체로 만들어서 넘긴 9개의 값 가져옴
			
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do"); // 실행안되면 가상주소로
			}
			
		}

		
		
//게시판 내용에 올라갈 파일 업로드,다운로드 부분 시작
		else if(command.equals("/board/fileDownload.do")) {
			
			String filename = request.getParameter("t_filename");
			
			String filePath = saveFullPath+File.separator+filename;	//파일의 전체경로
			Path source = Paths.get(filePath);	//실제 경로에 있는지 확인
			
			String mimeType = Files.probeContentType(source);
			response.setContentType(mimeType);	//파일형식을 헤더정보에 담는다
												//포스방법으로 넘길때 인풋에 담긴값 안보이게 하는것과 비슷함(헤더에 마임타입 받아서 넘김)
			
			String encodingFileName = new String(filename.getBytes("UTF-8"));
			
			response.setHeader("Content-Disposition", "attachment;fileName="+encodingFileName);	//첨부해서 다운로드되는 파일을 헤더정보에 담는다
			
			FileInputStream fileInputStream = new FileInputStream(filePath);	//해당위치에 있는 파일을 읽어들인다
			
			ServletOutputStream servletOutStream = response.getOutputStream();	//파일쓰기위한 스트링
			
			byte[] b = new byte[4096];
			
			int read = 0;
			
			while((read = fileInputStream.read(b, 0, b.length))!=-1) {
				
				servletOutStream.write(b, 0, read);	//파일쓰기
				
			}
			
			servletOutStream.flush();	//출력
			servletOutStream.close();
			fileInputStream.close();
		
		}
		
		
		
		
// 예약하기 화면 시작
		else if (command.equals("/board/reserve.do")) {
			//midx 값 null해서 세션값이 없으면 로그인하라고 뜨는걸 여기에 하기
			//String uri = request.getRequestURI(); //리퀘스트 값도 받기
			
			
			// 메소드를 호출해서  alist 담는다
			BoardDao bd = new BoardDao();
			ArrayList<ReserveDto> alist = bd.reservelist();
			System.out.println("alist"+alist);
			

			 // 내부적으로 자원을 공유해서 쓸때 값을 담는 메소드(setAttribute)
			// 화면으로 가져간다
			request.setAttribute("alist", alist);
			
			// forward는 가상경로로 들어왔으면 포워드로 실제경로로 들어가게
			RequestDispatcher rd = request.getRequestDispatcher("/board/reserve.jsp");
			rd.forward(request, response); 
			// forward방식으로 넘긴다
			
		}
		
		
		
		
//예약 처리 화면 시작
		else if (command.equals("/board/reserveAction.do")) {
			System.out.println("예약 처리 화면으로 들어왔음");
			int rsidx = Integer.parseInt(request.getParameter("rsidx"));	//스케줄 기본키 rsidx
			
			//int midx = 2; // 일단 2번회원이 썼다고 지정(test용)로그인 기능 후엔 변경해야함
			HttpSession session = request.getSession();			
			int m_midx = (int)session.getAttribute("m_midx");	//형변환 회원기본키 m_midx
			
			// BoardDao에서 만든 insert 구문 메소드 사용
			BoardDao bd = new BoardDao(); // 생성자 호출
				
			//System.out.println(rsidx);
			int value=0;
			value = bd.ReserveBoard(rsidx, m_midx);	//담아서 실행
			
			PrintWriter out=response.getWriter();	//밑에 alert문 사용하려면 필요
			
			// value값이 1이면 실행되었다
			if (value == 1) {
				out.println("<script>alert('예약이 완료 되었습니다.');location.href='"+request.getContextPath()+"/board/mypage.do'</script>");
				//response.sendRedirect(request.getContextPath() + "/board/mypage.do");
		
			} else {
				out.println("<script>alert('예약에 실패하였습니다.');location.href='"+request.getContextPath()+"/board/reserve.do'</script>");
				//response.sendRedirect(request.getContextPath() + "/board/reserve.do"); // 실행안되면 가상주소로
			}
		}
		
		
		
		
//마이페이지 화면 시작
		else if (command.equals("/board/mypage.do")) {
			HttpSession session = request.getSession();			
			int m_midx = (int)session.getAttribute("m_midx");	//m_midx를 꺼냄
			
			
			//dao객체생성
			BoardDao bd = new BoardDao();
			ArrayList<ReserveDto> alist = bd.mypagereserve(m_midx); //메소드 실행->dto타입으로 값을 받는다
			System.out.println("alist"+alist);
			
			if( alist != null) {
				request.setAttribute("alist", alist);	//null이 아니면 alist를 담는다
			}
			
			//이동하는부분
			RequestDispatcher rd = request.getRequestDispatcher("/board/mypage.jsp"); // 이동시키는 부분
			rd.forward(request, response); // forward방식으로 넘긴다
		}
		
		
		
		
//관리자 스케줄 추가 화면부분 시작
		else if (command.equals("/board/schedule_insert.do")) {
			
			// 메소드를 호출해서  alist 담는다
			BoardDao bd = new BoardDao();
			ArrayList<ReserveDto> alist = bd.reservelist();
			System.out.println("alist"+alist);
						

			// 내부적으로 자원을 공유해서 쓸때 값을 담는 메소드(setAttribute)
			// 화면으로 가져간다
			request.setAttribute("alist", alist);
			
			
			//이동
			RequestDispatcher rd = request.getRequestDispatcher("/board/schedule_insert.jsp"); // 이동시키는 부분
			rd.forward(request, response); // forward방식으로 넘긴다
		}
		
		
		
		
//관리자 스케출 추가 처리부분 시작
		else if (command.equals("/board/schedule_insertAction.do")) {
			
			String RS_STIME = request.getParameter("rs_stime");
			String RS_FTIME = request.getParameter("rs_ftime");
			String RS_DATE = request.getParameter("rs_date");
			String TEIDX = request.getParameter("teidx");
			

			//int midx = 2; // 일단 처음엔 2번회원이 썼다고 지정(test용)로그인 기능 후엔 변경해야함
			HttpSession session = request.getSession();
			int m_midx = (int)session.getAttribute("m_midx");	//형변환
			

			// BoardDao에서 만든 insert 구문 메소드 사용
			BoardDao bd = new BoardDao(); // 생성자 호출
			int value = bd.scheduleinsert(RS_STIME, RS_FTIME, RS_DATE, TEIDX);

			// value값이 1이면 실행되었다
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/schedule_insert.do"); // 실행안되면 가상주소로
			}
			
		
		}
		
		
		


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
