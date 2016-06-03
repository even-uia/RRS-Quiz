/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.animation;

import even.rrsquiz.animation.Drawable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author even
 */
public class Mark implements Drawable
{

    int x, y;
    int size;
    int zoneSize;

    Shape mark;
    Shape zone;

    public Mark(int x, int y, int size, int zoneSize) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.zoneSize = zoneSize;
        this.mark = new Ellipse2D.Float(x - size, y - size, 2 * size, 2 * size);
        if (zoneSize > 0)
            this.zone = new Ellipse2D.Float(x - zoneSize, y - zoneSize,
                                            2 * zoneSize, 2 * zoneSize);
        else this.zone = null;
    }

    @Override
    public void reset() {
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        g2.fill(mark);
        g2.setColor(Color.BLACK);
        g2.draw(mark);

        if (zone != null) {
            g2.setColor(Color.WHITE);
            g2.setStroke(PLAIN_STROKE);
            g2.draw(zone);
        }

    }

}
