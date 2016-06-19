package even.rrs.gui;

import even.rrs.animation.Animation;
import even.rrs.render.AnimationRenderer;
import even.swingutil.MainWindow;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;


/**
 *
 * @author even
 */
public class AnimationPanel extends JPanel
{
    public static Dimension DEFAULT_SIZE = new Dimension(800, 600);
    public static final Border LOW_BEVEL = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    public static final Border HIGH_BEVEL = BorderFactory.createBevelBorder(BevelBorder.RAISED);

    private Animation animation;
    private AnimationRenderer renderer;
    private JPanel toolBar;
    Action toggleStartStop, stepTime, restart;

    public AnimationPanel() {
        super(new BorderLayout(5, 5));
        buildGui();
    }

    public void setAnimation(File animationXmlFile) {
        this.animation = new Animation(animationXmlFile, renderer, stepTime);
    }

    // ////////////////////////////////////
    // constructor helper methods
    /**
     * set up the gui components
     */
    private void buildGui() {
        renderer = new AnimationRenderer(DEFAULT_SIZE);
        renderer.setBorder(HIGH_BEVEL);
        add(renderer, BorderLayout.CENTER);
        toolBar = new JPanel();
        add(toolBar, BorderLayout.SOUTH);
        buildActions();

        toolBar.add(new JButton(toggleStartStop));
        toolBar.add(new JButton(stepTime));
        toolBar.add(new JButton(restart));

    }

    private void buildActions() {
        toggleStartStop = new AbstractAction("Start/Stop")
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("toggle animation");
                if (null != animation) animation.toggleRunning();
            }
        };

        stepTime = new AbstractAction("Step")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\n*********************************");
                System.out.println("*** Step animation one time tick!");
                if (null != animation) animation.stepTime();
            }
        };

        restart = new AbstractAction("Restart")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (animation != null) animation.restart();
            }

        };
    }

    private void setEnabledActions(boolean toggleRun,
                                   boolean stop,
                                   boolean step,
                                   boolean restart) {
        this.toggleStartStop.setEnabled(toggleRun);
        this.stepTime.setEnabled(step);
        this.restart.setEnabled(restart);
    }

    public static void main(String[] args) {
        AnimationPanel panel = new AnimationPanel();
        MainWindow window = new MainWindow("AnimTest", panel);
        panel.setAnimation(new File("testanim.xml"));
        window.pack();
        window.setVisible(true);
    }
}
