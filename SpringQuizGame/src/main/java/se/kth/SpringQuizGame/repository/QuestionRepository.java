package se.kth.SpringQuizGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.kth.SpringQuizGame.model.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    @Query("SELECT q FROM Question q JOIN q.quizzes quiz WHERE quiz.id = :quizId")
    List<Question> findQuestionsByQuizId(Long quizId);
}
