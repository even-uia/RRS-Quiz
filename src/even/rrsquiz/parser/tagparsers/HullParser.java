package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.boat.Hull;
import even.rrsquiz.animation.boat.HullType;
import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import java.awt.Color;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class HullParser extends AbstractTagParser
{

    public HullParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        HullType type = getHullTypeValue(atts, Attribute.type, null);
        Color colour = getColourValue(atts, Attribute.colour, Color.gray);
        int length = getIntValue(atts, Attribute.length, 60);
        int beam = getIntValue(atts, Attribute.beam, 30);
        Hull hull = new Hull(type, colour, length, beam);
        if (contentHandler.sailBoat != null)
            contentHandler.sailBoat.setHull(hull);
        else if (contentHandler.startBoat != null)
            contentHandler.startBoat.setHull(hull);
    }

    @Override
    public void handleEnd() {
    }

}
