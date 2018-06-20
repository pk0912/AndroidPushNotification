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
import beans.DeptListVO;
import beans.DeptVO;
import beans.EmpListVO;
import beans.EmpVO;

@Path("DepartmentListWebService")
public class DepartmentListWebService {
	@GET
	@Path("/getDeptList")
	@Produces(MediaType.APPLICATION_JSON)
	
	public String getDeptList()
	{
		DeptVO deptVO; 
		DeptListVO deptListVO=new DeptListVO(); 
		Connection con=DBInfo.getConn();
		String query="select * from department";
		ArrayList<DeptVO> deptlist= new ArrayList<DeptVO>();
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			
			ResultSet res=ps.executeQuery();
			while(res.next())
			{
				deptVO=new DeptVO();
				deptVO.setDept_id(res.getString(1));
				deptVO.setDept_name(res.getString(2));
				deptVO.setSelected(false);
				deptlist.add(deptVO);
			}
			deptListVO.setDeptlist(deptlist);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(deptListVO);
		return responseJsonString;
	}
}
