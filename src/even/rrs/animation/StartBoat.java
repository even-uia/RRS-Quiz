package even.rrs.animation;

import even.rrs.animation.boat.NavData;
import even.rrs.animation.boatdef.Hull;
import even.rrs.render.Drawable;
import even.rrs.render.Flag;
import even.rrs.render.SceneElement;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


/**
 *
 * @author even
 */
public class StartBoat implements Actor, Drawable
{
    NavData pos;
    Hull hull;
    Color colour;

    Mark mark;
    EnumSet<Flag> signals;
    StartProcedure start;

    public StartBoat(NavData pos, Color colour) {
        this.pos = pos;
        this.colour = colour;
        signals = EnumSet.noneOf(Flag.class);
    }

    public void setStart(int time, String prepFlag) {
        start = new StartProcedure(time,
                                   Flag.valueOf(prepFlag));
    }

    public void act(int t) {
        if (start != null) start.act(t);
    }

    @Override
    public List<SceneElement> getSceneElements() {
        List<SceneElement> shapes = new ArrayList<>();
        AffineTransform trfm = pos.getTransform();
        Shape hullShape = trfm.createTransformedShape(hull.getShape());

        shapes.add(new SceneElement("Startboat",
                                    hullShape, null, colour, null,
                                    signals));
        return shapes;
    }

    @Override
    public String getName() {
        return "Startboat";
    }

    public void setHull(Hull hull) {
        this.hull = hull;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public void setFlag(Flag flag) {
        signals.add(flag);
    }

    public void removeFlag(Flag flag) {
        signals.remove(flag);
    }


    private class StartProcedure
    {
        public static final int MINUTE = 20; // number of frames / "minute"
        int startTime;
        Flag classFlag, prepSignal;
        Step step;
        int nextStepTime;

        public StartProcedure(int startTime, Flag prepSignal) {
            this.startTime = startTime;
            this.prepSignal = prepSignal;
            step = Step.WARNING;
            nextStepTime = startTime - 5 * MINUTE;
        }

        public void act(int t) {
            if (t == nextStepTime) switch (step) {
                case WARNING:
                    setFlag(Flag.CLASSFLAG);
                    step = Step.PREPARATORY;
                    nextStepTime += MINUTE;
                    break;
                case PREPARATORY:
                    setFlag(prepSignal);
                    step = Step.ONEMINUTE;
                    nextStepTime = startTime - MINUTE;
                    break;
                case ONEMINUTE:
                    removeFlag(prepSignal);
                    step = Step.START;
                    nextStepTime = startTime;
                    break;
                case START:
                    removeFlag(Flag.CLASSFLAG);
                    step = Step.STARTED;
                    nextStepTime = 0;
                    break;
            }
        }
    }


    enum Step
    {
        WARNING, PREPARATORY, ONEMINUTE, START, STARTED;
    }

}
