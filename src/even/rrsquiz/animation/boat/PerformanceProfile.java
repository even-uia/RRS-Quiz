/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.animation.boat;

import even.rrsquiz.animation.action.TurnRate;

/**
 *
 * @author even
 */
public enum PerformanceProfile
{

    OPPIE(45, 200,
          new PolarDiagram(new double[]{},
                           new double[]{}),
          0.4, 30, 15, 5),
    LASER(45, 200,
          new PolarDiagram(
                  new double[]{},
                  new double[]{}),
          0.4, 30, 15, 5),
    SKIFF(50, 181,
          new PolarDiagram(
                  new double[]{0, 40.0, 50.0, 60.0, 80, 140, 170.0, 181},
                  new double[]{0, 5.5, 6.6, 8.4, 11, 15, 6.5, 5}),
          0.4, 30, 10, 3),
    SMALL_KEELBOAT(40, 181,
                   new PolarDiagram(
                           new double[]{0, 40.0, 50.0, 60.0, 110.0, 140, 170, 181},
                           new double[]{0, 4.5, 5.3, 6.1, 6.5, 6, 5, 4}),
                   0.4, 20, 10, 5),
    BIG_KEELBOAT(40, 181,
                 new PolarDiagram(
                         new double[]{0, 40, 60, 100.0, 160.0, 181},
                         new double[]{0, 6, 7, 6.5, 5.2, 6}),
                 0.4, 15, 12, 3);

    double maxTurnRate; // deg per frame
    double stdTurnRate;
    double lowTurnRate;

    PolarDiagram polar;
    //* max twa angle (degrees) that the boat can realistically be sailed on
    double maxTwa;
    // optimum uwind twa (deg)
    double closeHauledAngle;
    // acceleration scaling constant
    double kAccel;

    PerformanceProfile(double closeHauledAngle, double maxTwa,
                       PolarDiagram polar, double kAccel,
                       double maxTurnRate, double stdTurnRate, double lowTurnRate) {

        this.maxTurnRate = Math.toRadians(maxTurnRate);
        this.stdTurnRate = Math.toRadians(stdTurnRate);
        this.lowTurnRate = Math.toRadians(lowTurnRate);
        this.closeHauledAngle = Math.toRadians(closeHauledAngle);
        this.maxTwa = Math.toRadians(maxTwa);
        this.polar = polar;
        this.kAccel = kAccel;
    }

    public double getTurnRate(TurnRate rate) {
        switch (rate) {
        case LOW:
            return lowTurnRate;
        case STD:
            return stdTurnRate;
        case MAX:
            return maxTurnRate;
        }
        return 0;
    }

    public double getMaxTwa() {
        return maxTwa;
    }

    public double getAcceleration(NavData navData, boolean autoTrim) {
        double targetSpd = polar.getTargetSpeed(navData.getHdg(), autoTrim);
        double dv = targetSpd - navData.getSpd();
        if (navData.getSpd() < 0) navData.setSpd(0);
        if (Math.abs(dv) < 0.01)
            return dv;
        else
            return dv * kAccel;
    }
}

///**
// * an attempt at a force based vpp - doesnt work yet
// */
//public static class SkiffVppEngine
//{
//
//    private static final double SCALE_X = 18.1;
//    private static final double TRANSLATE_X = -10;
//    private static final double SCALE_Y = 254;
//    private static final double TRANSLATE_Y = 2300;
//    private static final double MYSTERY = 2;
//
//    public static final double frictionCoeff = 27;
//    public static final double waveDragCoeff = 0.05;
//
//    double mass;
//
//    public SkiffVppEngine(double mass, double closeHauledAngle, double maxTwa, double maxTurnRate, double stdTurnRate, double lowTurnRate) {
//        super(closeHauledAngle, maxTwa, maxTurnRate, stdTurnRate, lowTurnRate);
//        this.mass = mass;
//    }
//
//    private double getForce(double hdgDeg) {
//        double param = hdgDeg / SCALE_X + TRANSLATE_X;
//        double f = (param - MYSTERY / (param * param)) * SCALE_Y + TRANSLATE_Y;
//        return f;
//    }
//
//    public double getForwardForce(double hdg) {
//        double hdgDeg = Math.toDegrees(hdg);
//        if (hdgDeg < 30) return getForce(30) * hdgDeg / 30;
//        else if (hdgDeg < 180) return getForce(hdgDeg);
//        else return getForce(180);
//    }
//
//    public double getDrag(double speed) {
//        double v2 = speed * speed;
//        double v4 = v2 * v2;
//        return frictionCoeff * v2 + waveDragCoeff * v4;
//    }
//
//    public double getAcceleration(NavData nd, boolean autoTrim) {
//        double v = nd.getSpd();
//        double c = nd.getHdg();
//        double ff = getForwardForce(v);
//        double fd = getDrag(nd.getSpd());
//        double totalForce = ff - fd;
//
//        System.out.format("c=%.0f v=%.2f ff=%.2f fd=%.2f ft=%.2f\n",
//                          Math.toDegrees(c), v,
//                          ff, fd, totalForce);
//        double a = totalForce / mass;
//        if (a < -v / 2) a = -v / 2;
//        return a;
//    }

