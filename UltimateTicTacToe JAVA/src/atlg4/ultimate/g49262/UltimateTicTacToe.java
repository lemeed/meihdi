
package atlg4.ultimate.g49262;


import atlg4.ultimate.g49262.model.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

/**
 * Represent a UTT view controller.
 * @author Meihdi El Amouri
 */
public class UltimateTicTacToe extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Game game = new Game();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Welcome.fxml"));
        WelcomeController welcome = new WelcomeController(game);
        loader.setController(welcome);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setMinHeight(450);
        stage.setMinWidth(650);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
