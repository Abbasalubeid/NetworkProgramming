package se.kth.SpringQuizGame.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String text;

    @Column(nullable = false, length = 255)
    private String options;

    @Column(nullable = false, length = 64)
    private String answer;

    @ManyToMany(mappedBy = "questions")
    private Set<Quiz> quizzes = new HashSet<>();

    public Question() {
    }

    public Question(String text, String options, String answer) {
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
