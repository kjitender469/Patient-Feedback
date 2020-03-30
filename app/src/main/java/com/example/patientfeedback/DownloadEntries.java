package com.example.patientfeedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DownloadEntries extends AppCompatActivity implements AsyncResponse{

    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    boolean flag = false;
    boolean valid = false;
    boolean startDateFlag = false;
    boolean endDateFlag = false;

    EditText et_startDate, et_endDate;
    Button bt_export;

    String startDate_date, startDate_month, startDate_year;
    String endDate_date, endDate_month, endDate_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_entries);

        et_startDate = findViewById(R.id.editText_startDate);
        et_endDate = findViewById(R.id.editText_endDate);

        bt_export = findViewById(R.id.button_export);
        /*
        String data = "12121992*";
        int index;
        index = data.indexOf("*");
        String sub_string = data.substring(0,index);
        String date, month, year;
        date = sub_string.substring(0,2);
        month = sub_string.substring(2,4);
        year = sub_string.substring(4,8);
        Log.wtf("Debug","debug App download entries date : "+date);
        Log.wtf("Debug","debug App download entries month : "+month);
        Log.wtf("Debug","debug App download entries year : "+year);

         */

        bt_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromEditText();
                if(valid){
                    //Toast.makeText(getApplicationContext(),"Permission Granted ", Toast.LENGTH_SHORT).show();
                    new GetDataFromDatabase().execute();
                }
                else{
//                    if(!startDateFlag)
//                        Toast.makeText(getApplicationContext(),"Incorrect Starting Date",Toast.LENGTH_SHORT).show();
//                    if(!endDateFlag)
//                        Toast.makeText(getApplicationContext(),"Incorrect Ending Date",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void processFinish(List<User> users) {
        boolean startDateNotFound = true;
        boolean endDateNotFound = true;
        boolean randomFlag = false;

        int index = 0;
        List<String> value = new ArrayList<>();
        String date = "", month = "", year = "";

        /*
        * This msg[] array is for testing purpose. For testing atleast one entry should be in database.
        * change for loop condition from users.size() --> msg.length
        * */
//        String [] msg = {"27032020*,4,Jitu 1,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "27032020*,4,Jitu 2,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "28032020*,4,Jitu 3,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "28032020*,4,Jitu 4,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "29032020*,4,Jitu 5,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "29032020*,4,Jitu 6,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "30032020*,4,Jitu 7,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "31032020*,4,Jitu 8,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "31032020*,4,Jitu 9,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "01042020*,4,Jitu 10,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "01042020*,4,Jitu 11,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "02042020*,4,Jitu 12,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "02042020*,4,Jitu 13,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//
//                        "05012021*,4,Jitu 14,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "12042021*,4,Jitu 15,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "12042021*,4,Jitu 16,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,",
//                        "02052021*,4,Jitu 17,12/85/1486,F,Secondary,Self Employed,Occasional Smoker,Occasional Drinker,7,new random,"};
//        String data = "";

        for(int i = 0; i < users.size(); i++){
            String data = users.get(i).getSubjectData();

            //data = msg[i];
            Log.wtf("Debug","debug App download entries comlete data from DB : "+data);
            index = data.indexOf("*");
            String sub_string = data.substring(0,index);
            date = sub_string.substring(0,2);
            month = sub_string.substring(2,4);
            year = sub_string.substring(4,8);
            getDataFromEditText();

            Log.wtf("Debug","debug App download entries date : "+date);
            Log.wtf("Debug","debug App download entries month : "+month);
            Log.wtf("Debug","debug App download entries year : "+year);

            Log.wtf("Debug","debug App download entries startDate_date : "+startDate_date);
            Log.wtf("Debug","debug App download entries startDate_month : "+startDate_month);
            Log.wtf("Debug","debug App download entries startDate_year : "+startDate_year);

            Log.wtf("Debug","debug App download entries endDate_date : "+endDate_date);
            Log.wtf("Debug","debug App download entries endDate_month : "+endDate_month);
            Log.wtf("Debug","debug App download entries endDate_year : "+endDate_year);

            /*
            * This if condition ensure that all the data before the startDate (given y user) will be ignore and
            * not added into value variable of type list.
            * */
            if(startDate_date.equals(date)  &&  startDate_month.equals(month)  && startDate_year.equals(year)){
                flag = true;
                //Log.wtf("Debug","debug App download entries date ###########: "+startDate_date);
            }

            if(flag){
                // Export data in excel file
                //Log.wtf("Debug","debug App download entries date *&*&*&*&*&*&*&*&*: "+startDate_date);

                /*
                * This if-else and randomFlag is used to extract all the data upto the endDate_date provided by the user
                * */
                if(endDate_date.equals(date)  &&  endDate_month.equals(month)  && endDate_year.equals(year)){
                    //flag = true;
                    randomFlag = true;
                    value.add(data.substring(index+1));
                    //Log.wtf("Debug","debug App download entries date ###########: "+startDate_date);
                }
                else{
                    if(!randomFlag){
                        value.add(data.substring(index+1));
                    }
                }
            }

            /*
            * Serach for existance of start date and end date.
            * */
            String unformattedDate = date+month+year;
            String unformattedStartDate = startDate_date+startDate_month+startDate_year;
            String unformattedEndDate = endDate_date+endDate_month+endDate_year;

//            Log.wtf("Debug","debug App download entries unformattedDate : "+unformattedDate);
//            Log.wtf("Debug","debug App download entries unformattedStartDate : "+unformattedStartDate);
//            Log.wtf("Debug","debug App download entries unformattedEndDate : "+unformattedEndDate);

            if(unformattedStartDate.equals(unformattedDate)){
                startDateNotFound = false;
            }
            if(unformattedEndDate.equals(unformattedDate)){
                endDateNotFound = false;
            }
        }

        if(startDateNotFound){
            et_startDate.setError("No entry found for this date");
            et_startDate.requestFocus();
            //Toast.makeText(getApplicationContext(), "No entry found for start date", Toast.LENGTH_LONG).show();
            return;
        }
        if(endDateNotFound){
            et_endDate.setError("No entry found for this date");
            et_endDate.requestFocus();
            //Toast.makeText(getApplicationContext(), "No entry found for end date", Toast.LENGTH_LONG).show();
            return;
        }

        /*
        * If both Start Date and End Date found than only proceed to export data in excel file otherwise only pop up information toast.
        * */
        if(!startDateNotFound && !endDateNotFound)
            checkPermission(value);
    }

    private void getDataFromEditText() {
        String startDate = et_startDate.getText().toString().trim();
        String endDate = et_endDate.getText().toString().trim();
        if(startDate.length() == 0){
            et_startDate.setError("Empty Field");
            et_startDate.requestFocus();
            return;
        }
        if(endDate.length() == 0){
            et_endDate.setError("Empty Field");
            et_endDate.requestFocus();
            return;
        }

        //startDate = "1/8/1992";
        int index;
        int index_end;

        index = startDate.indexOf('/');
        index_end = startDate.indexOf('/');

        startDate_date = startDate.substring(0, index).trim();
        endDate_date = endDate.substring(0, index_end).trim();

        String sub_string = startDate.substring(index+1);
        String sub_string_end = endDate.substring(index+1);
        //Log.wtf("Debug","debug App download entries sub_string : "+sub_string);

        index = sub_string.indexOf('/');
        index_end = sub_string_end.indexOf('/');

        startDate_month = sub_string.substring(0, index).trim();
        endDate_month = sub_string_end.substring(0, index_end).trim();

        startDate_year = sub_string.substring(index+1).trim();
        endDate_year = sub_string_end.substring(index_end+1).trim();

        if(startDate_date.length() == 1)
            startDate_date = "0"+startDate_date;
        if(startDate_month.length() == 1)
            startDate_month = "0"+startDate_month;

        if(endDate_date.length() == 1)
            endDate_date = "0"+endDate_date;
        if(endDate_month.length() == 1)
            endDate_month = "0"+endDate_month;

//        Log.wtf("Debug","debug App download entries date : "+startDate_date);
//        Log.wtf("Debug","debug App download entries month : "+startDate_month);
//        Log.wtf("Debug","debug App download entries year : "+startDate_year);

        if(Integer.parseInt(startDate_date) > 0 && Integer.parseInt(startDate_date) <= 31 && Integer.parseInt(startDate_month) > 0 && Integer.parseInt(startDate_month) <= 12){
            startDateFlag = true;
        }
        else{
            startDateFlag = false;
            et_startDate.setError("Incorrect Date");
            et_startDate.requestFocus();
        }

        if(Integer.parseInt(endDate_date) > 0 && Integer.parseInt(endDate_date) <= 31 && Integer.parseInt(endDate_month) > 0 && Integer.parseInt(endDate_month) <= 12){
            endDateFlag = true;
        }
        else{
            et_endDate.setError("Incorrect Date");
            et_endDate.requestFocus();
            endDateFlag = false;
        }

        if(startDateFlag && endDateFlag)
            valid = true;
        else
            valid = false;
    }

    public void checkPermission(List<String> value){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplicationContext(),"Permission Granted ", Toast.LENGTH_SHORT).show();
                //writeDataInExcelFile();
                saveToExternalStorage(value);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(getApplicationContext()).setPermissionListener(permissionListener).setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).check();
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void saveToExternalStorage(List<String> stringList) {

        List<String> key = new ArrayList<>();
        key.add("ID");
        key.add("Initial");
        key.add("DOB");
        key.add("Gender");
        key.add("Education");
        key.add("Employment");
        key.add("Smoking");
        key.add("Alcohol");
        key.add("Family Member");
        key.add("Caregiver");

        if(!isExternalStorageWritable()) {
            Toast.makeText(getApplicationContext(),"Storage is not available",Toast.LENGTH_SHORT).show();
            return;
        }
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PatientFeedback");
        if (!root.exists()) {
            boolean a =root.mkdirs();
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getApplicationContext(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        String fileName = "Pat"+"_";
        String timeStamp = DateFormat.getDateTimeInstance().format(new Date());
        File file = new File(root,fileName+timeStamp+".xls");

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet("Sheet 1");

        HSSFRow row1 = firstSheet.createRow(0);
        //HSSFRow row2 = firstSheet.createRow(1);

        /*
         * Make entries for first Row i.e. subject's related parameter names.
         * */
        for(int i=0;i<key.size();i++) {
            HSSFCell cell = row1.createCell(i);
            //HSSFRow row2 = firstSheet.createRow(i);
            //HSSFCell cell_r2 = row2.createCell(i);
            cell.setCellValue(new HSSFRichTextString(key.get(i)));
            //cell_r2.setCellValue(new HSSFRichTextString(value.get(i)));
        }

        /*
         * Make entries in Excel file here
         * */
        List<String> value = new ArrayList<>();
        String subjectData;
        String subString;
        String data;

        for(int k = 0; k < stringList.size(); k++){
            /*
             * Extract values from stringList for making entries in excel file
             * */
            subjectData = stringList.get(k);

            // remove all character from string upto the index of '*'. Original string will be something like --> ( 12062020*,5,Jitu,-----,Ashish, )
            subjectData = subjectData.substring(subjectData.indexOf('*')+1);

            int indexOfComma = subjectData.indexOf(',');
            for(int i = 0; i < key.size(); i++){
                //Log.wtf("debug","debug App subjectData is (start) : "+subjectData);
                subString = subjectData.substring(indexOfComma+1);
                //Log.wtf("debug","debug App sub_String is : "+subString+"  --> value of i : "+i);
                indexOfComma = subString.indexOf(',');
                //Log.wtf("debug","debug App indexOfComma is : "+indexOfComma);
                //Log.wtf("debug", MessageFormat.format("debug App indexOfComma %d is : {0}", indexOfComma));
                data = subString.substring(0,indexOfComma);
                //Log.wtf("debug","debug App data is : "+data+"  --> value of i : "+i);
                subjectData = subString;
                //Log.wtf("debug","debug App subjectData is (end) : "+subjectData);
                //Log.wtf("debug","debug App *****************************************************************************************");
                value.add(data);
            }
            //Log.wtf("debug","debug App *****************************************************************************************");

            HSSFRow row2 = firstSheet.createRow(k+1);
                for(int i = 0; i < value.size(); i++) {
                    //HSSFCell cell = row1.createCell(i);
                    HSSFCell cell_r2 = row2.createCell(i);
                    //cell.setCellValue(new HSSFRichTextString(key.get(i)));
                    cell_r2.setCellValue(new HSSFRichTextString(value.get(i)));
                }
            value.clear();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(),"File Exported",Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"File not found",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error Exporting File",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Exception Occurred",Toast.LENGTH_SHORT).show();
        }
    }

    class GetDataFromDatabase extends AsyncTask<Void, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> subjectData = DataBaseClient.getInstance(getApplicationContext()).getAppDataBase().userDao().getAllData();
            return subjectData;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            if(users.size() != 0) {
                processFinish(users);
            }
            else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"No Entry Found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}
