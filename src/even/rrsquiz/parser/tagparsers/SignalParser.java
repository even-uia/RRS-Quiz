/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.AbstractEvent;
import even.rrsquiz.animation.boat.Flag;
import even.rrsquiz.animation.event.SignalEvent;
import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class SignalParser extends AbstractTagParser
{

    public SignalParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        int frame = getIntValue(atts, Attribute.frame, -1);
        Flag up = getFlagValue(atts, Attribute.up, null);
        Flag down = getFlagValue(atts, Attribute.down, null);
        AbstractEvent e = new SignalEvent(frame, contentHandler.startBoat, up, down);
        contentHandler.animation.addEvent(e);
    }

    @Override
    public void handleEnd() {
    }
}
