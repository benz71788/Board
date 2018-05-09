package net.board.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardAddAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		BoardDAO boardDAO = new BoardDAO();
		BoardBean boardData = new BoardBean();
		ActionForward forward = new ActionForward();
		
		String realFolder = "";
		
		//WebContent 아래에 꼭 폴더 생성하세요.
		String saveFolder = "boardupload";
		
		int fileSize = 5 * 1024 * 1024;
		
		//실제 저장경로를 지정합니다.
		ServletContext sc = request.getServletContext();
		realFolder = sc.getRealPath(saveFolder);
		/*
		 *  realFolder = request.getSession().getServletContext().getRealPath(saveFolder);
		 */
		System.out.println("realFolder : " + realFolder);
		boolean result = false;
		try {
			MultipartRequest multi = null;
			multi = new MultipartRequest(request,
					realFolder, fileSize, "UTF-8",
					new DefaultFileRenamePolicy());
			
			//BoardBean 객체에 글 등록 폼에서 입력 받은 정보들을 저장합니다.
			boardData.setBOARD_NAME(multi.getParameter("BOARD_NAME"));
			boardData.setBOARD_PASS(multi.getParameter("BOARD_PASS"));
			boardData.setBOARD_SUBJECT(multi.getParameter("BOARD_SUBJECT"));
			boardData.setBOARD_CONTENT(multi.getParameter("BOARD_CONTENT"));
			
			//업로드의 파일명은 업로드한 파일의 전체 경로에서 파일 이름만 저장합니다.
			boardData.setBOARD_FILE(multi.getFilesystemName((String)multi.getFileNames().nextElement()));
			
			//글 등록 처리를 위해 DAO의 boardInsert()메서드를 호출합니다.
			//글 등록 폼에서 입력한 정보가 저장되어 있는 boardData객체를 전달합니다.
			result = boardDAO.boardInsert(boardData);
			
		} catch(Exception e) {
			
		}
		return forward;
	}

}