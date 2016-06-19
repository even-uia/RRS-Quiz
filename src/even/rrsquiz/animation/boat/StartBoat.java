package even.rrsquiz.animation.boat;

import even.rrsquiz.animation.Actor;
import even.rrsquiz.animation.Drawable;
import even.rrsquiz.animation.Mark;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.EnumSet;


/**
 *
 * @author even
 */
public class StartBoat implements Drawable, Actor
{

    protected String name;
    protected Shape hullShape;
    private double beam;
    private Color colour;
    protected NavData navData, startNavData;
    private Mark mark;
    private EnumSet<Flag> signals;
    private String hail;

    public StartBoat() {
        name = "Startboat";
//        hullShape = BoatType.BIG_KEELBOAT.hullShape;
        colour = Color.GRAY;
        signals = EnumSet.noneOf(Flag.class);
    }

    public String getHail() {
        return hail;
    }

    public void setHail(String hail) {
        this.hail = hail;
    }

    public void setup() {
    }

    public void update(int frame) {
    }

    @Override
    public void render(Graphics2D g2) {
        // render hull
        AffineTransform hullTransform = navData.getTransform();
        Shape outline = hullTransform.createTransformedShape(hullShape);
        g2.setColor(colour);
        g2.fill(outline);
        g2.setColor(Color.BLACK);
        g2.setStroke(Drawable.PLAIN_STROKE);
        g2.draw(outline);

        //render signals
        for (Flag f : signals) {
            int i = f.ordinal();
            int fx = (int) (navData.getX()) + (int) beam + 10;
            int fy = (f == Flag.CLASSFLAG)
                    ? (int) navData.getY()
                    : (int) (navData.getY() - 40);
            g2.drawImage(f.getImage(), fx, fy, null);
        }

        // render hail
        if (null != hail) {
            Font f = g2.getFont();
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.setColor(Color.YELLOW);
            g2.drawString(hail, (float) navData.getX(), (float) navData.getY());
        }
    }

    @Override
    public
            void reset() {
        signals = EnumSet.noneOf(Flag.class
        );
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public
            void resetSignals() {
        signals = EnumSet.noneOf(Flag.class
        );
    }

    public void setSignal(Flag flag) {
        signals.add(flag);
    }

    public void strikeSignal(Flag flag) {
        signals.remove(flag);
    }

}
