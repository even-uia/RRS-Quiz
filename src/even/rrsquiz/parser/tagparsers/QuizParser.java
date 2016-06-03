package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class QuizParser extends AbstractTagParser
{

    public QuizParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
    }

    @Override
    public void handleEnd() {
    }
}
