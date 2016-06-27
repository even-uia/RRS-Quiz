package even.rrs.animation;

import even.rrs.animation.boat.NavData;
import even.rrs.animation.boat.Tack;
import even.rrs.animation.boatdef.Hull;
import even.rrs.render.AnimationRenderer;
import even.rrs.render.Scene;
import even.util.EventManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;


/**
 * Controls the starting/stopping pausing/... of the animation
 *
 * @author even
 */
public class Animation implements XmlNameConstants
{
    public static final int FRAMERATE = 10;
//    public static final String ANIMATION_SCHEMA = "animation.xsd";

    XmlParser parser;

    BoatBuilder boatBuilder;
    EventManager eventMgr;
    ActorManager actorMgr;

    SceneManager sceneManager;
    AnimationRenderer renderer;

    Dimension dim; // screen or real

    State state;
    Timer timer;
    Action stepAction;

    Namespace namespace;
    File animationFile;

    public Animation() {
        boatBuilder = new BoatBuilder();
        eventMgr = new EventManager();
        actorMgr = new ActorManager();
        sceneManager = new SceneManager(dim);
        parser = new XmlParser();
        stepAction = new AbstractAction("Step")
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepTime();
            }
        };
        state = State.EMPTY;
    }

    public void setRenderer(AnimationRenderer renderer) {
        this.renderer = renderer;
    }

    //////////////////////////////////////////////////
    // Loading animation from xml file
    //
    public void loadAnimation(String filename) {
        Document doc = parser.parseXmlFile(filename, ANIMATION_XSD_FILENAME, "animation");
        buildAnimation(doc);
    }

    public void loadAnimation(File xmlFile) {
        Document doc = parser.parseXmlFile(xmlFile,
                                           new File("xml", ANIMATION_XSD_FILENAME));
        buildAnimation(doc);
    }

    private void buildAnimation(Document doc) {
        Element root = doc.getRootElement();
        namespace = root.getNamespace();
        int width = parser.getIntValue(root, WIDTH);
        int height = parser.getIntValue(root, HEIGHT);
        dim = new Dimension(width, height);

        loadBoatData(root);
        // TODO wind arrow
        loadMarks(root);
        loadStartboat(root);
    }

    private void loadBoatData(Element root) {
        Element bde = root.getChild(BOATDATA, namespace);
        String boatFilename = bde.getAttributeValue(FILE);
        boatBuilder.parser = this.parser;
        boatBuilder.loadFile(boatFilename);
    }

    private void loadMarks(Element root) {
        List<Element> markElts = root.getChildren(MARK, namespace);
        for (Element me : markElts) {
            loadMark(me);
        }
    }

    private Mark loadMark(Element me) {
        double x = parser.getDoubleValue(me, X);
        double y = parser.getDoubleValue(me, Y);
        double size = parser.getDoubleValue(me, SIZE);
        Color colour = parser.getColourValue(me, COLOUR);
        Mark mark = new Mark(x, y, size, colour);
        sceneManager.add(mark);
        return mark;
    }

    private void loadStartboat(Element root) {
        Element sbe = root.getChild(STARTBOAT, namespace);
        double x = parser.getDoubleValue(sbe, X);
        double y = parser.getDoubleValue(sbe, Y);
        double twa = parser.getDoubleValue(sbe, TWA);
        Tack tack = Tack.valueOf(sbe.getAttributeValue(TACK));
        NavData pos = new NavData(x, y, tack, twa);
        Color colour = parser.getColourValue(sbe, COLOUR);
        StartBoat startBoat = new StartBoat(pos, colour);
        actorMgr.addActor(startBoat);
        sceneManager.add(startBoat);
        // todo - create startboat and starter

        Element hr = sbe.getChild(HULLREF, namespace);
        String hullId = hr.getAttributeValue(REF);
        Hull hull = boatBuilder.hulls.get(hullId);
        startBoat.setHull(hull);

        Element sme = sbe.getChild(MARK, namespace);
        if (sme != null) {
            Mark mark = loadMark(sme);
            startBoat.setMark(mark);
        }

        Element se = sbe.getChild(START, namespace);
        if (se != null) {
            int time = parser.getIntValue(se, TIME);
            String prepFlag = se.getAttributeValue(PREPFLAG);
            startBoat.setStart(time, prepFlag);
        }
    }

// /////////////////////////////////////
// State changing methods
//
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
        System.out.flush();
        if (state != State.FINISHED) {
            System.out.print("tick...");
            int time = eventMgr.stepTime();
            System.out.println(" time is now " + time);
            actorMgr.act(time);

            Scene scene = sceneManager.makeScene(dim, time);
            renderer.setScene(scene);

        }
        System.out.println("===============================\n== End callback\n");
        System.out.flush();
    }

    /**
     * Restart the animation
     */
    public void restart() {
        if (timer == null && state == State.FINISHED) {
//            loadAnimation(animationFile);
            toggleRunning(); // start the animation
        }
    }

    public State getState() {
        return state;
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
        EMPTY, PAUSED, RUNNING, FINISHED;
    }

}
