 package net.javaguides.hrms.web;
  
  
  import net.javaguides.hrms.database.StaffDAO;
 import net.javaguides.hrms.bean.Staff;
  
  import jakarta.servlet.ServletException; 
  import jakarta.servlet.annotation.WebServlet;
  import jakarta.servlet.http.*; 
  import net.javaguides.hrms.util.DBConnection;
  import net.javaguides.hrms.util.EmailUtil; 
  import net.javaguides.hrms.util.PasswordUtils; 
  import java.io.IOException;
  import java.security.SecureRandom;
@WebServlet("/GeneratePasswordServlet")
public class GeneratePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empCode = request.getParameter("emp_code");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            StaffDAO staffDAO = new StaffDAO(DBConnection.getConnection());

            // Check if employee already exists
            Staff existing = staffDAO.getStaffByEmpCode(empCode);
            if (existing != null) {
                request.setAttribute("message", "Employee already exists with this code.");
                request.getRequestDispatcher("/reset_password.jsp").forward(request, response);
                return;
            }

            // Hash the password before saving
            String hashedPassword = PasswordUtils.hashPassword(password);

            // Save to database
            boolean isTempPassword = true;
            Staff staff = new Staff(empCode, name, email, hashedPassword, isTempPassword);

            staffDAO.addStaff(staff); // Create this method in DAO

            // Send email
            EmailUtil.sendEmail(
                email,
                "Your Account Details",
                "Hi " + name + ",\n\nYour account has been created.\nEmployee Code: " + empCode +
                "\nPassword: " + password +
                "\n\nPlease log in and change your password after first login."
            );

            request.setAttribute("message", "Employee added and password sent to " + email);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while processing the request.");
        }

        request.getRequestDispatcher("/reset_password.jsp").forward(request, response);
    }
}

/*
 * package net.javaguides.hrms.web;
 * 
 * 
 * import net.javaguides.hrms.database.StaffDAO; import
 * net.javaguides.hrms.bean.Staff;
 * 
 * import jakarta.servlet.ServletException; import
 * jakarta.servlet.annotation.WebServlet; import jakarta.servlet.http.*; import
 * net.javaguides.hrms.util.DBConnection; import
 * net.javaguides.hrms.util.EmailUtil; import
 * net.javaguides.hrms.util.PasswordUtils; import java.io.IOException; import
 * java.security.SecureRandom;
 * 
 * @WebServlet("/GeneratePasswordServlet") public class GeneratePasswordServlet
 * extends HttpServlet { private static final String CHARACTERS =
 * "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 * 
 * private String generateRandomPassword(int length) { SecureRandom random = new
 * SecureRandom(); StringBuilder sb = new StringBuilder(length); for (int i = 0;
 * i < length; i++) {
 * sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length()))); } return
 * sb.toString(); }
 * 
 * @Override protected void doPost(HttpServletRequest request,
 * HttpServletResponse response) throws ServletException, IOException { String
 * emp_code = request.getParameter("emp_code");
 * 
 * try { StaffDAO staffDAO = new StaffDAO(DBConnection.getConnection()); Staff
 * staff = staffDAO.getStaffByEmpCode(emp_code);
 * 
 * if (staff != null) { String tempPassword = generateRandomPassword(10); String
 * hashedPassword = PasswordUtils.hashPassword(tempPassword);
 * staffDAO.savePassword(emp_code, hashedPassword); // includes temp flag
 * 
 * EmailUtil.sendEmail( staff.getEmail(), "Your Temporary Password", "Hi " +
 * staff.getName() + ",\n\nYour temporary password is: " + tempPassword +
 * "\nPlease log in and reset it as soon as possible." );
 * 
 * request.setAttribute("message", "Temporary password sent to " +
 * staff.getEmail()); } else { request.setAttribute("message",
 * "Employee not found with the provided code."); }
 * 
 * } catch (Exception e) { e.printStackTrace(); request.setAttribute("message",
 * "An error occurred while generating the password."); }
 * 
 * request.getRequestDispatcher("/reset_password.jsp").forward(request,
 * response); } }
 */