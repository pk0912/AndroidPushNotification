package com.keystone.demo;

import java.io.Serializable;
import java.util.ArrayList;

public class EmpListVO implements Serializable
{
	private ArrayList<EmpVO> emplist;

	public ArrayList<EmpVO> getEmplist() {
		return emplist;
	}

	public void setEmplist(ArrayList<EmpVO> emplist) {
		this.emplist = emplist;
	}
	
}
