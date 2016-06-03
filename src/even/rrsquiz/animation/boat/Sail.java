package even.rrsquiz.animation.boat;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class Sail
{

    public static final Color SPAR_OUTLINE_COLOUR = Color.GRAY;
    public static final Color SPAR_FILL_COLOUR = Color.LIGHT_GRAY;
    public static final Color SAIL_COLOR = Color.BLACK;

    public static final int NONE = 0;
    public static final int FIXED = 1;
    public static final int BOOM = 2;
    public static final int SPIN_POLE = 3;

    // shape
    double chord, camber;
    Path2D.Double portShape;
    Path2D.Double stbdShape;
    Path2D.Double flappingShape;
    Shape shape;
    Shape spar;
    int sparType;
    double pos;

    // dynamics
    boolean isSet;
    double sheetAngle;

    // limits
    double minAngle;
    double maxAngle;

    public Sail(double pos, double chord, double camber, double minAngle, double maxAngle) {
        this.pos = pos;
        this.chord = chord;
        this.camber = camber;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;

        double depth = camber * chord;

        portShape = new Path2D.Double();
        portShape.moveTo(0, 0);
        portShape.curveTo(depth, 0.3 * chord,
                          depth, 0.6 * chord,
                          0, chord);
        stbdShape = new Path2D.Double();
        stbdShape.moveTo(0, 0);
        stbdShape.curveTo(-depth, 0.3 * chord,
                          -depth, 0.6 * chord,
                          0, chord);
        flappingShape = new Path2D.Double();
        flappingShape.moveTo(0, 0);
        flappingShape.curveTo(depth / 2, chord / 6,
                              -depth / 2, chord / 3,
                              0, chord / 2);
        flappingShape.curveTo(depth, chord * 2 / 3,
                              -depth, chord * 5 / 6,
                              0, chord);
        shape = flappingShape;
    }

    public static Sail getMain(double pos, double chord, double camber) {
        Sail main = new Sail(pos, chord, camber,
                             Math.toRadians(5), Math.toRadians(80));
        double bw = chord / 20;
        Shape boom = new Rectangle2D.Double(-bw / 2, 0, bw / 2, chord);
        Shape mast = new Ellipse2D.Double(-bw, -bw, bw, bw);
        Path2D spar = new Path2D.Double();
        spar.append(mast, false);
        spar.append(boom, false);
        main.setSpar(spar, BOOM);
        return main;
    }

    public static Sail getJib(double pos, double chord, double camber) {
        Sail jib = new Sail(pos, chord, camber, Math.toRadians(10), Math.toRadians(75));
        jib.setSpar(null, NONE);
        return jib;
    }

    public static Sail getAssymetric(double pos, double chord, double camber,
                                     double poleLen, double jPos) {
        Sail spin = new Sail(pos, chord, camber, Math.toRadians(10), Math.toRadians(75));
        double sw = chord / 40;
        Shape sprit = new Rectangle2D.Double(-sw, jPos, sw, jPos - poleLen);
        spin.setSpar(sprit, FIXED);
        return spin;
    }

    public void setSpar(Shape shape, int type) {
        spar = shape;
        sparType = type;
    }

    public Sail copy() {
        Sail newSail = new Sail(pos, chord, camber, minAngle, maxAngle);
        newSail.setSpar(spar, sparType);
        return newSail;
    }

    /**
     * prepare the sail for rendering. Sets the sheeting angle and sail shape
     */
    public void update(Tack tack, double twa, Rig rig, boolean trimmed) {
        if (tack == Tack.PORT) shape = portShape;
        else shape = stbdShape;

        if (twa < rig.type.minTwa) {
            sheetAngle = twa;
            shape = flappingShape;
        }
        else if (trimmed) {
            sheetAngle = minAngle + ((maxAngle - minAngle)
                    * (twa - rig.type.minTwa)
                    / (rig.type.maxTwa - rig.type.minTwa));
        }
        else if (twa < maxAngle) {
            sheetAngle = twa;
            shape = flappingShape;
        }
        else {
            sheetAngle = maxAngle;
        }
    }

    public void update(Tack tack, double angle) {
        if (tack == Tack.PORT) shape = portShape;
        else shape = stbdShape;
        sheetAngle = angle;

    }

    public void render(Graphics2D g2, Tack tack) {
        if (sparType == FIXED) renderSpar(g2);

        AffineTransform saved = g2.getTransform();
        double angle = sheetAngle;
        if (tack == Tack.PORT) angle = -angle;
        AffineTransform transform = new AffineTransform();
        transform.translate(0, pos);
        transform.rotate(angle);
        g2.transform(transform);
        if (sparType == BOOM) renderSpar(g2);
        g2.setColor(SAIL_COLOR);
        g2.draw(shape);
        g2.setTransform(saved);
    }

    public void renderSpar(Graphics2D g2) {
        if (sparType != NONE) {
            g2.setColor(SPAR_FILL_COLOUR);
            g2.fill(spar);
            g2.setColor(SPAR_OUTLINE_COLOUR);
            g2.draw(spar);

        }
    }
}
