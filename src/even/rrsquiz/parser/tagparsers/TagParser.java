/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.parser.tagparsers;

import org.xml.sax.Attributes;

/**
 *
 * @author even
 */
public interface TagParser
{

    public void handleStart(Attributes atts);

    public void handleEnd();

}
