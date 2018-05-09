<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="java.io.*"%>
<%
	request.setCharacterEncoding("UTF-8");

	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		conn = ds.getConnection();

		pstmt = conn.prepareStatement("select * from member where id=?");
		pstmt.setString(1, id);

		rs = pstmt.executeQuery();
		
		//해당 id가 DB에 있는 경우입니다
		if(rs.next()){
			if(pass.equals(rs.getString("password"))){ //입력한 비밀번호
				session.setAttribute("id", id); //세션에 id를 저장합니다.
				
				//main.jsp로 이동합니다.
				out.println("<script>");
				out.println("alert('" + id + "님 로그인입니다.')");
				out.println("location.href='main.jsp'");
				out.println("</script>");
			} else { //비밀번호가 틀린경우
				out.println("<script>");
				out.println("alert('비밀번호가 틀렸습니다.')");
				out.println("location.href='login.net'");
				out.println("id.focus()");
				out.println("</script>");
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if(rs != null){
			try{
				rs.close();
			} catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
</body>
</html>