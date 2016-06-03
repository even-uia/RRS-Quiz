package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.parser.ProblemContentHandler;

/**
 *
 * @author even
 */
public class QuestionParser extends ContentParser
{

    public QuestionParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void storeContent(String content) {
        contentHandler.problem.setQuestion(content);
    }
}
