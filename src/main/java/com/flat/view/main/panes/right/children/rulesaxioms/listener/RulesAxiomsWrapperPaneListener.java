package com.flat.view.main.panes.right.children.rulesaxioms.listener;

import com.flat.models.data.logicsymbols.LogicSymbols.SymbolKey;
import com.flat.tools.buses.eventbus.components.Event;
import com.flat.tools.buses.eventbus.components.EventListener;
import com.flat.view.main.panes.left.children.logicsymbolspane.children.buttons.base.events.LogicButtonPressed;
import com.flat.view.main.panes.right.children.rulesaxioms.RulesAxiomsWrapperPane;
import com.flat.view.main.panes.right.children.rulesaxioms.predicate.ExistentialRulesAxiomsPane;
import com.flat.view.main.panes.right.children.rulesaxioms.predicate.UniversalRulesAxiomsPane;
import com.flat.view.main.panes.right.children.rulesaxioms.propositional.BiconditionalRulesAxiomsPane;
import com.flat.view.main.panes.right.children.rulesaxioms.propositional.ConjunctionRulesAxiomsPane;
import com.flat.view.main.panes.right.children.rulesaxioms.propositional.DisjunctionRulesAxiomsPane;
import com.flat.view.main.panes.right.children.rulesaxioms.propositional.DoubleTurnstileRulesAxiomsPane;
import com.flat.view.main.panes.right.children.rulesaxioms.propositional.ExclusiveDisjunctionRulesAxiomsPane;
import com.flat.view.main.panes.right.children.rulesaxioms.propositional.ImplicationRulesAxiomsPane;
import com.flat.view.main.panes.right.children.rulesaxioms.propositional.NegationRulesAxiomsPane;
import com.flat.view.main.panes.right.children.rulesaxioms.propositional.TurnstileRulesAxiomsPane;

/**
 *
 * @author Christopher Brantley <c_brantl@uncg.edu>
 */
public class RulesAxiomsWrapperPaneListener implements EventListener {
    private RulesAxiomsWrapperPane wrapperPane;

    public RulesAxiomsWrapperPaneListener (RulesAxiomsWrapperPane _wrapperPane) {
        this.wrapperPane = _wrapperPane;
    }

    @Override
    public void catchEvent(Event _event) {
        switch (_event.getType()) {
            case LOGIC_BUTTON_PRESSED:
                this.addContent(((LogicButtonPressed)_event).getSymbolKey());
                break;
        }
    }

    private void addContent(SymbolKey _symbolKey) {
        switch (_symbolKey) {
            case EXISTENTIAL:
                this.wrapperPane.setContent(new ExistentialRulesAxiomsPane());
                break;
            case UNIVERSAL:
                this.wrapperPane.setContent(new UniversalRulesAxiomsPane());
                break;
            case BICONDITIONAL:
                this.wrapperPane.setContent(new BiconditionalRulesAxiomsPane());
                break;
            case CONJUNCTION:
                this.wrapperPane.setContent(new ConjunctionRulesAxiomsPane());
                break;
            case DISJUNCTION:
                this.wrapperPane.setContent(new DisjunctionRulesAxiomsPane());
                break;
            case DOUBLE_TURNSTILE:
                this.wrapperPane.setContent(new DoubleTurnstileRulesAxiomsPane());
                break;
            case EXCLUSIVE_DISJUNCTION:
                this.wrapperPane.setContent(new ExclusiveDisjunctionRulesAxiomsPane());
                break;
            case IMPLICATION:
                this.wrapperPane.setContent(new ImplicationRulesAxiomsPane());
                break;
            case NEGATION:
                this.wrapperPane.setContent(new NegationRulesAxiomsPane());
                break;
            case TURNSTILE:
                this.wrapperPane.setContent(new TurnstileRulesAxiomsPane());
                break;
            default:
                return;
        }
    }

}