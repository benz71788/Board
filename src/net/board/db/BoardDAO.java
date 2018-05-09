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
public class BoardDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//생성자에게 JNDI 리소스를 참조하여 Connection 객체를 얻어옵니다.
	public BoardDAO() {
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
			
			String sql = "select count(*) from BOARD ";
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
				+ " BOARD_SUBJECT, BOARD_CONTENT, BOARD_FILE, "
				+ " BOARD_RE_REF, BOARD_RE_LEV, BOARD_SE_SEQ, "
				+ " BOARD_READCOUNT, BOARD_DATE from "
					+ "(select * from board "
					+ " order by BOARD_RE_REF desc, "
					+ " BOARD_RE_SEQ asc)) "
				+ " where rnum>=? and rnum<=? ";
		
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
		int num = 0;
		String sql = "";
		int result = 0;
		try {
			con = ds.getConnection();
			//board 테이블의 board_num 필드의 최대값을 구해와서 글을
			//등록할 때 글 번호를 순차적으로 지정하기 위함입니다.
			String max_sql = "select max(board_num) from board";
			pstmt = con.prepareStatement(max_sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1) + 1;	//최대값보다 1만큼 큰값을 지정합니다.
			} else {
				num = 1;	//처음 데이터를 등록하는 경우입니다.
			}
			
			sql = "insert into board "
					+ " (BOARD_NUM, BOARD_NAME, BOARD_PASS, BOARD_SUBJECT, "
					+ " BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF, BOARD_RE_LEV, "
					+ " BOARD_RE_SEQ, BOARD_READCOUNT, BOARD_DATE) "
					+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate) ";
			
			//새로운 글을 등록하는 부분입니다.
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getBOARD_NAME());
			pstmt.setString(3, board.getBOARD_PASS());
			pstmt.setString(4, board.getBOARD_SUBJECT());
			pstmt.setString(5, board.getBOARD_CONTENT());
			pstmt.setString(6, board.getBOARD_FILE());
			pstmt.setInt(7, num);	//BOARD_RE_REF 필드
			
			//원문의 경우 BOARD_RE_LEV, BOARD_RE_SEQ 필드 값은 0이다.
			pstmt.setInt(8, 0);		//BOARD_RE_LEV 필드
			pstmt.setInt(9, 0);		//BOARD_RE_SEQ 필드
			pstmt.setInt(10, 0);	//BOARD_READOUT	필드
			
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
}
