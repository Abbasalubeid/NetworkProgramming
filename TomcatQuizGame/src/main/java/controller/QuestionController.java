package controller;

import dao.QuestionDao;
import dao.QuizDao;
import model.Question;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionController extends HttpServlet {

    private QuestionDao questionDao;
    private QuizDao quizDao;

    public void init() {
        questionDao = new QuestionDao();
        quizDao = new QuizDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizId = request.getParameter("quizId");
        if (quizId != null) {
            List<Question> questions = questionDao.getQuestionsForQuiz(Integer.parseInt(quizId));
            request.setAttribute("questions", questions);
            request.setAttribute("quizId", quizId);
            RequestDispatcher dispatcher = request.getRequestDispatcher("quiz.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("quizzes.jsp?error=NoQuizSelected");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int userId = getLoggedInUserId(request);
        List<Question> questions = questionDao.getQuestionsForQuiz(quizId);
        int score = 0;
    
        for (Question question : questions) {
            // Retrieve submitted answers as a list of strings
            String[] submittedAnswersArray = request.getParameterValues("question_" + question.getId());

            // submittedAnswersList is null when no checkbox is selected
            List<String> submittedAnswersList = submittedAnswersArray != null ? Arrays.asList(submittedAnswersArray) : new ArrayList<>();

    
            // Correct answers from the database are in the form of '0/1/0', where '1' indicates a correct option
            String[] answersFromDB = question.getAnswer().split("/");
            
            // Check each option to see if it was correctly answered
            for (int i = 0; i < answersFromDB.length; i++) {
                if (answersFromDB[i].equals("1")) { 
                    // Check if this option was submitted by the user
                    if (submittedAnswersList.contains(String.valueOf(i))) {
                        score++;
                    }
                }
            }
        }
    
        // Insert score into the database
        try {
            quizDao.insertResult(userId, quizId, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Lastly, redirect to the quizzes overview page
        response.sendRedirect("quizzes");
    }

    private int getLoggedInUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null && session.getAttribute("userId") != null) ? (Integer) session.getAttribute("userId") : -1;
    }
}
