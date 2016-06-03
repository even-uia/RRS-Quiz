/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class StartBoatParser extends AbstractTagParser
{

    public StartBoatParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        contentHandler.startBoat = new even.rrsquiz.animation.boat.StartBoat();
        contentHandler.animation.addActor(contentHandler.startBoat);
        contentHandler.animation.addDrawable(contentHandler.startBoat);

        double x = getFloatValue(atts, Attribute.x, 0);
        double y = getFloatValue(atts, Attribute.y, 0);
        double speed = 0;
        double hdg = getFloatValue(atts, Attribute.heading, speed);
    }

    @Override
    public void handleEnd() {
        contentHandler.startBoat = null;
    }
}
