/*BoardDao.java*/

package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.BoardVo;
import jspstudy.domain.Criteria;
import jspstudy.domain.MemberVo;
import jspstudy.domain.ReserveDto;
import jspstudy.domain.SearchCriteria;



public class BoardDao {
	
	private Connection conn;	//외부에서 사용못하게 private
	private PreparedStatement pstmt; //쿼리구문을 만들수 있는 PreparedStatement
	
	public BoardDao() {
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
	}
	
	//boardInsert 하려고 만듦
	//글 입력하는 메소드 만듬
	/*public int insertBoard(String subject,String content,String writer,String ip,int midx,String fileName)*/
	
	
	
//boardInsert 하려고 만듦
	public int insertBoard(String t_head, String t_subject,String t_content,String t_writer,String t_ip,int m_midx,String t_filename){
		int value=0;	//지역변수 초기값
		
		/*String sql="INSERT INTO b_board(bidx,originbidx,depth,level_,subject,content,writer,ip,midx,filename)"
					+"VALUES(BIDX_B_SEQ.NEXTVAL,bidx_b_seq.NEXTVAL,0,0,?,?,?,?,?,?)";*/
		
		/*String sql="INSERT INTO r_tip(tidx,origintidx,t_depth,t_level_,t_head,t_subject,t_content,t_writer,t_ip,m_midx,t_filename)"
				+"VALUES(t_tidx_seq.NEXTVAL,t_tidx_seq.NEXTVAL,0,0,?,?,?,?,?,?,?)";//넘어온 데이터에 대입해야해서 ?로 받음*/
		
		String sql="INSERT INTO b_board(origintidx,t_depth,t_level_,t_head,t_subject,t_content,t_writer,t_ip,m_midx,t_filename)"
				+" select max(bidx)+1,0,0,?,?,?,?,?,? from b_board";
		
		
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, t_head);
		pstmt.setString(2, t_subject);
		pstmt.setString(3, t_content);
		pstmt.setString(4, t_writer);
		pstmt.setString(5, t_ip);
		pstmt.setInt(6, m_midx);
		pstmt.setString(7, t_filename);
		value = pstmt.executeUpdate();
		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return value;
	}
	
	
	
	
//boardList 하려고 만듦
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri){
		//MemberVo 여러 객체를 담는 ArrayList 클래스를 객체생성한다
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();	//데이터베이스에서 셀렉트한 데이터 모두 가져오기
		//PreparedStatement pstmt = null;	//선언
		ResultSet rs = null;	//초기화 선언
		
		String str = "";
		if(scri.getSearchType().equals("t_subject")) {
			str = "and t_subject like ?";	//?는 키워드
		}
		else {
			str = "and t_writer like ?";
		}
		
		
		String sql = "SELECT * FROM("
				+"SELECT ROWNUM AS rnum, A.* FROM ("
				+"select * from b_board where t_delyn='N' "+str+" order by origintidx DESC, t_depth ASC) A"
				+") B WHERE rnum BETWEEN ? AND ?";
		
		try{
			pstmt = conn.prepareStatement(sql);	//오류가 날수 있어서 try catch문에 담음
												//연결객체에 있는 prepareStatement메소드를 실행해서 sql문자열을 담아 구문객체를 만든다
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			pstmt.setInt(2, (scri.getPage()-1)*15+1);	//1~15
			pstmt.setInt(3, scri.getPage()*15); 		//16~30
			rs = pstmt.executeQuery();	//실행시켜 rs에 담는다
			
			
			while(rs.next()){	//반복문 사용 //rs.next() 다음값이 존재하면 true이고 그 행으로 커서가 이동하는 메소드
				BoardVo bv = new BoardVo();	//반복할때마다 객체생성한다(mv 객체생성)
				//rs에 담아놓은 컬럼값들을 bv에 옮겨담는다
				bv.setTidx(rs.getInt("tidx")); 	//객체생성, getInt값을 setMidx에 담는다
				bv.setT_head(rs.getString("t_head"));
				bv.setT_subject(rs.getString("t_subject"));
				bv.setT_content(rs.getString("t_content"));
				bv.setT_writer(rs.getString("t_writer"));
				bv.setT_writeday(rs.getString("t_writeday"));
				bv.setT_level_(rs.getInt("t_level_"));
				//System.out.println(bv.getBidx());
				
				alist.add(bv);	//arraylist에 값을 담는 객체를 추가
			}		
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {	//메모리를 관리하기 수월
				e.printStackTrace();
			}
			
		}
		//System.out.println(alist.get(0).getWriter());
		return alist;
	}
	
	
	
	
//boardcontent 하려고 만듬
	//boardcontroller에서 넘어온 데이터 뿌려주기 위해
	public BoardVo boardSelectOne(int tidx) {
		BoardVo bv = null;	//초기화 선언
		ResultSet rs = null;	//초기화 선언
		
		String sql = "select * from b_board where tidx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);	//쿼리화 시킴
			pstmt.setInt(1, tidx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	//다음값이 존재하면 true, 커서는 다음행으로 이동한다
				bv = new BoardVo();	//bv를 객체생성
				
				bv.setTidx(rs.getInt("tidx")); //rs에 담겨져있는 데이터를 bv에 옮겨담는다
				bv.setOrigintidx(rs.getInt("origintidx"));
				bv.setT_depth(rs.getInt("t_depth"));
				bv.setT_level_(rs.getInt("t_Level_"));
				
				
				//화면에 출력해줄것 꺼내기
				bv.setT_head(rs.getString("t_head"));
				bv.setT_subject(rs.getString("t_subject"));
				bv.setT_content(rs.getString("t_content"));
				bv.setT_writer(rs.getString("t_writer"));
				bv.setT_writeday(rs.getString("t_writeday"));
				bv.setT_filename(rs.getString("t_filename")); 	//화면에서 꺼내서 이미지 사용할라고
				bv.setM_midx(rs.getInt("m_midx"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bv;
	}
	
	
	
//updateboard
	public int updateBoard(String t_head, String t_subject,String t_content,String t_writer, int m_midx, int tidx) {
		String sql = "update b_board set t_head=?,t_subject=?,t_content=?,t_writer=?, m_midx=?, t_writeday=sysdate where tidx=?";
		int value=0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t_head);
			pstmt.setString(2, t_subject);
			pstmt.setString(3, t_content);
			pstmt.setString(4, t_writer);
			pstmt.setInt(5, m_midx);
			pstmt.setInt(6,  tidx);
			value = pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return value;
		
	}
	
	
	
//deleteboard
	public int deleteBoard(int tidx) {
		String sql = "update b_board set t_delyn='Y',t_writeday=sysdate where tidx=?";
		int value=0;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  tidx);
			value = pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return value;
		
	}
	
	
//replayboard
	public int replyBoard(BoardVo bv) { // boardvo의 값을 하나로 담겨져서 넘어감
		int value=0;	//지역변수 초기값
		
		String sql1="update b_board set t_depth = t_depth+1 where origintidx=? and t_depth>?";
		
		String sql2="INSERT INTO b_board(tidx,origintidx,t_depth,t_level_,t_subject,t_content,t_writer,t_ip,m_midx)"
					+"VALUES(BIDX_B_SEQ.NEXTVAL,?,?,?,?,?,?,?,?)";	//넘어온 데이터에 대입해야해서 ?로 받음
		
		try {
			conn.setAutoCommit(false);	//자동으로 커밋기능 off시켜 수동으로 만듦
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, bv.getOrigintidx());	//sql1의 첫번째 물음표부터 들어감
			pstmt.setInt(2, bv.getT_depth());
			pstmt.executeUpdate();
					
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bv.getOrigintidx());	
			pstmt.setInt(2, bv.getT_depth()+1);	//답 순서
			pstmt.setInt(3, bv.getT_level_()+1);	//답변 레벨
			pstmt.setString(4, bv.getT_subject());
			pstmt.setString(5, bv.getT_content());
			pstmt.setString(6, bv.getT_writer());
			pstmt.setString(7, bv.getT_ip());
			pstmt.setInt(8, bv.getM_midx());
			
			value = pstmt.executeUpdate();
			
			conn.commit(); //둘중에 문제가 있으면 커밋 하지 않음
		
			//둘중에 하나라도 오류가 있으면 catch로 넘어가 롤백처리함
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return value;
	}

	
	
//페이징할때
	public int boardTotal(SearchCriteria scri) {
		int cnt=0;
		ResultSet rs = null;
		
		String str = "";
		if(scri.getSearchType().equals("t_subject")) {
			str = "and t_subject like ?";	//?는 키워드
		}
		else {
			str = "and t_writer like ?";
		}
		
		
		String sql="select count(*) as cnt from b_board where t_delyn='N' "+str+"";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt("cnt");	//전체개수 뽑기
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	
//예약리스트 받을때
	public ArrayList<ReserveDto> reservelist(){
		//MemberVo 여러 객체를 담는 ArrayList 클래스를 객체생성한다
		ArrayList<ReserveDto> alist = new ArrayList<ReserveDto>();	//데이터베이스에서 셀렉트한 데이터 모두 가져오기
		//PreparedStatement pstmt = null;	//선언
		ResultSet rs = null;	//초기화 선언
		
		
		
		//String sql = "SELECT RS_STIME,RS_FTIME,RS_DATE,a.rsidx FROM R_SCH a INNER JOIN r_reserve b ON a.rsidx=b.rsidx WHERE b.r_app = 'N' AND b.r_delyn ='N'";
		/*String sql = "SELECT RS_STIME,RS_FTIME,RS_DATE,a.rsidx, c.tename ,c.tegender FROM R_SCH A , R_RESERVE B, R_TEACH C "
				+ "WHERE A.RSIDX = B.RSIDX(+) "
				+ "AND A.TEIDX= C.TEIDX "
				+ "AND (R_APP='N' OR R_APP IS NULL) "
				+ "AND RS_DELYN='N' "
				+ "ORDER BY A.RSIDX DESC" ;*/
		
		
		String sql ="SELECT RS_STIME,RS_FTIME,RS_DATE,a.rsidx, c.tename ,c.tegender "
				+ "FROM R_SCH A , R_RESERVE B, R_TEACH C WHERE A.RSIDX = B.RSIDX(+) "
				+ "AND A.TEIDX= C.TEIDX "
				+ "AND (R_APP='N' OR R_APP IS NULL) "
				+ "AND RS_DELYN='N' "
				+ "ORDER BY A.RS_DATE ASC, A.RS_FTIME ASC";
		
		//R_APP 예약 승인(N) RS_DELYN 삭제 여부 (N) == 예약 할수 있는 시간
		

		try{
			pstmt = conn.prepareStatement(sql);	//오류가 날수 있어서 try catch문에 담음
												//연결객체에 있는 prepareStatement메소드를 실행해서 sql문자열을 담아 구문객체를 만든다
			
			rs = pstmt.executeQuery();	//실행시켜 rs에 담는다
			
			
			while(rs.next()){	//반복문 사용 //rs.next() 다음값이 존재하면 true이고 그 행으로 커서가 이동하는 메소드
				ReserveDto rv = new ReserveDto();	//반복할때마다 객체생성한다(mv 객체생성)
				//rs에 담아놓은 컬럼값들을 rv에 옮겨담는다
				rv.setRS_stime(rs.getString("RS_STIME")); 	//객체생성, getInt값을 setMidx에 담는다
				rv.setRS_ftime(rs.getString("RS_FTIME"));
				rv.setRS_date(rs.getString("RS_DATE"));
				rv.setRSIDX(rs.getInt("RSIDX"));	//담아야 값을 가져옴
				rv.setTENAME(rs.getString("TENAME"));
				rv.setTEGENDER(rs.getString("TEGENDER"));
				
				alist.add(rv);	//arraylist에 값을 담는 객체를 추가
			}		
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {	//메모리를 관리하기 수월
				e.printStackTrace();
			}
			
		}
		//System.out.println(alist.get(0).getWriter());
		System.out.println("alist는 "+alist.get(0).getRIDX());
		return alist;
	}
	
	
	
	
	
//예약하기 할때
	public int ReserveBoard(int rsidx, int m_midx){
		int value=0;	//지역변수 초기값
		System.out.println(rsidx +","+ m_midx);
		
		/*String sql="INSERT INTO b_board(bidx,originbidx,depth,level_,subject,content,writer,ip,midx,filename)"
					+"VALUES(BIDX_B_SEQ.NEXTVAL,bidx_b_seq.NEXTVAL,0,0,?,?,?,?,?,?)";*/
		
		String sql="INSERT INTO r_reserve(ridx,rsidx,m_midx,r_date) VALUES(r_ridx_seq.NEXTVAL,?,?,SYSDATE)";//넘어온 데이터에 대입해야해서 ?로 받음
		
		
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, rsidx);
		pstmt.setInt(2, m_midx);
		
		value = pstmt.executeUpdate();
		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return value;
	}
	
	
	
//마이페이지에 예약값 보여줄때
	public ArrayList<ReserveDto> mypagereserve(int m_midx){
		String sql="SELECT RS_STIME,RS_FTIME,RS_DATE,a.rsidx, c.tename,c.tegender FROM R_SCH A , R_RESERVE B, R_TEACH C WHERE M_MIDX=? AND A.RSIDX = B.RSIDX(+) AND A.TEIDX= C.TEIDX AND R_APP='Y' AND RS_DELYN='N' ORDER BY A.RSIDX DESC";
		ResultSet rs=null;
		ArrayList<ReserveDto> alist = new ArrayList<ReserveDto>();	//값을 담을 상자 생성
		int flag =0;	//flag를 0으로 선언
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m_midx);
			
			rs = pstmt.executeQuery();
						
			//rs.next()는 rs에 다음값이 있으면(값이 있으면)
			while(rs.next()) {	//객체생성을 하고 DTO안에있는 SET 메소드를 사용
				ReserveDto rdto  = new ReserveDto();	//객체생성 생성자 호출
				rdto.setRS_stime(rs.getString("RS_STIME"));
				rdto.setRS_ftime(rs.getString("RS_FTIME"));
				rdto.setRS_date(rs.getString("RS_DATE"));
				rdto.setRSIDX(rs.getInt("rsidx"));
				rdto.setTENAME(rs.getString("TENAME"));
				rdto.setTEGENDER(rs.getString("TEGENDER"));
				alist.add(rdto);
				flag = 1;	//값이 있으면 flag를 1로 선언
			}
			if (flag == 0) {	//값이 없으면 flag는 0이니까 alist를 null로 선언
				 alist =null;	//생성했던 상자를 없앤다
			}
			
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			return alist;	//리턴의 데이터타입이 메소드의 데이터타입과 같아야한다
	}
	
	
	
	
	
//관리자 스케출 추가 부분
	public int scheduleinsert(String rs_stime, String rs_ftime,String rs_date,String teidx){
		int value=0;	//지역변수 초기값
		System.out.println("rs_stime"+rs_stime);
		
		String sql= "INSERT INTO r_sch(RSIDX,RS_STIME,RS_FTIME,RS_DATE,TEIDX,RS_DELYN) VALUES(r_sidx_seq.NEXTVAL,?,?,?,?,'N')";
		//넘어온 데이터에 대입해야해서 ?로 받음
		
		try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, rs_stime);
		pstmt.setString(2, rs_ftime);
		pstmt.setString(3, rs_date);
		pstmt.setString(4, teidx);
		value = pstmt.executeUpdate();
		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return value;
	}
	

}
