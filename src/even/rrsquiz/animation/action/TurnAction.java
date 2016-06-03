/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package even.rrsquiz.animation.action;

import even.rrsquiz.animation.action.AbstractAction;

/**
 *
 * @author even
 */
public class TurnAction extends AbstractAction
{

    public final double targetHdg;
    public final TurnRate rate;

    public TurnAction(double targetHdg, TurnRate rate) {
        this.targetHdg = targetHdg;
        this.rate = rate;
    }

    public String toString() {
        return String.format("Turn[%d;%s]",
                             (int) Math.toDegrees(targetHdg),
                             rate);
    }

}
