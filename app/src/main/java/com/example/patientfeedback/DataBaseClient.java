package com.example.patientfeedback;

import android.content.Context;

import androidx.room.Room;

public class DataBaseClient {

    private AppDataBase appDataBase;

    private static DataBaseClient INSTANCE;

    private Context context;

    public DataBaseClient(Context context) {
        this.context = context;
        appDataBase = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "subject_data_table").build();
    }

    public static synchronized DataBaseClient getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new DataBaseClient(context);
        }
        return INSTANCE;
    }

    public AppDataBase getAppDataBase(){
        return appDataBase;
    }
}
