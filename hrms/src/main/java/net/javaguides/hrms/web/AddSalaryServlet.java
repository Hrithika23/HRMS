package net.javaguides.hrms.web;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import net.javaguides.hrms.bean.Salary;
import net.javaguides.hrms.database.SalaryDAO;

@WebServlet("/AddSalaryServlet")
public class AddSalaryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empCode = request.getParameter("empCode");
        String empName = request.getParameter("empName");
        double salary = Double.parseDouble(request.getParameter("salary"));
        int paidDays = Integer.parseInt(request.getParameter("paidDays"));
        double pfSal = Double.parseDouble(request.getParameter("pfSal"));
        double esiSal = Double.parseDouble(request.getParameter("esiSal"));
        double pt = Double.parseDouble(request.getParameter("pt"));
        String month = request.getParameter("month");
        int year = Integer.parseInt(request.getParameter("year"));

        Salary salaryObj = new Salary();
        salaryObj.setEmpCode(empCode);
        salaryObj.setEmpName(empName);
        salaryObj.setSalary(salary);
        salaryObj.setPaidDays(paidDays);
        salaryObj.setPfSal(pfSal);
        salaryObj.setEsiSal(esiSal);
        salaryObj.setPt(pt);
        salaryObj.setMonth(month);
        salaryObj.setYear(year);
        salaryObj.calculateSalary();

        SalaryDAO dao = new SalaryDAO();
        if (dao.addSalary(salaryObj)) {
            response.sendRedirect("admin_dashboard.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
