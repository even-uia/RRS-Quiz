package even.rrsquiz.animation;

import even.rrsquiz.animation.Drawable;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 *
 * @author even
 */
public class WindArrow implements Drawable
{

    public static final int BORDER = 10;
    public static final int HEAD_X = 15;
    public static final int HEAD_Y = 30;
    public static final String TEXT = "VIND";

    private int x;
    private int y;
    private static Path2D.Float arrow;

    public void print() {
    }

    public WindArrow(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void saveState() {
        // static
    }

    public void reset() {
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int tw = fm.stringWidth(TEXT);
        int th = fm.getHeight();
        if (arrow == null) {
            arrow = new Path2D.Float();
            arrow.moveTo(x, y + HEAD_Y);
            arrow.lineTo(x + tw / 2 + BORDER + HEAD_X, y);
            arrow.lineTo(x + tw / 2 + BORDER, y);
            arrow.lineTo(x + tw / 2 + BORDER, y - th - BORDER);
            arrow.lineTo(x - tw / 2 - BORDER, y - th - BORDER);
            arrow.lineTo(x - tw / 2 - BORDER, y);
            arrow.lineTo(x - tw / 2 - BORDER - HEAD_X, y);
            arrow.lineTo(x, y + HEAD_Y);
        }
        g2.draw(arrow);
        g2.drawString(TEXT, x - tw / 2, y);
    }
}
