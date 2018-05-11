package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberDAO;

public class LoginProcessAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		ActionForward forward = new ActionForward();
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		MemberDAO memberDao = new MemberDAO();
		int result = memberDao.isId(id, pass);
		System.out.println("결과는 " + result);
		
		if(result == 1) {
			HttpSession session = request.getSession();
			//로그인 성공
			session.setAttribute("id", id);
			forward.setRedirect(true);
			forward.setPath("./BoardList.bo");
			return forward;
		} else {
			String message = "";
			if(result == -1)
				message = "아이디가 존재하지 않습니다.";
			else
				message = "비밀번호가 일치하지 않습니다.";
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + message + "');");
			out.println("location.href='./login.net';");
			out.println("</script>");
			out.close();
			return null;
		}
	}
	
}
