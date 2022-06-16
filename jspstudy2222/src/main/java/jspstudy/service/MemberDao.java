//MemberDao.java

package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.MemberVo;


public class MemberDao {
		
		private Connection conn;	//전역으로 사용
		PreparedStatement pstmt = null;	//선언
		
		public MemberDao() {
			Dbconn db = new Dbconn();	//객체생성 시킴
			this.conn = db.getConnection();
		}
	
		public int insertMember(String M_MEMBERID, String M_MEMBERPWD, String M_MEMBERNAME, String M_MEMBERGENDER, String M_MEMBERPHONE, String M_MEMBERIP){
			int value=0;
			
			String sql = "insert into b_member(M_MIDX,M_MEMBERID,M_MEMBERPWD,M_MEMBERNAME,M_MEMBERGENDER,M_MEMBERPHONE,M_MEMBERIP)"
					+ "values(null,?,?,?,?,?,?)";
			
			try{
				pstmt = conn.prepareStatement(sql);	//문자열의 쿼리가 구문화 됨
				pstmt.setString(1, M_MEMBERID);	//첫번째 물음표에 memberId 매칭
				pstmt.setString(2, M_MEMBERPWD);	//넘어온건 매개변수값
				pstmt.setString(3, M_MEMBERNAME);
				pstmt.setString(4, M_MEMBERGENDER);
				pstmt.setString(5, M_MEMBERPHONE);
				pstmt.setString(6, M_MEMBERIP);
				value = pstmt.executeUpdate();
				
				//좀더 보안성 강하게 prepareStatement 사용
				
				
				//Statement stmt = conn.createStatement();	//연결객체를 통해서 Statement 구문객체를 생성해서 stmt에 담는다 / 구문을 사용할수있는 클래스 생성 //구문을 만들때 사용
				//value = stmt.executeUpdate(sql);	//구문을 실행하고 리턴값으로 실행되었으면 1 아니면 0을 value변수에 담는다
				
			}catch(Exception e){//try가 에러를 잡고 catch가 에러를 뿌려줌
				e.printStackTrace();
			}
			finally {
				try {	//try catch로 예외처리
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}			
		}
		
		return value;
		
	}


		
		
//selectall 부분 시작
		public ArrayList<MemberVo> memberSelectAll(){
			//MemberVo 여러 객체를 담는 ArrayList 클래스를 객체생성한다
			ArrayList<MemberVo> alist = new ArrayList<MemberVo>();	// membervo를 arraylist에 담음
			//PreparedStatement pstmt = null;	//선언
			ResultSet rs = null;	//선언
			
			String sql="select * from b_member where m_delyn='N' order by m_midx desc";	//쿼리구문을 문자열로 만들어 놓는다
			
			try{
				pstmt = conn.prepareStatement(sql);	//연결객체에 있는 prepareStatement메소드를 실행해서 sql문자열을 담아 구문객체를 만든다
				rs = pstmt.executeQuery();	//실행시켜 rs에 담는다
			
			
				while(rs.next()){	//반복문 사용, 데이터를 받아와서 다음값이 있는지 없는지 확인 있으면 참 //rs.next는 전용클래스 다음데이터가 존재하면 true
					MemberVo mv = new MemberVo();	//반복할때마다 객체생성한다(mv 객체생성)
					//rs에 담아놓은 컬럼값들을 mv에 옮겨담는다
					mv.setM_midx(rs.getInt("m_midx")); 	//객체생성, getInt값을 setMidx에 담는다
					mv.setM_membername(rs.getString("m_membername"));
					mv.setM_memberphone(rs.getString("m_memberphone"));
					mv.setM_joindate(rs.getString("m_joindate"));	
					alist.add(mv);	//arraylist에 값을 담는 객체를 추가
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
			
		return alist;
	}
	
	

//memberlogin 부분 시작
		public MemberVo memberLogin(String m_memberId, String m_memberPwd) {
			MemberVo mv = null;
			ResultSet rs = null;
			
			String sql="select * from b_member where m_delyn='N' and m_memberid=? and m_memberpwd=?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, m_memberId);
				pstmt.setString(2, m_memberPwd);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					mv = new MemberVo();
					mv.setM_midx(rs.getInt("m_midx"));
					mv.setM_memberid(rs.getString("m_memberid"));
					mv.setM_membername(rs.getString("m_memberName"));
					
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
			
			return mv;
	}
		
		
		
		
//아이디중복검사 체크 시작
		public int checkId(String id) {	//유저가 입력한 값을 매개변수로 한다
				String sql = "select * from b_member where m_memberid = ?";	//입력값이 테이블에 있는지 확인
				int value = 0;
				ResultSet rs = null;
				
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next() || id.equals("")) {
					value = 0;	//이미 존재하는 경우, 생성 불가능
				}
				else {
					value = 1;	//존재하지 않는 경우, 생성 가능
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return value;
		}
		
		

//아이디 찾기
		public String findId(String m_membername, String m_memberphone) {
			//MemberVo mv = null;
			ResultSet rs = null;
			String result = null;
			//String m_memberId = null;
			
			
			/*String sql="select m_memberid"+"from r_member"+"where m_membername=? and"+"m_memberphone=?";*/
			String sql="select m_memberid from b_member where m_membername=? AND m_memberphone=? AND m_delyn ='N'";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, m_membername);
				pstmt.setString(2, m_memberphone);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					result = rs.getString("m_memberid");
					
					/*mv = new MemberVo();
					mv.setM_memberid(rs.getString("m_memberid"));*/
				}
			}catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		
		

//비밀번호 찾기
				public String findPw(String m_memberid, String m_memberphone) {
					//MemberVo mv = null;
					ResultSet rs = null;
					String result = null;
					//String m_memberId = null;
					
					/*String sql="select m_memberid"+"from r_member"+"where m_membername=? and"+"m_memberphone=?";*/
					String sql="select m_memberpwd from b_member where m_memberid=? AND m_memberphone=? AND m_delyn ='N'";
					
					try {
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, m_memberid);
						pstmt.setString(2, m_memberphone);
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							result = rs.getString("m_memberpwd");
							
							/*mv = new MemberVo();
							mv.setM_memberpwd(rs.getString("m_memberpwd"));*/
						}
					}catch (SQLException e) {
						e.printStackTrace();
					} finally {
						try {
							rs.close();
							pstmt.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					return result;
				}
		
		
		
}
