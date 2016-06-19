package even.experiment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author even
 */
public class BorderLayoutExperiment extends JFrame
{

    JButton top;
    JButton bottom;
    JLabel canvas;

    public BorderLayoutExperiment() {
        top = new JButton("Toolbar");
        bottom = new JButton("Statusline");
        canvas = new Canvas();
        JPanel contentPane = new JPanel(new BorderLayout(5, 5));
        contentPane.add(top, BorderLayout.NORTH);
        contentPane.add(canvas, BorderLayout.CENTER);
        contentPane.add(bottom, BorderLayout.SOUTH);
        setContentPane(contentPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args) {
        BorderLayoutExperiment bt = new BorderLayoutExperiment();
        bt.setVisible(true);
    }

    public static class Canvas extends JLabel
    {

        Rectangle box = new Rectangle(10, 10, 380, 280);

        public Canvas() {
            setPreferredSize(new Dimension(400, 300));
            setBackground(Color.BLUE);

            addComponentListener(new ComponentAdapter()
            {
                @Override
                public void componentResized(ComponentEvent e) {
                    // System.out.println("Canvas resized to " + getSize());
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            draw(g2);
        }

        private void draw(Graphics2D g2) {
            Dimension size = getPreferredSize();
            Rectangle clip = g2.getClipBounds();
            // System.out.format("Size %d %d\n", size.width, size.height);
            // System.out.format("Clip %d %d %d %d\n", clip.x, clip.y, clip.width, clip.height);
            float xScale = 1.0f * clip.width / size.width;
            float yScale = 1.0f * clip.height / size.height;
            float scale = Math.min(xScale, yScale);
            // System.out.println("Scale :" + scale);

            g2.setColor(Color.BLUE);
            g2.fill(clip);
            g2.scale(scale, scale);
            g2.setColor(Color.gray);
            g2.fill(box);
            g2.setColor(Color.red);
            g2.draw(box);
        }

    }

}
