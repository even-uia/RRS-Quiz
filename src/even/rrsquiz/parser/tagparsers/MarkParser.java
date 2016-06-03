package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.Mark;
import even.rrsquiz.animation.boat.StartBoat;
import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class MarkParser extends AbstractTagParser
{

    public MarkParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        int x = getIntValue(atts, Attribute.x, 50);
        int y = getIntValue(atts, Attribute.y, 50);
        int size = getIntValue(atts, Attribute.size, 5);
        int zone = getIntValue(atts, Attribute.zone, 0);
        Mark mark = new Mark(x, y, size, zone);
        contentHandler.animation.addDrawable(mark);
        StartBoat startBoat = contentHandler.startBoat;
        if (null != startBoat)
            startBoat.setMark(mark);
    }

    @Override
    public void handleEnd() {
    }
}
