package com.example.surveyapp.data;

public class QuestionAndAnswer {

    private String questionCode;
    private String answerCode;

    private String questionDescription;


    private String answerDescription;


    public QuestionAndAnswer(String questionCode,String questionDescription, String answerCode, String answerDescription) {
        this.questionCode = questionCode;
        this.answerCode = answerCode;
        this.questionDescription = questionDescription;
        this.answerDescription = answerDescription;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }
}
