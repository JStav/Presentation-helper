package edu.ucf.cecs.acm.presentationhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NewPresentationActivity extends Activity {

    private static final String TAG = "NewPresentationActivity";

    PresentationDatabase presentationDatabase = null;
    PresentationStructure presentation = null;
    CharSequence totalDurationData = null, totalSlidesData = null;
    LinearLayout timeGraphLayout;
    TextView presentationName, totalDuration, totalSlides;
    TimeGraph timeGraph;
    CheckBox equallyDivided;
    Button  customAlertButton,
            createPresentationButton;
    int timeinSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_presentation);


        //divide equally checkbox
        equallyDivided = (CheckBox) findViewById(R.id.divideEquallyCheck);
        equallyDivided.setChecked(true);
        equallyDivided.setEnabled(false);

        //custom presentation button
        customAlertButton = (Button) findViewById(R.id.customAlertButton);
        customAlertButton.setEnabled(false);
        createPresentationButton = (Button) findViewById(R.id.createPresentationButton);

        //get UI Components
        presentationName = (TextView) findViewById(R.id.newPresentationName);
        totalDuration = (TextView) findViewById(R.id.totalDuration);
        totalSlides = (TextView) findViewById(R.id.totalSlides);
        timeGraphLayout = (LinearLayout) findViewById(R.id.timeGraphLayout);

        //Object to call graph drawing methods
        //timeGraph = new TimeGraph(timeGraphLayout);


        //CustomAlertButton clicked, call Custom Activity
        customAlertButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //Trigger Intent to CustomActivity to cutomize the time per slides
                Intent intent = new Intent(NewPresentationActivity.this, CustomActivity.class);
                NewPresentationActivity.this.startActivity(intent);

            }
        });

        //CreatePresentationButton clicked, create presentation entry into database
        createPresentationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                //Perform validations
                //Presentation name cannot have # / . : ? symbols


                //Saving presentation into database
                presentationDatabase = new PresentationDatabase(NewPresentationActivity.this);
                if(presentationDatabase.insertPresentationDatabase( presentationName.getText().toString(), PresentationStructure.generateStringFromSlides())){
                    Log.d(TAG, "Presentation data was inserted successfully!");
                }

                Intent intent = new Intent( NewPresentationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();

            }
        });

        //Check if the equallyDivided checkBox value is changed
        equallyDivided.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //timeGraph.fillPeriod(NewPresentationActivity.this, presentation);

                //Enable customActivityButton only when divideEqually is checked
                if (isChecked) {
                    customAlertButton.setEnabled(false);

                    if(createPresentationObject())
                         timeGraph.fillPeriod(NewPresentationActivity.this, presentation);

                } else {
                    customAlertButton.setEnabled(true);
                }
            }
        });


        //Check if the totalDuration - editText value is changed or not
        totalDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //check if textView is not empty else use 1(default)
               if(s.length()!=0 && Integer.parseInt(s.toString())!=0)
                    totalDurationData = s;
                else
                    totalDurationData = "1";


                if(!PresentationStructure.customTimeShared && createPresentationObject()){
                    equallyDivided.setEnabled(true);
                    timeGraph.fillPeriod(NewPresentationActivity.this, presentation);
                }

            }


            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Check if the totalSlides - editText value is changed or not
        totalSlides.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //check if textView is not empty else use 1(default)
                if(s.length()!=0 && Integer.parseInt(s.toString())!=0)
                    totalSlidesData = s;
                else
                    totalSlidesData = "1";

                if(createPresentationObject()){
                    equallyDivided.setEnabled(true);
                    timeGraph.fillPeriod(NewPresentationActivity.this, presentation);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    @Override
    protected void onStart(){
        super.onStart();


    }

    @Override
    protected void onResume()
    {
        super.onResume();


        //check if presentation object is initialised or not
        //if yes --> current Activity returned from custom Activity
        // update the totalDuration textView
        if(presentation!=null){
            totalDuration.setText(Integer.toString(PresentationStructure.getTotalDuration()));
            timeGraph.fillPeriod(NewPresentationActivity.this, presentation);
        }


        //This event is triggered when all the UI elements are inflated
        timeGraphLayout.post(new Runnable() {
            @Override
            public void run() {
                /*if (timeGraph != null && presentation!= null) {
                    timeGraph.fillPeriod(NewPresentationActivity.this, presentation);
                }*/
                timeGraph = new TimeGraph(timeGraphLayout);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_presentation, menu);
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

    public boolean createPresentationObject(){

        if(totalDurationData != null && totalSlidesData != null){

            timeinSec = Integer.parseInt(totalDurationData.toString());
            presentation = PresentationStructure.getPresentation(timeinSec, Integer.parseInt(totalSlidesData.toString()));

            return true;
        }
        return false;
    }

}
