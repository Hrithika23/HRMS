package net.javaguides.hrms.bean;

import java.sql.Timestamp;
import java.util.Date;

public class Attendance {
    private int attendanceId;
    private String empCode;
    private Timestamp loginTime;
    private Timestamp logoutTime;
    private Date attendanceDate;
    private String status;

    // Empty Constructor
    public Attendance() {
    }

    // Full Constructor
    public Attendance(int attendanceId, String empCode, Timestamp loginTime, Timestamp logoutTime, Date attendanceDate, String status) {
        this.attendanceId = attendanceId;
        this.empCode = empCode;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    // Getters and Setters
    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
