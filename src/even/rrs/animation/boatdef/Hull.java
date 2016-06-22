package even.rrs.animation.boatdef;

import java.awt.Shape;



/**
 *
 * @author even
 */
public class Hull
{

    String id;
    double beam;
    double length;
    Shape path;

    public Hull(String id, double beam, double length) {
        this.id = id;
        this.beam = beam;
        this.length = length;
    }

    public void setShape(Shape path) {
        this.path = path;
        System.out.println("Hull.setShape < -- " + path);
    }

    public String getId() {
        return id;
    }

    public double getBeam() {
        return beam;
    }

    public double getLength() {
        return length;
    }

    public Shape getShape() {
        System.out.println("Hull.getPath() " + id + " " + path);
        return path;
    }

}
