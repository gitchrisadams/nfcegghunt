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
import android.widget.VideoView;

public class YouLoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_lose);

        /***************************** Play a Local Video ********************************/
        final VideoView videoView = (VideoView) findViewById(R.id.videoViewYouLose);

        //Create the MediaController that allows use to stop/play/pause the video:
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        //Set the path and start the video:
        videoView.setVideoPath("android.resource://com.christopheradams.nfcegghunt/raw/cat");
        videoView.requestFocus();
        videoView.start();
        /**********************************************************************************/


        /***************** Sending user to a new Activity with button press *********************/
        //Send to the FindEggActivity Activity:
        //Associate our buttonFindEgg variable with our UI button:
        Button buttonPlayAgain =(Button)findViewById(R.id.buttonPlayAgain);

        //Setup a click listener. Set the XML file for button onClick to go to onClick:
        buttonPlayAgain.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
        /**************************************************************************************/


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

}
