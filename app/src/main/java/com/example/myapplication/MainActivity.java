package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataModelAndParse.City;
import com.example.myapplication.DataModelAndParse.CityJsonParser;
import com.example.myapplication.DataModelAndParse.Service;
import com.example.myapplication.DataModelAndParse.ServiceJsonParser;
import com.example.myapplication.Map.MapFragment;
import com.example.myapplication.OneService.ServiceAndSendActivity;
import com.example.myapplication.RecyclerAndFilter.DataModel;
import com.example.myapplication.RecyclerAndFilter.MyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public String ip = "192.168.0.103";

    //ListView listView;
    ServiceJsonParser parser;
    ArrayList<Service> services;
    ArrayList<City> cities;
    CityJsonParser cityJsonParser;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ActionMenuItemView item_city_text;
    private int cityId = 0;

    private MyAdapter myAdapter;
    ArrayList<DataModel> dataModelList;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        MenuItem city_item = findViewById(R.id.action_city);
//        item_city_text = findViewById(R.id.action_city);

        if(savedInstanceState == null){
            this.getSupportFragmentManager().beginTransaction().replace(R.id.container1, Services_Fragment.newInstance()).commit();

                try {
                    getJSON("http://192.168.0.103:8081/mysqlapp/service");
                } catch (Exception e) {

                }

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
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                loadIntoListView(services);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    parser = new ServiceJsonParser();
                    cityJsonParser = new CityJsonParser();

                    services = parser.readJsonStream(con.getInputStream());
                    cities = cityJsonParser.readJsonStream(con.getInputStream());

                   return services.toString();

                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView( ArrayList<Service> services) {

        dataModelList = new ArrayList<>();
        for(int i = 0; i < services.size(); i++){
            String title = services.get(i).getName();
            String subtitle = services.get(i).getAddress() + "\n" + "тел. " + services.get(i).getPhone() + "\n"
                    + "доставка техники в сервис: " + services.get(i).getDelivery();
            int itemId = services.get(i).getId();
            dataModelList.add(new DataModel(title, subtitle, itemId));


        }
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        myAdapter = new MyAdapter(dataModelList, this);

        mRecyclerView.setAdapter(myAdapter);
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.main, menu);
       final MenuItem item_search = menu.findItem(R.id.action_search);
       final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item_search);

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }
           @Override
           public boolean onQueryTextChange(String query) {
               //FILTER AS YOU TYPE
               myAdapter.getFilter().filter(query);
               return false;
           }
       });

       MenuItem item_city = menu.findItem(R.id.action_city);
       Menu menu_city = item_city.getSubMenu();


       for(int i = 0; i < cities.size(); i++){
           menu_city.add(0        // Группа
                   ,i+1       // id
                   ,i        //порядок
                   ,cities.get(i).getCityName());  // заголовок


       }



       return true;
    }

 @Override
    public boolean onOptionsItemSelected(MenuItem item) {

     item_city_text = findViewById(R.id.action_city);
        int id = item.getItemId();

        if (id == R.id.action_city) {
            Log.i("Menu", "Settings");
            return true;
        }

     for(int i = 0; i < cities.size(); i++){

         if (id == i+1) {
             try {
                 getJSON("http://192.168.0.103:8081/mysqlapp/SelectCity?cityId=" + id);
             } catch (Exception e) {

             }
             Log.i("Menu", "Create" + cities.get(i).getCityName());


             item_city_text.setTextAppearance(this, R.style.MenuItemViewStyle);

             item_city_text.setText(cities.get(i).getCityName());
             cityId = id;

             return true;
         }

     }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_map) {
            this.getSupportFragmentManager().beginTransaction().replace(R.id.container1, MapFragment.newInstance()).commit();
            setTitle(item.getTitle());


        } else if (id == R.id.nav_list) {


            this.getSupportFragmentManager().beginTransaction().replace(R.id.container1, Services_Fragment.newInstance()).commit();

            if(cityId > 0){

                for(int i = 0; i < cities.size(); i++) {
                    if (cityId == i + 1) {
                        try {
                            getJSON("http://192.168.0.103:8081/mysqlapp/SelectCity?cityId=" + cityId);
                            Log.i("cityId", String.valueOf(cityId));
                        } catch (Exception e) {

                        }
                    }


                    setTitle("");
                }
            }

            else{
                try {
                    getJSON("http://" + ip + ":8081/mysqlapp/service");
                } catch (Exception e) {
                }
            }


        }
        else if (id == R.id.nav_info) {
            this.getSupportFragmentManager().beginTransaction().replace(R.id.container1, InfoFragment.newInstance()).commit();
            // Выделяем выбранный пункт меню в шторке
            //item.setChecked(true);
            // Выводим выбранный пункт в заголовке
            setTitle(item.getTitle());

        } else if (id == R.id.nav_send) {
            CallBackFragment dialog = new CallBackFragment();
            dialog.show(getSupportFragmentManager(), "custom");

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void InfoClick(View view) {
       super.onPause();
//        super.getIntent().addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);


        int position = (int) view.getTag();
        Log.i("ID",String.valueOf(position));
        String id_str = String.valueOf(position);


        Intent intent = new Intent(this, ServiceAndSendActivity.class);
        intent.putExtra("id", id_str);
        startActivity(intent);

    }

}
