<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Leave Requests</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
            background-color: #f4f6f9;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px 15px;
            text-align: center;
        }
        th {
            background-color: #4a90e2;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f1f1f1;
        }
        tr:hover {
            background-color: #e6f7ff;
        }
        td i {
            color: #888;
            font-style: normal;
        }
        .btn {
            padding: 6px 12px;
            margin: 2px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            color: white;
        }
        .btn-approve {
            background-color: #28a745;
        }
        .btn-reject {
            background-color: #dc3545;
        }
        .status-approved {
            color: #28a745;
            font-weight: bold;
        }
        .status-rejected {
            color: #dc3545;
            font-weight: bold;
        }
        .status-pending {
            color: #ff9800;
            font-weight: bold;
        }
        .message {
            text-align: center;
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body>

<h2>All Leave Requests</h2>
<table>
    <tr>
        <th>Emp Code</th>
        <th>Type</th>
        <th>From</th>
        <th>To</th>
        <th>Days</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345");

        PreparedStatement ps = con.prepareStatement("SELECT *, DATEDIFF(to_date, from_date) + 1 AS days FROM leave_requests ORDER BY status DESC, id DESC");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String status = rs.getString("status");
            String statusClass = "status-pending";
            if ("Approved".equalsIgnoreCase(status)) statusClass = "status-approved";
            else if ("Rejected".equalsIgnoreCase(status)) statusClass = "status-rejected";
%>
    <tr>
        <td><%= rs.getString("emp_code") %></td>
        <td><%= rs.getString("leave_type") %></td>
        <td><%= rs.getDate("from_date") %></td>
        <td><%= rs.getDate("to_date") %></td>
        <td><%= rs.getInt("days") %></td>
        <td class="<%= statusClass %>"><%= status %></td>
        <td>
            <% if ("Pending".equalsIgnoreCase(status)) { %>
                <form action="<%= request.getContextPath() %>/LeaveApprovalServlet" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= rs.getInt("id") %>" />
                    <input type="hidden" name="emp_code" value="<%= rs.getString("emp_code") %>" />
                    <button type="submit" name="action" value="approve" class="btn btn-approve">Approve</button>
                    <button type="submit" name="action" value="reject" class="btn btn-reject">Reject</button>
                </form>
            <% } else { %>
                <i class="<%= statusClass %>"><%= status %></i>
            <% } %>
        </td>
    </tr>
<%
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</table>

<%
    String msg = (String) request.getAttribute("msg");
    if (msg != null) {
%>
    <p class="message"><%= msg %></p>
<%
    }
%>

</body>
</html>
