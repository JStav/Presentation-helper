package edu.ucf.cecs.acm.presentationhelper;

import android.app.Notification;
import android.content.Context;
import android.media.AudioManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import static android.os.SystemClock.sleep;


public class StartPresentationActivity extends ActionBarActivity {

    // -1 = uninitialized, 0 = silent, 1 = vibrate, >1 = volume levels
    int prevSilenceMode = -1;
    AudioManager audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_presentation);

        // Used for silence() and unsilence()
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_presentation, menu);
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



    public void silence(){

        prevSilenceMode = audio.getRingerMode();
        audio.setRingerMode(0);

    }


    public void unsilence(){

        // If initialized
        if(prevSilenceMode >= 0) {
            audio.setRingerMode(prevSilenceMode);
        }

    }



}
