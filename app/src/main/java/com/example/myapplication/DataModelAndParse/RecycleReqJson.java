package com.example.myapplication.DataModelAndParse;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class RecycleReqJson {
    RecycleRequest recycleReq;
    EditText fio;
    EditText phone;
    EditText address;
    EditText weight;
    Spinner deli;
    Spinner pay;
    CheckBox recyclables;


    public RecycleReqJson(){ };

//    public JSONObject buidJsonObject() throws JSONException {
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.accumulate("name", recycleReq.getName().toString());
//        jsonObject.accumulate("country",  recycleReq.getAddress().toString());
//        jsonObject.accumulate("twitter",  recycleReq.getPhone().toString());
//
//        return jsonObject;
//    }
//
//
//
//    public void setPostRequestContent(HttpURLConnection conn) throws IOException, JSONException {
//
//        OutputStream os = conn.getOutputStream();
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//        writer.write(buidJsonObject().toString());
//
//        writer.flush();
//        writer.close();
//        os.close();
//    }
}
