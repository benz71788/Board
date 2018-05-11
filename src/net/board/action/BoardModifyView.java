package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardModifyView implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("UTF-8");
		
		BoardDAO boardDAO = new BoardDAO();
		BoardBean boardData = new BoardBean();

		//파라미터로 전달받은 수정할 글 번호를 num변수에 저장합니다.
		int num = Integer.parseInt(request.getParameter("num"));
		//글 내용을 불러와서 boardData객체에 저장합니다.
		boardData = boardDAO.getDetail(num);
		
		//글 내용 불러오기 실패한 경우입니다.
		if(boardData == null) {
			System.out.println("수정 페이지 이동 실패");
			return null;
		}
		
		System.out.println("수정 페이지 이동 성공");
		
		//수정 폼 페이지로 이동할 때 원문 글 내용을 보여주기 때문에 boardData
		//객체를 request 객체에 저장합니다.
		request.setAttribute("boardData", boardData);
		
		//글 수정 폼 페이지로 이동하기 위해 경로를 설정합니다.
		forward.setRedirect(false);
		forward.setPath("./board/qna_board_modify.jsp");
		return forward;
	}

}
