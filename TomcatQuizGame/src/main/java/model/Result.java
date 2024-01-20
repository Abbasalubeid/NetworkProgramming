package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "score")
    private int score;

    public Result(int userId, int quizId, int score) {
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
    }


}
