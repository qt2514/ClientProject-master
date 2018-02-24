package com.twotr.twotr.globalpackfiles;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.twotr.twotr.R;
import com.twotr.twotr.db_handlers.SessionManager;
import com.twotr.twotr.tutorfiles.HomePage;

public class SplashActivity extends AppCompatActivity {
    VideoView videoView;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session = new SessionManager(getApplicationContext());

   //     videoView = findViewById(R.id.splash_video);

//        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_anim);
//        videoView.setVideoURI(video);
//
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            public void onCompletion(MediaPlayer mp) {
//                startNextActivity();
//            }
//        });
//
//        videoView.start();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
             startNextActivity();
            }
        }, 3000);
    }

    private void startNextActivity() {
        if (isFinishing())
            return;
        if (session.isLoggedIn()) {
            Intent intent = new Intent(SplashActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        }
        else
        {
            this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
            this.overridePendingTransition(0, 0);
            finish();
        }

    }



}
