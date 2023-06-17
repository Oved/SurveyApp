package com.example.surveyapp.interfaces.insertSurveysMySql;

public interface MainPresenter {

    void insertSurveysInDB();
    void showProgress();
    void hideProgress();
    void showSuccess();
    void showError(String message);
}
