<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Salary Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 30px auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #333;
        }

        form {
            display: grid;
            grid-template-columns: 1fr; /* Set to one column layout */
            gap: 20px;
            max-width: 1000px;
            margin: auto;
        }

        label {
            font-weight: bold;
            color: #555;
        }

        input[type="text"], input[type="number"], select {
            padding: 8px;
            font-size: 16px;
            width: 100%;
            margin-bottom: 15px;
            border-radius: 4px;
            border: 1px solid #ddd;
        }

        input[readonly] {
            background-color: #f0f0f0;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }

        button:hover {
            background-color: #45a049;
        }

        .form-actions {
            grid-column: span 2;
            text-align: center;
        }

        .form-actions button {
            margin: 10px 15px;
        }

        .modify-btn {
            background-color: #f44336;
        }

        .modify-btn:hover {
            background-color: #d32f2f;
        }
    </style>
    <script>
        function calculateSalary() {
            const salary = parseFloat(document.getElementById("salary").value) || 0;
            const paidDays = parseFloat(document.getElementById("paidDays").value) || 0;
            const pfSal = parseFloat(document.getElementById("pfSal").value) || 0;
            const esiSal = parseFloat(document.getElementById("esiSal").value) || 0;
            const pt = parseFloat(document.getElementById("pt").value) || 0;

            const grossSalary = Math.ceil((salary * paidDays) / 31.0);
            const pfEe = Math.ceil((pfSal * 12) / 100.0);
            const pfEr = Math.round((pfSal * 12) / 100.0);
            const esicEe = Math.ceil((esiSal * 0.75) / 100.0);
            const esicEr = Math.ceil((esiSal * 3.25) / 100.0);
            const deduction = pfEe + pfEr + esicEe + esicEr + pt;
            const netSalary = grossSalary - deduction;

            document.getElementById("grossSalary").value = grossSalary;
            document.getElementById("pfEe").value = pfEe;
            document.getElementById("pfEr").value = pfEr;
            document.getElementById("esicEe").value = esicEe;
            document.getElementById("esicEr").value = esicEr;
            document.getElementById("deduction").value = deduction;
            document.getElementById("netSalary").value = netSalary;
        }

        function setStatusAndSubmit(statusValue) {
            document.getElementById("status").value = statusValue;
            document.getElementById("salaryForm").submit();
        }

        function confirmAndDelete() {
            const empCode = prompt("Enter the Employee Code to delete the salary record:");
            if (empCode !== null && empCode.trim() !== "") {
                const form = document.createElement("form");
                form.method = "post";
                form.action = "DeleteSalaryServlet";

                const input = document.createElement("input");
                input.type = "hidden";
                input.name = "empCode";
                input.value = empCode.trim();

                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            } else {
                alert("Employee Code is required for deletion.");
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Salary Form</h2>
        <form id="salaryForm" action="SubmitSalaryServlet" method="post">
            <div>
                <label>Employee Code:</label>
                <input type="text" name="empCode" required value="${salary.empCode != null ? salary.empCode : ''}"><br>
            </div>
            <div>
                <label>Month:</label>
                <select name="month" required>
                    <option value="">Select Month</option>
                    <option value="January" ${salary.month == 'January' ? 'selected' : ''}>January</option>
                    <option value="February" ${salary.month == 'February' ? 'selected' : ''}>February</option>
                    <option value="March" ${salary.month == 'March' ? 'selected' : ''}>March</option>
                    <option value="April" ${salary.month == 'April' ? 'selected' : ''}>April</option>
                    <option value="May" ${salary.month == 'May' ? 'selected' : ''}>May</option>
                    <option value="June" ${salary.month == 'June' ? 'selected' : ''}>June</option>
                    <option value="July" ${salary.month == 'July' ? 'selected' : ''}>July</option>
                    <option value="August" ${salary.month == 'August' ? 'selected' : ''}>August</option>
                    <option value="September" ${salary.month == 'September' ? 'selected' : ''}>September</option>
                    <option value="October" ${salary.month == 'October' ? 'selected' : ''}>October</option>
                    <option value="November" ${salary.month == 'November' ? 'selected' : ''}>November</option>
                    <option value="December" ${salary.month == 'December' ? 'selected' : ''}>December</option>
                </select><br>
            </div>
            <div>
                <label>Year:</label>
                <input type="text" name="year" required value="${salary.year != null ? salary.year : ''}"><br>
            </div>
            <div>
                <label>Salary:</label>
                <input type="number" step="any" name="salary" id="salary" required value="${salary.salary}"><br>
            </div>
            <div>
                <label>Paid Days:</label>
                <input type="number" step="any" name="paidDays" id="paidDays" required value="${salary.paidDays}"><br>
            </div>
            <div>
                <label>PF Salary:</label>
                <input type="number" step="any" name="pfSal" id="pfSal" required value="${salary.pfSal}"><br>
            </div>
            <div>
                <label>ESI Salary:</label>
                <input type="number" step="any" name="esiSal" id="esiSal" required value="${salary.esiSal}"><br>
            </div>
            <div>
                <label>Professional Tax (PT):</label>
                <input type="number" step="any" name="pt" id="pt" required value="${salary.pt}"><br>
            </div>
            <div>
                <label>Gross Salary:</label>
                <input type="number" step="any" name="grossSalary" id="grossSalary" readonly value="${salary.grossSalary}"><br>
            </div>
            <div>
                <label>PF EE (12%):</label>
                <input type="number" step="any" name="pfEe" id="pfEe" readonly value="${salary.pfEe}"><br>
            </div>
            <div>
                <label>PF ER (12%):</label>
                <input type="number" step="any" name="pfEr" id="pfEr" readonly value="${salary.pfEr}"><br>
            </div>
            <div>
                <label>ESIC EE (0.75%):</label>
                <input type="number" step="any" name="esicEe" id="esicEe" readonly value="${salary.esicEe}"><br>
            </div>
            <div>
                <label>ESIC ER (3.25%):</label>
                <input type="number" step="any" name="esicEr" id="esicEr" readonly value="${salary.esicEr}"><br>
            </div>
            <div>
                <label>Total Deduction:</label>
                <input type="number" step="any" name="deduction" id="deduction" readonly value="${salary.deduction}"><br>
            </div>
            <div>
                <label>Net Salary:</label>
                <input type="number" step="any" name="netSalary" id="netSalary" readonly value="${salary.netSalary}"><br>
            </div>
            <input type="hidden" name="status" id="status" value="${salary.status}">
            <div class="form-actions">
                <button type="button" onclick="calculateSalary()">Calculate</button>
                <button type="button" onclick="setStatusAndSubmit('Approved')">Approve</button>
            </div>
        </form>
    </div>
</body>
</html>
