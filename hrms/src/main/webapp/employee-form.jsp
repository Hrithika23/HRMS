<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="net.javaguides.hrms.bean.Employee" %>
<%
    Employee emp = (Employee) request.getAttribute("emp");
    String message = (String) request.getAttribute("message");
    String selectedMode = request.getParameter("mode");
    if (selectedMode == null) selectedMode = (String) request.getAttribute("mode");
    if (selectedMode == null) selectedMode = "Add";
    boolean isView = "View".equals(selectedMode);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f6f9; padding: 30px; }
        .form-container {
            max-width: 850px;
            margin: auto;
            background: #fff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            animation: fadeIn 1s ease-in;
        }
        @keyframes fadeIn { from { opacity: 0; transform: translateY(20px); } to { opacity: 1; transform: translateY(0); } }
        h2, h4, h5 { color: green; font-weight: bold; text-align: center; }
        .form-group label { font-weight: 600; color: #333; }
        .btn { transition: all 0.3s ease; }
        .required-star { color: red; font-weight: bold; }
    </style>
    <script>
        function setMode(mode) {
            document.getElementById("fetchMode").value = mode;
            document.getElementById("modalTitle").innerText = mode + " Employee";
        }
    </script>
</head>

<body>

<div class="text-center mb-4">
    <h2>Employee Master!</h2>
</div>

<!-- Mode Buttons -->
<div class="text-center mb-4">
    <form method="get" action="employee-form.jsp" class="d-inline">
        <input type="hidden" name="mode" value="Add">
        <input type="submit" class="btn btn-primary" value="Add">
    </form>
    <button class="btn btn-primary" data-toggle="modal" data-target="#fetchModal" onclick="setMode('Modify')">Modify</button>
    <button class="btn btn-primary" data-toggle="modal" data-target="#fetchModal" onclick="setMode('View')">View</button>
</div>

<!-- Modal for View/Modify -->
<div class="modal fade" id="fetchModal" tabindex="-1" role="dialog" aria-labelledby="fetchModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <form method="post" action="FetchEmployeeServlet" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalTitle">Fetch Employee</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span>&times;</span></button>
            </div>
            <div class="modal-body">
                <label for="empCode">Enter Employee Code:<span class="required-star">*</span></label>
                <input type="text" name="empCode" id="empCode" class="form-control" required>
                <input type="hidden" name="mode" id="fetchMode" value="">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Fetch</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
            </div>
        </form>
    </div>
</div>

<!-- Message -->
<% if (message != null) { %>
    <div class="alert alert-info text-center"><%= message %></div>
<% } %>

<div class="form-container">
    <h4><%= selectedMode %> Employee Details</h4>
    <form method="post" action="RegisterEmployeeServlet">
        <input type="hidden" name="actionType" value="<%= selectedMode %>">

        <!-- Section: Personal Details -->
        <h5>Personal Details</h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label>Employee Code *</label>
                <input type="text" class="form-control" name="empCode" value="<%= emp != null ? emp.getEmpCode() : "" %>" <%= isView ? "readonly" : "required" %>>
            </div>
            <div class="form-group col-md-4">
                <label>Employee Name *</label>
                <input type="text" class="form-control" name="empName" value="<%= emp != null ? emp.getEmpName() : "" %>" <%= isView ? "readonly" : "required" %>>
            </div>
            <div class="form-group col-md-4">
                <label>Gender</label>
                <select class="form-control" name="gender" <%= isView ? "disabled" : "" %>>
                    <option <%= emp != null && "Male".equals(emp.getGender()) ? "selected" : "" %>>Male</option>
                    <option <%= emp != null && "Female".equals(emp.getGender()) ? "selected" : "" %>>Female</option>
                    <option <%= emp != null && "Other".equals(emp.getGender()) ? "selected" : "" %>>Other</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label>Date of Birth *</label>
            <input type="date" class="form-control" name="dob" value="<%= emp != null ? emp.getDob() : "" %>" <%= isView ? "readonly" : "required" %>>
        </div>

        <!-- Section: Address -->
        <h5>Address Details</h5>
        <div class="form-row">
            <div class="form-group col-md-4"><label>Home No</label><input type="text" class="form-control" name="homeNo" value="<%= emp != null ? emp.getHomeNo() : "" %>" <%= isView ? "readonly" : "required" %>></div>
            <div class="form-group col-md-4"><label>Street</label><input type="text" class="form-control" name="street" value="<%= emp != null ? emp.getStreet() : "" %>" <%= isView ? "readonly" : "required" %>></div>
            <div class="form-group col-md-4"><label>Taluk</label><input type="text" class="form-control" name="taluk" value="<%= emp != null ? emp.getTaluk() : "" %>" <%= isView ? "readonly" : "required" %>></div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-4"><label>District</label><input type="text" class="form-control" name="district" value="<%= emp != null ? emp.getDistrict() : "" %>" <%= isView ? "readonly" : "required" %>></div>
            <div class="form-group col-md-4"><label>State</label><input type="text" class="form-control" name="state" value="<%= emp != null ? emp.getState() : "" %>" <%= isView ? "readonly" : "required" %>></div>
            <div class="form-group col-md-4"><label>Pincode</label><input type="text" class="form-control" name="pincode" value="<%= emp != null ? emp.getPincode() : "" %>" <%= isView ? "readonly" : "required" %>></div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6"><label>Country</label><input type="text" class="form-control" name="country" value="<%= emp != null ? emp.getCountry() : "" %>" <%= isView ? "readonly" : "required" %>></div>
            <div class="form-group col-md-6"><label>Email</label><input type="email" class="form-control" name="email" value="<%= emp != null ? emp.getEmail() : "" %>" <%= isView ? "readonly" : "required" %>></div>
        </div>

        <!-- Section: Contact -->
        <h5>Contact Information</h5>
        <div class="form-row">
            <div class="form-group col-md-6"><label>Contact No</label><input type="text" class="form-control" name="contactNo" value="<%= emp != null ? emp.getContactNo() : "" %>" <%= isView ? "readonly" : "required" %>></div>
            <div class="form-group col-md-6"><label>Emergency Contact</label><input type="text" class="form-control" name="emergencyContact" value="<%= emp != null ? emp.getEmergencyContact() : "" %>" <%= isView ? "readonly" : "" %>></div>
        </div>

        <!-- Section: Education -->
        <h5>Education</h5>
        <div class="form-row">
            <div class="form-group col-md-6"><label>Higher Education</label><input type="text" class="form-control" name="higherEducation" value="<%= emp != null ? emp.getHigherEducation() : "" %>" <%= isView ? "readonly" : "" %>></div>
            <div class="form-group col-md-6"><label>Specialization</label><input type="text" class="form-control" name="specialization" value="<%= emp != null ? emp.getSpecialization() : "" %>" <%= isView ? "readonly" : "" %>></div>
        </div>

        <!-- Section: Employment -->
        <h5>Current Employment</h5>
        <div class="form-row">
            <div class="form-group col-md-6"><label>Department</label><input type="text" class="form-control" name="department" value="<%= emp != null ? emp.getDepartment() : "" %>" <%= isView ? "readonly" : "" %>></div>
            <div class="form-group col-md-6"><label>Designation</label><input type="text" class="form-control" name="designation" value="<%= emp != null ? emp.getDesignation() : "" %>" <%= isView ? "readonly" : "" %>></div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6"><label>Date of Resign</label><input type="date" class="form-control" name="dateOfResign" value="<%= emp != null ? emp.getDateOfResign() : "" %>" <%= isView ? "readonly" : "" %>></div>
            <div class="form-group col-md-6"><label>Relieving Date</label><input type="date" class="form-control" name="relievingDate" value="<%= emp != null ? emp.getRelievingDate() : "" %>" <%= isView ? "readonly" : "" %>></div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6"><label>Next Company</label><input type="text" class="form-control" name="nextCompany" value="<%= emp != null ? emp.getNextCompany() : "" %>" <%= isView ? "readonly" : "" %>></div>
        </div>

        <!-- Section: References -->
        <h5>References</h5>
        <div class="form-row">
            <div class="form-group col-md-6"><label>Reference Name</label><input type="text" class="form-control" name="referenceName" value="<%= emp != null ? emp.getReferenceName() : "" %>" <%= isView ? "readonly" : "" %>></div>
            <div class="form-group col-md-6"><label>Reference Contact No</label><input type="text" class="form-control" name="referenceContactNo" value="<%= emp != null ? emp.getReferenceContactNo() : "" %>" <%= isView ? "readonly" : "" %>></div>
        </div>

        <!-- Submit Button -->
        <div class="text-center">
            <% if (!isView) { %>
                <button type="submit" class="btn btn-success">Submit</button>
            <% } %>
        </div>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
