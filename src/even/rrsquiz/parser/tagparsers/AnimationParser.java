/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.animation.Animation;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class AnimationParser extends AbstractTagParser
{

    public AnimationParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        contentHandler.animation = new Animation();
    }

    @Override
    public void handleEnd() {
        contentHandler.problem.setAnimation(contentHandler.animation);
        contentHandler.animation = null;
    }
}
