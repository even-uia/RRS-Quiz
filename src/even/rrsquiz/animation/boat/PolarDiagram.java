package even.rrsquiz.animation.boat;

/**
 * This class is faking velocity prediction. It computes a reasonable terminal
 * speed for almost any combination of heading and sail setting
 *
 * @author even
 */
public class PolarDiagram
{

    double[] twa;
    double[] spd;
    int size;

    public PolarDiagram() {
    }

    public PolarDiagram(double[] twa, double[] spd) {
        if (twa.length != spd.length) {
            throw new IllegalArgumentException();
        }
        this.twa = twa;
        this.spd = spd;
        size = twa.length;
        for (int i = 0; i < twa.length; i++)
            twa[i] = (double) Math.toRadians(twa[i]);
    }

    private int indexOf(double twa) {
        for (int i = 0; i < size; i++) {
            if (twa <= this.twa[i]) {
                return i;
            }
        }
        return size;
    }

    public double getTargetSpeed(double hdg, boolean trimmed) {
        double boatTwa = (double) Math.abs(hdg);
        int i = indexOf(boatTwa);
        double retval;

        if (i == 0) {
            retval = spd[0];
        }
        else if (i == size) {
            retval = spd[size - 1];
        }
        else {
            double x1 = this.twa[i - 1];
            double x2 = this.twa[i];
            double y1 = spd[i - 1];
            double y2 = spd[i];

            retval = y1 + (boatTwa - x1) * (y2 - y1) / (x2 - x1);
        }

        if (trimmed)
            return retval;
        else
            return retval * hdg / Math.PI;
    }

    public double getAcceleration(NavData navData,
                                  boolean trimmed) {
        double boatTwa = Math.abs(navData.getHdg());
        double a = getTargetSpeed(boatTwa, trimmed) - navData.getSpd();
        if (trimmed) {
            if (a > 0)
                return a;
            else
                return a / 2;
        }
        else {
            if (a > 0)
                return a / 2;
            else
                return a;
        }

    }
}
