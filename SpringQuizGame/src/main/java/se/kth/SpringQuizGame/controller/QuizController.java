package se.kth.SpringQuizGame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.kth.SpringQuizGame.service.QuizService;
import se.kth.SpringQuizGame.service.ResultService;
import se.kth.SpringQuizGame.model.Quiz;
import se.kth.SpringQuizGame.model.Result;
import se.kth.SpringQuizGame.dto.QuizResultDTO;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class QuizController {

    private final QuizService quizService;
    private final ResultService resultService;

    public QuizController(QuizService quizService, ResultService resultService) {
        this.quizService = quizService;
        this.resultService = resultService;
    }

    @GetMapping("/quizzes")
    public String showQuizzes(HttpSession session, Model model) {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        model.addAttribute("quizzes", quizzes);

        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            List<QuizResultDTO> attemptedQuizzes = resultService.getQuizResultsWithSubjectByUserId(userId);
            model.addAttribute("attemptedQuizzes", attemptedQuizzes);
        }

        return "quizzes";
    }
}
