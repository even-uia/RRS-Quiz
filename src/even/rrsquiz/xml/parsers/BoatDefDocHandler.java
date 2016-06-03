package even.rrsquiz.xml.parsers;

import even.rrsquiz.animation.boat.BoatType;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;


/**
 *
 * @author even
 */
public class BoatDefDocHandler extends AbstractDocHandler<List<BoatType>>
{

    public BoatDefDocHandler() {
        super();
        result = new ArrayList<BoatType>();
    }


    public void setupHandlers() {
        handlers.put(Tag.boatdefs, new BoatDefsHandler());
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.hull, null);
        handlers.put(Tag.rig, null);
        handlers.put(Tag.performance, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);
        handlers.put(Tag.boattype, null);

    }


    private class BoatDefsHandler extends AbstractTagHandler
    {

        @Override
        public void handleEnd() {
        }


        @Override
        public void handleStart(Attributes atts) {
            result = new ArrayList<>();
        }

    }

}
