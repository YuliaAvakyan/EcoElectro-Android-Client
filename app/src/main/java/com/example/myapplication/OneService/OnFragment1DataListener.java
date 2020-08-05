package com.example.myapplication.OneService;

import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.DataModelAndParse.Delivery;
import com.example.myapplication.DataModelAndParse.PayWay;

import java.util.ArrayList;

interface OnFragment1DataListener {
    void onOpenSendReq(String str);
    boolean isChecked();
    //void onSendReq();
    void SetInfoPaymentText(EditText editText, TextView textView);
    //void JsonReq();
}
