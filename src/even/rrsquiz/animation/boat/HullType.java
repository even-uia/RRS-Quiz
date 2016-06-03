/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.animation.boat;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 *
 * @author even
 */
public enum HullType
{

    DINGHY(100, 36, 30, 40, 80, false),
    SKIFF(100, 55, 25, 50, 90, true),
    KEELBOAT(80, 30, 10, 30, 55, false),
    RIB(80, 30, 30, 25, 50, false);

    final Path2D shape;
    final int length;
    final int beam;

    /**
     * Set up a hull shape. The deck outline curves from the bow through a
     * forward and aft points with the specified beam, to the transom.
     *
     * Moving the control points towards the ends of the hull makes it rounder
     * (fatter?) in the ends. Moving them towards midship will give the hull
     * finer ends.
     *
     * @param len overall length
     * @param beam beam at the control points
     * @param beamAft transom beam
     * @param cpFwd distance from bow to forward control point
     * @param cpAft do. aft control point.
     */
    private HullType(double len, double beam, double beamAft,
                     double cpFwd, double cpAft, boolean skiff) {
        this.length = (int) len;
        this.beam = (int) beam;
        double l2 = len / 2;
        double b2 = beam / 2;
        double ba2 = beamAft / 2;
        double fcp = cpFwd - l2;
        double acp = cpAft - l2;

        shape = new Path2D.Double();
        if (skiff) {
            shape.moveTo(0, -l2);
            shape.lineTo(ba2, fcp);
            shape.lineTo(b2, fcp);
            shape.lineTo(b2, l2);
            shape.lineTo(ba2, acp);
            shape.lineTo(-ba2, acp);
            shape.lineTo(-b2, l2);
            shape.lineTo(-b2, fcp);
            shape.lineTo(-ba2, fcp);
            shape.moveTo(0, -l2);
        }
        else {
            shape.moveTo(0, -l2);
            shape.curveTo(b2, fcp, b2, acp, ba2, l2);
            shape.lineTo(-ba2, l2);
            shape.curveTo(-b2, acp, -b2, fcp, 0, -l2);
        }
    }
}
