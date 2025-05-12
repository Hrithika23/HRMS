<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.javaguides.hrms.bean.Employee" %>
<%
    Employee emp = (Employee) request.getAttribute("emp");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Employee and Send Password</title>

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

        .btn-primary {
            background-color: #2e7d32;
            border-color: #2e7d32;
        }

        .btn-primary:hover {
            background-color: #1b5e20;
            border-color: #1b5e20;
        }

        .message {
            color: red;
            font-weight: bold;
            text-align: center;
        }

        .password-container {
            position: relative;
        }

        .eye-icon {
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            width: 20px;
            opacity: 0.6;
        }

        .eye-icon:hover {
            opacity: 1;
        }

        .text-muted small {
            font-size: 0.85rem;
        }
    </style>

    <script>
        function togglePassword(id, iconId) {
            const field = document.getElementById(id);
            const icon = document.getElementById(iconId);
            if (field.type === "password" || field.type === "text") {
                field.type = field.type === "password" ? "text" : "password";
                icon.src = field.type === "text" ?
                    "https://img.icons8.com/ios-filled/50/000000/visible.png" :
                    "https://img.icons8.com/ios-filled/50/000000/invisible.png";
            }
        }
    </script>
</head>
<body>

<div class="form-container">
    <h2>Reset Employee Password</h2>

    <% if (message != null) { %>
        <div class="message mb-3"><%= message %></div>
    <% } %>

    <form method="post" action="GeneratePasswordServlet">
        <div class="form-group">
            <label for="emp_code">Employee Code <span style="color: red;">*</span></label>
            <input type="text" class="form-control" id="emp_code" name="emp_code" required>
        </div>

        <div class="form-group">
            <label for="name">Name <span style="color: red;">*</span></label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>

        <div class="form-group">
            <label for="email">Email <span style="color: red;">*</span></label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password <span style="color: red;">*</span></label>
            <div class="password-container">
                <input type="text" class="form-control" id="password" name="password" required>
                <img id="eyeGenerated" class="eye-icon" src="https://img.icons8.com/ios-filled/50/000000/invisible.png" onclick="togglePassword('password', 'eyeGenerated')"/>
            </div>
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-primary px-4">Save & Send Password</button>
        </div>
    </form>
</div>

</body>
</html>
