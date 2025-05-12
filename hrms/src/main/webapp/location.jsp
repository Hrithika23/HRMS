<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Company Location</title>
    <style>
        /* Back Button Style */
        .back-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #1abc9c;  /* Green color */
            color: white;
            text-decoration: none;
            font-size: 16px;
            border-radius: 5px;
            transition: background-color 0.2s, transform 0.2s;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        .back-button:hover {
            background-color: #16a085; /* Darker green on hover */
            transform: translateY(-2px); /* Lift effect */
        }

        .back-button i {
            margin-right: 8px;
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body>
    <h2>Company Location</h2>

    <!-- Google Maps Embed -->
    <div style="width: 100%; height: 400px; margin-bottom: 20px;">
        <iframe
            width="100%"
            height="100%"
            frameborder="0"
            style="border:0"
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3890.334741764406!2d74.85915729999999!3d12.8216326!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3bbcbd7b4612c38d%3A0x2216130d125531f5!2sAbhimo%20Techonologies%20Private%20Limited!5e0!3m2!1sen!2sin!4v1742965722231!5m2!1sen!2sin"
            allowfullscreen loading="lazy" referrerpolicy="no-referrer-when-downgrade">
        </iframe>
    </div>

    <!-- Back to Dashboard Button -->
    <a href="admin_dashboard.jsp" class="back-button"><i class="fas fa-arrow-left"></i> Back to Dashboard</a>

</body>
</html>
