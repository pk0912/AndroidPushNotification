package Webservices;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import DBConnector.DBInfo;
import beans.LogOutStatusVO;

@Path("/LogoutWebService")
public class LogoutWebService 
{
	@GET
	@Path("/logout/{emp_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String logout(@PathParam("emp_id") String emp_id)
	{
		LogOutStatusVO lo=new LogOutStatusVO();
		int i=0;
		Connection con=DBInfo.getConn();
		String query="delete from device where emp_id=?";
		try{
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, emp_id);
		i=ps.executeUpdate();
		if(i!=0)
		{
			lo.setStatus(true);
			lo.setMessage("You have logged out successfully");
		}
		else
		{
			lo.setStatus(false);
			lo.setMessage("Something went wrong.Please try again");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson=new Gson();
		String responseJsonString = gson.toJson(lo);
		return responseJsonString;
	}
}
