package Webservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import DBConnector.DBInfo;
import beans.EmpListVO;
import beans.EmpVO;

@Path("EmployeeListWebService")
public class EmployeeListWebService 
{
	@GET
	@Path("/getEmpList")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String getEmpList()
	{
		EmpVO empVO; 
		EmpListVO empListVO=new EmpListVO(); 
		Connection con=DBInfo.getConn();
		String query="select emp_id,emp_name from reg";
		ArrayList<EmpVO> emplist= new ArrayList<EmpVO>();
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				empVO=new EmpVO();
				empVO.setEmp_id(res.getString("emp_id"));
				empVO.setEmp_name(res.getString("emp_name"));
				emplist.add(empVO);
			}
			empListVO.setEmplist(emplist);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(empListVO);
		return responseJsonString;
	}
}
