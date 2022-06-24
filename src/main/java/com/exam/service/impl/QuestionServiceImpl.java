package com.exam.service.impl;

import com.exam.helper.UserFoundException;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.repository.QuestionRepository;
import com.exam.service.QuestionService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question addQuestion(Question question, Long id, Long totalObject) throws UserFoundException {
        if ( countByIdQuiz(id) >= totalObject ) {
            System.out.println("Bug");
            throw new UserFoundException("Qua BUGGGGGG");
        }
        return this.questionRepository.save(question);
    }
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;
    @Override
    public void doSomethingAfterStartup(Long id, Long totalObject) throws IOException, UserFoundException {
        if ( countByIdQuiz(id) >= totalObject ) {
            System.out.println("Bug");
            throw new UserFoundException("Qua BUGGGGGG");
        }else {
            long totalObjects =  (totalObject + 1) - countByIdQuiz(id)  ;
            System.out.println("hahahahaa" + totalObjects);
            long start = System.currentTimeMillis();
            Quiz q = new Quiz();
            q.setQId(id);
            List<Question> questions = IntStream.range(0, (int) totalObjects)
                    .mapToObj(val -> Question.builder()
                            .quesId((long) val)
                            .answer("" + (val + Integer.parseInt("1")))
                            .content(val + " + 1= ?")
                            .option1(1 + val + "").option2(1 + val + 1 + "").option3(1 + val + 1 + 2 + "").option4(1 + val + 1 + 2 + 3 + "").quiz(q).build())
                    .collect(Collectors.toList());
            for (int i = 0; i < totalObjects; i += batchSize) {
                if (i + batchSize > totalObjects) {
                    List<Question> questionList = questions.subList(i, (int) (totalObjects - 1));
                    questionRepository.saveAll(questionList);
                    break;
                }
                List<Question> questions1 = questions.subList(i, i + batchSize);
                questionRepository.saveAll(questions1);
            }
        }

    }

    @Override
    public void checkNumberOfQuestion(Long id, Long total) throws IOException, UserFoundException {
        if(countByIdQuiz(id) < total){
            System.out.println("áhdasjdashjdasjhasdasdasdkj: " + (countByIdQuiz(id) - total + 1));
            throw new UserFoundException("Qua Question");
        }
    }

    @Override
    public Long countByIdQuiz(Long id) {
        return this.questionRepository.countByQuizId(id);
    }


    @Override
    public Question updateQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestions() {
        return new HashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Question getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId).get();
    }

    @Override
    public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }

    @Override
    public void deleteQuestion(Long quesId) {
        Question question = new Question();
        question.setQuesId(quesId);
        this.questionRepository.delete(question);
    }

    @Override
    public Question get(Long questionsId) {
        return this.questionRepository.getOne(questionsId);
    }
}
