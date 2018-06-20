package Webservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;

import DBConnector.DBInfo;
import beans.WifiStatusVO;


@Path("WifiMacChecker")
public class WifiMacChecker 
{
//	private String oldmac="ac:f1:df:cc:24:4e";
	List<String> oldmac = new ArrayList<String>();
	public List<String> getMac()
	{
		Connection con=DBInfo.getConn();
		String str="select * from mac";
		try{
		PreparedStatement ps= con.prepareStatement(str);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			oldmac.add(rs.getString("macid"));
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return oldmac;
	}
	
	@GET
	@Path("/checker/{emp_id}/{mac_adr}")
	@Produces(MediaType.APPLICATION_JSON)
	public String checker(@PathParam("emp_id") String emp_id, @PathParam("mac_adr") String mac_adr)
	{
		List<String> list = getMac();
		WifiStatusVO wifistatusVO=new WifiStatusVO();
		Connection con=DBInfo.getConn();
		String query="update macstatus set status=? where emp_id=?";
		if(list.contains(mac_adr))
		{
			try
			{
				System.out.println(mac_adr);
				PreparedStatement ps= con.prepareStatement(query);
				ps.setString(1, "true");
				ps.setString(2, emp_id);
				ps.executeUpdate();
			}
			catch(SQLException e)
			{
				
			}
			wifistatusVO.setStatus("true");
		}
		else
		{
			try
			{
				PreparedStatement ps= con.prepareStatement(query);
				ps.setString(1, "false");
				ps.setString(2, emp_id);
				ps.executeUpdate();
			}
			catch(SQLException e)
			{
				
			}
			wifistatusVO.setStatus("false");
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(wifistatusVO);
		return responseJsonString;
	}
	
}
