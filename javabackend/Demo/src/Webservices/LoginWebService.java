package Webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import beans.LoginBean;
import beans.StatusVO;

@Path("LoginWebService")
public class LoginWebService
{
	@GET
	@Path("/login/{emp_id}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@PathParam("emp_id") String emp_id, @PathParam("password") String password)
	{
		StatusVO statusVO=new StatusVO();
		LoginBean loginbean=new LoginBean();
		loginbean.setEmp_id(emp_id);
		loginbean.setPassword(password);
		int i=loginbean.checkLoginDetails();
		if(i==1)
		{
			statusVO.setStatus(true);
			statusVO.setTitle("Thank you!!");
			statusVO.setMessage("Congratulates "+ emp_id+  " you have logged in succesfully.");
			statusVO.setUsertype("admin");
		}
		else if(i==2)
		{
			statusVO.setStatus(true);
			statusVO.setTitle("Thank you!!");
			statusVO.setMessage("Congratulates "+ emp_id+  " you have logged in succesfully.");
			statusVO.setUsertype("employee");
		}
		else
		{
			statusVO.setStatus(false);
			statusVO.setTitle("Login Failed");
			statusVO.setMessage("Please enter correct credentials.");
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(statusVO);
		return responseJsonString;
	}
}
