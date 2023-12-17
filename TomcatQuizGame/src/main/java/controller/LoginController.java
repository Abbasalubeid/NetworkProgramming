package controller;

import dao.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class LoginController extends HttpServlet {

    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

    // Display the login form for GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    // Process login attempt for POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userDao.validateUser(username, password)) {
            // Fetch user ID
            int userId = userDao.getUserIdByUsername(username);

            // Store user ID in session
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);

            response.sendRedirect("quizzes");
        } else {
            // authentication failed
            response.sendRedirect("login?error=true");
        }
    }
}
