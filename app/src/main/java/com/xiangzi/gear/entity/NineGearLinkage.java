package com.xiangzi.gear.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class NineGearLinkage {
    private Shell shell;
    private List<Gear> gears  = new ArrayList<>();


    private float angleV_S; //外壳∠
    private float angleV_C; //中心∠

    private float angleV_G = 0; //短边齿轮∠
    private float angleV_G2 = 0;//长边齿轮∠

    private Context context;
    private PointF centerF;
    private float diameter;


    public static final float loseV = 0.001f;


    public Shell getShell() {
        return shell;
    }

    public NineGearLinkage(Context context, PointF centerF, float diameter ){
        this.centerF = centerF;
        this.context = context;
        this.diameter = diameter;
        shell = new Shell(centerF,diameter,0);
        setAngledV(0,0);

    }
    private void setGears() {
        gears.clear();

        double r = diameter/Math.sqrt(2);
        for(int i =0;i<shell.getFixPoints().size();i++){
            Gear gear = null;

            if (i==0){
                gear = new Gear(shell.getFixPoints().get(i), (float) (r),angleV_C ,context);
            }else {
                gear = new Gear(shell.getFixPoints().get(i), (float) (r), (i%2==0) ? angleV_G : angleV_G2,context);
            }
            gears.add(gear);
        }
    }

    public void draw(Canvas canvas,  Paint paint1, Paint paint2 ,Paint paintS){
        for(Gear gear:gears){
            gear.draw(canvas,diameter/6,paint1,paint2);
        }
        shell.drawShell(canvas,paintS);
    }

    public void setAngledV(float angleV_dS ,float angleV_dC ) {
        this.angleV_S += angleV_dS;
        this.angleV_C += angleV_dC;
        float r = (float) (diameter/Math.sqrt(2)/2);
        float d = (float) (diameter/2*Math.sqrt(2));

        if(angleV_dS==0){
            float angleV_dG = -angleV_dC;
            this.angleV_G += angleV_dG;
            this.angleV_G2 += angleV_dC;
        }else {
            float angleV_dG = angleV_dS*(d/r);
            this.angleV_G += angleV_dG;
            this.angleV_G2 += angleV_dC;
        }


        shell.setSportAngular(angleV_S);
        setGears();

    }
    public void setAngleV(float angleV_S1 ,float angleV_C ) {

        this.angleV_C = angleV_C;
        float r = (float) (diameter/Math.sqrt(2)/2);
        float d = (float) (diameter/2*Math.sqrt(2));

        if(angleV_S1==0){
            this.angleV_G = -angleV_C;
            this.angleV_G2 = angleV_C;
        }else {
            this.angleV_S = angleV_S1;
            this.angleV_G =  angleV_S*(d/r);
            this.angleV_G2 = angleV_C;
        }


        shell.setSportAngular(angleV_S);
        setGears();

    }

}
