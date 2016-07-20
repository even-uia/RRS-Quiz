package even.rrs.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.EnumSet;


/**
 *
 * @author even
 */
public class SceneElement
{
    public static final Color DEFAULT_COLOR = Color.BLACK;
    public static final Stroke DEFAULT_STROKE = new BasicStroke();

    String name;
    Shape shape;
    Color colour;
    Color fillColour;
    Stroke stroke;
    EnumSet<Flag> signals;
    Point2D.Double sigPos;

    public SceneElement(String name, Shape shape, Color colour, Color fillColour, Stroke stroke) {
        this.name = name;
        this.shape = shape;
        this.colour = (null != colour) ? colour : DEFAULT_COLOR;
        this.fillColour = fillColour;
        this.stroke = (null != stroke) ? stroke : DEFAULT_STROKE;
        this.signals = null;
    }

    public SceneElement(String name,
                        Shape shape,
                        Color colour,
                        Color fillColour,
                        Stroke stroke,
                        EnumSet<Flag> signals,
                        Point2D.Double sigPos) {
        this(name, shape, colour, fillColour, stroke);
        this.signals = signals;
        this.sigPos = sigPos;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
