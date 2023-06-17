package com.example.surveyapp.interfaces.searchSurvey;

public interface iMainActivity {

    void showData(String response);
    void showProgress();
    void hideProgress();
    void showSnackbar(String message);
}
