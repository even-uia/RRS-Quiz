package even.rrs.animation;

import even.rrs.render.Drawable;
import even.rrs.render.SceneElement;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author even
 */
public class Mark implements Drawable
{
    double x;
    double y;
    double size;
    Color colour;
    ArrayList<SceneElement> shapes;

    public Mark(double x, double y, double size, Color colour) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.colour = colour;
        shapes = new ArrayList<>();
        Shape shape = new Ellipse2D.Double(x - size / 2, y - size / 2,
                                           size, size);
        shapes.add(new SceneElement("mark", shape, Color.BLACK, colour, null));
    }

    @Override
    public List<SceneElement> getSceneElements() {
        return shapes;
    }
}
