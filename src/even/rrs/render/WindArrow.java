package even.rrs.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;


/**
 *
 * @author even
 */
public class WindArrow
{

    public static final int BORDER = 10;
    public static final int HEAD_X = 15;
    public static final int HEAD_Y = 30;
    public static final String TEXT = "VIND";

    private int x;
    private int y;
    private static Path2D.Float shape;

    public WindArrow(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(TEXT);
        int th = fm.getHeight();
        if (shape == null) {
            shape = new Path2D.Float();
            shape.moveTo(x, y + HEAD_Y);
            shape.lineTo(x + tw / 2 + BORDER + HEAD_X, y);
            shape.lineTo(x + tw / 2 + BORDER, y);
            shape.lineTo(x + tw / 2 + BORDER, y - th - BORDER);
            shape.lineTo(x - tw / 2 - BORDER, y - th - BORDER);
            shape.lineTo(x - tw / 2 - BORDER, y);
            shape.lineTo(x - tw / 2 - BORDER - HEAD_X, y);
            shape.lineTo(x, y + HEAD_Y);
        }
        g2.draw(shape);
        g2.drawString(TEXT, x - tw / 2, y);
    }
}
