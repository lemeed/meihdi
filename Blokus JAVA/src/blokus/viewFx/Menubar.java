package blokus.viewFx;

import blokus.model.Model;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Region;

/**
 *
 * @author Meihdi El Amouri
 */
public class Menubar extends MenuBar {
private Alert story;
    /**
     * Constructor of the upper menu bar
     *
     * @param g
     */
    public Menubar(Model g) {

        Menu file = new Menu("File");
        Menu edit = new Menu("edit");
        Menu help = new Menu("Help");
        Menu rules = new Menu("Rules");
        MenuItem dvp = new MenuItem("L’un après l’autre les joueurs placent une de leurs pièces sur le plateau de jeu\n"
                + "Chaque joueur dispose d’un jeu de pièces identiques en début de partie. \n"
                + "tout en essayant de toucher le coin d'une pièce posé précedemment\n"
                + "Chaque joueur commence dans un coin du plateau... Bonne chance !");
        rules.addEventHandler(Event.ANY, (Event event) -> {
        });
        MenuItem exit = new MenuItem("Exit");
        MenuItem histo = new MenuItem("Historique");
        file.getItems().add(exit);
        help.getItems().add(rules);
        rules.getItems().add(dvp);
        edit.getItems().add(histo);
        this.getMenus().addAll(file, help,edit);
        this.story = new Alert(Alert.AlertType.INFORMATION);
       
story.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        exit.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        histo.setOnAction((ActionEvent e) ->{
            update(g);
        });

        this.getMenus().setAll(file, help,edit);

    }
    public void update(Model g){
        this.story.setTitle("historique");
        this.story.setContentText(g.getHistorique());
        this.story.showAndWait();
    }
}
