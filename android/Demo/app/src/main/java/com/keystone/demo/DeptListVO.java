package com.keystone.demo;

import java.util.ArrayList;

public class DeptListVO {
    private ArrayList<DeptVO> deptlist,emplist;

    public ArrayList<DeptVO> getEmplist() {
        return emplist;
    }

    public void setEmplist(ArrayList<DeptVO> emplist) {
        this.emplist = emplist;
    }

    public ArrayList<DeptVO> getDeptlist() {
        return deptlist;
    }

    public void setDeptlist(ArrayList<DeptVO> deptlist) {
        this.deptlist = deptlist;
    }
}
