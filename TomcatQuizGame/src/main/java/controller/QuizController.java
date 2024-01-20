package controller;

import dao.QuizDao;
import model.Quiz;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class QuizController extends HttpServlet {

    private QuizDao quizDao;

    public void init() {
        quizDao = new QuizDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Quiz> quizzes = quizDao.getAllQuizzes();
        int userId = getLoggedInUserId(request);
        List<Quiz> attemptedQuizzes = quizDao.getAttemptedQuizzes(userId);

        request.setAttribute("quizzes", quizzes);
        request.setAttribute("attemptedQuizzes", attemptedQuizzes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("quizzes.jsp");
        dispatcher.forward(request, response);
    }

    private int getLoggedInUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null && session.getAttribute("userId") != null) ? (Integer) session.getAttribute("userId") : -1;
    }
}
