/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.parser.Attribute;
import even.rrsquiz.Response;
import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class ResponseParser extends AbstractTagParser
{

    public ResponseParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void handleStart(Attributes atts) {
        contentHandler.response = new Response();
        String label = getStringValue(atts, Attribute.label, "");
        contentHandler.response.setLabel(label);
        int points = getIntValue(atts, Attribute.points, 0);
    }

    @Override
    public void handleEnd() {
        contentHandler.problem.addResponse(contentHandler.response);
    }
}
