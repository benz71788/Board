package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO_seq;

public class BoardDetailAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		BoardDAO_seq boardDAO = new BoardDAO_seq();
		BoardBean boardDetail = new BoardBean();
		
		request.setCharacterEncoding("UTF-8");
		
		//글 번호 파라미터 값을 num변수에 저장합니다.
		int num = Integer.parseInt(request.getParameter("num"));
		
		//내용을 확인할 글의 조회수를 증가시킵니다.
		boardDAO.setReadCountUpdate(num);
		
		//글의 내용을 DAO에서 읽은 후 얻은 결과를 boardDetail 객체에 저장합니다.
		boardDetail = boardDAO.getDetail(num);
		
		//DAO에서 글의 내용을 읽지 못했을 경우 null을 반환힙니다.
		if(boardDetail == null) {
			System.out.println("상세보기 실퍠");
			return null;
		}
		System.out.println("상세보기 성공");
		
		//boardDetail 객체를 Request 객체에 저장합니다.
		request.setAttribute("boardDetail", boardDetail);
		ActionForward forward = new ActionForward();
		
		//글 내용 보기 페이지로 이동하기 위해 경로를 설정합니다.
		forward.setRedirect(false);
		forward.setPath("./board/qna_board_view.jsp");

		return forward;
	}

}
