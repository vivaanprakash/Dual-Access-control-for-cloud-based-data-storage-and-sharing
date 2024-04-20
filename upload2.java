package pack;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import drivehq.FileUpload;

@WebServlet("/upload2")
public class upload2 extends HttpServlet {

	File file;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DbConnector.getConnection();

			String fileName=request.getParameter("filename");
			String fileData=request.getParameter("filedata");
			String userId=request.getParameter("userid");

			InputStream stream = new ByteArrayInputStream(fileData.getBytes(StandardCharsets.UTF_8));

			PreparedStatement pstm = null;

			Random r = new Random();

			String k="";
			String pattern="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnipqrstuvwxyz0123456789";
			
			for(int i=0;i<8;i++)
			{
				k=k+pattern.charAt(new Random().nextInt(60));
			}

			java.sql.Statement s=con.createStatement();

			String email="";

			ResultSet rs1=s.executeQuery("select * from regpage where userid='"+userId+"'");

			if(rs1.next())
			{
				email=rs1.getString("mail");
			}	

			String sql = "update userfiles set filekey='"+k+"' where filename='"+fileName+"'";

			pstm = con.prepareStatement(sql);

			pstm.executeUpdate();
			con.createStatement().executeUpdate("update files set key_='"+k+"' where name='"+fileName+"'");

			mail.mailsend(k, email, fileName);

			String path=util.Constants.UPLOAD_PATH+fileName;

			FileOutputStream myos=new FileOutputStream(path);
			
			try {
				
				String usql = "update files set isupdated='yes' where name='"+fileName+"'";
				PreparedStatement ps = con.prepareStatement(usql);
				ps.executeUpdate();
				
				AppSecurity.encrypt(stream, myos,k);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (FileUpload.fileUpload(path)) {

				response.sendRedirect("userpage.jsp?msg= sucess..!");
			} else {
				response.sendRedirect("userpage.jsp?msgg= NOT sucess..!");
			}
			
			for(File file: new File(util.Constants.UPLOAD_PATH).listFiles()) 
			    if (!file.isDirectory()) 
			        file.delete();
			
			
			for(File file: new File(util.Constants.DOWNLOAD_PATH).listFiles()) 
			    if (!file.isDirectory()) 
			        file.delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP
	 * <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
