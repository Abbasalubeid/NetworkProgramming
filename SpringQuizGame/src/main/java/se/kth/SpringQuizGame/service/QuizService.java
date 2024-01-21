package se.kth.SpringQuizGame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.SpringQuizGame.model.Quiz;
import se.kth.SpringQuizGame.repository.QuizRepository;
import java.util.*;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz getQuizById(Long quizId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        return quiz.orElse(null);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

}