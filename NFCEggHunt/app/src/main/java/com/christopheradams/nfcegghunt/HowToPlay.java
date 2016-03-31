package com.christopheradams.nfcegghunt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;;

public class HowToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        /***************************** Play a Local Video ********************************/
        VideoView videoView2 = (VideoView)findViewById(R.id.videoView2);
        //Set the path and start the video:
        videoView2.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.howtoplay2));
        videoView2.requestFocus();
        videoView2.start();
        /**********************************************************************************/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    /**************** Method to Send user to Activity based on what button they press *************/
    public void onClick(View v){
            Intent intent = new Intent(getApplicationContext(), FindEggActivity.class);
            startActivity(intent);
    }
    /*******************************************************************************************/




}
