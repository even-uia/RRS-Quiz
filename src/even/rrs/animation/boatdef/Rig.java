package even.rrs.animation.boatdef;

import even.rrs.animation.Navigator;
import even.rrs.animation.boat.Tack;
import even.rrs.render.SceneElement;
import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author even
 */
public class Rig
{

    String id;
    double poleLen;
    double polePos;
    double poleDiameter;

    Shape mastShape;
    Shape boomShape;
    Sail main;
    Sail jib;
    Sail spinnaker;
    Sail gennaker;

    public Rig(String id) {
        this.id = id;
    }

    public List<SceneElement> getSceneItems(Navigator nav) {
        List<SceneElement> items = new ArrayList<>();
        double twa = nav.getTwa();
        Tack tack = nav.getTack();
        boolean trimmed = true;
        boolean spinSet = spinnaker != null && twa > spinnaker.mintwa && nav.isUsingSpinnaker();
        boolean genSet = gennaker != null && twa > gennaker.mintwa && nav.isUsingSpinnaker();

        if (main != null) {
            Shape mainShape = main.getTransformedShape(twa, tack, trimmed, genSet);
            Shape boomShape = main.getBoomShape();

            items.add(new SceneElement("boom", boomShape,
                                       Color.LIGHT_GRAY, Color.LIGHT_GRAY, null));
            items.add(new SceneElement("main", mainShape, Color.WHITE, null, null));
        }

        if (jib != null) {
            Shape jibShape = jib.getTransformedShape(twa, tack, trimmed, genSet);
            items.add(new SceneElement("jib", jibShape, Color.WHITE, null, null));
        }

        if (spinSet) {

        }

        if (genSet) {
            Shape genShape = gennaker.getTransformedShape(twa, tack, trimmed, genSet);
            items.add(new SceneElement("gennaker", genShape, Color.WHITE, null, null));
        }
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
