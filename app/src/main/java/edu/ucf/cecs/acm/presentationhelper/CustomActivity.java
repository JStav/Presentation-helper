package edu.ucf.cecs.acm.presentationhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


public class CustomActivity extends Activity {

    int screenWidth, screenHeight;

    LinearLayout editableViewGroup;
    WindowManager screenObject;
    Display display;
    DisplayMetrics displayMetrics;
    Presentation_Structure presentation, current;
    final int   MAX_GAP = 10,
            TOP_MARGIN = 50;
    int width, gap, sliceHeight, perWidth, totalSlides;

    Button customButton, hiddenButton;
    CustomEditText view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        presentation = PipeObjects.pipe;


        editableViewGroup = (LinearLayout) findViewById(R.id.editableViewGroup);

        editableViewGroup.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        customButton = (Button) findViewById(R.id.customButton);

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current = presentation;
                totalSlides = presentation.totalSlides;

                while(current!=null){
                    view = (CustomEditText)findViewById(521450000+current.slideId());
                    current.setDuration(Integer.parseInt(view.getText().toString()));
                    current = current.nextSlide;
                }

                PipeObjects.pipe = presentation;

                Intent intent = new Intent(CustomActivity.this, NewPresentationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
    }
    @Override
    protected void onResume(){
        super.onResume();

        view = new CustomEditText(this);

        current = presentation;
        totalSlides = presentation.totalSlides;

        hiddenButton = new Button(this);

        while(current!=null){

            view = new CustomEditText(this);
            LinearLayout.LayoutParams params;

            editableViewGroup.addView(view);

            view.setHint("Edit Me");
            view.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME );
            view.setText(Integer.toString(current.getDuration()));
            view.setEnabled(true);
            view.setId(521450000 + current.slideId());
            view.setBackgroundColor(Color.parseColor("#537FA6"));
            view.setLayoutParams(new LinearLayout.LayoutParams(500, 120));
            params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(10, TOP_MARGIN, 10, 0);
            view.setLayoutParams(params);

            current = current.nextSlide;
        }

        //hiddenButton.setBackgroundColor(Color.parseColor("#00000000"));
        hiddenButton.setVisibility(View.INVISIBLE);
        editableViewGroup.addView(hiddenButton);



    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_custom, menu);
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
