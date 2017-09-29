package com.xiangzi.gear.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class Shell {
    private  float r;
    private PointF centerF;
    private float diameter;
    private List<PointF> fixPoints= new ArrayList<>();;
    private float sportAngular;
    List<Path> shellPaths = new ArrayList<>();
    public Shell(PointF centerF,float diameter,float sportAngular){
        this.centerF = centerF;
        this.diameter = diameter;
        r = diameter/5;
        setSportAngular(sportAngular);
    }
    public void setSportAngular(float sportAngular) {
        this.sportAngular = sportAngular;
        setFixPoints();
    }
    /*
    5  6  7
    4  0  8
    3  2  1
     */
    private void setFixPoints() {
        fixPoints .clear();

        float d = (float) (diameter/Math.sqrt(2));
        fixPoints.add(centerF);
        for (int i =1 ;i<9 ; i++ ){
            float j = (i%2==0) ? d : diameter;
            PointF p = new PointF((float) (centerF.x+ ( j*Math.cos(sportAngular+ Math.PI*i/4))),(float) (centerF.y+j*Math.sin(sportAngular+ Math.PI*i/4)));
            fixPoints.add(p);
        }

    }

    public float getDiameter() {
        return diameter;
    }
    public float getR() {
        return r;
    }

    public PointF getCenterF() {
        return centerF;
    }

    public List<PointF> getFixPoints() {
        return fixPoints;
    }

    public void drawShell(Canvas canvas, Paint paint){


        for(PointF f :fixPoints){
            canvas.drawCircle(f.x,f.y,r,paint);
        }
        shellPaths.clear();
        List<PointF[]> list1 = new ArrayList<>();
        PointF[] p1_2 = new PointF[]{fixPoints.get(1),fixPoints.get(2)};
        PointF[] p2_3 = new PointF[]{fixPoints.get(2),fixPoints.get(3)};
        PointF[] p6_5 = new PointF[]{fixPoints.get(6),fixPoints.get(5)};
        PointF[] p7_6 = new PointF[]{fixPoints.get(7),fixPoints.get(6)};
        list1.add(p1_2);list1.add(p2_3);list1.add(p6_5);list1.add(p7_6);
        for(PointF[] pointF :list1){
            Path path = new Path();
            path.moveTo((float) (pointF[0].x+r*Math.cos(Math.PI/2+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI/2+sportAngular)));
            path.quadTo((pointF[1].x+pointF[0].x)/2,(pointF[1].y+pointF[0].y)/2,
                    (float) (pointF[1].x+r*Math.cos(Math.PI/2+sportAngular)), (float) (pointF[1].y+r*Math.sin(Math.PI/2+sportAngular)));
            path.lineTo(  (float) (pointF[1].x+r*Math.cos(Math.PI*3/2+sportAngular)), (float) (pointF[1].y+r*Math.sin(Math.PI*3/2+sportAngular)));
            path.quadTo((pointF[1].x+pointF[0].x)/2,(pointF[1].y+pointF[0].y)/2,
                    (float) (pointF[0].x+r*Math.cos(Math.PI*3/2+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI*3/2+sportAngular)));
            path.lineTo( (float) (pointF[0].x+r*Math.cos(Math.PI/2+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI/2+sportAngular)));
            shellPaths.add(path);
        }
        List<PointF[]> list2 = new ArrayList<>();
        PointF[] p1_8 = new PointF[]{fixPoints.get(1),fixPoints.get(8)};
        PointF[] p8_7 = new PointF[]{fixPoints.get(8),fixPoints.get(7)};
        PointF[] p3_4 = new PointF[]{fixPoints.get(3),fixPoints.get(4)};
        PointF[] p4_5 = new PointF[]{fixPoints.get(4),fixPoints.get(5)};
        list2.add(p1_8);list2.add(p8_7);list2.add(p3_4);list2.add(p4_5);
        for(PointF[] pointF :list2){
            Path path = new Path();
            path.moveTo((float) (pointF[0].x+r*Math.cos(Math.PI+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI+sportAngular)));
            path.quadTo((pointF[1].x+pointF[0].x)/2,(pointF[1].y+pointF[0].y)/2,
                    (float) (pointF[1].x+r*Math.cos(Math.PI+sportAngular)), (float) (pointF[1].y+r*Math.sin(Math.PI+sportAngular)));
            path.lineTo(  (float) (pointF[1].x+r*Math.cos(0+sportAngular)), (float) (pointF[1].y+r*Math.sin(0+sportAngular)));
            path.quadTo((pointF[1].x+pointF[0].x)/2,(pointF[1].y+pointF[0].y)/2,
                    (float) (pointF[0].x+r*Math.cos(0+sportAngular)), (float) (pointF[0].y+r*Math.sin(0+sportAngular)));
            path.lineTo( (float) (pointF[0].x+r*Math.cos(Math.PI+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI+sportAngular)));
            shellPaths.add(path);
        }
        List<PointF[]> list3 = new ArrayList<>();
        PointF[] p5_0 = new PointF[]{fixPoints.get(5),fixPoints.get(0)};
        PointF[] p0_1 = new PointF[]{fixPoints.get(0),fixPoints.get(1)};
        list3.add(p5_0);list3.add(p0_1);
        for(PointF[] pointF :list3){
            Path path = new Path();
            path.moveTo((float) (pointF[0].x+r*Math.cos(Math.PI*7/4+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI*7/4+sportAngular)));
            path.quadTo((pointF[1].x+pointF[0].x)/2,(pointF[1].y+pointF[0].y)/2,
                    (float) (pointF[1].x+r*Math.cos(Math.PI*7/4+sportAngular)), (float) (pointF[1].y+r*Math.sin(Math.PI*7/4+sportAngular)));
            path.lineTo(  (float) (pointF[1].x+r*Math.cos(Math.PI*3/4+sportAngular)), (float) (pointF[1].y+r*Math.sin(Math.PI*3/4+sportAngular)));
            path.quadTo((pointF[1].x+pointF[0].x)/2,(pointF[1].y+pointF[0].y)/2,
                    (float) (pointF[0].x+r*Math.cos(Math.PI*3/4+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI*3/4+sportAngular)));
            path.lineTo( (float) (pointF[0].x+r*Math.cos(Math.PI*7/4+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI*7/4+sportAngular)));
            shellPaths.add(path);
        }
        List<PointF[]> list4 = new ArrayList<>();
        PointF[] p7_0 = new PointF[]{fixPoints.get(7),fixPoints.get(0)};
        PointF[] p0_3 = new PointF[]{fixPoints.get(0),fixPoints.get(3)};
        list4.add(p7_0);list4.add(p0_3);
        for(PointF[] pointF :list4){
            Path path = new Path();
            path.moveTo((float) (pointF[0].x+r*Math.cos(Math.PI*5/4+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI*5/4+sportAngular)));
            path.quadTo((pointF[1].x+pointF[0].x)/2,(pointF[1].y+pointF[0].y)/2,
                    (float) (pointF[1].x+r*Math.cos(Math.PI*5/4+sportAngular)), (float) (pointF[1].y+r*Math.sin(Math.PI*5/4+sportAngular)));
            path.lineTo(  (float) (pointF[1].x+r*Math.cos(Math.PI/4+sportAngular)), (float) (pointF[1].y+r*Math.sin(Math.PI/4+sportAngular)));
            path.quadTo((pointF[1].x+pointF[0].x)/2,(pointF[1].y+pointF[0].y)/2,
                    (float) (pointF[0].x+r*Math.cos(Math.PI/4+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI/4+sportAngular)));
            path.lineTo( (float) (pointF[0].x+r*Math.cos(Math.PI*5/4+sportAngular)), (float) (pointF[0].y+r*Math.sin(Math.PI*5/4+sportAngular)));
            shellPaths.add(path);
        }
        for (Path path :shellPaths){
            canvas.drawPath(path,paint);
        }
    }
}
