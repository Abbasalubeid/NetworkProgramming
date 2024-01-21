package se.kth.SpringQuizGame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.kth.SpringQuizGame.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        // Returns the name of the JSP file to show the login form.
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        
        if (userService.validateUser(username, password)) {
            // Store user ID in session
            Long userId = userService.getUserId(username);
            session.setAttribute("userId", userId);
            return new ModelAndView("redirect:/quizzes");
        } else {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", true);
            return modelAndView;
        }
    }
}
