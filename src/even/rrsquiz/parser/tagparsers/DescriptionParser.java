/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import even.rrsquiz.parser.ProblemContentHandler;
import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public class DescriptionParser extends ContentParser
{

    public DescriptionParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void storeContent(String content) {
        if (contentHandler.response != null)
            contentHandler.response.setDescription(content);
    }

}
