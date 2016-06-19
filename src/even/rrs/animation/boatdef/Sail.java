package even.rrs.animation.boatdef;

import java.awt.geom.Path2D;


/**
 *
 * @author even
 */
public class Sail
{
    Type type;
    double chord; // m
    double camber; // fraction of chord
    double tackPos;
    double minAngle; // sheeting angles, rad
    double maxAngle;
    double mintwa; // min twa for setting spinnakers
    Path2D.Double shape;
    Path2D.Double flappingShape;


    public static enum Type
    {
        main, jib, spinnaker, gennaker;
    }

    public Sail(Type type,
                double chord, double camber,
                double minAngle, double maxAngle, double mintwa) {
        this.type = type;
        this.chord = chord;
        this.camber = camber;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
        this.mintwa = mintwa;

        shape = new Path2D.Double();
        double depth = camber * chord;
        shape.moveTo(0, 0);
        shape.curveTo(depth, 0.3 * chord, depth, 0.5 * chord, 00, 0);
        flappingShape = new Path2D.Double();
        flappingShape.moveTo(0, 0);
        flappingShape.curveTo(depth, chord / 6,
                              -depth, chord / 3,
                              0, chord / 2);
        flappingShape.curveTo(depth, chord * 2 / 3,
                              -depth, chord * 5 / 6,
                              0, chord);
    }

    public double getSheetingAngle(double twa, boolean trimmed) {
        double aoa = Math.toRadians(camber * 500);
        double sheetAngle = twa - aoa;
        if (!trimmed) sheetAngle = twa;
        if (sheetAngle > maxAngle) sheetAngle = maxAngle;
        if (sheetAngle < minAngle) sheetAngle = twa;
        System.out.format("TWA %f, Sheetangle %f, aoa %f\n",
                          Math.toDegrees(twa),
                          Math.toDegrees(sheetAngle),
                          Math.toDegrees(aoa));
        return sheetAngle;
    }

    public boolean isFlapping(double twa, boolean trimmed) {
        if (twa < minAngle) return true;
        if (twa < maxAngle && !trimmed) return true;
        return false;
    }
}
