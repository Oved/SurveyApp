package com.example.surveyapp.interfaces;

import com.example.surveyapp.data.DataESC;
import com.example.surveyapp.data.DataQTS;
import com.example.surveyapp.data.DataRES;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.data.DataTPE;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface iService {

//    @GET("/users")
//    Call<SurveySVY> service();

    @GET("survey/leersvy.php")
    Call<List<DataSVY>> getSurveySVY(@Query("tab") String tab, @Query("cod") String cod);

    @GET("survey/leersvy.php")
    Call<List<DataQTS>> getSurveyQTS(@Query("tab") String tab, @Query("cod") String cod);

    @GET("survey/leersvy.php")
    Call<List<DataTPE>> getSurveyTPE(@Query("tab") String tab, @Query("cod") String cod);

    @GET("survey/leersvy.php")
    Call<List<DataRES>> getSurveyRES(@Query("tab") String tab, @Query("cod") String cod);

    @GET("survey/leersvy.php")
    Call<List<DataESC>> getSurveyESC(@Query("tab") String tab, @Query("cod") String cod);
}
