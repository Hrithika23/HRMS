<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Login</title>
        <style>
        html, body {
            height: 93%;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-image: url('https://uploads-ssl.webflow.com/64380d1f2275c122f55d7d8a/646a02bbe56849aa8a899e4f_0_3.png');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }
        .login-box {
            background: rgba(255, 255, 255, 0.7);
            width: 350px;
            padding: 30px;
            margin: 100px auto;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.5);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #333;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #aaa;
            border-radius: 5px;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #3498db;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #2980b9;
        }
        .error {
            color: red;
            text-align: center;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="login-box">
    <h2>Employee Login</h2>
   <form action="EmployeeLoginServlet" method="post">
    <label>Employee Code:</label>
    <input type="text" name="emp_code" required><br>

    <label>Password:</label>
    <input type="password" name="password" required><br>

    <input type="submit" value="Login" />

    <div class="message">
        <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
    </div>
</form>


</div>

</body>
</html>