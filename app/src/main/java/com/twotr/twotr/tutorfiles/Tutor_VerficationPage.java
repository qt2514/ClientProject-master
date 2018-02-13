package com.twotr.twotr.tutorfiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tutor_VerficationPage extends AppCompatActivity {
    EditText ETzone;
    SearchableSpinner SSdocu_type;
    private ArrayList<String> ASdocument;
    SharedPreferences Shared_user_details;
    String Stoken;
    AVLoadingIndicatorView avi;
    ArrayList<Uri> path = new ArrayList<>();
    //RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ImageAdapter imageAdapter;
    ImageController mainController;
    ImageView IVfront,IVback;
    SharedPreferences.Editor editor;
    String Szone,Sdocumenttype,sweetmessage,doctype_selec;
    Button saveandcontinue;
    public String Sid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor__verfication_page);

        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
        IVfront=findViewById(R.id.selected_images_front);
        IVback=findViewById(R.id.selected_images_back);

        ETzone=findViewById(R.id.prof_zone);
        //ETdocumnumer=findViewById(R.id.prof_doc_num);
        SSdocu_type=findViewById(R.id.prof_document_type);
       // recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        saveandcontinue=findViewById(R.id.id_verification_save);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        // mainController = new ImageController(this, IVverification);
        // imageAdapter = new ImageAdapter(this, mainController, path);
        //  recyclerView.setLayoutManager(linearLayoutManager);
        //  recyclerView.setAdapter(imageAdapter);
        documenttype();

        avi=findViewById(R.id.avi);
        avi.hide();
        Shared_user_details = getSharedPreferences("user_detail_mode", 0);
        Sid = Shared_user_details.getString("id", null);
        ASdocument = new ArrayList<String>();


        saveandcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ETzone.getText().toString().equals(""))
                {
                    sweetmessage="Please Enter Your Issued Country ";
                    new SweetAlertDialog(Tutor_VerficationPage.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
                else
                {
                    if(doctype_selec==null)
                    {
                        sweetmessage="Please Select Document Type";
                        new SweetAlertDialog(Tutor_VerficationPage.this, SweetAlertDialog.NORMAL_TYPE).setTitleText(sweetmessage)
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                }).show();
                    }
                    else
                    {
//                        if(ETdocumnumer.getText().toString().equals(""))
//                        {
//                            sweetmessage="Please Enter Document Number";
//                        }
//                        else
//                        {
                            //sweetmessage="Thank You";
                            avi.show();
                            Szone=ETzone.getText().toString();
                            Sdocumenttype=SSdocu_type.getSelectedItem().toString().trim();
                          //  Sdocumentnumber=ETdocumnumer.getText().toString();
                            professionalapiins(Szone,Sdocumenttype,"1");
                        }


                }



            }
        });


        IVfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FishBun.with(Tutor_VerficationPage.this)
//                        .setImageAdapter(new PicassoAdapter())
//                        .setPickerCount(2)
//                        .setPickerSpanCount(2)
//                        .setMaxCount(2)
//                        .setMinCount(2)
//                        .setActionBarColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark), true)
//                        .setActionBarTitleColor(getResources().getColor(R.color.colorwhite))
//                        .setSelectedImages(path)
//                        .setButtonInAlbumActivity(true)
//                        .setCamera(true)
//                        .exceptGif(true)
//                        .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(Tutor_VerficationPage.this, R.drawable.ic_back_page))
//                        .setOkButtonDrawable(ContextCompat.getDrawable(Tutor_VerficationPage.this, R.drawable.ic_check_black_24dp))
//                        .setReachLimitAutomaticClose(false)
//                        .setAllViewTitle("Front And Back Images")
//                        .setActionBarTitle("TWOTR Verification")
//                        .textOnImagesSelectionLimitReached("You can't select any more.")
//                        .textOnNothingSelected("Please Select Image To verify Your ID")
//                        .startAlbum();
                //if everything is ok we will open image chooser
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        IVback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 120);
            }
        });

        SSdocu_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                doctype_selec= parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void documenttype() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Global_url_twotr.Profile_document_type, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {


                    JSONArray jObj = new JSONArray(response);
                    for (int i = 0; i < jObj.length(); i++) {
                        ASdocument.add(String.valueOf(jObj.get(i)));
                    }

                    SSdocu_type.setAdapter(new ArrayAdapter<String>(Tutor_VerficationPage.this, android.R.layout.simple_dropdown_item_1line, ASdocument));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {

                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("content-Type", "application/json");
                headers.put("x-tutor-app-id", "tutor-app-android");
                headers.put("authorization", "Bearer "+Stoken);


                return headers;

            }



        };

        requestQueue.add(stringRequest);
    }


    public void professionalapiins(String szone, String sdocumenttype, String sdocumentnumber)
    {
        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("zone",szone);
            jsonObject.put("documentType",sdocumenttype);
            jsonObject.put("documentNumber",sdocumentnumber);
            jsonBody.put("idVerification",jsonObject);

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Global_url_twotr.User_Profile_Id_verification, new Response.Listener<String>() {

                public void onResponse(String response) {
                    editor = Shared_user_details.edit();
                    editor.putBoolean("isIdVerified",true);
                    editor.commit();
                    avi.hide();

                    startActivity(new Intent(Tutor_VerficationPage.this, HomePage.class));
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    avi.hide();

                    new SweetAlertDialog(Tutor_VerficationPage.this, SweetAlertDialog.WARNING_TYPE).setTitleText("Server Error")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();            }
            }) {
                @Override
                public String getBodyContentType() {

                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("content-Type", "application/json");
                    headers.put("x-tutor-app-id", "tutor-app-android");
                    headers.put("authorization", "Bearer "+Stoken);


                    return headers;

                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");

                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }




            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
//        switch (requestCode) {
//            case Define.ALBUM_REQUEST_CODE:
             //   if (resultCode == RESULT_OK) {
        if (requestCode == 100 && resultCode == RESULT_OK && imageData != null) {

                    //getting the image Uri
                    Uri imageUri = imageData.getData();
                    try {
                        //getting bitmap object from uri
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//               More then one file
                        List<Bitmap> listBitmap = new ArrayList<>();
                        listBitmap.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));
                      //  listBitmap.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));
                        IVfront.setImageBitmap(bitmap);

                        uploadBitmap(listBitmap);


                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }



                  //  imageAdapter.changePath(path);

                //    break;
               // }
                
                
        }
        else if(requestCode == 120 && resultCode == RESULT_OK && imageData != null) {


            Uri imageUri = imageData.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//               More then one file
                List<Bitmap> listBitmap = new ArrayList<>();
                listBitmap.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));
                //  listBitmap.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));
                IVback.setImageBitmap(bitmap);

                uploadBitmap(listBitmap);


            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final List<Bitmap> listBitmap) {

        Map<String, String> myheader = new HashMap<String, String>();
        myheader.put("x-tutor-app-id","tutor-app-android");
        myheader.put("Authorization","Bearer "+Stoken);
//        myheader.put("content-type", "application/json");


        //our custom volley request
        VolleyMultipartFiles VolleyMultipartFiles = new VolleyMultipartFiles(Request.Method.POST,Global_url_twotr.Profile_image_id_verification+Sid, myheader,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.i("myresult", "onResponse: " + obj);
//                           Log.d(TAG, "onResponse: " + obj.toString());
                            Toast.makeText(getApplicationContext(), obj.getString("Success"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("myresult", "onError: " + error);

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", "files");
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                for(Bitmap bitm : listBitmap){
                    long imagename = System.currentTimeMillis();
                    params.put("file" + imagename , new DataPart(imagename + ".png", getFileDataFromDrawable(bitm)));
                }
                return params;
            }
        };

        Volley.newRequestQueue(this).add(VolleyMultipartFiles);
    }



    public byte[] getFileDataFromDrawableback(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmapback(final List<Bitmap> listBitmap) {

        Map<String, String> myheader = new HashMap<String, String>();
        myheader.put("x-tutor-app-id","tutor-app-android");
        myheader.put("Authorization","Bearer "+Stoken);
//        myheader.put("content-type", "application/json");


        //our custom volley request
        VolleyMultipartFiles VolleyMultipartFiles = new VolleyMultipartFiles(Request.Method.POST,Global_url_twotr.Profile_image_id_verification+Sid, myheader,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.i("myresult", "onResponse: " + obj);
//                           Log.d(TAG, "onResponse: " + obj.toString());
                            Toast.makeText(getApplicationContext(), obj.getString("Success"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("myresult", "onError: " + error);

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", "files");
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                for(Bitmap bitm : listBitmap){
                    long imagename = System.currentTimeMillis();
                    params.put("file" + imagename , new DataPart(imagename + ".png", getFileDataFromDrawableback(bitm)));
                }
                return params;
            }
        };

        Volley.newRequestQueue(this).add(VolleyMultipartFiles);
    }
}
