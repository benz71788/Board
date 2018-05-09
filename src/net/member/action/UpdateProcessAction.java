package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.Member;
import net.member.db.MemberDAO;

public class UpdateProcessAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		
		Member m = new Member();
		m.setId(id);
		m.setPassward(pass);
		m.setName(name);
		m.setAge(age);
		m.setGender(gender);
		m.setEmail(email);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberDAO memberDAO = new MemberDAO();
		int result = memberDAO.update(m);
		out.println("<script>");
		if(result != 0) {
			out.println("alert('" + id + "님의 프로필이 수정 되었습니다.')");
			out.println("location.href='login.net'");
		} else {
			out.println("alert('수정되지 않았습니다.');");
			out.println("history.back();");
		}
		out.println("</script>");
		
		return null;
	}

}
