package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.Member;
import net.member.db.MemberDAO;

public class Member_updateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		MemberDAO memberDAO = new MemberDAO();
		Member m = memberDAO.member_info(id);
		
		forward.setPath("./member/updateForm.jsp");
		forward.setRedirect(false);
		request.setAttribute("memberinfo", m);
		
		return forward;
	}

}
