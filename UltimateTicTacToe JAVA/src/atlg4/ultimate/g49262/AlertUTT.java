/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262;

import javafx.scene.control.Alert;

/**
 * Allows to show an alertMessage in UTT game.
 * @author 49262
 */
public class AlertUTT extends Alert {

    public AlertUTT(AlertType alertType, String title, String header, String msg) {
        super(alertType);
        this.setTitle(title);
        this.setHeaderText(header);
        this.setContentText(msg);
        this.showAndWait();

    }

}
