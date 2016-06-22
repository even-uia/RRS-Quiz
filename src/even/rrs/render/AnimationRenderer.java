package even.rrs.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.JComponent;


/**
 *
 * @author even
 */
public class AnimationRenderer extends JComponent
{

    private Dimension renderSize;

    private Color bgColor;
    Scene scene;

    public AnimationRenderer(Dimension size) {
        setRenderSize(size);
        bgColor = new Color(0x7999af);
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXX Start rendering");
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
        System.out.println("\nXX End rendering\nXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        System.out.println("AnimationRenderer accepting new scene: " + this.scene);
        repaint();
    }

    private void draw(Graphics2D g2) {
        if (scene == null) return;
        Dimension size = scene.getDimension();
        // System.out.println(size);
        Rectangle clip = g2.getClipBounds();
        AffineTransform scaler = getTransform(size, clip);

        g2.setColor(scene.getBackground());
        g2.fill(clip);
        g2.setStroke(new BasicStroke(1));

        List<SceneElement> items = scene.getItems();
        for (SceneElement item : items) {
            System.out.println("Rendering item " + item.name);
            Shape scaledShape = scaler.createTransformedShape(item.shape);
            g2.setColor(item.fillColour);
            g2.fill(scaledShape);
            g2.setStroke(item.stroke);
            g2.setColor(item.colour);
            g2.draw(scaledShape);
        }
    }

    private AffineTransform getTransform(Dimension realSize, Rectangle window) {
        // System.out.format("Size %d %d\n", realSize.width, realSize.height);
        // System.out.format("Clip %d %d %d %d\n", window.x, window.y, window.width, window.height);

        float xScale = 1.0f * window.width / realSize.width;
        float yScale = 1.0f * window.height / realSize.height;
        float scale = Math.min(xScale, yScale);
        // System.out.println("Scale :" + scale);
        return AffineTransform.getScaleInstance(scale, scale);
    }

    public Dimension getRenderSize() {
        return renderSize;
    }

    public final void setRenderSize(Dimension renderSize) {
        this.renderSize = renderSize;
        setPreferredSize(renderSize);
    }

}