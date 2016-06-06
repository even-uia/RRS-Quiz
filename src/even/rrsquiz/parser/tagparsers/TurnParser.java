/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.AbstractEvent;
import even.rrsquiz.animation.action.AbstractAction;
import even.rrsquiz.animation.action.TwoPartTurnAction;
import even.rrsquiz.animation.boat.Sailboat;
import even.rrsquiz.animation.action.TurnAction;
import even.rrsquiz.animation.action.TurnRate;
import even.rrsquiz.animation.event.ActionEvent;
import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class TurnParser extends AbstractTagParser
{

    public TurnParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        int frame = getIntValue(atts, Attribute.frame, 0);
        int gybeAtHdg = getIntValue(atts, Attribute.at, 180);
        int toHdg = getIntValue(atts, Attribute.to, frame);
        TurnRate rate = getTurnRateValue(atts, Attribute.turnrate, TurnRate.STD);

        Sailboat boat = contentHandler.sailBoat;
        assert boat != null;
        AbstractAction a = createAction(Math.toRadians(toHdg), rate);
        if (a instanceof TwoPartTurnAction)
            ((TwoPartTurnAction) a).setGybeAtHdg(Math.toRadians(gybeAtHdg));
        AbstractEvent e = new ActionEvent(frame, boat, a);
        contentHandler.animation.addEvent(e);
    }

    @Override
    public void handleEnd() {
    }

    AbstractAction createAction(double hdg, TurnRate rate) {
        return new TurnAction(hdg, rate);
    }
}
