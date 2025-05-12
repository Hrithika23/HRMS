package net.javaguides.hrms.bean;



public class Staff {
    private String emp_code;
    private String name;
    private String email;
    private String password;
    private boolean isTempPassword;

    // Constructor
    public Staff(String emp_code, String name, String email, String password, boolean isTempPassword) {
        this.emp_code = emp_code;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isTempPassword = isTempPassword;
    }

    // Getters and Setters
    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTempPassword() {
        return isTempPassword;
    }

    public void setTempPassword(boolean isTempPassword) {
        this.isTempPassword = isTempPassword;
    }
}
