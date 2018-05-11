package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.Member;
import net.member.db.MemberDAO;



public class JoinProcessAction implements Action{

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
		int isId = memberDAO.isId(id, pass);
		if(isId == -1) {
			out.println("<script>");
			out.println("alert('중복된 아이디가 없습니다.');");
			out.println("</script>");
		}
		
		int result = memberDAO.insert(m);
		out.println("<script>");
		if(result == 1) {
			out.println("alert('" + id + "님이 가입되셨습니다.');");
			out.println("location.href='login.net'");
		} else if(result == -1) {
			out.println("alert('아이디가 중복되었습니다.');");
			out.println("history.back();");
		}
		out.println("</script>");
		return null;
	}

}
