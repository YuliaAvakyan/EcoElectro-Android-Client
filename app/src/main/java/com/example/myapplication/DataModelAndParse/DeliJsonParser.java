package com.example.myapplication.DataModelAndParse;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DeliJsonParser {

    private ArrayList<Delivery> delivery;

    public DeliJsonParser(){
        delivery = new ArrayList<>();
    }

    public ArrayList<Delivery> getDelivery(){
        return  delivery;
    }

    public ArrayList<Delivery> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public ArrayList<Delivery> readMessagesArray(JsonReader reader) throws IOException {

        reader.beginArray();
        while (reader.hasNext()) {
            delivery.add(readMessage(reader));
        }
        reader.endArray();
        return delivery;
    }

    public Delivery readMessage(JsonReader reader) throws IOException {
        int id = -1;
        String typeS = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id_deli")) {
                id = reader.nextInt();
            } else if (name.equals("type")) {
                typeS = reader.nextString();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Delivery(id, typeS);
    }
}
