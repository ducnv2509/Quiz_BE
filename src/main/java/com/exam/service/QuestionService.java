package com.exam.service;

import com.exam.helper.UserFoundException;
import com.exam.model.exam.Category;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface QuestionService {

    public Question addQuestion(Question question, Long id, Long totalObject) throws UserFoundException;

    //    public void insertExcel();
    public void doSomethingAfterStartup(Long id, Long total) throws IOException, UserFoundException;

    public void checkNumberOfQuestion(Long id, Long total) throws IOException, UserFoundException;
    public Long countByIdQuiz(Long id);

    public Question updateQuestion(Question question);

    public Set<Question> getQuestions();

    public Question getQuestion(Long questionId);

    public Set<Question> getQuestionsOfQuiz(Quiz quiz);

    public void deleteQuestion(Long quesId);

    public Question get(Long questionsId);

}
