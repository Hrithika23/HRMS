<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            display: flex;
            height: 100vh;
        }

        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: white;
            display: flex;
            flex-direction: column;
            padding-top: 20px;
        }

        .sidebar h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        .sidebar a,
        .dropdown-toggle {
            padding: 15px 20px;
            text-decoration: none;
            color: white;
            display: flex;
            align-items: center;
            transition: 0.3s;
            cursor: pointer;
        }

        .sidebar a:hover,
        .dropdown-toggle:hover {
            background-color: #1abc9c;
        }

        .sidebar a.active,
        .dropdown-toggle.active {
            background-color: #1abc9c;
            font-weight: bold;
        }

        .sidebar i {
            margin-right: 12px;
            width: 20px;
            text-align: center;
        }

        .dropdown {
            display: flex;
            flex-direction: column;
        }

        .dropdown-content {
            display: none;
            flex-direction: column;
            padding-left: 40px;
            background-color: #34495e;
        }

        .dropdown-content a {
            padding: 10px 0;
            text-decoration: none;
            color: #ecf0f1;
            font-size: 14px;
            display: flex;
            align-items: center;
        }

        .dropdown-content i {
            margin-right: 10px;
        }

        .dropdown-content a:hover {
            text-decoration: underline;
        }

        .arrow-icon {
            margin-left: auto;
            transition: transform 0.3s ease;
        }

        .rotate {
            transform: rotate(180deg);
        }

        .content {
            flex-grow: 1;
            background-color: #ecf0f1;
            position: relative;
        }

        .welcome-box {
            background-image: url('https://images.unsplash.com/photo-1504384308090-c894fdcc538d');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 40px;
            border-radius: 10px;
            text-align: center;
            margin: 40px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            height: 80vh;
            position: relative;
        }

        .welcome-box::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(255, 255, 255, 0.3); /* Light overlay */
            z-index: 0;
        }

        .welcome-box h3, .welcome-box p {
            position: relative;
            z-index: 1;
        }

        .welcome-box h3 {
            margin: 0;
            font-size: 50px;
            text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.6);
        }

        .welcome-box p {
            font-size: 20px;
            margin-top: 12px;
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.5);
        }

        iframe {
            width: 100%;
            height: calc(100vh - 40px);
            border: none;
            display: none;
        }
    </style>
</head>
<body>

<div class="sidebar">
    <h2>Admin Panel</h2>

    <a href="#" id="dashboardLink" class="active" onclick="loadContent('dashboard', this)">
        <i class="fas fa-tachometer-alt"></i>
        <span>Dashboard</span>
    </a>

    <div class="dropdown">
        <div class="dropdown-toggle" onclick="toggleDropdown('companyDropdown')">
            <i class="fas fa-building"></i>
            <span>Company</span>
            <i class="fas fa-chevron-down arrow-icon" id="companyDropdownArrow"></i>
        </div>
        <div id="companyDropdown" class="dropdown-content">
            <a href="https://abhimo.com/"><i class="fas fa-info-circle"></i>About</a>
            <a href="location.jsp"><i class="fas fa-map-marker-alt"></i>Location</a>
        </div>
    </div>

    <a href="#" onclick="loadContent('employee-form.jsp', this)">
        <i class="fas fa-user-plus"></i>
        <span>Add Employee</span>
    </a>

    <a href="#" onclick="loadContent('reset_password.jsp', this)">
        <i class="fas fa-key"></i>
        <span>Reset Password</span>
    </a>
    <a href="#" onclick="loadContent('admin_leave_approval.jsp', this)">
    <i class="fas fa-check-circle"></i>
    <span>Leave Approval</span>
</a>
     <!-- Employee Master -->
    <a href="#" onclick="loadContent('leave_master.jsp', this)">
        <i class="fas fa-id-card"></i>
        <span>Leave Master</span>
    </a>
    <a href="#" onclick="loadContent('holiday-list.jsp', this)">
    <i class="fas fa-calendar-day"></i> <!-- Icon for Holiday Calendar -->
    <span>Holiday Calendar</span>
</a>
    
    <a href="#" onclick="loadContent('salary_form.jsp', this)">
    <i class="fas fa-money-bill-wave"></i> <!-- Icon for Salary -->
    <span>Salary</span>
</a>
</div>

<div class="content">
    <div class="welcome-box" id="welcomeBox">
        <h3>Welcome to Abhimo Technologies</h3>
        <p>Manage your company efficiently with our HRMS system.</p>
    </div>
    <iframe id="contentFrame"></iframe>
</div>

<script>
    function toggleDropdown(id) {
        const dropdown = document.getElementById(id);
        const arrow = document.getElementById(id + "Arrow");
        dropdown.style.display = dropdown.style.display === "flex" ? "none" : "flex";
        arrow.classList.toggle("rotate");
    }

    function loadContent(page, element) {
        // Highlight active item
        const links = document.querySelectorAll('.sidebar a, .dropdown-toggle');
        links.forEach(link => link.classList.remove('active'));
        element.classList.add('active');

        const welcomeBox = document.getElementById('welcomeBox');
        const iframe = document.getElementById('contentFrame');

        if (page === 'dashboard') {
            welcomeBox.style.display = 'block';
            iframe.style.display = 'none';
        } else {
            welcomeBox.style.display = 'none';
            iframe.src = page;
            iframe.style.display = 'block';
        }
    }
</script>

</body>
</html>
