package dao;

import model.Quiz;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Result;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class QuizDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("quizPersistenceUnit");
    
    public void insertResult(int userId, int quizId, int score) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            Result result = new Result(userId, quizId, score);
            
            em.persist(result);
            
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    public void close() {
        emf.close();
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Quiz quiz = new Quiz(rs.getInt("id"), rs.getString("subject"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public List<Quiz> getAttemptedQuizzes(int userId) {
        List<Quiz> attemptedQuizzes = new ArrayList<>();
        String sql = "SELECT q.id, q.subject, r.score FROM results r JOIN quizzes q ON r.quiz_id = q.id WHERE r.user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String subject = rs.getString("subject");
                    int score = rs.getInt("score");
                    Quiz quiz = new Quiz(id, subject);
                    quiz.setLastScore(score); 
                    attemptedQuizzes.add(quiz);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attemptedQuizzes;
    }

/*     public void insertResult(int userId, int quizId, int score) throws SQLException {
        String sql = "INSERT INTO results (user_id, quiz_id, score) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, quizId);
            pstmt.setInt(3, score);
            pstmt.executeUpdate();
        }
    } */

}
