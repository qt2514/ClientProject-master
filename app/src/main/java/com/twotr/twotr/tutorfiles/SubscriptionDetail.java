package com.twotr.twotr.tutorfiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.twotr.twotr.guestfiles.GuestActivityDetails;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionDetail extends AppCompatActivity {
TextView TVtitle,TVdescrption,TVprice;
    List<String> Listgrade ;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    Context context;
    String Stitle,Sdescription,Sprice,Spaymenttype,Surl;
    ImageView Ivimage;
    ImageButton IB_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_detail);

        TVtitle=findViewById(R.id.title_subs);
        TVdescrption=findViewById(R.id.descrip_subs);
        TVprice=findViewById(R.id.price_subs);
        Ivimage=findViewById(R.id.image_subs);
        recyclerView=findViewById(R.id.recyclerview1);
        Listgrade= new ArrayList<String>();
        context=SubscriptionDetail.this;
        IB_back=findViewById(R.id.back_ima_scedule);

        final Intent intent = getIntent();
        Bundle Bintent = intent.getExtras();
        if(Bintent != null) {
            Listgrade= Bintent.getStringArrayList("gradelevel");
Log.i("listgrade",Listgrade.toString());
            Stitle= Bintent.getString("title_main");
            Sdescription= Bintent.getString("description");
            Sprice= Bintent.getString("price");
            Spaymenttype= Bintent.getString("paymenttype");
            Surl= Bintent.getString("imagemain");



        }
        IB_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Picasso
                .with(context)
                .load(Global_url_twotr.Image_Base_url+Surl)
                .fit()
                .error(getResources().getDrawable(R.drawable.profile_image_tutor))
                .centerCrop()
                .into( Ivimage);

TVtitle.setText(Stitle);
TVdescrption.setText(Sdescription);

TVprice.setText(Spaymenttype+"-"+Sprice);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Listgrade);

        HorizontalLayout = new LinearLayoutManager(SubscriptionDetail.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setHorizontalScrollBarEnabled(false);

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

    }

}
