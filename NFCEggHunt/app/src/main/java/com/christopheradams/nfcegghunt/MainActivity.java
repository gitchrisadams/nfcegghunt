package com.christopheradams.nfcegghunt;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Define button var:
    Button buttonFindEgg;
    Button buttonHowToPlay;
    Button buttonBuyNfcEggs;
    //TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Associate our button variable with our UI button:
        buttonFindEgg =(Button)findViewById(R.id.buttonFindEgg);
        buttonHowToPlay =(Button)findViewById(R.id.buttonHowToPlay);
        buttonBuyNfcEggs =(Button)findViewById(R.id.buttonBuyNfcEggs);
        //textView4 = (TextView)findViewById(R.id.textView4);

        /***************** Displays a Toast message if NFC not available or enabled*********/
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is available.", Toast.LENGTH_LONG).
                    show();
        } else {
            Toast.makeText(this, "NFC is not enabled on this device.",
                    Toast.LENGTH_LONG).show();
            Toast.makeText(this, "On your phone go to Settings(gear icon) then \"more settings\" or \"more networks\" and look for word \"NFC\"." ,
                    Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Make sure NFC is set to enabled. For specific phone instructions Google it.",
                    Toast.LENGTH_LONG).show();
            //textView4.setVisibility(View.VISIBLE);



        }
        /******************************************************************************************/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(
//                new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        }
//        );
    }


    /**************** Method to Send user to Activity based on what button they press *************/
    public void onClick(View v){
        if(v.getId() == R.id.buttonFindEgg){
            Intent intent = new Intent(getApplicationContext(), FindEggActivity.class);
            startActivity(intent);

        }else if(v.getId() == R.id.buttonHowToPlay){
            Intent intent = new Intent(getApplicationContext(), HowToPlay.class);
            startActivity(intent);
        } else if(v.getId() == R.id.buttonBuyNfcEggs) {
            Intent intent = new Intent(getApplicationContext(), BuyNfcTags.class);
            startActivity(intent);
        }

    }
    /*******************************************************************************************/


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
