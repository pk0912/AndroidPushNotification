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
import beans.RegMacVO;

@Path("RegMacWebService")
public class RegMacWebService
{
	@GET
	@Path("/regMAC/{mac}")
	@Produces(MediaType.APPLICATION_JSON)
	public String regMAC(@PathParam("mac") String mac)
	{
		RegMacVO regMacVO = new RegMacVO();
		Connection con=DBInfo.getConn();
		String query = "insert into mac values(?)";
		try
		{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, mac);
			int i = ps.executeUpdate();
			if(i>0)
			{
				regMacVO.setStatus(true);
				regMacVO.setMessage("Insertion done");
			}
			else
			{
				regMacVO.setStatus(false);
				regMacVO.setMessage("Insertion failed");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(regMacVO);
		return responseJsonString;
	}
}
