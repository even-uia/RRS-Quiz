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
public class TwoPartTurnAction extends AbstractAction
{

    final TurnAction bearaway;
    final TurnAction luff;

    public TwoPartTurnAction(TurnAction bearaway, TurnAction luff) {
        this.bearaway = bearaway;
        this.luff = luff;
    }

    public String toString() {
        return "TwoPartTurn " + bearaway + " " + luff;
    }

    public TurnAction getBearaway() {
        return bearaway;
    }

    public TurnAction getLuff() {
        return luff;
    }
}
