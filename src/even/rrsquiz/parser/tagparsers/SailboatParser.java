package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.boat.PerformanceProfile;
import even.rrsquiz.animation.boat.Sailboat;
import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class SailboatParser extends AbstractTagParser
{

    public SailboatParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        String name = getStringValue(atts, Attribute.id, "");
        PerformanceProfile perf = getPerformanceProfileValue(atts,
                                                             Attribute.performanceprofile,
                                                             PerformanceProfile.SKIFF);
        contentHandler.sailBoat = new Sailboat(name, perf);
        contentHandler.animation.addActor(contentHandler.sailBoat);
        contentHandler.animation.addDrawable(contentHandler.sailBoat);
    }

    @Override
    public void handleEnd() {
        contentHandler.sailBoat = null;
    }

    public static PerformanceProfile getPerformanceProfileValue(Attributes atts,
                                                                Attribute name,
                                                                PerformanceProfile defval) {
        String s = atts.getValue(name.toString());
        if (null == s || s.length() == 0)
            return defval;
        else
            return PerformanceProfile.valueOf(s);

    }
}
