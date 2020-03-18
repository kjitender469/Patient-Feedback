package com.example.patientfeedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewSubjectData extends AppCompatActivity {

    Button bt_list_view;
    ListView lv_subject_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject_data);

        bt_list_view = findViewById(R.id.button_lv);
        lv_subject_data = findViewById(R.id.listView_SubjectData);
        new getSavedData().execute();

        bt_list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });
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

            String data[] = new String[users.size()];
            String dataIndex[] = new String[users.size()];
            for(int i=0; i<users.size(); i++)
            {
                dataIndex[i] = String.valueOf(users.get(i).uid);
                Log.wtf("debug","debug dataIndex is : "+users.get(i).uid);
                Log.wtf("debug","debug subject ID is : "+users.get(i).getSubjectID());
                data[i] = users.get(i).getSubjectData();
            }

            ArrayList<String> arrayList = new ArrayList<>();
            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, data);
            lv_subject_data.setAdapter(arrayAdapter);
        }
    }
}
