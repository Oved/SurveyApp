package com.example.surveyapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
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
import com.example.surveyapp.data.Answers;
import com.example.surveyapp.data.DataESC;
import com.example.surveyapp.data.DataQTS;
import com.example.surveyapp.data.DataRES;
import com.example.surveyapp.data.DataSVY;
import com.example.surveyapp.data.DataTPE;
import com.example.surveyapp.data.QuestionAndAnswer;
import com.example.surveyapp.data.SurveyResponses;
import com.example.surveyapp.utils.MyLocation;
import com.example.surveyapp.utils.Tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StartSurvey extends AppCompatActivity{

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
    private double latitude;
    private double longitude;
    private String timestamp ;

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

                    TextView considerationText = new TextView(this);
                    considerationText.setText("Seleccione la opción teniendo en cuenta a 1 como Malo y 5 como Excelente");
                    considerationText.setTextColor(getResources().getColor(R.color.purple_700));
                    layoutCheckBox.addView(considerationText);

                    //TODO: creo que no lo uso
                    LinearLayout linearGrid = new LinearLayout(this);
                    linearGrid.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    linearGrid.setGravity(Gravity.END);
                    for (int cond =1; cond<=5; cond++){
                        TextView textViewCondition = new TextView(this);
                        textViewCondition.setText(String.valueOf(cond));
                        textViewCondition.setPadding(26,0,26,0);
                        textViewCondition.setTextColor(getResources().getColor(R.color.teal_700));
                        linearGrid.addView(textViewCondition);
                    }

                    linearGrid.setOrientation(LinearLayout.HORIZONTAL);
                    linearGrid.setTag("id_linear_grid_" + question.getSeqnqts());

                    layoutCheckBox.addView(linearGrid);

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
                                    0,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    1
                            ));
                            textViewAnswerGrid.setText(answer.getDescres());
                            String tagTVGrid = "id_text_answer_grid_" + answer.getOrdnres();
                            textViewAnswerGrid.setTag(tagTVGrid);
                            if (!question.getDepnqts().isEmpty())
                                linearAnswerGrid.setVisibility(View.GONE);
                            linearAnswerGrid.addView(textViewAnswerGrid);

                            RadioGroup radioGroupGrid = new RadioGroup(this);
                            radioGroupGrid.setLayoutParams(new LinearLayout.LayoutParams(
                                    0,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    1
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
                                    Drawable drawable;
                                        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                                        String name = options.getDescres() + options.getSeqnqts();
                                        File imageFile = new File(directory, name + ".png");
                                        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                                        if (bitmap==null)
                                            drawable = getResources().getDrawable(R.drawable.no_image);
                                        else
                                            drawable = new BitmapDrawable(getResources(), bitmap);

                                    drawable.setBounds(0, 0, 350, 350);
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

                            //TODO Corregir el valor quemado de la respuesta para copntinuar con el flujo
                            if (selectedRadioButton.getText().equals("Si")) {
                                cardViewList.get(i).setAlpha(0.5f);
                                for (int posRadio = 0; posRadio < radioGroup.getChildCount(); posRadio++) {
                                    View child = radioGroup.getChildAt(posRadio);
                                    child.setClickable(false);
                                }
                                nextQuestions();
                                break;
                            } else {

                                String tagButton = selectedRadioButton.getTag().toString();
                                List<QuestionAndAnswer> questionAnsResponse = new ArrayList<>();
                                QuestionAndAnswer questionAndAnswer = new QuestionAndAnswer();
                                questionAndAnswer.setQuestionCode(dataQTSList.get(i).getSeqnqts());
                                questionAndAnswer.setAnswerCode(tagButton.substring(tagButton.length() - 2));
                                questionAndAnswer.setAnswerDescription(selectedRadioButton.getText().toString());
                                questionAndAnswer.setTipQuestion(dataQTSList.get(i).getTiprqts());
                                questionAnsResponse.add(questionAndAnswer);
                                saveSurvey(questionAnsResponse);
                                break;

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
                }
            }
        }
        showHiddenQuestions();
    }

    private void showHiddenQuestions() {
        //TODO toca revisar que se validen los check box
        // porque se pueden venir todos los de la diferentes preguntas

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
        }

        allFieldsCorrect();

    }

    private void allFieldsCorrect() {
        boolean hasSelected = true;

        List<QuestionAndAnswer> questionAndAnswersList = new ArrayList<>();
        loop:
        for (int i = 0; i < cardViewList.size(); i++) {
            String subCardId = cardViewList.get(i).getTag().toString();
            if (subCardId.substring(subCardId.length() - 2).equals(dataQTSList.get(i).getSeqnqts())) {
                LinearLayout linearLayoutCard = (LinearLayout) cardViewList.get(i).getChildAt(0);

                if (dataQTSList.get(i).getTiprqts().toUpperCase(Locale.ROOT).equals("U")) {
                    RadioGroup radioGroupLinear = (RadioGroup) linearLayoutCard.getChildAt(1);
                    int idRadioButtonSelected = radioGroupLinear.getCheckedRadioButtonId();
                    if (idRadioButtonSelected == -1) {
                        Toast.makeText(this, "Tienes preguntas por responder", Toast.LENGTH_SHORT).show();
                        hasSelected = false;
                        break;
                    }

                    RadioButton radioButtonSelected = findViewById(idRadioButtonSelected);
                    String tagRB = radioButtonSelected.getTag().toString();
                    String ordAnswer = tagRB.substring(tagRB.length() - 2);

                    QuestionAndAnswer answer = new QuestionAndAnswer();
                    answer.setQuestionCode(dataQTSList.get(i).getSeqnqts());
                    answer.setAnswerDescription(radioButtonSelected.getText().toString());
                    answer.setTipQuestion("U");
                    answer.setAnswerDescription(radioButtonSelected.getText().toString());
                    answer.setAnswerCode(ordAnswer);
                    questionAndAnswersList.add(answer);

                }

                if (dataQTSList.get(i).getTiprqts().toUpperCase(Locale.ROOT).equals("M")) {

                    LinearLayout layoutCheckBox = (LinearLayout) cardViewList.get(i).getChildAt(0);
                    QuestionAndAnswer answer = new QuestionAndAnswer();
                    answer.setQuestionCode(dataQTSList.get(i).getSeqnqts());
                    answer.setTipQuestion("M");
                    if (dataQTSList.get(i).getCodetpe().toUpperCase(Locale.ROOT).equals("CA")) {

                        LinearLayout linearContainingRadioGroup = new LinearLayout(this);
                        for (int ly = 0; ly < layoutCheckBox.getChildCount(); ly++) {
                            if (layoutCheckBox.getChildAt(ly) instanceof LinearLayout)
                                linearContainingRadioGroup = (LinearLayout) layoutCheckBox.getChildAt(ly);
                        }

                        List<LinearLayout> linearAnswerList = new ArrayList<>();
                        for (int ind = 0; ind < linearContainingRadioGroup.getChildCount(); ind++) {
                            if (linearContainingRadioGroup.getChildAt(ind).getVisibility() == View.VISIBLE &&
                                    (linearContainingRadioGroup.getChildAt(ind) instanceof LinearLayout)) {
                                linearAnswerList.add((LinearLayout) linearContainingRadioGroup.getChildAt(ind));
                            }
                        }

                        List<RadioGroup> radioGroupList = new ArrayList<>();
                        for (LinearLayout layoutAnswer : linearAnswerList) {
                            for (int rg = 0; rg < layoutAnswer.getChildCount(); rg++) {
                                if (layoutAnswer.getChildAt(rg) instanceof RadioGroup)
                                    radioGroupList.add((RadioGroup) layoutAnswer.getChildAt(rg));
                            }
                        }

                        String ordAnswer = "";
                        for (RadioGroup radioGroupGrid : radioGroupList) {
                            int selectedRadioButtonId = radioGroupGrid.getCheckedRadioButtonId();
                            if (selectedRadioButtonId == -1) {
                                Toast.makeText(this, "Tienes preguntas por responder", Toast.LENGTH_SHORT).show();
                                hasSelected = false;
                                break loop;
                            } else {
                                RadioButton radioButton = findViewById(selectedRadioButtonId);
                                String tagRadioGroup = radioGroupGrid.getTag().toString();
                                String tag = radioButton.getTag().toString();
                                String rta = tag.substring(tag.length()-1);
                                switch (rta) {
                                    case "1":
                                        rta = "Pésimo";
                                        break;
                                    case "2":
                                        rta = "Malo";
                                        break;
                                    case "3":
                                        rta = "Regular";
                                        break;
                                    case "4":
                                        rta = "Bueno";
                                        break;
                                    case "5":
                                        rta = "Excelente";
                                        break;
                                }
                                ordAnswer = tagRadioGroup.substring(tagRadioGroup.length() - 2);
                                switch (ordAnswer) {
                                    case "01":
                                        answer.setAnswer01(rta);
                                        break;
                                    case "02":
                                        answer.setAnswer02(rta);
                                        break;
                                    case "03":
                                        answer.setAnswer03(rta);
                                        break;
                                    case "04":
                                        answer.setAnswer04(rta);
                                        break;
                                    case "05":
                                        answer.setAnswer05(rta);
                                        break;
                                    case "06":
                                        answer.setAnswer06(rta);
                                        break;
                                }
                            }
                        }
                        //answer.setAnswerCode(ordAnswer);
                        questionAndAnswersList.add(answer);

                    } else {

                        String ordAnswer = "";
                        String response = "";
//                        List<CheckBox> checkBoxListAnswerSelected = new ArrayList<>();
//                        for (int ind = 0; ind < layoutCheckBox.getChildCount(); ind++) {
//                            if (layoutCheckBox.getChildAt(ind) instanceof Lin) {
//                                if (((CheckBox) layoutCheckBox.getChildAt(ind)).isChecked()) {
//                                    ordAnswer = ordAnswer + " / " + ((CheckBox) layoutCheckBox.getTag(ind)).toString().substring(layoutCheckBox.getTag(ind).toString().length() - 2);
//                                    response = response + " / " + ((CheckBox) layoutCheckBox.getChildAt(ind)).getText().toString();
//                                    checkBoxListAnswerSelected.add((CheckBox) layoutCheckBox.getChildAt(ind));
//                                }
//                            }
//                        }

                        //todo Test
                        LinearLayout linearChecks = new LinearLayout(this);
                        for (int ind = 0; ind < layoutCheckBox.getChildCount(); ind++) {
                            if (layoutCheckBox.getChildAt(ind) instanceof LinearLayout) {
                                linearChecks = ((LinearLayout) layoutCheckBox.getChildAt(ind));
                            }
                        }

                        List<CheckBox> checkBoxListAnswerSelected = new ArrayList<>();
                        for (int ind = 0; ind < linearChecks.getChildCount(); ind++) {
                            if (linearChecks.getChildAt(ind) instanceof CheckBox) {
                                if (((CheckBox) linearChecks.getChildAt(ind)).isChecked()) {
                                    String tag = ((CheckBox) linearChecks.getChildAt(ind)).getTag().toString();
                                    ordAnswer = tag.substring(tag.length() - 2);
                                    switch (ordAnswer) {
                                        case "01":
                                            answer.setAnswer01(((CheckBox) linearChecks.getChildAt(ind)).getText().toString());
                                            break;
                                        case "02":
                                            answer.setAnswer02(((CheckBox) linearChecks.getChildAt(ind)).getText().toString());
                                            break;
                                        case "03":
                                            answer.setAnswer03(((CheckBox) linearChecks.getChildAt(ind)).getText().toString());
                                            break;
                                        case "04":
                                            answer.setAnswer04(((CheckBox) linearChecks.getChildAt(ind)).getText().toString());
                                            break;
                                        case "05":
                                            answer.setAnswer05(((CheckBox) linearChecks.getChildAt(ind)).getText().toString());
                                            break;
                                        case "06":
                                            answer.setAnswer06(((CheckBox) linearChecks.getChildAt(ind)).getText().toString());
                                            break;
                                    }

                                    response = response + " / " + ((CheckBox) linearChecks.getChildAt(ind)).getText().toString();

                                    checkBoxListAnswerSelected.add((CheckBox) linearChecks.getChildAt(ind));
                                }
                            }
                        }

                        if (checkBoxListAnswerSelected.size() == 0) {
                            Toast.makeText(this, "Tienes preguntas por responder", Toast.LENGTH_SHORT).show();
                            hasSelected = false;
                            break;
                        }

                        //questionAndAnswersList.add(new QuestionAndAnswer(dataQTSList.get(i).getSeqnqts(),dataQTSList.get(i).getDescqts(),ordAnswer,response));

                        questionAndAnswersList.add(answer);
                    }
                }
            }
        }

        if (hasSelected)
            saveSurvey(questionAndAnswersList);
    }

    private void saveSurvey(List<QuestionAndAnswer> questionAndAnswersList) {

        Location location = MyLocation.getRecipientLocation(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        String formattedTimestamp = sdf.format(currentDate).replace('-',' ').replace(':',' ');
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.timestamp = formattedTimestamp.replaceAll("\\s+", "");;

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando encuesta...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(() -> {
            String numberSurvey = String.valueOf(Tools.generateCodeSurvey(this));
            List<Answers> answersList = new ArrayList<>();
            Answers answer = new Answers();
            answer.setCodesvy(dataSVYList.get(0).getCodesvy());
            answer.setSeqnqts("00");  //TODO secuencia de la pregunta
            answer.setSeqnrpt(numberSurvey); //TODO secuencia de número de encuesta, va aumentando
            answer.setEncurpt(tvNameRespondent.getText().toString());
            answer.setCodecel(Tools.getCodeAlpha(this));
            answer.setRpt01(String.valueOf(this.latitude));
            answer.setRpt02(String.valueOf(this.longitude));
            answer.setRpt03(this.timestamp);

            answersList.add(answer);

            for (QuestionAndAnswer questionAndAnswer : questionAndAnswersList) {
                Answers responses = new Answers();
                responses.setCodesvy(dataSVYList.get(0).getCodesvy());
                responses.setSeqnqts(questionAndAnswer.getQuestionCode());  //TODO secuencia de la pregunta
                responses.setSeqnrpt(numberSurvey); //TODO secuencia de número de encuesta, va aumentando
                responses.setEncurpt(tvNameRespondent.getText().toString());
                responses.setCodecel(Tools.getCodeAlpha(this));

                if (questionAndAnswer.getTipQuestion().equals("M")) {
                    responses.setRpt01(questionAndAnswer.getAnswer01());
                    responses.setRpt02(questionAndAnswer.getAnswer02());
                    responses.setRpt03(questionAndAnswer.getAnswer03());
                    responses.setRpt04(questionAndAnswer.getAnswer04());
                    responses.setRpt05(questionAndAnswer.getAnswer05());
                    responses.setRpt06(questionAndAnswer.getAnswer06());
                    responses.setRpt07(questionAndAnswer.getAnswer07());
                    responses.setRpt08(questionAndAnswer.getAnswer08());
                    responses.setRpt09(questionAndAnswer.getAnswer09());
                    responses.setRpt10(questionAndAnswer.getAnswer10());
                    responses.setRpt11(questionAndAnswer.getAnswer11());
                    responses.setRpt12(questionAndAnswer.getAnswer12());
                    responses.setRpt13(questionAndAnswer.getAnswer13());
                    responses.setRpt14(questionAndAnswer.getAnswer14());
                    responses.setRpt15(questionAndAnswer.getAnswer15());
                    responses.setRpt16(questionAndAnswer.getAnswer16());
                    responses.setRpt17(questionAndAnswer.getAnswer17());
                    responses.setRpt18(questionAndAnswer.getAnswer18());
                    responses.setRpt19(questionAndAnswer.getAnswer19());
                    responses.setRpt20(questionAndAnswer.getAnswer20());
                } else {
                    responses.setRpt01(questionAndAnswer.getAnswerDescription());
                }
                answersList.add(responses);
            }

            Tools.saveAnswers(answersList, this);
            progressDialog.dismiss();
            finish();
        }, 2000);
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