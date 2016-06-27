package even.rrs.animation;

import even.rrs.animation.boat.NavData;
import even.rrs.animation.boat.Tack;
import java.awt.geom.AffineTransform;


/**
 *
 * @author even
 */
public class Navigator implements Actor
{
    NavData pos; // unit m
    double speed; // unit m/s
    boolean trimmed;
    boolean usingSpinnaker;
    Boat boat;

    public Navigator(Boat boat) {
        this.boat = boat;
        boat.nav = this;

        pos = new NavData(10, 10, 0);
        speed = 0;
        trimmed = true;
        usingSpinnaker = true;
    }

    public void act(int t) {
        pos.turn(Math.toRadians(1));
    }

    public AffineTransform getHullTransform() {
        return pos.getTransform();
    }

    public boolean isUsingSpinnaker() {
        return usingSpinnaker;
    }

    public String getName() {
        return null;
    }

    public double getX() {
        return pos.x;
    }

    public double getY() {
        return pos.y;
    }

    public double getTwa() {
        return pos.twa;
    }

    public Tack getTack() {
        return pos.tack;
    }
}
