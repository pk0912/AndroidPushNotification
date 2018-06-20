package gcm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import DBConnector.DBInfo;
import beans.NotificationBean;

/**
 * Servlet implementation class GCMNotificationNew
 */
@WebServlet("/GCMNotificationNew")
public class GCMNotificationNew extends HttpServlet {
	
	public GCMNotificationNew() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		try
		{
			PrintWriter pw=response.getWriter();
			String usermessage=request.getParameter("message");
			String query="select dev_id from device";
			int i=NotificationBean.sendNotification(usermessage,query);
			if(i==1)
			{
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			    rd.include(request, response); 
			    pw.println("<script>alert('Notification sending Successfull.')</script>");
			}
			
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			    rd.include(request, response); 
			    pw.println("<script>alert('Notification sending fail.')</script>");
			}
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
