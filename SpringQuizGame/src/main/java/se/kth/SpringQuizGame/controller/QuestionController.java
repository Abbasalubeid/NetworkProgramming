package se.kth.SpringQuizGame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import se.kth.SpringQuizGame.service.QuestionService;
import se.kth.SpringQuizGame.service.ResultService;
import se.kth.SpringQuizGame.model.Question;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

@Controller
public class QuestionController {

    @Autowired
    private ResultService resultService;
    
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/quiz")
    public String showQuestions(@RequestParam Long quizId, Model model) {
        List<Question> questions = questionService.getQuestionsForQuiz(quizId);
        model.addAttribute("questions", questions);
        model.addAttribute("quizId", quizId);
        return "quiz";
    }

    @PostMapping("/quiz")
    public String submitQuiz(@RequestParam Long quizId, @RequestParam Map<String, String> allParams, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        int score = questionService.calculateScore(quizId, allParams);
        resultService.insertResult(userId, quizId, score);
        return "redirect:/quizzes";
    }
}