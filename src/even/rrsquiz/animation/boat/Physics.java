package even.rrsquiz.animation.boat;

/**
 *
 * @author even
 */
public class Physics
{

    Sailboat boat;

    double mass;
    double rm;
    double cDf;
    double cDw;

    public static final double xScale = 18.1;
    public static final double xTranslate = -10;
    public static final double yScale = 254;
    public static final double yTranslate = 2300;
    public static final double radToDeg = 180 / Math.PI;
    public static final double mystery = 2;
    public static final double frictionCoeff = 27;
    public static final double waveDragCoeff = 0.05;

    public Physics(Sailboat boat) {
        this.boat = boat;
    }

    /**
     * get the total force generated by the rig
     */
    public double getForwardForce(double twa) {
        double x = twa / xScale + xTranslate;
        double f = (x - mystery / (x * x)) * yScale + yTranslate;
        return f;
    }

    public double getDrag(double speed) {
        double v2 = speed * speed;
        double v4 = v2 * v2;
        return frictionCoeff * v2 + waveDragCoeff * v4;

    }

    public double getAccelearation() {
        return 0;
    }

    public static void main(String[] args) {
        Physics physics = new Physics(null);

        for (double x = 30; x < 181; x += 10) {
            double xx = physics.getForwardForce(x);
            System.out.format("x = %8.2f, x' = %8.2f\n", x, xx);
        }
    }
}
