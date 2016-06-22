package even.rrs.animation.boatdef;

import even.rrs.render.SceneElement;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author even
 */
public class Rig
{

    String id;
    double forestayPos;
    double mastDiameter;
    double mastRadius;
    double mastPos;
    double poleLen;
    double polePos;
    double poleDiameter;

    Shape mastShape;
    Shape boomShape;
    Sail main;
    Sail jib;
    Sail spinnaker;
    Sail gennaker;

    public Rig(String id, double mastPos, double forestayPos) {
        this.id = id;
        this.mastPos = mastPos;
        this.forestayPos = forestayPos;

        mastDiameter = 0.08 * (mastPos - forestayPos);
        mastRadius = mastDiameter / 2;
    }

    public void createShapes() {
        mastShape = new Ellipse2D.Double(-mastRadius, -mastRadius,
                                         mastDiameter, mastDiameter);
        boomShape = new Rectangle2D.Double(-mastRadius, 0,
                                           mastDiameter, main.chord);
    }

    public List<SceneElement> getSceneItems(double twa, boolean trimmed) {
        List<SceneElement> items = new ArrayList<>();
        AffineTransform mastTransform = AffineTransform.getTranslateInstance(0, mastPos);
        Shape transMastShape = mastTransform.createTransformedShape(mastShape);
        System.out.format("Translating mast to %f, %f\n",
                          transMastShape.getBounds2D().getCenterX(),
                          transMastShape.getBounds2D().getCenterY());
        items.add(new SceneElement("mast", transMastShape,
                                   Color.LIGHT_GRAY, Color.LIGHT_GRAY, null));
        if (main != null) {
            if (main.isFlapping(mastPos, true))

        }


        System.out.println("Rig returning rendering items: " + items.size());
        return items;
    }

    public void setPoleLen(double len) {
        poleLen = len;
        poleDiameter = 0.03 * len;
    }

    public void addSail(Sail s) {
        switch (s.type) {
        case main:
            main = s;
            break;
        case jib:
            jib = s;
            break;
        case spinnaker:
            spinnaker = s;
            break;
        case gennaker:
            gennaker = s;
            break;
        }
    }

    public String getId() {
        return id;
    }

    public double getForestayPos() {
        return forestayPos;
    }

    public double getMastDiameter() {
        return mastDiameter;
    }

    public double getMastPos() {
        return mastPos;
    }

    public double getPoleLen() {
        return poleLen;
    }

    public double getPolePos() {
        return polePos;
    }

    public double getPoleDiameter() {
        return poleDiameter;
    }

    public Sail getMain() {
        return main;
    }

    public Sail getJib() {
        return jib;
    }

    public Sail getSpinnaker() {
        return spinnaker;
    }

    public Sail getGennaker() {
        return gennaker;
    }
}
