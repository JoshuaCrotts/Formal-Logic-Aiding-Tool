package com.flat.view.main.menubar.items.file;

import com.flat.view.viewdata.menubar.MenuBarData;
import javafx.scene.control.MenuItem;

/**
 * @author Christopher Brantley <ccbrantley@uncg.edu>
 */
public class ExitMenuItem extends MenuItem {

    public ExitMenuItem() {
        super.textProperty().bind(MenuBarData.getExit().textProperty());
        super.setOnAction((event) -> {
            System.exit(0);
        });
    }

}