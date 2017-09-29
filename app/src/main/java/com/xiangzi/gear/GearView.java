package com.xiangzi.gear;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.xiangzi.gear.entity.Gear;
import com.xiangzi.gear.entity.NineGearLinkage;

/**
 * Created by Administrator on 2017/9/26.
 */

public class GearView extends View {
    float angle = 0;
    Gear gear;
    Paint paint1, paint2;

    public GearView(Context context) {
        super(context);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        gear = new Gear(new PointF(width/2,height/2),300,0,context);
        paint1  = new Paint();
        paint1.setColor(getResources().getColor(R.color.colorPrimary));
        paint1.setStrokeWidth(4);
        paint1.setStyle(Paint.Style.FILL);
        paint2  = new Paint();
        paint2.setColor(getResources().getColor(R.color.white));
        paint2.setStrokeWidth(4);
        paint2.setStyle(Paint.Style.FILL);
        startThread();
    }

    public GearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gear.draw(canvas,100,paint1,paint2);
    }

    Handler handler = new Handler();
    private void startThread() {
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                angle+=(float) (Math.PI/100);
                gear.setSportAngular(angle);
                invalidate();
                handler.postDelayed(this,5);
            }
        };
        handler .postDelayed(runnable,0);

    }
}
