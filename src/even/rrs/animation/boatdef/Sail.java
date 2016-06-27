package even.rrs.animation.boatdef;

import even.rrs.animation.boat.Tack;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;


/**
 *
 * @author even
 */
public class Sail
{
    Type type;
    double x;
    double y;
    double chord; // m
    double camber; // fraction of chord
    double tackPos;
    double minAngle; // sheeting angles, rad
    double maxAngle;
    double mintwa; // min twa for setting spinnakers
    Path2D.Double portShape;
    Path2D.Double starboardShape;
    Path2D.Double flappingShape;
    Shape boomShape;
    AffineTransform transform;


    public static enum Type
    {
        main, jib, spinnaker, gennaker;
    }

    public Sail(Type type, double x, double y,
                double chord, double camber,
                double minAngle, double maxAngle, double mintwa) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.chord = chord;
        this.camber = camber;
        this.minAngle = Math.toRadians(minAngle);
        this.maxAngle = Math.toRadians(maxAngle);
        this.mintwa = Math.toRadians(mintwa);

        double depth = camber * chord;
        portShape = new Path2D.Double();
        portShape.moveTo(0, 0);
        portShape.curveTo(depth, 0.3 * chord, depth, 0.5 * chord, 0, chord);
        starboardShape = new Path2D.Double();
        starboardShape.moveTo(0, 0);
        starboardShape.curveTo(-depth, 0.3 * chord, -depth, 0.5 * chord, 0, chord);
        flappingShape = new Path2D.Double();
        flappingShape.moveTo(0, 0);
        flappingShape.curveTo(depth, chord / 6,
                              -depth, chord / 3,
                              0, chord / 2);
        flappingShape.curveTo(depth, chord * 2 / 3,
                              -depth, chord * 5 / 6,
                              0, chord);
        if (type == Type.main) {
            double halfWidth = chord / 30;
            boomShape = new Rectangle2D.Double(-halfWidth, 0,
                                               2 * halfWidth, chord);
        }
    }

    public Shape getTransformedShape(double twa, Tack tack,
                                     boolean trimmed, boolean gennSet) {
        double sheetAngle = getSheetingAngle(twa, trimmed);
        if (gennSet) sheetAngle = sheetAngle / 2;
        if (tack == Tack.PORT) sheetAngle = -sheetAngle;
        transform = AffineTransform.getTranslateInstance(x, y);
        transform.concatenate(AffineTransform.getRotateInstance(sheetAngle));

        Shape shape = selectShape(twa, tack, trimmed);
        return transform.createTransformedShape(shape);
    }

    public Shape getBoomShape() {
        return transform.createTransformedShape(boomShape);
    }

    public AffineTransform getTransform() {
        return transform;
    }

    private Shape selectShape(double twa, Tack tack, boolean trimmed) {
        if (isFlapping(twa, trimmed)) return flappingShape;
        else if (tack == Tack.PORT) return portShape;
        else return starboardShape;
    }

    private double getSheetingAngle(double twa, boolean trimmed) {
        double aoa = 2 * Math.sqrt(camber * twa);
        double sheetAngle = twa - aoa;
        if (!trimmed) sheetAngle = twa;
        if (sheetAngle > maxAngle) sheetAngle = maxAngle;
        if (sheetAngle < minAngle) sheetAngle = Math.min(twa, minAngle);
        System.out.format("TWA %f, Sheetangle %f, aoa %f\n",
                          Math.toDegrees(twa),
                          Math.toDegrees(sheetAngle),
                          Math.toDegrees(aoa));
        return sheetAngle;
    }

    private boolean isFlapping(double twa, boolean trimmed) {
        if (twa < minAngle) return true;
        if (twa < maxAngle && !trimmed) return true;
        return false;
    }
}
