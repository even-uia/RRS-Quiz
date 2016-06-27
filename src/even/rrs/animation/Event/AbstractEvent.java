package even.rrs.animation.Event;

import even.rrs.animation.Actor;
import even.util.Event;


/**
 *
 * @author even
 */
public abstract class AbstractEvent implements Event
{
    int time;
    Actor actor;

    public AbstractEvent(int time, Actor actor) {
        this.time = time;
        this.actor = actor;
    }

    @Override
    public int getTime() {
        return time;
    }
}
