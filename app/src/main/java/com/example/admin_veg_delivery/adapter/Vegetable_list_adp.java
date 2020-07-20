package com.example.admin_veg_delivery.adapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin_veg_delivery.Item_listActivity;
import com.example.admin_veg_delivery.R;
import com.example.admin_veg_delivery.model.Product_details;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.admin_veg_delivery.Item_listActivity.recyclerView;

public class Vegetable_list_adp extends RecyclerView.Adapter<Vegetable_list_adp.Myholder>{
    Context context;
    List<Product_details> lits;
   int id;
   Vegetable_list_adp adp;
    public String name,price,price1,imageurl,weight,weight1,URL1="https://diyavegetable.000webhostapp.com/vegetable_show.php";
    String URL="https://diyavegetable.000webhostapp.com/RemoveVeg.php";
    RequestQueue requestQueue;
    public Vegetable_list_adp(Context context, List<Product_details> lits) {
        this.context = context;
        this.lits = lits;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.price_list_view, parent, false);

        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myholder holder, final int position) {
        Picasso.get().load(lits.get(position).getImageurl()).into(holder.iv);
        holder.name.setText(lits.get(position).getName());
        holder.weight.setText(lits.get(position).getKilogram());
        holder.weight1.setText(lits.get(position).getKilogram1());
        holder.price.setText(lits.get(position).getPrice());
        holder.price1.setText(lits.get(position).getPrice1());
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
        String dateString = sdf.format(date);
        holder.time.setText(dateString);
        requestQueue = Volley.newRequestQueue(context);
         holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
             @Override
             public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                 menu.add("Remove").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                     @Override
                     public boolean onMenuItemClick(MenuItem item) {
                         id= (lits.get(position).getId());
                            RemovedData();
                         return false;
                     }
                 });
             }
         });




    }

    @Override
    public int getItemCount() {
        return lits.size();
    }

    class Myholder extends RecyclerView.ViewHolder   {
        TextView name, weight,weight1, price,price1, time;
        ImageView iv;


        public Myholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.veg_name);
            weight = itemView.findViewById(R.id.veg_unit);
            price = itemView.findViewById(R.id.veg_price);
            iv = itemView.findViewById(R.id.veg_iv);
            time = itemView.findViewById(R.id.time);
            weight1=itemView.findViewById(R.id.veg_unit1);
            price1=itemView.findViewById(R.id.veg_price1);



        }


    }
    public void RemovedData()
    {


            //  Toast.makeText(Add_itemActivity.this, "Entered value =" + name.getText().toString() + "pirce = " + price.getText().toString() + "weight =" + weight.getText().toString() + " " + img, Toast.LENGTH_LONG).show();
            final ProgressDialog progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Loading... ");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("key")) {
                            lits.clear();
                            getdata();

                            // here write image clear code

                            Toast.makeText(context, "Remove", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(context, "false", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context, "Exception =" + e.getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error =" + error.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("id", String.valueOf(id));
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    private void getdata() {
        StringRequest stringRequest1=new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
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
                            lits.add(product_details);


                        }

                        adp= new Vegetable_list_adp(context,lits);
                        recyclerView.setAdapter(adp);
                        adp.notifyDataSetChanged();




                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "exception"+e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }else
                {
                    Toast.makeText(context, "null", Toast.LENGTH_LONG).show();

                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                Toast.makeText( context,"Volley Response error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }

        });


        requestQueue.add(stringRequest1);
    }




}
