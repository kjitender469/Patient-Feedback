package com.example.patientfeedback;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import java.util.Date;
import java.util.List;

public class ExportDataExcel extends AsyncTask<Void, Void, Void> {

    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    User user;
    Context context;
    boolean permission;

    List<String> key, value;
    int size;

    public ExportDataExcel(User user, Context context) {
        this.user = user;
        this.context = context;
    }

    public ExportDataExcel(List<String> key, List<String> value, int size, Context context) {
        this.key = key;
        this.value = value;
        this.context = context;
        this.size = size;
    }

    public void exportExcelFile()
    {
        permission = false;

        checkPermission();
        //saveToExternalStorage("");
    }

    private boolean checkForExternalStoragePermission() {
//        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//            Toast.makeText(context, "Permission Not Granted", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else{
//            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
//            return true;
//        }

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(context,
//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        } else {
            // Permission has already been granted
            return true;
        }
        return true;
    }

public void checkPermission(){
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            //Toast.makeText(context,"Permission Granted ", Toast.LENGTH_SHORT).show();
            //writeDataInExcelFile();
            saveToExternalStorage("");
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(context,"Permission Denied", Toast.LENGTH_SHORT).show();
        }
    };
    TedPermission.with(context).setPermissionListener(permissionListener).setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).check();
}

    private void writeDataInExcelFile() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet("Sheet No 1");
        //HSSFSheet secondSheet = workbook.createSheet("Sheet No 2");

        HSSFRow rowA = firstSheet.createRow(0);
        HSSFCell cellA = rowA.createCell(0);
        cellA.setCellValue(new HSSFRichTextString("Sheet One"));

        HSSFRow row1 = firstSheet.createRow(0);
        HSSFCell cell1 = row1.createCell(1);
        HSSFCell cell2 = row1.createCell(2);
        cell1.setCellValue(new HSSFRichTextString("Col 1"));
        cell2.setCellValue(new HSSFRichTextString("Col 2"));

        for(int i=0;i<size;i++) {
            HSSFCell cell = row1.createCell(i);
            cell.setCellValue(new HSSFRichTextString("Col " + i));


//            HSSFRow rowB = secondSheet.createRow(0);
//            HSSFCell cellB = rowB.createCell(0);
//            cellB.setCellValue(new HSSFRichTextString("Sheet two"));
            FileOutputStream fos = null;
            try {
                String str_path = Environment.getExternalStorageDirectory().getAbsolutePath();
                Log.wtf("debug", "debug App excel file path is : " + str_path);
                File file;
                file = new File(str_path+File.separator+"Patient feedback", getString(R.string.app_name) + ".xls");
                file.mkdirs();
                Log.wtf("debug", "debug App excel file 2 path is : " + file.getAbsolutePath());
                //Log.wtf("debug", "debug App excel file 4 path is : " + file.toString());
                file = new File(str_path+"/Patient feedback", getString(R.string.app_name) + ".xls");
                fos = new FileOutputStream(file);
                Log.wtf("debug", "debug App excel file 3 path is : " + fos.toString());
                workbook.write(fos);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                        //Log.wtf("debug", "debug App excel file path is : Generated");
                        Toast.makeText(context, "Excel Sheet Generated", Toast.LENGTH_SHORT).show();
                        permission = false;
                    } catch (IOException e) {
                        //Log.wtf("debug", "debug App excel file path is : Exception :" + e);
                        //Toast.makeText(context, ""+(String)e.printStackTrace(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    //Log.wtf("debug", "debug App excel file path is : else");
                }
                //Toast.makeText(context, "Excel Sheet Generated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToExternalStorage(String rec_data) {
        //String dbData = "**";
        if(!isExternalStorageWritable()) {
            Toast.makeText(context,"Storage is not available",Toast.LENGTH_SHORT).show();
            return;
        }
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PatientFeedback");
        if (!root.exists()) {
            boolean a =root.mkdirs();
            if(ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
        //List<EV_User> myEV_users = MainActivity.ev_database.ev_dao().getParametrs();
        String fileName = value.get(0)+"_";
        String timeStamp = DateFormat.getDateTimeInstance().format(new Date());
        File file = new File(root,fileName+timeStamp+".xls");

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet("Sheet 1");

        HSSFRow row1 = firstSheet.createRow(0);
        HSSFRow row2 = firstSheet.createRow(1);

        /*
        * Make entries in Excel file here
        * */
        for(int i=0;i<size;i++) {
            HSSFCell cell = row1.createCell(i);
            HSSFCell cell_r2 = row2.createCell(i);
            cell.setCellValue(new HSSFRichTextString(key.get(i)));
            cell_r2.setCellValue(new HSSFRichTextString(value.get(i)));
        }

        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
//            fileOutputStream.write(rec_data.getBytes());
//            fileOutputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            //fileOutputStream.write(rec_data.getBytes());
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            Toast.makeText(context,"File Exported",Toast.LENGTH_SHORT).show();
            //dbData="";
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context,"File not found",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"Error Exporting File",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Exception Occurred",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private String getString(int app_name) {
        return "PatientFile";
    }


    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}

