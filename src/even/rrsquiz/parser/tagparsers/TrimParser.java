package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.Event;
import even.rrsquiz.animation.event.AutoTrimEvent;
import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class TrimParser extends AbstractTagParser
{

    public TrimParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        boolean autoTrim = getBooleanValue(atts, Attribute.auto, true);
        int frame = getIntValue(atts, Attribute.frame, 0);
        Event e = new AutoTrimEvent(frame, contentHandler.sailBoat, autoTrim);
        contentHandler.animation.addEvent(e);
    }

    @Override
    public void handleEnd() {
    }
}
