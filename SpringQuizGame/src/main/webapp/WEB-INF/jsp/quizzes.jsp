<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <title>Quizzes</title>
</head>
<body>
    <h1>Available Quizzes:</h1>
    <c:forEach items="${quizzes}" var="quiz">
        <a href="quiz?quizId=${quiz.id}">${quiz.subject}</a><br>
    </c:forEach>

    <hr/>

    <h2>Attempted Quizzes:</h2>
    <c:forEach items="${attemptedQuizzes}" var="attemptedQuiz">
        <p>Subject: ${attemptedQuiz.subject}, Score: ${attemptedQuiz.score}</p>
    </c:forEach>
</body>
</html>
