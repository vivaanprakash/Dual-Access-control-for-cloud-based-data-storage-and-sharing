<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="pack.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%
	String usr = request.getParameter("user");
	String pas = request.getParameter("pass");
	
	if(usr.equals("authority") && pas.equals("authority"))
	{
		response.sendRedirect("viewreq.jsp");
	}
	else
	{

		Connection con = DbConnector.getConnection();
		Statement st = con.createStatement();
		
		String utype=null;
		
		ResultSet rs = st
				.executeQuery(" select utype from regpage where userid='" + usr
						+ "' and pass='"+pas+"'");
		while(rs.next()) {
			
			utype=rs.getString("utype");
			
		} 
		
		if(utype!=null)
		{
			request.getSession().setAttribute("me",usr);
			 
			if(utype.equals("owner"))
			{
				response.sendRedirect("adminpage.jsp");
			}
			else
			{
				response.sendRedirect("userpage.jsp");
			}
		}
		else {
			response.sendRedirect("userlog.jsp?msgg=fails");
		}
	}
%>