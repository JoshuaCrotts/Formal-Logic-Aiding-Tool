package com.flat.models.data.logicsymbols.propositional.turnstile;

import com.flat.models.data.base.text.KeyedText;
import com.flat.models.data.logicsymbols.base.LogicSymbol;
import com.flat.models.data.logicsymbols.base.attributes.Axioms;
import com.flat.models.data.logicsymbols.base.attributes.Symbols;
import com.flat.models.data.logicsymbols.propositional.turnstile.attributes.TurnstileAxioms;
import com.flat.models.data.logicsymbols.propositional.turnstile.attributes.TurnstileSymbols;

/**
 *
 * @author christopherbrantley
 */
public class TurnstileSymbol extends LogicSymbol {

    public TurnstileSymbol () {
        super(
                new KeyedText(TurnstileSymbol.class, LogicSymbol.Keys.LABEL, "Turnstile"),
                new TurnstileSymbols(),
                new KeyedText(TurnstileSymbol.class, LogicSymbol.Keys.TOOL_TIP, "Syntactic Entailment"),
                new KeyedText(TurnstileSymbol.class, LogicSymbol.Keys.DESCRIPTION, "This is the \\\"proves\\\" button."),
                new KeyedText(TurnstileSymbol.class, LogicSymbol.Keys.READ_AS, "Proves..."),
                new TurnstileAxioms()
        );
    }

    public TurnstileSymbol (KeyedText _label, Symbols _symbols, KeyedText _toolTip, KeyedText _description, KeyedText _readAs, Axioms _axioms) {
        super(_label, _symbols, _toolTip, _description, _readAs, _axioms);
    }

}