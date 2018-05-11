package net.board.db;

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
public class BoardDAO_seq {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//생성자에게 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public BoardDAO_seq() {
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
		} catch(Exception ex) {
			System.out.println("DB 연결 실패 : " + ex);
			return;
		}
	}
	
	public int getListCount() {
		int result = 0;
		try {
			con = ds.getConnection();
			
			String sql = "select count(*) from BOARD11 ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch(Exception ex) {
			System.out.println("getListCount() error :" + ex);
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
	
	//글 목록 보기
	public List<BoardBean> getBoardList(int page, int limit){
		//page : 페이지
		//limit : 페이지 당 목록의 수
		//BOARD_RE_REF desc, BOARD_RE_SEQ asc에 의해 정렬한 것을
		//조건절에 맞는 rnum의 범위 만큼 가져오는 쿼리문입니다.
		String board_list_sql = 
				"select * from "
				+ "(select rownum rnum, BOARD_NUM, BOARD_NAME, "
				+ "BOARD_SUBJECT, BOARD_CONTENT, BOARD_FILE, "
				+ "BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ, "
				+ "BOARD_READCOUNT, BOARD_DATE from "
					+ "(select * from board11 "
					+ "order by BOARD_RE_REF desc, "
					+ "BOARD_RE_SEQ asc)) "
				+ "where rnum>=? and rnum<=? ";
		
		List<BoardBean> list = new ArrayList<BoardBean>();
				//한 페이지당 10개씩 목록인 경우 							1페이	지  2페이지  3페이지
		int startrow = (page - 1) * limit + 1;	//읽기 시작할 row 번호(	  1		 11     21
		int endrow = startrow + limit - 1;		//읽을 마지막 row 번호(  10		 20	    30
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_list_sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();

			//DB에서 가져온 데이터를 VO객체에 담습니다.
			while(rs.next()) {
				BoardBean board = new BoardBean();
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_NAME(rs.getString("BOARD_NAME"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
				list.add(board);	//값을 담은 객체를 리스트에 저장합니다.
			}
			return list;	//값을 담은 객체를 저장한 리스트를 호출한 곳으로 가져간다.
		} catch(Exception ex) {
			System.out.println("getListCount() error :" + ex);
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
		return null;
	}
	
	public boolean boardInsert(BoardBean board) {
		String sql = "";
		int result = 0;
		try {
			con = ds.getConnection();
			
			sql = "insert into board11 "
					+ " (BOARD_NUM, BOARD_NAME, BOARD_PASS, BOARD_SUBJECT, "
					+ " BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF, BOARD_RE_LEV, "
					+ " BOARD_RE_SEQ, BOARD_READCOUNT, BOARD_DATE) "
					+ " VALUES(board11_seq.nextval, ?, ?, ?, ?, ?, "
					+ " board11_seq.nextval, ?, ?, ?, sysdate) ";
			
			//새로운 글을 등록하는 부분입니다.
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getBOARD_NAME());
			pstmt.setString(2, board.getBOARD_PASS());
			pstmt.setString(3, board.getBOARD_SUBJECT());
			pstmt.setString(4, board.getBOARD_CONTENT());
			pstmt.setString(5, board.getBOARD_FILE());
			
			//원문의 경우 BOARD_RE_LEV, BOARD_RE_SEQ 필드 값은 0이다.
			pstmt.setInt(6, 0);		//BOARD_RE_LEV 필드
			pstmt.setInt(7, 0);		//BOARD_RE_SEQ 필드
			pstmt.setInt(8, 0);	//BOARD_READOUT	필드
			
			result = pstmt.executeUpdate();
			if(result == 0) {
				return false;
			}
			return true;
		} catch(Exception ex) {
			System.out.println("boardInsert() error : " + ex);
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
		return false;
	}
	
	public void setReadCountUpdate(int num) {
		try {
			con = ds.getConnection();
			
			String sql = "update board11 "
					+ "set BOARD_READCOUNT = BOARD_READCOUNT + 1 "
					+ "where BOARD_NUM = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		} catch(Exception ex) {
			System.out.println("setReadCountUpdate() error : " + ex);
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
	
	public BoardBean getDetail(int num) {
		BoardBean board = null;
		try {
			con = ds.getConnection();
			
			String sql = "select * from BOARD11 where BOARD_NUM = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new BoardBean();
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_NAME(rs.getString("BOARD_NAME"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
			}
			return board;
		} catch(Exception ex) {
			System.out.println("getDeail() error : " + ex);
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
		return null;
	}
	
	//글 답변
	public int boardReply(BoardBean board) {
		String sql = "";
		int result = 0;
		/*
		 * 답변을 할 원문 글 그룹 번호입니다.
		 * 답변을 달게 되면 답변 글은 이 번호와 같은 관련글 번호를 갖게 처리되면서
		 * 같은 그룹에 속하게 됩니다.
		 * 글 목록에서 보여줄때 하나의 그룹으로 묶여서 출력됩니다.
		 */
		int re_ref = board.getBOARD_RE_REF();
		
		/*
		 * 답글의 깊이를 의미합니다.
		 * 원문에 대한 답글이 출력될 때 한 번 들여쓰기 처리가 되고
		 * 답글에 대한 답글은 들여쓰기가 두 번 처리되게 합니다.
		 * 원문인 경우에는 이 값이 0이고 원문의 답글은 1, 답글의 답글은 2가 됩니다.
		 */
		int re_lev = board.getBOARD_RE_LEV();
		
		//같은 관련 글 중에서 해당 글이 출력되는 순서입니다.
		int re_seq = board.getBOARD_RE_SEQ();
		try {
			con = ds.getConnection();
			
			//BOARD_RE_REF, BOARD_RE_SEQ 값을 확인하여 원문 글에서 다른 답글이 있으면
			//다른 답글들의 BOARD_RE_SEQ값을 1씩 증가시킵니다.
			//현재 글을 다른 답글보다 앞에 출력되게 하기 위해서 입니다.
			sql = "update BOARD11 "
					+ "set BOARD_RE_SEQ = BOARD_RE_SEQ + 1 "
					+ "where BOARD_RE_REF = ? "
					+ "and BOARD_RE_SEQ > ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_seq);
			pstmt.executeUpdate();
			pstmt.close();
			
			//등록할 답변 글의 BOARD_RE_LEV, BOARD_RE_SEQ 값을 원문 글보다
			//1씩 증가시킵니다.
			re_seq = re_seq + 1;
			re_lev = re_lev + 1;
			sql = "insert into BOARD11(BOARD_NUM, BOARD_NAME, BOARD_PASS, "
					+ "BOARD_SUBJECT, BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF, "
					+ "BOARD_RE_LEV, BOARD_RE_SEQ, BOARD_READCOUNT, BOARD_DATE) "
					+ "values(board11_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate) ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getBOARD_NAME());
			pstmt.setString(2, board.getBOARD_PASS());
			pstmt.setString(3, board.getBOARD_SUBJECT());
			pstmt.setString(4, board.getBOARD_CONTENT());
			pstmt.setString(5, "");	//답변에는 파일을 업로드하지 않습니다.
			pstmt.setInt(6, re_ref);
			pstmt.setInt(7, re_lev);
			pstmt.setInt(8, re_seq);
			pstmt.setInt(9, 0);	//BOARD_READCOUNT(조회수)는 0
			result = pstmt.executeUpdate();
			
			return result;
		} catch(Exception ex) {
			System.out.println("boardReply() error : " + ex);
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
		return 0;
	}
	
	public boolean isBoardWriter(int num, String pass) {
		String board_sql = "select * from board11 where BOARD_NUM = ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			rs.next();
			if(pass.equals(rs.getString("BOARD_PASS"))) {
				return true;
			}
		} catch(Exception ex) {
			System.out.println("isBoardWriter() error : " + ex);
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
		return false;
	}
	
	public boolean boardModify(BoardBean board) {
		int num = 0;
		try {
			con = ds.getConnection();
			
			String sql = "update BOARD11 "
					+ "set BOARD_SUBJECT = ?, BOARD_CONTENT = ? "
					+ "where BOARD_NUM = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getBOARD_SUBJECT());
			pstmt.setString(2, board.getBOARD_CONTENT());
			pstmt.setInt(3, board.getBOARD_NUM());
			num = pstmt.executeUpdate();
			if(num != 0) {
				return true;
			}
		} catch(Exception ex) {
			System.out.println("boardModify() error : " + ex);
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
		return false;
	}
	
	public boolean boardDelete(int num) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql = "delete from BOARD11 "
					+ "where BOARD_NUM = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			if(result != 0) {
				return true;
			}
		} catch(Exception ex) {
			System.out.println("boardModify() error : " + ex);
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
		return false;
	}
}
