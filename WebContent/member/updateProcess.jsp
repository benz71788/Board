<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="java.io.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	String inputId = (String)session.getAttribute("id");
	String updatePass = request.getParameter("pass");
	String updateName = request.getParameter("name");
	String updateAge = request.getParameter("age");
	String updateGender = request.getParameter("gender");
	String updateEmail = request.getParameter("email");
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	String sql = "update member "
			+ "set password=?, name=?, age=?, gender=?, email=? "
			+ "where id=? ";
	try{
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		conn = ds.getConnection();
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, updatePass);
		pstmt.setString(2, updateName);
		pstmt.setInt(3, Integer.parseInt(updateAge));
		pstmt.setString(4, updateGender);
		pstmt.setString(5, updateEmail);
		pstmt.setString(6, inputId);
		
		int result = pstmt.executeUpdate();
		if(result != 0){
			out.println("<script>");
			out.println("alert('수정이 완료되었습니다.');");
			out.println("location.href='main.jsp'");
			out.println("</script>");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		
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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>