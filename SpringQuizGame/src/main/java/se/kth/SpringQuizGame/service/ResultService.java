package se.kth.SpringQuizGame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.SpringQuizGame.model.Result;
import se.kth.SpringQuizGame.dto.QuizResultDTO;
import se.kth.SpringQuizGame.repository.ResultRepository;
import se.kth.SpringQuizGame.repository.UserRepository;

import java.util.List;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    public void insertResult(Long userId, Long quizId, int score) {
        Result result = new Result(userId, quizId, score);
        resultRepository.save(result);

        try {
            String userEmail = userRepository.findById(userId).map(user -> user.getUsername()).orElse(null);
            if (userEmail != null) {
                String subject = "Your Quiz Results";
                String text = "You scored " + score;
                emailService.sendResultEmail(userEmail, subject, text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<QuizResultDTO> getQuizResultsWithSubjectByUserId(Long userId) {
        return resultRepository.findResultsWithQuizSubjectByUserId(userId);
    }
}
