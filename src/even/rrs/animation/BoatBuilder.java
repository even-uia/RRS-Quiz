package even.rrs.animation;

import even.rrs.animation.boatdef.BoatType;
import java.awt.Color;
import java.util.Map;


/**
 *
 * @author even
 */
public class BoatBuilder
{
    Map<String, BoatType> boatTypes;

    public BoatBuilder(Map<String, BoatType> boatTypes) {
        this.boatTypes = boatTypes;
    }

    public Boat buildBoat(String typeName, String boatName, Color colour) {
        BoatType type = boatTypes.get(typeName);
        Boat boat = new Boat(type, boatName, colour);
        return boat;
    }

}
