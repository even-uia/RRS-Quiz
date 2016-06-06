package even.rrsquiz.animation.event;

import even.rrsquiz.animation.Actor;
import even.rrsquiz.animation.AbstractEvent;

public class HailEvent extends AbstractEvent
{

    private String hail;
    private int duration;

    public HailEvent(int frame, Actor boat, String hail, int duration) {
        super(frame, boat);
        this.hail = hail;
        this.duration = duration;
    }

    public void happen() {
        actor.setHail(hail);
    }

    public String toString() {
        return super.toString() + "[" + time + ",'" + hail + "', " + duration + "]";
    }
}
