package com.xiangzi.gear.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.xiangzi.gear.R;

import java.util.ArrayList;
import java.util.List;


public class InvoluteGear {

    private Context context;
    private PointF centerF;
    private Path gearPathLine;
    private Path gearPathArc;
    private Path gearPathSide;
    private double sportAngular;
    private double breadth ;

    private double m,z,ha,c,x,alpha,r,ra,rf,rb,p,s,beta,alphake;

    private double diameter;

    public InvoluteGear(PointF centerF, double diameter, double startAngular, Context context){
        this.centerF = centerF;
        this.context = context;
        this.diameter = diameter;
        getParams();
        gearPathLine = new Path();
        gearPathArc = new Path();
        gearPathSide = new Path();
        setSportAngular(startAngular,centerF);
    }

    public double getSportAngular() {
        return sportAngular;
    }

    public void setSportAngular(double sAgular ,PointF centerF) {
        this.sportAngular = sAgular;
        this.centerF = centerF;
        setGearPath();
    }
    public PointF getCenterF() {
        return centerF;
    }

    private void getParams(){
        m=1;//模数
        z=12;//齿数
        ha=1;c=0.25;x=0;//齿顶高系数;顶隙系数;变位系数
        alpha=Math.toRadians(20);//压力角
        m = 2*diameter/z;
        r=z*m/2;//分度圆半径
        ra=r+(ha+x)*m;//齿顶圆半径
        rf=r-(ha+c-x)*m;//齿根圆半径
        rb=r*Math.cos(alpha);//基圆半径
        p=Math.PI*m;//齿距
        s=p/2+2*x*m*Math.tan(alpha);//齿厚
        double beta1=involute(Math.acos(rb/r));
        double beta2=s/(2*r);
        beta=beta1+beta2;
        alphake = Math.acos(rb/ra);
//        integration();
    }
    public static double involute(double x){
        double v=Math.tan(x)-x;
        return v;
    }
    private void setGearPath() {
        gearPathLine.reset();
        gearPathArc.reset();
        gearPathSide .reset();
        for (double a = 0;a<2*Math.PI;a+=2*Math.PI/z){
            PointF[] fs = getSevenPoint(a+sportAngular);
            if(a ==0){
                gearPathLine.moveTo(fs[0].x,fs[0].y);
                gearPathSide.moveTo(fs[0].x,fs[0].y);
            }
            gearPathLine.lineTo(fs[0].x,fs[0].y);
            gearPathSide.lineTo(fs[0].x,fs[0].y);
            gearPathLine.lineTo(fs[1].x,fs[1].y);
            gearPathSide.lineTo(fs[1].x,fs[1].y);

//            gearPathLine.lineTo(fs[2].x,fs[2].y);
//            gearPathSide.lineTo(fs[2].x,fs[2].y);
            PointF bp = getAxis(-beta+a+sportAngular,r);
            gearPathLine.quadTo(bp.x,bp.y,fs[2].x,fs[2].y);
            gearPathSide.quadTo(bp.x,bp.y,fs[2].x,fs[2].y);
//            integration12(fs[2],gearPathLine,gearPathSide,a+sportAngular);
            gearPathLine.lineTo(fs[3].x,fs[3].y);
            gearPathSide.moveTo(fs[3].x,fs[3].y);
            if(a<2*Math.PI){
                RectF oval23 =  new RectF((float) (centerF.x-ra),(float)(centerF.y-ra),(float)(centerF.x+ra),(float)(centerF.y+ra));
                gearPathArc.addArc(oval23,
                        (float) Math.toDegrees(involute(alphake)-beta+a+sportAngular),
                        (float) -Math.toDegrees(2*(involute(alphake)-beta)));
                gearPathSide.addArc(oval23,
                        (float) Math.toDegrees(involute(alphake)-beta+a+sportAngular),
                        (float) -Math.toDegrees(2*(involute(alphake)-beta)));
            }
//            gearPathLine.lineTo(fs[4].x,fs[4].y);
//            gearPathSide.lineTo(fs[4].x,fs[4].y);
            PointF bp2 = getAxis(beta+a+sportAngular,r);
            gearPathLine.quadTo(bp2.x,bp2.y,fs[4].x,fs[4].y);
            gearPathSide.quadTo(bp2.x,bp2.y,fs[4].x,fs[4].y);
//            integration34(fs[4],gearPathLine,gearPathSide,a+sportAngular);


            gearPathLine.lineTo(fs[5].x,fs[5].y);
            gearPathSide.lineTo(fs[5].x,fs[5].y);
            if(a<2*Math.PI){
                gearPathLine.lineTo(fs[6].x,fs[6].y);
                RectF oval56 =  new RectF((float) (centerF.x-rf),(float)(centerF.y-rf),(float)(centerF.x+rf),(float)(centerF.y+rf));
                gearPathArc.addArc(oval56,
                        (float) Math.toDegrees(beta+a+sportAngular),
                        (float) Math.toDegrees(2*Math.PI/z-2*beta));
                gearPathSide.addArc(oval56,
                        (float) Math.toDegrees(beta+a+sportAngular),
                        (float) Math.toDegrees(2*Math.PI/z-2*beta));
            }
        }
        gearPathLine.close();
    }

    private PointF[] getSevenPoint(double startAngler) {
        PointF[] pointFS = new PointF[7];
        PointF p0 = getAxis(-beta+startAngler,rf);
        PointF p1 = getAxis(-beta+startAngler,rb);
        PointF p2 = getAxis(involute(alphake)-beta+startAngler,ra);
        PointF p3 = getAxis(-(involute(alphake)-beta)+startAngler,ra);
        PointF p4 = getAxis(beta+startAngler,rb);
        PointF p5 = getAxis(beta+startAngler,rf);
        PointF p6 = getAxis(-beta+startAngler+2*Math.PI/z,rf);

        pointFS[0] = p0;
        pointFS[1] = p1;
        pointFS[2] = p2;
        pointFS[3] = p3;
        pointFS[4] = p4;
        pointFS[5] = p5;
        pointFS[6] = p6;

        Log.e("pointFS",pointFS.toString());
        return pointFS;
    }

    List<InvoluteAR> fristIntegrations;
    public class InvoluteAR{
        public double a;
        public double r;
    }
    private void integration() {
        fristIntegrations = new ArrayList<>();
        for (double i=0;i<alphake;i+=0.01){
            double a = involute(i) -beta ;
            double r = rb/Math.cos(i) ;
            InvoluteAR involuteAR = new InvoluteAR();
            involuteAR.a =a;
            involuteAR.r =r;
            fristIntegrations.add(involuteAR);

        }

    }
    private void integration12(PointF end,Path gearPathLine ,Path gearPathSide,double a) {
        for(int i=0;i<fristIntegrations.size();i++){
            PointF f  = getAxis(fristIntegrations.get(i).a+a, fristIntegrations.get(i).r);
            gearPathLine.lineTo(f.x,f.y);
            gearPathSide.lineTo(f.x,f.y);
        }
        gearPathLine.lineTo(end.x,end.y);
        gearPathSide.lineTo(end.x,end.y);
    }
    private void integration34(PointF end,Path gearPathLine ,Path gearPathSide,double a) {
        for(int i=0;i<fristIntegrations.size();i++){
            PointF f  = getAxis(fristIntegrations.get(i).a, fristIntegrations.get(i).r);
            f.y = -f.y;
            double na = Math.atan2(f.y-centerF.y,f.x-centerF.x);
            PointF fn = getAxis(na+a, fristIntegrations.get(i).r);
            gearPathLine.lineTo(fn.x,fn.y);
            gearPathSide.lineTo(fn.x,fn.y);
        }
        gearPathLine.lineTo(end.x,end.y);
        gearPathSide.lineTo(end.x,end.y);
    }
    private PointF getAxis(double a ,double r) {
        //x=ρcosθ，y=ρsinθ
        PointF p = new PointF();
        p.x = (float) (r*Math.cos(a)+centerF.x);
        p.y = (float) (r*Math.sin(a)+centerF.y);
        return p;
    }
    public Path getGearPathLine() {
        return gearPathLine;
    }
    public Path getGearPathArc() {
        return gearPathArc;
    }
    public void draw(Canvas canvas, Paint paint1,Paint paint2){
        canvas.drawPath(getGearPathLine(),paint1);
        canvas.drawPath(getGearPathArc(),paint1);
        Paint paint3  = new Paint();
        paint3.setColor(context.getResources().getColor(R.color.black));
        paint3.setStrokeWidth(2);
        paint3.setStyle(Paint.Style.STROKE);
        canvas.drawPath(gearPathSide,paint3);
        canvas.drawCircle(centerF.x,centerF.y, (float) diameter/6,paint2);
        canvas.drawCircle(centerF.x,centerF.y, (float) diameter/6,paint3);
    }
}
