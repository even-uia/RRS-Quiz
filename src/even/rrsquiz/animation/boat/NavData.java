/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.animation.boat;

import even.util.MyMath;
import java.awt.geom.AffineTransform;
import static java.lang.Math.PI;

/**
 *
 * @author even
 */
public class NavData
{

    private double x;
    private double y;
    private double hdg;
    private double spd;

    private AffineTransform transform;
    private int transformFrame;
    private Sailboat boat;

    public NavData(double x, double y, double hdg, double speed) {
        this.x = x;
        this.y = y;
        this.spd = speed;
        this.hdg = hdg;
    }

    public NavData(NavData that) {
        this.x = that.x;
        this.y = that.y;
        this.hdg = that.hdg;
        this.spd = that.spd;
    }

    /**
     * Make sure that hdg is always within the interval [-gybeAngle,gybeAngle]
     *
     * @param hdg in radians
     * @return a heading within the interval [-gybeAngle,gybeAngle]
     */
    public double normalize(double hdg) {
        double limit = boat.getGybeAngle();
        while (hdg < -limit)
            hdg += 2 * PI;
        while (hdg > limit)
            hdg -= 2 * PI;
        return hdg;
    }

    public double move(double dt, double a) {
        double oldSpd = spd;
        double aEff = a * dt;
        if (aEff < -oldSpd) aEff = -oldSpd;
        setSpd(oldSpd + aEff);
        double vAvg = (spd + oldSpd) / 2;
        double dist = vAvg * dt;
        move(dist);
        spd += aEff;
        return 0;
    }

    private void move(double dist) {
        double dx = MyMath.sin(hdg) * dist;
        double dy = -MyMath.cos(hdg) * dist;
        x += dx;
        y += dy;
    }

    public void turn(double t, double angle, double a, boolean normalizeHeading) {
        hdg = hdg + angle / 2;
        move(t / 2, a);
        hdg = hdg + angle / 2;
        move(t / 2, a);
        if (normalizeHeading)
            hdg = normalize(hdg);
    }

    public String toString() {
        int ix = (int) x;
        int iy = (int) y;
        int h = (int) Math.toDegrees(hdg);
        return String.format("NavData[%d;%d;%d;%.1f]", ix, iy, h, spd);
    }

    public AffineTransform getTransform() {
        transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(hdg);
        return transform;
    }

    public double getHdg() {
        return hdg;
    }

    public double angleOff(double dir) {
        double angle = dir - hdg;
        if (dir < 0 && hdg > 0)
            angle += 2 * PI;
        if (dir > 0 && hdg < 0)
            angle -= 2 * PI;
        return angle;
    }

    public void setHdg(double hdg) {
        this.hdg = hdg;
    }

    public double getSpd() {
        return spd;
    }

    public void setSpd(double spd) {
        this.spd = spd;
        if (spd < 0) spd = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
