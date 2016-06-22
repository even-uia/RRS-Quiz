package even.rrs.animation.boatdef;

import even.rrs.animation.Boat;
import java.awt.Color;



/**
 *
 * @author even
 */
public class BoatType
{

    String id;
    Hull hull;
    Rig rig;
    PerformanceData perfData;

    public BoatType(String id, Hull hull, Rig rig, PerformanceData perfData) {
        this.id = id;
        this.hull = hull;
        this.rig = rig;
        this.perfData = perfData;
    }

    public Boat buildBoat(String name, Color fillColour) {
        Boat boat = new Boat(this, name, fillColour);
        return boat;
    }

    // ////////////////////////////////////////
    // Getters and setters - lots of them....
    public void setId(String id) {
        this.id = id;
    }

//    public void setHullRef(String id) {
//        hullId = id;
//    }
//
//    public void setRigRef(String id) {
//        rigId = id;
//    }
//
//    public void setPerfRef(String id) {
//        perfId = id;
//    }
    public void setHull(Hull hull) {
        this.hull = hull;
    }

    public void setRig(Rig rig) {
        this.rig = rig;
    }

    public void setPerfData(PerformanceData perfData) {
        this.perfData = perfData;
    }

    public String getId() {
        return id;
    }

    public Hull getHull() {
        return hull;
    }

    public Rig getRig() {
        return rig;
    }

    public PerformanceData getPerfData() {
        return perfData;
    }

}
