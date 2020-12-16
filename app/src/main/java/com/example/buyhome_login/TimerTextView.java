package com.example.buyhome_login;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;

public class TimerTextView extends androidx.appcompat.widget.AppCompatTextView {

    private int mViewWidth = 0;
    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mTranslate = 0;

    public void setDrutime(long time) {
        this.drutime = time*60*1000;
    }

    private long drutime;

    public TimerTextView(Context context) {
        super(context);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        SimpleDateFormat sdf=new SimpleDateFormat("mm:ss.SS");
        if(drutime>=0){
            String text=sdf.format(drutime);
            setText(text);
            drutime=drutime-16;
        }


    }
}