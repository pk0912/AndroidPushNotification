package Webservices;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import beans.NotificationBean;
import beans.StatusVO;

@Path("WifiMsgWebService")
public class WifiMsgWebService {
	@GET
	@Path("/notifi/{message}")
	@Produces(MediaType.APPLICATION_JSON)
	public String notifi(@PathParam("message") String message)
	{
		StatusVO statusVO=new StatusVO();
		String query="select dev_id from device where emp_id in (select emp_id from macstatus where status='true')";
		int i=NotificationBean.sendNotification(message,query);
		if(i==1)
		{
			statusVO.setStatus(true);
			statusVO.setTitle("Thank you!!");
			statusVO.setMessage("Congratulates message delivered succesfully.");
			statusVO.setUsertype("admin");
		}
		
		else
		{
			statusVO.setStatus(false);
			statusVO.setTitle("message Failed");
			statusVO.setMessage("No Device Registered.");
			statusVO.setUsertype("admin");
		}
		Gson gson = new Gson();
		String responseJsonString = gson.toJson(statusVO);
		return responseJsonString;
	}
}