package se.kth.SpringQuizGame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.SpringQuizGame.model.Question;
import se.kth.SpringQuizGame.repository.QuestionRepository;

import java.util.*;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestionsForQuiz(Long quizId) {
        return questionRepository.findQuestionsByQuizId(quizId);
    }

    public int calculateScore(Long quizId, Map<String, String> userAnswers) {
        List<Question> questions = getQuestionsForQuiz(quizId);

        // System.out.println();
        
        // System.out.println("Questions");

        // for (Question question : questions) {
        //     System.out.println(question.getId());
        //     System.out.println(question.getText());
        //     System.out.println(question.getOptions());
        //     System.out.println(question.getAnswer());

        //     System.out.println();
        // }
        
        // System.out.println();
        // System.out.println("User answers: " + userAnswers);

        int score = 0;
        for (Question question : questions) {
            String[] questionAnswers = question.getAnswer().split("/");

            // System.out.println("questionAnswers "+ Arrays.toString(questionAnswers));

            String userAnswerIndex = userAnswers.get("question_" + question.getId());
            if (userAnswerIndex != null && questionAnswers[Integer.parseInt(userAnswerIndex)].equals("1")) {
                score++;
            }
        }
        return score;
    }
}
