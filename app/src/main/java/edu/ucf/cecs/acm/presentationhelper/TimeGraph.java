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


    private int gap;
    private double unitWidth;
    private double unitHeightInverse;
    private double thisChildHeight;
    private static String TAG = "TimeGraph";
    private LinearLayout graphLayout;
    private int totalWidth, totalHeight;
    private PresentationStructure current;
    private View childView;
    private LinearLayout.LayoutParams thisChildParams;

    private final int   MAX_GAP = 10,
                        TOP_MARGIN = 100;

    public TimeGraph(LinearLayout timeGraphLayout){

        this.graphLayout = timeGraphLayout;
        this.totalWidth = timeGraphLayout.getWidth();
        this.totalHeight = timeGraphLayout.getHeight()*2/5;
        this.gap = MAX_GAP;
    }


    public void fillPeriod(Activity context, PresentationStructure presentation){



        if(presentation != null){

            //store slide that has max. duration of all
            int maxDuration = PresentationStructure.getMaxDuration();

            //validation: check maxDuration greater than 0
            if(maxDuration>0) {

                //Divide maxDuration with 100 to get the unitHeightInverse value
                unitHeightInverse =  100/maxDuration ;

                //validation: check unitHeightInverse greater than 0
                if(unitHeightInverse >0.0){

                    gap = MAX_GAP;
                    do{

                        unitWidth = totalWidth/ PresentationStructure.getTotalSlides() - gap;
                        gap--;

                    }while(gap>unitWidth);

                    //Unit width of the graph is stored in unitWidth
                    //Unit height of the graph is stored in unitHeightInverse


                    //remove all tne previous child views from the graphLayout
                    graphLayout.removeAllViews();

                    current = presentation;

                    //Inflate the the graph with new child Views
                    while(current != null){

                        //New Graph Column object child view
                        childView = new View(context);


                        /*Set childView attributes
                        1. unique view ID derived from slideID
                        2. child view color to fill each column
                        3. calculate the height and use width information
                         */
                        childView.setId(511450000+current.slideId());
                        childView.setBackgroundColor(Color.parseColor("#537FA6"));

                        thisChildHeight = current.getDuration()*unitHeightInverse*this.totalHeight/100;

                        //New Graph Column layout object
                        thisChildParams = new LinearLayout.LayoutParams((int)Math.round(unitWidth), (int)Math.round(thisChildHeight));
                        thisChildParams.setMargins(gap/2, TOP_MARGIN, gap/2, 0);

                        //Implant Layout properties into childView
                        childView.setLayoutParams(thisChildParams);

                        //Implant childView into GraphView
                        graphLayout.addView(childView);

                        current = current.nextSlide;

                    }



                }else{
                    Log.e(TAG,"CANNOT RENDER GRAPH: Slide time is way too less to draw graph");
                }
            }else{
                Log.e(TAG,"CANNOT RENDER GRAPH: No slide with valid time value.");
            }
        }else{
            Log.e(TAG, "CANNOT RENDER GRAPH: Presentation object is null, when timeGraph object called fillPeriod method!");
        }

    }

}
