package even.rrsquiz.animation.boat;

public enum RigType
{

    SINGLE(30, 210), SLOOP(30, 190), SPINNAKER(30, 185), ASSYMETRIC(30, 180);

    double minTwa, maxTwa;

    private RigType(double minTwa, double maxTwa) {
        this.minTwa = Math.toRadians(minTwa);
        this.maxTwa = Math.toRadians(maxTwa);
    }

};
