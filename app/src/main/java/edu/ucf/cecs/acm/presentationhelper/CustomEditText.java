package edu.ucf.cecs.acm.presentationhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.SeekBar;


/**
 * Created by kishoredebnath on 02/04/15.
 */
public class CustomEditText extends EditText {

    private class CustomBlend extends SeekBar{
        CustomBlend(Context context){
            super(context);
        }
        CustomBlend(Context context, AttributeSet attrs){
            super(context, attrs);
        }
        CustomBlend(Context context, AttributeSet attrs, int defStyleAttr){
            super(context, attrs, defStyleAttr);
        }
    }

    private static final String TAG = "Custom Edit Text";
    private boolean dragStatus;
    GestureDetector gestureDetector;

    CustomBlend customBlend;

    CustomEditText(Context context){
        super(context);
        customBlend = new CustomBlend(context);
    }

    CustomEditText(Context context, AttributeSet attrs){
        super(context, attrs);
        customBlend = new CustomBlend(context, attrs);
    }

    CustomEditText(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        customBlend = new CustomBlend(context, attrs, defStyleAttr);
    }

    CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        customBlend = new CustomBlend(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        return super.onTouchEvent(event);
    }
}
