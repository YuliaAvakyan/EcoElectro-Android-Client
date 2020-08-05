package com.example.myapplication.DataModelAndParse;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CityJsonParser {

    private ArrayList<City> cityArrayList;

    public CityJsonParser(){
        cityArrayList = new ArrayList<>();
    }

    public ArrayList<City> getDelivery(){
        return  cityArrayList;
    }

    public ArrayList<City> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public ArrayList<City> readMessagesArray(JsonReader reader) throws IOException {

        reader.beginArray();
        while (reader.hasNext()) {
            cityArrayList.add(readMessage(reader));
        }
        reader.endArray();
        return cityArrayList;
    }

    public City readMessage(JsonReader reader) throws IOException {
        int id = -1;
        String nameC = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("idcity")) {
                id = reader.nextInt();
            } else if (name.equals("city")) {
                nameC = reader.nextString();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new City(id, nameC);
    }

}
