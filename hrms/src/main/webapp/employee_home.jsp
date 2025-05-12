<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page session="true" %>
<%
    String empCode = (String) session.getAttribute("emp_code");
    if (empCode == null) {
        response.sendRedirect("employee_login.jsp");
        return;
    }

    // Variables to store employee info
    String empName = "", dob = "", email = "", contactNo = "", emergencyContact = "";
    String educationQualification = "", department = "", designation = "";
    String homeNo = "", street = "", taluk = "", district = "", pincode = "", state = "", country = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345");

        String sql = "SELECT * FROM employee_master WHERE emp_code = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, empCode);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            empName = rs.getString("emp_name");
            dob = rs.getString("dob");
            homeNo = rs.getString("home_no");
            street = rs.getString("street");
            taluk = rs.getString("taluk");
            district = rs.getString("district");
            pincode = rs.getString("pincode");
            state = rs.getString("state");
            country = rs.getString("country");
            email = rs.getString("email");
            contactNo = rs.getString("contact_no");
            emergencyContact = rs.getString("emergency_contact");
            educationQualification = rs.getString("education_qualification");
            department = rs.getString("department");
            designation = rs.getString("designation");
        } else {
            out.println("<p>Employee data not found.</p>");
        }

        con.close();
    } catch (Exception e) {
        out.println("<p>Error: " + e.getMessage() + "</p>");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Dashboard</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            display: flex;
            height: 100vh;
            background-color: #f0f4f8;
        }

        .sidebar {
            width: 220px;
            background-color: #2c3e50;
            padding: 20px 0;
            color: #fff;
            display: flex;
            flex-direction: column;
        }

        .sidebar h2 {
            text-align: center;
            margin-bottom: 30px;
            font-size: 22px;
        }

        .sidebar a {
            padding: 15px 25px;
            color: #bdc3c7;
            text-decoration: none;
            display: block;
            transition: background 0.3s;
        }

        .sidebar a:hover, .sidebar a.active {
            background-color: #1abc9c;
            color: white;
        }

        .main-content {
            flex-grow: 1;
            padding: 30px;
            background-color: #ffffff;
            overflow-y: auto;
        }

        h1 {
            text-align: center;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #fafafa;
        }

        td {
            padding: 10px 15px;
            border-bottom: 1px solid #ddd;
        }

        tr:nth-child(even) {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Employee Panel</h2>
        <a href="employee_home.jsp" class="active">Dashboard</a>
        <a href="attendance.jsp">Attendance</a>
        <a href="leave_apply.jsp">Apply Leave</a>
        <a href="holiday-list.jsp">Holiday Calendar</a>
        
        <a href="home.jsp">Logout</a>
    </div>

    <div class="main-content">
        <h1>Welcome, <%= empName %>!</h1>
        <table>
            <tr><td><strong>Employee Code:</strong></td><td><%= empCode %></td></tr>
            <tr><td><strong>Name:</strong></td><td><%= empName %></td></tr>
            <tr><td><strong>Date of Birth:</strong></td><td><%= dob %></td></tr>
            <tr>
                <td><strong>Address:</strong></td>
                <td>
                    <%= homeNo %>, <%= street %>, <%= taluk %>, <%= district %> - <%= pincode %><br>
                    <%= state %>, <%= country %>
                </td>
            </tr>
            <tr><td><strong>Email:</strong></td><td><%= email %></td></tr>
            <tr><td><strong>Contact No.:</strong></td><td><%= contactNo %></td></tr>
            <tr><td><strong>Emergency Contact:</strong></td><td><%= emergencyContact %></td></tr>
            <tr><td><strong>Education:</strong></td><td><%= educationQualification %></td></tr>
            <tr><td><strong>Department:</strong></td><td><%= department %></td></tr>
            <tr><td><strong>Designation:</strong></td><td><%= designation %></td></tr>
        </table>
    </div>
</body>
</html>
