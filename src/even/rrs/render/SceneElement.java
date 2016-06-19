package even.rrs.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;


/**
 *
 * @author even
 */
public class SceneElement
{
    public static final Color DEFAULT_COLOR = Color.BLACK;
    public static final Color DEFAULT_FILL = Color.RED;
    public static final Stroke DEFAULT_STROKE = new BasicStroke();

    String name;
    Shape shape;
    Color colour;
    Color fillColour;
    Stroke stroke;

    public SceneElement(String name, Shape shape, Color colour, Color fillColour, Stroke stroke) {
        this.shape = shape;
        this.colour = (null != colour) ? colour : DEFAULT_COLOR;
        this.fillColour = (null != fillColour) ? fillColour : DEFAULT_FILL;
        this.stroke = (null != stroke) ? stroke : DEFAULT_STROKE;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
