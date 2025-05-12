package net.javaguides.hrms.web;

import net.javaguides.hrms.database.StaffDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import net.javaguides.hrms.bean.Staff;
import net.javaguides.hrms.util.DBConnection;
import net.javaguides.hrms.util.PasswordUtils;

import java.io.IOException;

@WebServlet("/EmployeeLoginServlet")
public class EmployeeLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("✅ EmployeeLoginServlet triggered");

        String emp_code = request.getParameter("emp_code");
        String password = request.getParameter("password");

        try {
            StaffDAO dao = new StaffDAO(DBConnection.getConnection());
            Staff staff = dao.getStaffByEmpCode(emp_code);

            if (staff != null) {
                boolean matched = PasswordUtils.checkPassword(password, staff.getPassword());

                if (matched) {
                    HttpSession session = request.getSession();
                    session.setAttribute("employee", staff);
                    session.setAttribute("emp_code", emp_code);  // Set emp_code in session

                    if (staff.isTempPassword()) {
                        response.sendRedirect("reset_new_password.jsp");
                    } else {
                        response.sendRedirect("employee_home.jsp");  // Redirect to employee home page
                    }

                } else {
                    request.setAttribute("message", "❌ Invalid password.");
                    request.getRequestDispatcher("employee_login.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("message", "❌ Employee not found with the given code.");
                request.getRequestDispatcher("employee_login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "❌ Server error occurred.");
            request.getRequestDispatcher("employee_login.jsp").forward(request, response);
        }
    }

    // ✅ ADD THIS METHOD BELOW doPost()
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().println("✅ EmployeeLoginServlet is working fine!");
    }
}
