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
		
		private Connection conn;	//�������� ���
		PreparedStatement pstmt = null;	//����
		
		public MemberDao() {
			Dbconn db = new Dbconn();	//��ü���� ��Ŵ
			this.conn = db.getConnection();
		}
	
		public int insertMember(String M_MEMBERID, String M_MEMBERPWD, String M_MEMBERNAME, String M_MEMBERGENDER, String M_MEMBERPHONE, String M_MEMBERIP){
			int value=0;
			
			String sql = "insert into b_member(M_MIDX,M_MEMBERID,M_MEMBERPWD,M_MEMBERNAME,M_MEMBERGENDER,M_MEMBERPHONE,M_MEMBERIP)"
					+ "values(null,?,?,?,?,?,?)";
			
			try{
				pstmt = conn.prepareStatement(sql);	//���ڿ��� ������ ����ȭ ��
				pstmt.setString(1, M_MEMBERID);	//ù��° ����ǥ�� memberId ��Ī
				pstmt.setString(2, M_MEMBERPWD);	//�Ѿ�°� �Ű�������
				pstmt.setString(3, M_MEMBERNAME);
				pstmt.setString(4, M_MEMBERGENDER);
				pstmt.setString(5, M_MEMBERPHONE);
				pstmt.setString(6, M_MEMBERIP);
				value = pstmt.executeUpdate();
				
				//���� ���ȼ� ���ϰ� prepareStatement ���
				
				
				//Statement stmt = conn.createStatement();	//���ᰴü�� ���ؼ� Statement ������ü�� �����ؼ� stmt�� ��´� / ������ ����Ҽ��ִ� Ŭ���� ���� //������ ���鶧 ���
				//value = stmt.executeUpdate(sql);	//������ �����ϰ� ���ϰ����� ����Ǿ����� 1 �ƴϸ� 0�� value������ ��´�
				
			}catch(Exception e){//try�� ������ ��� catch�� ������ �ѷ���
				e.printStackTrace();
			}
			finally {
				try {	//try catch�� ����ó��
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}			
		}
		
		return value;
		
	}


		
		
//selectall �κ� ����
		public ArrayList<MemberVo> memberSelectAll(){
			//MemberVo ���� ��ü�� ��� ArrayList Ŭ������ ��ü�����Ѵ�
			ArrayList<MemberVo> alist = new ArrayList<MemberVo>();	// membervo�� arraylist�� ����
			//PreparedStatement pstmt = null;	//����
			ResultSet rs = null;	//����
			
			String sql="select * from b_member where m_delyn='N' order by m_midx desc";	//���������� ���ڿ��� ����� ���´�
			
			try{
				pstmt = conn.prepareStatement(sql);	//���ᰴü�� �ִ� prepareStatement�޼ҵ带 �����ؼ� sql���ڿ��� ��� ������ü�� �����
				rs = pstmt.executeQuery();	//������� rs�� ��´�
			
			
				while(rs.next()){	//�ݺ��� ���, �����͸� �޾ƿͼ� �������� �ִ��� ������ Ȯ�� ������ �� //rs.next�� ����Ŭ���� ���������Ͱ� �����ϸ� true
					MemberVo mv = new MemberVo();	//�ݺ��Ҷ����� ��ü�����Ѵ�(mv ��ü����)
					//rs�� ��Ƴ��� �÷������� mv�� �Űܴ�´�
					mv.setM_midx(rs.getInt("m_midx")); 	//��ü����, getInt���� setMidx�� ��´�
					mv.setM_membername(rs.getString("m_membername"));
					mv.setM_memberphone(rs.getString("m_memberphone"));
					mv.setM_joindate(rs.getString("m_joindate"));	
					alist.add(mv);	//arraylist�� ���� ��� ��ü�� �߰�
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
			
		return alist;
	}
	
	

//memberlogin �κ� ����
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
		
		
		
		
//���̵��ߺ��˻� üũ ����
		public int checkId(String id) {	//������ �Է��� ���� �Ű������� �Ѵ�
				String sql = "select * from b_member where m_memberid = ?";	//�Է°��� ���̺� �ִ��� Ȯ��
				int value = 0;
				ResultSet rs = null;
				
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				if(rs.next() || id.equals("")) {
					value = 0;	//�̹� �����ϴ� ���, ���� �Ұ���
				}
				else {
					value = 1;	//�������� �ʴ� ���, ���� ����
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
		
		

//���̵� ã��
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
		
		

//��й�ȣ ã��
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
