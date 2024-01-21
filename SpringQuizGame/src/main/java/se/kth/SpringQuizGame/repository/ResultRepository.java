package se.kth.SpringQuizGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.kth.SpringQuizGame.dto.QuizResultDTO;
import se.kth.SpringQuizGame.model.Result;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    
    @Query("SELECT new se.kth.SpringQuizGame.dto.QuizResultDTO(r.quizId, q.subject, r.score) " +
           "FROM Result r JOIN Quiz q ON r.quizId = q.id " +
           "WHERE r.userId = :userId")
    List<QuizResultDTO> findResultsWithQuizSubjectByUserId(Long userId);
}
