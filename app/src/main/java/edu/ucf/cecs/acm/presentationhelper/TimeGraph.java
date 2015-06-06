package edu.ucf.cecs.acm.presentationhelper;


import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by kishoredebnath on 27/02/15.
 */
public class TimeGraph {


    int width, gap, sliceHeight, perWidth, totalSlides;
    private static String LOG_TAG = "TimeGraph";
    LinearLayout mainLayout;

    private final int   MAX_GAP = 10,
                         TOP_MARGIN = 100;

    public TimeGraph(LinearLayout timeGraphLayout){

        mainLayout = timeGraphLayout;
        gap = MAX_GAP;
    }


    public void fillPeriod(Activity context, Presentation_Structure presentation){

        if (presentation == null){
            return;
        }

        Presentation_Structure current = presentation;

        totalSlides = presentation.totalSlides;

        width = mainLayout.getWidth();

        do{
            perWidth = width / totalSlides - gap;
            if(perWidth>gap)
                break;
            gap--;
            if(gap<=0) {
                gap = 1;
                break;
            }

        }while(true);


        Log.i(LOG_TAG, "View width: " + Integer.toString(perWidth));

        sliceHeight = 180/presentation.maxDuration;
        Log.i(LOG_TAG, "View Height: " + Integer.toString(width));

        mainLayout.removeAllViews();


        while(current!=null){

            View view = new View(context);
            LinearLayout.LayoutParams params;

            mainLayout.addView(view);

            view.setId(511450000+current.slideId());
            view.setBackgroundColor(Color.parseColor("#537FA6"));
            view.setLayoutParams(new LinearLayout.LayoutParams(perWidth, current.getDuration()*sliceHeight));
            params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(gap/2, TOP_MARGIN, gap/2, 0);
            view.setLayoutParams(params);

            current = current.nextSlide;

        }

    }

    public void fillPeriodCustom(){

        Log.i(LOG_TAG, "TimeGraph function working!");

    }
/*
    private View drawTimeGraph(final int position, View convertView, ViewGroup parent){
        View row = null;

        LayoutInflater inflater = context.getLayoutInflater();
        row = inflater.inflate(R.layout.simplerow, null);


        highest = Collections.max(slidePeriod);

        return row;
    }
*/
}
