package net.javaguides.hrms.web;

import net.javaguides.hrms.bean.Employee;
import net.javaguides.hrms.database.EmployeeDao;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FetchEmployeeServlet")
public class FetchEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Supports GET (from browser or form)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    // Handles POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empCode = request.getParameter("empCode");

        if (empCode == null || empCode.trim().isEmpty()) {
            request.setAttribute("message", "Please enter Employee Code to fetch data.");
            request.getRequestDispatcher("employee-form.jsp").forward(request, response);
            return;
        }

        EmployeeDao dao = new EmployeeDao();
        Employee emp = dao.getEmployeeByCode(empCode);

        if (emp != null) {
            request.setAttribute("emp", emp);
        } else {
            request.setAttribute("message", "Employee not found for code: " + empCode);
        }

        request.getRequestDispatcher("employee-form.jsp").forward(request, response);
    }
}
