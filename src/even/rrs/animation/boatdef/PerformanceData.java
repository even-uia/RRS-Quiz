package even.rrs.animation.boatdef;

/**
 *
 * @author even
 */
public class PerformanceData
{
    String id;
    PolarData polar;

    public PerformanceData(String id, PolarData polar) {
        this.id = id;
        this.polar = polar;
    }

    public void addPolarDatum(double twa, double speed) {
        polar.addDatum(twa, speed);;
    }

    public String getId() {
        return id;
    }

    public PolarData getPolar() {
        return polar;
    }

}
