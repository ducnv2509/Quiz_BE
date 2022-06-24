package com.exam.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private java.util.Date createDate;
    private String nameTest;
    private String marksGot;
    private String correctAnswer;
    private String questionError;
    private String nameCategory;

}
