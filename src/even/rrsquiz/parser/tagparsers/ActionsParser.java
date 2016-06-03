package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;

/**
 *
 * @author even
 */
public class ActionsParser extends AbstractTagParser
{

    public ActionsParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        if (contentHandler.response != null) {
            String boatId = getStringValue(atts, Attribute.boat, null);
            contentHandler.boatId = boatId;
        }
        if (contentHandler.boatId == null && contentHandler.sailBoat == null) {
            throw new IllegalStateException("Orphaned events tag");
        }
    }

    @Override
    public void handleEnd() {
        contentHandler.boatId = null;
    }

}
