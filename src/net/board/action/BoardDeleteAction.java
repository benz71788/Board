package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO_seq;

public class BoardDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		boolean result = false;
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		BoardDAO_seq boardDAO = new BoardDAO_seq();
		
		//글 삭제 명령을 요청한 사용자가 글을 작성한 사용자인지 판단하기 위해
		//입력한 비밀번호와 저장된 비밀번호를 비교하여 일치하면 삭제합니다.
		boolean usercheck = boardDAO.isBoardWriter(num, request.getParameter("BOARD_PASS"));
		
		//비밀번호 일치하지 않는 경우
		if(usercheck == false) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return null;
		}
		//비밀번호 일치하는 경우 삭제 처리 합니다.
		result = boardDAO.boardDelete(num);
		//삭제 처리 실패한 경우
		if(result == false) {
			System.out.println("삭제 실패");
			return null;
		}
		//삭제 처리 성공한 경우 - 글 목록 보기 요청을 전송하는 부분입니다.
		System.out.println("삭제 성공");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('삭제 되었습니다.');");
		out.println("location.href='./BoardList.bo';");
		out.println("</script>");
		out.close();
		return null;
	}

}
