<%@ page contentType="text/html; charset=UTF-8" session="true" %>
<%@ page import="net.javaguides.hrms.bean.Attendance" %>
<%
    String empCode = (String) session.getAttribute("emp_code");
    if (empCode == null) {
        response.sendRedirect("employee_login.jsp");
        return;
    }

    // Retrieve the Attendance object from the session
    Attendance attendance = (Attendance) session.getAttribute("attendance");

    // If attendance object is not in session, you can create a new one if necessary
    if (attendance == null) {
        attendance = new Attendance();
        session.setAttribute("attendance", attendance);
    }

    // Retrieve any message set by the servlet
    String message = (String) request.getAttribute("message");
%>
<html>
<head>
    <title>Employee Attendance</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8ff;
            text-align: center;
            padding: 30px;
        }

        h2 {
            color: #333;
        }

        form {
            background: #4682b4;
            max-width: 400px;
            margin: 20px auto;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0px 0px 12px rgba(0,0,0,0.2);
            text-align: center;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 12px 20px;
            cursor: pointer;
            font-size: 18px;
            border-radius: 5px;
            margin: 10px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        input[type="button"] {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 12px 20px;
            cursor: pointer;
            font-size: 18px;
            border-radius: 5px;
        }

        input[type="button"]:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
    <h2>Welcome, Employee <%= empCode %></h2>

    <!-- Display success/error message if exists -->
    <%
        if (message != null) {
            out.print("<p style='color: green;'>" + message + "</p>");
        }
    %>


    <!-- Form to Mark Attendance (Login) -->
    <form action="AttendanceServlet" method="post">
        <input type="hidden" name="action" value="login">
        <input type="submit" value="Mark Attendance">
        <input type="hidden" name="action" value="logout">
        <input type="submit" value="Log Out">
    </form>

    
</body>
</html>
