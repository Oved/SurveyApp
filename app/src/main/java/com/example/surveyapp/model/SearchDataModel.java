package com.example.surveyapp.model;

import static com.example.surveyapp.utils.Tools.isNetworkAvailable;

import android.content.Context;
import android.util.Log;

import com.example.surveyapp.data.DataESC;
import com.example.surveyapp.data.DataQTS;
import com.example.surveyapp.data.DataRES;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.data.DataTPE;
import com.example.surveyapp.interfaces.searchSurvey.iSearchDataModel;
import com.example.surveyapp.interfaces.searchSurvey.iSearchPresenter;
import com.example.surveyapp.interfaces.iService;
import com.example.surveyapp.utils.Tools;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchDataModel implements iSearchDataModel {

    //    private String BASE_URL = "https://oyntv.co/survey/leersvy.php?tab=datasvy&cod=";
    private final Retrofit retrofit;
    private final Context context;
    private final String BASE_URL = "https://oyntv.co/";

    private iSearchPresenter presenter;

    public SearchDataModel(iSearchPresenter presenter, Context ctx) {
        context= ctx;
        this.presenter = presenter;
        retrofit = getRetrofit();
    }

    @Override
    public void searchSurveys(String search) {
        if(isNetworkAvailable(context)) {
            presenter.showProgress();

            iService service = retrofit.create(iService.class);
            service.getSurveySVY("datasvy", search).enqueue(new Callback<List<DataSVY>>() {
                @Override
                public void onResponse(Call<List<DataSVY>> call, Response<List<DataSVY>> response) {
                    if (response.isSuccessful()) {
                        List<DataSVY> data = response.body();
                        Tools.saveDataSVY(data, context);
                        searchSurveyQTS(search);

                    } else {
                        Log.e("ERROR", "onFailure: ");
                    }
                }

                @Override
                public void onFailure(Call<List<DataSVY>> call, Throwable t) {
                    presenter.hideProgress();
                    Log.e("ERROR", "onFailure: ");
                }
            });
        }else {
            presenter.showSnackbar("No hay conexión a internet");
        }
    }

    private void searchSurveyQTS(String codeSurvey) {
        iService service = retrofit.create(iService.class);
        service.getSurveyQTS("dataqts",codeSurvey).enqueue(new Callback<List<DataQTS>>() {
            @Override
            public void onResponse(Call<List<DataQTS>> call, Response<List<DataQTS>> response) {
                if (response.isSuccessful()) {
                    List<DataQTS> data = response.body();
                    Tools.saveDataQTS(data, context);
                    searchSurveyRES(codeSurvey);

                } else {
                    Log.e("ERROR", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<List<DataQTS>> call, Throwable t) {
                presenter.hideProgress();
            }
        });
    }

    private void searchSurveyRES(String codeSurvey) {
        iService service = retrofit.create(iService.class);
        service.getSurveyRES("datares",codeSurvey).enqueue(new Callback<List<DataRES>>() {
            @Override
            public void onResponse(Call<List<DataRES>> call, Response<List<DataRES>> response) {
                if (response.isSuccessful()) {
                    List<DataRES> data = response.body();
                    Tools.saveDataRES(data, context);
                    searchSurveyESC();

                } else {
                    Log.e("ERROR", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<List<DataRES>> call, Throwable t) {
                presenter.hideProgress();
            }
        });
    }

    private void searchSurveyESC() {
        iService service = retrofit.create(iService.class);
        service.getSurveyESC("dataesc","%").enqueue(new Callback<List<DataESC>>() {
            @Override
            public void onResponse(Call<List<DataESC>> call, Response<List<DataESC>> response) {
                if (response.isSuccessful()) {
                    List<DataESC> data = response.body();
                    Tools.saveDataESC(data, context);
                    saveImages();
                    presenter.showSnackbar("Encuesta guarda de manera exitosa");

                } else {
                    Log.e("ERROR", "onFailure: ");
                }
                presenter.hideProgress();
            }

            @Override
            public void onFailure(Call<List<DataESC>> call, Throwable t) {
                presenter.hideProgress();
            }
        });
    }

    private void saveImages() {
        if (Tools.getDataRES(context)!=null) {
            for (DataRES res : Tools.getDataRES(context)) {
                if (!res.getFotores().isEmpty())
                    Tools.savePicture(context, res.getFotores(), res.getDescres()+res.getSeqnqts());
            }
        }
    }

    private void searchSurveyTPE() {
        iService service = retrofit.create(iService.class);
        service.getSurveyTPE("datatpe","%").enqueue(new Callback<List<DataTPE>>() {
            @Override
            public void onResponse(Call<List<DataTPE>> call, Response<List<DataTPE>> response) {
                presenter.hideProgress();
                if (response.isSuccessful()) {
                    List<DataTPE> data = response.body();
                    Tools.saveDataTPE(data, context);


                } else {
                    Log.e("ERROR", "onFailure: ");
                }
            }

            @Override
            public void onFailure(Call<List<DataTPE>> call, Throwable t) {
                presenter.hideProgress();
            }
        });
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
