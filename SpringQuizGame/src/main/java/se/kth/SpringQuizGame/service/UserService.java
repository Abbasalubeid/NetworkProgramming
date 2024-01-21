package se.kth.SpringQuizGame.service;

import se.kth.SpringQuizGame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.SpringQuizGame.model.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user != null;
    }

    public Long getUserId(String username) {
        User user = userRepository.findByUsername(username);
        return (user != null) ? user.getId() : null;
    }
}
