/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.WindArrow;
import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class WindParser extends AbstractTagParser
{

    public WindParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        int x = getIntValue(atts, Attribute.x, 50);
        int y = getIntValue(atts, Attribute.y, 50);
        WindArrow wind = new WindArrow(x, y);
        contentHandler.animation.addDrawable(wind);
    }

    @Override
    public void handleEnd() {
    }
}
