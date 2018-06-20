package beans;

import java.util.ArrayList;

/**
 * Created by vicky on 2/11/2016.
 */
public class EmployeeDetailVO {
    String Empname,Email,Phone,Deptname,id,message;
    
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	ArrayList<String> Grpname;

	public String getEmpname() {
		return Empname;
	}

	public void setEmpname(String empname) {
		Empname = empname;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getDeptname() {
		return Deptname;
	}

	public void setDeptname(String deptname) {
		Deptname = deptname;
	}

	public ArrayList<String> getGrpname() {
		return Grpname;
	}

	public void setGrpname(ArrayList<String> grpname) {
		Grpname = grpname;
	}



   

}
