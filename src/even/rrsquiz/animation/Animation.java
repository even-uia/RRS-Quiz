package even.rrsquiz.animation;

import even.rrsquiz.animation.boat.Sailboat;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The animation object uses a birds-eye view animation to present a situation.
 *
 * @author even
 */
public class Animation
{

    public static final int FRAMERATE = 20;

    private List<Actor> actors;
    private List<Drawable> visibleItems;
    private ArrayList<AbstractEvent> events;
    private PriorityQueue<AbstractEvent> eventQueue;

    private State state;

    private Dimension size; // canvas size in pixels
    public static final Color backgroundColour = new Color(0x7999af);
    public static final int margin = 0;
    int maxFrame;

    /**
     * The frame counter does double duty as the measure of time. This
     * conveniently makes the length of time between frames unity.
     */
    int curFrame;

    // ================ Parser Section ================
    public Animation() {
        actors = new ArrayList<>();
        visibleItems = new ArrayList<>();
        events = new ArrayList<>();
        state = State.PREPARING;
    }

    public void addEvent(AbstractEvent e) {
        events.add(e);
    }

    // ================ Lifecycle management Section ================
    /**
     * Do whatever is needed to prepare all objects that take part to be
     * prepared for the animation. If it succeeds the animation will be brought
     * to the READY state.
     *
     * @return
     */
    public void setup() {
        // prepare all actiors
        // note: the actors are responsible for preparing their associated
        // drawables
        for (Actor a : actors) a.setup();
        state = State.READY;
    }

    public void reset() {
        for (Actor a : actors) a.reset();
        // put the event into a priority queue
        eventQueue = new PriorityQueue<>(events);
        state = State.RUNNING;
        curFrame = 0;
    }

    // fasst forward to frame
    public void skipToFrame(int frame) {
        setup();
        state = State.RUNNING;
        while (curFrame < frame)
            stepFrame();
    }

    /**
     * Makes one step in the animation by advancing time to the next frame,
     * process the events, update the actors, and render the new frame
     */
    public void stepFrame() {
        if (state == State.RUNNING) { // || state == State.READY;
            curFrame++;
            if (curFrame >= maxFrame) state = State.FINISHED;

//            // System.out.println("Frame " + curFrame + " ==================");
            while (!eventQueue.isEmpty() && eventQueue.peek().getTime() == curFrame) {
                AbstractEvent e = eventQueue.poll();
                e.happen();
            }

            for (Actor a : actors)
                a.update(curFrame);
        }
//        for (Drawable item : visibleItems) item.render(g2);
    }

    public void render(Graphics2D g2) {
        for (Drawable item : visibleItems) item.render(g2);
        renderFrameNo(g2, curFrame);
    }

    private void renderFrameNo(Graphics2D g2, int frame) {
        Font f = g2.getFont();
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.YELLOW);
        String output = String.format("[%5d]", frame);
        g2.drawString("" + output, size.width - 50, 15);

    }

    // setters and getters
    public void setSize(Dimension d) {
        this.size = d;
    }

    public Dimension getSize() {
        return size;
    }

    public Sailboat getBoat(String id) {
        for (Actor a : actors) {
            if (a instanceof Sailboat) {
                Sailboat boat = (Sailboat) a;
                if (boat.getName().equals(id)) return boat;

            }
        }
        return null;
    }

    public int getFrame() {
        return curFrame;
    }

    public void setMaxFrames(int maxFrame) {
        this.maxFrame = maxFrame;
    }

    public void addActor(Actor a) {
        actors.add(a);
    }

    public void addDrawable(Drawable d) {
        visibleItems.add(d);
    }

    public State getState() {
        return state;
    }

    public enum State
    {

        PREPARING, READY, RUNNING, PAUSED, STOPPED, FINISHED;
    }
}
