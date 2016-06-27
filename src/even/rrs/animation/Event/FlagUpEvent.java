package even.rrs.animation.Event;

import even.rrs.animation.Actor;
import even.rrs.animation.StartBoat;
import even.rrs.render.Flag;


/**
 *
 * @author even
 */
public class FlagUpEvent extends AbstractEvent
{
    Flag flag;

    public FlagUpEvent(int time, Actor actor, Flag flag) {
        super(time, actor);
        this.flag = flag;
    }

    @Override
    public void happen() {
        StartBoat startBoat = (StartBoat) actor;
        startBoat.setFlag(flag);
    }
}
