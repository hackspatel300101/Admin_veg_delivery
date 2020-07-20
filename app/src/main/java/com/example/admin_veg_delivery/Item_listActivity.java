package com.example.admin_veg_delivery;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin_veg_delivery.adapter.Vegetable_list_adp;
import com.example.admin_veg_delivery.model.Product_details;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Item_listActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
     Integer id;
    public String name,price,price1,imageurl,weight,weight1,URL="https://diyavegetable.000webhostapp.com/vegetable_show.php";
    List<Product_details> list = new ArrayList<>();
    Vegetable_list_adp adp;
     RequestQueue requestQueue;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Vegetable List");
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        recyclerView= findViewById(R.id.recycleView);
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
                            id= object.getInt("id");
                            name = object.getString("name");
                            price = object.getString("price");
                            price1=object.getString("price1");
                            imageurl=object.getString("image_path");
                           weight=object.getString("kilogram");
                            weight1=object.getString("kilogram1");



                            // here create model object and setter the data into setter method
                            Product_details product_details = new Product_details(id,imageurl,name,price,price1,weight,weight1);
                            list.add(product_details);


                        }
                        adp= new Vegetable_list_adp(Item_listActivity.this,list);
                        recyclerView.setAdapter(adp);
                        pd.dismiss();



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Item_listActivity.this, "exception"+e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }else
                {
                    Toast.makeText(Item_listActivity.this, "null", Toast.LENGTH_LONG).show();

                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText( Item_listActivity.this,"Volley Response error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }

        });


        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Item_listActivity.this,Dashboad.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        {
            Intent i = new Intent(Item_listActivity.this,Dashboad.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
