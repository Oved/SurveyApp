package com.example.surveyapp.presenter;

import android.content.Context;

import com.example.surveyapp.interfaces.insertSurveysMySql.MainModel;
import com.example.surveyapp.interfaces.insertSurveysMySql.MainPresenter;
import com.example.surveyapp.interfaces.insertSurveysMySql.MainView;
import com.example.surveyapp.model.MainModelImpl;
import com.example.surveyapp.view.MainActivity;

public class MainPresenterImpl implements MainPresenter {

    private MainModel model;
    private Context context;
    private MainView view;

    public MainPresenterImpl(Context context, MainActivity view) {
        this.view = view;
        this.context = context;
        model = new MainModelImpl(context, this);
    }

    @Override
    public void insertSurveysInDB() {
        model.insertSurveys();

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showSuccess() {
        view.showSuccess();
    }

    @Override
    public void showError(String message) {
        view.showError(message);
    }
}
