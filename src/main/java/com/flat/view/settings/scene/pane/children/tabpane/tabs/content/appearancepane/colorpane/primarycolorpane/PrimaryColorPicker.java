package com.flat.view.settings.scene.pane.children.tabpane.tabs.content.appearancepane.colorpane.primarycolorpane;

import com.flat.models.fx.FxPreviewData;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * @author Christopher Brantley <ccbrantley@uncg.edu>
 */
public class PrimaryColorPicker extends ColorPicker {

    public PrimaryColorPicker() {
        this.setOnAction();
    }

    private void setOnAction() {
        super.setOnAction((event) -> {
            FxPreviewData.getPrimaryColor().setColor(new Color(super.getValue().getRed(), super.getValue().getGreen(), super.getValue().getBlue(), super.getOpacity()));
        });
    }

}