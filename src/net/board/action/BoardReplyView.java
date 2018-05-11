package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO_seq;

public class BoardReplyView  implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		
		BoardDAO_seq boardDAO = new BoardDAO_seq();
		BoardBean boardData = new BoardBean();
		
		//파라미터로 전달받은 답변할 글 번호를 num변수에 저장합니다.
		int num = Integer.parseInt(request.getParameter("num"));
		
		//글 번호 num에 해당하는 내용을 가져와서 boardData 객체에 저장합니다.
		boardData = boardDAO.getDetail(num);
		
		//글 내용이 없는 경우
		if(boardData == null) {
			System.out.println("답장 페이지 이동 실패");
		}
		System.out.println("답장 페이지 이동 완료");
		
		//답변 폼 페이지로 이동할 때 원본 글 내용을 보여주기 위해 boardData객체를 보여준다.
		request.setAttribute("boardData", boardData);
		
		forward.setRedirect(false);
		forward.setPath("./board/qna_board_reply.jsp");
		
		return forward;
	}

}
