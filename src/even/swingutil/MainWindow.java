package even.swingutil;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;


/**
 * This is a convenience class for creating top-level windows, that can be
 * resized and moved around the screen.
 *
 * @author even
 */
public class MainWindow extends JFrame
{

    /**
     * This object will be used to exit the application. If you want/need
     * something to happen before the application closes, you should create your
     * own action to handle it.
     */
    private static Action exitHandler;

    /**
     * Default constructor
     *
     * @param title - will be shown on the window frame
     */
    public MainWindow(String title) {
        super(title);
        setVisible(false);
        setLookAndFeel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e) {
                exitHandler.actionPerformed(null);
            }
        });

        setExitHandler(new SimpleExit());
    }

    /**
     * Move the application to the center of the screen
     */
    public void center() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screen.getWidth() - getWidth()) / 2);
        int y = (int) ((screen.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    /**
     * Exit the applicaition, by calling the exitHandler
     */
    public void exit() {
        exitHandler.actionPerformed(null);
    }

    /**
     * Set the exitHandler.
     *
     * @param a
     */
    public static void setExitHandler(Action a) {
        exitHandler = a;
    }

    public static Action getExitHandler() {
        if (null == exitHandler) exitHandler = new SimpleExit();
        return exitHandler;
    }


    /**
     * This is the default exithandler. It closes, and destroys, all windows.
     * When all windows have been disposed of, the program terminates.
     */
    private static class SimpleExit extends AbstractAction
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (java.awt.Window win : java.awt.Dialog.getWindows()) {
                win.dispose();
            }
            for (java.awt.Frame frame : java.awt.Frame.getFrames()) {
                frame.dispose();
            }
        }

    }

    public MainWindow(String title, JPanel contentPane) {
        this(title);
        setContentPane(contentPane);
    }

    @Override
    public void setContentPane(Container contentPane) {
        super.setContentPane(contentPane);
        pack();
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

    /**
     * This code is copied from a student project. It changes some visual
     * properties, e.g. colors and fonts.
     *
     * @throws IOException
     */
    protected void setUIProperties() throws IOException {
        // swing properties
        // colours
        UIManager.getLookAndFeelDefaults().put(
                "Table:\"Table.cellRenderer\".background",
                new ColorUIResource(new java.awt.Color(51, 51, 51)));
        UIManager.put("Table.alternateRowColor",
                      new java.awt.Color(236, 235, 232));
        UIManager.put("TableHeader.background",
                      new java.awt.Color(236, 235, 232));

        // fonts
        UIManager.put("Label.font", new FontUIResource("tahome",
                                                       FontUIResource.BOLD, 12));
        UIManager.put("TextField.font", new FontUIResource("tahome",
                                                           FontUIResource.PLAIN, 12));
        UIManager.put("ComboBox.background", new ColorUIResource(
                      Color.WHITE));
        UIManager.put("ComboBox.disabledBackground", new Color(212,
                                                               212, 210));
        UIManager.put("ComboBox.disabledForeground", Color.BLACK);
    }

    private ImageIcon loadImageIcon(ArrayList<BufferedImage> icons,
                                    String path) throws IOException {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            BufferedImage li
                    = ImageIO.read(getClass().getResourceAsStream(path));
            icons.add(li);
            return new ImageIcon(imgURL);
        }
        else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
