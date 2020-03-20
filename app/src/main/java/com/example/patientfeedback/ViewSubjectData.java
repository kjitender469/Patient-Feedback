package com.example.patientfeedback;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewSubjectData extends AppCompatActivity implements AsyncResponse{

    Button bt_list_view;
    ListView lv_subject_data;

    /*
     * These text view ids are for viewing subject complete info in a seprate layout
     * */
    TextView tv_subject_initial, tv_subject_id, tv_subject_dob, tv_subject_gender, tv_subject_education, tv_subject_employment_status,
            tv_subject_smoking_status, tv_subject_alcohol_consumption, tv_subject_family_members, tv_subject_primary_caregiver;
    Button bt_back;

    AsyncResponse asyncResponse = null;

    int uid_global[];
    String subjectID[];
    String completeDataFrom_DB[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject_data);

        bt_list_view = findViewById(R.id.button_lv);
        lv_subject_data = findViewById(R.id.listView_SubjectData);


        asyncResponse = this;
        new getSavedData().execute();

        // going back to the main activity i.e. Patient Information
        bt_list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });
    }

    @Override
    public void processFinish(List<User> users) {
        // here we will receiving the data from background thread onPostExecute() method in our main UI thread
        subjectID = new String[users.size()];
        uid_global = new int[users.size()];
        completeDataFrom_DB = new String[users.size()];
        for(int i = 0; i < users.size(); i++){
            uid_global[i] = users.get(i).uid;
            subjectID[i] = "Subject ID : "+users.get(i).getSubjectID();
            completeDataFrom_DB[i] = users.get(i).getSubjectData();
        }

        // populate the subject IDs in a list  view
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, subjectID);
        lv_subject_data.setAdapter(arrayAdapter);

        lv_subject_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateDeleteDialogBox(Math.toIntExact(parent.getItemIdAtPosition(position)));
            }
        });
    }

    private void updateDeleteDialogBox(int id) {
        final int position = id;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewSubjectData.this);
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want View or Delete entry?")
                .setCancelable(false)
                .setPositiveButton("View", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String subjectData = subjectDataByUID(position);
                        updateTextViews(subjectData);
                    }
                })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Delete the specific entry from database
                                        User user = new User();
                                        user.uid = uid_global[position];
                                        DataBaseClient.getInstance(getApplicationContext()).getAppDataBase().userDao().delete(user);
                                    }
                                }).start();
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    private String subjectDataByUID(int index) {
        String subjectInfo = completeDataFrom_DB[index];
        return subjectInfo;
    }

    private void updateTextViews(String data) {

        setContentView(R.layout.subject_complete_info);
        /*
         * These text view ids are for viewing subject complete info in a seprate layout
         * */
        tv_subject_initial = findViewById(R.id.textView_sub_initial);
        tv_subject_id = findViewById(R.id.textView_sub_id);
        tv_subject_dob = findViewById(R.id.textView_sub_dob);
        tv_subject_gender = findViewById(R.id.textView_sub_gender);
        tv_subject_education = findViewById(R.id.textView_sub_education);
        tv_subject_employment_status = findViewById(R.id.textView_sub_employment_status);
        tv_subject_smoking_status = findViewById(R.id.textView_sub_smoking_status);
        tv_subject_alcohol_consumption = findViewById(R.id.textView_sub_alcohol_consumption);
        tv_subject_family_members = findViewById(R.id.textView_sub_family_members);
        tv_subject_primary_caregiver = findViewById(R.id.textView_sub_primary_caregiver);

        bt_back = findViewById(R.id.button_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_view_subject_data);
                startActivity(getIntent());
                finish();
            }
        });

        String subjectData = data;

        Log.wtf("debug","debug complete data string is : "+subjectData);
        String subString = subjectData.substring(1);
        int indexOfComma = subString.indexOf(',');
        Log.wtf("debug","debug index of comma is : "+indexOfComma);
        String sub_initial = subString.substring(0,indexOfComma);
        Log.wtf("debug","debug sub string is : "+sub_initial);
        String subString_2 = subString.substring(indexOfComma+1);
        Log.wtf("debug","debug sub string_2 is : "+subString_2);
        indexOfComma = subString_2.indexOf(',');
        String sub_id = subString_2.substring(0,indexOfComma);

        String subString_3 = subString_2.substring(indexOfComma+1);
        indexOfComma = subString_3.indexOf(',');
        String sub_dob = subString_3.substring(0,indexOfComma);

        String subString_4 = subString_3.substring(indexOfComma+1);
        indexOfComma = subString_4.indexOf(',');
        String sub_gender = subString_4.substring(0,indexOfComma);

        String subString_5 = subString_4.substring(indexOfComma+1);
        indexOfComma = subString_5.indexOf(',');
        String sub_education = subString_5.substring(0,indexOfComma);

        String subString_6 = subString_5.substring(indexOfComma+1);
        indexOfComma = subString_6.indexOf(',');
        String sub_employment_status = subString_6.substring(0,indexOfComma);

        String subString_7 = subString_6.substring(indexOfComma+1);
        indexOfComma = subString_7.indexOf(',');
        String sub_smoking_status = subString_7.substring(0,indexOfComma);

        String subString_8 = subString_7.substring(indexOfComma+1);
        indexOfComma = subString_8.indexOf(',');
        String sub_alcohol_consumption = subString_8.substring(0,indexOfComma);

        String subString_9 = subString_8.substring(indexOfComma+1);
        indexOfComma = subString_9.indexOf(',');
        String sub_family_members = subString_9.substring(0,indexOfComma);

        String subString_10 = subString_9.substring(indexOfComma+1);
        indexOfComma = subString_10.indexOf(',');
        String sub_primary_caregiver = subString_10.substring(0, indexOfComma);

        tv_subject_initial.setText(": "+sub_initial);
        //tv_subject_initial.setText(R.string.prefix_colon+sub_initial);
        tv_subject_id.setText(": "+sub_id);
        tv_subject_dob.setText(": "+sub_dob);
        tv_subject_gender.setText(": "+sub_gender);
        tv_subject_education.setText(": "+sub_education);
        tv_subject_employment_status.setText(": "+sub_employment_status);
        tv_subject_smoking_status.setText(": "+sub_smoking_status);
        tv_subject_alcohol_consumption.setText(": "+sub_alcohol_consumption);
        tv_subject_family_members.setText(": "+sub_family_members);
        tv_subject_primary_caregiver.setText(": "+sub_primary_caregiver);
    }

    class getSavedData extends AsyncTask<Void, Void, List<User>>{

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> subjectData = DataBaseClient.getInstance(getApplicationContext()).getAppDataBase().userDao().getAllData();
            return subjectData;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            processFinish(users);
            String data[] = new String[users.size()];
            String dataIndex[] = new String[users.size()];
            for(int i=0; i<users.size(); i++)
            {
                dataIndex[i] = String.valueOf(users.get(i).uid);
//                Log.wtf("debug","debug dataIndex is : "+users.get(i).uid);
//                Log.wtf("debug","debug subject ID is : "+users.get(i).getSubjectID());
                data[i] = users.get(i).getSubjectData();
            }
        }
    }

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
