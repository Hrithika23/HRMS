package net.javaguides.hrms.bean;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Employee implements Serializable {
    private String empCode;
    private String empName;
    private String gender; // Male, Female, Other
    private Date dob;
    private String homeNo;
    private String street;
    private String taluk;
    private String district;
    private String state;
    private String pincode;
    private String country;
    private String email;
    private String contactNo;
    private String emergencyContact;
    private String higherEducation; // PUC, Diploma, Degree, MCA, MBA, BE
    private String specialization;
    private String department;
    private String designation;
    private Date dateOfResign;
    private Date relievingDate;
    private String nextCompany;
    private Date previousEmploymentFrom;
    private Date previousEmploymentTo;
    private String referenceName;
    private String referenceContactNo;
    
    // Getters and Setters

    // Add the formatDate method here
    public String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    // Getters and Setters
    public String getEmpCode() { return empCode; }
    public void setEmpCode(String empCode) { this.empCode = empCode; }

    public String getEmpName() { return empName; }
    public void setEmpName(String empName) { this.empName = empName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getHomeNo() { return homeNo; }
    public void setHomeNo(String homeNo) { this.homeNo = homeNo; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getTaluk() { return taluk; }
    public void setTaluk(String taluk) { this.taluk = taluk; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getHigherEducation() { return higherEducation; }
    public void setHigherEducation(String higherEducation) { this.higherEducation = higherEducation; }
    
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public Date getDateOfResign() { return dateOfResign; }
    public void setDateOfResign(Date dateOfResign) { this.dateOfResign = dateOfResign; }

    public Date getRelievingDate() { return relievingDate; }
    public void setRelievingDate(Date relievingDate) { this.relievingDate = relievingDate; }

    public String getNextCompany() { return nextCompany; }
    public void setNextCompany(String nextCompany) { this.nextCompany = nextCompany; }

    public Date getPreviousEmploymentFrom() { return previousEmploymentFrom; }
    public void setPreviousEmploymentFrom(Date previousEmploymentFrom) { this.previousEmploymentFrom = previousEmploymentFrom; }

    public Date getPreviousEmploymentTo() { return previousEmploymentTo; }
    public void setPreviousEmploymentTo(Date previousEmploymentTo) { this.previousEmploymentTo = previousEmploymentTo; }

    public String getReferenceName() { return referenceName; }
    public void setReferenceName(String referenceName) { this.referenceName = referenceName; }

    public String getReferenceContactNo() { return referenceContactNo; }
    public void setReferenceContactNo(String referenceContactNo) { this.referenceContactNo = referenceContactNo; }
     
 


    public static Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

/*
 * package net.javaguides.hrms.bean;
 * 
 * import java.io.Serializable; import java.util.Date; import
 * java.text.SimpleDateFormat; import java.text.ParseException;
 * 
 * public class Employee implements Serializable { private String empCode;
 * private String empName; private Date dob; private String homeNo; private
 * String street; private String taluk; private String district; private String
 * state; private String pincode; private String country; private String email;
 * private String contactNo; private String emergencyContact; private String
 * educationQualification; private String department; private String
 * designation; private Date dateOfResign; private Date relievingDate; private
 * String nextCompany;
 * 
 * // Getters and Setters public String getEmpCode() { return empCode; } public
 * void setEmpCode(String empCode) { this.empCode = empCode; }
 * 
 * public String getEmpName() { return empName; } public void setEmpName(String
 * empName) { this.empName = empName; }
 * 
 * public Date getDob() { return dob; } public void setDob(Date dob) { this.dob
 * = dob; }
 * 
 * public String getHomeNo() { return homeNo; } public void setHomeNo(String
 * homeNo) { this.homeNo = homeNo; }
 * 
 * public String getStreet() { return street; } public void setStreet(String
 * street) { this.street = street; }
 * 
 * public String getTaluk() { return taluk; } public void setTaluk(String taluk)
 * { this.taluk = taluk; }
 * 
 * public String getDistrict() { return district; } public void
 * setDistrict(String district) { this.district = district; }
 * 
 * public String getState() { return state; } public void setState(String state)
 * { this.state = state; }
 * 
 * public String getPincode() { return pincode; } public void setPincode(String
 * pincode) { this.pincode = pincode; }
 * 
 * public String getCountry() { return country; } public void setCountry(String
 * country) { this.country = country; }
 * 
 * public String getEmail() { return email; } public void setEmail(String email)
 * { this.email = email; }
 * 
 * public String getContactNo() { return contactNo; } public void
 * setContactNo(String contactNo) { this.contactNo = contactNo; }
 * 
 * public String getEmergencyContact() { return emergencyContact; } public void
 * setEmergencyContact(String emergencyContact) { this.emergencyContact =
 * emergencyContact; }
 * 
 * public String getEducationQualification() { return educationQualification; }
 * public void setEducationQualification(String educationQualification) {
 * this.educationQualification = educationQualification; }
 * 
 * public String getDepartment() { return department; } public void
 * setDepartment(String department) { this.department = department; }
 * 
 * public String getDesignation() { return designation; } public void
 * setDesignation(String designation) { this.designation = designation; }
 * 
 * public Date getDateOfResign() { return dateOfResign; } public void
 * setDateOfResign(Date dateOfResign) { this.dateOfResign = dateOfResign; }
 * 
 * public Date getRelievingDate() { return relievingDate; } public void
 * setRelievingDate(Date relievingDate) { this.relievingDate = relievingDate; }
 * 
 * public String getNextCompany() { return nextCompany; } public void
 * setNextCompany(String nextCompany) { this.nextCompany = nextCompany; }
 * 
 * public static Date parseDate(String dateStr) { if (dateStr == null ||
 * dateStr.trim().isEmpty()) { return null; } try { SimpleDateFormat sdf = new
 * SimpleDateFormat("yyyy-MM-dd"); return sdf.parse(dateStr); } catch
 * (ParseException e) { e.printStackTrace(); return null; } } }
 * 
 */