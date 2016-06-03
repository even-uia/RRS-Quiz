/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.animation.action;

/**
 *
 * @author even
 */
public class TackAction extends TurnAction
{

    private TurnAction luff;

    public TackAction(double targetHdg, TurnRate rate) {
        super(targetHdg, rate);
        luff = new TurnAction(0, rate);
    }

    public TurnAction getLuff() {
        return luff;
    }

    public String toString() {
        return String.format("Tack[%d;%s]",
                             (int) Math.toDegrees(targetHdg),
                             rate);
    }
}
