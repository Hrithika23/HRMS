package net.javaguides.hrms.web;



import net.javaguides.hrms.database.HolidayDao;
import net.javaguides.hrms.bean.Holiday;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@WebServlet("/holiday")
public class HolidayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private HolidayDao holidayDao;

    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrms", "root", "Hrithi@12345");
            holidayDao = new HolidayDao(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                listHolidays(request, response);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "insert":
                        insertHoliday(request, response);
                        break;
                    case "delete":
                        deleteHoliday(request, response);
                        break;
                    case "update":
                        updateHoliday(request, response);
                        break;
                    default:
                        listHolidays(request, response);
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }
    
    private void listHolidays(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        request.setAttribute("listHolidays", holidayDao.selectAllHolidays());
        request.setAttribute("yearsList", holidayDao.getAllYears()); // <-- Add this line
        RequestDispatcher dispatcher = request.getRequestDispatcher("holiday-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("add-holiday.jsp");
        dispatcher.forward(request, response);
    }

    private void insertHoliday(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            String title = request.getParameter("title");
            String dateStr = request.getParameter("date");
            String description = request.getParameter("description");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(dateStr);

            Holiday newHoliday = new Holiday();
            newHoliday.setTitle(title);
            newHoliday.setDate(parsedDate);
            newHoliday.setDescription(description);

            holidayDao.insertHoliday(newHoliday);
            response.sendRedirect("holiday");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error inserting holiday.");
        }
    }

    private void deleteHoliday(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        holidayDao.deleteHoliday(id);
        response.sendRedirect("holiday");
    }

    // NEW! --> Update Holiday method
    private void updateHoliday(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String dateStr = request.getParameter("date");
            String description = request.getParameter("description");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(dateStr);

            Holiday holiday = new Holiday();
            holiday.setId(id);
            holiday.setTitle(title);
            holiday.setDate(parsedDate);
            holiday.setDescription(description);

            holidayDao.updateHoliday(holiday);
            response.sendRedirect("holiday");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error updating holiday.");
        }
    }
}
