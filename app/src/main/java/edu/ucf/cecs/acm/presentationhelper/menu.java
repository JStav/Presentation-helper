package edu.ucf.cecs.acm.presentationhelper;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Fred Gravil on 3/24/2015.
 */
public class menu  extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
