package com.example.patientfeedback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataViewHolder> {

    List<String> subjectID;
    List<String> subjectCompleteInfo;
    List<String> subjectSpecificData;
    List<String> key_list;
    int[] uid;
    Context context;

    boolean flag = false;

    public RecyclerViewAdapter(List<String> subjectID, List<String> subjectCompleteInfo, int[] uid, Context context) {
        this.subjectID = subjectID;
        this.subjectCompleteInfo = subjectCompleteInfo;
        this.uid = uid;
        this.context = context;
        flag = false;
    }

    public RecyclerViewAdapter(List<String> subjectSpecificData, List<String> key_list, Context context) {
        this.subjectSpecificData = subjectSpecificData;
        this.context = context;
        this.key_list = key_list;
        flag = true;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if(flag){
            view = layoutInflater.inflate(R.layout.item_list_subject_specific, parent, false);
        }
        else {
            view = layoutInflater.inflate(R.layout.recycle_view_item_list_layout, parent, false);
        }
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if(flag){
            String data = subjectSpecificData.get(position);
            String key = key_list.get(position);
            holder.tv_subjectSpecific.setText(": "+data);
            holder.tv_key_list.setText(key);
        }
        else {
            String data = subjectID.get(position);
            holder.tv.setText(data);

            holder.imageButton_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setMessage("Are you sure?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    holder.deleteItemAndUpdateList(position);
                                }
                            })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
            });
            holder.imageButton_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Sending Data "+dataList.get(position),Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(context.getApplicationContext(), SubjectSpecificInfo.class);

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
                    List<String> value = new ArrayList<>();
                    String subjectData = subjectCompleteInfo.get(position);
                    subjectData = subjectData.substring(subjectData.indexOf('*')+1);
                    Log.wtf("debug","debug App subjectData (Original) is : "+subjectData);

                    String subString;
                    String data;
                    int indexOfComma = subjectData.indexOf(',');
                    Log.wtf("debug","debug App indexOfComma initial is : "+indexOfComma);
                    for(int i = 0; i < key.size(); i++){
                        Log.wtf("debug","debug App subjectData is (start) : "+subjectData);
                        subString = subjectData.substring(indexOfComma+1);
                        Log.wtf("debug","debug App sub_String is : "+subString+"  --> value of i : "+i);
                        indexOfComma = subString.indexOf(',');
                        Log.wtf("debug","debug App indexOfComma is : "+indexOfComma);
                        //Log.wtf("debug", MessageFormat.format("debug App indexOfComma %d is : {0}", indexOfComma));
                        data = subString.substring(0,indexOfComma);
                        Log.wtf("debug","debug App data is : "+data+"  --> value of i : "+i);
                        subjectData = subString;
                        //Log.wtf("debug","debug App subjectData is (end) : "+subjectData);
                        Log.wtf("debug","debug App *****************************************************************************************");
                        value.add(data);
                    }

                    //Log.wtf("debug","debug complete data string is : "+subjectData);

//                    String subString = subjectData.substring(1);
//
//                    int indexOfComma = subString.indexOf(',');
//                    //Log.wtf("debug","debug index of comma is : "+indexOfComma);
//                    String sub_initial = "Initial : "+subString.substring(0,indexOfComma);
//                    key.add("Initial");
//                    value.add(sub_initial);
//                    //Log.wtf("debug","debug sub string is : "+sub_initial);
//
//
//                    String subString_2 = subString.substring(indexOfComma+1);
//                    //Log.wtf("debug","debug sub string_2 is : "+subString_2);
//                    indexOfComma = subString_2.indexOf(',');
//                    String sub_id = "ID : "+subString_2.substring(0,indexOfComma);
//                    key.add("ID");
//                    value.add(sub_id);
//
//                    String subString_3 = subString_2.substring(indexOfComma+1);
//                    indexOfComma = subString_3.indexOf(',');
//                    String sub_dob = "DOB : "+subString_3.substring(0,indexOfComma);
//                    key.add("DOB");
//                    value.add(sub_dob);
//
//                    String subString_4 = subString_3.substring(indexOfComma+1);
//                    indexOfComma = subString_4.indexOf(',');
//                    String sub_gender = "Gender : "+subString_4.substring(0,indexOfComma);
//                    key.add("Gender");
//                    value.add(sub_gender);
//
//                    String subString_5 = subString_4.substring(indexOfComma+1);
//                    indexOfComma = subString_5.indexOf(',');
//                    String sub_education = "Education : "+subString_5.substring(0,indexOfComma);
//                    key.add("Education");
//                    value.add(sub_education);
//
//                    String subString_6 = subString_5.substring(indexOfComma+1);
//                    indexOfComma = subString_6.indexOf(',');
//                    String sub_employment_status = "Employment Status : "+subString_6.substring(0,indexOfComma);
//                    key.add("Employment");
//                    value.add(sub_employment_status);
//
//                    String subString_7 = subString_6.substring(indexOfComma+1);
//                    indexOfComma = subString_7.indexOf(',');
//                    String sub_smoking_status = "Smoking Status : "+subString_7.substring(0,indexOfComma);
//                    key.add("Smoking");
//                    value.add(sub_smoking_status);
//
//                    String subString_8 = subString_7.substring(indexOfComma+1);
//                    indexOfComma = subString_8.indexOf(',');
//                    String sub_alcohol_consumption = "Alcohol Consumption : "+subString_8.substring(0,indexOfComma);
//                    key.add("Alcohol");
//                    value.add(sub_alcohol_consumption);
//
//                    String subString_9 = subString_8.substring(indexOfComma+1);
//                    indexOfComma = subString_9.indexOf(',');
//                    String sub_family_members = "Family Members : "+subString_9.substring(0,indexOfComma);
//                    key.add("Family");
//                    value.add(sub_family_members);
//
//                    String subString_10 = subString_9.substring(indexOfComma+1);
//                    indexOfComma = subString_10.indexOf(',');
//                    String sub_primary_caregiver = "Primary Caregiver : "+subString_10.substring(0, indexOfComma);
//                    key.add("Caregiver");
//                    value.add(sub_primary_caregiver);
//                    for(int i=0; i<key.size(); i++){
//                        //intent.putExtra("Subject")
//                    }
                    intent.putStringArrayListExtra("SubjectInfo", (ArrayList<String>) value);
                    intent.putStringArrayListExtra("fields", (ArrayList<String>) key);
                    context.startActivity(intent);
                }
            });
            holder.imageButton_download.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Exporting...",Toast.LENGTH_SHORT).show();
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
                    List<String> value = new ArrayList<>();
                    String subjectData = subjectCompleteInfo.get(position);
                    Log.wtf("debug","debug App subjectData (Original) is : "+subjectData);

                    String subString;
                    String data;
                    int indexOfComma = subjectData.indexOf(',');
                    Log.wtf("debug","debug App indexOfComma initial is : "+indexOfComma);
                    for(int i = 0; i < key.size(); i++){
                        Log.wtf("debug","debug App subjectData is (start) : "+subjectData);
                        subString = subjectData.substring(indexOfComma+1);
                        Log.wtf("debug","debug App sub_String is : "+subString+"  --> value of i : "+i);
                        indexOfComma = subString.indexOf(',');
                        Log.wtf("debug","debug App indexOfComma is : "+indexOfComma);
                        //Log.wtf("debug", MessageFormat.format("debug App indexOfComma %d is : {0}", indexOfComma));
                        data = subString.substring(0,indexOfComma);
                        Log.wtf("debug","debug App data is : "+data+"  --> value of i : "+i);
                        subjectData = subString;
                        //Log.wtf("debug","debug App subjectData is (end) : "+subjectData);
                        Log.wtf("debug","debug App *****************************************************************************************");
                        value.add(data);
                    }

                    ExportDataExcel exportDataExcel = new ExportDataExcel(key, value, key.size(), context);
                    exportDataExcel.checkPermission();
                    holder.imageButton_download.setImageResource(R.drawable.ic_file_download_black_24dp);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(flag){
            return subjectSpecificData.size();
        }
        else {
            return subjectID.size();
        }
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{
        TextView tv, tv_subjectSpecific, tv_key_list;
        ImageButton imageButton_delete, imageButton_info, imageButton_download;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            if(flag){
                tv_subjectSpecific = itemView.findViewById(R.id.textView_subjectSpecific);
                tv_key_list = itemView.findViewById(R.id.textView_key_list);
            }
            else {
                tv = itemView.findViewById(R.id.textView);
                imageButton_delete = itemView.findViewById(R.id.imageButton_delete);
                imageButton_info = itemView.findViewById(R.id.imageButton_info);
                imageButton_download = itemView.findViewById(R.id.imageButton_export);
                imageButton_delete.setBackgroundColor(0);
                imageButton_info.setBackgroundColor(0);
                imageButton_download.setBackgroundColor(0);
            }
        }

        public void deleteItemAndUpdateList(int position) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    User user = new User();
                    user.uid = uid[position];
                    DataBaseClient.getInstance(context).getAppDataBase().userDao().delete(user);
                }
            }).start();

            int adapterPosition = getAdapterPosition();
            subjectID.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
        }

        private void changeDrawableIco(){
            imageButton_delete.setImageResource(R.drawable.ic_file_2_download_black_24dp);
        }
    }
}
