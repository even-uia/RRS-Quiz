package even.rrs.render;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Path2D;


/**
 *
 * @author even
 */
public interface Renderable
{
    Path2D.Double getPath();

    Color getColor();

    Color getFillColor();

    Stroke getStroke();

}
