package com.xiangzi.gear.entity;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import java.util.ArrayList;
import java.util.List;


public class NineGearLinkage {
    private Shell shell;
    private List<InvoluteGear> gears  = new ArrayList<>();


    private float angleV_S; //外壳∠
    private float angleV_C; //中心∠

    private float angleV_dS; //外壳∠
    private float angleV_dC; //中心∠

    private Context context;
    private PointF centerF;
    private float diameter;


    public static final float loseV = 0.01f;


    public Shell getShell() {
        return shell;
    }

    public NineGearLinkage(Context context, PointF centerF, float diameter ){
        this.centerF = centerF;
        this.context = context;
        this.diameter = diameter;
        shell = new Shell(centerF,diameter,0,context);
        setGears();

    }
    private void setGears() {
        gears.clear();

        double r = diameter/(2*Math.sqrt(2));
        for(int i =0;i<shell.getFixPoints().size();i++){
            InvoluteGear gear = null;
            if (i==0){
                gear = new InvoluteGear(shell.getFixPoints().get(i), (float) (r),angleV_C ,context);
            }else {
                gear = new InvoluteGear(shell.getFixPoints().get(i), (float) (r), (i%2!=0) ? angleV_C : (angleV_C+2*Math.PI/24),context);
            }
            gears.add(gear);
        }
    }
    private void changeGears() {
        for(int i =0;i<gears.size();i++){
            if (i==0){
                if(angleV_dC!=0){
                    gears.get(i).setSportAngular(gears.get(i).getSportAngular()+angleV_dC,shell.getFixPoints().get(i));
                }else {
                    gears.get(i).setSportAngular( gears.get(i).getSportAngular(),shell.getFixPoints().get(i));

                }

            }else {
                if(angleV_dC!=0){
                    gears.get(i).setSportAngular((i%2!=0) ? gears.get(i).getSportAngular()+angleV_dC:gears.get(i).getSportAngular()-angleV_dC,shell.getFixPoints().get(i));
                }else {
                    gears.get(i).setSportAngular((i%2==0) ? gears.get(i).getSportAngular()+angleV_dS*2:gears.get(i).getSportAngular(),shell.getFixPoints().get(i));
                }
            }

        }

    }
    public void draw(Canvas canvas,  Paint paint1, Paint paint2 ,Paint paintS){
//        canvas.drawColor(context.getResources().getColor(R.color.black,null));
        for(InvoluteGear gear:gears){
            gear.draw(canvas,paint1,paint2);
        }
        shell.drawShell(canvas,paintS);
    }
    public void setAngleV(float angleV_S ,float angleV_C ) {

        this.angleV_dC = angleV_C - this.angleV_C;
        this.angleV_dS = angleV_S - this.angleV_S;
        this.angleV_C = angleV_C;
        this.angleV_S = angleV_S;
        shell.setSportAngular(angleV_S);
        changeGears();

    }
