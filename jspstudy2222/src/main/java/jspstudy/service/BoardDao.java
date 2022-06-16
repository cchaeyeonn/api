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
	
	private Connection conn;	//�ܺο��� �����ϰ� private
	private PreparedStatement pstmt; //���������� ����� �ִ� PreparedStatement
	
	public BoardDao() {
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
	}
	
	//boardInsert �Ϸ��� ����
	//�� �Է��ϴ� �޼ҵ� ����
	/*public int insertBoard(String subject,String content,String writer,String ip,int midx,String fileName)*/
	
	
	
//boardInsert �Ϸ��� ����
	public int insertBoard(String t_head, String t_subject,String t_content,String t_writer,String t_ip,int m_midx,String t_filename){
		int value=0;	//�������� �ʱⰪ
		
		/*String sql="INSERT INTO b_board(bidx,originbidx,depth,level_,subject,content,writer,ip,midx,filename)"
					+"VALUES(BIDX_B_SEQ.NEXTVAL,bidx_b_seq.NEXTVAL,0,0,?,?,?,?,?,?)";*/
		
		/*String sql="INSERT INTO r_tip(tidx,origintidx,t_depth,t_level_,t_head,t_subject,t_content,t_writer,t_ip,m_midx,t_filename)"
				+"VALUES(t_tidx_seq.NEXTVAL,t_tidx_seq.NEXTVAL,0,0,?,?,?,?,?,?,?)";//�Ѿ�� �����Ϳ� �����ؾ��ؼ� ?�� ����*/
		
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
	
	
	
	
//boardList �Ϸ��� ����
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri){
		//MemberVo ���� ��ü�� ��� ArrayList Ŭ������ ��ü�����Ѵ�
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();	//�����ͺ��̽����� ����Ʈ�� ������ ��� ��������
		//PreparedStatement pstmt = null;	//����
		ResultSet rs = null;	//�ʱ�ȭ ����
		
		String str = "";
		if(scri.getSearchType().equals("t_subject")) {
			str = "and t_subject like ?";	//?�� Ű����
		}
		else {
			str = "and t_writer like ?";
		}
		
		
		String sql = "SELECT * FROM("
				+"SELECT ROWNUM AS rnum, A.* FROM ("
				+"select * from b_board where t_delyn='N' "+str+" order by origintidx DESC, t_depth ASC) A"
				+") B WHERE rnum BETWEEN ? AND ?";
		
		try{
			pstmt = conn.prepareStatement(sql);	//������ ���� �־ try catch���� ����
												//���ᰴü�� �ִ� prepareStatement�޼ҵ带 �����ؼ� sql���ڿ��� ��� ������ü�� �����
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			pstmt.setInt(2, (scri.getPage()-1)*15+1);	//1~15
			pstmt.setInt(3, scri.getPage()*15); 		//16~30
			rs = pstmt.executeQuery();	//������� rs�� ��´�
			
			
			while(rs.next()){	//�ݺ��� ��� //rs.next() �������� �����ϸ� true�̰� �� ������ Ŀ���� �̵��ϴ� �޼ҵ�
				BoardVo bv = new BoardVo();	//�ݺ��Ҷ����� ��ü�����Ѵ�(mv ��ü����)
				//rs�� ��Ƴ��� �÷������� bv�� �Űܴ�´�
				bv.setTidx(rs.getInt("tidx")); 	//��ü����, getInt���� setMidx�� ��´�
				bv.setT_head(rs.getString("t_head"));
				bv.setT_subject(rs.getString("t_subject"));
				bv.setT_content(rs.getString("t_content"));
				bv.setT_writer(rs.getString("t_writer"));
				bv.setT_writeday(rs.getString("t_writeday"));
				bv.setT_level_(rs.getInt("t_level_"));
				//System.out.println(bv.getBidx());
				
				alist.add(bv);	//arraylist�� ���� ��� ��ü�� �߰�
			}		
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {	//�޸𸮸� �����ϱ� ����
				e.printStackTrace();
			}
			
		}
		//System.out.println(alist.get(0).getWriter());
		return alist;
	}
	
	
	
	
//boardcontent �Ϸ��� ����
	//boardcontroller���� �Ѿ�� ������ �ѷ��ֱ� ����
	public BoardVo boardSelectOne(int tidx) {
		BoardVo bv = null;	//�ʱ�ȭ ����
		ResultSet rs = null;	//�ʱ�ȭ ����
		
		String sql = "select * from b_board where tidx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);	//����ȭ ��Ŵ
			pstmt.setInt(1, tidx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	//�������� �����ϸ� true, Ŀ���� ���������� �̵��Ѵ�
				bv = new BoardVo();	//bv�� ��ü����
				
				bv.setTidx(rs.getInt("tidx")); //rs�� ������ִ� �����͸� bv�� �Űܴ�´�
				bv.setOrigintidx(rs.getInt("origintidx"));
				bv.setT_depth(rs.getInt("t_depth"));
				bv.setT_level_(rs.getInt("t_Level_"));
				
				
				//ȭ�鿡 ������ٰ� ������
				bv.setT_head(rs.getString("t_head"));
				bv.setT_subject(rs.getString("t_subject"));
				bv.setT_content(rs.getString("t_content"));
				bv.setT_writer(rs.getString("t_writer"));
				bv.setT_writeday(rs.getString("t_writeday"));
				bv.setT_filename(rs.getString("t_filename")); 	//ȭ�鿡�� ������ �̹��� ����Ҷ��
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
	public int replyBoard(BoardVo bv) { // boardvo�� ���� �ϳ��� ������� �Ѿ
		int value=0;	//�������� �ʱⰪ
		
		String sql1="update b_board set t_depth = t_depth+1 where origintidx=? and t_depth>?";
		
		String sql2="INSERT INTO b_board(tidx,origintidx,t_depth,t_level_,t_subject,t_content,t_writer,t_ip,m_midx)"
					+"VALUES(BIDX_B_SEQ.NEXTVAL,?,?,?,?,?,?,?,?)";	//�Ѿ�� �����Ϳ� �����ؾ��ؼ� ?�� ����
		
		try {
			conn.setAutoCommit(false);	//�ڵ����� Ŀ�Ա�� off���� �������� ����
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, bv.getOrigintidx());	//sql1�� ù��° ����ǥ���� ��
			pstmt.setInt(2, bv.getT_depth());
			pstmt.executeUpdate();
					
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bv.getOrigintidx());	
			pstmt.setInt(2, bv.getT_depth()+1);	//�� ����
			pstmt.setInt(3, bv.getT_level_()+1);	//�亯 ����
			pstmt.setString(4, bv.getT_subject());
			pstmt.setString(5, bv.getT_content());
			pstmt.setString(6, bv.getT_writer());
			pstmt.setString(7, bv.getT_ip());
			pstmt.setInt(8, bv.getM_midx());
			
			value = pstmt.executeUpdate();
			
			conn.commit(); //���߿� ������ ������ Ŀ�� ���� ����
		
			//���߿� �ϳ��� ������ ������ catch�� �Ѿ �ѹ�ó����
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

	
	
//����¡�Ҷ�
	public int boardTotal(SearchCriteria scri) {
		int cnt=0;
		ResultSet rs = null;
		
		String str = "";
		if(scri.getSearchType().equals("t_subject")) {
			str = "and t_subject like ?";	//?�� Ű����
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
				cnt = rs.getInt("cnt");	//��ü���� �̱�
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	
//���ฮ��Ʈ ������
	public ArrayList<ReserveDto> reservelist(){
		//MemberVo ���� ��ü�� ��� ArrayList Ŭ������ ��ü�����Ѵ�
		ArrayList<ReserveDto> alist = new ArrayList<ReserveDto>();	//�����ͺ��̽����� ����Ʈ�� ������ ��� ��������
		//PreparedStatement pstmt = null;	//����
		ResultSet rs = null;	//�ʱ�ȭ ����
		
		
		
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
		
		//R_APP ���� ����(N) RS_DELYN ���� ���� (N) == ���� �Ҽ� �ִ� �ð�
		

		try{
			pstmt = conn.prepareStatement(sql);	//������ ���� �־ try catch���� ����
												//���ᰴü�� �ִ� prepareStatement�޼ҵ带 �����ؼ� sql���ڿ��� ��� ������ü�� �����
			
			rs = pstmt.executeQuery();	//������� rs�� ��´�
			
			
			while(rs.next()){	//�ݺ��� ��� //rs.next() �������� �����ϸ� true�̰� �� ������ Ŀ���� �̵��ϴ� �޼ҵ�
				ReserveDto rv = new ReserveDto();	//�ݺ��Ҷ����� ��ü�����Ѵ�(mv ��ü����)
				//rs�� ��Ƴ��� �÷������� rv�� �Űܴ�´�
				rv.setRS_stime(rs.getString("RS_STIME")); 	//��ü����, getInt���� setMidx�� ��´�
				rv.setRS_ftime(rs.getString("RS_FTIME"));
				rv.setRS_date(rs.getString("RS_DATE"));
				rv.setRSIDX(rs.getInt("RSIDX"));	//��ƾ� ���� ������
				rv.setTENAME(rs.getString("TENAME"));
				rv.setTEGENDER(rs.getString("TEGENDER"));
				
				alist.add(rv);	//arraylist�� ���� ��� ��ü�� �߰�
			}		
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {	//�޸𸮸� �����ϱ� ����
				e.printStackTrace();
			}
			
		}
		//System.out.println(alist.get(0).getWriter());
		System.out.println("alist�� "+alist.get(0).getRIDX());
		return alist;
	}
	
	
	
	
	
//�����ϱ� �Ҷ�
	public int ReserveBoard(int rsidx, int m_midx){
		int value=0;	//�������� �ʱⰪ
		System.out.println(rsidx +","+ m_midx);
		
		/*String sql="INSERT INTO b_board(bidx,originbidx,depth,level_,subject,content,writer,ip,midx,filename)"
					+"VALUES(BIDX_B_SEQ.NEXTVAL,bidx_b_seq.NEXTVAL,0,0,?,?,?,?,?,?)";*/
		
		String sql="INSERT INTO r_reserve(ridx,rsidx,m_midx,r_date) VALUES(r_ridx_seq.NEXTVAL,?,?,SYSDATE)";//�Ѿ�� �����Ϳ� �����ؾ��ؼ� ?�� ����
		
		
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
	
	
	
//������������ ���ప �����ٶ�
	public ArrayList<ReserveDto> mypagereserve(int m_midx){
		String sql="SELECT RS_STIME,RS_FTIME,RS_DATE,a.rsidx, c.tename,c.tegender FROM R_SCH A , R_RESERVE B, R_TEACH C WHERE M_MIDX=? AND A.RSIDX = B.RSIDX(+) AND A.TEIDX= C.TEIDX AND R_APP='Y' AND RS_DELYN='N' ORDER BY A.RSIDX DESC";
		ResultSet rs=null;
		ArrayList<ReserveDto> alist = new ArrayList<ReserveDto>();	//���� ���� ���� ����
		int flag =0;	//flag�� 0���� ����
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m_midx);
			
			rs = pstmt.executeQuery();
						
			//rs.next()�� rs�� �������� ������(���� ������)
			while(rs.next()) {	//��ü������ �ϰ� DTO�ȿ��ִ� SET �޼ҵ带 ���
				ReserveDto rdto  = new ReserveDto();	//��ü���� ������ ȣ��
				rdto.setRS_stime(rs.getString("RS_STIME"));
				rdto.setRS_ftime(rs.getString("RS_FTIME"));
				rdto.setRS_date(rs.getString("RS_DATE"));
				rdto.setRSIDX(rs.getInt("rsidx"));
				rdto.setTENAME(rs.getString("TENAME"));
				rdto.setTEGENDER(rs.getString("TEGENDER"));
				alist.add(rdto);
				flag = 1;	//���� ������ flag�� 1�� ����
			}
			if (flag == 0) {	//���� ������ flag�� 0�̴ϱ� alist�� null�� ����
				 alist =null;	//�����ߴ� ���ڸ� ���ش�
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
			
			return alist;	//������ ������Ÿ���� �޼ҵ��� ������Ÿ�԰� ���ƾ��Ѵ�
	}
	
	
	
	
	
//������ ������ �߰� �κ�
	public int scheduleinsert(String rs_stime, String rs_ftime,String rs_date,String teidx){
		int value=0;	//�������� �ʱⰪ
		System.out.println("rs_stime"+rs_stime);
		
		String sql= "INSERT INTO r_sch(RSIDX,RS_STIME,RS_FTIME,RS_DATE,TEIDX,RS_DELYN) VALUES(r_sidx_seq.NEXTVAL,?,?,?,?,'N')";
		//�Ѿ�� �����Ϳ� �����ؾ��ؼ� ?�� ����
		
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
