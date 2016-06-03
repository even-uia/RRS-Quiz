/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.util;

/**
 *
 * @author even
 */
public class MyMath
{

    private MyMath() {
    }

    public static boolean equal(double v1, double v2, double tolerance) {
        return Math.abs(v1 - v2) < tolerance;
    }

    public static double sin(double angle) {
        return (double) Math.sin(angle);
    }

    public static double cos(double angle) {
        return (double) Math.cos(angle);
    }
}
