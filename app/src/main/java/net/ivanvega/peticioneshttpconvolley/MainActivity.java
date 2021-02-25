package net.ivanvega.peticioneshttpconvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RequestQueue  requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        peticionSimple();
    }

    public void peticionSimple(){
        String url ="http://www.google.com";

        StringRequest stringRequest = new StringRequest(url,
                response -> {
                    Log.d("VOLL", "Exito \n" + response);},
                error -> {
                    Log.d("VOLL","Error \n" + error.toString());}
                );


        String urlGitHubJobs = "https://jobs.github.com/positions.json?description=python&location=new+york";

        JsonRequest jsonRequest  = new JsonArrayRequest(
                urlGitHubJobs,
                response -> {

                     Gson gson = new Gson();

                    Type listType =
                            new TypeToken<List<Trabajos>>() {}.getType();

                    List<Trabajos> list = gson.fromJson(response.toString(),
                            listType);

                    for( Trabajos itm : list){
                        String job="";


                            job =
                                    itm.getId();
                            job += "\n" + itm.getTitle();
                            job += "\n" + itm.getCompany();
                            job += "\n" + itm.getCompany_url();
                            job += "\n" + itm.getType();

                        Log.d("VOLLGSON",  job);

                    }


                     for(  int i=0;i<response.length(); i++){
                         JSONObject    item = null;
                         String job="";
                         try {
                             item = response.getJSONObject(i);
                              job =
                                     item.getString("id");
                             job += "\n" + item.getString("company");
                             job += "\n" + item.getString("type");
                             job += "\n" + item.getString("company_url");
                             job += "\n" + item.getString("title");
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }


                         Log.d("VOLL",  job);
                     }
                }
        ,
                error -> {Log.d("VOLL","Error \n" + error.toString());}
                );

        requestQueue.add(jsonRequest);

        requestQueue.add(stringRequest);

    }

    class Trabajos {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompany_url() {
            return company_url;
        }

        public void setCompany_url(String company_url) {
            this.company_url = company_url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        String id;
        String title;
        String company;
        String company_url;
        String type;

    }

}