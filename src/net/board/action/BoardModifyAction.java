package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO_seq;

public class BoardModifyAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("UTF-8");
		boolean result = false;
		
		//전달받은 파라미터 num 변수에 저장합니다.
		int num = Integer.parseInt(request.getParameter("BOARD_NUM"));
		
		BoardDAO_seq boardDAO = new BoardDAO_seq();
		BoardBean boardData = new BoardBean();
		
		//글쓴이 인지 확인하기 위해 저장된 비밀번호와 입력한 비밀번호를 비교합니다.
		boolean usercheck = boardDAO.isBoardWriter(num, request.getParameter("BOARD_PASS"));
		//비밀번호가 다른 경우
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
		
		boardData.setBOARD_NUM(num);
		boardData.setBOARD_SUBJECT(request.getParameter("BOARD_SUBJECT"));
		boardData.setBOARD_CONTENT(request.getParameter("BOARD_CONTENT"));
		
		result = boardDAO.boardModify(boardData);
		if(!result) {
			System.out.println("수정 실패");
			return null;
		}
		System.out.println("수정 성공");
		
		request.setAttribute("boardData", boardData);
		
		forward.setRedirect(true);
		forward.setPath("./BoardDetailAction.bo?num=" + boardData.getBOARD_NUM());
		
		return forward;
	}

}
