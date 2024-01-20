package dao;

import model.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDao {
    
    // Fetches all questions for a specific quiz
    public List<Question> getQuestionsForQuiz(int quizId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT q.* FROM questions q INNER JOIN selector s ON q.id = s.question_id WHERE s.quiz_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, quizId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("text");
                String options = rs.getString("options");
                String answer = rs.getString("answer");
                
                // Split the options by '/' and convert to a List
                List<String> optionsList = Arrays.asList(options.split("/"));
                
                // Create a new Question object and add it to the list
                Question question = new Question(id, text, optionsList, answer);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

}
