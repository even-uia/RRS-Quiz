package even.rrsquiz.xml.parsers;

import even.rrsquiz.animation.boat.BoatType;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


/**
 *
 * @author even
 */
public class BoatTypeParser extends XmlParser<List<BoatType>>
{

    public BoatTypeParser(BoatDefDocHandler handler)
            throws SAXException, ParserConfigurationException {
        super(handler);
    }

}
