package even.rrs.animation;

import even.rrs.render.AnimationRenderer;
import even.rrs.render.Scene;
import static even.rrsquiz.animation.Animation.FRAMERATE;
import even.util.EventManager;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import javax.swing.Action;
import javax.swing.Timer;
import org.jdom2.Document;



/**
 * Controls the starting/stopping pausing/... of the animation
 *
 * @author even
 */
public class Animation
{

    public static final String ANIMATION_SCHEMA = "";

    BoatBuilder boatBuilder;
    EventManager eventMgr;
    ActorManager actorMgr;

    SceneManager sceneManager;
    AnimationRenderer renderer;

    Dimension dim; // screen or real

    State state;
    Timer timer;
    Action stepAction;

    File animationFile;

    public Animation(File animationXmlFile,
                     AnimationRenderer renderer,
                     Action stepAction) {
        boatBuilder = new BoatBuilder();
        boatBuilder.loadDesigns(new File("xml", "boatdefs.xml"));
        eventMgr = new EventManager();
        actorMgr = new ActorManager();
        // // System.out.println("dim (in constructor =" + dim);
        sceneManager = new SceneManager(dim);
        this.renderer = renderer;
        this.animationFile = animationXmlFile;
        this.stepAction = stepAction;
        loadAnimation(animationXmlFile);

    }

    // /////////////////////////////////////
    // State changing methods
    /**
     * Start the animation. Creates a new timer which triggers the stepAction
     */
    private void createTimer() {
        if (timer == null) {
            timer = new Timer(1000 / FRAMERATE, stepAction);
        }
    }

    /**
     * Start/stop the animation
     */
    public void toggleRunning() {
        if (timer == null) {
            createTimer();
            state = State.PAUSED;
        }

        if (timer.isRunning()) {
            timer.stop();
            state = State.PAUSED;
        }
        else {
            timer.start();
            state = State.RUNNING;
        }
        System.out.println("animation is " + state);
    }

    /**
     * Advances animation time one step (or tick).
     */
    public void stepTime() {
        System.out.println("\n\n===============================\n== Start callback");
        if (state == State.RUNNING) {
            System.out.print("tick...");
            int time = eventMgr.stepTime();
            System.out.println(" time is now " + time);
            actorMgr.act(time);

            Scene scene = sceneManager.makeScene(dim, time);
            renderer.setScene(scene);

            System.out.println("===============================\n== End callback\n");
        }
    }

    /**
     * Restart the animation
     */
    public void restart() {
        if (timer == null && state == State.FINISHED) {
            loadAnimation(animationFile);
            toggleRunning(); // start the animation
        }
    }

    /**
     * Parse and interpret scenario definition file
     *
     * @param animationXmlFile xml file
     */
    private void loadAnimation(File animationXmlFile) {
//        try {
//            XMLReaderJDOMFactory factory2 = new XMLReaderXSDFactory(ANIMATION_SCHEMA);
//            SAXBuilder sb2 = new SAXBuilder(factory2);
//            org.jdom2.Document doc = sb2.build(animationXmlFile);
        setup(null); //(doc);
//        }
//        catch (JDOMException | IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Set up the animation for playing
     *
     * @param doc the parsed xml animation file
     */
    private void setup(Document doc) {
        dim = new Dimension(20, 20);
        state = State.PAUSED;

        // DEBUG
        Boat boat = boatBuilder.buildBoat("skiff", "Skiff", Color.RED);
        Navigator nav = new Navigator(boat);
        actorMgr.addActor(nav);
        sceneManager.add(boat);
    }



    public static enum State
    {
        PAUSED, RUNNING, FINISHED;
    }

}
