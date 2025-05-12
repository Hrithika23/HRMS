package net.javaguides.hrms.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

@WebServlet("/SubmitSalaryServlet")
public class SubmitSalaryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve form values
            String empCode = request.getParameter("empCode");
            double salary = Double.parseDouble(request.getParameter("salary"));
            int paidDays = Integer.parseInt(request.getParameter("paidDays"));
            double pfSal = Double.parseDouble(request.getParameter("pfSal"));
            double esiSal = Double.parseDouble(request.getParameter("esiSal"));
            double pt = Double.parseDouble(request.getParameter("pt"));

            // Calculate gross salary and deductions
            double grossSalary = Math.ceil((salary * paidDays) / 31.0);
            double pfEe = Math.ceil((pfSal * 12) / 100.0);
            double pfEr = Math.round((pfSal * 12) / 100.0);
            double esicEe = Math.ceil((esiSal * 0.75) / 100.0);
            double esicEr = Math.ceil((esiSal * 3.25) / 100.0);
            double deduction = pfEe + pfEr + esicEe + esicEr + pt;
            double netSalary = grossSalary - deduction;

            // Retrieve other form values
            String status = request.getParameter("status");
            String month = request.getParameter("month");
            String year = request.getParameter("year");
            String email = "employee@example.com";  // Replace with actual employee's email

            // JDBC connection (No JNDI lookup)
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345");

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO salary (emp_code, salary, paid_days, pf_sal, esi_sal, pt, gross_salary, pf_ee, pf_er, esic_ee, esic_er, deduction, net_salary, status, month, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            ps.setString(1, empCode);
            ps.setDouble(2, salary);
            ps.setInt(3, paidDays);
            ps.setDouble(4, pfSal);
            ps.setDouble(5, esiSal);
            ps.setDouble(6, pt);
            ps.setDouble(7, grossSalary);
            ps.setDouble(8, pfEe);
            ps.setDouble(9, pfEr);
            ps.setDouble(10, esicEe);
            ps.setDouble(11, esicEr);
            ps.setDouble(12, deduction);
            ps.setDouble(13, netSalary);
            ps.setString(14, status);
            ps.setString(15, month);
            ps.setString(16, year);

            int result = ps.executeUpdate();

            if (result > 0) {
                // Send email after successful salary submission
                sendEmail(email, month, year, netSalary);

                // Redirect to admin dashboard
                response.sendRedirect("admin_dashboard.jsp?msg=Salary submitted successfully");
            } else {
                // Redirect back to salary form if insertion failed
                response.sendRedirect("salary_form.jsp?msg=Error submitting salary details");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("salary_form.jsp?msg=Exception occurred: " + e.getMessage());
        }
    }

    private void sendEmail(String recipientEmail, String month, String year, double netSalary) {
        // Set email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with authentication
        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("hrithikakenaje@gmail.com", "bldi tanw uqpu jeey"); // Use your email credentials here
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("hrithikakenaje@gmail.com")); // Your email address
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail)); // Employee's email
            message.setSubject("Salary Approved for " + month + " " + year);
            message.setText("Dear Employee,\n\nYour salary for the month of " + month + " " + year + " has been approved. Your net salary is " + netSalary + ".\n\nBest regards,\nHR Department");

            // Send the email
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
