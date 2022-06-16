/*BoardController.java(servlet���� ����)*/

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
		request.setCharacterEncoding("UTF-8"); // �ѱ۱��� ����
		response.setContentType("text/html;charset=UTF-8"); // �ѱ۱��� ����

		// �����η� �� request�� ������ ó��
		String uri = request.getRequestURI();
		String pj = request.getContextPath();
		String command = uri.substring(pj.length());
		
		int sizeLimit = 1024*1024*15;
		//String uploadPath="D:\\openApi(B)\\dev\\jspstudy\\src\\main\\webapp\\";
		String uploadPath="D:\\dev\\workspace\\jspstudy\\src\\main\\webapp\\";	//�̹��� �ö󰡴� ��� ����(image������)
		String saveFolder="images";
		String saveFullPath = uploadPath+saveFolder;

		
		
//�۾��� ȭ�� ����
		if (command.equals("/board/boardWrite.do")) {
			System.out.println("�۾��� ȭ�鿡 ������");

			// forward�� �����η� �������� ������� ������η� ����
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardWrite.jsp");
			rd.forward(request, response); // forward������� �ѱ��
			
		}
		
		

//�۾��� ó��ȭ�� ����
		else if (command.equals("/board/boardWriteAction.do")) {
			System.out.println("�۾��� ó�� ȭ������ ������");
			
			MultipartRequest multipartRequest = null;
			multipartRequest = new MultipartRequest(request,saveFullPath,sizeLimit,"UTF-8",new DefaultFileRenamePolicy());

			String t_head = multipartRequest.getParameter("t_head");
			String t_subject = multipartRequest.getParameter("t_subject");
			String t_content = multipartRequest.getParameter("t_content");
			String t_writer = multipartRequest.getParameter("t_writer");
			
			Enumeration files = multipartRequest.getFileNames();	//�����ڿ� ����� ������ ��� ��ü����
			String file = (String)files.nextElement();	//��� ��ü�� ���� �̸��� ��´�(����Ǵ� �����̸�)
			String t_fileName = multipartRequest.getFilesystemName(file);	//�Ѿ���� ��ü�߿� �ش�Ǵ� �����̸����� �Ǿ��ִ� �����̸��� �����Ѵ�
			String originFileName = multipartRequest.getOriginalFileName(file);	//������ �����̸�

			String ip = InetAddress.getLocalHost().getHostAddress(); // ip�� �����ϱ�

			//int midx = 2; // �ϴ� ó���� 2��ȸ���� ��ٰ� ����(test��)�α��� ��� �Ŀ� �����ؾ���
			HttpSession session = request.getSession();
			int m_midx = (int)session.getAttribute("m_midx");	//����ȯ

			// BoardDao���� ���� insert ���� �޼ҵ� ���
			BoardDao bd = new BoardDao(); // ������ ȣ��
			int value = bd.insertBoard(t_head, t_subject, t_content, t_writer, ip, m_midx, t_fileName);

			// value���� 1�̸� ����Ǿ���
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardWrite.do"); // ����ȵǸ� �����ּҷ�
			}
		}

		
		
//�Խ��� ��(����Ʈ) ����
		else if (command.equals("/board/boardtip.do")) {
			System.out.println("�Խ��� �� ȭ�� ������");
			
			String page = request.getParameter("page");	//�Ķ���Ͱ����� page�� �ҷ���
			if(page == null) page ="1";
			int pagex = Integer.parseInt(page);
			
			String keyword = request.getParameter("keyword");	//�Ķ���Ͱ����� keyword�� �ҷ���
			if(keyword == null) keyword="";	//Ű���� ������ ������ ó��		
			
			
			String searchType = request.getParameter("searchType");
			if(searchType == null) searchType="t_subject";
			
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setSearchType(searchType);
			scri.setKeyword(keyword);
			

			// ó���ϴ� �κ�
			BoardDao bd = new BoardDao(); /* BoardDao�� ��ü�������Ѽ� ��� */
			int cnt = bd.boardTotal(scri);
			
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			pm.setTotalCount(cnt);
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(scri);	//cri�� ���������� ����

			request.setAttribute("alist", alist); // ���������� �ڿ��� �����ؼ� ���� ���� ��� �޼ҵ�(setAttribute)
			request.setAttribute("pm", pm);
				
			// �̵��ϴ� �κ�
			RequestDispatcher rd = request.getRequestDispatcher("/board/board_tip.jsp"); // �̵���Ű�� �κ�
			rd.forward(request, response); // forward������� �ѱ��
		}
		
		

// �Խ��� ������(���뺸��) ����
		else if (command.equals("/board/boardContent.do")) {
			System.out.println("�Խ��� �������� ����");

			// 1.�Ѿ�� ���� �޴´�
			String tidx = request.getParameter("tidx"); // �Ķ���ͷ� �ѱ�� ���� ������ String��
			int tidx_ = Integer.parseInt(tidx); // string�� int������ �ٲٱ�

			// 2.ó���Ѵ�
			BoardDao bd = new BoardDao(); // boaddao��ü����
			BoardVo bv = bd.boardSelectOne(tidx_); // bidx�� ������ int���ε�string���� �Ǿ��־ int������ �ٲ� bidx_�� ����־���
			request.setAttribute("bv", bv); // ���ο� ���� ��ġ���� �ڿ� �����Ѵ�
											// ���� ȭ����� �������� ȭ�鿡�� Ǯ� ������

			// 3.�̵��ϴ� �κ�
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardContent.jsp"); // �̵���Ű�� �κ�
			rd.forward(request, response); // forward������� �ѱ��
		}
		
		
// �Խ��� �����κ� ����
		else if (command.equals("/board/boardModify.do")) {
			System.out.println("�Խ��� ������ ����");

			String tidx = request.getParameter("tidx"); // �Ķ���ͷ� �ѱ�� ���� ������ String��
			int tidx_ = Integer.parseInt(tidx); // string�� int������ �ٲٱ�

			// 2.ó���Ѵ�
			BoardDao bd = new BoardDao(); // boaddao��ü����
			BoardVo bv = bd.boardSelectOne(tidx_); // bidx�� ������ int���ε�string���� �Ǿ��־ int������ �ٲ� bidx_�� ����־���
			request.setAttribute("bv", bv); // ���ο� ���� ��ġ���� �ڿ� �����Ѵ�
			// System.out.println(bv.getBidx()); //���� ȭ����� �������� ȭ�鿡�� Ǯ� ������

			// �̵��ϴ� �κ�
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardModify.jsp"); // �̵���Ű�� �κ�
			rd.forward(request, response); // forward������� �ѱ��
		}

		
// �۾��� ����ó�� �κ� ����
		else if (command.equals("/board/boardModifyAction.do")) {
			System.out.println("�۾��� ����ó�� ȭ������ ������");
			
			String t_head = request.getParameter("t_head");
			String t_subject = request.getParameter("t_subject");
			String t_content = request.getParameter("t_content");
			String t_writer = request.getParameter("t_writer");
			int tidx = Integer.parseInt(request.getParameter("tidx"));
			
			//int m_midx = Integer.parseInt(request.getParameter("m_midx"));
			//int tidx_ = Integer.parseInt(tidx);


			String ip = InetAddress.getLocalHost().getHostAddress(); // ip�� �����ϱ�

			//int midx = 2; // �ϴ� 2��ȸ���� ��ٰ� ����(test��)�α��� ��� �Ŀ� �����ؾ���
			HttpSession session = request.getSession();
			//int midx = (int)session.getAttribute("m_midx");	//����ȯ

			int m_midx = (int)session.getAttribute("m_midx");
			// BoardDao���� ���� insert ���� �޼ҵ� ���
			BoardDao bd = new BoardDao(); // ������ ȣ��
			int value = bd.updateBoard(t_head, t_subject, t_content, t_writer, m_midx, tidx);

			// value���� 1�̸� ����Ǿ���
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardContent.do?tidx=" + tidx);
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardModify.do?tidx=" + tidx); // ����ȵǸ� �����ּҷ�
			}
		}
		
		

//�Խ��� �� ���� �κ� ����
		else if (command.equals("/board/boardDelete.do")) {
			System.out.println("�Խ��� ������ ����");

			String tidx = request.getParameter("tidx"); // �Ķ���ͷ� �ѱ�� ���� ������ String��
			int tidx_ = Integer.parseInt(tidx); // string�� int������ �ٲٱ�

			// 2.ó���Ѵ�
			BoardDao bd = new BoardDao(); // boaddao��ü����
			BoardVo bv = bd.boardSelectOne(tidx_); // bidx�� ������ int���ε�string���� �Ǿ��־ int������ �ٲ� bidx_�� ����־���
			request.setAttribute("bv", bv); // ���ο� ���� ��ġ���� �ڿ� �����Ѵ�
			//System.out.println(bv.getBidx()); //���� ȭ����� �������� ȭ�鿡�� Ǯ� ������

			// �̵��ϴ� �κ�
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardDelete.jsp"); // �̵���Ű�� �κ�
			rd.forward(request, response); // forward������� �ѱ��
			
		}
		
		
//�Խ��� �� ���� ó�� ����
		else if (command.equals("/board/boardDeleteAction.do")) {
			System.out.println("�۾��� ����ó�� ȭ������ ������");
			
			String tidx = request.getParameter("tidx");
			int tidx_ = Integer.parseInt(tidx);

			String ip = InetAddress.getLocalHost().getHostAddress(); // ip�� �����ϱ�

			//int midx = 2; // �ϴ� 2��ȸ���� ��ٰ� ����(test��)�α��� ��� �Ŀ� �����ؾ���
			HttpSession session = request.getSession();
			int midx = (int)session.getAttribute("m_midx");	//����ȯ

			// BoardDao���� ���� insert ���� �޼ҵ� ���
			BoardDao bd = new BoardDao(); // ������ ȣ��
			int value = bd.deleteBoard(tidx_);

			// value���� 1�̸� ����Ǿ���
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do"); // ����ȵǸ� �����ּҷ�
			}
		}
		
		
		
//�Խ��� �亯ȭ�� ����
		else if (command.equals("/board/boardReply.do")) {
			
			System.out.println("�Խ��� �亯 ȭ������ ������");
			
			String tidx = request.getParameter("tidx");
			String origintidx = request.getParameter("origintidx");
			String t_depth = request.getParameter("t_depth");
			String t_level_ = request.getParameter("t_level_");
			
			BoardVo bv = new BoardVo();	//�Ѿ�°��� bv�� ��´�
			bv.setM_midx(Integer.parseInt(tidx));	//bidx���� int������ �޾��ش�
			bv.setOrigintidx(Integer.parseInt(origintidx));
			bv.setT_depth(Integer.parseInt(t_depth));
			bv.setT_level_(Integer.parseInt(t_level_));
			
			//bv�� �������� ȭ������ ������ ��
			request.setAttribute("bv", bv);
			
			// �̵��ϴ� �κ� ��Ÿ����
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardReply.jsp"); // �̵���Ű�� �κ�
			rd.forward(request, response); // forward������� �ѱ��
			
		}
		
		
//�Խ��� �亯ȭ�� ó�� ����
		else if (command.equals("/board/boardReplyAction.do")) {
			
			// �޼ҵ忡 �� �Ʒ� ���� �Ѱ��ַ��� 
			String tidx = request.getParameter("tidx");
			String origintidx = request.getParameter("origintidx");
			String t_depth = request.getParameter("t_depth");
			String t_level_ = request.getParameter("t_level_");
			String t_subject = request.getParameter("t_subject");
			String t_content = request.getParameter("t_content");
			String t_writer = request.getParameter("t_writer");
			String ip = InetAddress.getLocalHost().getHostAddress(); // ip�� �����ϱ�
			
			//int midx = 2; // �ϴ� 2��ȸ���� ��ٰ� ����(test��)�α��� ��� �Ŀ� �����ؾ���
			HttpSession session = request.getSession();
			int m_midx = (int)session.getAttribute("m_midx");	//����ȯ
			
			BoardVo bv = new BoardVo();	//��ü�� ���� �ѱ�
			bv.setM_midx(Integer.parseInt(tidx));	//bidx���� int������ �޾��ش�
			bv.setOrigintidx(Integer.parseInt(origintidx));
			bv.setT_depth(Integer.parseInt(t_depth));
			bv.setT_level_(Integer.parseInt(t_level_));
			bv.setT_subject(t_subject);
			bv.setT_content(t_content);
			bv.setT_writer(t_writer);
			bv.setT_ip(ip);
			bv.setM_midx(m_midx);
			
			BoardDao bd = new BoardDao(); //��ü����
			int value = bd.replyBoard(bv);	//��ü�� ���� �ѱ� 9���� �� ������
			
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/boardtip.do"); // ����ȵǸ� �����ּҷ�
			}
			
		}

		
		
//�Խ��� ���뿡 �ö� ���� ���ε�,�ٿ�ε� �κ� ����
		else if(command.equals("/board/fileDownload.do")) {
			
			String filename = request.getParameter("t_filename");
			
			String filePath = saveFullPath+File.separator+filename;	//������ ��ü���
			Path source = Paths.get(filePath);	//���� ��ο� �ִ��� Ȯ��
			
			String mimeType = Files.probeContentType(source);
			response.setContentType(mimeType);	//���������� ��������� ��´�
												//����������� �ѱ涧 ��ǲ�� ��䰪 �Ⱥ��̰� �ϴ°Ͱ� �����(����� ����Ÿ�� �޾Ƽ� �ѱ�)
			
			String encodingFileName = new String(filename.getBytes("UTF-8"));
			
			response.setHeader("Content-Disposition", "attachment;fileName="+encodingFileName);	//÷���ؼ� �ٿ�ε�Ǵ� ������ ��������� ��´�
			
			FileInputStream fileInputStream = new FileInputStream(filePath);	//�ش���ġ�� �ִ� ������ �о���δ�
			
			ServletOutputStream servletOutStream = response.getOutputStream();	//���Ͼ������� ��Ʈ��
			
			byte[] b = new byte[4096];
			
			int read = 0;
			
			while((read = fileInputStream.read(b, 0, b.length))!=-1) {
				
				servletOutStream.write(b, 0, read);	//���Ͼ���
				
			}
			
			servletOutStream.flush();	//���
			servletOutStream.close();
			fileInputStream.close();
		
		}
		
		
		
		
// �����ϱ� ȭ�� ����
		else if (command.equals("/board/reserve.do")) {
			//midx �� null�ؼ� ���ǰ��� ������ �α����϶�� �ߴ°� ���⿡ �ϱ�
			//String uri = request.getRequestURI(); //������Ʈ ���� �ޱ�
			
			
			// �޼ҵ带 ȣ���ؼ�  alist ��´�
			BoardDao bd = new BoardDao();
			ArrayList<ReserveDto> alist = bd.reservelist();
			System.out.println("alist"+alist);
			

			 // ���������� �ڿ��� �����ؼ� ���� ���� ��� �޼ҵ�(setAttribute)
			// ȭ������ ��������
			request.setAttribute("alist", alist);
			
			// forward�� �����η� �������� ������� ������η� ����
			RequestDispatcher rd = request.getRequestDispatcher("/board/reserve.jsp");
			rd.forward(request, response); 
			// forward������� �ѱ��
			
		}
		
		
		
		
//���� ó�� ȭ�� ����
		else if (command.equals("/board/reserveAction.do")) {
			System.out.println("���� ó�� ȭ������ ������");
			int rsidx = Integer.parseInt(request.getParameter("rsidx"));	//������ �⺻Ű rsidx
			
			//int midx = 2; // �ϴ� 2��ȸ���� ��ٰ� ����(test��)�α��� ��� �Ŀ� �����ؾ���
			HttpSession session = request.getSession();			
			int m_midx = (int)session.getAttribute("m_midx");	//����ȯ ȸ���⺻Ű m_midx
			
			// BoardDao���� ���� insert ���� �޼ҵ� ���
			BoardDao bd = new BoardDao(); // ������ ȣ��
				
			//System.out.println(rsidx);
			int value=0;
			value = bd.ReserveBoard(rsidx, m_midx);	//��Ƽ� ����
			
			PrintWriter out=response.getWriter();	//�ؿ� alert�� ����Ϸ��� �ʿ�
			
			// value���� 1�̸� ����Ǿ���
			if (value == 1) {
				out.println("<script>alert('������ �Ϸ� �Ǿ����ϴ�.');location.href='"+request.getContextPath()+"/board/mypage.do'</script>");
				//response.sendRedirect(request.getContextPath() + "/board/mypage.do");
		
			} else {
				out.println("<script>alert('���࿡ �����Ͽ����ϴ�.');location.href='"+request.getContextPath()+"/board/reserve.do'</script>");
				//response.sendRedirect(request.getContextPath() + "/board/reserve.do"); // ����ȵǸ� �����ּҷ�
			}
		}
		
		
		
		
//���������� ȭ�� ����
		else if (command.equals("/board/mypage.do")) {
			HttpSession session = request.getSession();			
			int m_midx = (int)session.getAttribute("m_midx");	//m_midx�� ����
			
			
			//dao��ü����
			BoardDao bd = new BoardDao();
			ArrayList<ReserveDto> alist = bd.mypagereserve(m_midx); //�޼ҵ� ����->dtoŸ������ ���� �޴´�
			System.out.println("alist"+alist);
			
			if( alist != null) {
				request.setAttribute("alist", alist);	//null�� �ƴϸ� alist�� ��´�
			}
			
			//�̵��ϴºκ�
			RequestDispatcher rd = request.getRequestDispatcher("/board/mypage.jsp"); // �̵���Ű�� �κ�
			rd.forward(request, response); // forward������� �ѱ��
		}
		
		
		
		
//������ ������ �߰� ȭ��κ� ����
		else if (command.equals("/board/schedule_insert.do")) {
			
			// �޼ҵ带 ȣ���ؼ�  alist ��´�
			BoardDao bd = new BoardDao();
			ArrayList<ReserveDto> alist = bd.reservelist();
			System.out.println("alist"+alist);
						

			// ���������� �ڿ��� �����ؼ� ���� ���� ��� �޼ҵ�(setAttribute)
			// ȭ������ ��������
			request.setAttribute("alist", alist);
			
			
			//�̵�
			RequestDispatcher rd = request.getRequestDispatcher("/board/schedule_insert.jsp"); // �̵���Ű�� �κ�
			rd.forward(request, response); // forward������� �ѱ��
		}
		
		
		
		
//������ ������ �߰� ó���κ� ����
		else if (command.equals("/board/schedule_insertAction.do")) {
			
			String RS_STIME = request.getParameter("rs_stime");
			String RS_FTIME = request.getParameter("rs_ftime");
			String RS_DATE = request.getParameter("rs_date");
			String TEIDX = request.getParameter("teidx");
			

			//int midx = 2; // �ϴ� ó���� 2��ȸ���� ��ٰ� ����(test��)�α��� ��� �Ŀ� �����ؾ���
			HttpSession session = request.getSession();
			int m_midx = (int)session.getAttribute("m_midx");	//����ȯ
			

			// BoardDao���� ���� insert ���� �޼ҵ� ���
			BoardDao bd = new BoardDao(); // ������ ȣ��
			int value = bd.scheduleinsert(RS_STIME, RS_FTIME, RS_DATE, TEIDX);

			// value���� 1�̸� ����Ǿ���
			if (value == 1) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} else {
				response.sendRedirect(request.getContextPath() + "/board/schedule_insert.do"); // ����ȵǸ� �����ּҷ�
			}
			
		
		}
		
		
		


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
