<%@ page import="java.sql.*" %>
<%
    // Get emp_code from session (set this during login)
    String empCode = (String) session.getAttribute("emp_code");

    // For testing fallback
    if (empCode == null || empCode.trim().isEmpty()) {
        empCode = "EMP001"; // Change this to a valid code for testing
    }

    int cl = 0, el = 0, ml = 0;
    boolean recordFound = false;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345");
        PreparedStatement ps = con.prepareStatement("SELECT * FROM leave_master WHERE emp_code=?");
        ps.setString(1, empCode);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            cl = rs.getInt("casual_leave");
            el = rs.getInt("earned_leave");
            ml = rs.getInt("maternity_leave");
            recordFound = true;
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Apply for Leave</title>

    <!-- Bootstrap & jQuery -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

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

        .leave-box {
            display: inline-block;
            padding: 15px 25px;
            margin: 10px;
            border-radius: 10px;
            background: #e8f5e9;
            color: #1b5e20;
            font-weight: bold;
            width: 200px;
            text-align: center;
            box-shadow: 2px 2px 8px rgba(0,0,0,0.1);
        }

        .form-group label {
            font-weight: 600;
        }

        .form-control:focus {
            border-color: #66bb6a;
            box-shadow: 0 0 0 0.2rem rgba(102, 187, 106, 0.25);
        }

        .btn-submit {
            background-color: #2e7d32;
            border-color: #2e7d32;
            color: white;
            font-weight: bold;
            padding: 10px 25px;
            border-radius: 8px;
            transition: background-color 0.3s ease;
        }

        .btn-submit:hover {
            background-color: #1b5e20;
            border-color: #1b5e20;
        }

        .message {
            text-align: center;
            margin-top: 15px;
            font-size: 16px;
        }

    </style>
</head>
<body>

    <div class="form-container">
        <h2>Leave Balance for <%= empCode %></h2>
        <div class="text-center">
            <div class="leave-box">Casual Leave (CL): <%= cl %></div>
            <div class="leave-box">Earned Leave (EL): <%= el %></div>
            <div class="leave-box">Maternity Leave (ML): <%= ml %></div>
        </div>

        <form action="LeaveApplyServlet" method="post" class="mt-4">
            <input type="hidden" name="emp_code" value="<%= empCode %>" />

            <div class="form-group">
                <label for="leave_type">Leave Type</label>
                <select name="leave_type" id="leave_type" class="form-control" required>
                    <option value="CL">Casual Leave</option>
                    <option value="EL">Earned Leave</option>
                    <option value="ML">Maternity Leave</option>
                </select>
            </div>

            <div class="form-group">
                <label for="from_date">From Date</label>
                <input type="date" name="from_date" id="from_date" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="to_date">To Date</label>
                <input type="date" name="to_date" id="to_date" class="form-control" required>
            </div>

            <div class="text-center">
                <input type="submit" value="Apply Leave" class="btn btn-submit">
            </div>
        </form>

        <%
            String msg = request.getParameter("msg");
            if ("success".equals(msg)) {
        %>
            <div class="message text-success">Leave applied successfully!</div>
        <%
            } else if ("error".equals(msg)) {
        %>
            <div class="message text-danger">Something went wrong!</div>
        <%
            }
        %>
    </div>

</body>
</html>
