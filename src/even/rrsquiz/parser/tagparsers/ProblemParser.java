package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.Problem;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class ProblemParser extends AbstractTagParser
{

    public ProblemParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        contentHandler.problem = new Problem();
    }

    @Override
    public void handleEnd() {
    }
}
