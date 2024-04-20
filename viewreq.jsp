<!DOCTYPE HTML>
<%@page import="pack.DbConnector"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<html>

<head>
<title>Dual Access Control for Cloud-Based Data Storage and Sharing</title>
<meta name="description" content="website description" />
<meta name="keywords" content="website keywords, website keywords" />
<meta http-equiv="content-type"
	content="text/html; charset=windows-1252" />
<link rel="stylesheet" type="text/css" href="style/style.css" />

</head>

<body>
	<div id="main">
		<div id="header">
			<div id="logo">
				<div id="logo_text">
					<br /> <br /> <font size="5"><a href="index.html"><span
							class="logo_colour">Dual Access Control for Cloud-Based Data Storage and Sharing
								</span></a></font>
				</div>
			</div>
			<div id="menubar">
				<ul id="menu">
					<li><a href="view.jsp">View Data Details</a></li>
					<li><a href="viewreq.jsp">View User Request</a></li>
					<li><a href="logout.jsp">Logout</a></li>
				</ul>
			</div>
		</div>
		<div id="content_header"></div>
		<div id="site_content">
			<div id="sidebar_container">
				<br /> <br /> <br /> <br />
				<div class="sidebar">
					<div class="sidebar_top"></div>
					<div class="sidebar_item">
						<h3>Useful Links</h3>
						<ul>
							<li><a href="view.jsp">View Data Details</a></li>
							<li><a href="viewreq.jsp">View User Request</a></li>
							<li><a href="logout.jsp">Logout</a></li>
						</ul>
					</div>
					<div class="sidebar_base"></div>
				</div>
			</div>
			<div id="content">
				
				<br/><br/><br/><br/>
				<table style="width: 100%; border-spacing: 0;">
					<tr>
						<th>REQUEST ID</th>
						<th>PUBLIC KEY</th>
						<th>USERNAME</th>
						<th>FILENAME</th>
						<th>Send</th>
						<th>CANCEL</th>
					</tr>
					<%
						String fname = null, r = null, k = null, t = null, key = null, cat = null;

						String owner = (String) request.getSession().getAttribute("me");

						Class.forName("com.mysql.jdbc.Driver");
						Connection conn = DbConnector.getConnection();
						Statement st1 = conn.createStatement();

						ResultSet rs1 = st1
								.executeQuery("select * from  request");

						while (rs1.next()) {

							fname = rs1.getString("id");
							r = rs1.getString("name");
							k = rs1.getString("user");
							t = rs1.getString("filename");
					%>
					<tr>
						<td><label><%=fname%></label> <input type="hidden" name="r"
							value="<%=fname%>"></td>
						
						<td><label><%=r%></label><input type="hidden" name="r"
							value="<%=r%>"></td>
						
						<td><label><%=k%></label><input type="hidden" name="k"
							value="<%=k%>"></td>
						
						<td><label><%=t%></label><input type="hidden" name="t"
							value="<%=t%>"></td>

						<td><a href="send.jsp?r=<%=r%>&k=<%=k%>&t=<%=t%>">Send Keys</a></td>

						<td><a href="delete.jsp?<%=fname%>">delete request</a></td>
					</tr>
					<%
						}
					%>
				</table>
			</div>
		</div>
		<br /> <br /> <br /> <br />
		<div id="content_footer"></div>
		<div id="footer">
			
		</div>
	</div>
</body>
</html>
