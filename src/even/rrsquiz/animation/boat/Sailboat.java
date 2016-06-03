/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.animation.boat;

import even.rrsquiz.animation.Actor;
import even.rrsquiz.animation.Drawable;
import even.rrsquiz.animation.action.TurnAction;
import even.rrsquiz.animation.action.TackAction;
import even.rrsquiz.animation.action.TwoPartTurnAction;
import even.rrsquiz.animation.action.AbstractAction;
import even.util.MyMath;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 *
 * @author even
 */
public class Sailboat implements Actor, Drawable
{

    public static final Color OUTLINE_COLOUR = Color.BLACK;
    public static final double HEADING_TOLERANCE = 0.01f;

    AbstractAction currentAction;
    NavData navData, startNavData;

    String name;
    BoatType type;
    Color colour;
    public Rig rig;
    String hail;

    public Sailboat(BoatType type, String name, Color colour) {
        this.name = name;
        this.type = type;
        this.colour = colour;
        rig = type.getRig();
        currentAction = null;
        hail = null;
    }

    public NavData getNavData() {
        return navData;
    }

    public void setNavData(NavData navData) {
        this.navData = navData;
    }

    public String getHail() {
        return hail;
    }

    public void setHail(String hail) {
        this.hail = hail;
    }

    public void setup() {
        startNavData = new NavData(navData);
    }

    public void reset() {
        navData = new NavData(startNavData);
    }

    public void setAction(AbstractAction a) {
        currentAction = a;
    }

    public void setSpinnaker(boolean set) {
        rig.setSpinnaker(set);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxTwa() {
        return type.vpp.maxTwa;
    }

    //private boolean forceGybe;
    public void update(int frame) {
        double dt = 1;
        boolean forceGybe = false;

        while (dt > 0) {
            if (null == currentAction)
                dt = goOn(dt);
            else if (currentAction instanceof TackAction)
                dt = tack(dt, (TackAction) currentAction);
            else if (currentAction instanceof TwoPartTurnAction)
                dt = gybe(dt, (TwoPartTurnAction) currentAction);

            else if (currentAction instanceof TurnAction)
                dt = turn(dt, (TurnAction) currentAction);
        }
        rig.update(getHeading());
    }

    private double goOn(double dt) {
        double a = type.vpp.getAcceleration(navData, rig.trimmed);
        return navData.move(dt, a);
    }

    private double turnSome(double dt, TurnAction action) {
        double retVal = 0;

        double dh = navData.angleOff(action.targetHdg);
        double w = type.vpp.getTurnRate(action.rate);
        double nt = Math.abs(dh / w); // needed time to complete turn
        if (nt < dt)
            retVal = dt - nt;
        else if (nt >= dt) {
            // make only part of the turn to stay within specified turn rate
            dh = Math.signum(dh) * w * dt;
            nt = dt;
        }

        // make the turn (or part of it)
        double a = type.vpp.getAcceleration(navData, true);
        navData.turn(nt, dh, a, true);
        return retVal;
    }

    private double turn(double dt, TurnAction action) {
        dt = turnSome(dt, action);

        if (MyMath.equal(navData.getHdg(), action.targetHdg, HEADING_TOLERANCE)) {
            navData.setHdg(action.targetHdg);
            currentAction = null;
        }
        return dt;
    }

    private double tack(double dt, TackAction action) {
        TurnAction luff = action.getLuff();
        dt = turnSome(dt, luff);
        if (MyMath.equal(navData.getHdg(), luff.targetHdg,
                         HEADING_TOLERANCE)) {
            // switch to bearaway on new tack
            currentAction = new TurnAction(action.targetHdg, action.rate);
//            }
        }
        return dt;
    }

    private double gybe(double dt, TwoPartTurnAction action) {
        TurnAction bearaway = action.getBearaway();
        dt = turnSome(dt, bearaway);
        if (MyMath.equal(navData.getHdg(),
                         bearaway.targetHdg,
                         HEADING_TOLERANCE)) {
            currentAction = action.getLuff();
        }
        return dt;
    }

    public void render(Graphics2D g2) {
        AffineTransform saved = g2.getTransform();
        AffineTransform hullTransform = navData.getTransform();
        g2.transform(hullTransform);

//        Shape transformedHull = hullTransform.createTransformedShape(type.hullShape);
        g2.setColor(OUTLINE_COLOUR);
        g2.setStroke(PLAIN_STROKE);
        g2.draw(type.hullShape);
        g2.setColor(colour);
        g2.fill(type.hullShape);

        rig.render(g2, navData.getHdg());
        g2.setTransform(saved);
    }

    public void setAutoTrim(boolean auto) {
        rig.setTrimmed(auto);
    }

    public double getHeading() {
        return navData.getHdg();
    }

    public AffineTransform getHullTransform() {
        return navData.getTransform();
    }

    public double getGybeAngle() {
        return type.vpp.getMaxTwa();
    }
}
