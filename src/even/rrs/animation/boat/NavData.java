package even.rrs.animation.boat;

import java.awt.geom.AffineTransform;


/**
 *
 * @author even
 */
public class NavData
{
    //position
    public double x;
    public double y;

    // heading
    public double heading; // -pi to pi rad
    public double twa; // 0 to pi rad
    public Tack tack;

    //speed - todo
    public double speed;

    public NavData(double x, double y, double course) {
        this.x = x;
        this.y = y;
        set(course);
    }

    public NavData(double x, double y, Tack tack, double twa) {
        this.x = x;
        this.y = y;
        set(tack, twa);
    }

    private void set(double hdg) {
        heading = hdg;
        while (heading > Math.PI)
            heading -= 2 * Math.PI;
        while (heading < -Math.PI)
            heading += 2 * Math.PI;

        twa = Math.abs(hdg);
        tack = (hdg > 0) ? Tack.PORT : Tack.STARBOARD;
    }

    private void set(Tack tack, double twa) {
        set((tack == Tack.PORT) ? twa : -twa);
    }

    public AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(heading);
        return transform;
    }

    public void turn(double angle) {
        double newHdg = heading + angle;
        set(newHdg);
    }

}
