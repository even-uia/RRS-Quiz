package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.boat.NavData;
import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class NavDataParser extends AbstractTagParser
{

    public NavDataParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        super.handleStart(atts);

        double x = getFloatValue(atts, Attribute.x, 0);
        double y = getFloatValue(atts, Attribute.y, 0);
        int hdg = getIntValue(atts, Attribute.heading, 0);
        double spd = getFloatValue(atts, Attribute.speed, 0);

        NavData nd = new NavData(x, y, Math.toRadians(hdg), spd);
        if (contentHandler.startBoat != null)
            contentHandler.startBoat.setNavData(nd);
        else if (contentHandler.sailBoat != null)
            contentHandler.sailBoat.setNavData(nd);
    }

    @Override
    public void handleEnd() {
    }

}
