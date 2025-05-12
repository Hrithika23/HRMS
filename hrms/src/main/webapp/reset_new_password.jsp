<%@ page language="java" %>
<html>
<head>
    <title>Reset Your Password</title>
    
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
    </style>

    <script>
        function togglePassword(id, iconId) {
            const field = document.getElementById(id);
            const icon = document.getElementById(iconId);
            if (field.type === "password") {
                field.type = "text";
                icon.src = "https://img.icons8.com/ios-filled/50/000000/visible.png";
            } else {
                field.type = "password";
                icon.src = "https://img.icons8.com/ios-filled/50/000000/invisible.png";
            }
        }
    </script>
</head>
<body>

<div class="form-container">
    <h2>Reset Your Password</h2>

    <form action="UpdatePasswordServlet" method="post">
        <div class="form-group">
            <label for="newPassword">New Password:</label>
            <div class="password-container">
                <input type="password" name="newPassword" id="newPassword" class="form-control" required>
                <img id="eyeNew" class="eye-icon" src="https://img.icons8.com/ios-filled/50/000000/invisible.png" 
                     onclick="togglePassword('newPassword', 'eyeNew')" />
            </div>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password:</label>
            <div class="password-container">
                <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" required>
                <img id="eyeConfirm" class="eye-icon" src="https://img.icons8.com/ios-filled/50/000000/invisible.png" 
                     onclick="togglePassword('confirmPassword', 'eyeConfirm')" />
            </div>
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-primary px-4">Update Password</button>
        </div>
    </form>
</div>

</body>
</html>
