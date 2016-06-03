package even.rrsquiz.render;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;


/**
 *
 * @author even
 */
public interface Renderable
{
    Shape getShape();


    Color getColor();


    Color getFillColor();


    Stroke getStroke();

}
