package com.example.myapplication.OneService;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.DataModelAndParse.DeliJsonParser;
import com.example.myapplication.DataModelAndParse.Delivery;
import com.example.myapplication.DataModelAndParse.PayJsonParser;
import com.example.myapplication.DataModelAndParse.PayWay;
import com.example.myapplication.DataModelAndParse.RecycleRequest;
import com.example.myapplication.DataModelAndParse.Service;
import com.example.myapplication.DataModelAndParse.ServiceJsonParser;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerAndFilter.DataModel;
import com.example.myapplication.RecyclerAndFilter.MyAdapter;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SendReqFragment extends Fragment {
    public static final String KEY = "text";
    public final static String TAG = "REQ";
    String id;
    CheckBox recyclables;
    MaterialButton send_req_btn;
    Spinner spinner_deli;
    Spinner spinner_pay;
    TextView pay_info;


    EditText fio;
    EditText phone;
    EditText address;
    EditText weight;
    String pay_text;
    String deli_text;
    TextView mes_info;

    RecycleRequest recycleReq;

    PayJsonParser payParser;
    ArrayList<PayWay> payWay;

    DeliJsonParser deliParser;
    ArrayList<Delivery> deli;


    private OnFragment1DataListener mListener;

    public static SendReqFragment newInstance() {
        return new SendReqFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.req_fragment, container, false);

//        mListener.JsonReq();

        try {
            getJSON("http://192.168.0.103:8081/mysqlapp/PayDeliServlet");
        }
        catch (Exception e){
        }



        id = getArguments().getString(KEY);


        recyclables = v.findViewById(R.id.recyclables);
        recyclables.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mListener.isChecked()){
                    Log.i("KEY", "CHECKED");
                }
            }
        });

        send_req_btn = v.findViewById(R.id.button_send_req);
        send_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendReq();
                //mListener.onSendReq();
            }
        });

        spinner_deli = v.findViewById(R.id.delivery_spin);

        spinner_pay = v.findViewById(R.id.pay_spin);
        //loadInSpinner(payWay,deli);



        pay_info = v.findViewById(R.id.pay_info);
        EditText weight = v.findViewById(R.id.weight_text);

        mListener.SetInfoPaymentText(weight, pay_info);


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment1DataListener) {
            mListener = (OnFragment1DataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }



    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loadInSpinner(payWay, deli);
                Log.i("PAYYYY", payWay.get(0).getWay());
                Log.i("DELI", deli.get(0).getType());
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    payParser = new PayJsonParser();
                    payWay = payParser.readJsonStream(con.getInputStream());

                    deliParser = new DeliJsonParser();
                    deli = deliParser.readJsonStream(con.getInputStream());

                    return "";

                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadInSpinner(ArrayList<PayWay> payWay, ArrayList<Delivery> deli){


        ArrayList<String> payModelList = new ArrayList<>();
        ArrayList<String> deliModelList = new ArrayList<>();
        for(int i = 0; i < payWay.size(); i++){
            String way = payWay.get(i).getWay();
            payModelList.add(way);
        }

        for(int i = 0; i < deli.size(); i++){
            String type = deli.get(i).getType();
            deliModelList.add(type);
        }

        ArrayAdapter<String> pay_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, payModelList);
        ArrayAdapter<String> deli_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, deliModelList);


        pay_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pay.setAdapter(pay_adapter);

        deli_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_deli.setAdapter(deli_adapter);

    }

    public void onSendReq(){
        fio = getView().findViewById(R.id.fio_text);
        phone = getView().findViewById(R.id.phone_text);
        address = getView().findViewById(R.id.address_text);
        weight = getView().findViewById(R.id.weight_text);
        spinner_deli = getView().findViewById(R.id.delivery_spin);
        spinner_pay = getView().findViewById(R.id.pay_spin);
        recyclables = getView().findViewById(R.id.recyclables);
        mes_info = getView().findViewById(R.id.mes_info_text);

        deli_text = spinner_deli.getSelectedItem().toString();
        pay_text = spinner_pay.getSelectedItem().toString();
        Log.i("Deli texr", deli_text);

        if(fio.getText().toString().isEmpty() ||
                phone.getText().toString().isEmpty() ||
                address.getText().toString().isEmpty() ||
                weight.getText().toString().isEmpty()){

            mes_info.setText("Заполните все поля");
        }
        else {
            mes_info.setText("");

            try {
                setJSON("http://192.168.0.103:8081/mysqlapp/insertReq");
            } catch (Exception e) {

            }
        }


    }

    private void setJSON(final String urlWebService) {

        class SetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                String mes = null;
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                    setPostRequestContent(con);
                    con.connect();
                    mes = con.getResponseMessage()+"";

                } catch (Exception e) {
                    return null;
                }
                return mes;
            }
        }
        SetJSON setJSON = new SetJSON();
        setJSON.execute();
    }



    public JSONObject buidJsonObject() throws JSONException {


        recycleReq = new RecycleRequest();
        recycleReq.setName(fio.getText().toString());
        recycleReq.setPhone(phone.getText().toString());
        recycleReq.setAddress(address.getText().toString());
        recycleReq.setWeight(Integer.parseInt(weight.getText().toString()));
        recycleReq.setService_id(Integer.parseInt(id));
        if(deli_text == "курьер"){
            recycleReq.setDelivery(1);
        } else  if(deli_text == "самостоятельно"){
            recycleReq.setDelivery(2);
        }
        if(pay_text == "карта"){
            recycleReq.setPay_way(1);
        } else if (pay_text == "наличные") {
            recycleReq.setPay_way(2);
        }
        recycleReq.setDelivery(2);
        recycleReq.setPay_way(2);


        JSONObject jsonObject = new JSONObject();

        jsonObject.accumulate("name", recycleReq.getName());
        jsonObject.accumulate("phone", recycleReq.getPhone());
        jsonObject.accumulate("address", recycleReq.getAddress());
        jsonObject.accumulate("weight", recycleReq.getWeight());
        jsonObject.accumulate("service_id", recycleReq.getService_id());
        jsonObject.accumulate("delivery", recycleReq.getDelivery());
        jsonObject.accumulate("pay_way", recycleReq.getPay_way());
        Log.i("JSON", jsonObject.toString());
        return jsonObject;
    }



    public void setPostRequestContent(HttpURLConnection conn) throws IOException, JSONException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(buidJsonObject().toString());

        writer.flush();
        writer.close();
        os.close();
    }



}