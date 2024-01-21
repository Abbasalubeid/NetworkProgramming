package se.kth.SpringQuizGame.dto;

public class QuizResultDTO {
    private Long quizId;
    private String subject;
    private int score;

    public QuizResultDTO(Long quizId, String subject, int score) {
        this.quizId = quizId;
        this.subject = subject;
        this.score = score;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
