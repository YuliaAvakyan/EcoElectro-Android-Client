package com.example.myapplication.OneService;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataModelAndParse.DeliJsonParser;
import com.example.myapplication.DataModelAndParse.Delivery;
import com.example.myapplication.DataModelAndParse.PayJsonParser;
import com.example.myapplication.DataModelAndParse.PayWay;
import com.example.myapplication.DataModelAndParse.RecycleReqJson;
import com.example.myapplication.DataModelAndParse.RecycleRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.DataModelAndParse.Service;
import com.example.myapplication.DataModelAndParse.ServiceJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ServiceAndSendActivity extends AppCompatActivity implements OnFragment1DataListener{

    ServiceJsonParser parser;
    RecycleReqJson reqJson;
    ArrayList<Service> services;
    Service service;
    TextView textView_name;
    TextView textView_descr;
    TextView textView_contacts;
    TextView textView_pay;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);



        this.getSupportActionBar().setTitle("О сервисе");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle arguments = getIntent().getExtras();
        id = arguments.getString("id");

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new ServiceInfoFragment();

            Bundle bundle = new Bundle();
            bundle.putString("send_id", id);
            fragment.setArguments(bundle);

            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, ServiceInfoFragment.TAG)
                    .commit();
        }

            try {
                getJSON("http://192.168.0.103:8081/mysqlapp/selectOne?id=" + id);
            } catch (Exception e) {

            }

    }


    //Возврат к MainActivity в исходном состоянии
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityForResult(myIntent, 0);
        return true;
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
                loadIntoTextView(services);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    parser = new ServiceJsonParser();
                    services = parser.readJsonStream(con.getInputStream());

                    return services.toString();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoTextView(ArrayList<Service> services) {
        textView_name = findViewById(R.id.info_name);
        textView_descr = findViewById(R.id.info_descr);
        textView_contacts = findViewById(R.id.info_contacts);
        textView_pay = findViewById(R.id.info_pay);
        service = services.get(0);
        //toolbar.setTitle(service.getName());
        String name = service.getName();
        textView_name.setText(name);

        String descr = service.getDescription();
        textView_descr.setText(descr);

        String contacts = "Вы можете найти нас по адресу " + service.getAddress() + "\n"
                + "Телефон: " + service.getPhone() + "\n"
                + "e-mail: " + service.getWeb();
        textView_contacts.setText(contacts);

        String payments = null;
        if(service.getRecycle_fee() == 1 && service.getBack_fee() == 0){
            payments = "Стоимость утилизациии " + service.getPay_rate() + " руб/кг";
        } else if(service.getRecycle_fee() == 1 && service.getBack_fee() == 1){
            payments = "Стоимость утилизациии " + service.getPay_rate() + " руб/кг" + "\n"
            + "При сдаче вторсырья вы получаете " + service.getPay_back_rate() + " руб/кг";
        }else if(service.getRecycle_fee() == 0 && service.getBack_fee() == 1){
            payments = "Бесплатная утилизация!" + "\n"
                    + "При сдаче вторсырья вы получаете " + service.getPay_back_rate() + " руб/кг";
        }
        else if(service.getRecycle_fee() == 0 && service.getBack_fee() == 0){
            payments = "Бесплатная утилизация!";
        }
        textView_pay.setText(payments);
    }

    //ServiceInfoFragment
    @Override
    public void onOpenSendReq(String str) {

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment instanceof ServiceInfoFragment) {
            Fragment fragmentReplace;
            fragmentReplace = new SendReqFragment();

            Bundle bundle = new Bundle();
            bundle.putString(SendReqFragment.KEY, str);
            fragmentReplace.setArguments(bundle);

            fm.beginTransaction()
                    .replace(R.id.fragmentContainer, fragmentReplace, SendReqFragment.TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }


    //SendReqFragment
    @Override
    public boolean isChecked() {
        return true;
    }

    //SendReqFragment
    @Override
    public void SetInfoPaymentText(EditText editText, final TextView textView){

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(service.getRecycle_fee() == 1 && service.getBack_fee() == 0){
                    int payrate = Integer.parseInt(s.toString())*service.getPay_rate();
                    textView.setText("Оплата утилизации: " + payrate + " руб." + "\n" +
                            "Выплата за сдачу вторсырья не производится");
                } else if(service.getRecycle_fee() == 1 && service.getBack_fee() == 1){
                    int payrate = Integer.parseInt(s.toString())*service.getPay_rate();
                    textView.setText("Оплата утилизации: " + payrate + " руб." + "\n" +
                            "Выплата за сдачу вторсырья " + service.getPay_back_rate() + " руб/кг. Оценка будет произведена при получении техники.");
                } else if(service.getRecycle_fee() == 0 && service.getBack_fee() == 1){
                    textView.setText("Утилизация бесплатная" + "\n" +
                            "Выплата за сдачу вторсырья " + service.getPay_back_rate() + " руб/кг. Оценка будет произведена при получении техники.");
                } else if(service.getRecycle_fee() == 0 && service.getBack_fee() == 0){
                    textView.setText("Утилизация бесплатная" + "\n" +
                            "Выплата за сдачу вторсырья не производится");
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

}
