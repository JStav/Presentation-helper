package edu.ucf.cecs.acm.presentationhelper;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    public Vibrator vibe;
    public Vibrator warning;
    public Vibrator change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

    }

    //vibrations for a 10 second warning, and to change slides. warning for 3 quick vibrations. change for one longer vibration.
    public void VibrationCalls(View v) {
        long[] warn = {50,50,50};
        warning = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        change = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        warning.vibrate(warn, -1);
        change.vibrate(600);
    }

    public void onClickNew(View v){
        //vibe.vibrate(25);
    }

    public void onClickEdit(View v){
        //vibe.vibrate(25);
    }

    public void onClickStart(View v){
        //vibe.vibrate(25);

        Intent startPresIntent = new Intent(MainActivity.this, StartPresentationActivity.class);
        startActivity(startPresIntent);

    }

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
