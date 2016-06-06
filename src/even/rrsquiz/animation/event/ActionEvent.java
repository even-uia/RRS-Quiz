package even.rrsquiz.animation.event;

import even.rrsquiz.animation.action.AbstractAction;
import even.rrsquiz.animation.AbstractEvent;
import even.rrsquiz.animation.boat.Sailboat;

/**
 *
 * @author even
 */
public class ActionEvent extends AbstractEvent
{

    private AbstractAction action;

    public ActionEvent(int frame, Sailboat actor, AbstractAction action) {
        super(frame, actor);
        this.action = action;
    }

    @Override
    public void happen() {
        Sailboat boat = (Sailboat) actor;
        boat.setAction(action);
    }

    public String toString() {
        return String.format("ActionEvent[%d;%s]", time, action.toString());
    }
}
