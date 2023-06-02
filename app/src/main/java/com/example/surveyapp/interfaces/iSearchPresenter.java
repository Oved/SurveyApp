package com.example.surveyapp.interfaces;

public interface iSearchPresenter {

    void searchData(String search);
    void showData(String response);
    void showProgress();
    void hideProgress();
    void showSnackbar(String message);
}
