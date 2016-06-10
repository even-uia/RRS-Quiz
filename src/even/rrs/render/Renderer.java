package even.rrs.render;

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
import java.awt.geom.Path2D;
import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 *
 * @author even
 */
public class Renderer extends JComponent
{

    private Dimension renderSize;

    private Color bgColor;
    Scene scene;

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

    public Renderer(Dimension size) {
        setRenderSize(size);
        bgColor = new Color(0x7999af);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        repaint();
    }

    private void draw(Graphics2D g2) {
        Dimension size = scene.getDimension();
        Rectangle clip = g2.getClipBounds();
        AffineTransform scaler = getTransform(size, clip);

        g2.setColor(scene.getBackground());
        g2.fill(clip);
        g2.setStroke(new BasicStroke(1));

        for (Renderable shape : scene.getShapes()) {
            Shape scaledShape = scaler.createTransformedShape(shape.getPath());
            g2.setColor(shape.getFillColor());
            g2.fill(scaledShape);
            g2.setColor(shape.getColor());
            g2.draw(scaledShape);
        }
    }

    private AffineTransform getTransform(Dimension realSize, Rectangle window) {
        System.out.format("Size %d %d\n", realSize.width, realSize.height);
        System.out.format("Clip %d %d %d %d\n",
                          window.x, window.y, window.width, window.height);

        float xScale = 1.0f * window.width / realSize.width;
        float yScale = 1.0f * window.height / realSize.height;
        float scale = Math.min(xScale, yScale);
        System.out.println("Scale :" + scale);
        return AffineTransform.getScaleInstance(scale, scale);
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
        Renderer canvas = new Renderer(new Dimension(800, 450));

        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

}
