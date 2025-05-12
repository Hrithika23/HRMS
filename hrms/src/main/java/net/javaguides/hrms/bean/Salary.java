package net.javaguides.hrms.bean;

public class Salary {
    private String empCode, empName, month;
    private int year, paidDays;
    private double salary, pfSal, esiSal, pt;
    private double grossSalary, pfEe, pfEr, esicEe, esicEr, deduction, netSalary;

    // Getters and Setters
    public String getEmpCode() { return empCode; }
    public void setEmpCode(String empCode) { this.empCode = empCode; }

    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public int getPaidDays() { return paidDays; }
    public void setPaidDays(int paidDays) { this.paidDays = paidDays; }

    public double getPfSal() { return pfSal; }
    public void setPfSal(double pfSal) { this.pfSal = pfSal; }

    public double getEsiSal() { return esiSal; }
    public void setEsiSal(double esiSal) { this.esiSal = esiSal; }

    public double getPt() { return pt; }
    public void setPt(double pt) { this.pt = pt; }

    public double getGrossSalary() { return grossSalary; }
    public double getPfEe() { return pfEe; }
    public double getPfEr() { return pfEr; }
    public double getEsicEe() { return esicEe; }
    public double getEsicEr() { return esicEr; }
    public double getDeduction() { return deduction; }
    public double getNetSalary() { return netSalary; }

    // Salary calculation logic
    public void calculateSalary() {
        grossSalary = Math.ceil((salary * paidDays) / 31.0);
        pfEe = Math.ceil((pfSal * 12) / 100.0);
        pfEr = Math.round((pfSal * 12) / 100.0);
        esicEe = Math.ceil((esiSal * 0.75) / 100.0);
        esicEr = Math.ceil((esiSal * 3.25) / 100.0);
        deduction = pfEe + pfEr + esicEe + esicEr + pt;
        netSalary = grossSalary - deduction;
    }
}
