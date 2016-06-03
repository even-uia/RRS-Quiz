package even.rrsquiz.animation.boat;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Selecting a BoatType will automatically select hull shape, rig type
 * performance etc. The only things left to specify in the problem-file is the
 * id, colour, starting position and actions;
 *
 * @author even
 */
public enum BoatType
{

    OPPIE(makeOppieShape(),
          Rig.getSingleSailRig(-19, 40, 0.2),
          PerformanceProfile.OPPIE),
    LASER(makeGenericShape(83, 30, 28, 66, 20),
          Rig.getSingleSailRig(-20, 57, 0.15),
          PerformanceProfile.LASER),
    SKIFF(makeSkiffShape(100, 20, 60, 60),
          Rig.getAssymetricRig(-10, 60, 0.1,
                               -40, 30, 0.1,
                               -90, 50, 0.25, 50),
          PerformanceProfile.SKIFF);
//    SMALL_KEELBOAT(makeGenericShape(140, 50, 50, 100, 35),
//                   Rig.getSpinnakerRig(-5, 70, -60, 70, 100, 70),
//                   PerformanceProfile.SMALL_KEELBOAT),
//    BIG_KEELBOAT(makeGenericShape(200, 80, 60, 150, 40),
//                 Rig.getSpinnakerRig(-15, 90, -90, 100, 150, 100),
//                 PerformanceProfile.BIG_KEELBOAT);

    final Shape hullShape;
    final Rig rig;
    final PerformanceProfile vpp;
    double length;
    double beam;

    private BoatType(Shape hullShape, Rig rig, PerformanceProfile vpp) {
        this.hullShape = hullShape;
        this.rig = rig;
        this.vpp = vpp;
        Rectangle2D dim = hullShape.getBounds2D();
        length = dim.getHeight();
        beam = dim.getWidth();
    }

    Rig getRig() {
        return rig.copy();
    }

    // helpers for making hull shapes
    private static Shape makeOppieShape() {
        Path2D.Double path = new Path2D.Double();
        path.moveTo(0, -24);
        path.lineTo(4, -24);
        path.quadTo(11, 2, 6, 24);
        path.lineTo(-6, 24);
        path.quadTo(-11, 2, -4, -24);
        return path;
    }

    private static Shape makeSkiffShape(double len,
                                        double beam,
                                        double wngWidth,
                                        double wngLen) {
        double l2 = len / 2;
        double b2 = beam / 2;
        double w2 = wngWidth / 2;
        Path2D.Double shape = new Path2D.Double();
        shape.moveTo(0, -l2);
        shape.lineTo(b2, 0);
        shape.lineTo(w2, 0);
        shape.lineTo(w2, wngLen);
        shape.lineTo(b2, l2);
        shape.lineTo(-b2, l2);
        shape.lineTo(-w2, wngLen);
        shape.lineTo(-w2, 0);
        shape.lineTo(-b2, 0);
        shape.lineTo(0, -l2);
        return shape;
    }

    /**
     * Generates a reasonable drawing of most hull shapes. The control points
     * determine the curvature of the outline, them together generate a hull
     * with pointy ends and a lot of curvature midships. Moving the control
     * points towards the ends create fat ends all distances in pixels.
     *
     * all distances in pixels
     *
     * @param len length over all
     * @param cpFwd bow to fwd control point
     * @param beam approximate beam
     * @param cpAft bow to aft control point
     * @param beamAft transom beam
     * @return
     */
    private static Shape makeGenericShape(double len,
                                          double cpFwd,
                                          double beam,
                                          double cpAft,
                                          double beamAft) {
        Path2D.Double shape = new Path2D.Double();
        double l2 = len / 2;
        double b2 = beam / 2;
        double ba2 = beamAft / 2;
        double fcp = cpFwd - l2;
        double acp = cpAft - l2;
        shape.moveTo(0, -l2);
        shape.curveTo(b2, fcp, b2, acp, ba2, l2);
        shape.lineTo(-ba2, l2);
        shape.curveTo(-b2, acp, -b2, fcp, 0, -l2);
        return shape;
    }

    private interface HullShapeTemplate
    {

    }
}
