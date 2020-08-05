package com.example.myapplication.DataModelAndParse;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PayJsonParser {

    private ArrayList<PayWay> payway;

    public PayJsonParser(){
        payway = new ArrayList<>();
    }

    public ArrayList<PayWay> getPayWay(){
        return  payway;
    }

    public ArrayList<PayWay> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            //reader.close();
        }
    }

    public ArrayList<PayWay> readMessagesArray(JsonReader reader) throws IOException {

        reader.beginArray();
        while (reader.hasNext()) {
            payway.add(readMessage(reader));
        }
        reader.endArray();
        return payway;
    }

    public PayWay readMessage(JsonReader reader) throws IOException {
        int id = -1;
        String way = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("way")) {
                way = reader.nextString();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new PayWay(id, way);
    }
}
