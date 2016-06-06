package even.rrsquiz.gui;

import even.rrsquiz.animation.Animation;
import even.rrs.render.Renderable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 *
 * @author even
 */
public class Canvas extends JComponent
{

    private Dimension renderSize;

    private Color bgColor;
    Renderable output;

    private Rectangle ActualSize = new Rectangle(0, 0, 15, 15);


    private static Shape createShape() {
        GeneralPath.Double path = new GeneralPath.Double();
        path.moveTo(0, 2.5);
        path.lineTo(0.6, 0);
        path.lineTo(1.5, -0.5);
        path.lineTo(1.5, -2.5);
        path.lineTo(-1.5, -2.5);
        path.lineTo(-1.5, -0.5);
        path.lineTo(-0.6, 0);
        path.closePath();
        return path;
    }


    public Canvas(Dimension size) {
        setRenderSize(size);
        bgColor = new Color(0x7999af);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }


    private void draw(Graphics2D g2) {
        Dimension size = new Dimension(ActualSize.width, ActualSize.height);
        Rectangle clip = g2.getClipBounds();
        System.out.format("Size %d %d\n", size.width, size.height);
        System.out.format("Clip %d %d %d %d\n", clip.x, clip.y, clip.width, clip.height);

        float xScale = 1.0f * clip.width / size.width;
        float yScale = 1.0f * clip.height / size.height;
        float scale = Math.min(xScale, yScale);
        System.out.println("Scale :" + scale);
        AffineTransform scaler = AffineTransform.getScaleInstance(scale, scale);

        g2.setColor(bgColor);
        g2.fill(clip);
        g2.setStroke(new BasicStroke(1));

        Shape scaledShape = scaler.createTransformedShape(output.getShape());
        g2.setColor(output.getFillColor());
        g2.fill(scaledShape);
        g2.setColor(output.getColor());
        g2.draw(scaledShape);
    }


    public void setOutput(Renderable output) {
        this.output = output;
    }


    public Dimension getRenderSize() {
        return renderSize;
    }


    public final void setRenderSize(Dimension renderSize) {
        this.renderSize = renderSize;
        setPreferredSize(renderSize);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Canvas canvas = new Canvas(new Dimension(800, 450));

        Renderable output = new Renderable()
        {
            Shape s = createShape();
            AffineTransform transform = new AffineTransform();
            Shape transformed;


            @Override
            public Shape getShape() {
                if (transformed == null) {
                    transform.scale(1, -1);
                    transform.translate(5, -5);
                    transform.rotate(Math.toRadians(45));
                    transformed = transform.createTransformedShape(s);
                }
                return transformed;
            }


            @Override
            public Color getColor() {
                return Color.BLACK;
            }


            @Override
            public Color getFillColor() {
                return Color.RED;
            }


            @Override
            public Stroke getStroke() {
                return new BasicStroke(1);
            }

        };

        canvas.setOutput(output);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

}
