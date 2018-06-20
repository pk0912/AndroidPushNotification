package Webservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import DBConnector.DBInfo;
import beans.DevRegVO;

@Path("DevRegWebService")
public class DevRegWebService 
{
	@GET
	@Path("/deviceRegister/{emp_id}/{regId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deviceRegister(@PathParam("emp_id") String emp_id,@PathParam("regId") String regId)
	{
		int flag=0,i=0;
		DevRegVO dv=new DevRegVO();
		String query="select * from device where emp_id=?";
		String query1;
		Connection con=DBInfo.getConn();
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1,emp_id);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				flag=1;
			}
			
			if(flag==1)
			{
				query1="UPDATE device SET dev_id=? WHERE emp_id=?";
				PreparedStatement ps1=con.prepareStatement(query1);
				ps1.setString(1, regId);
				ps1.setString(2, emp_id);
				i=ps1.executeUpdate();
			}
			else
			{
				query1="insert into device values(?,?)";
				PreparedStatement ps1=con.prepareStatement(query1);
				ps1.setString(1, emp_id);
				ps1.setString(2, regId);
				i=ps1.executeUpdate();
			}
			
			if(i!=0)
			{
				dv.setMessage("Your device is registered for pushnotification service");
			}
			else
			{
				dv.setMessage("OOPS!! Something went wrong at server side.Try Again!!");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(dv);
		return responseJsonString;
	}
}
