package com.example.surveyapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.surveyapp.R;
import com.example.surveyapp.data.DataESC;
import com.example.surveyapp.data.DataQTS;
import com.example.surveyapp.data.DataRES;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.data.DataTPE;
import com.example.surveyapp.utils.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class StartSurvey extends AppCompatActivity {

    private TextView tvNameRespondent;
    private Button buttonNext;
    private TextView titleSurvey, subTitleSurvey, datesSurvey;
    private LinearLayout layoutContainer;
    private List<DataSVY> dataSVYList;
    private List<DataQTS> dataQTSList;
    private List<DataRES> dataRESList;
    private List<DataESC> dataESCList;
    private List<DataTPE> dataTPEList;
    private List<CardView> cardViewList;
    private List<CheckBox> listChecks = new ArrayList<>();
    private boolean isAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_survey);
        addView();
        getSurveyLists();
        setDataInit();
        insertQuestions();
    }

    private void insertQuestions() {
        for (DataQTS question : dataQTSList) {
            // Crear CardView para cada pregunta
            CardView cardView = new CardView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 0, 12, 16);
            cardView.setRadius(10f);
            cardView.setElevation(25f);
            cardView.setLayoutParams(params);

            // Crear LinearLayout para el contenido del CardView
            LinearLayout cardContent = new LinearLayout(this);
            cardContent.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            cardContent.setOrientation(LinearLayout.VERTICAL);
            cardContent.setPadding(16, 16, 16, 16); // Relleno interno del LinearLayout

            // Configurar los datos en los elementos del CardView
            TextView questionTextView = new TextView(this);
            questionTextView.setTextColor(getResources().getColor(R.color.black));
            questionTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            questionTextView.setText(question.getDescqts());
            String textViewQuestionId = "textView_qts_" + question.getSeqnqts();
            questionTextView.setId(ViewCompat.generateViewId());
            questionTextView.setTag(textViewQuestionId);
            cardContent.addView(questionTextView);

            if (question.getTiprqts().toUpperCase(Locale.ROOT).equals("U")) {
                RadioGroup radioGroup = new RadioGroup(this);
                radioGroup.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                String radioGroupId = "radioGroup_qts_" + question.getSeqnqts();
                radioGroup.setId(ViewCompat.generateViewId());
                radioGroup.setTag(radioGroupId);
                for (DataRES options : dataRESList) {
                    if (options.getSeqnqts().equals(question.getSeqnqts())) {
                        RadioButton radioButton = new RadioButton(this);
                        radioButton.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));
                        radioButton.setText(options.getDescres());
                        String radioButtonId = "radioButton_qts_" + question.getSeqnqts() + options.getOrdnres();
                        radioButton.setId(ViewCompat.generateViewId());
                        radioButton.setTag(radioButtonId);
                        radioGroup.addView(radioButton);
                    }
                }
                cardContent.addView(radioGroup);
            }
            if (question.getTiprqts().toUpperCase(Locale.ROOT).equals("M")) {

                LinearLayout layoutCheckBox = new LinearLayout(this);
                layoutCheckBox.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                if (question.getCodetpe().toUpperCase(Locale.ROOT).equals("CA")) {

                    layoutCheckBox.setOrientation(LinearLayout.VERTICAL);
                    String radioGroupId = "layoutCheck_qts_" + question.getSeqnqts();
                    layoutCheckBox.setTag(radioGroupId);


                    LinearLayout linearGrid = new LinearLayout(this);
                    linearGrid.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    linearGrid.setOrientation(LinearLayout.VERTICAL);
                    linearGrid.setPadding(16, 16, 16, 16);
                    linearGrid.setTag("id_linear_grid_" + question.getSeqnqts());

                    for (DataRES answer : dataRESList) {
                        if (answer.getSeqnqts().equals(question.getSeqnqts())) {
                            LinearLayout linearAnswerGrid = new LinearLayout(this);
                            linearAnswerGrid.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            ));
                            linearAnswerGrid.setOrientation(LinearLayout.HORIZONTAL);
                            linearAnswerGrid.setTag("id_linear_grid_answers_" + answer.getOrdnres());
                            TextView textViewAnswerGrid = new TextView(this);
                            textViewAnswerGrid.setTextColor(getResources().getColor(R.color.black));
                            textViewAnswerGrid.setLayoutParams(new LinearLayout.LayoutParams(
                                    150,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            ));
                            textViewAnswerGrid.setText(answer.getDescres());
                            String tagTVGrid = "id_text_answer_grid_" + answer.getOrdnres();
                            textViewAnswerGrid.setTag(tagTVGrid);
                            if (!question.getDepnqts().isEmpty())
                                linearAnswerGrid.setVisibility(View.GONE);
                            linearAnswerGrid.addView(textViewAnswerGrid);

                            RadioGroup radioGroupGrid = new RadioGroup(this);
                            radioGroupGrid.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            ));
                            radioGroupGrid.setOrientation(LinearLayout.HORIZONTAL);
                            String idRadioGroupGrid = "id_radio_group_grid_" + answer.getOrdnres();
                            radioGroupGrid.setTag(idRadioGroupGrid);

                            for (int ind = 1; ind <= 5; ind++) {
                                RadioButton radioButton = new RadioButton(this);
                                radioButton.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));
                                String radioButtonId = "id_button_answer_grid_" + ind;
                                radioButton.setTag(radioButtonId);
                                radioGroupGrid.addView(radioButton);
                            }
                            linearAnswerGrid.addView(radioGroupGrid);
                            layoutCheckBox.addView(linearAnswerGrid);
                        }
                    }
                } else {
                    layoutCheckBox.setOrientation(LinearLayout.VERTICAL);
                    String radioGroupId = "layoutCheck_qts_" + question.getSeqnqts();
                    layoutCheckBox.setId(ViewCompat.generateViewId());
                    layoutCheckBox.setTag(radioGroupId);
                    for (DataRES options : dataRESList) {
                        if (options.getSeqnqts().equals(question.getSeqnqts())) {
                            CheckBox checkBox = new CheckBox(this);
                            checkBox.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            ));
                            if (!options.getFotores().isEmpty()) {
                                if (Tools.isNetworkAvailable(this)) {
                                    Glide.with(this)
                                            .load(options.getFotores())
                                            .into(new CustomTarget<Drawable>() {
                                                @Override
                                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                    int desiredWidth = 350;  // Tamaño deseado en píxeles
                                                    int desiredHeight = 350;

                                                    resource.setBounds(0, 0, desiredWidth, desiredHeight);
                                                    checkBox.setCompoundDrawables(resource, null, null, null);
                                                }

                                                @Override
                                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                                    // Opcional: Manejar la limpieza de la carga
                                                }
                                            });
                                } else {
                                    checkBox.setWidth(350);
                                    checkBox.setHeight(350);
                                    Drawable drawable = getResources().getDrawable(R.drawable.splash);
                                    drawable.setBounds(0, 0, 100, 100);
                                    checkBox.setCompoundDrawables(drawable, null, null, null);
                                }
                            }
                            checkBox.setText(options.getDescres());
                            String checkBoxId = "checkBox_qts_" + question.getSeqnqts() + options.getOrdnres();
                            checkBox.setId(ViewCompat.generateViewId());
                            checkBox.setTag(checkBoxId);
                            if (!question.getDepnqts().isEmpty())
                                checkBox.setVisibility(View.GONE);
                            layoutCheckBox.addView(checkBox);

                        }
                    }
                }
                cardContent.addView(layoutCheckBox);
            }

            // Agregar el LinearLayout del contenido al CardView
            cardView.addView(cardContent);

            String cardViewId = "cardview_qts_" + question.getSeqnqts();
            cardView.setId(ViewCompat.generateViewId());
            cardView.setTag(cardViewId);
            cardView.setVisibility(View.GONE);
            layoutContainer.addView(cardView);
            cardViewList.add(cardView);

        }
        showQuestionCards();
    }

    private void createQuestionCodeCA(DataQTS question, CardView cardView, LinearLayout layoutCardContent) {
    }

    private void showQuestionCards() {
        for (int i = 0; i < cardViewList.size(); i++) {
            String subCardId = cardViewList.get(i).getTag().toString();
            if (subCardId.substring(subCardId.length() - 2).equals(dataQTSList.get(i).getSeqnqts())) {
                if (dataQTSList.get(i).getCodetpe().toUpperCase(Locale.ROOT).equals("PF")) {
                    cardViewList.get(i).setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

        buttonNext.setOnClickListener(v -> {

            if (tvNameRespondent.getText().toString().trim().isEmpty()) {
                tvNameRespondent.setError("Ingresa el nombre del encuestado");
                tvNameRespondent.setFocusable(true);
                return;
            }

            for (int i = 0; i < cardViewList.size(); i++) {
                String subCardId = cardViewList.get(i).getTag().toString();
                if (subCardId.substring(subCardId.length() - 2).equals(dataQTSList.get(i).getSeqnqts())) {
                    if (dataQTSList.get(i).getCodetpe().toUpperCase(Locale.ROOT).equals("PF") && cardViewList.get(i).isEnabled()) {
                        String idRadioGroup = "radioGroup_qts_" + dataQTSList.get(i).getSeqnqts();
                        RadioGroup radioGroup = cardViewList.get(i).findViewWithTag(idRadioGroup);
                        // Obtener el ID del RadioButton seleccionado
                        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                        if (selectedRadioButtonId != -1) {
                            RadioButton selectedRadioButton = cardViewList.get(i).findViewById(selectedRadioButtonId);
                            if (selectedRadioButton.getText().equals("Si")) {
                                cardViewList.get(i).setAlpha(0.5f);
                                for (int posRadio = 0; posRadio < radioGroup.getChildCount(); posRadio++) {
                                    View child = radioGroup.getChildAt(posRadio);
                                    child.setClickable(false);
                                }
                                nextQuestions();
                                break;
                            } else {
                                Toast.makeText(this, "Encuesta guardada y se finaliza la encuesta", Toast.LENGTH_SHORT).show();
                                allFieldsCorrect();
                                return;
                            }
                        } else {
                            Toast.makeText(this, "Selecciona una respuesta para continuar o finalizar la encuesta", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    } else {
                        nextQuestions();
                        break;
                    }
                }
//                else {  //todo funcionaba bien con esto pero creo que es innecesario
//                    nextQuestions();
//                    break;
//                }
            }
        });
    }

    private void nextQuestions() {
        boolean noCheckBoxSelected = true;
        Drawable drawableEnd = getResources().getDrawable(R.drawable.ic_error);

        for (int i = 0; i < cardViewList.size(); i++) {
            if (dataQTSList.get(i).getDepnqts().isEmpty()) {
                cardViewList.get(i).setVisibility(View.VISIBLE);
                String idTextQuestion = "textView_qts_" + dataQTSList.get(i).getSeqnqts();
                TextView textViewQuestion = cardViewList.get(i).findViewWithTag(idTextQuestion);

                if (dataQTSList.get(i).getTiprqts().toUpperCase(Locale.ROOT).equals("U")) {
                    String idRadioGroup = "radioGroup_qts_" + dataQTSList.get(i).getSeqnqts();
                    RadioGroup radioGroup = cardViewList.get(i).findViewWithTag(idRadioGroup);
                    // Obtener el ID del RadioButton seleccionado
                    int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    if (selectedRadioButtonId != -1) {
                        textViewQuestion.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                null,
                                null,
                                null,
                                null
                        );
                        RadioButton selectedRadioButton = cardViewList.get(i).findViewById(selectedRadioButtonId);
                        cardViewList.get(i).setAlpha(0.5f);
                        for (int posRadio = 0; posRadio < radioGroup.getChildCount(); posRadio++) {
                            View child = radioGroup.getChildAt(posRadio);
                            child.setClickable(false);
                        }
//                        if (selectedRadioButton.getText().equals("Si")) {
//                            cardViewList.get(i).setAlpha(0.5f);
//                            cardViewList.get(i).setEnabled(false);
//                        } else {
//                            Toast.makeText(this, "Encuesta guardada", Toast.LENGTH_SHORT).show();
//                            //Guardar esa respuesta pero verificar que no se guarde dos veces
//                            saveSurvey();
//                        }
                    } else {
                        textViewQuestion.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                null,
                                null,
                                drawableEnd,
                                null
                        );
                    }
                }
                if (dataQTSList.get(i).getTiprqts().toUpperCase(Locale.ROOT).equals("M")) {
                    String idLinearCheck = "layoutCheck_qts_" + dataQTSList.get(i).getSeqnqts();
                    LinearLayout linearChecks = cardViewList.get(i).findViewWithTag(idLinearCheck);
                    int childCount = linearChecks.getChildCount();
                    List<CheckBox> checkBoxList = new ArrayList<>();

                    for (int j = 0; j < childCount; j++) {
                        View childView = linearChecks.getChildAt(j);
                        CheckBox checkBox = (CheckBox) childView;
                        checkBoxList.add(checkBox);
                    }

                    for (CheckBox checkBox : checkBoxList) {
                        if (checkBox.isChecked()) {
                            textViewQuestion.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
                            noCheckBoxSelected = false;
                            //checkBox.setTag("qts_" + dataQTSList.get(i).getSeqnqts());
                            listChecks.add(checkBox);
                            checkBox.setClickable(false);
                            cardViewList.get(i).setAlpha(0.5f);
                            for (CheckBox checks : checkBoxList) checks.setClickable(false);

                        } else {
                            if (noCheckBoxSelected) {
                                textViewQuestion.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                        null,
                                        null,
                                        drawableEnd,
                                        null
                                );
                            }
                        }
                    }


//                    for (int j = 0; j < childCount; j++) {
//                        View childView = linearChecks.getChildAt(j);
//                        //if (childView instanceof CheckBox) {
//                        CheckBox checkBox = (CheckBox) childView;
//                        if (checkBox.isChecked()) {
//                            textViewQuestion.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
//                            noCheckBoxSelected = false;
//                            checkBox.setTag("qts_" + dataQTSList.get(i).getSeqnqts());
//                            listChecks.add(checkBox);
//
//                            checkBox.setClickable(false);
//                            cardViewList.get(i).setAlpha(0.5f);
//
//                        } else {
//                            if (noCheckBoxSelected) {
//                                textViewQuestion.setCompoundDrawablesRelativeWithIntrinsicBounds(
//                                        null,
//                                        null,
//                                        drawableEnd,
//                                        null
//                                );
//                            }
//                        }
//                        //}
//                    }

                }
            }

//            if (!dataQTSList.get(i).getDepnqts().isEmpty()) {
//
//            }
        }
        showHiddenQuestions();
    }

    private void showHiddenQuestions() {
        for (CheckBox check : listChecks) {
            for (DataQTS qts : dataQTSList) {
                String modifiedString = check.getTag().toString().substring(0, check.getTag().toString().length() - 2);
                if (modifiedString.equals("checkBox_qts_" + qts.getDepnqts())) {
                    String cardViewId = "cardview_qts_" + qts.getSeqnqts();
                    CardView cardQts = layoutContainer.findViewWithTag(cardViewId);
                    if (cardQts != null) {
                        cardQts.setVisibility(View.VISIBLE);
                        String idLinear = "layoutCheck_qts_" + qts.getSeqnqts();
                        LinearLayout linearLayout = cardQts.findViewWithTag(idLinear);
                        if (linearLayout != null) {

                            List<LinearLayout> listLinearGrid = new ArrayList<>();
                            for (DataRES answer : dataRESList) {
                                if (answer.getSeqnqts().equals(qts.getSeqnqts())) {
                                    String idLayoutGrid = "id_linear_grid_answers_" + answer.getOrdnres();
                                    LinearLayout linearAnswerGrid = linearLayout.findViewWithTag(idLayoutGrid);
                                    if (linearAnswerGrid != null)
                                        listLinearGrid.add(linearAnswerGrid);
                                }
                            }


                            for (LinearLayout linearAnswerGrid : listLinearGrid) {
                                for (DataRES answer : dataRESList) {
                                    for (int i = 0; i < linearAnswerGrid.getChildCount(); i++) {
                                        String idRadioGroupGrid = "id_radio_group_grid_" + answer.getOrdnres();
                                        RadioGroup radioGroupGrid = linearAnswerGrid.findViewWithTag(idRadioGroupGrid);

                                        String idTextViewGrid = "id_text_answer_grid_" + answer.getOrdnres();
                                        TextView tvAnswerGrid = linearAnswerGrid.findViewWithTag(idTextViewGrid);
                                        if (tvAnswerGrid != null) {
                                            String tagCheck = check.getTag().toString();
                                            if (tagCheck.substring(tagCheck.length() - 2).equals(answer.getOrdnres())) {
                                                if (check.getText().equals(tvAnswerGrid.getText().toString()))
                                                    linearAnswerGrid.setVisibility(View.VISIBLE);
                                            }
                                        }


                                        if (radioGroupGrid != null) {
                                            int selectedRadioButtonId = radioGroupGrid.getCheckedRadioButtonId();
                                            if (selectedRadioButtonId != -1) {
                                                for (int rb = 0; rb < radioGroupGrid.getChildCount(); rb++) {
                                                    radioGroupGrid.getChildAt(rb).setClickable(false);
                                                    linearAnswerGrid.setAlpha(0.5f);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //todo Hasta aquí bien
        }

        if (allFieldsCorrect())
            saveSurvey();

    }

    private boolean allFieldsCorrect() {
        boolean isSave = true;


        for (int i = 0; i < cardViewList.size(); i++) {
            //validar que los campos esten seleccionados
        }

        return isSave;
    }

    private void saveSurvey() {
        //Se debe guardar la encuesta localmente
        //Se debe generar un id de la encuesta e ir aumentando a medida que se vayan realizando
        //Guardar también el campo alfanumérico
    }

    private void addView() {

        layoutContainer = findViewById(R.id.linear_layout_container);
        titleSurvey = findViewById(R.id.text_title_survey);
        subTitleSurvey = findViewById(R.id.text_subtitle_survey);
        datesSurvey = findViewById(R.id.text_dates_survey);
        tvNameRespondent = findViewById(R.id.edittext_name_respondent);
        buttonNext = findViewById(R.id.buttonNext);
        cardViewList = new ArrayList<>();
        layoutContainer.setOnClickListener(v -> {
            tvNameRespondent.setCursorVisible(false);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });
        tvNameRespondent.setOnClickListener(c -> {
            tvNameRespondent.setCursorVisible(true);
        });
    }

    private void getSurveyLists() {
        dataSVYList = Tools.getDataSVY(this);
        dataQTSList = Tools.getDataQTS(this);
        dataRESList = Tools.getDataRES(this);
        dataESCList = Tools.getDataESC(this);
        dataTPEList = Tools.getDataTPE(this);

        Collections.sort(dataQTSList, (data1, data2) -> {
            String seqnqts1 = data1.getSeqnqts();
            String seqnqts2 = data2.getSeqnqts();
            return seqnqts1.compareTo(seqnqts2);
        });

        Collections.sort(dataRESList, (data1, data2) -> {
            String seqnqts1 = data1.getOrdnres();
            String seqnqts2 = data2.getOrdnres();
            return seqnqts1.compareTo(seqnqts2);
        });

    }

    private void setDataInit() {
        DataSVY dataSVY = dataSVYList.get(0);
        titleSurvey.setText(dataSVY.getNamesvy());
        subTitleSurvey.setText(dataSVY.getObjesvy());
        datesSurvey.setText("Fecha inicio " + dataSVY.getDinisvy() + " - " + "Fecha fin " + dataSVY.getDfinsvy());
    }

}