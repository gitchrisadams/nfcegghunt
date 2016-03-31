package com.christopheradams.nfcegghunt;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class FindEggActivity extends AppCompatActivity {

    //Define timerSeekBar here so it is available in entire class:
    SeekBar timerSeekBar;

    //Define our TextView so accessible to all of the class:
    TextView timerTextView;

    //Boolean var too tell when counterIsActive
    Boolean counterIsActive = false;

    //Create variable for our controllerButton:
    Button controllerButton;

    //Create variable for our CountDownTimer:
    CountDownTimer countDownTimer;

    //Create class member for NfcADapter:
    private NfcAdapter nfcAdapter;

    //Method to reset our timer back to 0:00:
    public void resetTimer(){
        //Reset the timer:
        //Set timer text back to "0:30"
        timerTextView.setText("0:30");

        //Set timer progress bar to have a progress of 30 seconds displayed:
        timerSeekBar.setProgress(30);

        //Cancel/Stop our countDownTimer:
        countDownTimer.cancel();

        //Set the text on the button back to "GO"
        controllerButton.setText("Go!");

        //Re-enable our seekBar:
        timerSeekBar.setEnabled(true);

        //Set counterIsActive back to false again so can restart timer if we want:
        counterIsActive = false;
    }



    public void updateTimer(int secondsLeft){
        //Convert from minutes to have minutes and seconds:
        //Create int for the minutes that is the num of seconds total(progress) / 60:
        //Cast to an int so it rounds down and get whole number.
        int minutes = (int) secondsLeft / 60;

        //Get the number of seconds left over after we divide by 60.
        int seconds = secondsLeft - (minutes * 60);

        //Make it so when timer goes to 10:00 it displays 10:00 not 10:0
        //Create string variable of the the seconds converted to a string:
        String secondString = Integer.toString(seconds);

        //Do a check to see if secondString is a single digit and if so set to "08" 09" etc...
        if(seconds <= 9){
            secondString = "0" + secondString;
        }

        //Display the time on the timerTextView:
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }



    public void controlTimer(View view){

        //We only want all this code to run when counterIsActive is set to false:
        if(counterIsActive == false) {
            //Counter is now active so set it to true:
            counterIsActive = true;

            //When timer is active we want to disable the seek bar:
            timerSeekBar.setEnabled(false);

            //Change the text on our "GO" button to read Stop while timer is counting down:
            controllerButton.setText("Stop");

            //Start the timer:
            //We user timerSeekBar.getProgress to get the number of seconds.
            //Then we need to multiply by 1000 to go from seconds to milliseconds.
            //The (..., 1000) says to count down every second.(1000 milliseconds)
            //The + 100 adds on tenth of a second to timer so it gives time for timer to start:
            //We are assigning all this to var countDownTimer
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 300, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //On each tick of timer we need to update value of timer:
                    //millisUntilFinished passed in as long so cast it to an (int):
                    updateTimer((int) millisUntilFinished / 1000);



                }

                @Override
                public void onFinish() {
                    //Timer finished so call our method to reset the timer to 0:00:
                    resetTimer();

                    //Make it play our air horn sound when timer finishes:
                    //Create variable for our airhorn sound:
                    //R.raw.airhorn is the file name
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);

                    //start the sound:
                    mplayer.start();

                    //When timer gets to end, take user to the You Lose Activity:
                    Intent intent = new Intent(getApplicationContext(), YouLoseActivity.class);
                    startActivity(intent);


                    Log.i("finished", "timer done");
                }
            }.start(); //Start timer.
        }else {
            //Call resetTimer() method to reset our timer:
            resetTimer();
        }

    }

    /*************************** Override methods for NFC: *****************************/
    //When activity received a new incoming intent, this is called.
    @Override
    protected void onNewIntent(Intent intent) {
        //Display Toast "NFC intent received" and tag action when tag is scanned:
        Toast.makeText(this, "NFC intent received", Toast.LENGTH_SHORT).show();
        super.onNewIntent(intent);

        String action = intent.getAction();
        Toast.makeText(this, action, Toast.LENGTH_LONG).show();




    }



    //This gets called when our activity starts or resumes:
    @Override
    protected void onResume(){
        super.onResume();

        //YouWonActivity.class is the class you want this to go to after tag is scanned:
        Intent intent = new Intent(this, YouWonActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);


        //We create a PendingIntent object so it can be populated with tag details.
        //We use the IntentFilter to let the foreground dispatch system know what intent to intercept.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[] { };

        //We enable this which indicates any Intent should be dispatched to MainActivity file:
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);
    }

    //User has left the app so pause it and disableForegroundDispatch:
    @Override
    protected void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);

        //Resets the timer if paused(If user has won)
        resetTimer();
    }
    /****************************************************************************************/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_egg);

        //Instantiate nfcAdapater class field:
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        //Associate our SeekBar with our UI element:
        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);

        //Associate our TextView with our UI element:
        timerTextView = (TextView)findViewById(R.id.timerTextView);

        //Associate our controllerButton with our UI element:
        controllerButton = (Button)findViewById(R.id.buttonController);

        //Set max for your timer in seconds: So 6minutes
        timerSeekBar.setMax(600);

        //Set the current progress in seconds:
        timerSeekBar.setProgress(30);

        //Setup so when your slider is moved up and down the timer text will update:
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Call our updateTimer method above passing in progress:
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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

//    @Override
//    public void onBackPressed() {
//        Intent intentBack = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intentBack);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
