package com.xiangzi.gear.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import com.xiangzi.gear.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class Gear {

    private Context context;
    private PointF centerF;
    private float diameter;
    private float sportAngular;
    private double breadth ;
    private Path gearPath;

    private List<BreadthP> breadthP = new ArrayList<>();


    public class BreadthP{
        public  PointF f;
        public float angular;

        public BreadthP(PointF f,float angular){
            this.f =f;
            this.angular = angular;
        }

        @Override
        public String toString() {
            return "BreadthP{" +
                    "f=" + f +
                    ", angular=" + angular +
                    '}';
        }
    }

    public Gear(PointF centerF, float diameter, float startAngular, Context context){

        this.centerF = centerF;
        this.diameter = diameter;
        this.breadth = Math.PI/20;
        this.context = context;
        gearPath = new Path();
        setSportAngular(startAngular);

    }



    public PointF getCenterF() {
        return centerF;
    }


    public float getDiameter() {
        return diameter;
    }


    public List<BreadthP> getBreadthP() {
        return breadthP;
    }

    public void setSportAngular(float sAgular) {
        this.sportAngular = sAgular;
        float angular = sportAngular;
        breadthP.clear();
        while (angular<(2*Math.PI)+sportAngular){
            PointF p = new PointF((float)( centerF.x+(diameter/2)*Math.cos(angular)),(float) (centerF.y+(diameter/2)*Math.sin(angular)));
            breadthP.add(new BreadthP(p,angular));
            angular+=breadth;
        }
        setGearPath();
    }

    private void setGearPath() {

        gearPath.reset();
        gearPath.moveTo(breadthP.get(0).f.x,breadthP.get(0).f.y);

        for(int i =0;i<breadthP.size();i++){
            int j ;
            float angulaQ;
            if(i<breadthP.size()-1){
                j = i+1;
                angulaQ = (breadthP.get(i).angular+breadthP.get(j).angular)/2;
            }else {
                j = 0;
                angulaQ = (float) ((breadthP.get(i).angular+breadthP.get(j).angular+2*Math.PI)/2);
            }

            float b = (float) Math.sqrt((breadthP.get(j).f.x-breadthP.get(i).f.x)*(breadthP.get(j).f.x-breadthP.get(i).f.x)+
                    (breadthP.get(j).f.y-breadthP.get(i).f.y)*(breadthP.get(j).f.y-breadthP.get(i).f.y));
            float x1 = (breadthP.get(j).f.x+breadthP.get(i).f.x)/2;
            float y1 = (breadthP.get(j).f.y+breadthP.get(i).f.y)/2;
            float d = (float) ((float) Math.sqrt((x1-centerF.x)*(x1-centerF.x) +(y1-centerF.y)*(y1-centerF.y))+
                    ( (i%2==0) ? (-b) : b )
            );
            float quadPx =(float)( centerF.x+d*Math.cos(angulaQ));
            float quadPy =(float)( centerF.y+d*Math.sin(angulaQ));

            gearPath.quadTo(quadPx,quadPy,breadthP.get(j).f.x,breadthP.get(j).f.y);
        }

    }
    public Path getGearPath() {
        return gearPath;
    }

    public void draw(Canvas canvas,float circleDiameter, Paint paint1,Paint paint2){
        canvas.drawPath(getGearPath(),paint1);
        canvas.drawCircle(centerF.x,centerF.y,circleDiameter,paint2);
    }
}
