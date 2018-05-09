package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.MemberDAO;

public class Member_deleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		if(id.equals("admin")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자는 삭제 하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			return null;
		} else {
			response.setContentType("text/html; charset=UTF-8");
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.delete(id);
			
			forward.setPath("member_list.net");
			forward.setRedirect(true);
			return forward;
		}
	}
}
