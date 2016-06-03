package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.action.AbstractAction;
import even.rrsquiz.animation.action.TackAction;
import even.rrsquiz.animation.action.TurnRate;
import even.rrsquiz.parser.ProblemContentHandler;

/**
 *
 * @author even
 */
public class TackParser extends AbstractTagParser
{

    public TackParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    AbstractAction createAction(double hdg, TurnRate rate) {
        return new TackAction(hdg, rate);
    }

    @Override
    public void handleEnd() {
    }
}
