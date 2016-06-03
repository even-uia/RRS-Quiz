package even.rrsquiz.gui;

import even.rrsquiz.animation.Drawable;
import even.rrsquiz.animation.boat.BoatType;
import even.rrsquiz.animation.boat.NavData;
import even.rrsquiz.animation.boat.Sailboat;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 *
 * @author even
 */
public class TestDrawable extends JFrame
{

    private TestCanvas canvas;
    Action stepAnimationAction;
    Timer timer;
    int hdgDeg = 140;
    NavData nd;
    final Sailboat subject;

    public TestDrawable(Sailboat subject) {
        nd = new NavData(150, 150, Math.toRadians(140), 0);
        subject.setSpinnaker(true);
        this.subject = subject;
        this.subject.setNavData(nd);
        setVisible(false);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        setupGui();
        pack();
        setVisible(true);
        startTimer();
    }

    private void setupGui() {
        setLookAndFeel();
        setLayout(new BorderLayout(5, 5));
        createComponents();
    }

    /**
     * Set the application to use the system look and feel, i.e. it will look
     * like a windows program in windows, a mac program on a mac etc.
     */
    public void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception cnfe) {
        }
    }

    private void createComponents() {
        canvas = new TestCanvas(new Dimension(300, 300),
                                subject);
        add(canvas, BorderLayout.CENTER);

        stepAnimationAction = new AbstractAction("Step")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                hdgDeg++;
                if (hdgDeg > 180) hdgDeg = hdgDeg - 360;
                double hdg = (Math.toRadians(hdgDeg));
                nd.setHdg(hdg);
                subject.rig.update((hdg));
                canvas.repaint();
            }
        };
    }

    protected void exit() {
        stopTimer();

        for (java.awt.Window win : java.awt.Dialog.getWindows()) {
            win.dispose();
        }
        for (java.awt.Frame frame : java.awt.Frame.getFrames()) {
            frame.dispose();
        }
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer(1000 / 10, stepAnimationAction);
        }
//        setEnabledActions(false, false, true, false, false);
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    public static void main(String[] args) {
        Sailboat oppie = new Sailboat(BoatType.LASER, "oppie", Color.RED);
        TestDrawable td = new TestDrawable(oppie);
    }

    public class TestCanvas extends JComponent
    {

        private Color bgColor;
        Drawable testSubject;

        public TestCanvas(Dimension size, Drawable testSubject) {
            setPreferredSize(size);
            bgColor = new Color(0x55555ff);
            this.testSubject = testSubject;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setBackground(bgColor);
            testSubject.render(g2);
        }

    }
}
