package net.javaguides.hrms.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import net.javaguides.hrms.database.EmployeeDao;
import net.javaguides.hrms.bean.Employee;

@WebServlet("/RegisterEmployeeServlet")
public class RegisterEmployeeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String actionType = request.getParameter("actionType");
        String empCode = request.getParameter("empCode");
        String empName = request.getParameter("empName");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");

        // Address fields
        String homeNo = request.getParameter("homeNo");
        String street = request.getParameter("street");
        String taluk = request.getParameter("taluk");
        String district = request.getParameter("district");
        String pincode = request.getParameter("pincode");
        String state = request.getParameter("state");
        String country = request.getParameter("country");

        String email = request.getParameter("email");
        String contactNo = request.getParameter("contactNo");
        String emergencyContact = request.getParameter("emergencyContact");

        String higherEducation = request.getParameter("higherEducation");
        String specialization = request.getParameter("specialization");
        String department = request.getParameter("department");
        String designation = request.getParameter("designation");

        String dateOfResign = request.getParameter("dateOfResign");
        String relievingDate = request.getParameter("relievingDate");
        String nextCompany = request.getParameter("nextCompany");

        String previousEmploymentFrom = request.getParameter("previousEmploymentFrom");
        String previousEmploymentTo = request.getParameter("previousEmploymentTo");
        String referenceName = request.getParameter("referenceName");
        String referenceContactNo = request.getParameter("referenceContactNo");

        try {
            if ("View".equalsIgnoreCase(actionType)) {
                EmployeeDao dao = new EmployeeDao();
                Employee emp = dao.getEmployeeByCode(empCode);

                if (emp != null) {
                    request.setAttribute("employee", emp);
                } else {
                    request.setAttribute("error", "Employee data not available.");
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("viewEmployee.jsp");
                dispatcher.forward(request, response);
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345"
            );

            PreparedStatement ps;
            int rows = 0;

            if ("Modify".equalsIgnoreCase(actionType)) {
                String query = "UPDATE employee_master SET emp_name=?, gender=?, dob=?, home_no=?, street=?, taluk=?, district=?, pincode=?, state=?, country=?, email=?, contact_no=?, emergency_contact=?, higher_education=?, specialization=?, department=?, designation=?, date_of_resign=?, relieving_date=?, next_company=?, previous_employment_from=?, previous_employment_to=?, reference_name=?, reference_contact_no=? WHERE emp_code=?";
                ps = con.prepareStatement(query);
                ps.setString(1, empName);
                ps.setString(2, gender);
                ps.setDate(3, dob == null || dob.isEmpty() ? null : Date.valueOf(dob));
                ps.setString(4, homeNo);
                ps.setString(5, street);
                ps.setString(6, taluk);
                ps.setString(7, district);
                ps.setString(8, pincode);
                ps.setString(9, state);
                ps.setString(10, country);
                ps.setString(11, email);
                ps.setString(12, contactNo);
                ps.setString(13, emergencyContact);
                ps.setString(14, higherEducation);
                ps.setString(15, specialization);
                ps.setString(16, department);
                ps.setString(17, designation);
                ps.setDate(18, dateOfResign == null || dateOfResign.isEmpty() ? null : Date.valueOf(dateOfResign));
                ps.setDate(19, relievingDate == null || relievingDate.isEmpty() ? null : Date.valueOf(relievingDate));
                ps.setString(20, nextCompany);
                ps.setDate(21, previousEmploymentFrom == null || previousEmploymentFrom.isEmpty() ? null : Date.valueOf(previousEmploymentFrom));
                ps.setDate(22, previousEmploymentTo == null || previousEmploymentTo.isEmpty() ? null : Date.valueOf(previousEmploymentTo));
                ps.setString(23, referenceName);
                ps.setString(24, referenceContactNo);
                ps.setString(25, empCode);

                rows = ps.executeUpdate();

                if (rows > 0) {
                    out.println("<h2>Employee updated successfully!</h2>");
                } else {
                    out.println("<h2>No record found to update.</h2>");
                }

            } else {
                String query = "INSERT INTO employee_master (emp_code, emp_name, gender, dob, home_no, street, taluk, district, pincode, state, country, email, contact_no, emergency_contact, higher_education, specialization, department, designation, date_of_resign, relieving_date, next_company, previous_employment_from, previous_employment_to, reference_name, reference_contact_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                ps = con.prepareStatement(query);
                ps.setString(1, empCode);
                ps.setString(2, empName);
                ps.setString(3, gender);
                ps.setDate(4, dob == null || dob.isEmpty() ? null : Date.valueOf(dob));
                ps.setString(5, homeNo);
                ps.setString(6, street);
                ps.setString(7, taluk);
                ps.setString(8, district);
                ps.setString(9, pincode);
                ps.setString(10, state);
                ps.setString(11, country);
                ps.setString(12, email);
                ps.setString(13, contactNo);
                ps.setString(14, emergencyContact);
                ps.setString(15, higherEducation);
                ps.setString(16, specialization);
                ps.setString(17, department);
                ps.setString(18, designation);
                ps.setDate(19, dateOfResign == null || dateOfResign.isEmpty() ? null : Date.valueOf(dateOfResign));
                ps.setDate(20, relievingDate == null || relievingDate.isEmpty() ? null : Date.valueOf(relievingDate));
                ps.setString(21, nextCompany);
                ps.setDate(22, previousEmploymentFrom == null || previousEmploymentFrom.isEmpty() ? null : Date.valueOf(previousEmploymentFrom));
                ps.setDate(23, previousEmploymentTo == null || previousEmploymentTo.isEmpty() ? null : Date.valueOf(previousEmploymentTo));
                ps.setString(24, referenceName);
                ps.setString(25, referenceContactNo);

                rows = ps.executeUpdate();

                if (rows > 0) {
                    out.println("<h2>Employee added successfully!</h2>");
                } else {
                    out.println("<h2>Failed to add employee.</h2>");
                }
            }

            con.close();

        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h3>Please submit the form using POST method.</h3>");
    }
}

/*
 * package net.javaguides.hrms.web;
 * 
 * import jakarta.servlet.*; import jakarta.servlet.annotation.WebServlet;
 * import jakarta.servlet.http.*; import java.io.IOException; import
 * java.io.PrintWriter; import java.sql.*;
 * 
 * import net.javaguides.hrms.database.EmployeeDao; import
 * net.javaguides.hrms.bean.Employee;
 * 
 * @WebServlet("/RegisterEmployeeServlet") public class RegisterEmployeeServlet
 * extends HttpServlet {
 * 
 * protected void doPost(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException {
 * response.setContentType("text/html"); PrintWriter out = response.getWriter();
 * 
 * String actionType = request.getParameter("actionType"); String empCode =
 * request.getParameter("empCode"); String empName =
 * request.getParameter("empName"); String dob = request.getParameter("dob");
 * 
 * // Address fields String homeNo = request.getParameter("homeNo"); String
 * street = request.getParameter("street"); String taluk =
 * request.getParameter("taluk"); String district =
 * request.getParameter("district"); String pincode =
 * request.getParameter("pincode"); String state =
 * request.getParameter("state"); String country =
 * request.getParameter("country");
 * 
 * String email = request.getParameter("email"); String contactNo =
 * request.getParameter("contactNo"); String emergencyContact =
 * request.getParameter("emergencyContact"); String education =
 * request.getParameter("educationQualification"); String department =
 * request.getParameter("department"); String designation =
 * request.getParameter("designation"); String dateOfResign =
 * request.getParameter("dateOfResign"); String relievingDate =
 * request.getParameter("relievingDate"); String nextCompany =
 * request.getParameter("nextCompany");
 * 
 * try { if ("View".equalsIgnoreCase(actionType)) { EmployeeDao dao = new
 * EmployeeDao(); Employee emp = dao.getEmployeeByCode(empCode);
 * 
 * if (emp != null) { request.setAttribute("employee", emp); } else {
 * request.setAttribute("error", "Employee data not available."); }
 * RequestDispatcher dispatcher =
 * request.getRequestDispatcher("viewEmployee.jsp"); dispatcher.forward(request,
 * response); return; }
 * 
 * Class.forName("com.mysql.cj.jdbc.Driver"); Connection con =
 * DriverManager.getConnection( "jdbc:mysql://localhost:3306/hrms", "root",
 * "Hrithi@12345" );
 * 
 * PreparedStatement ps; int rows = 0;
 * 
 * if ("Modify".equalsIgnoreCase(actionType)) { String query =
 * "UPDATE employee_master SET emp_name=?, dob=?, home_no=?, street=?, taluk=?, district=?, pincode=?, state=?, country=?, email=?, contact_no=?, emergency_contact=?, education_qualification=?, department=?, designation=?, date_of_resign=?, relieving_date=?, next_company=? WHERE emp_code=?"
 * ; ps = con.prepareStatement(query); ps.setString(1, empName); ps.setDate(2,
 * dob == null || dob.isEmpty() ? null : Date.valueOf(dob)); ps.setString(3,
 * homeNo); ps.setString(4, street); ps.setString(5, taluk); ps.setString(6,
 * district); ps.setString(7, pincode); ps.setString(8, state); ps.setString(9,
 * country); ps.setString(10, email); ps.setString(11, contactNo);
 * ps.setString(12, emergencyContact); ps.setString(13, education);
 * ps.setString(14, department); ps.setString(15, designation); ps.setDate(16,
 * dateOfResign == null || dateOfResign.isEmpty() ? null :
 * Date.valueOf(dateOfResign)); ps.setDate(17, relievingDate == null ||
 * relievingDate.isEmpty() ? null : Date.valueOf(relievingDate));
 * ps.setString(18, nextCompany); ps.setString(19, empCode); rows =
 * ps.executeUpdate();
 * 
 * if (rows > 0) { out.println("<h2>Employee updated successfully!</h2>"); }
 * else { out.println("<h2>No record found to update.</h2>"); }
 * 
 * } else { String query =
 * "INSERT INTO employee_master (emp_code, emp_name, dob, home_no, street, taluk, district, pincode, state, country, email, contact_no, emergency_contact, education_qualification, department, designation, date_of_resign, relieving_date, next_company) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
 * ; ps = con.prepareStatement(query); ps.setString(1, empCode); ps.setString(2,
 * empName); ps.setDate(3, dob == null || dob.isEmpty() ? null :
 * Date.valueOf(dob)); ps.setString(4, homeNo); ps.setString(5, street);
 * ps.setString(6, taluk); ps.setString(7, district); ps.setString(8, pincode);
 * ps.setString(9, state); ps.setString(10, country); ps.setString(11, email);
 * ps.setString(12, contactNo); ps.setString(13, emergencyContact);
 * ps.setString(14, education); ps.setString(15, department); ps.setString(16,
 * designation); ps.setDate(17, dateOfResign == null || dateOfResign.isEmpty() ?
 * null : Date.valueOf(dateOfResign)); ps.setDate(18, relievingDate == null ||
 * relievingDate.isEmpty() ? null : Date.valueOf(relievingDate));
 * ps.setString(19, nextCompany); rows = ps.executeUpdate();
 * 
 * if (rows > 0) { out.println("<h2>Employee added successfully!</h2>"); } else
 * { out.println("<h2>Failed to add employee.</h2>"); } }
 * 
 * con.close();
 * 
 * } catch (Exception e) { out.println("<h2>Error: " + e.getMessage() +
 * "</h2>"); e.printStackTrace(out); } }
 * 
 * protected void doGet(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException {
 * response.setContentType("text/html"); response.getWriter().
 * println("<h3>Please submit the form using POST method.</h3>"); } }
 * 
 */