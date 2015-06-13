package edu.ucf.cecs.acm.presentationhelper;

import android.app.Notification;
import android.content.Context;
import android.media.AudioManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.os.SystemClock.sleep;


public class StartPresentationActivity extends ActionBarActivity {

    // -1 = uninitialized, 0 = silent, 1 = vibrate, >1 = volume levels
    int prevSilenceMode = -1;
    AudioManager audio;

    //Unique Id for all custom Groupview to be displayed
    private int groupViewId;

    private LinearLayout startActivityParentLayout;
    private PresentationDatabase presentationDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_presentation);

        groupViewId = 1;

        // Used for silence() and unsilence()
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        startActivityParentLayout = (LinearLayout) findViewById(R.id.startActivityParentLayout);


    }

    @Override
    protected void onStart(){
        super.onStart();


    }

    @Override
    protected void onResume(){
        super.onResume();

        startActivityParentLayout.post(new Runnable() {

            @Override
            public void run() {

                presentationDatabase = new PresentationDatabase(StartPresentationActivity.this);
                ArrayList<String> presentationCollections = presentationDatabase.getPresentationDatabase("*");

                if (presentationCollections != null && presentationCollections.size() > 0) {

                    int index = 0;

                    do {

                        String eachPresentation = presentationCollections.get(index);
                        String[] presentationContents = eachPresentation.split("#");

                        if (presentationContents.length == 2) {

                            String[] allSlides = presentationContents[1].split("/");

                            startActivityParentLayout.addView(createCustomLayout(presentationContents[0], "Contain " + (allSlides.length-1) + " slides"));

                        }

                        index++;

                    } while (index < presentationCollections.size());

                }
            }
        });


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


    public ViewGroup createCustomLayout(String presentationName, String otherInformation){

        if(!presentationName.equals("")&&!otherInformation.equals("")) {

            LinearLayout customLayoutGroupView = new LinearLayout(this);

            customLayoutGroupView.setOrientation(LinearLayout.VERTICAL);

            customLayoutGroupView.setId(555450000+groupViewId);
            groupViewId++;

            TextView presentationNameTextView = new TextView(this);
            presentationNameTextView.setText(presentationName);
            presentationNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

            customLayoutGroupView.addView(presentationNameTextView);

            TextView presentationOtherInfoTextView = new TextView(this);
            presentationOtherInfoTextView.setText(otherInformation);
            presentationOtherInfoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

            customLayoutGroupView.addView(presentationOtherInfoTextView);

            LinearLayout.LayoutParams customLayoutGroupViewParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            customLayoutGroupView.setLayoutParams(customLayoutGroupViewParam);

            return customLayoutGroupView;
        }

        return null;
    }



}
