package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import DBConnector.DBInfo;

public class NotificationBean {
	static int flag=0;
	private static final long serialVersionUID = 1L;
	//private static final String GOOGLE_SERVER_KEY = "AIzaSyAvXQrIfnTNRp-OsbfSSSNd67PiWugRc00";
	private static final String GOOGLE_SERVER_KEY = "AIzaSyD4SzFayjGoTwDOzH3GR-4ISu9chhFEdq4";
	
	static final String MESSAGE_KEY = "message";	
    
	public static int sendNotification(String userMessage, String query)
	{
	
	Result result = null;
	String regId = "";
	try
	{
		Connection con=DBInfo.getConn();
			PreparedStatement ps= con.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			regId=rs.getString(1);
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			Message message = new Message.Builder().timeToLive(30)
					.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
			System.out.println("regId: " + regId);
			
			result = sender.send(message, regId, 1);
			flag= 1;
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return 0;
	}

	return flag;
	}
}
