package com.example.surveyapp.interfaces.insertSurveysMySql;

public interface MainView {
    void showSuccess();
    void showError(String message);
    void showProgress();
    void hideProgress();
}
