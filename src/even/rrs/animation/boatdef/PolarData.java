package even.rrs.animation.boatdef;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author even
 */
public class PolarData
{
    List<Datum> data;

    public PolarData() {
        data = new ArrayList<>();
    }

    public void addDatum(double twa, double speed) {
        data.add(new Datum(twa, speed));
    }

    public static final class Datum
    {
        double twa;
        double speed;

        public Datum(double twa, double speed) {
            this.twa = twa;
            this.speed = speed;
        }

    }

}
