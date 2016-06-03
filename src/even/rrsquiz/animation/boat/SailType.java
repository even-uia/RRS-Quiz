package even.rrsquiz.animation.boat;

public enum SailType
{

    MAIN(0.1, 5, 80, 12, 0.5),
    JIB(0.1, 10, 90, 12, 0.5),
    SPINNAKER(0.3, 40, 90, 30, 0.7),
    ASSYMETRIC(0.2, 30, 60, 30, 0.3);

    double depth; // sail profile depth as fraction of chord
    // angles in radians
    double minSheetAngle; // minimum reasonable sheeting angle
    double maxSheetAngle; // maximum sheeting angle, in
    double minAoA; // minimum angle of attack for sail to set properly
    double sheetRate; // d sheetAngle / d aoa

    private SailType(double depth, double minSheetAngle, double maxSheetAngle, double minAoA, double sheetRate) {
        this.depth = depth;
        this.minSheetAngle = Math.toRadians(minSheetAngle);
        this.maxSheetAngle = Math.toRadians(maxSheetAngle);
        this.minAoA = Math.toRadians(minAoA);
        this.sheetRate = sheetRate;
    }
}
