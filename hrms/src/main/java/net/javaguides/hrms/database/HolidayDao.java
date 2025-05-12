package net.javaguides.hrms.database;

import net.javaguides.hrms.bean.Holiday;
import net.javaguides.hrms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HolidayDao {
    private Connection conn;

    // ✅ Constructor 1: Injected Connection
    public HolidayDao(Connection conn) {
        this.conn = conn;
    }

    // ✅ Constructor 2: No-arg constructor using utility class
    public HolidayDao() throws SQLException {
        this.conn = DBConnection.getConnection(); // adjust if your method name is different
    }

    // Insert a new holiday
    public void insertHoliday(Holiday holiday) throws SQLException {
        String sql = "INSERT INTO holidays (title, date, description) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, holiday.getTitle());
        statement.setDate(2, new java.sql.Date(holiday.getDate().getTime()));
        statement.setString(3, holiday.getDescription());
        statement.executeUpdate();
    }

    // Select all holidays
    public List<Holiday> selectAllHolidays() throws SQLException {
        List<Holiday> holidays = new ArrayList<>();
        String sql = "SELECT * FROM holidays ORDER BY date ASC";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            Date date = resultSet.getDate("date");
            String description = resultSet.getString("description");

            holidays.add(new Holiday(id, title, date, description));
        }
        return holidays;
    }

    // Select holidays for a specific year
    public List<Holiday> selectHolidaysByYear(int year) throws SQLException {
        List<Holiday> holidays = new ArrayList<>();
        String sql = "SELECT * FROM holidays WHERE YEAR(date) = ? ORDER BY date ASC";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, year);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            Date date = resultSet.getDate("date");
            String description = resultSet.getString("description");

            holidays.add(new Holiday(id, title, date, description));
        }
        return holidays;
    }

    // Delete holiday by ID
    public boolean deleteHoliday(int id) throws SQLException {
        String sql = "DELETE FROM holidays WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate() > 0;
    }

    // Update an existing holiday
    public boolean updateHoliday(Holiday holiday) throws SQLException {
        String sql = "UPDATE holidays SET title = ?, date = ?, description = ? WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, holiday.getTitle());
        statement.setDate(2, new java.sql.Date(holiday.getDate().getTime()));
        statement.setString(3, holiday.getDescription());
        statement.setInt(4, holiday.getId());

        return statement.executeUpdate() > 0;
    }

    // Fetch distinct years from holidays table
    public List<Integer> getAllYears() throws SQLException {
        List<Integer> years = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(date) as year FROM holidays ORDER BY year DESC";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            years.add(rs.getInt("year"));
        }
        return years;
    }
}
