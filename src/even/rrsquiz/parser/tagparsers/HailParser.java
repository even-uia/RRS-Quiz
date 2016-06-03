/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.Actor;
import even.rrsquiz.animation.Event;
import even.rrsquiz.animation.event.HailEvent;
import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class HailParser extends AbstractTagParser
{

    public HailParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        int frame = getIntValue(atts, Attribute.frame, 0);
        String hail = getStringValue(atts, Attribute.hail, null);
        int duration = getIntValue(atts, Attribute.duration, 1);
        Actor boat = null;
        if (contentHandler.startBoat != null) boat = contentHandler.startBoat;
        else if (contentHandler.sailBoat != null)
            boat = contentHandler.sailBoat;
        else throw new IllegalStateException("Hail must be made from a boat");
        Event startHail = new HailEvent(frame, boat, hail, duration);
        contentHandler.animation.addEvent(startHail);;
        Event endHail = new HailEvent(frame + duration, boat, null, 0);
        contentHandler.animation.addEvent(endHail);
    }

    @Override
    public void handleEnd() {
    }
}
