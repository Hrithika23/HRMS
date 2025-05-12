<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, net.javaguides.hrms.bean.Holiday" %>
<%
    if (request.getAttribute("listHolidays") == null) {
        response.sendRedirect("holiday?action=list");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Holiday List</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .page-container {
            width: 70%;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-top: 30px;
            margin-bottom: 30px;
        }

        .top-bar {
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .table {
            margin-top: 20px;
            background: white;
            border-radius: 10px;
            overflow: hidden;
        }

        .table th, .table td {
            vertical-align: middle;
            text-align: center;
        }

        .btn-success {
            padding: 10px 20px;
            font-size: 16px;
        }

        .btn-primary, .btn-danger {
            width: 70px;
        }

        .modal-content {
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
        }

        .form-select {
            min-width: 150px;
        }

        h2 {
            font-weight: bold;
            text-align: center;
            margin-bottom: 30px;
        }

        @media (max-width: 768px) {
            .page-container {
                width: 90%;
            }
            .top-bar {
                flex-direction: column;
                gap: 10px;
            }
        }
    </style>
</head>
<body>

<div class="page-container">

    <h2>Holiday Calendar</h2>

    <div class="top-bar">
        <button class="btn btn-success" onclick="openNewHolidayModal()">Add New Holiday</button>

        <div class="d-flex align-items-center">
            <label for="yearFilter" class="me-2">Filter by Year:</label>
            <select class="form-select" id="yearFilter" onchange="filterByYear()" style="width: auto;">
                <option value="">All Years</option>
                <%
                    List<Integer> yearsList = (List<Integer>) request.getAttribute("yearsList");
                    if (yearsList != null) {
                        for (Integer year : yearsList) {
                %>
                    <option value="<%= year %>"><%= year %></option>
                <% 
                        }
                    }
                %>
            </select>
        </div>
    </div>

    <table class="table table-bordered table-hover" id="holidayTable">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Date</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Holiday> list = (List<Holiday>) request.getAttribute("listHolidays");
                if (list != null) {
                    for (Holiday holiday : list) {
            %>
            <tr data-year="<%= new java.text.SimpleDateFormat("yyyy").format(holiday.getDate()) %>">
                <td><%= holiday.getId() %></td>
                <td><%= holiday.getTitle() %></td>
                <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(holiday.getDate()) %></td>
                <td><%= holiday.getDescription() %></td>
                <td>
                    <button 
                        class="btn btn-sm btn-primary me-2"
                        onclick="openEditHolidayModal(<%= holiday.getId() %>, '<%= holiday.getTitle().replace("'", "\\'") %>', '<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(holiday.getDate()) %>', '<%= holiday.getDescription().replace("'", "\\'") %>')">
                        Edit
                    </button>
                    <a href="holiday?action=delete&id=<%= holiday.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete?');">
                        Delete
                    </a>
                </td>
            </tr>
            <% 
                    }
                }
            %>
        </tbody>
    </table>

</div> <!-- End of page-container -->

<!-- Modal -->
<div class="modal fade" id="holidayModal" tabindex="-1" aria-labelledby="holidayModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content rounded-3 shadow-lg">
      <div class="modal-header">
        <h5 class="modal-title" id="holidayModalLabel">Add New Holiday</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <form id="holidayForm" method="post">
        <div class="modal-body">
            <input type="hidden" id="holidayId" name="id">
            <div class="mb-3">
                <label for="title" class="form-label">Holiday Title</label>
                <input type="text" class="form-control" id="title" name="title" required>
            </div>

            <div class="mb-3">
                <label for="date" class="form-label">Holiday Date</label>
                <input type="date" class="form-control" id="date" name="date" required>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" rows="4"></textarea>
            </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <input type="submit" class="btn btn-primary" value="Save">
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Bootstrap 5 JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function openNewHolidayModal() {
        document.getElementById('holidayModalLabel').innerText = "Add New Holiday";
        document.getElementById('holidayForm').action = "holiday?action=insert";
        document.getElementById('holidayForm').reset();
        var myModal = new bootstrap.Modal(document.getElementById('holidayModal'));
        myModal.show();
    }

    function openEditHolidayModal(id, title, date, description) {
        document.getElementById('holidayModalLabel').innerText = "Edit Holiday";
        document.getElementById('holidayForm').action = "holiday?action=update&id=" + id;
        document.getElementById('holidayId').value = id;
        document.getElementById('title').value = title;
        document.getElementById('date').value = date;
        document.getElementById('description').value = description;
        var myModal = new bootstrap.Modal(document.getElementById('holidayModal'));
        myModal.show();
    }

    function filterByYear() {
        var selectedYear = document.getElementById('yearFilter').value;
        var rows = document.querySelectorAll('#holidayTable tbody tr');

        rows.forEach(function(row) {
            var rowYear = row.getAttribute('data-year');
            if (selectedYear === "" || rowYear === selectedYear) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        });
    }
</script>

</body>
</html>
