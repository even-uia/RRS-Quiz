package even.rrs.animation;

import java.awt.geom.AffineTransform;


/**
 *
 * @author even
 */
public class Navigator implements Actor
{
    Pos pos; // unit m
    Heading hdg; // unit rad
    double speed; // unit m/s
    Boat boat;

    public Navigator(Boat boat) {
        this.boat = boat;
        boat.nav = this;

        pos = new Pos(10, 10);
        hdg = new Heading(0);
        speed = 0;
    }

    public void print() {
        System.out.format("\nPos: x =%5.1f y = %5.1f\n", pos.x, pos.y);
        System.out.format("TWA = %7.1f %s Speed %5.1f\n", Math.toDegrees(hdg.twa), hdg.tack, speed);
    }

    public void act(int t) {
        hdg.turn(Math.toRadians(1));
        print();
    }

    public AffineTransform getHullTransform() {
        System.out.println("Navigator: Computing hull transform");
        AffineTransform transform = new AffineTransform();
        transform.translate(pos.x, pos.y);
        transform.rotate(hdg.heading);
        return transform;
    }

    public String getName() {
        return null;
    }


    // helper classes
    private class Pos
    {
        double x;
        double y;

        public Pos(double x, double y) {
            this.x = x;
            this.y = y;
        }

    }


    class Heading
    {
        double heading; // -pi to pi rad
        double twa; // 0 to pi rad
        Tack tack;

        public Heading(double course) {
            set(course);
        }

        public Heading(Tack tack, double twa) {
            set(tack, twa);
        }

        /**
         * turn angle radians
         */
        public void turn(double angle) {
            set(heading += angle);
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
    }


    public enum Tack
    {
        PORT, STARBOARD
    }

}
