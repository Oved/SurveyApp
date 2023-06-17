package com.example.surveyapp.model;

import static com.example.surveyapp.utils.Tools.isNetworkAvailable;

import android.content.Context;
import android.util.Log;

import com.example.surveyapp.data.Answers;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.data.ResponseObject;
import com.example.surveyapp.interfaces.iService;
import com.example.surveyapp.interfaces.insertSurveysMySql.MainModel;
import com.example.surveyapp.interfaces.insertSurveysMySql.MainPresenter;
import com.example.surveyapp.utils.Tools;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainModelImpl implements MainModel {

    private final String BASE_URL = "https://oyntv.co/";
    private Context context;
    private MainPresenter presenter;
    private Retrofit retrofit;
    private String rq;

    public MainModelImpl(Context context, MainPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
        retrofit = getRetrofit();
    }

    List<Answers> data;
    boolean flag = false;

    @Override
    public void insertSurveys() {
        if (isNetworkAvailable(context)) {
            presenter.showProgress();

            iService service = retrofit.create(iService.class);
            data = Tools.getSavedAnswers(context);
            for (Answers answers : Tools.getSavedAnswers(context)) {

                service.insertData(answers.getTip(), answers.getCodesvy(), answers.getSeqnqts(), answers.getSeqnrpt(),
                                answers.getEncurpt(), answers.getCodecel(), answers.getRpt01(), answers.getRpt02(), answers.getRpt03(),
                                answers.getRpt04(), answers.getRpt05(), answers.getRpt06(), answers.getRpt07(), answers.getRpt08(), answers.getRpt09(),
                                answers.getRpt10(), answers.getRpt11(), answers.getRpt12(), answers.getRpt13(), answers.getRpt14(), answers.getRpt15(),
                                answers.getRpt16(), answers.getRpt17(), answers.getRpt18(), answers.getRpt19(), answers.getRpt20())
                        .enqueue(new Callback<List<ResponseObject>>() {
                            @Override
                            public void onResponse(Call<List<ResponseObject>> call, Response<List<ResponseObject>> response) {
                                presenter.hideProgress();
                                if (response.isSuccessful()) {
                                    ResponseObject data = response.body().get(0);
                                    if (data.getSAVE().equals("1"))
                                        flag = true;
                                } else {
                                    presenter.showError(response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ResponseObject>> call, Throwable t) {
                                presenter.hideProgress();
                                presenter.showError(t.getMessage());
                            }
                        });
            }
            if (flag)
                presenter.showSuccess();
            else
                presenter.showError("Parece que hubo problemas al insertar los datos");
        } else {
            presenter.showError("No hay conexión a internet");
        }
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
}
