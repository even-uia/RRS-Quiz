package even.rrs.animation;

import even.rrs.animation.boatdef.BoatType;
import even.rrs.animation.boatdef.Hull;
import even.rrs.animation.boatdef.Rig;
import even.rrs.render.Drawable;
import even.rrs.render.SceneElement;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author even
 */
public class Boat implements Drawable
{

    BoatType type;
    String name;
    Color hullColour;
    Navigator nav;

    public Boat(BoatType type, String name, Color hullColour) {
        this.type = type;
        this.name = name;
        this.hullColour = hullColour;
    }

    public List<SceneElement> getSceneItems() {
        List<SceneElement> shapes = new ArrayList<>();

        AffineTransform transform = nav.getHullTransform();
        Hull hull = type.getHull();
        Shape hullShape = transform.createTransformedShape(hull.getShape());
        shapes.add(new SceneElement("hull", hullShape, null, hullColour, null));

        Rig rig = type.getRig();
        List<SceneElement> rigShapes = rig.getSceneItems(Navigator nav
        );
        for (SceneElement se : rigShapes) {
            se.setShape(transform.createTransformedShape(se.getShape()));
            shapes.add(se);
        }
        return shapes;
    }

}
