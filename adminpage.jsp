<!DOCTYPE HTML>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="pack.DbConnector"%>
<%@page import="java.sql.Connection"%>
<html>

<head>
<title>Dual Access Control for Cloud-Based Data Storage and
	Sharing</title>
<meta name="description" content="website description" />
<meta name="keywords" content="website keywords, website keywords" />
<meta http-equiv="content-type"
	content="text/html; charset=windows-1252" />
<link rel="stylesheet" type="text/css" href="style/style.css" />

<script>
	function check() {
		var a = document.ff.user.value;
		if (a == 0) {
			alert('Please Enter The Keyword..!');
			return false;
			document.getElementById("user").focus();
		}
	}
</script>

</head>

<body>
	<div id="main">
		<div id="header">
			<div id="logo">
				<div id="logo_text">
					<br /> <br /> <font size="5"><a href="index.html"><span
							class="logo_colour">Dual Access Control for Cloud-Based
								Data Storage and Sharing </span></a></font>
				</div>
			</div>
			<div id="menubar">
				<ul id="menu">

					<li><a href="adminpage.jsp">Home</a></li>
					<li><a href="viewfiles.jsp">View Files</a></li>
					<li><a href="deletefile.jsp">delete files</a></li>
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
							<li><a href="adminpage.jsp">Home</a></li>
							<li><a href="viewfiles.jsp">View Files</a></li>
							<li><a href="deletefile.jsp">delete files</a></li>
							<li><a href="logout.jsp">Logout</a></li>
						</ul>
					</div>
					<div class="sidebar_base"></div>
				</div>
			</div>
			<div id="content">

				<br /> <br />

				<%
					String my = session.getAttribute("me").toString();
					
					String msg=request.getParameter("msg");
					
					if (msg!= null) {
				%>
				<h3><%=msg%></h3>
				<%
					} else {
				%>
				<h3>
					<center>
						Welcome to
						<%=my%>
					</center>
				</h3>
				<%
					}
				%>

				<br /> <br />

				<div class="form_settings">
					<form action="upload1" method="post" enctype="multipart/form-data">

						<p>
							<span>Upload File :</span><input type="file" name="file"><br />
						</p>
						<br />
						<p style="padding-top: 15px">
							<span>&nbsp;</span><input class="submit" type="submit"
								name="contact_submitted" value="Upload" />
						</p>
					</form>
				</div>
			</div>
		</div>
		<br /> <br /> <br /> <br />
		<div id="content_footer"></div>
		<div id="footer"></div>
	</div>
</body>
</html>
