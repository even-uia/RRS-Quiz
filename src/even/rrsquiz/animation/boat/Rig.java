package even.rrsquiz.animation.boat;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author even
 */
public class Rig
{

    // rig properties
    RigType type;

    // rendering
    Tack tack;
    Sail main;
    Sail jib;
    Sail spin;

    boolean trimmed;

    /**
     * Rig constructor - can create all rig types. not intended for general use,
     * factory method provided below
     *
     * @param t rig type
     * @param mp mast position, pixels from boat origin
     * @param mc
     * @param fp
     * @param jc
     * @param sc
     * @param pl
     */
    private Rig(RigType t, Sail main, Sail jib, Sail spin) {
        type = t;
        trimmed = true;
        tack = Tack.PORT;

        this.main = main;
        this.jib = jib;
        this.spin = spin;
    }

    public static Rig getSingleSailRig(double pos, double chord, double camber) {
        Sail main = Sail.getMain(pos, chord, camber);
        Rig r = new Rig(RigType.SINGLE, main, null, null);
        return r;
    }

    public static Rig getSloopRig(double mPos, double mChord, double mCamber,
                                  double jPos, double jChord, double jCamber) {
        Sail main = Sail.getMain(mPos, mChord, mCamber);
        Sail jib = Sail.getJib(jPos, jChord, jCamber);
        Rig r = new Rig(RigType.SLOOP, main, jib, null);
        return r;
    }

    public static Rig getAssymetricRig(double mPos, double mChord, double mCamber,
                                       double jPos, double jChord, double jCamber,
                                       double sPos, double sChord, double sCamber, double poleLen) {
        Sail main = Sail.getMain(mPos, mChord, mCamber);
        Sail jib = Sail.getJib(jPos, jChord, jCamber);
        Sail spin = Sail.getAssymetric(sPos, sChord, sCamber, poleLen, jPos);
        Rig r = new Rig(RigType.SLOOP, main, jib, spin);
        return r;
    }

    public Rig copy() {
        Sail mcopy = main.copy();
        Sail jcopy = jib == null ? null : jib.copy();
        Sail scopy = spin == null ? null : spin.copy();
        Rig newRig = new Rig(type, mcopy, jcopy, scopy);
        return newRig;
    }

    public void update(double hdg) {
        if (hdg > 0) this.tack = Tack.PORT;
        else this.tack = Tack.STARBOARD;
        double twa = Math.abs(hdg);

        if (twa < 130 && spin != null) spin.isSet = false;

        if (twa > type.minTwa && trimmed) {
            if (spin != null && spin.isSet) {
                spin.update(tack, twa, this, trimmed);
                if (jib.isSet) jib.update(tack, spin.sheetAngle * 0.67);
                main.update(tack, spin.sheetAngle * 0.33);
            }
            else if (jib != null && jib.isSet) {
                jib.update(tack, twa, this, trimmed);
                main.update(tack, jib.sheetAngle / 2);
            }
            else main.update(tack, twa, this, trimmed);
        }
        else {
            main.update(tack, twa, this, trimmed);
            if (jib != null && jib.isSet) {
                jib.update(tack, twa, this, trimmed);
            }
            if (spin != null && spin.isSet) {
                spin.update(tack, twa, this, trimmed);
            }
        }
    }

    public void setSpinnaker(boolean set) {
        if (spin == null) return;
        else spin.isSet = set;
    }

    public void setTrimmed(boolean trimmed) {
        this.trimmed = trimmed;
    }

    /**
     * Render the rig
     *
     * @param g2 graphics context with transform set to the hull's transform
     * @param hdg heading in range (-maxtwa,maxtwa), used to set sheeting angles
     */
    public void render(Graphics2D g2, double hdg) {
        if (hdg > 0) tack = Tack.PORT;
        else tack = Tack.STARBOARD;
        double twa = Math.abs(hdg);

        main.render(g2, tack);
        // draw jib if set
        if (jib != null && jib.isSet) jib.render(g2, tack);
        if (spin != null && spin.isSet) spin.render(g2, tack);
    }
}
