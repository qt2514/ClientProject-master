package com.twotr.twotr.tutorfiles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;
import com.twotr.twotr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchTextView extends AppCompatActivity {


    SharedPreferences Shared_user_details;
String Stoken;
    SearchResult searchResult;
    SearchBox search;

    String BaseSearchurl="http://twotr.com:4040/api/subject/search?key=";
    String search_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_text_view);
        search = (SearchBox) findViewById(R.id.searchbox);
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);

        search.setLogoText("Earch Your Subject here");
        subject_spinner("http://twotr.com:4040/api/userinfo/basic/profile");
        search.setMenuListener(new SearchBox.MenuListener(){

            @Override
            public void onMenuClick() {
                //Hamburger has been clicked
                Toast.makeText(SearchTextView.this, "Menu click", Toast.LENGTH_LONG).show();
            }

        });
        search.setSearchListener(new SearchBox.SearchListener(){

            @Override
            public void onSearchOpened() {
                //Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                //Use this to un-tint the screen
            }

            @Override
            public void onSearchTermChanged(String s) {
                subject_spinner(BaseSearchurl+s);

            }

            @Override
            public void onSearch(String searchTerm) {

                Toast.makeText(SearchTextView.this, searchTerm +" Searched", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResultClick(SearchResult result){
                search_result= String.valueOf(result);
                Toast.makeText(SearchTextView.this, search_result, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onSearchCleared() {

            }

        });
    }
    public void subject_spinner(String myapi) {


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,myapi , new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jsonArray = jObj.getJSONArray("subjects");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);

                        String name = jsonobject.getString("title");

                            SearchResult option = new SearchResult(name, getResources().getDrawable(R.drawable.ic_local_library_black_24dp));
                            search.addSearchable(option);

//                        // String id = jsonobject.getString("_id");
//
//                        subject_name.add(new MultiSelectModel(i,name));
                    }


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


}
