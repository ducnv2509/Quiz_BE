package com.exam.repository;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Set<Question> findByQuiz(Quiz quiz);

    @Query(value = "select  count(*) from question where quiz_q_id = ?", nativeQuery = true)
    Long countByQuizId(Long qId);
}
