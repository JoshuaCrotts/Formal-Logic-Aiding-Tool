package com.flat.view.main.panes.right.children.rulesaxioms.base.children.alternative.children;

import com.flat.view.main.panes.right.MainRight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Christopher Brantley <c_brantl@uncg.edu>
 */
public class RulesAxiomsAlternativeLabel extends Text {

    public RulesAxiomsAlternativeLabel () {
        super.setText("Alternative symbols");
        super.wrappingWidthProperty().bind(MainRight.WRAPPING_WIDTH);
        super.setTextAlignment(TextAlignment.CENTER);
    }

}