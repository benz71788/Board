package net.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/*
 * DAO(Data Access Object) 클래스
 * - 데이터 베이스와 연동하여 레코드의 추가, 수정, 삭제 작업이
 * 		이루어지는 클래스 입니다.
 * - 어떤 Action 클래스가 호출되더라도 그에 해당하는
 * 		데이터 베이스 연동 처리는 DAO 클래스에서 이루어지게 됩니다.
 */
public class MemberDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;
	
	//생성자에게 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public MemberDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
		} catch(Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
			return;
		}
	}
	
	public int isId(String id, String pass) {
		try {
			con = ds.getConnection();
			System.out.println("getConnection");
			
			String sql = "select id, password from member where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(2).equals(pass)) {
					result = 1;		//아이다와 비밀번호가 일치하는 경우
				} else {
					result = 0;		//비밀번호가 일치하지 않은 경우
				}
			} else {
				result = -1;		//아이디가 존재하지 않는 경우
			}
				
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			
		}
		
		return result;
	}
	
	public List<Member> getList(){
		List<Member> list = new ArrayList<Member>();
		try {
			con = ds.getConnection();
			
			String sql = "select * from member ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member m = new Member();
				m.setId(rs.getString(1));
				m.setPassward(rs.getString(2));
				m.setName(rs.getString(3));
				m.setAge(rs.getInt(4));
				m.setGender(rs.getString(5));
				m.setEmail(rs.getString(6));
				list.add(m);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public Member member_info(String id) {
		Member m = new Member();
		try {
			con = ds.getConnection();
			
			String sql = "select * from member where id = ?"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
	
			rs = pstmt.executeQuery();
			if(rs.next()) {
				m.setId(rs.getString(1));
				m.setPassward(rs.getString(2));
				m.setName(rs.getString(3));
				m.setAge(rs.getInt(4));
				m.setGender(rs.getString(5));
				m.setEmail(rs.getString(6));
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return m;
	}
	
	public void delete(String id) {
		try {
			con = ds.getConnection();
			
			String sql = "delete from member where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int update(Member m) {
		int result = 0;
		try {
			con = ds.getConnection();
			
			String sql = "update member "
					+ "set password=?, name=?, age=?, gender=?, email=? "
					+ "where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getPassward());
			pstmt.setString(2, m.getName());
			pstmt.setInt(3, m.getAge());
			pstmt.setString(4, m.getGender());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getId());
			result = pstmt.executeUpdate();

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public int insert(Member m) {
		int result = 0;
		try {
			con = ds.getConnection();
			
			String sql = "insert into member values(?, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPassward());
			pstmt.setString(3, m.getName());
			pstmt.setInt(4, m.getAge());
			pstmt.setString(5, m.getGender());
			pstmt.setString(6, m.getEmail());
			
			result = pstmt.executeUpdate();
			
			/*
			 * primary key 제약조건 위반했을 경우 발생하는 에러
			 * 
			 */
		} catch(java.sql.SQLIntegrityConstraintViolationException e) {
			result = -1;
			System.out.println("멤버 아이디 중복 에러입니다.");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
