<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz</title>
</head>
<body>
    <h1>Quiz</h1>
    <form action="quiz" method="post">
        <input type="hidden" name="quizId" value="${quizId}" />
        <c:forEach items="${questions}" var="question">
            <p>${question.text}</p>
            <c:forEach items="${question.options}" var="option" varStatus="status">
                <input type="radio" name="question_${question.id}" value="${status.index}"> ${option}<br>
            </c:forEach>
        </c:forEach>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
