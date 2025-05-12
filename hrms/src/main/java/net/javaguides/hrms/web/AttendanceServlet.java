
package net.javaguides.hrms.web;

import net.javaguides.hrms.bean.Attendance;
import net.javaguides.hrms.database.AttendanceDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    private AttendanceDAO attendanceDAO;

    @Override
    public void init() throws ServletException {
        attendanceDAO = new AttendanceDAO();
    }

    // Handle Attendance Marking (Login time)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String empCode = (String) session.getAttribute("emp_code");

        if (empCode == null) {
            response.sendRedirect("employee_login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("login".equals(action)) {
                // Handle login (mark login time)
                Attendance attendance = attendanceDAO.getTodaysAttendanceByEmpCode(empCode);

                if (attendance == null) {
                    // If no attendance for today, mark login time
                    Timestamp loginTime = new Timestamp(System.currentTimeMillis());
                    attendance = new Attendance();
                    attendance.setEmpCode(empCode);
                    attendance.setLoginTime(loginTime);
                    attendance.setAttendanceDate(new java.util.Date(System.currentTimeMillis()));
                    attendance.setStatus("Present");

                    boolean success = attendanceDAO.addAttendance(attendance);
                    if (success) {
                        request.setAttribute("message", "Login time marked successfully. Now, please logout when you finish your work.");
                    } else {
                        request.setAttribute("message", "Error saving attendance.");
                    }
                } else {
                    // If already logged in today
                    request.setAttribute("message", "Attendance already marked for today.");
                }
            } else if ("logout".equals(action)) {
                // Handle logout (update logout time)
                Attendance attendance = attendanceDAO.getTodaysAttendanceByEmpCode(empCode);

                if (attendance != null && attendance.getLogoutTime() == null) {
                    // If attendance exists for today but no logout time, update the logout time
                    Timestamp logoutTime = new Timestamp(System.currentTimeMillis());

                    boolean success = attendanceDAO.updateLogoutTime(empCode, logoutTime);
                    if (success) {
                        request.setAttribute("message", "Logout time updated successfully.");
                    } else {
                        request.setAttribute("message", "Failed to update logout time.");
                    }
                } else {
                    request.setAttribute("message", "No attendance record found for today, or already logged out.");
                }
            } else {
                request.setAttribute("message", "Invalid action.");
            }

            // Forward to the same page to show the message
            request.getRequestDispatcher("attendance.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error while updating attendance.", e);
        }
    }
}

