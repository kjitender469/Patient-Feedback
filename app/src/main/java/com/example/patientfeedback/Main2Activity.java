package com.example.patientfeedback;

/*
* Patient Information
* */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements AsyncResponse{

    EditText et_subject_initial, et_subject_id, et_dob, et_members, et_caregiver;
    RadioButton rb_male, rb_female, rb_illiterate, rb_primary, rb_secondary, rb_graduate, rb_post_graduate, rb_unemployed, rb_self_employed,
                rb_retired, rb_govt_pvt_service, rb_non_smoker, rb_occasional_smoker, rb_smoker, rb_non_alcoholic, rb_occasional_drinker,
                rb_alcoholic;
    Button bt_next, bt_save, bt_submit, bt_list_view;

    String subject_initial, subject_id, subject_dob, subject_family_members, subject_caregiver, subject_gender, subject_education,
            subject_employment_status, subject_smoking_status, subject_alcohol_consumption;

    String subject_all_data = "";

    List<String> subjectID_FromDB_List;

    int subjectID_count;
    boolean isDatabaseEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        //setActionBar(myToolbar);

        et_subject_initial = (EditText) findViewById(R.id.edit_text_subject_initial);
        //et_subject_id = (EditText) findViewById(R.id.edit_text_subject_id);
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

        //bt_next = (Button)findViewById(R.id.button_next);
        bt_save = (Button)findViewById(R.id.button_save);
        //bt_submit = (Button)findViewById(R.id.button_submit);

        new GetDataFromDB().execute();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    // Dialog box for confirmation
                    openDialogBox();
                }
            }
        });

//        bt_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        bt_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //setContentView(R.layout.subject_complete_info);
//                User user = new User();
//                ExportDataExcel exportDataExcel = new ExportDataExcel(user, getApplicationContext());
//                exportDataExcel.exportExcelFile();
//                //exportDataExcel.saveToExternalStorage("");
//            }
//        });
    }

    private void openDialogBox() {
        // get alert_dialog.xml view
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity.this);
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to save this entry?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        getSubjectInfo();
                        new saveData().execute();
                    }
                })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //showToast("Delete Pressed");
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    private void getSubjectID_FromDB() {
        //new GetDataFromDB().execute();
    }


    public void getSubjectInfo(){
        subject_initial = et_subject_initial.getText().toString();
        //subject_id = et_subject_id.getText().toString();
        //Log.wtf("Debug","This log info is just to provide some delay --> subject_initial is : "+subject_initial);
        subject_dob = et_dob.getText().toString();
        //Log.wtf("Debug","This log info is just to provide some delay --> subject_dob is : "+subject_dob);
        subject_family_members = et_members.getText().toString();
        //Log.wtf("Debug","This log info is just to provide some delay --> subject_family is : "+subject_family_members);
        subject_caregiver = et_caregiver.getText().toString();
        //Log.wtf("Debug","This log info is just to provide some delay --> subject_caregiver is : "+subject_caregiver);
        //Log.wtf("debug","debug App subject_id before ID count : "+subject_id);
        //Log.wtf("debug","debug App subjectID_count before ID count : "+subjectID_count);
        subject_id = String.valueOf(subjectID_count+1);
        //Log.wtf("debug","debug App subject_id after ID count : "+subject_id);

        Date date = Calendar.getInstance().getTime();
        //Log.wtf("debug","debug Date : "+date);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(date);
        //Log.wtf("debug","debug Date Formatted : "+formattedDate);

        int index;
        index = formattedDate.indexOf('-');
        String startDate_date = formattedDate.substring(0, index);

        String sub_string = formattedDate.substring(index+1);
        //Log.wtf("Debug","debug App download entries sub_string : "+sub_string);
        index = sub_string.indexOf('-');
        String startDate_month = sub_string.substring(0, index);
        String startDate_year = sub_string.substring(index + 1);

        if(startDate_date.length() == 1)
            startDate_date = "0"+startDate_date;
        if(startDate_month.length() == 1)
            startDate_month = "0"+startDate_month;

        String date_final = startDate_date+startDate_month+startDate_year+"*";

        //Log.wtf("Debug","debug App download entries date : "+startDate_date);
        //Log.wtf("Debug","debug App download entries month : "+startDate_month);
        //Log.wtf("Debug","debug App download entries year : "+startDate_year);

        subject_all_data = date_final+","+subject_id+","+subject_initial+","+subject_dob+","+subject_gender+","+subject_education+","
                +subject_employment_status+","+subject_smoking_status+","+subject_alcohol_consumption+","+subject_family_members
                +","+subject_caregiver+",";
        //showToast(subject_final_data);
        //data_array = new String[]{subject_initial, subject_id, subject_dob, subject_family_members, subject_caregiver};
    }

    private boolean validate() {
        if(et_subject_initial.getText().toString().trim().isEmpty()){
            et_subject_initial.setError("This field can not be empty");
            et_subject_initial.requestFocus();
            return false;
        }
//        else if(et_subject_id.getText().toString().trim().isEmpty()){
//            et_subject_id.setError("This field can not be empty");
//            et_subject_id.requestFocus();
//            return false;
//        }
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
            return true;
        }
        if(id == R.id.clear_all_data)
        {
            openDialogClearAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDialogClearAll() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity.this);
        // set dialog message
        alertDialogBuilder
                .setMessage("Clear all data?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // clear alldatabase
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DataBaseClient.getInstance(getApplicationContext()).getAppDataBase().userDao().deleteAllData();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showToast("Cleared Successfully");
                                    }
                                });
                            }
                        }).start();
                    }
                })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //showToast("Delete Pressed");
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
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
                    subject_alcohol_consumption = "Occasional Drinker";
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
    public void processFinish(List<User> users) {
        String data = users.get(users.size()-1).getSubjectData();
        data = data.substring(data.indexOf('*')+1);
        String sub_string = data.substring(1);
        int commaIndex = sub_string.indexOf(',');
//        Log.wtf("debug","debug App : "+data);
//        Log.wtf("debug","debug App sub_string : "+sub_string);
//        Log.wtf("debug","debug App data length : "+data.length());
//        Log.wtf("debug","debug App commaIndex : "+commaIndex);
        //Log.wtf("debug","debug App sub string is : "+data.substring(1, commaIndex));

        subjectID_count = Integer.parseInt(sub_string.substring(0,commaIndex));
        //Log.wtf("debug","debug App ID count : "+subjectID_count);
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
                    startActivity(getIntent());
                    finish();
                }
            });
        }
    }

    class GetDataFromDB extends AsyncTask<Void, Void, List<User>>{

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> subjectID = DataBaseClient.getInstance(getApplicationContext()).getAppDataBase().userDao().getAllData();
            return subjectID;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            if(users.size() != 0) {
                isDatabaseEmpty = false;
                //Log.wtf("debug","debug App (inside if) : "+users.size());
                processFinish(users);
            }
            else{
                isDatabaseEmpty = true;
                //Log.wtf("debug","debug App (inside else) : "+users.size());
            }
        }
    }
}
