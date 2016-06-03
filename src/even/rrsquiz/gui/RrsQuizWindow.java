package even.rrsquiz.gui;

import even.rrsquiz.Problem;
import even.rrsquiz.animation.Animation;
import even.rrsquiz.parser.Parser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author even
 */
public class RrsQuizWindow extends JFrame
{

    public static Dimension DEFAULT_SIZE = new Dimension(800, 600);
    public static final Border LOW_BEVEL = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    public static final Border HIGH_BEVEL = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    public static final Border ETCHED = BorderFactory.createEtchedBorder(BevelBorder.LOWERED);

    private Canvas canvas;
    JPanel toolBar;
    JTextField messageBar;
    Timer timer;
    Action loadProblemAction,
            startAnimationAction, stopAnimationAction, stepAnimationAction,
            restartAnimationAction, exitAction;

    private Problem problem;
    private Animation animation;

    public RrsQuizWindow() {
        super("RRS Quiz");
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
    }

    @Override
    public void setSize(Dimension d) {
        System.out.println("Changig size");
        setSize(d.width, d.height);
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

    private void setupGui() {
        setLookAndFeel();
        setLayout(new BorderLayout(5, 5));

        setupActions();
        createComponents();
    }

    private void createComponents() {
        canvas = new Canvas(DEFAULT_SIZE);
        canvas.setBorder(ETCHED);
        add(canvas, BorderLayout.CENTER);

        JPanel toolBar = new JPanel();
        add(toolBar, BorderLayout.NORTH);
        toolBar.add(new JButton(loadProblemAction));
        toolBar.add(new JButton(startAnimationAction));
        toolBar.add(new JButton(stopAnimationAction));
        toolBar.add(new JButton(stepAnimationAction));
        toolBar.add(new JButton(restartAnimationAction));

        messageBar = new JTextField();
        add(messageBar, BorderLayout.SOUTH);
    }

    private void setupActions() {
        loadProblemAction = new AbstractAction("Load")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
                loadProblem();

                if (null != problem) {
                    setEnabledActions(true, true, true, true, true);
                    pack();
                    canvas.repaint();
                }
//                else setEnabledActions(true, false, false, false, false);

            }
        };

        startAnimationAction = new AbstractAction("Start")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        };

        stopAnimationAction = new AbstractAction("Stop")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
            }
        };

        stepAnimationAction = new AbstractAction("Step")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                animation.stepFrame();
                if (animation.getState() == Animation.State.FINISHED)
                    stopTimer();
                canvas.repaint();
            }
        };

        restartAnimationAction = new AbstractAction("Restart")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
                animation.reset();
            }
        };
        exitAction = new AbstractAction("Exit")
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        };
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer(1000 / animation.FRAMERATE, stepAnimationAction);
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

    private void loadProblem() {
        stopTimer();
        File f = selectFile();
        loadFile(f);
    }

    private File selectFile() {
        File file = null;
        JFileChooser fileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter
                = new FileNameExtensionFilter("Animation files", "xml");
        fileChooser.setFileFilter(filter);
        int opt = fileChooser.showOpenDialog(canvas);
        if (JFileChooser.APPROVE_OPTION == opt) {
            file = fileChooser.getSelectedFile();
        }
        return file;
    }

    private void loadFile(File file) {
        Parser p = new Parser(file);
        if (p.ready) {
            try {
                problem = p.parse();
                animation = problem.getAnimation();
                animation.setup();
                animation.reset();
                canvas.setAnimation(animation);
                pack();
            }
            catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }
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

    public static void main(String[] args) {
        RrsQuizWindow win = new RrsQuizWindow();
        win.setVisible(true);
    }
}
