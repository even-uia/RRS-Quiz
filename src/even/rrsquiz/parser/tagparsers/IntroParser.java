package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.parser.ProblemContentHandler;

/**
 *
 * @author even
 */
public class IntroParser extends ContentParser
{

    public IntroParser(ProblemContentHandler handler) {
        super(handler);
    }

    public void storeContent(String content) {
        contentHandler.problem.setIntro(content);
    }

}
