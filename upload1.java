package pack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import drivehq.FileUpload;

@WebServlet("/upload1")
public class upload1 extends HttpServlet {

	File file;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();

		String my = request.getSession().getAttribute("me").toString();

		try {
			
			for(File file: new File(util.Constants.UPLOAD_PATH).listFiles()) 
			    if (!file.isDirectory()) 
			        file.delete();
			
			
			for(File file: new File(util.Constants.DOWNLOAD_PATH).listFiles()) 
			    if (!file.isDirectory()) 
			        file.delete();
			

			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			
			diskFileItemFactory.setRepository(file);
			diskFileItemFactory.setSizeThreshold(1 * 1024 * 1024);

			ServletFileUpload newHUpload = new ServletFileUpload(diskFileItemFactory);
			List items = newHUpload.parseRequest(new ServletRequestContext(request));
			
			Iterator iterator = items.iterator();
			FileItem fileItem = (FileItem) iterator.next();
			Iterator itr = items.iterator();
			FileItem item = (FileItem) itr.next();
			
			InputStream myis=fileItem.getInputStream();
			String itemName=item.getName();
			
			String key="";
			String pattern="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnipqrstuvwxyz0123456789";
			
			for(int i=0;i<8;i++)
			{
				key=key+pattern.charAt(new Random().nextInt(60));
			}

			PreparedStatement pstm = null;
			String sql = "insert into files (fileid,name,rank_,key_,userid)values(null,?,?,?,?)";
			pstm = DbConnector.getConnection().prepareStatement(sql);

			pstm.setString(1,itemName);
			pstm.setString(2,"1");
			pstm.setString(3,key);
			pstm.setString(4,my);
			pstm.execute();

			HttpSession session = request.getSession(true);
			session.setAttribute("nn",key);

			String path=util.Constants.UPLOAD_PATH+itemName;
			FileOutputStream myos=new FileOutputStream(path);
			
			try {
				AppSecurity.encrypt(myis, myos,key);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (FileUpload.fileUpload(path)) {
				
				response.sendRedirect("setkeyword.jsp?msg= sucess..!");
			} else {
				response.sendRedirect("adminpage.jsp?msgg= NOT sucess..!");
			}
			
			File f=new File(path);
			f.deleteOnExit();

		} catch (Exception ex) {
			Logger.getLogger(upload1.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally {
			out.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
