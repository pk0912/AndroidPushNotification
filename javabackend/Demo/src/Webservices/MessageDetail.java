package Webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.google.gson.Gson;

import DBConnector.DBInfo;
import beans.DeptListVO;
import beans.DeptVO;
import beans.EmployeeDetailVO;
import beans.MessageVO;

@Path("/MessageDetailWS")
public class MessageDetail {

	@GET
	@Path("/getmsg/{data}")

	@Produces(MediaType.APPLICATION_JSON)
	public String getHTMLUniversity(@PathParam("data") String data){
		MessageVO ob=new MessageVO();
		ArrayList<String> msg=new ArrayList<>();
		Connection con=DBInfo.getConn();
		String query="select msg from message where emp_id=?";
		try
		{
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, data);
		ResultSet res=ps.executeQuery();
		while(res.next())
		{
			msg.add(res.getString(1));
			
		}
		ob.setMessage(msg);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(ob);
		return responseJsonString;
	}
	
	
}
