package even.rrsquiz.gui;

import even.rrsquiz.Problem;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 *
 * @author even
 */
public class RrsQuizPanel extends JPanel
{

    public static Dimension DEFAULT_SIZE = new Dimension(800, 600);
    public static final Border LOW_BEVEL = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    public static final Border HIGH_BEVEL = BorderFactory.createBevelBorder(BevelBorder.RAISED);

    private Canvas canvas;
    JPanel toolBar;
    Timer timer;
    Action loadProblemAction,
            startAnimationAction, stopAnimationAction, stepAnimationAction,
            restartAnimationAction;

    private Problem problem;

    public RrsQuizPanel() {
        super(new BorderLayout(5, 5));
        setupGui();
    }

    private void setupGui() {
        canvas = new Canvas(DEFAULT_SIZE);
        canvas.setBorder(HIGH_BEVEL);
        add(canvas, BorderLayout.CENTER);
        JPanel toolBar = new JPanel();
        add(toolBar, BorderLayout.NORTH);
        setupActions();
    }

    private void stopAnimation() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    private void loadProblem() {

    }

    private void setupActions() {
        loadProblemAction = new AbstractAction("Load")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                stopAnimation();
                loadProblem();
                if (null != problem) {
                    setEnabledActions(false, true, false, true, false);

                }

            }
        };

    }

    private void setEnabledActions(boolean load,
                                   boolean start, boolean stop,
                                   boolean step, boolean restart) {
        loadProblemAction.setEnabled(load);
        startAnimationAction.setEnabled(start);
        stopAnimationAction.setEnabled(stop);
        stepAnimationAction.setEnabled(step);
        restartAnimationAction.setEnabled(restart);
    }

}
