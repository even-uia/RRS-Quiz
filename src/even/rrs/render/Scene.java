package even.rrs.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;


/**
 *
 * @author even
 */
public class Scene
{
    Dimension dim;
    ArrayList<Renderable> shapes;
    Color fill = Color.RED;
    Color fg = Color.BLACK;
    BasicStroke stroke = new BasicStroke(1);


    public Scene(Dimension dim) {
        this.dim = dim;
    }


    public Dimension getDimension() {
        return dim;
    }


    public void addShape(Renderable shape) {
        shapes.add(shape);
    }


    public ArrayList<Renderable> getShapes() {
        return shapes;
    }


    public Color getDefaultColor() {
        return fg;
    }


    public Color getDefaultFill() {
        return fill;
    }


    public BasicStroke getDefaultStroke() {
        return stroke;
    }

}
