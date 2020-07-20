package com.example.admin_veg_delivery;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin_veg_delivery.adapter.Oder_list_adp;
import com.example.admin_veg_delivery.model.Oders_details;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Oders_details> list = new ArrayList<>();
    Oder_list_adp adpl;
    ProgressDialog pd;
    public String number,name_add,oder,URL="https://diyavegetable.000webhostapp.com/fetchoder.php",URL1="https://diyavegetable.000webhostapp.com/delete_order.php";
    RequestQueue requestQueue;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Oders List");
        recyclerView=findViewById(R.id.oder_list_rec);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pd= new ProgressDialog(this);
        pd.setMessage("Please Wait.....");
        pd.show();
        getdata();


    }
    private void getdata() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                if(response!=null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("root");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            number = object.getString("number");
                            name_add = object.getString("name_add");
                            oder=object.getString("oder");
                            time=object.getString("time");



                            // here create model object and setter the data into setter method


                            Oders_details oders_details = new Oders_details(number,name_add,time,oder);
                            list.add(oders_details);

                        }
                        adpl= new Oder_list_adp(OderActivity.this,list);
                        recyclerView.setAdapter(adpl);
                        pd.dismiss();





                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(OderActivity.this, "exception"+e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }else
                {
                    Toast.makeText(OderActivity.this, "null", Toast.LENGTH_LONG).show();

                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText( OderActivity.this,"Volley Response error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }

        });


        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(OderActivity.this,Dashboad.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        {
            Intent i = new Intent(OderActivity.this,Dashboad.class);
            startActivity(i);
            finish();
        }
        switch (item.getItemId()){
            case R.id.remove_all:
                Delete_all();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Delete_all() {



            //  Toast.makeText(Add_itemActivity.this, "Entered value =" + name.getText().toString() + "pirce = " + price.getText().toString() + "weight =" + weight.getText().toString() + " " + img, Toast.LENGTH_LONG).show();

            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("key")) {
                            // here write image clear code
                            Toast.makeText(OderActivity.this, "Remove", Toast.LENGTH_SHORT).show();
                          /*  Intent i = new Intent(OderActivity.this,Dashboad.class);
                            startActivity(i);
                            finish();*/
                          finish();

                        } else {
                            Toast.makeText(OderActivity.this, "false", Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        Toast.makeText(OderActivity.this, "Exception =" + e.getMessage(), Toast.LENGTH_LONG).show();


                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(OderActivity.this, "Error =" + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }) {


            };
            requestQueue.add(stringRequest1);
        }

    }


