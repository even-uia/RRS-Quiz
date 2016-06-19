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
    ArrayList<SceneElement> items;
    Color background = Color.BLUE;
    Color fill = Color.RED;
    Color fg = Color.BLACK;
    BasicStroke stroke = new BasicStroke(1);

    public Scene(Dimension dim) {
        this.dim = dim;
        items = new ArrayList<>();
    }

    public Dimension getDimension() {
        return dim;
    }

    public void addItem(SceneElement shape) {
        items.add(shape);
    }

    public ArrayList<SceneElement> getItems() {
        return items;
    }

    public Color getBackground() {
        return background;
    }

    @Override
    public String toString() {
        return "Scene{" + "dim=" + dim + ", items=" + items + " fill=" + fill + ", fg=" + fg + ", stroke=" + stroke + '}';
    }
}
