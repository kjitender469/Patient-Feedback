package com.example.patientfeedback;

/*
* Patient Information
* */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    EditText et_subject_initial, et_subject_id, et_dob, et_members, et_caregiver;
    RadioButton rb_male, rb_female, rb_illiterate, rb_primary, rb_secondary, rb_graduate, rb_post_graduate, rb_unemployed, rb_self_employed,
                rb_retired, rb_govt_pvt_service, rb_non_smoker, rb_occasional_smoker, rb_smoker, rb_non_alcoholic, rb_occasional_drinker,
                rb_alcoholic;
    Button bt_next, bt_save, bt_submit;

    String subject_initial, subject_id, subject_dob, subject_family_members, subject_caregiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et_subject_initial = (EditText) findViewById(R.id.edit_text_subject_initial);
        et_subject_id = (EditText) findViewById(R.id.edit_text_subject_id);
        et_dob = (EditText) findViewById(R.id.edit_text_dob);
        et_members = (EditText) findViewById(R.id.edit_text_members);
        et_caregiver = (EditText) findViewById(R.id.edit_text_caregiver);

        rb_male = (RadioButton)findViewById(R.id.radio_bt_male);
        rb_female = (RadioButton)findViewById(R.id.radio_bt_female);
        rb_illiterate = (RadioButton)findViewById(R.id.radioButton_Illiterate);
        rb_primary = (RadioButton)findViewById(R.id.radioButton_Primary);
        rb_secondary = (RadioButton)findViewById(R.id.radioButton_Secondary);
        rb_graduate = (RadioButton)findViewById(R.id.radioButton_Graduate);
        rb_post_graduate = (RadioButton)findViewById(R.id.radioButton_Post_Graduate);
        rb_unemployed = (RadioButton)findViewById(R.id.radioButton_Unemployed);
        rb_self_employed = (RadioButton)findViewById(R.id.radioButton_self_employed);
        rb_retired = (RadioButton)findViewById(R.id.radioButton_Retired);
        rb_govt_pvt_service = (RadioButton)findViewById(R.id.radioButton_Govt_Pvt_Service);
        rb_non_smoker = (RadioButton)findViewById(R.id.radioButton_non_smoker);
        rb_occasional_smoker = (RadioButton)findViewById(R.id.radioButton_Occasional_smoker);
        rb_smoker = (RadioButton)findViewById(R.id.radioButton_Smoker);
        rb_non_alcoholic = (RadioButton)findViewById(R.id.radioButton_non_alcoholic);
        rb_occasional_drinker = (RadioButton)findViewById(R.id.radioButton_Occasional_drinker);
        rb_alcoholic = (RadioButton)findViewById(R.id.radioButton_Alcoholic);

        bt_next = (Button)findViewById(R.id.button_next);
        bt_save = (Button)findViewById(R.id.button_save);
        bt_submit = (Button)findViewById(R.id.button_submit);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSubjectInfo();
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void getSubjectInfo(){
        subject_initial = et_subject_initial.getText().toString();
        subject_id = et_subject_id.getText().toString();
        subject_dob = et_dob.getText().toString();
        subject_family_members = et_members.getText().toString();
        subject_caregiver = et_caregiver.getText().toString();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_bt_male:
                if (checked){
                    showToast("male");
                }
                break;
            case R.id.radio_bt_female:
                if (checked){
                    showToast("female");
                }
                break;
            default:
                showToast("Inside Default");
                break;
        }
    }

    public void onRadioButtonClicked_education(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.radioButton_Illiterate:
                if(checked){
                    showToast("Illiterate");
                }
                break;
            case R.id.radioButton_Primary:
            if(checked){
                showToast("Primary");
            }
                break;
            case R.id.radioButton_Secondary:
                if(checked){
                    showToast("Secondary");
                }
                break;
            case R.id.radioButton_Graduate:
                if(checked){
                    showToast("Graduate");
                }
                break;
            case R.id.radioButton_Post_Graduate:
                if(checked){
                    showToast("Post graduate");
                }
                break;
        }
    }

    public void onRadioButtonClicked_EmploymentStatus(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.radioButton_Unemployed:
                if(checked){
                    showToast("Unemployed");
                }
                break;
            case R.id.radioButton_self_employed:
                if(checked){
                    showToast("Self Employed");
                }
                break;
            case R.id.radioButton_Govt_Pvt_Service:
                if(checked){
                    showToast("Govt./Pvt Service");
                }
                break;
            case R.id.radioButton_Retired:
                if(checked){
                    showToast("Retired");
                }
                break;
        }
    }

    public void onRadioButtonClicked_SmokingStatus(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.radioButton_non_smoker:
                if(checked){
                    showToast("Non Smoker");
                }
                break;
            case R.id.radioButton_Occasional_smoker:
                if(checked){
                    showToast("Occasional Smoker");
                }
                break;
            case R.id.radioButton_Smoker:
                if(checked){
                    showToast("Smoker");
                }
                break;
        }
    }

    public void onRadioButtonClicked_AlcoholConsumption(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.radioButton_non_alcoholic:
                if(checked){
                    showToast("Non Alcoholic");
                }
                break;
            case R.id.radioButton_Occasional_drinker:
                if(checked){
                    showToast("Occasional Drinker");
                }
                break;
            case R.id.radioButton_Alcoholic:
                if(checked){
                    showToast("Alcoholic");
                }
                break;
        }
    }

    public void showToast(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
