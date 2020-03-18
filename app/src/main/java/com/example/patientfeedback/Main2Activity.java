package com.example.patientfeedback;

/*
* Patient Information
* */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    EditText et_subject_initial, et_subject_id, et_dob, et_members, et_caregiver;
    RadioButton rb_male, rb_female, rb_illiterate, rb_primary, rb_secondary, rb_graduate, rb_post_graduate, rb_unemployed, rb_self_employed,
                rb_retired, rb_govt_pvt_service, rb_non_smoker, rb_occasional_smoker, rb_smoker, rb_non_alcoholic, rb_occasional_drinker,
                rb_alcoholic;
    Button bt_next, bt_save, bt_submit, bt_list_view;

    String subject_initial, subject_id, subject_dob, subject_family_members, subject_caregiver, subject_gender, subject_education,
            subject_employment_status, subject_smoking_status, subject_alcohol_consumption;

    public String data_array[];
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    String subject_all_data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //setActionBar(myToolbar);


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
                if(valiadte()) {
                    getSubjectInfo();
                    new saveData().execute();
                }
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

    private boolean valiadte() {
        if(et_subject_initial.getText().toString().trim().isEmpty()){
            et_subject_initial.setError("This field can not be empty");
            et_subject_initial.requestFocus();
            return false;
        }
        else if(et_subject_id.getText().toString().trim().isEmpty()){
            et_subject_id.setError("This field can not be empty");
            et_subject_id.requestFocus();
            return false;
        }
        else if(et_dob.getText().toString().trim().isEmpty()){
            et_dob.setError("This field can not be empty");
            et_dob.requestFocus();
            return false;
        }
        else if(et_members.getText().toString().trim().isEmpty()){
            et_members.setError("This field can not be empty");
            et_members.requestFocus();
            return false;
        }
        else if(et_caregiver.getText().toString().trim().isEmpty()){
            et_caregiver.setError("This field can not be empty");
            et_caregiver.requestFocus();
            return false;
        }
        else
            return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.view_entries) {
            showToast("View Entries");
            startActivity(new Intent(getApplicationContext(), ViewSubjectData.class));
//            setContentView(R.layout.list_view_main);
//            bt_list_view = (Button)findViewById(R.id.button_list_view);
//
//            bt_list_view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Intent intent = new Intent(getApplicationContext(), );
//                    setContentView(R.layout.activity_main2);
//                    Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
//                    setSupportActionBar(myToolbar);
//                }
//            });
//            String data[] = {subject_initial};
//            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
//                    R.layout.list_view, data);
//            ListView lv = (ListView)findViewById(R.id.list_view);
//            //adapter.add(subject_initial);
//            //adapter.notifyDataSetChanged();
//            lv.setAdapter(adapter);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getSubjectInfo(){
        subject_initial = et_subject_initial.getText().toString();
        subject_id = et_subject_id.getText().toString();
        subject_dob = et_dob.getText().toString();
        subject_family_members = et_members.getText().toString();
        subject_caregiver = et_caregiver.getText().toString();
        subject_all_data = "*"+","+subject_initial+","+subject_id+","+subject_dob+","+subject_family_members
                +","+subject_caregiver+","+subject_gender+","+subject_education+","+subject_employment_status
                +","+subject_smoking_status+","+subject_alcohol_consumption+"#";
        //showToast(subject_final_data);
        //data_array = new String[]{subject_initial, subject_id, subject_dob, subject_family_members, subject_caregiver};
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_bt_male:
                if (checked){
                    subject_gender = "M";
                    showToast("male");
                }
                break;
            case R.id.radio_bt_female:
                if (checked){
                    subject_gender = "F";
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
                    subject_education = "Illiterate";
                    showToast("Illiterate");
                }
                break;
            case R.id.radioButton_Primary:
            if(checked){
                subject_education = "Primary";
                showToast("Primary");
            }
                break;
            case R.id.radioButton_Secondary:
                if(checked){
                    subject_education = "Secondary";
                    showToast("Secondary");
                }
                break;
            case R.id.radioButton_Graduate:
                if(checked){
                    subject_education = "Graduate";
                    showToast("Graduate");
                }
                break;
            case R.id.radioButton_Post_Graduate:
                if(checked){
                    subject_education = "Post graduate";
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
                    subject_employment_status = "Unemployed";
                    showToast("Unemployed");
                }
                break;
            case R.id.radioButton_self_employed:
                if(checked){
                    subject_employment_status = "Self Employed";
                    showToast("Self Employed");
                }
                break;
            case R.id.radioButton_Govt_Pvt_Service:
                if(checked){
                    subject_employment_status = "Govt./Pvt Service";
                    showToast("Govt./Pvt Service");
                }
                break;
            case R.id.radioButton_Retired:
                if(checked){
                    subject_employment_status = "Retired";
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
                    subject_smoking_status = "Non Smoker";
                    showToast("Non Smoker");
                }
                break;
            case R.id.radioButton_Occasional_smoker:
                if(checked){
                    subject_smoking_status = "Occasional Smoker";
                    showToast("Occasional Smoker");
                }
                break;
            case R.id.radioButton_Smoker:
                if(checked){
                    subject_smoking_status = "Smoker";
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
                    subject_alcohol_consumption = "Non Alcoholic";
                    showToast("Non Alcoholic");
                }
                break;
            case R.id.radioButton_Occasional_drinker:
                if(checked){
                    subject_alcohol_consumption = "Occasional Alcoholic";
                    showToast("Occasional Drinker");
                }
                break;
            case R.id.radioButton_Alcoholic:
                if(checked){
                    subject_alcohol_consumption = "Alcoholic";
                    showToast("Alcoholic");
                }
                break;
        }
    }

    public void showToast(String msg)
    {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        //Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    class saveData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            User user = new User();
            user.setSubjectID(subject_id);
            user.setSubjectData(subject_all_data);

            // add data to database
            DataBaseClient.getInstance(getApplicationContext()).getAppDataBase().userDao().insert(user);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast("Saved Successfully");
                }
            });
        }
    }
}
