package com.example.surveyapp.presenter;

import android.content.Context;

import com.example.surveyapp.interfaces.searchSurvey.iSearchPresenter;
import com.example.surveyapp.model.SearchDataModel;
import com.example.surveyapp.interfaces.searchSurvey.iSearchDataModel;
import com.example.surveyapp.interfaces.searchSurvey.iMainActivity;

public class SearchPresenter implements iSearchPresenter {

    private iMainActivity view;
    private iSearchDataModel model;

    public SearchPresenter(iMainActivity view, Context context) {
        this.view = view;
        model = new SearchDataModel(this, context);
    }

    @Override
    public void searchData(String search) {
        model.searchSurveys(search);
    }

    @Override
    public void showData(String response) {
        view.showData(response);
    }

    @Override
    public void showProgress() {
        view.showProgress();
    }

    @Override
    public void hideProgress() {
        view.hideProgress();
    }

    @Override
    public void showSnackbar(String message) {
        view.showSnackbar(message);
    }
}
