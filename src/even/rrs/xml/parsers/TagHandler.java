package even.rrs.xml.parsers;

import org.xml.sax.Attributes;


/**
 *
 * @author even
 */
public interface TagHandler
{

    public void handleStart(Attributes atts);


    public void handleEnd();

}
