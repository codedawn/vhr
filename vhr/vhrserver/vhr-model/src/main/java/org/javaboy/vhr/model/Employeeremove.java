package org.javaboy.vhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Employeeremove {
    private Integer id;

    private Integer eid;

    private Integer afterdepid;

    private Integer afterjobid;

    private Department department;

    private Position position;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date removedate;

    private String reason;

    private String remark;


    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public Employeeremove setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public Employeeremove() {
    }

    public Department getDepartment() {
        return department;
    }

    public Employeeremove setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public Employeeremove setPosition(Position position) {
        this.position = position;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getAfterdepid() {
        return afterdepid;
    }

    public void setAfterdepid(Integer afterdepid) {
        this.afterdepid = afterdepid;
    }

    public Integer getAfterjobid() {
        return afterjobid;
    }

    public void setAfterjobid(Integer afterjobid) {
        this.afterjobid = afterjobid;
    }

    public Date getRemovedate() {
        return removedate;
    }

    public void setRemovedate(Date removedate) {
        this.removedate = removedate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
