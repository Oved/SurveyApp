package com.example.surveyapp.interfaces;

import com.example.surveyapp.data.Answers;
import com.example.surveyapp.data.DataESC;
import com.example.surveyapp.data.DataQTS;
import com.example.surveyapp.data.DataRES;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.data.DataTPE;
import com.example.surveyapp.data.ResponseObject;
import com.example.surveyapp.data.SurveyResponses;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface iService {

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

    @POST("survey/leersvy.php")
    @FormUrlEncoded
    Call<List<ResponseObject>> insertData(@Field("tip") String tip,
                                          @Field("codesvy") String codesvy,
                                          @Field("seqnqts") String seqnqts,
                                          @Field("seqnrpt") String seqnrpt,
                                          @Field("encurpt") String encurpt,
                                          @Field("codecel") String codecel,
                                          @Field("rpt01") String rpt01,
                                          @Field("rpt02") String rpt02,
                                          @Field("rpt03") String rpt03,
                                          @Field("rpt04") String rpt04,
                                          @Field("rpt05") String rpt05,
                                          @Field("rpt06") String rpt06,
                                          @Field("rpt07") String rpt07,
                                          @Field("rpt08") String rpt08,
                                          @Field("rpt09") String rpt09,
                                          @Field("rpt10") String rpt10,
                                          @Field("rpt11") String rpt11,
                                          @Field("rpt12") String rpt12,
                                          @Field("rpt13") String rpt13,
                                          @Field("rpt14") String rpt14,
                                          @Field("rpt15") String rpt15,
                                          @Field("rpt16") String rpt16,
                                          @Field("rpt17") String rpt17,
                                          @Field("rpt18") String rpt18,
                                          @Field("rpt19") String rpt19,
                                          @Field("rpt20") String rpt20
    );
}
