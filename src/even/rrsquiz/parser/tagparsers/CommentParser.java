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
public class CommentParser extends ContentParser
{

    public CommentParser(ProblemContentHandler contentHandler) {
        super(contentHandler);
    }

    @Override
    public void storeContent(String content) {
        contentHandler.problem.setComment(content);
    }

}
