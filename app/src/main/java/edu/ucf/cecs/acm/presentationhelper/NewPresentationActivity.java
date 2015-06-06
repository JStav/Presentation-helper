package edu.ucf.cecs.acm.presentationhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    Presentation_Structure presentation = null;
    CharSequence totalDurationData = null, totalSlidesData = null;
    LinearLayout timeGraphLayout;
    TextView totalDuration, totalSlides;
    TimeGraph timeGraph;
    CheckBox equallyDivided;
    Button  customAlertButton,
            createPresentationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_presentation);

        //PipeObjects.pipe = null;

        equallyDivided = (CheckBox) findViewById(R.id.divideEquallyCheck);
        equallyDivided.setChecked(true);
        equallyDivided.setEnabled(false);

        customAlertButton = (Button) findViewById(R.id.customAlertButton);
        customAlertButton.setEnabled(false);
        createPresentationButton = (Button) findViewById(R.id.createPresentationButton);

        totalDuration = (TextView) findViewById(R.id.totalDuration);
        totalSlides = (TextView) findViewById(R.id.totalSlides);

        timeGraphLayout = (LinearLayout) findViewById(R.id.timeGraphLayout);

       /* timeGraphLayout.post(new Runnable() {
            @Override
            public void run() {
                timeGraph.fillPeriod(NewPresentationActivity.this);
            }
        });*/
        timeGraph = new TimeGraph(timeGraphLayout);

        customAlertButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewPresentationActivity.this, CustomActivity.class);
                PipeObjects.pipe = presentation;
                NewPresentationActivity.this.startActivity(intent);

            }
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

        if(PipeObjects.pipe!=null){
            presentation = PipeObjects.pipe;
        }

        //This event is triggered when all the UI elements are initially
        timeGraphLayout.post(new Runnable() {
            @Override
            public void run() {
                if (timeGraph != null) {
                    timeGraph.fillPeriod(NewPresentationActivity.this, presentation);
                }
            }
        });

        //Check if the equallyDivided - checkBox value is changed to what?
        equallyDivided.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                timeGraph.fillPeriod(NewPresentationActivity.this, presentation);

                if (isChecked) {
                    customAlertButton.setEnabled(false);
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

                totalDurationData = s;
                if(totalDurationData!=null&&totalSlidesData != null){
                    equallyDivided.setEnabled(true);
                    presentation = Presentation_Structure.createPresentation(Integer.parseInt(totalSlidesData.toString()), Integer.parseInt(totalDurationData.toString()));
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

                totalSlidesData = s;
                if(totalDurationData!=null&&totalSlidesData != null){
                    equallyDivided.setEnabled(true);
                    presentation = Presentation_Structure.createPresentation(Integer.parseInt(totalSlidesData.toString()), Integer.parseInt(totalDurationData.toString()));
                    timeGraph.fillPeriod(NewPresentationActivity.this, presentation);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
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
}
