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
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;

import com.xiangzi.gear.entity.Gear;
import com.xiangzi.gear.entity.NineGearLinkage;

/**
 * Created by Administrator on 2017/9/26.
 */

public class GearLinkView extends View {
    private float angle = 0;
    private Gear gear;
    private NineGearLinkage nineGearLinkage;
    private Paint paint1, paint2 ,paint3;
    double dV = 0;
    private PointF centerF;
    private VelocityTracker mTracker;
    private GestureDetector ges;
    private final float diameter = 250;
    private boolean isOut = false;
    public GearLinkView(Context context) {
        super(context);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        centerF = new PointF(width/2,height/2);
        nineGearLinkage = new NineGearLinkage(context,new PointF(width/2,height/2),diameter);
        paint1  = new Paint();
        paint1.setColor(getResources().getColor(R.color.colorPrimary));
        paint1.setStrokeWidth(4);
        paint1.setStyle(Paint.Style.FILL);
        paint2  = new Paint();
        paint2.setColor(getResources().getColor(R.color.white));
        paint2.setStrokeWidth(4);
        paint2.setStyle(Paint.Style.FILL);
        paint3  = new Paint();
        paint3.setColor(getResources().getColor(R.color.colorAccent));
        paint3.setStrokeWidth(4);
        paint3.setStyle(Paint.Style.FILL);
//        startThread();
    }

    public GearLinkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GearLinkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GearLinkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        nineGearLinkage.draw(canvas,paint1,paint2,paint3);
    }

    Handler handler = new Handler();
    Runnable runnable =new Runnable() {
        @Override
        public void run() {
            dV = dV*(1-NineGearLinkage.loseV);
            if(Math.abs(dV)<0.00001f){
                dV = 0;
            }else {
                angle+=(float) dV;
                if(isOut){
                    nineGearLinkage.setAngledV(0 ,(float)dV);
                }else {
                    nineGearLinkage.setAngledV((float)dV,0);
                }

                invalidate();
                handler.postDelayed(this,5);
            }

        }
    };
    private void startThread() {

        handler .postDelayed(runnable,0);

    }
    float startX = 0,startY = 0 ,sA = 0,X,Y,mA = 0 ,mV = 0 ;
    @Override
    public boolean onTouchEvent(MotionEvent event){

        getTracker(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                startX = event.getX();
                float r = (float) (nineGearLinkage.getShell().getDiameter()/Math.sqrt(2)+nineGearLinkage.getShell().getR());
                boolean a  = Math.abs(centerF.x-startX)<r;
                boolean a1 = Math.abs(centerF.y-startY)<r;
                if(a&&a1){
                    isOut = false;
                }else {
                    isOut = true;
                }
                dV = 0;
                startThread();
                sA = (float) Math.atan((startY-centerF.y)/(startX-centerF.x));
                if(mTracker==null){
                    mTracker = VelocityTracker.obtain();
                }else{
                    mTracker.clear();
                }
                mTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                X = event.getX();
                Y = event.getY();
                mA = (float) Math.atan((Y-centerF.y)/(X-centerF.x));

                if(isOut){
                    nineGearLinkage.setAngleV(0,mA-sA);
                }else {
                    angle = mA-sA;
                    nineGearLinkage.setAngleV(angle ,0);
                }

                invalidate();
                mTracker.addMovement(event);
                mTracker.computeCurrentVelocity(1000);

                break;
            case MotionEvent.ACTION_UP:
                float b = Math.abs(X-startX)>Math.abs(Y-startY)? (startX-X): (Y-startY);
                mV = (b>0)? getSpeed(): -(getSpeed());
                dV = mV;
                cancelTracker();
                startThread();

                break;
        }
        return true;
    }
    private void cancelTracker(){
        if(mTracker!=null){
            mTracker.recycle();
            mTracker = null;
        }
    }

    private float getSpeed(){
        mTracker.computeCurrentVelocity(1000);
        float xSpeed = Math.abs(mTracker.getXVelocity());
        float ySpeed = Math.abs(mTracker.getYVelocity());
        float d = (float) ( Math.sqrt(xSpeed*xSpeed+ySpeed*ySpeed)/200/(Math.PI*diameter));
        return d;
    }

    private void getTracker(MotionEvent event){
        if(mTracker==null){
            mTracker = VelocityTracker.obtain();
            mTracker.addMovement(event);
        }
    }
}
