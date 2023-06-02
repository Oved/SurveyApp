package com.example.surveyapp.data;

import java.util.List;

public class SurveyResponses {
    private String respondentName;
    private String surveyCode;
    private String date;
    private List<QuestionAndAnswer> questionAndAnswersList;

    public String getRespondentName() {
        return respondentName;
    }

    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }

    public String getSurveyCode() {
        return surveyCode;
    }

    public void setSurveyCode(String surveyCode) {
        this.surveyCode = surveyCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<QuestionAndAnswer> getQuestionAndAnswersList() {
        return questionAndAnswersList;
    }

    public void setQuestionAndAnswersList(List<QuestionAndAnswer> questionAndAnswersList) {
        this.questionAndAnswersList = questionAndAnswersList;
    }
}
