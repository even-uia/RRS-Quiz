package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.action.AbstractAction;
import even.rrsquiz.animation.action.TwoPartTurnAction;
import even.rrsquiz.animation.action.TurnRate;
import even.rrsquiz.parser.ProblemContentHandler;

/**
 *
 * @author even
 */
public class GybeParser extends TurnParser
{

    public GybeParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    AbstractAction createAction(double hdg1, TurnRate rate1, double hdg2, TurnRate rate2) {
        return new TwoPartTurnAction(hdg1, rate1, hdg2, rate2);
    }

    @Override
    public void handleEnd() {
    }
}
