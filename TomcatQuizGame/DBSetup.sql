-- Drop the tables if they exist
DROP TABLE IF EXISTS results CASCADE;
DROP TABLE IF EXISTS selector CASCADE;
DROP TABLE IF EXISTS questions CASCADE;
DROP TABLE IF EXISTS quizzes CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Create the users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE NOT NULL,
    password VARCHAR(32) NOT NULL
);

-- Sample users
INSERT INTO users (username, password) VALUES ('ada@kth.se', 'qwerty');
INSERT INTO users (username, password) VALUES ('beda@kth.se', 'asdfgh');

-- Create the questions table
CREATE TABLE questions (
    id SERIAL PRIMARY KEY,
    text VARCHAR(255) NOT NULL,
    options VARCHAR(255) NOT NULL,
    answer VARCHAR(64) NOT NULL
);

-- Sample questions with answers formatted as '0' for f alse and '1' for true
INSERT INTO questions (text, options, answer) VALUES ('When was the Ancient Greek period?', '1200-800 BC/800-500 BC/500-323 BC', '0/1/0');
INSERT INTO questions (text, options, answer) VALUES ('Which period is known as the Middle Ages?', '476-1000 AD/1000-1300 AD/1300-1600 AD', '1/0/0');
INSERT INTO questions (text, options, answer) VALUES ('When did the Renaissance period occur?', '1300-1600 AD/1600-1750 AD/1750-1850 AD', '1/0/0');

-- Create the quizzes table
CREATE TABLE quizzes (
    id SERIAL PRIMARY KEY,
    subject VARCHAR(64) NOT NULL
);

-- Sample quiz
INSERT INTO quizzes (subject) VALUES ('History Timeline');

-- Create the selector table
-- This table does not need its own primary key as it's a linking table between quizzes and questions
CREATE TABLE selector(
    quiz_id INT NOT NULL REFERENCES quizzes(id),
    question_id INT NOT NULL REFERENCES questions(id)
);

-- Associating questions with the quiz
INSERT INTO selector (quiz_id, question_id) VALUES (1, 1);
INSERT INTO selector (quiz_id, question_id) VALUES (1, 2);
INSERT INTO selector (quiz_id, question_id) VALUES (1, 3);

-- Create the results table
CREATE TABLE results(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id),
    quiz_id INT NOT NULL REFERENCES quizzes(id),
    score INT NOT NULL
);

-- Sample result
INSERT INTO results (user_id, quiz_id, score) VALUES (1, 1, 0); 
