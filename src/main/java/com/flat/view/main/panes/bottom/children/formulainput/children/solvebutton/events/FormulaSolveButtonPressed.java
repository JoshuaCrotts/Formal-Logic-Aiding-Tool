package com.flat.view.main.panes.bottom.children.formulainput.children.solvebutton.events;

import com.flat.tools.buses.eventbus.components.Event;

/**
 *
 * @author Christopher Brantley <c_brantl@uncg.edu>
 */
public class FormulaSolveButtonPressed extends Event {

    public FormulaSolveButtonPressed () {
        super(Type.FORMULA_SOLVE_BUTTON_PRESSED);
    }

}