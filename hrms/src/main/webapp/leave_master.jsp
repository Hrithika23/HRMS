<%@ page import="java.sql.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Leave Master</title>

    <!-- Bootstrap & jQuery -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f9;
            padding: 30px;
        }

        .form-container {
            max-width: 700px;
            margin: auto;
            background: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            animation: fadeIn 0.6s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h2 {
            font-weight: 600;
            color: #2e7d32;
            text-align: center;
            margin-bottom: 25px;
            letter-spacing: 1px;
        }

        .form-group label {
            font-weight: 600;
        }

        .form-control:focus {
            border-color: #66bb6a;
            box-shadow: 0 0 0 0.2rem rgba(102, 187, 106, 0.25);
        }

        .btn-action {
            background-color: #2e7d32;
            border-color: #2e7d32;
            color: white;
            font-weight: bold;
            margin: 0 10px;
            padding: 10px 25px;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        .btn-action:hover {
            background-color: #1b5e20;
            border-color: #1b5e20;
        }

        .top-buttons {
            text-align: center;
            margin-bottom: 30px;
        }

        .success-message {
            color: green;
            font-weight: bold;
            text-align: center;
            margin-bottom: 20px;
        }

        .error-message {
            color: red;
            font-weight: bold;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Leave Master Page</h2>

    <!-- Display Success or Error Message -->
    <%
        String successMessage = (String) request.getAttribute("successMessage");
        if (successMessage != null) {
    %>
        <div class="success-message">
            <%= successMessage %>
        </div>
    <% 
        }

        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <div class="error-message">
            <%= errorMessage %>
        </div>
    <% 
        }
    %>

    <form action="LeaveMasterServlet" method="post">

        <div class="top-buttons">
            <input type="submit" name="action" value="Add" class="btn btn-action">
            
        </div>

        <div class="form-group">
            <label for="emp_code">Employee Code <span style="color: red;">*</span></label>
            <input type="text" class="form-control" id="emp_code" name="emp_code" required>
        </div>

        <div class="form-group">
            <label for="year">Year <span style="color: red;">*</span></label>
            <input type="number" class="form-control" id="year" name="year" required>
        </div>

        <div class="form-group">
            <label for="casual_leave">Casual Leave (max 12) <span style="color: red;">*</span></label>
            <input type="number" class="form-control" id="casual_leave" name="casual_leave" max="12" required>
        </div>

        <div class="form-group">
            <label for="earned_leave">Earned Leave <span style="color: red;">*</span></label>
            <input type="number" class="form-control" id="earned_leave" name="earned_leave" required>
        </div>

        <div class="form-group">
            <label for="maternity_leave">Maternity Leave (max 90)</label>
            <input type="number" class="form-control" id="maternity_leave" name="maternity_leave" max="90">
        </div>

    </form>
</div>

</body>
</html>
