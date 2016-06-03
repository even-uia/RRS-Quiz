/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.parser.Attribute;
import even.rrsquiz.parser.ProblemContentHandler;
import java.awt.Dimension;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class SizeParser extends AbstractTagParser
{

    public SizeParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {

        int width = getIntValue(atts, Attribute.width, 800);
        int height = getIntValue(atts, Attribute.height, 600);
        int frames = getIntValue(atts, Attribute.frames, 800);
        contentHandler.animation.setSize(new Dimension(width, height));
        contentHandler.animation.setMaxFrames(frames);
    }

    @Override
    public void handleEnd() {
    }
}
