package com.example.admin_veg_delivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Add_itemActivity extends AppCompatActivity implements View.OnClickListener {
    Button browse,submit_btn;
    ImageView pic_veg;
  public   TextInputEditText price,name,price1;
  public   MaterialBetterSpinner weight,weight1;
    private Bitmap bitmap;
    private  final int IMG_REQUEST=1;
    public int check_img=0;
    long sizeofpic;

    String URL="https://diyavegetable.000webhostapp.com/add_vegetable.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Vegetable");
        browse=findViewById(R.id.browse);
        submit_btn=findViewById(R.id.submit_btn);
        pic_veg=findViewById(R.id.pic_veg);
        price=findViewById(R.id.price);
        name=findViewById(R.id.name);
        weight=findViewById(R.id.weight);
        price1=findViewById(R.id.price1);
        weight1=findViewById(R.id.weight1);
        String str[] = getResources().getStringArray(R.array.weight);
        String str1[] =getResources().getStringArray(R.array.weight1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, str);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, str1);
        weight.setAdapter(adapter);
        weight1.setAdapter(adapter1);
         requestQueue= Volley.newRequestQueue(getApplicationContext());

         submit_btn.setOnClickListener(this);
         browse.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.browse:
                check_img=1;
                selectImage();
                 break;
            case R.id.submit_btn:
                  SubmitData();
                break;

        }
    }
    private void selectImage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);

    }
    private String ImageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgeByte=byteArrayOutputStream.toByteArray();
        sizeofpic= imgeByte.length;
        Toast.makeText(Add_itemActivity.this," String ="+imgeByte,Toast.LENGTH_LONG).show();

        return Base64.encodeToString(imgeByte,Base64.DEFAULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            try {

                Uri path = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);

                pic_veg.setImageBitmap(bitmap);
                Toast.makeText(Add_itemActivity.this,"Path ="+path+" Bitmap  ="+bitmap,Toast.LENGTH_LONG).show();

            }catch(IOException e)
            {
                Toast.makeText(Add_itemActivity.this,"Error "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }
    public void SubmitData()
    {

        if(CheckEmpty()) {
            final String img = ImageToString(bitmap);
          //  Toast.makeText(Add_itemActivity.this, "Entered value =" + name.getText().toString() + "pirce = " + price.getText().toString() + "weight =" + weight.getText().toString() + " " + img, Toast.LENGTH_LONG).show();
            final ProgressDialog progressDialog=new ProgressDialog(Add_itemActivity.this);
            progressDialog.setMessage("Loading... ");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getBoolean("key")) {
                            name.getText().clear();
                            price.getText().clear();
                            weight.getText().clear();
                            weight1.getText().clear();
                            pic_veg.setImageResource(R.mipmap.ic_launcher);
                            weight.clearFocus();
                            weight1.clearFocus();
                            price1.getText().clear();
                            // here write image clear code

                            Toast.makeText(Add_itemActivity.this, "Succees", Toast.LENGTH_LONG).show();
                             progressDialog.dismiss();
                        } else {
                            Toast.makeText(Add_itemActivity.this, "false", Toast.LENGTH_LONG).show();
                             progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(Add_itemActivity.this, "Exception =" + e.getMessage(), Toast.LENGTH_LONG).show();
                          progressDialog.dismiss();

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_itemActivity.this, "Error =" + error.getMessage(), Toast.LENGTH_LONG).show();
                     progressDialog.dismiss();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("name", name.getText().toString().trim());
                    params.put("price", price.getText().toString().trim());
                    params.put("price1",price1.getText().toString());
                    params.put("weight", weight.getText().toString());
                    params.put("weight1", weight1.getText().toString());
                    params.put("img", img);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

    }

    public boolean CheckEmpty()
    {  // check the You enter number or anything else


        if(name.getText().toString().isEmpty())
        {
            name.setError("Invalid Input");
            return false;

        }else if(price.getText().toString().isEmpty())
        {price.setError("Invalid Input");
            return  false;
        }else if(weight.getText().toString().isEmpty())
        {
            weight.setError("Invalid Input");
            return false;
        }else if(check_img==0)
        {
            browse.setError("Please select Image");
            return false;
        }else
        {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Add_itemActivity.this,Dashboad.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home);
        {
            Intent i = new Intent(Add_itemActivity.this,Dashboad.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
