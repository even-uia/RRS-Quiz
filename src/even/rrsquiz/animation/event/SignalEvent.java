package even.rrsquiz.animation.event;

import even.rrsquiz.animation.Event;
import even.rrsquiz.animation.boat.Flag;
import even.rrsquiz.animation.boat.StartBoat;

/**
 *
 * @author even
 */
public class SignalEvent extends Event
{

    Flag up, down;

    public SignalEvent(int frame, StartBoat startBoat, Flag up, Flag down) {
        super(frame, startBoat);
        this.up = up;
        this.down = down;
    }

    @Override
    public void happen() {
        StartBoat startBoat = (StartBoat) actor;
        if (up != null)
            startBoat.setSignal(up);
        if (down != null)
            startBoat.strikeSignal(down);
    }
}
