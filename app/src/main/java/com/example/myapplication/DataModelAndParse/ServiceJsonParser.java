package com.example.myapplication.DataModelAndParse;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ServiceJsonParser {

    private ArrayList<Service> services;

    public ServiceJsonParser(){
        services = new ArrayList<>();
    }

    public ArrayList<Service> getServices(){
        return  services;
    }

    public ArrayList<Service> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            //reader.close();
        }
    }

    public ArrayList<Service> readMessagesArray(JsonReader reader) throws IOException {

        reader.beginArray();
        while (reader.hasNext()) {
            services.add(readMessage(reader));
        }
        reader.endArray();
        return services;
    }

    public Service readMessage(JsonReader reader) throws IOException {
        int id = -1;
        String nameS = null;
        String address = null;
        String web = null;
        String description = null;
        String phone = null;
        String delivery = null;
        int recycle_fee = 0;
        int back_fee = 0;
        int pay_rate = 0;
        int pay_back_rate = 0;
        String city = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                id = reader.nextInt();
            } else if (name.equals("name")) {
                nameS = reader.nextString();
            }else if (name.equals("phone")) {
                phone = reader.nextString();
            } else if (name.equals("address")) {
                address = reader.nextString();
            } else if (name.equals("delivery")) {
                delivery = reader.nextString();
            } else if (name.equals("web")) {
                web = reader.nextString();
            } else if (name.equals("description")) {
                description = reader.nextString();
            } else if (name.equals("recycle_fee")) {
                recycle_fee = reader.nextInt();
            } else if (name.equals("back_fee")) {
                back_fee = reader.nextInt();
            }
            else if (name.equals("pay_rate")) {
                pay_rate = reader.nextInt();
            } else if (name.equals("pay_back_rate")) {
                pay_back_rate = reader.nextInt();
            }
            else if (name.equals("city")) {
                city = reader.nextString();
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Service(id, nameS, phone, address, delivery , web, description, recycle_fee, back_fee, pay_rate, pay_back_rate, city);
    }
}
