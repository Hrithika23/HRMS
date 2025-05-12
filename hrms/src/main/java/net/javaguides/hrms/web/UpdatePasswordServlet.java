package net.javaguides.hrms.web;

import net.javaguides.hrms.database.StaffDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import net.javaguides.hrms.bean.Staff;
import net.javaguides.hrms.util.DBConnection;
import net.javaguides.hrms.util.PasswordUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UpdatePasswordServlet")
public class UpdatePasswordServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UpdatePasswordServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String emp_code = (String) request.getSession().getAttribute("emp_code");

        // Validate input
        if (newPassword == null || confirmPassword == null || emp_code == null) {
            request.setAttribute("message", "Invalid request, missing required fields.");
            request.getRequestDispatcher("reset_new_password.jsp").forward(request, response);
            return;
        }

        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "Passwords do not match!");
            request.getRequestDispatcher("reset_new_password.jsp").forward(request, response);
            return;
        }

        // Hash the new password
        String hashedPassword = PasswordUtils.hashPassword(newPassword);

        try {
            // Use DAO to update the password
            StaffDAO dao = new StaffDAO(DBConnection.getConnection());
            dao.updatePassword(emp_code, hashedPassword);  // No need to check boolean value here

            // Invalidate the session after password update
            request.getSession().invalidate();  // This will completely invalidate the session

            // Redirect to employee home page after password reset
            response.sendRedirect("employee_home.jsp");

        } catch (Exception e) {
            // Log the error and show a generic message to the user
            logger.log(Level.SEVERE, "Error updating password for employee: " + emp_code, e);
            request.setAttribute("message", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("reset_new_password.jsp").forward(request, response);
        }
    }
}
