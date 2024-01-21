package se.kth.SpringQuizGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.SpringQuizGame.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // The JpaRepository interface already includes standard methods like findAll(), findById(), save(), etc.
}
