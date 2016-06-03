package even.rrsquiz.animation;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author even
 */
public interface Drawable
{

    Stroke PLAIN_STROKE = new BasicStroke(0);
    Stroke DASHED_STROKE = new BasicStroke(0,
                                          BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                                          10, new float[]{10.0f}, 0);

    public void reset();

    /**
     * Render the current state
     */
    public void render(Graphics2D g2);

}
